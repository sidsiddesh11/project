package com.project.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {

    /**
     * Reads a sheet into Object[][].
     * - Skips the first row (assumed header)
     * - Uses DataFormatter so numbers/dates become strings safely
     */
    public static Object[][] getData(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(excelPath));
        try (XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            XSSFSheet sh = wb.getSheet(sheetName);
            int totalRows = sh.getPhysicalNumberOfRows();  
            int totalCols = sh.getRow(0).getLastCellNum(); 

            // Skip header (row 0)
            int dataRows = Math.max(0, totalRows - 1);
            Object[][] data = new Object[dataRows][totalCols];

            DataFormatter fmt = new DataFormatter();
            for (int r = 1; r < totalRows; r++) {
                for (int c = 0; c < totalCols; c++) {
                    String value = fmt.formatCellValue(sh.getRow(r).getCell(c));
                    data[r - 1][c] = value;
                }
            }
            return data;
        }
    }
}