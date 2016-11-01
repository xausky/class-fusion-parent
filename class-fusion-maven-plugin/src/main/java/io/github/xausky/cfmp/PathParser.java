package io.github.xausky.cfmp;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.IOUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xausky on 11/1/16.
 */
public class PathParser {
    public static void parser(File path, Map<String,ClassNode> classes, Map<String,Set<String>> imps){
        try {
            Collection<File> files = FileUtils.listFiles(path,new String[]{"class"},true);
            for(File file:files){
                byte[] classData = FileUtils.readFileToByteArray(file);
                ClassParser.parser(classData,classes,imps);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
