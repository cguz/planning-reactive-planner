package pelea.marp.common.rp.reactive.search_spaces;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.planning_task.common.pddl.DSCondition;

public interface SearchSpaceByState extends SearchSpace {	
	// translating
	/**
	 * self-repair
	 * translating the plan as partial states from o to window
	 * @param window_size the plan window
	 */
	void translate_plan_as_partial_states(int window_size);

	void translating(int plan_window, SETTINGS_REACTIVESTRUCTURE approach);

	
	
	
	/**
	 * self-repair
	 * the total plan as partial states
	 * @return
	 */
	List<NodeState> get_plan_as_partial_states();
	
	
	EstimatedTime get_estimated_size();
	
	
	
	
	
	
	// calculating repairing structures - SearchSpaceBasic
	/**
	 * 03/02/2013 14:43:00
	 * self-repair
	 * Generating a reactive structure from a root goal state
	 * @param best_setting.window 	window size to be used
	 * @param best_setting.depth 	max length of the structure
	 */
	void calculating(EstimatedTime best_setting);
	

	
	
	
	
	// self-repairing method - SearchSpaceBasic	
	void reordering_other(int window_from, List<NodeState> ps);
	

	/**
	 * getting a plan solution
	 * @param s current world state
	 * @param index_target
	 * @return a path solution
	 */
	public ArrayList<Integer> getPlan(ArrayList<DSCondition> s, int index_target);
	
	public List<Integer> get_plan_remaining();
	
	
	// others - MAIN

	void forward_partial_states(int from, int planning_horizon);
	
	void 		setWindowFrom(int window);

	
	NodeState 	getLastGState();
	
	// TEST - 11/04/2014
	ArrayList<DSCondition> filterRelevantVar(ArrayList<DSCondition> goal_state);
	ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state);	
	ArrayList<DSCondition> filter_next_action(ArrayList<DSCondition> goal_state, int timeStep);

	
	// others
	/**
	 * 11/04/2014
	 * @return the total of actions of the repaired plan that are in the original plan
	 */
	Integer getRepairedStability();
	Integer getRepairedStability(int from, int Window_size);
	
}
