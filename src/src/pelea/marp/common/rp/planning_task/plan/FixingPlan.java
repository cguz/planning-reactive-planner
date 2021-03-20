package pelea.marp.common.rp.planning_task.plan;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.planning_task.pddl.PartialState;
import pelea.marp.common.rp.reactive.enums.T_SOLUTION;

public interface FixingPlan {	

	/**
	 * @return the name of the helper agent 
	 */
	String getHelper();
	
	/**
	 * @return the set of agents in the teamwork 
	 */
	List<String> getAgents();
	
	/**
	 * @return the solution as sequence of partial states
	 */
	ArrayList<PartialState> getSolution();
	
	/**
	 * @return the type of solution (total or partial)
	 */
	T_SOLUTION getTypeSolution();
	
	/**
	 * @return the time when reach the partial state that request the agent
	 */
	double getTimeReachPS();

	/**
	 * @return the number of fluents that need to be reached with this solution
	 */
	int getFluents();
	
	void mixed(ArrayList<PartialState> mixed);

	/**
	 * @return the set of fluents reached by this solution
	 */
	List<Integer> getFluentsReached();

	/**
	 * multi-repair teamwork committed
	 * @return true if any fluents to reach is removed from the solutions
	 */
	boolean filter_fluents_reached();
	
}
