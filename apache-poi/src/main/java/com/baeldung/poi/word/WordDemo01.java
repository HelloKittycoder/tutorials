package com.baeldung.poi.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng on 2019-9-8 下午 20:57
 * Word操作01
 */
public class WordDemo01 {

    public static String tableOutput = "tableTest.docx";

    // 表格处理
    public void handleTable() throws Exception {
        XWPFDocument document = new XWPFDocument();

        List<Student> list = generateStudentList();

        Field[] fields = Student.class.getDeclaredFields();
        XWPFTable table = document.createTable(3, 3);
        for (int i = 0; i < 3; i++) {
            Student student = list.get(i);
            for (int j = 0; j < 3; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                if (field.get(student) != null) {
                    table.getRow(i).getCell(j).setText(String.valueOf(field.get(student)));
                }
            }
        }

        FileOutputStream out = new FileOutputStream(tableOutput);
        document.write(out);
        out.close();
        document.close();
    }

    public List<Student> generateStudentList() {
        List<Student> list = new ArrayList<>();
        Student s = new Student(1, "张三", 10);
        list.add(s);
        s = new Student(2, "李四", 20);
        list.add(s);
        s = new Student(3, "王五", 30);
        list.add(s);
        return list;
    }

    public static class Student {
        private Integer id;
        private String name;
        private Integer age;

        public Student() {
        }

        public Student(Integer id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
