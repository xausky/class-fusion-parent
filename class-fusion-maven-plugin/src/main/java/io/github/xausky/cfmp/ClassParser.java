package io.github.xausky.cfmp;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by xausky on 10/31/16.
 */
public class ClassParser {
    public static void parser(byte[] data, Map<String,ClassNode> classes , Map<String,Set<String>> imps){
        try{
            ClassReader reader = new ClassReader(data);
            ClassNode root = new ClassNode();
            reader.accept(root,0);
            if(isFusionClass(root.visibleAnnotations)){
                classes.put(root.name,root);
            }
            List<Type> types = parserFusionTemplate(root.visibleAnnotations);
            if(types != null){
                Set<String> set = imps.get(root.name);
                if(set == null){
                    set = new TreeSet<String>();
                    imps.put(root.name,set);
                }
                for(Type type:types){
                    set.add(type.getInternalName());
                }
                classes.put(root.name,root);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isFusionClass(List<AnnotationNode> anns){
        if(anns != null){
            for(AnnotationNode ann:anns){
                if(ann.desc.equals("Lio/github/xausky/cfc/FusionClass;")){
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Type> parserFusionTemplate(List<AnnotationNode> anns){
        if(anns != null) {
            for (AnnotationNode ann : anns) {
                if (ann.desc.equals("Lio/github/xausky/cfc/FusionTemplate;")) {
                    return (List<Type>) ann.values.get(1);
                }
            }
        }
        return null;
    }
}
