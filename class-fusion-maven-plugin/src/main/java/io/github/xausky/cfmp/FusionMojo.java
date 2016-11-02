package io.github.xausky.cfmp;

import io.github.xausky.cfmp.utils.ClassFusion;
import io.github.xausky.cfmp.utils.FusionArtifactFilter;
import io.github.xausky.cfmp.utils.JarParser;
import io.github.xausky.cfmp.utils.PathParser;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.CollectingDependencyNodeVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.*;

/**
 * Created by xausky on 10/31/16.
 */
@Mojo(name = "fusion")
public class FusionMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;

    @Component( hint = "default" )
    private DependencyGraphBuilder dependencyGraphBuilder;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            long start = System.currentTimeMillis();
            Map<String, ClassNode> classes = new TreeMap<String, ClassNode>();
            Map<String, Set<String>> imps = new TreeMap<String, Set<String>>();
            Set<File> artifactFiles = new TreeSet<>();

            //获取所有依赖jar包和class路径
            ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
            buildingRequest.setProject(project);
            DependencyNode rootNode = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, new FusionArtifactFilter());
            CollectingDependencyNodeVisitor visitor = new CollectingDependencyNodeVisitor();
            rootNode.accept(visitor);
            List<DependencyNode> nodes = visitor.getNodes();
            for (DependencyNode dependencyNode : nodes) {
                Artifact artifact = dependencyNode.getArtifact();
                artifactFiles.add(artifact.getFile());
            }

            for (File file : artifactFiles) {
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
            getLog().info(String.format("artifacts count : %d artifacts",artifactFiles.size()));
            getLog().info(String.format("fusion time     : %d ms.", System.currentTimeMillis() - start));
        }catch (Exception e){
            e.printStackTrace();
            throw new MojoFailureException(e.getMessage());
        }
    }
}
