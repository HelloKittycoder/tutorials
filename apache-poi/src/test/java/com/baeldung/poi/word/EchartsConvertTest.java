package com.baeldung.poi.word;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.baeldung.poi.word.EchartsUtil.generateStringFromFtl;
import static com.baeldung.poi.word.EchartsUtil.post;
import static com.baeldung.poi.word.EchartsUtil.writeImageToFile;

/**
 * Created by shucheng on 2019-9-12 下午 16:24
 */
public class EchartsConvertTest {

    public static String url = "http://localhost:9090";
    public static String outputPath = "aaa.png";

    // 简单测试（option非常简单时）
    // https://gitee.com/saintlee/echartsconvert/tree/master
    @Test
    public void testSimple() {
        // 不必要的空格最好删除，字符串请求过程中会将空格转码成+号
        String optJson = "{title:{text:'ECharts 示例'},tooltip:{},legend:{data:['销量']},"
                + "xAxis:{data:['衬衫','羊毛衫','雪纺衫','裤子','鞋子','袜子']},yAxis:{},"
                + "series:[{name:'销量',type:'bar',data:[5,20,36,10,10,20]}]}";
        // System.out.println(optJson);
        Map<String, String> params = new HashMap<>();
        params.put("opt", optJson);
        try {
            String post = post(url, params, "utf-8");
            // System.out.println(post);
            JSONObject jsonObject = JSON.parseObject(post);
            String imageDataStr = jsonObject.getString("data");
            InputStream inputStream = EchartsUtil.base64ToInputStream(imageDataStr);
            writeImageToFile(inputStream, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从文本中加载option（通常是测一些例子）
    @Test
    public void testFromTextFile() {
        // 不必要的空格最好删除，字符串请求过程中会将空格转码成+号
        String optJson = EchartsUtil.convertFileToString("echarts/optionData.txt");
        optJson = optJson.replaceAll("\\s", "");
        
        Map<String, String> params = new HashMap<>();
        params.put("opt", optJson);
        try {
            String post = post(url, params, "utf-8");
            // System.out.println(post);
            JSONObject jsonObject = JSON.parseObject(post);
            String imageDataStr = jsonObject.getString("data");
            InputStream inputStream = EchartsUtil.base64ToInputStream(imageDataStr);
            writeImageToFile(inputStream, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从模板文件中获取option（通常用于需要动态填充的数据）
    @Test
    public void testFromFtl() throws Exception {
        String option = prepareOption();
        // System.out.println(option);

        Map<String, String> params = new HashMap<>();
        params.put("opt", option);
        String post = post(url, params, "utf-8");
        // System.out.println(post);
        JSONObject jsonObject = JSON.parseObject(post);
        String imageDataStr = jsonObject.getString("data");
        InputStream inputStream = EchartsUtil.base64ToInputStream(imageDataStr);
        writeImageToFile(inputStream, outputPath);
    }

    private String prepareOption() {
        String option = "";
        Map<String, Object> map = new HashMap<>();
        String[] xAxisData = new String[]{"衬衫", "羊毛衫", "雪纺衫",
                "裤子", "鞋子","袜子"};
        map.put("xAxisData", JSON.toJSONString(xAxisData));
        int[] seriesData = new int[]{5, 20, 36, 10, 10, 20};
        map.put("seriesData", JSON.toJSONString(seriesData));
        try {
            option = generateStringFromFtl("echarts/option.ftl", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回的时候先把所有空白字符去掉
        return option.replaceAll("\\s", "");
    }
}
