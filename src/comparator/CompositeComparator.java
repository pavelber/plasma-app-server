package comparator;

import java.util.Comparator;

import fac2db.level.ILevelState;




public class CompositeComparator implements Comparator<ILevelState> 
{
    private Comparator<ILevelState> m_major;
    private Comparator<ILevelState> m_minor;

    public CompositeComparator(Comparator<ILevelState> major, 
    		Comparator<ILevelState> minor) 
    {
        m_major = major;
        m_minor = minor;
    }
    
    public int compare(ILevelState o1, ILevelState o2) 
    {
        int result1 = m_major.compare(o1, o2);
        
        if (result1 != 0) 
        {
            return result1;
        }
        else 
        {
            return m_minor.compare(o1, o2);
        }
    }
}
