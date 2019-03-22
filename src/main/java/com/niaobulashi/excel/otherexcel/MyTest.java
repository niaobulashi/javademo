package com.niaobulashi.excel.otherexcel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MyTest {
    //      读取EXCEL数据，循环EXCEL行及列
    public static void main(String[] args) {
//      读取EXCEL数据
        ArrayList<User> columnList = new ArrayList<User>();
//        File file = new File("E:\\网上信托披露文件待确定的数据.xls");
        File file = new File("E:\\家族信托项目-期间管理报告披露频率信息配置表_20190320 (4).xls");

        try {
            FileInputStream in = new FileInputStream(file);

//            EXCEL2003-2007版本用HSSF类
            HSSFWorkbook wb = new HSSFWorkbook(in);
            //EXCEL2007以上版本用XSSF类
//            XSSFWorkbook wb = new XSSFWorkbook(in);

//          获取某个sheet页
            Sheet sheet = wb.getSheetAt(0);
//            int firstRowNum = sheet.getFirstRowNum();
//            int lastRowNum = sheet.getLastRowNum();
            //开始行数
            int firstRowNum = 5;
            //结束行数
            int lastRowNum = 8;

            Row row = null;
            Cell cell_a = null;
            Cell cell_b = null;
            //循环行
            for (int i = firstRowNum; i <= lastRowNum; i++) {
                row = sheet.getRow(i);          //取得第i行
                User user = new User();
//                cell_a = row.getCell(1);        //取得i行的第n列
//                String cellValue = cell_a.getStringCellValue().trim();//取得i行的第n列的值
//                System.out.println(cellValue);

                System.out.println(row.getCell(0).getStringCellValue());
                user.setUserId(row.getCell(1).getStringCellValue()); //第i行第n列的值
                System.out.println(row.getCell(1).getStringCellValue());
                user.setDeptName(row.getCell(2).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                user.setCompanyId((int) row.getCell(3).getNumericCellValue());
                System.out.println((int) row.getCell(3).getNumericCellValue());
                //将行的值附给list
                columnList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
