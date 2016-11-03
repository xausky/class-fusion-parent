package io.github.xausky.cfmp.visitor;

import org.objectweb.asm.*;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xausky on 10/28/16.
 */
public class FusionClassVisitor extends ClassVisitor {
    private Set<String> anns = new TreeSet<String>();
    private Collection<String> imps;
    private String name;

    public FusionClassVisitor(ClassVisitor classVisitor, Collection<String> imps, String name) {
        super(Opcodes.ASM5, classVisitor);
        this.imps = imps;
        this.name = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //去掉构造函数
        if(name.equals("<init>")){
            return null;
        }
        return new FusionMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions),imps,this.name);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        //去掉合成引入的FusionImpl注解
        if(desc.equals("Lio/github/xausky/cfc/FusionImpl;")){
            return null;
        }
        //保证注解唯一
        if(!anns.add(desc)){
            return null;
        }
        return super.visitAnnotation(desc, visible);
    }
}
