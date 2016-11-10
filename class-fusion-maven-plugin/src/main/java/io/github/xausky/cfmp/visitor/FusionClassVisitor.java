package io.github.xausky.cfmp.visitor;

import org.objectweb.asm.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * 合成类的类修改器
 * Created by xausky on 10/28/16.
 */
public class FusionClassVisitor extends ClassVisitor {
    private Set<String> anns = new TreeSet<String>();
    private Set<String> interfaces = new TreeSet<>();
    private Collection<String> imps;
    private String superName;
    private String name;

    /**
     * 构造函数.
     * @param classVisitor 需要修改的ClassVisitor
     * @param imps 需要修改的类的接口的实现类
     * @param name 需要修改的类的目标类名
     */
    public FusionClassVisitor(ClassVisitor classVisitor,
                              Collection<String> imps,
                              String name) {
        super(Opcodes.ASM5, classVisitor);
        this.imps = imps;
        this.name = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc,
                                     String signature,
                                     String[] exceptions) {
        //去掉构造函数
        if(name.equals("<init>")){
            return null;
        }
        return new FusionMethodVisitor(super.visitMethod(access, name,
                desc, signature, exceptions),imps,this.name);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        //去掉合成引入的FusionImpl注解
        if(desc.equals("Lio/github/xausky/cfa/FusionImpl;")){
            return null;
        }
        //保证注解唯一
        if(!anns.add(desc)){
            return null;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        //合并interface,保存superName
        this.superName = superName;
        this.interfaces.addAll(Arrays.asList(interfaces));
        super.visit(version, access, this.name, signature, superName,
                (String[]) this.interfaces.toArray(new String[this.interfaces.size()]));
    }

    public String getSuperName() {
        return superName;
    }
}
