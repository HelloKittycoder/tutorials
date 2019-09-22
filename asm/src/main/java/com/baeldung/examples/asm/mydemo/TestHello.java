package com.baeldung.examples.asm.mydemo;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

/**
 * Created by shucheng on 2019-9-22 上午 10:34
 * asm基础用法
 * https://blog.csdn.net/sweatOtt/article/details/88114002
 */
public class TestHello {

    public static void main(String[] args)
            throws IOException {
        ClassReader classReader = new ClassReader("com/baeldung/examples/asm/mydemo/Hello");
        ClassWriter classWriter = new ClassWriter(COMPUTE_MAXS);
        AddClassVisitor metricClassVisitor = new AddClassVisitor(classWriter);
        classReader.accept(metricClassVisitor, ClassReader.SKIP_FRAMES);
        byte[] bytes = classWriter.toByteArray();
        File file = new File("Hello.class");
        file.createNewFile();
        FileUtils.writeByteArrayToFile(file, bytes);
    }
}
