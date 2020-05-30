package Setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {

	
	public static  Object[][] getCredentailsData(String ExcelName, String SheetName, String TestName ) throws IOException
	{
		String [][]dataSet=null;
		
		String ExcelPath= System.getProperty("user.dir")+"\\Resources\\Excel\\"+ExcelName;
		
		FileInputStream input= new FileInputStream(new File(ExcelPath));
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet sheet= workbook.getSheet(SheetName);
		
	
		int rownum=0;
		try {
		while(!sheet.getRow(rownum).getCell(0).getStringCellValue().equals(TestName))		
			rownum++;
		}
		catch(Exception e)
		{
			rownum++;
		}
		
		int noOfRows=1;
		int noOfColumns=2;
		dataSet= new String[noOfRows][noOfColumns];
	
		int x=0,y=0;
		for(int i=0;i<noOfRows;i++, x++)
		{
			rownum++;
			y=0;
			for(int j=0;j<noOfColumns;j++,y++)
			{
				dataSet[x][y]= sheet.getRow(rownum).getCell(j).getStringCellValue();
			}
		}
		return dataSet;
	}

}
