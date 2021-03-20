package pelea.marp.common.rp.reactive.shared.pddl;

import java.util.List;


/**
 * @author cguzman
 * 25-08-2014
 */
public interface SharedSolution extends java.io.Serializable {

	/**
	 * @return helper agent
	 */
	public String getHelper();
	
	/**
	 * @return set of agents in the teamwork
	 */
	public List<String> getAgents();

	/**
	 * @return set of partial states
	 */
	public List<SharedPartialState> getPlan();
	
}
