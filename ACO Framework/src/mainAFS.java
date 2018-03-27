
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mainAFS{
	
	     private static PrintStream s_outs;
	     static Connection ACOfeature_selection;
	     static PreparedStatement query,query1,query2,query3,query4;
   	     static ResultSet query_rs,query_rs2,query_rs3,query_rs4;
		 static int query_rs1;
		 
		 //constructor
		 public mainAFS(){
			        ACOfeature_selection = null;
			        query = null;
			        query_rs=null;
			        query1 = null;
			        query2 = null;
			        query_rs2=null;
			        s_outs = null;
			        
		            }
		 
		 
		 public static void main(String args[]){
			int fnode=0; 
			
			try{    
				    //database conectivity
					String url1="jdbc:mysql://localhost:3306/ACO" ;
					Class.forName("com.mysql.jdbc.Driver").newInstance(); 
					ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
				    //System.out.println("connection established succesfully");
				    
				    query = ACOfeature_selection.prepareStatement("select * from webpagestested where tested=0");
				    query_rs=query.executeQuery();
				    
				    while(query_rs.next()){
				    	
				    	 String t_name=query_rs.getString(2);
				    	 String wpage_name=query_rs.getString(1);
				    	 if(s_outs == null)
		    		        {
		    		            try
		    		            { 
		    		             
   		    		                s_outs = new PrintStream(new FileOutputStream("E:\\temp\\OptimalSet.txt"));
		    		            }
		    		            catch(Exception ex)
		    		            {
		    		                ex.printStackTrace();
		    		            }

				 	 	}
 
				    	 try{			    	
					    	 query1 = ACOfeature_selection.prepareStatement("update webpagestested set tested = 1 where tablename='"+t_name+"'");
							 query_rs1=query1.executeUpdate();
				    	 }
				    	 
				    	 catch(Exception ex){
		
				    	 }
				    	 
				    	 try{
				    		 query2 = ACOfeature_selection.prepareStatement("select count(*) from "+t_name);
				    		 query_rs2=query2.executeQuery();
						 
				    		 while(query_rs2.next()){
				    			 fnode=query_rs2.getInt(1);	
						 	 	} 
				    	 }
				    	 
				    	 catch(Exception ex){
		
				    	 }
						 ACOpreprocessing acopreprocessing = new ACOpreprocessing(t_name,fnode);
						// acopreprocessing.AFS_intialprocessing();
				    	 acopreprocessing.ACO_afs();
				    	 try{
				    	 query4 = ACOfeature_selection.prepareStatement("select MAX(DISTINCT iteration) from ofeature_"+t_name);
						 query_rs4=query4.executeQuery();
						 String rs_iteration=null;
						 while(query_rs4.next()){
			    			  //System.out.println(query_rs4.getString(1));
			    			  rs_iteration=query_rs4.getString(1);	 
					 	 	}
						 try{
							 
						 query3 = ACOfeature_selection.prepareStatement("select feature,occ,nrweight from ofeature_"+t_name+" where iteration="+rs_iteration);
						 query_rs3=query3.executeQuery();
						 System.out.println("                                                                     ");
						 System.out.println("-------------------------------------------------------------------------------------------------------------------------");	
						 System.out.println("	OPTIMAL Feature Set Extracted From : "+wpage_name);
						 System.out.println("-------------------------------------------------------------------------------------------------------------------------");
						 s_outs.println("                                                                         ");
						 s_outs.println("                                                                         ");
						 s_outs.println("-------------------------------------------------------------------------------------------------------------------------");
 	   	                 s_outs.println("		Optimal feature set extracted from "+wpage_name);
 		                 s_outs.println("-------------------------------------------------------------------------------------------------------------------------");

						 while(query_rs3.next()){
							 //output is being printed here
			    			  System.out.println("			"+query_rs3.getString(1)+"("+query_rs3.getString(2)+","+query_rs3.getString(3)+")");
			    			  s_outs.println("		"+query_rs3.getString(1)+"("+query_rs3.getString(2)+","+query_rs3.getString(3)+")");
			    			  }
						 System.out.println("                                                                         ");
						// System.out.println("-------------------------------------------------------------------------");
 	   	                 
						 }
						 catch(Exception ex){
							 
							 
						 }
				    	 }
				    	 catch(Exception ex){
				    		 
				    	 }
				         }
				    
	                			    
		            }
			
		     catch(Exception ex){
		    	 System.out.println("DB: EXCEPTION OCCURED at mainAFS.java MSG:"+ex);
		          }
	
		   }
		 
	    
	     }
