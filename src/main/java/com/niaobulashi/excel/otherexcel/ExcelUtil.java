package com.niaobulashi.excel.otherexcel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            if(values[i]!=null){
                for(int j=0;j<values[i].length;j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }else{
                continue;
            }

        }
        return wb;
    }


    /**
     * 将流中的Excel数据转成List<Map>(读取Excel)
     *
     * @param in
     *            输入流
     * @param fileName
     *            文件名（判断Excel版本）
     * @return
     * @throws Exception
     */
    public static List<Map<String,Object>> readExcel(InputStream in, String fileName) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<Map<String,Object>> resultList=new ArrayList<>();
        //循环多个工作表
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            Map<String,Object> result=new HashMap<>();
            sheet = work.getSheetAt(i);
            if (sheet == null)
                continue;
            //获取有合并单元格的区域
            List<CellRangeAddress> combineCellList=getCombineCellList(sheet);
            // 测试有几行数据是有表头数据的
            Boolean flag=true;
            int h=0;
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                // 遍历所有的列
                if(flag) {
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        Object v = getCellValue(cell);
                        flag = false;
                        if (v != null && !v.toString().equals("")) {
                            flag = true;
                            break;
                        }
                    }
                    h++;
                }
            }
            //列数
            int colNum=0;
            //表头行数
            int h1=h-1;
            //表头数据
            List<List> list1=new ArrayList<>();
            //循环行，不要从0开始，防止前几行为空的情况
            for (int k = sheet.getFirstRowNum(); k < sheet.getFirstRowNum()+h1; k++) {
                List<Map<String,Object>> list=new ArrayList<>();
                row = sheet.getRow(k);
                //获取列数
                colNum=row.getLastCellNum()-row.getFirstCellNum();
                // 遍历所有的列
                for (int x = row.getFirstCellNum(); x < row.getLastCellNum(); x++) {
                    Map<String,Object> map=new HashMap<>();
                    cell = row.getCell(x);
                    //表格cell的数据，合并的只有左上角一个有数据，其余全为空
                    String v = getCellValue(cell).toString();
                    //判断该cell是否为合同单元格中的一个
                    Map<String,Object> isCombined=isCombineCell(combineCellList,cell,sheet);
                    //如果是，则判断是否有值，有值的才添加到list中
                    if((Boolean) isCombined.get("flag")){
                        if(v!=null&&!v.equals("")){
                            map.put("name",v);
                            map.put("x",isCombined.get("mergedCol"));
                            map.put("y",isCombined.get("mergedRow"));
                            list.add(map);
                        }
                        //如果不是，则直接插入
                    }else{
                        map.put("name",v);
                        map.put("x",1);
                        map.put("y",1);
                        list.add(map);
                    }
                }
                list1.add(list);
            }

            int rowIndex=1;
            //处理数据拼接字符串
            for(int c=0;c<list1.size();c++){
                String s="";
                for(int d=0;d<list1.get(c).size();d++){
                    Map<String,Object> map2= (Map<String, Object>) list1.get(c).get(d);
                    s+=map2.get("name")+","+map2.get("x")+","+map2.get("y")+";";
                }
                result.put("row"+rowIndex++,s);
            }

            System.out.println(result);
            resultList.add(result);
        }

        //work.close();
        return resultList;
    }

    //获取合并单元格集合
    public static List<CellRangeAddress> getCombineCellList(Sheet sheet)
    {
        List<CellRangeAddress> list = new ArrayList<>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for(int i = 0; i<sheetmergerCount;i++)
        {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    /**
     * 判断cell是否为合并单元格，是的话返回合并行数和列数（只要在合并区域中的cell就会返回合同行列数，但只有左上角第一个有数据）
     * @param listCombineCell  上面获取的合并区域列表
     * @param cell
     * @param sheet
     * @return
     * @throws Exception
     */
    public  static Map<String,Object> isCombineCell(List<CellRangeAddress> listCombineCell,Cell cell,Sheet sheet)
            throws Exception{
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        Boolean flag=false;
        int mergedRow=0;
        int mergedCol=0;
        Map<String,Object> result=new HashMap<>();
        result.put("flag",flag);
        for(CellRangeAddress ca:listCombineCell)
        {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            //判断cell是否在合并区域之内，在的话返回true和合并行列数
            if(cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR)
            {
                if(cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC)
                {
                    flag=true;
                    mergedRow=lastR-firstR+1;
                    mergedCol=lastC-firstC+1;
                    result.put("flag",true);
                    result.put("mergedRow",mergedRow);
                    result.put("mergedCol",mergedCol);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr
     *            ,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字
        if(cell!=null){
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        value = df.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        value = df2.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value="";
                    break;
                default:
                    break;
            }
        }else{
            value="";
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("E:\\网上信托披露文件待确定的数据.xls");
        File file = new File("C:\\Users\\niaobulashi\\Desktop\\temp\\草稿\\excel\\test.xls");
        FileInputStream fis = new FileInputStream(file);
//        List<Map<String, Object>> ls = readExcel(fis, file.getName());
        readExcel(fis, file.getName());
        //System.out.println(JSON.toJSONString(ls));
    }





}