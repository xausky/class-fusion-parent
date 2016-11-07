package io.github.xausky.cfmp.execption;

import org.objectweb.asm.tree.MethodNode;

/**
 * Created by xausky on 11/1/16.
 */
public class MethodNameConflictException extends Exception {
    public MethodNameConflictException(String message) {
        super(message);
    }
}
