/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webfeaturingaco;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author JP
 */
public class produceARFF {

    private static PrintStream s_outs;
    static Connection ACOfeature_selection;
    static PreparedStatement query, query1, query2, query3, query4, query5;
    static ResultSet query_rs, query_rs2, query_rs3, query_rs4, query_rs5;
    static int query_rs1;

    public produceARFF() {
        ACOfeature_selection = null;
        query = null;
        query_rs = null;
        query1 = null;
        query2 = null;
        query_rs2 = null;
        s_outs = null;
                        if (s_outs == null) {
                    try {

                        s_outs = new PrintStream(new FileOutputStream("E:\\ACO_workspace\\Results\\ARFF\\test.arff",true));
                        
                    } catch (Exception ex) {
                    }

                }

    }

    public void generateARFF() {
         
        try {
            //database conectivity
            String url1 = "jdbc:mysql://localhost:3306/ACO2";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            ACOfeature_selection = DriverManager.getConnection(url1, "root", "root");
            //WebFeaturingACOView.writeToTextArea("connection established succesfully");
            s_outs.println("@RELATION aco");  
            try{
            query3 = ACOfeature_selection.prepareStatement("select feature from trainingdataset");
                        query_rs3 = query3.executeQuery();
                        while (query_rs3.next()) {
                            s_outs.println("@ATTRIBUTE " + query_rs3.getString(1) + " NUMERIC");
                        }
                        s_outs.println("@ATTRIBUTE class {course,student,faculty,others}");
                        s_outs.println("@DATA");}
            catch(Exception ex){}
            query = ACOfeature_selection.prepareStatement("select * from webpagestested where test=1");
            query_rs = query.executeQuery();


            while (query_rs.next()) {

                String t_name = query_rs.getString(3);
                System.out.println(t_name);
                         if (s_outs == null) {
                    try {

                        s_outs = new PrintStream(new FileOutputStream("E:\\ACO_workspace\\Results\\ARFF\\test.arff",true));
                        
                    } catch (Exception ex) {
                    }

                }


                try {
                    query1 = ACOfeature_selection.prepareStatement("update webpagestested set test = 2 where tablename='" + t_name + "'");
                    query_rs1 = query1.executeUpdate();
                } catch (Exception ex) {
                }


                try {
                    query4 = ACOfeature_selection.prepareStatement("select MAX(DISTINCT iteration) from ofeature_" + t_name);
                    query_rs4 = query4.executeQuery();
                    String rs_iteration = null;
                    String data = "";
                    while (query_rs4.next()) {
                        //System.out.println(query_rs4.getString(1));
                        rs_iteration = query_rs4.getString(1);
                    }
                   
                    try {
                        String wg = "0";
                        

                        query2 = ACOfeature_selection.prepareStatement("select feature from trainingdataset");
                        query_rs2 = query2.executeQuery();
                        while (query_rs2.next()) {
                            try {

                                query5 = ACOfeature_selection.prepareStatement("select feature,nrweight from ofeature_" + t_name + " where iteration=" + rs_iteration);
                                query_rs5 = query5.executeQuery();
                                while (query_rs5.next()) {
                                   
                                    if (query_rs2.getString(1).equals(query_rs5.getString(1))) {
                                        wg = null;
                                        wg = query_rs5.getString(2);
                                        System.out.println(wg+ query_rs5.getString(2));
                                        
                                    }

                                }
                            data = data + wg;
                            data = data + ",";
                            wg="0";
                            } catch (Exception ex) {
                            }

                        
                        }
                       
                    } catch (Exception ex) {
                    }
                        data = data + "?";
                        System.out.println("ding dong");
                        s_outs.println(data);
                } catch (Exception ex) {
                }
                
            }

            if (!query_rs.next()) {
                System.out.println("No more ARFF to be generated");
                s_outs=null;
            }




        } catch (Exception ex) {
        }

    }

    public static void main(String[] args) {
        new produceARFF().generateARFF();

    }
}
