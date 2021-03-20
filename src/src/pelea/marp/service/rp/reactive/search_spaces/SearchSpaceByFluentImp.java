package pelea.marp.service.rp.reactive.search_spaces;

import java.util.ArrayList;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByFluent;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.service.rp.reactive.settings.MultiSettingsImp;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSCondition;

/**
 * @author cguzman
 * implements a search space by fluent
 */
public class SearchSpaceByFluentImp extends SearchSpaceAbstractImp implements SearchSpaceByFluent { 	
	/**
	 * multi-repairing process
	 * @param planningProblem
	 * @param goal_state
	 * @param settings
	 * @param path_eval_benchmarks 
	 * @param window_current 
	 */
	public SearchSpaceByFluentImp(PlanningProblem planningProblem, GeneralSettings settings, String path_eval_benchmarks) {
		super(planningProblem, settings,path_eval_benchmarks);
	}

	@Override
	public int getRootIndex() {
		return ((MultiSettingsImp)settings).getFluent();
	}


	@Override
	public ArrayList<Integer> getPlan(ArrayList<DSCondition> S) {
		long initialTime = System.nanoTime();
		
		NodeState targetNode = null;
        NodeState originNode = null;
		// RGS_GH origin = null;
        
        ArrayList<ArrayList<Integer>> 	plan_repaired 	= new ArrayList<ArrayList<Integer>>();		
        
        // searching the target state 
        targetNode = s_statesIndex.get(0); 
		
		// changing the value of the state variables in the goal state with the failure value, return the pre of the next action as a goal state 
		// origin = changingCurrentValuesPre(S, this.root_node.getGoalState());

    	originNode = getOriginNode(new ArrayList<DSCondition>(0), targetNode, S, null);
    	// originNode = getOriginNode(origin.getGoalState(), targetNode, S, null);
        if(originNode == null){
        	if(total_results.repair_error == SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_TARGET)
        		total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_TARGET_AND_ORIGIN; 
        	else
        		total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_ORIGIN;
        }else{
        	plan_repaired = get_paths(originNode, targetNode);
	        System.out.println("[PLAN REPAIRED]"+plan_repaired);
	        System.out.println("[PLAN REPAIRED]"+originNode.getPartialStatesToRoot());
        }
        
        total_results.repair_total_time = System.nanoTime()-initialTime;
        
        if(originNode != null){
        	self_repair_origin = originNode;
			total_results.repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND;
			if(originNode.isSuperSet() && plan_repaired.size()>1){
	        	self_repair_origin = originNode.getSubSet();
				total_results.repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND_ORIGIN_IS_SUPERSET;
				return plan_repaired.get(1);
			}
			return plan_repaired.get(0);
        }
        
        return null;
	}
}
