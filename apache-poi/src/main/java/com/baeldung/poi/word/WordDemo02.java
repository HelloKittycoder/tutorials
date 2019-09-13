package com.baeldung.poi.word;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;

/**
 * Created by shucheng on 2019-9-12 下午 15:35
 * Word写入图片操作
 */
public class WordDemo02 {

    public static String imageOutput = "imageTest.docx";
    public static String imageDataPath = "echarts/echartsimage.txt";

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
        String imageDataStr = EchartsUtil.convertFileToString(imageDataPath);
        // https://blog.csdn.net/tjj3027/article/details/80421170
        String[] arr = imageDataStr.split("base64,");
        return EchartsUtil.base64ToInputStream(arr[1]);
    }
}
