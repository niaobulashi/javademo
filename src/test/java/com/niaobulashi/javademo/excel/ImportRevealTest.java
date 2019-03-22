package com.niaobulashi.javademo.excel;

import com.niaobulashi.excel.ExcelContext;
import com.niaobulashi.excel.result.ExcelImportResult;
import com.niaobulashi.javademo.excel.model.revealReportModel;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * Excel导入测试
 * @author lisuo
 *
 */
public class ImportRevealTest {
	
	// 测试时文件磁盘路径
	private static String path = "家族信托项目-期间管理报告披露频率信息配置表_20190321.xls";
	// 配置文件路径
	private static ExcelContext revealContext = new ExcelContext("raveal-config.xml");
	// Excel配置文件中配置的id
	private static String revealExcelId = "reveal";

	/**
	 * 导入Excel,使用了org.easy.excel.test.ExportTest.testExportCustomHeader()方法生成的Excel
	 * @throws Exception
	 */
	@Test
	public void testRevealImport()throws Exception{
		ClassPathResource resource = new ClassPathResource(path);
		//第二个参数需要注意,它是指标题索引的位置,可能你的前几行并不是标题,而是其他信息,
		//比如数据批次号之类的,关于如何转换成javaBean,具体参考配置信息描述
		ExcelImportResult result = revealContext.readExcel(revealExcelId, 3, resource.getInputStream(), 0);
//		System.out.println(result.getHeader());
		List<revealReportModel> stus = result.getListBean();
		for(revealReportModel stu:stus){
			System.out.println(stu);
		}
		resource.getInputStream().close();
		//这种方式和上面的没有任何区别,底层方法默认标题索引为0
		//context.readExcel(excelId, fis);
	}

}
