package com.baeldung.examples.asm.mydemo;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shucheng on 2019-9-22 上午 10:13
 */
public class AddMethodVisitor extends AdviceAdapter {

    private boolean addAnnotation = false;

    protected AddMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(ASM5, mv, access, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (visible && desc.equals("Lcom/baeldung/examples/asm/mydemo/Add;")) {
            addAnnotation = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!addAnnotation) {
            super.visitEnd();
            return;
        }
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitVarInsn(Opcodes.ILOAD, 2);
        mv.visitInsn(Opcodes.IADD);
        int newLocal = newLocal(Type.INT_TYPE);
        mv.visitVarInsn(Opcodes.ISTORE, newLocal);
        mv.visitVarInsn(Opcodes.ILOAD, newLocal);
        mv.visitInsn(Opcodes.IRETURN);
    }
}
