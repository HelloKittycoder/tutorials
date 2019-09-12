package com.baeldung.poi.word;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shucheng on 2019-9-12 下午 16:07
 */
public class EchartsUtil {

    // post请求
    public static String post(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }

    /** 将图片写入文件
     * 调用方法：
     * writeImageToFile(inputStream, "png", new File("aaa.png"));
     * @param inputStream 图片文件输入流
     * @param imageFormat 图片格式
     * @param outputFile 输出文件
     * @throws Exception
     */
    public static void writeImageToFile(InputStream inputStream, String imageFormat, File outputFile) throws Exception {
        BufferedImage bi = ImageIO.read(inputStream);
        ImageIO.write(bi, imageFormat, outputFile);
        inputStream.close();
    }

    /**
     * 将图片写入文件（这种方法比ImageIO写入文件的文件大小更大，原因未知，有待探索）
     * 调用方法：
     * writeImageToFile(intputStream, "aaa.png");
     * @param inputStream 图片文件输入流
     * @param filePath 图片输出路径（可以是绝对路径；也可以是相对路径，如：aaa.png）
     * @throws Exception
     */
    public static void writeImageToFile(InputStream inputStream, String filePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            fos.write(b, 0, length);
        }
        inputStream.close();
        fos.close();
    }

    // 将base64转换为InputStream（https://blog.csdn.net/weixin_40467684/article/details/91872973）
    public static InputStream base64ToInputStream(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = new byte[1024];
        try {
            bytes = decoder.decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(bytes);
    }
}
