package pelea.marp.common.rp.reactive.shared.pddl;

import java.util.List;


/**
 * @author cguzman
 * 25-08-2014
 */
public interface SharedPartialState extends java.io.Serializable {

	/**
	 * @return label of the partial state 
	 */
	public int getTimeInstant();

	/**
	 * @return set of fluents of the partial state
	 */
	public List<SharedFluent> getFluents();
	
}
