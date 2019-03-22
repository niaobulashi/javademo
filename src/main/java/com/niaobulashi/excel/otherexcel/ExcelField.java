package com.niaobulashi.excel.otherexcel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 属性的标题名称
     */
    String title();

    /**
     * 在excel的顺
     */
    int colum() default 9999;

    Class claz();

    int maxLength() ;

}