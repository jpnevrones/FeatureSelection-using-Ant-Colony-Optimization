package webfeaturingaco;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ACO_db {
	     //declaration of variables
	     Connection ACOfeature_selection;
	     PreparedStatement query;
   	     ResultSet query_rs;
   	     Vector<String> dfeature = new Vector<String>();
		 Vector<Integer> dweight = new Vector<Integer>();
		 Vector<Integer> docc = new Vector<Integer>();
         
   	     //constructor
		 public ACO_db(Vector<String> feature, Vector<Integer> weight, Vector<Integer> occ){
			        ACOfeature_selection = null;
			        query = null;
			        query_rs=null;
			        dfeature=feature;
			        dweight=weight;
			        docc=occ;
			        
		            }
		 
		 public void ACO_GETinput(){
			try{    
				    //database conectivity
					String url1="jdbc:mysql://localhost:3306/ACO2" ;
					Class.forName("com.mysql.jdbc.Driver").newInstance(); 
					ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
				    WebFeaturingACOView.writeToTextArea("connection established succesfully");
				    query = ACOfeature_selection.prepareStatement("select * from accentureaspx");
				    query_rs=query.executeQuery();
				    while(query_rs.next()){
				    	dfeature.add(query_rs.getString(2));//feature name
				    	docc.add(Integer.parseInt(query_rs.getString(3)));//occurence
				    	dweight.add(Integer.parseInt(query_rs.getString(4)));//weight
				        }
				    
		            }
			
		     catch(Exception e){
		          }
	
			 
		     }
		 
	    
	     }
