package pelea.marp.common.rp.reactive.search_spaces;

import java.util.ArrayList;

import pelea.planning_task.common.pddl.DSCondition;


public interface SearchSpaceAdaptingOwnPlan extends SearchSpace {
	
	/**
	 * getting a plan solution
	 * @param current world state S
	 * @return a path solution
	 */
	public ArrayList<Integer> find_plan(ArrayList<DSCondition> S);

	/**
	 * @return the root node of the search space
	 */
	public NodeState get_root();
	
}
