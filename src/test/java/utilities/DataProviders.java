package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	// DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException{
		String path = ".\\testData\\Opencart_LoginData.xlsx";  // taking excel file from the testData
		
		ExcelUtility xlutil = new ExcelUtility(path); // creating the object for EccelUtility class
		
		int totalrows = xlutil.getRowCount("Sheet1");  // 4
		int totalcolumns = xlutil.getCellCount("Sheet1",1); // 3
		
		String logindata[][] = new String[totalrows][totalcolumns];
		
		for(int i=1;i<=totalrows;i++) {
			for(int j=0;j<totalcolumns;j++) {
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
    return logindata;
		
	}
	
	// DataProvider 2
	
	// DataProvider 3
}
