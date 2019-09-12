package com.baeldung.poi.word;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shucheng on 2019-9-12 下午 15:35
 * Word写入图片操作
 */
public class WordDemo02 {

    public static String imageOutput = "imageTest.docx";
    public static String imageDataPath = "echartsimage.txt";

    // 生成图片（这里拿echarts中得到的base64数据来生成img，base64数据存放在echartsimage.txt中）
    public void handleImage() throws Exception {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        // 原始大小是888*350（宽*高），放不下
        imageRun.addPicture(getImageInputStream(imageDataPath), XWPFDocument.PICTURE_TYPE_PNG,
                "test.png", Units.toEMU(444), Units.toEMU(175));

        FileOutputStream out = new FileOutputStream(imageOutput);
        document.write(out);
        out.close();
        document.close();
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

    // 测试将图片写到文件中
    public static void main(String[] args) throws Exception {
        InputStream inputStream = getImageInputStream(imageDataPath);

        /*BufferedImage bi = ImageIO.read(inputStream);

        File file = new File("aaa.png");
        ImageIO.write(bi, "png", file);
        inputStream.close();*/

        FileOutputStream fos = new FileOutputStream("aaa.png");
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            fos.write(b, 0, length);
        }
        inputStream.close();
        fos.close();
    }

    /**
     * 将图片数据转换成ImageInputStream
     * @param imageDataPath
     * @return
     */
    public static InputStream getImageInputStream(String imageDataPath) throws Exception {
        Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(imageDataPath).toURI()));
        String imageDataStr = lines.collect(Collectors.joining());
        // https://blog.csdn.net/tjj3027/article/details/80421170
        String[] arr = imageDataStr.split("base64,");
        return base64ToInputStream(arr[1]);
    }
}
