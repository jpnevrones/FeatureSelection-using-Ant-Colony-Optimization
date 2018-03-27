package ACO_GenFramwork.Feature_ACO;
import ACO_GenFramwork.*;

import java.util.*;

public class AFS_antcol extends AntColony
{
    protected static final double A = 0.1;
    String wpage_name;
    double weight_threshold;
    double ref_occ;
    public AFS_antcol(AntGraph graph, int ants, int iterations,String t_name,double threshold, double refocc)
    {
        
        super(graph, ants, iterations);
        wpage_name=t_name;
        weight_threshold=threshold;
        ref_occ=refocc;
    }
    
    protected Ant[] createAnts(AntGraph graph, int nAnts)
    {
        Random ran = new Random(System.currentTimeMillis());
        AFS_ant.reset();
        AFS_ant.setAntColony(this);
        AFS_ant ant[] = new AFS_ant[nAnts];
        for(int i = 0; i < nAnts; i++)
        {
            ant[i] = new AFS_ant((int)(graph.nodes() * ran.nextDouble()),this, wpage_name,weight_threshold,ref_occ);
        }
        
        return ant;
    }
    
    protected void globalUpdatingRule()
    {
        double dEvaporation = 0;
        double dDeposition  = 0;
        
        for(int r = 0; r < m_graph.nodes(); r++)
        {
            for(int s = 0; s < m_graph.nodes(); s++)
            {
                if(r != s)
                {
                    // get the value for deltatau
                    double deltaTau =((double)1 / AFS_ant.s_dBestPathValue) * (double)AFS_ant.s_bestPath[r][s];
                                    
                    // get the value for phermone evaporation as defined in eq. d)
                    dEvaporation = ((double)1 - A) * m_graph.tau(r,s);
                    // get the value for phermone deposition as defined in eq. d)
                    dDeposition  = A * deltaTau;
                  
                    // update tau
                    m_graph.updateTau(r, s, dEvaporation + dDeposition);
                }
            }
        }
    }
}
