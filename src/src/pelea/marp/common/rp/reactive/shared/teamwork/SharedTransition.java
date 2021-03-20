package pelea.marp.common.rp.reactive.shared.teamwork;

import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;

/**
 * @author cguzman
 * 25-08-2014
 */
public interface SharedTransition extends java.io.Serializable {

	/**
	 * @return time instant where the agent reach the fluents
	 */
	public double getTimeInstant();

	/**
	 * @return get the committed of the transition
	 */
	public String getCommitted();

	/**
	 * @return get the requester of the transition
	 */
	public String getRequester();
	
	/**
	 * @return the fluents to reach by the committed agent
	 */
	public SharedPartialState getFluentsToReach();

	/**
	 * is the transition reached ?
	 * @return true / false
	 */
	public boolean is_reached();

	/**
	 * change the transition to reach
	 */
	public void change_reached();
	
}