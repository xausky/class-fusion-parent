package io.github.xausky.cfmp.utils;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.*;

/**
 * Created by xausky on 11/1/16.
 */
public class PathParser {
    public static void parser(File path, Map<String,ClassNode> classes, Map<String,Set<String>> itfs, Map<String,String> imps, Set<String> fusions){
        try {
            Collection<File> files = FileUtils.listFiles(path,new String[]{"class"},true);
            for(File file:files){
                byte[] classData = FileUtils.readFileToByteArray(file);
                ClassParser.parser(classData,classes,itfs,imps, fusions);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
