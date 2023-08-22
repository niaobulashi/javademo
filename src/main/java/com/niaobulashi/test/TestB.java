package com.niaobulashi.test;

public class TestB {

    public static void main(String[] args) {
        int param = 10;

        try {
            // 修改参数的值
            param += 5;
            // 执行一些其他操作
            // ...
        } catch (Exception e) {
            // 处理异常
            // ...
        } finally {
            // 在finally块中访问参数的最终值
            System.out.println("参数的最终值: " + param);
        }
    }
}
