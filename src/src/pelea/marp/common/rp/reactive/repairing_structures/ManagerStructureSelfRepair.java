package pelea.marp.common.rp.reactive.repairing_structures;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.search_spaces.NodeState;

public interface ManagerStructureSelfRepair extends ManagerStructure {

	/**
	 * plan window of the search tree
	 * @return execution cycles covered by the search space
	 */
	public int get_plan_window();
	
	/**
	 * associated partial states to the search tree
	 * @return partial states of the plan
	 */
	public List<NodeState> get_associated_plan();

	int get_total_act_supported();

	
	/**
	 * @param plan
	 * @return
	 */
	public List<NodeState> reordering(ArrayList<Integer> plan);

	
	
	
	// self-repair process

	public List<Integer> get_actions_current_window();

	// public List<DSPlanAction> get_actions_plan_current_window();
}
