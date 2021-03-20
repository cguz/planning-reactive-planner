package pelea.marp.service.rp.reactive.search_spaces;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import pelea.planning_task.service.plan.NPlanAction;

/**
 * @author cguzman
 * implements a search space to apply the merge
 */
public class SearchSpaceDummy extends SearchSpaceAbstractImp { 	
	

	/**
	 * @param depth maximum depth
	 * @param planningProblem
	 * @param settings
	 * @param path_eval_benchmarks 
	 * @param root_node
	 */
	public SearchSpaceDummy(NodeState root, PlanningProblem planning_problem) {
		super(planning_problem,null,null);
		
		root_node = new NodeStateImp(root.getRPS(), planning_problem, true);
		root_node.getRPS().getOperator().clear();
		
	}

	public List<NodeState> regress_plan_node(List<DSPlanAction> plan) {
		return regress_plan_node(root_node, plan);
	}

	private List<NodeState> regress_plan_node(NodeState root, List<DSPlanAction> plan) {
		
		List<NodeState> partial_states = new ArrayList<NodeState>((plan!=null)?plan.size()+1:0);
		
		if((plan!=null)){
			DSAction act;
			PS new_state;
			
			new_state = root.getRPS();
			partial_states.add(new NodeStateImp(new_state, planning_problem, true));
			
			int index;
			
			for(int i = plan.size()-1; i >= 0; --i) {
				
				index = plan.get(i).getIndexGroundingAction();
				
				// if it is not a dummy action
				if(index != -1){
					
					// we regress the partial state
					act = planning_problem.getActions().get(index);
					new_state = planning_problem.regress(new_state,act);
					
					((PSImp)new_state).time_instant=i;
					partial_states.add(0,new NodeStateImp(new_state,planning_problem, true));
					
					// we add the operator
					partial_states.get(0).getRPS().getOperator().clear();
					partial_states.get(0).getRPS().getOperator().add(plan.get(i));
					
				}else{  // if it is a dummy action, we save an empty state
					
					((PSImp)new_state).time_instant=i;
					partial_states.add(0, new NodeStateImp(new ArrayList<DSCondition>(), planning_problem));
					partial_states.get(0).getRPS().getOperator().clear();
					
				}
			}
		}
		
		return partial_states;
	}
	
	public List<NodeState> regress_plan_node_integer(List<Integer> plan) {
		return regress_plan_node_integer(root_node, plan);
	}

	private List<NodeState> regress_plan_node_integer(NodeState root, List<Integer> plan) {
		
		List<NodeState> partial_states = new ArrayList<NodeState>((plan!=null)?plan.size()+1:0);
		
		if((plan!=null)){
			DSAction act;
			PS new_state;
			
			new_state = root.getRPS();
			partial_states.add(new NodeStateImp(new_state, planning_problem, true));
			
			int index;
			
			for(int i = plan.size()-1; i >= 0; --i) {
				
				index = plan.get(i);
				
				// if it is not a dummy action
				if(index != -1){
					// we regress the partial state
					act = planning_problem.getActions().get(index);
					new_state = planning_problem.regress(new_state,act);
					
					((PSImp)new_state).time_instant=i;
					partial_states.add(0,new NodeStateImp(new_state,planning_problem, true));
					
					// we add the operator
					partial_states.get(0).getRPS().getOperator().clear();
					partial_states.get(0).getRPS().getOperator().add(new NPlanAction(act.toString(), (double)i, (double)1, i, index, 1, DSPlanAction.CURRENT_ONGOING));
					
				}else{ // if it is a dummy action, we save an empty state
					
					((PSImp)new_state).time_instant=i;
					partial_states.add(0, new NodeStateImp(new ArrayList<DSCondition>(), planning_problem));
					partial_states.get(0).getRPS().getOperator().clear();
					
				}
			}
		}
		
		return partial_states;
	}
	
	
}
