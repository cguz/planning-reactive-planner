package pelea.marp.common.rp.reactive.search_spaces;

import java.util.ArrayList;

import pelea.planning_task.common.pddl.DSCondition;


public interface SearchSpaceByFluent extends SearchSpace {
	// calculating
	/**
	 * 03/02/2013 14:43:00
	 * multi-repair
	 * Generating a reactive structure from a root goal state
	 * @param path 		of the test configuration file
	 * @param depth 	max length of the structure
	 */
	void calculating(int depth);

	/**
	 * @return the fluent of the root
	 */
	public int getRootIndex();
	
	/**
	 * getting a plan solution
	 * @param s current world state
	 * @return a path solution
	 */
	public ArrayList<Integer> getPlan(ArrayList<DSCondition> s);
	
}
