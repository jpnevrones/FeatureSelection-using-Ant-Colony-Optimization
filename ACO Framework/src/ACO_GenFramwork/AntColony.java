package ACO_GenFramwork;

import java.util.*;


public abstract class AntColony implements Observer
{    
    protected AntGraph m_graph;
    protected Ant[]    m_ants;
    
    protected int      m_nAnts;
    protected int      m_nAntCounter;
    protected int      m_nIterCounter;
    protected int      m_nIterations;
    
    private int      m_nID;
    
    private static int s_nIDCounter = 0;
    
    public AntColony(AntGraph graph, int nAnts, int nIterations)
    {
        m_graph = graph;
        m_nAnts = nAnts;
        m_nIterations = nIterations;
        s_nIDCounter++;
        m_nID = s_nIDCounter;
    }
    
    public synchronized void start()
    {
        // creates all ants
        m_ants  = createAnts(m_graph, m_nAnts);
        
        m_nIterCounter = 0;
        
        // loop for all iterations
        while(m_nIterCounter < m_nIterations)
        {
            // run an iteration
            iteration();
            try
            {
                wait();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            
            // synchronize the access to the graph
            synchronized(m_graph)
            {
                // apply global updating rule
                globalUpdatingRule();
            }
        }
        
    }
    
    private void iteration()
    {
        m_nAntCounter = 0;
        m_nIterCounter++;
        for(int i = 0; i < m_ants.length; i++)
        {
            m_ants[i].start();
        }
    }
    
    public AntGraph getGraph()
    {
        return m_graph;
    }
    
    public int getAnts()
    {
        return m_ants.length;
    }
    
    public int getIterations()
    {
        return m_nIterations;
    }
    
    public int getIterationCounter()
    {
        return m_nIterCounter;
    }
    
    public int getID()
    {
        return m_nID;
    }
    
    public synchronized void update(Observable ant, Object obj)
    {
        m_nAntCounter++;
        
        if(m_nAntCounter == m_ants.length)
        {
            notify();
            
        }
    }
    
    public double getBestPathValue()
    {
        return Ant.s_dBestPathValue;
    }
    
    public int[] getBestPath()
    {
        return Ant.getBestPath();
    }
    
    public Vector<?> getBestPathVector()
    {
        return Ant.s_bestPathVect;
    }
    
    public int getLastBestPathIteration()
    {
        return Ant.s_nLastBestPathIteration;
    }
    
    public boolean done()
    {
        return m_nIterCounter == m_nIterations;
    }
    
    protected abstract Ant[] createAnts(AntGraph graph, int ants);
    
    protected abstract void globalUpdatingRule();
}
