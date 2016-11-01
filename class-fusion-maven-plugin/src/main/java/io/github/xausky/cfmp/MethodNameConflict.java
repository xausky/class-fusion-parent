package io.github.xausky.cfmp;

import org.objectweb.asm.tree.MethodNode;

/**
 * Created by xausky on 11/1/16.
 */
public class MethodNameConflict extends Exception {
    public MethodNameConflict(String message){
        super(message);
    }
}
