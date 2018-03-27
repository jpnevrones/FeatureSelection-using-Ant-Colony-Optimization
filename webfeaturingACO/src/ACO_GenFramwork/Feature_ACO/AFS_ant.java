package ACO_GenFramwork.Feature_ACO;
import ACO_GenFramwork.*;
import java.util.*;



public class AFS_ant extends Ant
{
    private static final double B    = 2;
    private static final double Q0   = 0.8;
    private static final double R    = 0.1;
    
    

	String wpage_name;
	double weight_threshold;
      
 
    
    private static final Random s_randGen = new Random(System.currentTimeMillis());
        
    protected Hashtable<Integer, Integer> m_nodesToVisitTbl;
    
        
    public AFS_ant(int startNode, Observer observer,String t_name,double threshold,double refocc)
    { 
    	
        super(startNode, observer, t_name,refocc,threshold);
        
        wpage_name=t_name;
        weight_threshold=threshold;
        
    }
    
    @Override
    public void init()
    {
        super.init();
        
        final AntGraph graph = s_antColony.getGraph();
        
        m_nodesToVisitTbl = new Hashtable<Integer, Integer>(graph.nodes());
        for(int i = 0; i < graph.nodes(); i++)
            m_nodesToVisitTbl.put(new Integer(i), new Integer(i));
        m_nodesToVisitTbl.remove(new Integer(m_nStartNode));
        
    }

    @Override
    public int stateTransitionRule(int nCurNode)
    {
        final AntGraph graph = s_antColony.getGraph();
       
        
        
        // generate a random number
        double q    = s_randGen.nextDouble();
        int nMaxNode = -1;
        
        if(q <= Q0)  // Exploitation
        {
//            System.out.print("Exploitation: ");
            double dMaxVal = -1;
            double dVal;
            int nNode;
            
            // search the max of the value as defined in Eq. a)
             Enumeration<Integer> m = m_nodesToVisitTbl.elements();
            while(m.hasMoreElements())
            {
                // select a node
                nNode = ((Integer)m.nextElement()).intValue();
                // check on tau
                if(graph.tau(nCurNode, nNode) == 0)
                    throw new RuntimeException("tau = 0");
                
                // get the value
                
                dVal = graph.tau(nCurNode, nNode) * Math.pow(graph.etha(nCurNode, nNode), B);
                
                // check if it is the max
                if(dVal > dMaxVal)
                {
                    dMaxVal  = dVal;
                    nMaxNode = nNode;
                    double weight=graph.delta(nCurNode, nNode);
                    if(weight_threshold>weight){ 
                   	 m_nodesToVisitTbl.remove(new Integer(nMaxNode));
    				   }
                    
                }
                //else{
                	//}

            }
            
        }
        else  // Exploration
        {
//              ("Exploration");
            double dSum = 0;
            int nNode = -1;
            
            // get the sum at denominator
            Enumeration<Integer> enu = m_nodesToVisitTbl.elements();
            while(enu.hasMoreElements())
            {
                nNode = ((Integer)enu.nextElement()).intValue();
                if(graph.tau(nCurNode, nNode) == 0)
                    throw new RuntimeException("tau = 0");
                
                // Update the sum
                dSum += graph.tau(nCurNode, nNode) * Math.pow(graph.etha(nCurNode, nNode), B);
            }
            
            if(dSum == 0)
                throw new RuntimeException("SUM = 0");
            
            // get the everage value
            double dAverage = dSum / (double)m_nodesToVisitTbl.size();
            
            // search the node in agreement with eq. b)
            enu = m_nodesToVisitTbl.elements();
            while(enu.hasMoreElements() && nMaxNode < 0)
            {
                nNode = ((Integer)enu.nextElement()).intValue();
                
                              
                // if the value of p is greater the the average value the node is good
                if((graph.tau(nCurNode, nNode) * Math.pow(graph.etha(nCurNode, nNode), B)) > dAverage)
                {
                    nMaxNode = nNode;
                }
                
            }
            
            if(nMaxNode == -1)
                nMaxNode = nNode;
       }
                 
        if(nMaxNode < 0)
            throw new RuntimeException("maxNode = -1");
        
        // delete the selected node from the list of node to visit
        m_nodesToVisitTbl.remove(new Integer(nMaxNode));
        
        return nMaxNode;    }
    
    @Override
    public void localUpdatingRule(int nCurNode, int nNextNode)
    {
        final AntGraph graph = s_antColony.getGraph();
        
        double val =((double)1 - R) * graph.tau(nCurNode, nNextNode) +(R * (graph.tau0()));
        graph.updateTau(nCurNode, nNextNode, val);
    }
    
    @Override
    public boolean better(double dPathValue1, double dPathValue2)
    {
        return dPathValue1 < dPathValue2;
    }

    @Override
    public boolean end()
    {
        return m_nodesToVisitTbl.isEmpty();
    }
}

