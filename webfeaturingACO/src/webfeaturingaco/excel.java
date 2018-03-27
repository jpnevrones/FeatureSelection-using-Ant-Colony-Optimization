/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package webfeaturingaco;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileOutputStream;

/**
 *
 * @author JP
 */
public class excel {
    

 
    public void  generateExcel( String excelFilename){
 
        try {
 

            //New Workbook
            Workbook wb = new XSSFWorkbook();
 
            Cell c = null;
 
            //Cell style for header row
            CellStyle cs = wb.createCellStyle();
            cs.setFillForegroundColor(IndexedColors.LIME.getIndex());
            cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            Font f = wb.createFont();
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);
            f.setFontHeightInPoints((short) 12);
            cs.setFont(f);
 
            //New Sheet
            Sheet sheet1 = null;
            sheet1 = wb.createSheet("myData");
 
 

 
            // Row and column indexes
            int idx = 0;
            int idy = 0;
 
            // Generate column headings
            Row row = sheet1.createRow(idx);
            
 
           
             
                c = row.createCell(idy);
                c.setCellValue("jithin");
                
               
             
 

 
            }
            catch(Exception ex){}    
 
 
        }
 
    }
 
 
  

