package pelea.marp.common.rp.reactive.shared.pddl;

import java.util.ArrayList;

/**
 * @author cguzman - Cesar Guzman Alvarez
 * 25-08-2014
 */
public interface SharedVariable extends java.io.Serializable {

	/**
	 * @return name of the variable
	 */
	public String getName();
    
    /**
     * @return tuple of objects of the variable
     */
    public ArrayList<String> getParams();
    
    /**
     * @return set of values that reach the variable
     */
    public ArrayList<String> getReachableValues();
    
    /**
     * @return type of the values
     */
    public ArrayList<String> getValueTypes();

	String toPddl();
	
}
