package io.github.xausky.cfmp;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.IOUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xausky on 10/31/16.
 */
@Mojo(name = "fusion")
public class FusionMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        long start = System.currentTimeMillis();
        Map<String, ClassNode> classes = new TreeMap<String, ClassNode>();
        Map<String, Set<String>> imps = new TreeMap<String, Set<String>>();
        Set<Artifact> artifacts = project.getDependencyArtifacts();
        List<File> classFiles = new ArrayList<>();
        //添加依赖class路径
        for (Artifact artifact : artifacts) {
            classFiles.add(artifact.getFile());
        }
        //添加本身class路径
        classFiles.add(project.getArtifact().getFile());

        for (File file : classFiles) {
            if (file.isDirectory()) {
                //目录形式的class路径
                PathParser.parser(file, classes, imps);
            } else {
                //Jar包形式的class路径
                JarParser.parser(file, classes, imps);
            }
        }

        for (String key : classes.keySet()) {
            getLog().info("classes: " + key);
        }

        for (String key : imps.keySet()) {
            getLog().info("imps: " + key);
            for (String imp : imps.get(key)) {
                getLog().info("imps: -- " + imp);
            }
            try {
                ClassWriter writer = new ClassWriter(0);
                ClassFusion.fusion(writer, key, imps.get(key), classes);
                File classfile = new File(project.getArtifact().getFile().getAbsolutePath() + File.separator + key + ".class");
                FileUtils.forceMkdir(classfile.getParentFile());
                FileUtils.writeByteArrayToFile(classfile, writer.toByteArray());
            } catch (Exception e) {
                throw new MojoFailureException(e.getMessage());
            }
        }
        getLog().info(String.format("fusion time:%d ms",System.currentTimeMillis()-start));
    }
}
