package webfeaturingaco;

import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.IndexedColors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExcelSheetWriter1 {

    static Connection ACOfeature_selection;
    static PreparedStatement query, query1, query2, query3, query4, query8, query9, query10, query11, query14, query13, query19, query21, query15, query18,query22,query24;
    static ResultSet query_rs, query_rs3, query_rs4, query_rs8, query_rs10, query_rs11, query_rs14, query_rs13, query_rs19, query_rs18,query_rs22;
    static int query_rs1, wpage_counter, query_rs2, fworddup_counter, query_rs9, query_rs21,query_rs15,query_rs24;

    public ExcelSheetWriter1() {
        ACOfeature_selection = null;
        query = null;
        query_rs = null;
        query1 = null;
        query2 = null;
        wpage_counter = 1;
        fworddup_counter = 0;

    }

    private void writeExcelFile(String fileName) {
        FileOutputStream fileOutputStream = null;
        HSSFWorkbook sampleWorkbook = null;
        HSSFSheet sampleDataSheet = null;

        //Gathering and processing the training data set
        try{
        String url1="jdbc:mysql://localhost:3306/ACO1" ;
        Class.forName("com.mysql.jdbc.Driver").newInstance(); 
        ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
        
        query = ACOfeature_selection.prepareStatement("select * from webpagestested where training=0");
        query_rs=query.executeQuery();
        while(query_rs.next()){
        String t_name=query_rs.getString(3);
        String wpage_name=query_rs.getString(2);
        try{	
        query1 = ACOfeature_selection.prepareStatement("update webpagestested set training = 1 where tablename='"+t_name+"'");
        query_rs1=query1.executeUpdate();
        }
        
        catch(Exception ex){}
        try{
        query4 = ACOfeature_selection.prepareStatement("select MAX(iteration) from ofeature_"+t_name);
        query_rs4=query4.executeQuery();
        String rs_iteration=null;
        while(query_rs4.next()){
        rs_iteration=query_rs4.getString(1);	 
        }
        try{
        
        query3 = ACOfeature_selection.prepareStatement("select feature from ofeature_"+t_name+" where iteration="+rs_iteration);
        query_rs3=query3.executeQuery();			 
        while(query_rs3.next()){	
        try{
        query2 = ACOfeature_selection.prepareStatement("insert into trainingdatset_dup values( '"+fworddup_counter+"','"+query_rs3.getString(1)+"')");
        query_rs2=query2.executeUpdate();
        fworddup_counter++;
        }
        catch(Exception ex){}
        
        }
        
        }
        catch(Exception ex){}
        
        }
        catch(Exception ex){}
        }
        try{
        
        query8 = ACOfeature_selection.prepareStatement("select DISTINCT(feature) from trainingdatset_dup order by feature");
        query_rs8=query8.executeQuery();			 
        while(query_rs8.next())
        {	
            try{
            query9 = ACOfeature_selection.prepareStatement("insert into trainingdataset values( '"+query_rs8.getString(1)+"','0')");
            query_rs9=query9.executeUpdate();
        
            }
            catch(Exception ex){}
        
        }
        
        }
        catch(Exception ex){}
        
     
        
        }
        catch(Exception ex){}
        
        
        
        
         
        //excel writer!!!!!
        try
        {
            sampleWorkbook = new HSSFWorkbook();
            sampleDataSheet = sampleWorkbook.createSheet("Training_Sheet1");

            HSSFRow headerRow = sampleDataSheet.createRow(0);
            HSSFCellStyle cellStyle = setHeaderStyle(sampleWorkbook);
            
            HSSFCell firstHeaderCell = headerRow.createCell(0);
            firstHeaderCell.setCellStyle(cellStyle);
            firstHeaderCell.setCellValue(new HSSFRichTextString("No"));
            
            HSSFCell secondHeaderCell = headerRow.createCell(1);
            secondHeaderCell.setCellStyle(cellStyle);
            secondHeaderCell.setCellValue(new HSSFRichTextString("URL"));
            int count = 0;
            
            try
            {
                int feature_no;
                String url1 = "jdbc:mysql://localhost:3306/ACO1";
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                ACOfeature_selection = DriverManager.getConnection(url1, "root", "root");
                System.out.println("hkjhjkhkjnkljkl");
                query10 = ACOfeature_selection.prepareStatement("select count(*) from trainingdataset ");
                query_rs10 = query10.executeQuery();
                while (query_rs10.next())
                {
                    count = query_rs10.getInt(1);
                }
                
                try
                {
                    int fcount = 2;
                    query11 = ACOfeature_selection.prepareStatement("select feature from trainingdataset ");
                    query_rs11 = query11.executeQuery();
                    while (query_rs11.next())
                    {

                        HSSFCell feature_name = headerRow.createCell(fcount);
                        feature_name.setCellStyle(cellStyle);
                        feature_name.setCellValue(new HSSFRichTextString(query_rs11.getString(1)));
                        fcount++;
                    }
                    HSSFCell class_header = headerRow.createCell(fcount);
                    class_header.setCellStyle(cellStyle);
                    class_header.setCellValue(new HSSFRichTextString("class"));
                    
                    

                }
                catch(Exception ex){}
                
                try
                {
                    query18 = ACOfeature_selection.prepareStatement("select * from trainingdataset");
                    query_rs18=query18.executeQuery();
                    feature_no=2;
                    while(query_rs18.next())
                    {
                            String up_feature=query_rs18.getString(1);
                            try
                            {
                            query15 = ACOfeature_selection.prepareStatement("update trainingdataset set no ='"+feature_no+"' where feature='"+up_feature+"'");
                            query_rs15=query15.executeUpdate();
                            feature_no++;
                            //System.out.println(query_rs18.getString(1));
                            }
                            catch(Exception ex){}
                       
                        
                    }
                    
                }  
                catch(Exception ex){}          
                
                try
                {
                                        
                    query19 = ACOfeature_selection.prepareStatement("select * from webpagestested where training=1");
                    query_rs19 = query19.executeQuery();
                    while (query_rs19.next()) 
                    {
                        String t_name = query_rs19.getString(3);
                        System.out.println(t_name);
                        String wpage_name=query_rs19.getString(2);
                        String category = query_rs19.getString(1);
                        
                        HSSFRow dataRow1 = sampleDataSheet.createRow(wpage_counter); System.out.println(t_name+"3");
                        dataRow1.createCell(0).setCellValue(wpage_counter);
                        dataRow1.createCell(1).setCellValue(wpage_name);System.out.println(count);
                        try{
                        for(int j=2;j<(count+2);j++)
                        {
                         System.out.println(j);
                            dataRow1.createCell(j).setCellValue(0);
                        }
                        dataRow1.createCell(count + 2).setCellValue(category);
                        wpage_counter++;
                        }
                          catch(Exception ex){  System.out.println(ex);}
                         System.out.println(t_name);
                        
                        try
                        {
                            query14 = ACOfeature_selection.prepareStatement("select MAX(iteration) from ofeature_" + t_name);
                            query_rs14 = query14.executeQuery();
                            String rs_iteration = null;
                            while (query_rs14.next()) {
                                rs_iteration = query_rs14.getString(1);
                                System.out.println( rs_iteration);
                            }

                            try
                            {
                                
                               query13 = ACOfeature_selection.prepareStatement("select feature,nrweight from ofeature_" + t_name + " where iteration=" + rs_iteration);
                               query_rs13 = query13.executeQuery();

                                while (query_rs13.next())
                                {
                                    System.out.println(query_rs13.getString(1));
                                    try
                                    {
                                        query22 = ACOfeature_selection.prepareStatement("select * from trainingdataset");
                                        query_rs22=query22.executeQuery();
                                        while(query_rs22.next())
                                        {
                                            int cl_no=query_rs22.getInt(2);
                                             
                                            
                                            if (query_rs22.getString(1).equals(query_rs13.getString(1)))
                                            {   
                                                
                                                System.out.println(query_rs13.getString(1));
                                                System.out.println(dataRow1.getCell(cl_no));
          
                                                //if(dataRow1.getCell(cl_no).toString().equals("0.0") )
                                                //{
                                                dataRow1.createCell(cl_no).setCellValue(query_rs13.getString(2));
                                                System.out.println("done for: "+query_rs13.getString(1) +"clno"+cl_no);
                                                //}
                                                
                                            }
                                        }
                                    }
                                    catch(Exception ex){  System.out.println("writing to excel"+ex);}
                                    

                                }
 
                            }
                            catch(Exception ex){}
                        }
                        catch(Exception ex){}
                        
                    }
                    
                    
                    fileOutputStream = new FileOutputStream(fileName);
                    sampleWorkbook.write(fileOutputStream);
                    System.out.println("end of training");
                    
                }
                catch(Exception ex){}
            }
            catch(Exception ex){}//db

        } 
        catch(Exception ex){}//main
                
        finally 
        {
            try 
            {
                    if (fileOutputStream != null) 
                    {
                        fileOutputStream.close();
                    }
            } 
            catch (IOException ex) {}
        }

        
        }//func

    private HSSFCellStyle setHeaderStyle(HSSFWorkbook sampleWorkBook) {
        HSSFFont font = sampleWorkBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setColor(IndexedColors.PLUM.getIndex());
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

   
}
