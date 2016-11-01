package io.github.xausky.cfmp;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xausky on 11/1/16.
 */
public class ClassFusion {
    public static void fusion(ClassWriter writer, String name, Set<String> imps, Map<String,ClassNode> classes){
        FusionClassVisitor visitor = new FusionClassVisitor(writer, imps, name);
        Set<String> interfaces = new TreeSet<String>();
        for(String imp:imps){
            ClassNode root = classes.get(imp);
            if(root!=null){
                interfaces.addAll(root.interfaces);
                root.accept(visitor);
            }
        }
        ClassNode root = classes.get(name);
        if(root!=null){
            root.accept(visitor);
        }
        //修改定义，添加接口
        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", (String[]) interfaces.toArray(new String[interfaces.size()]));
        //添加空构造函数
        MethodVisitor initMethod = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        initMethod.visitCode();
        initMethod.visitVarInsn(Opcodes.ALOAD, 0);
        initMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        initMethod.visitInsn(Opcodes.RETURN);
        initMethod.visitMaxs(1, 1);
        initMethod.visitEnd();
        writer.visitEnd();
    }
}
