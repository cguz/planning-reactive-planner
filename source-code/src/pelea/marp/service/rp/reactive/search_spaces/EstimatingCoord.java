package pelea.marp.service.rp.reactive.search_spaces;

import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.planning_task.common.PlanningProblem;

/**
 * @author cguzman
 * implements a search space by state
 */
public class EstimatingCoord extends Estimating { 


	/**
	 * 02/10/2013
	 * constructor of the estimating process
	 * 
	 * @param planningProblem		planning task model
	 * @param r_settings 			settings of the r application
	 */
	public EstimatingCoord(PlanningProblem planningProblem,  GeneralSettings set) {
		super(planningProblem, set);
	}

	/**
	 * the intial partial state G_0
	 * @param time_index initial of the plan
	 * @param act  first action of the plan execution
	 * @return a partial state 
	 * @throws NoInstantiatedActionException 
	 * @throws NullActionException 
	protected PS forward_create_initial_partial_state(int time_index, DSPlanAction act) throws NoInstantiatedActionException, NullActionException {
		PS G_i;
		Integer index_act;
		
		// if it is a dummy action, we create an empty partial state
		if(act != null && ( act instanceof DPlanAction || act instanceof FPlanAction ) )
			G_i = new PSImp(new ArrayList<DSCondition>(), null,
				new ArrayList<DSPlanAction>(Arrays.asList(new DSPlanAction[] {act})), time_index, act.getStartTime(), false);
		else {
			if(act == null)
				throw new NullActionException("["+planning_problem.getAgentName()+"][Error] the action is null - time index ["+time_index);
			
			if(act instanceof GPlanAction){
				index_act = ((GAction)planning_problem.getActions().get(act.getIndexGroundingAction())).getIndex()[1];
			}else
				index_act = act.getIndexGroundingAction();
			
			if(index_act==null)
				throw new NoInstantiatedActionException("["+planning_problem.getAgentName()+"][Error] the action "+act.toString()+" is not instantiated !");
			
			G_i = new PSImp(new ArrayList<DSCondition>(planning_problem.getActions().get(index_act).getConditions()), 
				null,new ArrayList<DSPlanAction>(Arrays.asList(new DSPlanAction[] {act})), time_index, act.getStartTime(), false);
		}
		
		return G_i;
		
	}
	 */



	/**
	 * applying the forward in the partial state
	 * @param G a partial state where we have to apply the action
	 * @param act action to supports. the action may be a dummy action
	 * @return a forward partial state
	protected PS forward(PS G, DSPlanAction act) {
		APPROACH app_pos_effs;
		
		// if it is a dummy action, we just duplicate the partial state
		if(act instanceof DPlanAction || act instanceof FPlanAction)
			return new PSImp((ArrayList<DSCondition>)G.getOwnFluents(),(ArrayList<DSCondition>)G.getOtherFluents(),G.getOperator(),G.getState(),G.getTimeInstant(), G.containsFictitious());
		
		app_pos_effs = (((SingleSettings)this.settings).getFilteringRootNodeEffects() == SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_POS_EFFECTS)?APPROACH.APP_POS_EFFECTS:APPROACH.APP_ALL_EFFECTS;
		
		// applying the forward
		G = planning_problem.forward(planning_problem.getActions().get(act.getIndexGroundingAction()), G, app_pos_effs);			
		
		return G;
	}
	 */

	/**
	 * return the relevant variables from index_from to the planning window
	 * @param basic_states
	 * @param index_from  instant time from
	 * @param planning_window, length of the planning window
	 * @return
	protected List<Integer> get_relevant_actions(List<NodeState> basic_states, int index_from, int planning_window) {
		
		List<Integer> relevantActions = new ArrayList<Integer>();
		List<Integer> op = null;
		int i=-1;
		try {
			for(i = index_from;i <= index_from+planning_window; ++i) { 
				op = basic_states.get(i).getOperatorIndex();
				
				if(op.size() > 0 && op.get(0) != -1) { // if it is not a dummy action 
					
					DSAction act = planning_problem.getActions().get(op.get(0));
					
					if(!(act instanceof FAction)){
						if(act instanceof GAction){
							relevantActions.add(((GAction)act).getIndex()[1]);
						}else
							relevantActions.add(op.get(0));
					}
					
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("["+planning_problem.getAgentName()+"][Error] Possible action is not instantiated ("+((i!=-1)?i:"")+")");
		}
		
		return relevantActions;
	}
	 */

}
