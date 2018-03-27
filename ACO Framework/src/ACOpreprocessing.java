
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.*;
import ACO_GenFramwork.Feature_ACO.*;
import ACO_GenFramwork.*;

public class ACOpreprocessing {		

		int nAnts=0;
		int nNodes=0;
		int nIterations=20;
		int nRepetitions=1;
		String wpage_name;
		Vector<String> feature = new Vector<String>();
		Vector<Integer> weight = new Vector<Integer>();
		Vector<Integer> occ = new Vector<Integer>();
		static Connection ACOfeature_selection;
	    static PreparedStatement query,query1,query2;
  	    static ResultSet query_rs,query_rs2;
		static int query_rs1;
		String max_weight;
		double ref_weight;
		 
		
		public ACOpreprocessing(String t_name, int fnode){
			ACOfeature_selection = null;
	        query = null;
	        query_rs=null;
	        query1 = null;
	        query2 = null;
	        query_rs2=null;
			ACO_input aco_input = new ACO_input(feature,weight,occ);
			aco_input.ACO_GETinput(t_name);
			wpage_name=t_name;
			if(fnode > 0 && fnode <= 200){
			   nNodes=fnode;
			   nAnts=fnode;
			}
			else if(fnode > 200 && fnode <= 250){
				nNodes=fnode;
				nAnts=(fnode-135);
			}
			else if(fnode > 250 && fnode <= 300){
				nNodes=fnode;
				nAnts=(fnode-230);
			}
			else{
				nNodes=fnode;
				nAnts=10;

			}
			}
		
		void ACO_afs(){
			double nr_weight[][] = new double[nNodes][nNodes];
			double occmat[][] = new double[nNodes][nNodes];
			
			//System.out.println(nAnts);
			if(nAnts == 0 || nNodes == 0 || nIterations == 0 || nRepetitions == 0) {
				System.out.println("CANNOT PERFORM ACO FEATURE SELECTION WITH THESE DATA----- INSUFFICENT DATA"); 
				return;
		    	}
			try{
			
			String url1="jdbc:mysql://localhost:3306/ACO" ;
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
		   	query2 = ACOfeature_selection.prepareStatement("select max(totaloccurence) from "+wpage_name);
		    query_rs2=query2.executeQuery();
		    while(query_rs2.next()){
		    max_weight=query_rs2.getString(1);
		    }
		    ref_weight=(((Double.parseDouble(max_weight))/2)+((Double.parseDouble(max_weight))/4))/2;
		    
		    //System.out.println(ref_weight);
			}
			catch(Exception ex){
			}
			
			try{
				for(int i=0;i<nNodes;i++){
					for(int j=i+1;j<nNodes;j++){
						nr_weight[i][j]=weight.elementAt(j);
						nr_weight[j][i]=weight.elementAt(i);
						occmat[i][j]=occ.elementAt(j);
						occmat[j][i]=occ.elementAt(i);
					}
				}
			}
			catch(Exception ex){
				System.out.println("FOR LOOPS: EXCEPTION OCCURED at ACOpreprocessing.java MSG:"+ex);
			}
			
			AntGraph graph = new AntGraph(nNodes, nr_weight);

			try {	  			 
				 for (int i = 0; i < nRepetitions; i++) {
					 graph.resetTau();
					 AFS_antcol antColony = new AFS_antcol(graph, nAnts,nIterations,wpage_name,ref_weight);
					 antColony.start();					
				     }				 
			     } 
			 catch (Exception ex) {
				 System.out.println(" ANT COLONY CREATION:EXCEPTION OCCURED at ACOpreprocessing.java MSG:"+ex);				 
			     }
			}
		/*void AFS_intialprocessing(){
			try{
			
		    query = ACOfeature_selection.prepareStatement("select * from '"+wpage_name+"' where tested=0");
		    query_rs=query.executeQuery();
		    
			}
			catch(Exception ex){
				
			}
			
			 
		 }*/

	    }
