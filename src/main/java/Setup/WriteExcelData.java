package Setup;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelData {
	

	public static void ListOutHospitals(List<String> BestListedHospitals)
	{
		try {
		String ExcelPath=System.getProperty("user.dir")+"\\Resources\\Excel\\";
		FileOutputStream output= new FileOutputStream(ExcelPath+"Hospitals.xlsx");
		
		XSSFWorkbook workbook= new XSSFWorkbook();
		XSSFSheet sheet= workbook.createSheet("BestHospitals");
		
		sheet.setColumnWidth(0, 255*100);
		XSSFFont font = workbook.createFont();
		  font.setBold(true);
		  
		 CellStyle style = workbook.createCellStyle();
	      style.setFont(font);
	   
		Row row = sheet.createRow(0);  
        Cell cell = row.createCell(0);
        cell.setCellValue("Best Hospitals that Opens 24/7 , Has Parking Facility and Rated Above 3.5 ");
        cell.setCellStyle(style);
        
		int startRow=1;
		for(String HospNames: BestListedHospitals )
		{
			sheet.createRow(startRow++).createCell(0).setCellValue(HospNames);
		}
		workbook.write(output);
		workbook.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
