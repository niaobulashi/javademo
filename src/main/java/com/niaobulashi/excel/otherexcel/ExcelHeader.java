package com.niaobulashi.excel.otherexcel;

public class ExcelHeader implements Comparable<ExcelHeader> {
    /**
     * excel的标题名称
     */
    private String title;
    /**
     * 每一个标题的顺
     */
    private int order;
    /**
     * 变量名
     */
    private String filed;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }


    public int compareTo(ExcelHeader o) {
        return order - o.order;
    }

    public ExcelHeader(String title, int order, String filed) {
        super();
        this.title = title;
        this.order = order;
        this.filed = filed;

    }

}
