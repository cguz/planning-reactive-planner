package pelea.marp.service.rp.reactive.repairing_structures;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.repairing_structures.ManagerStructureSelfRepair;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByState;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.plan.DPlanAction;

public class ManagerStructureSelfRepairImp extends ManagerStructureAbstract implements ManagerStructureSelfRepair { 
	
	private boolean debug = false;
	
	
	// associated partial states or sub-plan for the own repairing structure
	private List<NodeState> sub_plan;
	
	// total of actions supported
	private int acts_supported;
	
	
	
	public ManagerStructureSelfRepairImp(SearchSpace _temp_search_space) { 
		super(_temp_search_space);
		if(debug)
			System.out.println("[ManagerStructureSelfRepairImp] debug is activated.");
	}

	public void saving() {
		
		sub_plan = ((SearchSpaceByState)search_tree).get_plan_as_partial_states();
		
		acts_supported = ((SearchSpaceByState)search_tree).get_estimated_size().getPlanningHorizon();

		if(debug)
		System.out.println("\n[SAVING][ACTS-SUPPORTED]: "+acts_supported+" [PLAN-AS-PS]: "+sub_plan.toString());
		
	}
	
	@Override
	public List<NodeState> reordering(ArrayList<Integer> plan) {
		List<NodeState> ps = null;
		
		((SearchSpaceByState)search_tree).get_estimated_size().setPlanningWindow(plan.size());
		ps = ((SearchSpaceByState)search_tree).get_plan_as_partial_states();
		
		//associated plan
		if(ps!=null){
			sub_plan = ps;
			acts_supported = get_plan_window();
		}
		
		return ps;
	}

	@Override
	public List<NodeState> get_associated_plan() {
		return sub_plan;
	}

	@Override // same as acts_supported
	public int get_plan_window() {
		return ((SearchSpaceByState)search_tree).get_estimated_size().getPlanningHorizon();
	}
	
	@Override
	public int get_total_act_supported() {
		return acts_supported;
	}
	
	/**
	 * @used adapting own plan to may change to the next structure
	 * @param acts_supported
	 */
	public void set_total_act_supported(int acts_supported) {
		this.acts_supported = acts_supported;
	}

	public void coord_set_actions_current_window(List<DSPlanAction> plan) {
		if(plan.size() != acts_supported)
			throw new ExceptionInInitializerError("[Error] the size of the fictitious plan is different from the supported actions.");
		
		for(int i = 0; i < acts_supported; ++i)
			if(!(plan.get(i) instanceof DPlanAction)){
				sub_plan.get(i).getRPS().getOperator().clear();
				sub_plan.get(i).getRPS().getOperator().add(plan.get(i));
			}

		if(debug)
			System.out.println("[SAVING][ACTS-SUPPORTED]: "+acts_supported+" [PLAN-AS-PS]: "+sub_plan.toString());
	}

	@Override
	public List<Integer> get_actions_current_window() {

		List<NodeState> l_nodes =  get_associated_plan();
		List<Integer> plan = new ArrayList<Integer>();
		List<Integer> op;
		
		for(int i=0;i<l_nodes.size()-1;++i){ 
			op = l_nodes.get(i).getOperatorIndex();	
			if(op.size() > 0)
				plan.addAll(op);
			else
				plan.add(-1);
		}
		
		return plan;
		
	}
	
	/*@Override
	public List<DSPlanAction> get_actions_plan_current_window() {

		List<NodeState> l_nodes =  get_associated_plan();
		List<DSPlanAction> plan = new ArrayList<DSPlanAction>();
		ArrayList<DSPlanAction> op;
		
		for(NodeState n : l_nodes) { 
			op = n.getOperator();
			if(op.size() > 0){
				plan.addAll(op);
			}else{
				plan.add(-1); // IF WE USED IT, WE NEED TO INSTANTIATE A DUMMY ACTION
				System.out.println("[PLAN-CURRENT-WINDOW] adding a dummy-action ... ¿ and final ?");
			}
		}
		
		return plan;
	}*/
}
