package com.baeldung.poi.word;

import org.junit.Test;

import java.io.InputStream;

/**
 * Created by shucheng on 2019-9-12 下午 15:13
 */
public class WordDemo02Test {
    static WordDemo02 wordDemo02;

    @Test
    public void testHandleImage() throws Exception {
        wordDemo02 = new WordDemo02();
        wordDemo02.handleImage();
    }

    @Test
    public void testWriteImageToFile() throws Exception {
        InputStream inputStream = WordDemo02.getImageInputStream(wordDemo02.imageDataPath);
        EchartsUtil.writeImageToFile(inputStream, "aaa.png");
        // EchartsUtil.writeImageToFile(inputStream, "png", new File("aaa.png"));
    }
}
