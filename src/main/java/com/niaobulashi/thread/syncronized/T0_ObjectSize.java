package com.niaobulashi.thread.syncronized;

import org.openjdk.jol.info.ClassLayout;

/**
 * @className: T0_ObjectSize
 * @description: TODO 类描述
 * @author: HuLang
 * @date: 2023/11/14
 **/
public class T0_ObjectSize {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
