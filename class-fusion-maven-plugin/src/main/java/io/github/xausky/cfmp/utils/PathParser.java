package io.github.xausky.cfmp.utils;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.*;

/**
 * 解析一个路径内class的静态工具类
 * Created by xausky on 11/1/16.
 */
public class PathParser {
    /**
     * 解析一个合成类所在路径.
     * @param path 要解析的路径
     * @param classes 缓存类的集合
     * @param itfs 合成目标与实现接口的映射
     * @param imps 接口与实现类的映射
     * @param fusions 合成目标类的集合
     */
    public static void parser(File path,
                              Map<String,ClassNode> classes,
                              Map<String,Set<String>> itfs,
                              Map<String,String> imps,
                              Set<String> fusions){
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
