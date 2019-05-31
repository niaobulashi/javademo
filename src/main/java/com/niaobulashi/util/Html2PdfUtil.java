package com.niaobulashi.util;

import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;

/**
 * @program: javademo
 * @description: html转pdf
 * @author: hulang
 * @create: 2019-05-31 10:54
 */
public class Html2PdfUtil {

    static final Logger logger = LoggerFactory.getLogger(Html2PdfUtil.class);

    // 本地静态Html生成PDF
    public static Boolean renderPDF(String html, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        try {
            String webAppRoot = System.getProperty("family.back.root");
            logger.info("webAppRoot:"+webAppRoot);
            PdfRendererBuilder builder = new PdfRendererBuilder();
            String path = webAppRoot +File.separator+ "family"+File.separator+"default"+File.separator+"styles"+File.separator+"Fonts";
            addFont(builder, path);
            builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
            builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
            builder.defaultTextDirection(PdfRendererBuilder.TextDirection.LTR);
            builder.useSVGDrawer(new BatikSVGDrawer());
            //   builder.useMathMLDrawer(new MathMLDrawer());
            //  builder.useObjectDrawerFactory(buildObjectDrawerFactory());
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            file.delete();
            return Boolean.FALSE;
        } finally {
            outputStream.close();
        }
    }


    /**
     * 添加字体库
     *
     * @param builder
     * @param dir
     */
    private static void addFont(PdfRendererBuilder builder, String dir) {
        File f = new File(dir);
        if (f.isDirectory()) {
            File[] files = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".ttf") || lower.endsWith(".ttc");
                }
            });
            for (File subFile : files) {
                String fontFamily = subFile.getName().substring(0, subFile.getName().lastIndexOf("."));
                builder.useFont(subFile, fontFamily);
            }
        }
    }
}

