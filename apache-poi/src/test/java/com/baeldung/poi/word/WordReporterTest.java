package com.baeldung.poi.word;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shucheng on 2019-9-8 下午 21:36
 */
public class WordReporterTest {

    public static String templatePath = "booking_delivery.docx";
    public static String outputPath = "booking_delivery_result.doc"; // 写doc和docx都可以

    @Test
    public void testFindTemplate() throws Exception {
        /* 这种写法只会到根目录下找，当前子模块的根目录为d:/tutorials/apache-poi
        打印出来的路径就是d:/tutorials/apache-poi/booking_delivery.docx
        File f = new File(templatePath);
        System.out.println(f.getAbsolutePath());*/
        // 这种写法会到编译后的资源目录里去找
        // 找到的是d:/tutorials/apache-poi/target/classes/booking_delivery.docx
        File f = new File(ClassLoader.getSystemResource(templatePath).toURI());
        System.out.println(f.getAbsolutePath());
    }

    @Test
    public void test() throws Exception{
        File f = new File(ClassLoader.getSystemResource(templatePath).toURI());
        WordReporter exporter = new WordReporter(f.getAbsolutePath());
        exporter.init();
        Map<String,Object> params1 = new HashMap<String,Object>();
        params1.put("myTable", "怡美假日旅行社");
        params1.put("username", "张三");
        params1.put("Title", "昆大丽5天4晚休闲游");
        Map<String,String> picMap = new HashMap<String,String>();
        picMap.put("width", "416");//经测试416可以占一行
        picMap.put("height", "120");
        picMap.put("type", "jpg");
        // picMap.put("path", "http://192.168.1.81/p2/group1/M00/00/02/wKgBWVWBJF2AfcrKAAJnyycNFME158.jpg");
        picMap.put("path", "http://pics.sc.chinaz.com/Files/pic/icons128/5658/16.png");
        params1.put("logo", picMap);

        exporter.export(params1);

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> m1 = new HashMap<String,String>();
        m1.put("name", "李四1");
        m1.put("age", "20");
        m1.put("sex", "男");
        m1.put("job", "攻城狮1");
        m1.put("hobby", "篮球1");
        m1.put("phone", "1231231");
        list.add(m1);
        Map<String,String> m2 = new HashMap<String,String>();
        m2.put("name", "李四2");
        m2.put("age", "22");
        m2.put("sex", "女");
        m2.put("job", "攻城狮2");
        m2.put("hobby", "篮球2");
        m2.put("phone", "1231232");
        list.add(m2);
        Map<String,String> m3 = new HashMap<String,String>();
        m3.put("name", "李四3");
        m3.put("age", "23");
        m3.put("sex", "男3");
        m3.put("job", "攻城狮3");
        m3.put("hobby", "篮球3");
        m3.put("phone", "1231233");
        list.add(m3);
        exporter.export(list,0);
        Map<String,Object> m4 = new HashMap<String,Object>();
        m4.put("name", "李四4");
        m4.put("age", "24");
        m4.put("sex", "男4");
        Map<String,String> headMap = new HashMap<String,String>();
        headMap.put("width", "170");
        headMap.put("height", "170");
        // headMap.put("type", "jpg");
        headMap.put("type", "png");
        // headMap.put("path", "http://192.168.1.81/p2/group1/M00/00/02/wKgBWVWBJF2AfcrKAAJnyycNFME158.jpg");
        headMap.put("path", "http://pics.sc.chinaz.com/Files/pic/icons128/5658/16.png");
        m4.put("head", headMap);
        exporter.export(m4, 2);

        // exporter.exportImg(m4);
        // exporter.generate("e:\\"+System.currentTimeMillis()+".doc");
        exporter.generate(outputPath);

		/*String text = "test"+"\n"+"test\n";

		String[] arr = text.split("\n");
        for(String str : arr){
            System.out.println(str);
        }*/

    }
}
