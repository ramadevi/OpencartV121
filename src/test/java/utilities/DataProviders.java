package utilities;

import java.io.IOException;

public class DataProviders {
 
	//DataProviders 1 
	public String [][] getData() throws IOException{
		String path = "./testData/Opencart-testData.xlsx"; // getting excell file form testdata 
		
		ExcelUtility xlutil = new ExcelUtility(path); // creating object for xl utility 
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][] = new String [totalrows][totalcols]; // creating two dimentional array 
		
		for (int i=1; i<=totalrows;i++) {
			for (int j=0; j<totalcols; j++) {
				logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);
			}
		}
		return logindata;
		
	}
}
