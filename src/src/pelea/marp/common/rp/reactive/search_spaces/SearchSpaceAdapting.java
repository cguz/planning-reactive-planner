package pelea.marp.common.rp.reactive.search_spaces;

import java.util.ArrayList;

import pelea.planning_task.common.pddl.DSCondition;


public interface SearchSpaceAdapting extends SearchSpace {

	/**
	 * adapting the plan solution
	 * @param current world state S
	 * @param dummy actions of the other agent
	 * @return a path solution with dummy actions
	 */
	public ArrayList<Integer> plan_adaptation(ArrayList<DSCondition> S, ArrayList<Integer> actions);

	/**
	 * parsing the plan solution
	 * @param dummy actions of the other agent
	 * @param new adapted plan
	 * @return a path solution with dummy actions
	 */
	public ArrayList<Integer> parsing_plan(ArrayList<Integer> plan_other_agent, ArrayList<Integer> adapted_plan);

	/**
	 * generate the reschedule info for the other agents
	 * @param dummy actions of the other agent
	 * @param new adapted plan
	 */
	public void reschedule(ArrayList<Integer> plan_other_agent, ArrayList<Integer> adapted_plan);
	
}
