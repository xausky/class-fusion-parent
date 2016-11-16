package io.github.xausky.cfmp;

import io.github.xausky.cfmp.utils.ClassFusion;
import io.github.xausky.cfmp.utils.FusionDependencyFilter;
import io.github.xausky.cfmp.utils.JarParser;
import io.github.xausky.cfmp.utils.PathParser;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.traversal.CollectingDependencyNodeVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 插件入口类.
 * Created by xausky on 10/31/16.
 */
@Mojo(name = "fusion")
public class FusionMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Component
    private DependencyTreeBuilder dependencyTreeBuilder;

    @Parameter(defaultValue = "${localRepository}", readonly = true)
    private ArtifactRepository localRepository;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            long start = System.currentTimeMillis();
            Map<String, ClassNode> classes = new HashMap<>();
            Map<String, Set<String>> itfs = new HashMap<>();
            Map<String, String> imps = new HashMap<>();
            Set<String> fusions = new HashSet<>();
            Artifact projectArtifact = project.getArtifact();

            //artifactFiles必须保证有序
            List<File> artifactFiles = new LinkedList<>();

            //获取所有依赖jar包和class路径
            DependencyNode rootNode = dependencyTreeBuilder.buildDependencyTree(project,
                    localRepository, new ArtifactFilter() {
                        @Override
                        public boolean include(Artifact artifact) {
                            return true;
                        }
                    });
            CollectingDependencyNodeVisitor visitor = new CollectingDependencyNodeVisitor();
            rootNode.accept(visitor);
            List<DependencyNode> nodes = visitor.getNodes();
            for (DependencyNode node : nodes) {
                //排除不依赖class-fusion-core的artifact
                if (FusionDependencyFilter.include(node)) {
                    Artifact artifact = node.getArtifact();
                    //排除当前项目的Artifact
                    if(!projectArtifact.equals(artifact)) {
                        File file = artifact.getFile();
                        if (file == null) {
                            file = localRepository.find(artifact).getFile();
                        }
                        artifactFiles.add(file);
                    }
                }
            }
            //添加当前项目Artifact,保证优先级
            artifactFiles.add(projectArtifact.getFile());
            getLog().info(String.format("artifacts count : %d artifacts", artifactFiles.size()));
            for (File file : artifactFiles) {
                getLog().info("artifact file   : -- " + file.getName());
                if (file.isDirectory()) {
                    //目录形式的class路径
                    PathParser.parser(file, classes, itfs, imps, fusions);
                } else {
                    //Jar包形式的class路径
                    JarParser.parser(file, classes, itfs, imps, fusions);
                }
            }

            for (String key : classes.keySet()) {
                getLog().info("cache class     : " + key);
            }
            for (String key : fusions) {
                getLog().info("fusion class    : " + key);
                for (String itf : itfs.get(key)) {
                    getLog().info("implements      : -- " + itf + " --> " + imps.get(itf));
                }
                ClassWriter writer = new ClassWriter(0);
                ClassFusion.fusion(writer, key, itfs.get(key), imps, classes);
                File fusionFile = new File(projectArtifact.getFile().getAbsolutePath()
                        + File.separator + key + ".class");
                if(fusionFile.exists()){
                    //备份原class文件
                    File originFile = new File(projectArtifact.getFile().getAbsolutePath()
                            + File.separator + key + ".class.origin");
                    if(!originFile.exists()) {
                        FileUtils.moveFile(fusionFile, originFile);
                    }
                }else {
                    FileUtils.forceMkdir(fusionFile.getParentFile());
                }
                FileUtils.writeByteArrayToFile(fusionFile, writer.toByteArray());
            }
            getLog().info(String.format("fusion time     : %d ms.",
                    System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoFailureException(e.getMessage());
        }
    }
}
