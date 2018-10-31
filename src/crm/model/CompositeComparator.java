package crm.model;

import java.util.Comparator;



public class CompositeComparator implements Comparator<Object> 
{
    private Comparator<Object> m_major;
    private Comparator<Object> m_middle;
    private Comparator<Object> m_minor;

    public CompositeComparator(Comparator<Object> major, 
    		Comparator<Object> middle, Comparator<Object> minor) 
    {
        m_major = major;
        m_middle = middle;
        m_minor = minor;
    }
    
    public int compare(Object o1, Object o2) 
    {
        int result1 = m_major.compare(o1, o2);
        int result2 = m_middle.compare(o1, o2);
        
        if (result1 != 0) 
        {
            return result1;
        }
        else if(result2 != 0)
        {
        	return result2;
        }
        else 
        {
            return m_minor.compare(o1, o2);
        }
    }
    
}
