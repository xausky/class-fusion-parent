package io.github.xausky.cfmp.visitor;


import org.objectweb.asm.*;

import java.util.*;

/**
 * 合成类的方法修改器
 * Created by xausky on 10/28/16.
 */
public class FusionMethodVisitor extends MethodVisitor {
    private Collection<String> imps;
    private String name;

    /**
     * 构造函数.
     * @param methodVisitor 要修改的methodVisitor
     * @param imps 合成类需要实现的实现类集合
     * @param name 合成类的目标类名
     */
    public FusionMethodVisitor(MethodVisitor methodVisitor, Collection<String> imps, String name) {
        super(Opcodes.ASM5, methodVisitor);
        this.imps = imps;
        this.name = name;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        //修改函数调用的目标为新类
        if(imps.contains(owner)){
            owner = this.name;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        //修改属性调用的目标为新类
        if(imps.contains(owner)){
            owner = this.name;
        }
        super.visitFieldInsn(opcode,owner,name,desc);
    }



}
