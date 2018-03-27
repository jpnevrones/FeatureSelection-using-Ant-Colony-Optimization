package webfeaturingaco;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ACO_input {
	   
	     Connection ACOfeature_selection;
	     PreparedStatement query,query1;
   	     ResultSet query_rs,query_rs1;
   	     Vector<String> dfeature = new Vector<String>();
		 Vector<Integer> dweight = new Vector<Integer>();
		 Vector<Integer> docc = new Vector<Integer>();
         
 
		 public ACO_input(Vector<String> feature, Vector<Integer> weight, Vector<Integer> occ){
			        ACOfeature_selection = null;
			        query = null;
			        query_rs=null;
			        query1 = null;
			        query_rs1=null;
			        dfeature=feature;
			        dweight=weight;
			        docc=occ;
			        
		            }
		 
		 public void ACO_GETinput(String t_name){
			try{    
				    //database conectivity
					String url1="jdbc:mysql://localhost:3306/ACO2" ;
					Class.forName("com.mysql.jdbc.Driver").newInstance(); 
					ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
				    //System.out.println("connection established succesfully");
				    //System.out.println(t_name);
				    query = ACOfeature_selection.prepareStatement("select * from "+t_name);
				    query_rs=query.executeQuery();
				    while(query_rs.next()){
				    	dfeature.add(query_rs.getString(2));//feature name
				    	docc.add(Integer.parseInt(query_rs.getString(3)));//occurence
				    	dweight.add(Integer.parseInt(query_rs.getString(4)));//weight
				      }
		            }
			
		     catch(Exception ex){
		    	 WebFeaturingACOView.writeToTextArea("DB: EXCEPTION OCCURED at ACO_input.java MSG:"+ex);

		          }
	
			 
		     }
		
	     }



