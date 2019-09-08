package com.baeldung.poi.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordDemo01Test {

    static WordDemo01 wordDemo01;

    @Test
    public void handleTable() throws Exception {
        // 生成word
        wordDemo01 = new WordDemo01();
        wordDemo01.handleTable();

        // 验证生成的数据
        Path msWordPath = Paths.get(WordDemo01.tableOutput);
        XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
        List<XWPFTable> tables = document.getTables();
        document.close();

        // 验证第1行第1列
        XWPFTable table= tables.get(0);
        assertEquals(table.getRow(0).getCell(0).getText(), "1");

        // 通过循环来验证所有单元格的数据
        List<WordDemo01.Student> students = wordDemo01.generateStudentList();
        Field[] fields = WordDemo01.Student.class.getDeclaredFields();
        for (int i = 0; i < 3; i++) {
            WordDemo01.Student student = students.get(i);
            for (int j = 0; j < 3; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                assertEquals(table.getRow(i).getCell(j).getText(), String.valueOf(field.get(student)));
            }
        }
    }
}