package io.github.xausky.cfmp;

import io.github.xausky.cfmp.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
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
import java.util.*;

/**
 * Created by xausky on 10/31/16.
 */
@Mojo(name = "fusion")
public class FusionMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Component
    private DependencyTreeBuilder dependencyTreeBuilder;

    @Parameter( defaultValue = "${localRepository}", readonly = true )
    private ArtifactRepository localRepository;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            long start = System.currentTimeMillis();
            Map<String, ClassNode> classes = new TreeMap<String, ClassNode>();
            Map<String, Set<String>> imps = new TreeMap<String, Set<String>>();
            Set<File> artifactFiles = new TreeSet<>();

            //获取所有依赖jar包和class路径
            DependencyNode rootNode = dependencyTreeBuilder.buildDependencyTree(project,localRepository,
                    new AllArtifactFilter());
            CollectingDependencyNodeVisitor visitor = new CollectingDependencyNodeVisitor();
            rootNode.accept(visitor);
            List<DependencyNode> nodes = visitor.getNodes();
            for (DependencyNode node : nodes) {
                //排除不依赖class-fusion-core的artifact
                if(FusionDependencyFilter.include(node)){
                    Artifact artifact = node.getArtifact();
                    File file = artifact.getFile();
                    if(file == null){
                        file = localRepository.find(artifact).getFile();
                    }
                    artifactFiles.add(file);
                }
            }
            getLog().info(String.format("artifacts count : %d artifacts",artifactFiles.size()));
            for (File file : artifactFiles) {
                getLog().info("artifact file   : " + file.getName());
                if (file.isDirectory()) {
                    //目录形式的class路径
                    PathParser.parser(file, classes, imps);
                } else {
                    //Jar包形式的class路径
                    JarParser.parser(file, classes, imps);
                }
            }

            for (String key : classes.keySet()) {
                getLog().info("cache class     : " + key);
            }
            for (String key : imps.keySet()) {
                getLog().info("fusion class    : " + key);
                for (String imp : imps.get(key)) {
                    getLog().info("implements      : -- " + imp);
                }
                ClassWriter writer = new ClassWriter(0);
                ClassFusion.fusion(writer, key, imps.get(key), classes);
                File classfile = new File(project.getArtifact().getFile().getAbsolutePath() + File.separator + key + ".class");
                FileUtils.forceMkdir(classfile.getParentFile());
                FileUtils.writeByteArrayToFile(classfile, writer.toByteArray());
            }
            getLog().info(String.format("fusion time     : %d ms.", System.currentTimeMillis() - start));
        }catch (Exception e){
            e.printStackTrace();
            throw new MojoFailureException(e.getMessage());
        }
    }
}
