package pelea.marp.common.rp.planning_task.pddl;

import java.util.List;


/**
 * @author cguzman
 * 25-08-2014
 */
public interface PartialState {

	/**
	 * @return label of the partial state 
	 */
	public int getTimeInstant();

	/**
	 * @return set of fluents by the index of the knowledge agent
	 */
	List<Integer> getFluents();

	/**
	 * @return set of fluents by the name of the knowledge agent
	 */
	List<String> getFluentsName();

	/**
	 * to verify of this partial state contains other partial state
	 * @param f set of fluents
	 * @return true / false
	 */
	boolean contains(List<Integer> f);

}
