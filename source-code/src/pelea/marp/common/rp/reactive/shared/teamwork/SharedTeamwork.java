package pelea.marp.common.rp.reactive.shared.teamwork;

import java.util.List;

import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;

public interface SharedTeamwork extends java.io.Serializable {

	/**
	 * @return the set of agents of the teamwork
	 */
	List<String> getAgents();
	
	/**
	 * @param agent
	 * @return the set of agents which agent i is committed to
	 */
	List<String> get_commitments(String agent);
	
	/**
	 * @param t, a given time instant
	 * @param agent
	 * @return the set of agents which agent is committed to in a given time instant
	 */
	List<String> get_commitments(int t, String agent);
	
	/**
	 * @param agent
	 * @return the set of fluents that the agent need to reach
	 */
	SharedPartialState get_all_helper_fluents(String agent);

	/**
	 * @param agent
	 * @return the set of fluents that the agent need to reach from a specific time
	 */
	SharedPartialState[] get_helper_fluents(String agent_name);
	
	/**
	 * @param agent
	 * @param last_time 
	 * @return the set of fluents that other agents reach to the requester agent
	 */
	SharedPartialState[] get_assisted_fluents(String agent, int last_time);

	SharedPartialState[] get_assisted_fluents(String agent);
	
	/**
	 * add a transition to the teamwork
	 * @param transition
	 */
	void update(SharedTransition transition);
	
	/**
	 * teamwork committed
	 * update the current teamwork
	 * @param current time
	 * @param info to reschedule
	 */
	void update(int time, List<Integer> reschedul);
	
	/**
	 * remove the previous transitions with agents from time instant t
	 * @param t time instant
	 * @return true if the teamwork is empty
	 */
	boolean remove(int t);

	/**
	 * update the status to transition reached 
	 * @param t time instant
	 * @return true if the teamwork is empty
	 */
	boolean reached(int i);

	/**
	 * @param agent 
	 * @return the last time that an agent has to wait
	 */
	int get_last_time_assisted_fluents(String agent);
	
	/**
	 * @param agent 
	 * @return the first time that an agent has to wait
	 */
	int get_first_time_assisted_fluents(String agent);
	
	/**
	 * @param agent 
	 * @return the last time where an  reach fluents to other agents
	 */
	int get_last_time_helper_fluents(String agent);

	/**
	 * @return the info to reschedule the plan
	 */
	List<Integer> get_reschedule();

	/**
	 * get the executed time
	 * @param real simulator time
	 * @return executed time or actions ( single-strips )
	 */
	int get_executed_time(double time);

	double get_last_removed_time();

	void set_last_removed_time(double last_removed_time);

	int get_created_at_time();

	List<SharedTransition> get_transtions();
	
}
