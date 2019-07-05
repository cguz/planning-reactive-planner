package pelea.marp.service.rp.reactive.search_spaces;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import pelea.atpa.common.enums.Enums.APPROACH;
import pelea.marp.common.rp.planning_task.PlanningProblemRP;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.exceptions.NoInstantiatedActionException;
import pelea.marp.common.rp.reactive.exceptions.NullActionException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByState;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.reactive.trie.TrieStruct;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.enums.T_SORT;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlan;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import pelea.planning_task.service.plan.DPlanAction;
import tools.files.Files;

/**
 * @author cguzman
 * implements a search space by state
 */
public class SearchSpaceByStateImp extends SearchSpaceAbstractImp implements SearchSpaceByState { 

	
	//debug for this class
	private boolean debug = false;
	
	
	protected ArrayList<Integer> 	plan_remaining;
	
	
	/**
	 * Describes the stopping rule 1.), they are 
	 * the relevant variables that needs to be considered
	 * in a generated goal-state.
	 * */
	Hashtable<DSVariables, String> 	relevant_variables;
	private boolean 				goals_as_relevant 	= false;
	
	
	
	// variables for generating repairing structures self process
	private int WINDOW_FROM_TIME_INSTANT = 0;
	
	

	// Estimating the time to generate the structure 
	private EstimatedTime 			best_size			= null;
	
	

	private boolean			 		just_pre_in_first_ps= false;



	
	
	/**
	 * 02/10/2013
	 * Initializing structure and creating the basic structure
	 * 
	 * @param planning_problem		planning task model
	 * @param root					root node as a goal state
	 * @param remaining_plan 
	 * @param r_settings 			settings of the R application
	 * @param path_eval_benchmarks  path to save the benchmarks
	 * @param goals_as_relevant 
	 */
	public SearchSpaceByStateImp(PlanningProblem planning_problem, PS root, List<DSPlanAction> sub_plan, List<DSPlanAction> sub_plan_remaining, GeneralSettings settings, String path_eval_benchmarks, boolean goals_as_relevant) {
		super(planning_problem,settings,path_eval_benchmarks);

		int size = sub_plan.size();
		
		this.goals_as_relevant = goals_as_relevant;
		
		// creating the debug file
		if(debug_eval) eval = new Files("./results/"+planning_problem.getDomainName()+"/eval-"+planning_problem.getDomainName()+"-"+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)this.settings).getTranslatingApproach()), true);
		
		// saving the root node
		root_node = new NodeStateImp(root, planning_problem, true);
		
		// saving the associated plan
		plan = new ArrayList<Integer>(size);
		for (int i=0; i < size; ++i){
			// if it is a dummy action
			if(!(sub_plan.get(i) instanceof DPlanAction)){
				try {plan.add(sub_plan.get(i).getIndexGroundingAction()); } catch (Exception e) {}
			}else 
				plan.add(-1);
		}
		
		// saving the remaining plan
		plan_remaining = new ArrayList<Integer>(sub_plan_remaining.size());
		for (int i=0; i < sub_plan_remaining.size(); ++i){
			// if it is a dummy action
			if(!(sub_plan_remaining.get(i) instanceof DPlanAction)){
				try {plan_remaining.add(sub_plan_remaining.get(i).getIndexGroundingAction()); } catch (Exception e) {}
			}else 
				plan_remaining.add(-1);
		}
		
		if(debug)
			System.out.println("[SearchSpaceByStateImp] debug activated.");
	}
	






	// translating - just to test the generated partial states * TEST 
	@Override
	public void translate_plan_as_partial_states(int window) { 
		
		translating(window, ((SingleSettings)this.settings).getTranslatingApproach());
		
	}

	// JUST TO TEST 
	@Override
	public void translating(int window, SETTINGS_REACTIVESTRUCTURE approach) {
		DSPlan plan_action;
		HashMap<Integer, PS> goal_state;
		switch(approach){
			case RP_FORWARD_APPROACH: 				
				forward_partial_states(0, window);
				break;			
			default: case RP_REGRESSED_APPROACH: 
				// get the current plan
				plan_action = planning_problem.getBestPlan();
				plan_action.sort(T_SORT.MENOR_MAYOR, T_SORT.BURBUJA);
				
				goal_state = planning_problem.regress_plan(new PSImp((ArrayList<DSCondition>) basic_states.get(window).getRPS().getFluents(), null),plan_action.getActionPlan());
				
				basic_states = new ArrayList<NodeState>(goal_state.keySet().size());
				for (int i=0; i <= window;++i){
					basic_states.add(i, new NodeStateImp((PS)goal_state.get(i), planning_problem, true));
					try {plan.add(goal_state.get(i).getOperator().get(0).getIndexGroundingAction()); } catch (Exception e) {}
				}
				break;
		}
	}

	@Override
	public List<NodeState> get_plan_as_partial_states() {
		
		return basic_states = new SearchSpaceDummy(root_node, planning_problem).regress_plan_node_integer(plan);
		
	}
	
	@Override
	public List<SharedPartialState> get_ps_to_publicize() {
		
		return get_ps_to_publicize(WINDOW_FROM_TIME_INSTANT, true);
		
	}
	
	
	
	
	
	
	// JUST TO TEST
	@Override
	public void forward_partial_states(int from, int plan_window) {

		PS G_i=null;
		DSPlanAction op=null;
		Integer index_act;
		ArrayList<DSPlanAction> plan_ds = planning_problem.getBestPlan().getActionPlan();
		int first_ps = basic_states.size();
		
		int last_ps = from + plan_window;
		
		// if last partial state 
		if(last_ps == plan_ds.size()+1)
			return;
		
		String warning_t = "["+planning_problem.getAgentName()+"][Warning] there is not more action in the plan";
		for(int j=first_ps;j<=last_ps;++j){
			
				if(j==0 || just_pre_in_first_ps){// first partial state = initial state

					just_pre_in_first_ps = false;
					
					// get a given action a_j in the plan
					op = get_action(j, plan_ds, warning_t);
					
					// creating the first partial state - dummy action
					try {
						G_i = forward_create_initial_partial_state(j, op);
					} catch (NoInstantiatedActionException | NullActionException e) {e.printStackTrace();}
				
				}else{ // for other partial states
					
					// get a given action a_i in the plan
					op = get_action(j-1, plan_ds, warning_t);
					
					// applying the forward function
					G_i = forward_create_partial_state(j, op, plan_ds, warning_t);
				}
			
			((PSImp)G_i).state = j;
			((PSImp)G_i).time_instant = j;
			basic_states.add(new NodeStateImp(G_i, planning_problem, true));
			try {
				index_act = G_i.getOperator().get(0).getIndexGroundingAction();
				if(index_act==null)
					throw new NoInstantiatedActionException("["+planning_problem.getAgentName()+"][Error] the action "+G_i.getOperator().get(0).toString()+" is not instantiated !");
				plan.add(index_act); 
			} catch (Exception e) {}
		}
		
		// if it is the final partial state and the last action was a dummy action, we change the final state with the goal state.
		forward_change_final_state(last_ps, plan_ds);
	}

	/**
	 * change the final partial state for the global goals if the last action is a dummy action
	 * @param last_ps last index partial state
	 * @param plan_ds the plan execution
	 * JUST TO TEST
	 */
	private void forward_change_final_state(int last_ps, ArrayList<DSPlanAction> plan_ds) {
		if(last_ps == plan_ds.size()){
			if(get_action(last_ps-1, plan_ds, "[Error]") instanceof DPlanAction){
				((PSImp)basic_states.get(basic_states.size()-1).getRPS()).setOwnGoalState(planning_problem.getGlobalGoalState());
			}
		}
	}

	/**
	 * return a given action in the current plan
	 * @param act_index action index in the plan
	 * @param plan_ds current plan
	 * @param warning_t custom message if the action does not exist
	 * @return a given action in the plan
	 */
	private DSPlanAction get_action(int act_index, ArrayList<DSPlanAction> plan_ds,	String warning_t) {

		DSPlanAction op = null;
		try { op = plan_ds.get(act_index);
		} catch (Exception e) { System.out.println(warning_t ); 
		}
		
		return op;
	}

	/**
	 * the next partial state in the forward approach
	 * @param time_index of the plan
	 * @param act action of the plan execution
	 * @param plan_ds current plan 
	 * @param warning_t custom message if the action does not exist
	 * @return a partial state
	 * JUST TO TEST
	 */
	private PS forward_create_partial_state(int time_index, DSPlanAction act, ArrayList<DSPlanAction> plan_ds, String warning_t) {
		
		PS G_i;
		
		G_i = forward(basic_states.get(time_index-1).getRPS(), act);
		G_i.getOperator().clear();
		try {
			act = plan_ds.get(time_index);
			G_i.getOperator().add(act);
		} catch (Exception ef) { System.out.println(warning_t); 
		}
		
		return G_i;
		
	}

	/**
	 * the intial partial state G_0
	 * @param time_index initial of the plan
	 * @param act  first action of the plan execution
	 * @return a partial state 
	 * @throws NoInstantiatedActionException 
	 * @throws NullActionException 
	 * JUST TO TEST
	 */
	private PS forward_create_initial_partial_state(int time_index, DSPlanAction act) throws NoInstantiatedActionException, NullActionException {
		PS G_i;
		Integer index_act;
		
		// if it is a dummy action, we create an empty partial state
		if(act != null && act instanceof DPlanAction)
			G_i = new PSImp(new ArrayList<DSCondition>(), null,
				new ArrayList<DSPlanAction>(Arrays.asList(new DSPlanAction[] {act})), time_index, act.getStartTime(), false);
		else {
			if(act == null)
				throw new NullActionException("["+planning_problem.getAgentName()+"][Error] the action is null - time index ["+time_index);
			
			index_act = act.getIndexGroundingAction();
			if(index_act==null)
				throw new NoInstantiatedActionException("["+planning_problem.getAgentName()+"][Error] the action "+act.toString()+" is not instantiated !");
			
			G_i= new PSImp(new ArrayList<DSCondition>(planning_problem.getActions().get(index_act).getConditions()), 
				null,new ArrayList<DSPlanAction>(Arrays.asList(new DSPlanAction[] {act})), time_index, act.getStartTime(), false);
		}
		
		return G_i;
		
	}






	/**
	 * applying the forward in the partial state
	 * @param G a partial state where we have to apply the action
	 * @param act action to supports. the action may be a dummy action
	 * @return a forward partial state
	 * JUST TO TEST
	 */
	private PS forward(PS G, DSPlanAction act) {
		APPROACH app_pos_effs;
		
		// if it is a dummy action, we just duplicate the partial state
		if(act instanceof DPlanAction)
			return new PSImp((ArrayList<DSCondition>)G.getOwnFluents(),(ArrayList<DSCondition>)G.getOtherFluents(),G.getOperator(),G.getState(),G.getTimeInstant(), G.containsFictitious());
		
		app_pos_effs = (((SingleSettings)this.settings).getFilteringRootNodeEffects() == SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_POS_EFFECTS)?APPROACH.APP_POS_EFFECTS:APPROACH.APP_ALL_EFFECTS;
		
		// applying the forward
		G = planning_problem.forward(planning_problem.getActions().get(act.getIndexGroundingAction()), G, app_pos_effs);			
		
		return G;
	}
	

	@Override
	public EstimatedTime get_estimated_size(){
		return best_size;
	}
	
	
	
	
	
	
	// calculating reactive structure 
	@Override
	public void calculating(EstimatedTime best_setting) { 

		initial_time = System.nanoTime();
		
		if(best_size==null)
			best_size = best_setting;
		
		if(((SingleSettings)settings).single_repair_activated()==SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE){
			// default values and saving the root node
			default_values();
			
			DEPTH_MAX = best_size.getDepth();
			
			if(debug)
				System.out.println("["+planning_problem.getAgentName()+"][RP] single-repairing is deactivated. We don't generate the own tree.");
		}else
			calculating(best_size.getDepth());
		
	}

	protected boolean exceeds_time() {
		long   elapsed_time 	= System.nanoTime()-initial_time;
		double sec_elapsed_time = (double)elapsed_time / 1000000000.0;
		
		double sec_exe_cycle = best_size.getPlanningHorizon();
		
		return (sec_elapsed_time>=sec_exe_cycle);
	}
	
	
	protected void saving_root_node() {
		
		root_node.setIndex(t_states);
		s_states.put(root_node, root_node);
		s_statesIndex.put(root_node.getIndex(), root_node);

		// getting the relevant variables
		relevant_variables = getRelevantVariables(goals_as_relevant);
		
		if (root_var == null) { 
			root_var = new TrieStruct(planning_problem, settings);
			root_var.insert(root_node);
		}
	}
	
	protected void startingStatistic() { 
		
		if(debug_eval) { 
			eval.write("no_iteration");
			eval.write("\tdomain");
			// System.out.print("\t"+rootNode.getIndex());
			eval.write("\tms");
			//eval.write("\test-GSs");
			eval.write("\tGSs");
			eval.write("\tleaf-states");
			eval.write("\ttrie");
			eval.write("\trVar");//+relevantVariables.toString());
			eval.write("\tdomain-rVar");//+relevantVariables.toString());
			eval.write("\troot-var");//+nodeState.toPddl());
			//eval.write("\test-prod");
			//eval.write("\test-prod-uni");
			eval.write("\tw");
			eval.write("\tl");
			//eval.write("\t"+ramificationFactor() + " ram_fact");
			//eval.write("\t"+getNumNewPontentialNodes2() + " est2 nodes");
			//eval.write("\t"+String.valueOf(cleverMethod(nodeState)) + " est clever");
			eval.write("\n");
		}
		
		if(debug_eval_ad) { 
			
			eval_additional = new Files("./results/"+planning_problem.getDomainName()+"/infoAditional-"+planning_problem.getDomainName()+"-"+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach()), true);
			eval_additional.write("\nWindow = "+best_size.getPlanningHorizon());
			eval_additional.write("\nDepth = "+DEPTH_MAX);
			
		}
		
		if(debug_evalan) {
			eval_training = new Files(path_eval_benchmarks+"/training-"+planning_problem.getDomainName()+"-"
						+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach()), true);
		
		}
	}
	
	protected void defaultStatistic() {		
		if(debug_evalan){
			String path = path_eval_benchmarks+"/training-"+planning_problem.getDomainName()+"-"
					+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach());
			eval_training = new Files();
			if(!eval_training.fileExist(path)) {
			
				eval_training = new Files(path, true);
	
				eval_training.write("domain");
				eval_training.write("\tw"); // w
				eval_training.write("\td"); // d
				eval_training.write("\tpfile");
				eval_training.write("\trealT"); // ms
				eval_training.write("\tN");// GSs
				eval_training.write("\trVar");//+relevantVariables.toString()); 
				eval_training.write("\tDrVar");// domain value in rVar
				eval_training.write("\tfluentRoot");//+nodeState.toPddl());+" root var"
				eval_training.write("\trVarRoot");//  rVar in root
				eval_training.write("\tProductor");//  productors
				eval_training.write("\tUProductor");//  unique productors
				eval_training.write("\ttrie"); // tries
				if(settings.getTypeExecution() != SETTINGS_REACTIVESTRUCTURE.RP_TE_CREATING_DATASET_BENCHMARK_MODE){
					eval_training.write("\tTtoEst"); // estimated time to generate the gts
					eval_training.write("\testT"); // estimated time to generate the gts
				}
				eval_training.write("\tEN"); // evaluated nodes
				eval_training.write("\n");
				eval_training.close();
			}		
		}
	}

	private Hashtable<DSVariables, String> getRelevantVariables(boolean global_goals_as_relevant) {

		// getting the relevant actions
		List<Integer> relevantActions = get_relevant_actions(plan);
		
		if(((SingleSettings)this.settings).getJournal() == SETTINGS_REACTIVESTRUCTURE.JOURNAL_ICAE13){
			return getRelevantVariablesICAE(root_node, global_goals_as_relevant, relevantActions);
		} 
		
		return getRelevantVariablesOthers(root_node, global_goals_as_relevant, relevantActions);
		
	}
	
	private Hashtable<DSVariables, String> getRelevantVariablesICAE(NodeState root_node, boolean global_goals_as_relevant, List<Integer> relevantActions) {

		Hashtable<DSVariables, String> relevantVariables = new Hashtable<DSVariables, String>(10);
		
		DSAction act;
		for(Integer i: relevantActions){
			act = this.planning_problem.getActions().get(i);
			for(DSCondition cond : act.getConditions()){
				if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
					if(!relevantVariables.containsKey(cond.getFunction())){
						relevantVariables.put(cond.getFunction(), cond.getValue());
					}
				}
			}
		}
		
		// keeping goals as relevant variables
		if(global_goals_as_relevant){
			boolean found=false;
			for(DSCondition goals : root_node.getGoalState()){
				found=false;
				if (goals.getTime() == DSCondition.TT_AT_START || goals.getTime() == DSCondition.TT_OVER_ALL || 
						goals.getTime() == DSCondition.TT_NONE){
					for(DSCondition cond : planning_problem.getGlobalGoalState()){
							if(cond.equals(goals))
							{found=true;break;}
					}
					if(found && !relevantVariables.containsKey(goals.getFunction())){
						relevantVariables.put(goals.getFunction(), goals.getValue());
					}
				}
			}
		}
		
		// special case: keeping all fluent in the last partial state as relevant by a fictitious action
		if(global_goals_as_relevant){
			for(DSCondition cond : root_node.getGoalState()){
				if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
					if(!relevantVariables.containsKey(cond.getFunction())){
						relevantVariables.put(cond.getFunction(), cond.getValue());
					}
				}
			}
		}
		
		if(debug){
			System.out.println("\n[ICAE-RELEVANT-ACTIONS]");
			for(int i = 0; i < relevantActions.size(); ++i) 
				System.out.println(this.planning_problem.getActions().get(relevantActions.get(i)));
		}
		// System.out.println(relevantVariables.toString());
		
		return relevantVariables;
	}
	
	private Hashtable<DSVariables, String> getRelevantVariablesOthers(NodeState root_node, boolean global_goals_as_relevant, List<Integer> relevantActions) {

		Hashtable<DSVariables, String> relevantVariables = new Hashtable<DSVariables, String>(10);

		DSAction act;
		
		// new approach, relaxed model: keeping the preconditions and effects
		for(int i = 0; i < relevantActions.size(); ++i) { 
			
			act = this.planning_problem.getActions().get(relevantActions.get(i));
			// keeping the preconditions
			for(DSCondition cond : act.getConditions()){
				if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
					if(!relevantVariables.containsKey(cond.getFunction())){
						relevantVariables.put(cond.getFunction(), cond.getValue());
					}
				}
			}
			
			if(i <= relevantActions.size()-1) {
				// keeping the effects
				for(DSCondition effe : act.getEffects()){
					if (effe.getTime() == DSCondition.TT_AT_START || effe.getTime() == DSCondition.TT_OVER_ALL || 
							effe.getTime() == DSCondition.TT_NONE){
						if(!relevantVariables.containsKey(effe.getFunction())){
							if(((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_POS_EFFECTS){
								if(!"false".equals(effe.getValueExpression().toString().toLowerCase())
									&&!"not".equals(effe.getValueExpression().toString().toLowerCase())){
									relevantVariables.put(effe.getFunction(), effe.getValue());
								}
							} else 
								relevantVariables.put(effe.getFunction(), effe.getValue());
						}
					}
				}
			}
		}
		
		// special case: keeping all fluent in the last partial state as relevant by a fictitious action
 		if(global_goals_as_relevant){
			for(DSCondition cond : root_node.getGoalState()){
				if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
					if(!relevantVariables.containsKey(cond.getFunction())){
						relevantVariables.put(cond.getFunction(), cond.getValue());
					}
				}
			}
		}
		
		//this.printAction(relevantActions);
		// System.out.println(relevantVariables.toString());
		
		return relevantVariables;
	}

	/**
	 * return the relevant variables from index_from to the planning window
	 * @param associated_plan
	 * @return
	 */
	private List<Integer> get_relevant_actions(List<Integer> associated_plan) {
		
		List<Integer> relevantActions = new ArrayList<Integer>();
		
		for(Integer i: associated_plan) { 
			if(i != -1) { // if it is not a dummy action 
				relevantActions.add(i);
			}
		}
		
		return relevantActions;
	}




	protected void statistics(long endTime, NodeState nodeState, int evaluatedNodes) {
		
		int domain = getDomainValue(relevant_variables);
		
		if(debug_eval){
			eval.write(""+no_iteration);
			eval.write("\t"+this.planning_problem.getProblemName());
			eval.write("\t"+endTime);//  + " ms"
			eval.write("\t"+s_states.size()); //  + " GSs"
			eval.write("\t"+leaf_nodes.size()); //  + " leaf-states"
			eval.write("\t"+root_var.getTotalTrieNodes()); //  + " trie-nodes"
			eval.write("\t"+relevant_variables.size());//+relevantVariables.toString()); +" relevant var"
			eval.write("\t"+domain);//+relevantVariables.toString()); +" value rel var"
			eval.write("\t"+nodeState.getGoalState().size());//+nodeState.toPddl()); +" root var"
			eval.write("\t"+best_size.getPlanningHorizon()); //  + " w"
			eval.write("\t"+DEPTH_MAX); //  + " l"
			eval.write("\n");
		}
		
		
		int total = 0;
		HashSet<Integer> productors = new HashSet<Integer>(); 
		for(DSVariables var : relevant_variables.keySet()){
			total += var.getProducers();
			productors.addAll(planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var)));
		}

		if(debug_evalan){
			eval_training.write(""+this.planning_problem.getDomainName());
			eval_training.write("\t"+best_size.getPlanningHorizon()); // w
			eval_training.write("\t"+DEPTH_MAX); // l
			eval_training.write("\t"+this.planning_problem.getProblemName().replaceAll("pfile", ""));
			eval_training.write("\t"+endTime); // ms
			eval_training.write("\t"+s_states.size());// GSs
			eval_training.write("\t"+relevant_variables.size());//+relevantVariables.toString()); 
			eval_training.write("\t"+domain);//+relevantVariables.toString());+" domain value rVar"
			eval_training.write("\t"+nodeState.getGoalState().size());//+nodeState.toPddl());+" root var"
			eval_training.write("\t"+filterRelevantVar(nodeState.getGoalState()).size());//+nodeState.toPddl());+" rVar in root"
			eval_training.write("\t"+total);// number of productors
			eval_training.write("\t"+productors.size());// number of productors uniques
			eval_training.write("\t"+root_var.getTotalTrieNodes());//  + " trie-nodes"
			if(settings.getTypeExecution() != SETTINGS_REACTIVESTRUCTURE.RP_TE_CREATING_DATASET_BENCHMARK_MODE){
				eval_training.write("\t"+best_size.getTimeFindingBestSize()); // estimated time to generate the gts
				eval_training.write("\t"+best_size.getEstimatedTime()); // estimated time to generate the gts
			}
			eval_training.write("\t"+evaluatedNodes); // evaluated nodes
			//eval_analysis.write("\t"+(bestWindow.getEstimatedTime()/0.767433)); // estimated nodes to generate the gts
			eval_training.write("\n");
		}

		// saving total results of generating the search tree
		total_results.generating_total_nodes=s_states.size();
		total_results.generating_relevant_vars = relevant_variables.size();
		total_results.generating_relevant_vars_domain = domain;
		total_results.generating_leaf_nodes = leaf_nodes.size();
		total_results.generating_total_trie_nodes = root_var.getTotalTrieNodes();
		total_results.generating_evaluated_nodes = evaluatedNodes;	
		total_results.generating_total_time = endTime;
	}
	
	protected List<Integer> getRelevantActionsTo(NodeState g) {
		
		List<Integer> 	l_a = new ArrayList<Integer>();
		DSAction 		act = null;
		
		SortedSet<Integer> t = new TreeSet<Integer>();
		for (DSCondition var:g.getGoalState()) { 
			if(relevant_variables.containsKey(var.getFunction())) { 
				t.addAll(planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var.getFunction())));
			}
			//if(var.toPddl21().equals("(have_soil_analysis rover0 waypoint2 yes)"))
			//	t.addAll(planningProblem.getHashProducersVar().get(planningProblem.getVariablesIndex().get(var.getFunction())));
			
		}

		while(!t.isEmpty()){
			int i = t.first();
			// System.out.println(i+":"+this.planningProblem.getActions().get(i));
			t.remove(i);
			act = planning_problem.getActions().get(i);
			if(g.supports_regression(act.getEffects(),act.getConditions()))
				l_a.add(i);
		}
		
		t = null;
		
		return l_a;
	}

	private int getDomainValue(Hashtable<DSVariables, String> relevantVariables) {
		int total = 0;
		for(DSVariables var : relevantVariables.keySet()){
			total += (((PlanningProblemRP)planning_problem).getVariablesHelpful().get(planning_problem.getVariablesIndex().get(var)).size());
		}
		return total;
	}
	
	
	
	
	
	
	
	
	// self-repair
	@Override
	public ArrayList<Integer> getPlan(ArrayList<DSCondition> S, int time_instant_target) {
		long initialTime = System.nanoTime();
		
		NodeState targetNode = null;
        NodeState originNode = null;
        PSImp origin = null;
        
        ArrayList<ArrayList<Integer>> 	plan_repaired 	= new ArrayList<ArrayList<Integer>>();		
        
        // searching the target state
        ArrayList<DSCondition> state = find_basic_state(time_instant_target);
        targetNode = get_target_node(state, S);
		if(targetNode == null){
			total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_TARGET;	
			time_instant_target = (int) basic_states.size()-1; // altamente probable que vengamos de multi-repair
            targetNode = get_target_node(root_node.getGoalState(), S); // to the root node
        }
		
		// changing the value of the state variables in the goal state with the failure value, return the preconditions and effects of the next action as a goal state 
		origin = (PSImp) changingCurrentValuesPre(S, filter_next_action(targetNode.getGoalState(), time_instant_target));
        
    	originNode = getOriginNode((ArrayList<DSCondition>) origin.getFluents(), targetNode, S, null);
        if(originNode == null){
        	if(total_results.repair_error == SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_TARGET)
        		total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_TARGET_AND_ORIGIN; 
        	else
        		total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_N_FOUND_ORIGIN;
        }else{
        	plan_repaired = get_paths(originNode, targetNode);	

        	if(debug){
	        	System.out.println("\n[PLAN-REPAIRED][TO-ROOT] as actions: "+plan_repaired);
		        System.out.println("[PLAN-REPAIRED][TO-ROOT] as states: "+originNode.getPartialStatesToRoot());
        	}
	        
        }
        
        total_results.repair_total_time = System.nanoTime()-initialTime;
        
        if(originNode != null){
        	self_repair_origin = originNode;
			total_results.repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND;
			if(originNode.isSuperSet() && plan_repaired.size()>1){
	        	self_repair_origin = originNode.getSubSet();
				total_results.repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND_ORIGIN_IS_SUPERSET;
				plan = plan_repaired.get(1);
			}
			plan = plan_repaired.get(0);
			return plan;
        }
        
        return null;
	}	
	
	@Override
	public List<Integer> get_plan_remaining() {
		return plan_remaining;
	}


	@Override
	public void reordering_other(int window_from, List<NodeState> ps) {
		
    	List<NodeState> partial_state=new ArrayList<NodeState>();
    	int temp, size;
    	
		// saving the partial states from the previous structures
    	for(int i = 0; i < window_from;++i)
    		partial_state.add(ps.get(i));
    	
    	temp = window_from;
    	// adding the partial states of this search space and ordering the time from the window_from until the final
    	for(int i = WINDOW_FROM_TIME_INSTANT; i < basic_states.size();++i){
    		partial_state.add(basic_states.get(i));
    		
    		size = partial_state.size()-1;
    		
    		((PSImp)partial_state.get(size).getRPS()).time_instant=temp;
    		((PSImp)partial_state.get(size).getRPS()).state=size;
    		
    		temp += (int)(partial_state.get(size).getDurationOutOperator());
    	}

    	WINDOW_FROM_TIME_INSTANT = window_from; 
    	
    	// saving the new partial state
    	basic_states = partial_state;
    	
    	// INDEX_WINDOW_FROM = findingIndexBasicState(WINDOW_FROM_TIME_INSTANT); // index window from
		
	}
	
	// helpfull methods	
	private ArrayList<DSCondition> find_basic_state(int time) {
		
		if(basic_states.size()==0 || basic_states.size() <= time)
			return null;
		
		return (ArrayList<DSCondition>) basic_states.get(time).getRPS().getFluents();
		
	}
	
	@Override
	public void printGraph() {	
		String filename = Double.toString(System.currentTimeMillis());
		
		filename = "searchSpaceByState-"+this.planning_problem.getDomainName()+"-"+this.planning_problem.getProblemName()
			+"-"+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach())+"-w"+best_size.getPlanningHorizon()+"-d"+DEPTH_MAX
			+"-"+filename.substring(filename.length()-5, filename.length());
		
		String path = settings.getPathToPrint()+"search-space/";

		File f = new File(path);
		
		if(!f.exists())
			f.mkdirs();
		
		Files files = new Files(path+filename, false);
				
		files.write("digraph workingcomputer {\n");	
		files.write("	rankdir=BT;\n");
		files.write("	size=\"8,5\"\n");   
		files.write("	node [shape=doublecircle];s0;\n");
		files.write("	node [shape=circle]\n");
		
		files.write("rVar[label=\""+relevant_variables.toString().replaceAll(", ", "\\\\n").replaceAll("\\{", "").replaceAll("\\}", "")+"\\nrVar\"]\n");
		
		int size = t_states;
		for(int i=0;i<=size;++i){
			NodeState s = s_statesIndex.get(i); 
			if(s != null){
			String subSet ="";
			if (s.isSuperSet()){
				int index = s.getSubSet().getIndex();
				if (index!=-1)
					subSet = "-G"+index;
			}
			
			if (this.equals(s))
				files.write("s"+s.getIndex()+"[	 			color=\"red\" label=\""+s.toPddl().replaceAll(", ", "\\\\n").replaceAll("\\[", "").replaceAll("\\]", "")+"\\nG"+s.getIndex()+subSet+"\"]\n");
			else
				files.write("s"+s.getIndex()+"[	 			label=\""+s.toPddl().replaceAll(", ", "\\\\n").replaceAll("\\[", "").replaceAll("\\]", "")+"\\nG"+s.getIndex()+subSet+"\"]\n");
			}
		}
		
		size = t_states;
		for(int i=0;i<=size;++i){
			NodeState state = s_statesIndex.get(i);
			if(state != null){
				//System.out.println(state.getIndex());
				for (NodeState state1: state.getOutComming().keySet()){
					
					if (state1.getOutComming().containsKey(state)){
						if (state1.getIndex()>state.getIndex())
							files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+this.planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\" dir=\"both\"];\n");
					}else
						files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+this.planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\"];\n");
					
				}
			}
		}
		
		files.write("}");
		files.close();
		
		String temp = "cd "+path+" \n";
		temp = temp + "dot -Tpdf "+filename+" -O \n";
		executeScript(temp);
		temp = "cd "+path+" \n";
		//temp = temp + "rm "+filename;
		executeScript(temp);
		
	}
	
	
	
	
	
	
	// others 
	
	@Override
	public void setWindowFrom(int window) {
		this.WINDOW_FROM_TIME_INSTANT = window;
	}
	
	@Override
	public NodeState getLastGState(){
		return basic_states.get(basic_states.size()-1);
	}
	
	@Override
	public NodeState get_root(){
		return root_node;
	}
	
	@Override
	public ArrayList<DSCondition> filterRelevantVar(ArrayList<DSCondition> goal_state) {
		
		ArrayList<DSCondition> goal_state_rVarIn = new ArrayList<DSCondition>(goal_state.size());
		for(DSCondition var : goal_state)
			if(this.relevant_variables.containsKey(var.getFunction()))
				goal_state_rVarIn.add(var);
		
		return goal_state_rVarIn;
	}
	
	public ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state) {
		
		Hashtable<DSVariables, String> rVar = getRelevantVariables(true);
		
		ArrayList<DSCondition> goal_state_rVarIn = new ArrayList<DSCondition>(rVar.size());
		for(DSCondition var : goal_state)
			if(rVar.containsKey(var.getFunction()))
				goal_state_rVarIn.add(var);
		
		return goal_state_rVarIn;
	}
	
	public ArrayList<DSCondition> filter_next_action(ArrayList<DSCondition> goal_state, int index_target) {
		
		// las variables que son precondiciones y efectos de la acción
		Hashtable<DSVariables, String> rVar = getRelevantVariables(true);
		
		ArrayList<DSCondition> goal_state_rVarIn = new ArrayList<DSCondition>(rVar.size());
		for(DSCondition var : goal_state)
			if(rVar.containsKey(var.getFunction()))
				goal_state_rVarIn.add(var);
		
		return goal_state_rVarIn;
	}

	@Override
	public Integer getRepairedStability() {
   		int stability=0;
		
		// original plan
		ArrayList<Integer> plan_ori_repairing_window = (ArrayList<Integer>) get_relevant_actions(plan);
		ArrayList<Integer> plan2; 
		
		if(self_repair_planToRoot.size()>1)
			plan2 = self_repair_planToRoot.get(1);
		plan2 = self_repair_planToRoot.get(0);
		
		// repaired actions
   		ArrayList<Integer> w_plan_repaired = new ArrayList<Integer>(plan2.size());
   		w_plan_repaired.addAll(plan2);
   		
   		/* calculating total of actions that are in the original plan */
   		for(Integer act : w_plan_repaired){
   			if(plan_ori_repairing_window.contains(act))
   				++stability;
   		}
		
		return stability;
	}

	@Override
	public Integer getRepairedStability(int index_from, int length_planning_window) {
   		int stability=0;
		
		// original plan
		ArrayList<Integer> plan_ori_repairing_window = (ArrayList<Integer>) get_relevant_actions(plan);
		ArrayList<Integer> plan2; 
		
		if(self_repair_planToRoot.size()>1)
			plan2 = self_repair_planToRoot.get(1);
		else
			plan2 = self_repair_planToRoot.get(0);
		
		// repaired actions
   		ArrayList<Integer> w_plan_repaired = new ArrayList<Integer>(plan2.size());
   		w_plan_repaired.addAll(plan2);
   		
   		/* calculating total of actions that are in the original plan */
   		for(Integer act : w_plan_repaired){
   			if(plan_ori_repairing_window.contains(act))
   				++stability;
   		}
		
		return stability;
	}













}
