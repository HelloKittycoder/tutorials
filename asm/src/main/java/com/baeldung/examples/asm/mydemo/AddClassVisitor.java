package com.baeldung.examples.asm.mydemo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * Created by shucheng on 2019-9-22 上午 10:06
 */
public class AddClassVisitor extends ClassVisitor {

    public AddClassVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature,
                                     String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!name.equals("<init>")) {
            return new AddMethodVisitor(mv, access, name, desc);
        }
        return mv;
    }
}
