package com.niaobulashi.doc.pdf;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * @ClassName: QueryKeywordPositionUtil
 * @Description: 查找关键字位置坐标工具类
 * @Author: HuLang
 * @Date: 2023/8/22
 */
public class PdfTest {
    private static final Logger logger = LoggerFactory.getLogger(PdfTest.class);
    public static void main(String[] args) {
        //初始化文件地址，或者其他位置的文件模板位置
        String initPdf = "C:\\Users\\ladyh\\Downloads\\2-恒字财富传承信托财顾合同（资金+保险金）-0530法改-长安_sign.pdf";
        logger.info("初始化之后的pdf地址：" + initPdf);
        //关键字
        String keyword = "委托人22";
        //调用工具类，查找关键字的位置信息
        List<float[]> floats = QueryKeywordPositionUtil.queryKeywordPosition(initPdf, keyword);
        logger.info("关键字位置--" + JSON.toJSONString(floats));
    }
}
