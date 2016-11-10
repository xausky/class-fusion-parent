package io.github.xausky.cfmp.utils;

import io.github.xausky.cfmp.visitor.FusionClassVisitor;
import io.github.xausky.cfmp.execption.FieldNameConflictException;
import io.github.xausky.cfmp.execption.MethodNameConflictException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

/**
 * 合成目标类的工具类
 * Created by xausky on 11/1/16.
 */
public class ClassFusion {
    /**
     * 应用实现类到目标类
     * @param writer 目标类Writer
     * @param name 目标类名
     * @param itfs 目标类实现接口
     * @param imps 接口与实现类的映射
     * @param classes 相关类缓存
     * @throws MethodNameConflictException 方法重复冲突异常
     * @throws FieldNameConflictException 属性重复冲突异常
     * @throws ClassNotFoundException 找不到实现类异常
     */
    public static void fusion(ClassWriter writer,
                              String name,
                              Set<String> itfs,
                              Map<String, String> imps,
                              Map<String,ClassNode> classes)
            throws MethodNameConflictException,
            FieldNameConflictException,
            ClassNotFoundException {
        //impls必须保证有序
        List<String> impls = new LinkedList<>();

        Set<String> methods = new HashSet<>();
        Set<String> fields = new HashSet<>();
        for(String itf:itfs){
            String imp = imps.get(itf);
            if(imp != null){
                impls.add(imp);
            }
        }
        impls.add(name);
        FusionClassVisitor visitor = new FusionClassVisitor(writer, impls, name);
        for(String imp:impls){
            ClassNode root = classes.get(imp);
            if(root!=null){
                //检查同名函数
                for(MethodNode method:(List<MethodNode>)root.methods){
                    if(!method.name.equals("<init>")){
                        if(!methods.add(method.name)){
                            throw new MethodNameConflictException(String.format(
                                    "method name conflict class: %s," +
                                            " method name:%s please check and clean maven project.",
                                    imp,method.name));
                        }
                    }
                }
                //检查同名属性
                for(FieldNode field:(List<FieldNode>)root.fields){
                    if(!fields.add(field.name)){
                        throw new FieldNameConflictException(String.format(
                                "field name conflict class: %s," +
                                        " field name:%s please check and clean maven project.",
                                imp,field.name));
                    }
                }
                root.accept(visitor);
            }else {
                throw new ClassNotFoundException("fusion class not found:"+imp);
            }
        }
        /*修改定义，添加接口
        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                name, null, "java/lang/Object",
                (String[]) itfs.toArray(new String[itfs.size()]));*/
        //添加空构造函数
        MethodVisitor initMethod = writer.visitMethod(Opcodes.ACC_PUBLIC,
                "<init>", "()V", null, null);
        initMethod.visitCode();
        initMethod.visitVarInsn(Opcodes.ALOAD, 0);
        initMethod.visitMethodInsn(Opcodes.INVOKESPECIAL,
                visitor.getSuperName(), "<init>", "()V", false);
        initMethod.visitInsn(Opcodes.RETURN);
        initMethod.visitMaxs(1, 1);
        initMethod.visitEnd();
        writer.visitEnd();
    }
}
