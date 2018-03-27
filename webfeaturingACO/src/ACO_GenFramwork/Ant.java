
package ACO_GenFramwork;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import webfeaturingaco.WebFeaturingACOView;

public abstract class Ant extends Observable implements Runnable
{
    private int m_nAntID;
    protected int[][]  m_path;
    protected int      m_nCurNode;
    protected int      m_nStartNode;
    protected double   m_dPathValue;
    protected Observer m_observer;
    protected Vector<Integer>   m_pathVect;
    private static int s_nAntIDCounter = 0;
    protected static AntColony s_antColony;
    public static double    s_dBestPathValue = Double.MAX_VALUE;
    public static Vector<Integer>    s_bestPathVect  = null;
    public static int[][]   s_bestPath      = null;
    public static int       s_nLastBestPathIteration = 0;
    public static int       result_counter ;
    public static int       no_counter ;
    Connection ACOfeature_selection;
    PreparedStatement query,query1,query2,query3;
	static int  query_rs,query_rs1;
	ResultSet query_rs2,query_rs3;
	String wpage_name,feature,occ,nr_weight;
  double  ref_occ,ref_weight;
                        
    public static void setAntColony(AntColony antColony)
    {
        s_antColony = antColony;
    }
    
    // resetting ants to initial values for next round
    public static void reset()
    {
        s_dBestPathValue = Double.MAX_VALUE;
        s_bestPathVect = null;
        s_bestPath = null;
        s_nLastBestPathIteration = 0;
    }
    

    //initialization of ant
    public Ant(int nStartNode, Observer observer, String t_name,double refocc,double refweight)
    {
        s_nAntIDCounter++;
        m_nAntID    = s_nAntIDCounter;
        m_nStartNode = nStartNode;
        m_observer  = observer;
        ACOfeature_selection = null;
        query = null;
        query1 = null;
        query2 = null;
        query_rs2=null;
        wpage_name=t_name;
        result_counter = 0 ;
        no_counter = 0 ;
        ref_occ=refocc;
        ref_weight=refweight;
    }

    public void init()
    {
        
        final AntGraph graph = s_antColony.getGraph();
        m_nCurNode   = m_nStartNode;
        
        m_path      = new int[graph.nodes()][graph.nodes()];
        m_pathVect  = new Vector<Integer>(graph.nodes());
        
        m_pathVect.addElement(new Integer(m_nStartNode));
        m_dPathValue = 0;
    }

    public void start()
    {
        init();
        Thread thread = new Thread(this);
        thread.setName("Ant " + m_nAntID);
        thread.start();
    }

    public void run()
    {
        final AntGraph graph = s_antColony.getGraph();
        
        // repeat while End of Activity Rule returns false
        while(!end())
        {
            int nNewNode;
            
            // synchronize the access to the graph
            synchronized(graph)
            {
                // apply the State Transition Rule
                nNewNode = stateTransitionRule(m_nCurNode);
                
                // update the length of the path
                m_dPathValue += graph.delta(m_nCurNode, nNewNode);
            }
                        
            // add the current node the list of visited nodes
           
	            m_pathVect.addElement(new Integer(nNewNode));
           
	            m_path[m_nCurNode][nNewNode] = 1;     
            synchronized(graph)
            {
                // apply the Local Updating Rule
                localUpdatingRule(m_nCurNode, nNewNode);
            }
            
            // update the current node
            m_nCurNode = nNewNode;
        }
        
        synchronized(graph)
        {
            // update the best tour value
            if((better(m_dPathValue, s_dBestPathValue)))
            {
                s_dBestPathValue        = m_dPathValue;
                s_bestPath              = m_path;
                s_bestPathVect          = m_pathVect;
                s_nLastBestPathIteration = s_antColony.getIterationCounter();
                result_counter++;
                try{    
				    //database conectivity
					String url1="jdbc:mysql://localhost:3306/ACO2" ;
					Class.forName("com.mysql.jdbc.Driver").newInstance(); 
					ACOfeature_selection =DriverManager.getConnection (url1,"root","root");
				    //System.out.println("WRITING  FEATURE SET"+result_counter);
				    if(result_counter==1)
				    {
					    query = ACOfeature_selection.prepareStatement("CREATE TABLE `OFeature_"+wpage_name+"` (`no` INT(10)  UNSIGNED NOT NULL ,`iteration` INT(10)  UNSIGNED NOT NULL ,`feature` VARCHAR(200) NOT NULL ,`bestpathvalue` INT(10) UNSIGNED NOT NULL ,`occ` INT(10) UNSIGNED NOT NULL , `nrweight` INT(10) UNSIGNED NOT NULL ,PRIMARY KEY (`no`))");
					    query_rs=query.executeUpdate();
				    }
				    for(int j=(s_bestPathVect.size()-1);j>=0;j--)
				    {
					    no_counter++;
					    int feature_no=(s_bestPathVect.elementAt(j));
					    feature_no++;
					    try{
						    query2 = ACOfeature_selection.prepareStatement("select words from "+wpage_name+" where no="+feature_no);
						    query_rs2=query2.executeQuery();
						    while(query_rs2.next()){
						    feature=query_rs2.getString(1);
						    }
						    query3 = ACOfeature_selection.prepareStatement("select totaloccurence,normalisedweight from "+wpage_name+" where no="+feature_no);
						    query_rs3=query3.executeQuery();
						    while(query_rs3.next()){
						    occ=query_rs3.getString(1);
                                             
						    nr_weight=query_rs3.getString(2);
						    }

						    try{
						    	if((Double.parseDouble(occ))>ref_occ){
							    query1 = ACOfeature_selection.prepareStatement("insert into OFeature_"+wpage_name+" values( '"+no_counter+"' , '"+result_counter+"' ,'"+feature+"','"+s_dBestPathValue+"','"+occ+"','"+nr_weight+"')");
							    query_rs1=query1.executeUpdate();
						    	}
                                                        if((Double.parseDouble(occ))<=ref_occ){
                                                            if((Double.parseDouble(nr_weight))>ref_weight){
							    query1 = ACOfeature_selection.prepareStatement("insert into OFeature_"+wpage_name+" values( '"+no_counter+"' , '"+result_counter+"' ,'"+feature+"','"+s_dBestPathValue+"','"+occ+"','"+nr_weight+"')");
							    query_rs1=query1.executeUpdate();
                                                            }
						    	}
						
						    }
						    catch(Exception ex){
						    	WebFeaturingACOView.writeToTextArea("DB INSERT INTO : EXCEPTION OCCURED at Ant.java MSG:"+ex);
						    }

					    }
					    catch(Exception ex){
					    	WebFeaturingACOView.writeToTextArea("DB SELECT : EXCEPTION OCCURED at Ant.java MSG:"+ex);				    
					    }
					}
				 }
			
		     catch(Exception ex){
		    	 WebFeaturingACOView.writeToTextArea("DB CREATE TABLE: EXCEPTION OCCURED at Ant.java MSG:"+ex);

		          }

                }
  
        }
        
        // update the observer
        m_observer.update(this, null);
        
    }
    
    protected abstract boolean better(double dPathValue, double dBestPathValue);
    
    public abstract int stateTransitionRule(int r);
    
    public abstract void localUpdatingRule(int r, int s);
    
    public abstract boolean end();
    
    public static int[] getBestPath()
    {
        int nBestPathArray[] = new int[s_bestPathVect.size()];
        for(int i = 0; i < s_bestPathVect.size(); i++)
        {
            nBestPathArray[i] = ((Integer)s_bestPathVect.elementAt(i)).intValue();
        }

        return nBestPathArray;
    }
        
    @Override
    public String toString()
    {
        return "Ant " + m_nAntID + ":" + m_nCurNode;
    }
}






