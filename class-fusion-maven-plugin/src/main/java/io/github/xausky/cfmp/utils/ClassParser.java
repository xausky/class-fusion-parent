package io.github.xausky.cfmp.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.logging.Logger;

/**
 * 解析一个class文件的工具类.
 * Created by xausky on 10/31/16.
 */
public class ClassParser {
    /**
     * 解析一个class文件
     * @param data class文件字节数据
     * @param classes class缓存
     * @param itfs 目标类与接口映射
     * @param imps 接口与实现映射
     * @param fusions 目标类名集合
     */
    public static void parser(byte[] data, Map<String,ClassNode> classes,
                              Map<String,Set<String>> itfs,
                              Map<String,String> imps,
                              Set<String> fusions){
        try{
            ClassReader reader = new ClassReader(data);
            ClassNode root = new ClassNode();
            reader.accept(root,0);
            List<String> implItfs = parserFusionImpl(root);
            if(implItfs != null){
                for(String itf:implItfs){
                    imps.put(itf,root.name);
                }
                classes.put(root.name,root);
            }
            List<String> fusionItfs = parserFusion(root);
            if(fusionItfs != null){
                Set<String> set = itfs.get(root.name);
                if(set == null){
                    set = new TreeSet<String>();
                    itfs.put(root.name,set);
                }
                set.addAll(fusionItfs);
                classes.put(root.name,root);
                fusions.add(root.name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //是否是FusionClass并解析出接口
    private static List<String> parserFusionImpl(ClassNode root){
        List<AnnotationNode> anns = root.visibleAnnotations;
        if(anns != null) {
            for (AnnotationNode ann : anns) {
                if (ann.desc.equals("Lio/github/xausky/cfa/FusionImpl;")) {
                    return root.interfaces;
                }
            }
        }
        return null;
    }

    //是否是FusionTemplate并解析出接口
    private static List<String> parserFusion(ClassNode root){
        List<AnnotationNode> anns = root.visibleAnnotations;
        if(anns != null) {
            for (AnnotationNode ann : anns) {
                if (ann.desc.equals("Lio/github/xausky/cfa/Fusion;")) {
                    return root.interfaces;
                }
            }
        }
        return null;
    }
}
