package pelea.marp.common.rp.planning_task;

import gnu.trove.set.hash.THashSet;

import java.util.Hashtable;

public interface PlanningProblemRP {


	/**
	 * return the services 
	 * @return
	 */
	THashSet<String>[] getServices();
	
	int getVariablesHelpfulSize();

	/**
	 * @return an helpful structure to find quickly the values of the variables
	 */
	Hashtable<Integer, Hashtable<String, Integer>> getVariablesHelpful();
	
}
