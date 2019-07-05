package pelea.marp.service.rp.reactive.search_spaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import pelea.atpa.common.enums.Enums.APPROACH;
import pelea.marp.common.r.RModel;
import pelea.marp.common.r.RSettings;
import pelea.marp.common.rp.planning_task.PlanningProblemRP;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.exceptions.NoInstantiatedActionException;
import pelea.marp.common.rp.reactive.exceptions.NullActionException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.helper.EstimatedTimeComparator;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import pelea.planning_task.service.plan.DPlanAction;
import pelea.planning_task.service.plan.FPlanAction;

/**
 * @author cguzman
 * implements a search space by state
 */
public class Estimating { 

	// debug for this class
	private boolean 				debug = false;
	

	public PlanningProblem 			planning_problem;
	List<NodeState> 				basic_states;
	
	protected GeneralSettings 		settings;
	
	
	
	/**
	 * Describes the stopping rule 1.), they are 
	 * the relevant variables that needs to be considered
	 * in a generated goal-state.
	 * */
	Hashtable<DSVariables, String> 	relevant_variables;
	
	
	
	// variables for generating repairing structures self process
	private int INDEX_WINDOW_FROM		 = 0;
	
	

	// Estimating the time to generate the structure 
	private long 					time_find_bestW = 0; // time to estimate the best window
	private EstimatedTime 			best_size		= null;
	private float[] 				conf_weights;
	

	private boolean			 		just_pre_in_first_ps=false;

	
	
	
	/**
	 * 02/10/2013
	 * constructor of the estimating process
	 * 
	 * @param planningProblem		planning task model
	 * @param r_settings 			settings of the r application
	 */
	public Estimating(PlanningProblem planningProblem,  GeneralSettings set) {
		settings			= set;

		planning_problem 	= planningProblem;
		
		if(debug)
			System.out.println("[Estimating] debug is activated.");
	}
	
	
	// estimating the size
	@SuppressWarnings("unchecked")
	public EstimatedTime estimating_setting(double time_limit, HashMap<Integer, PS> goal_state, List<DSPlanAction> plan) {

		int max_value_m = 11;
		int max_value_l = 6;
		int window_from = 0;
		int i = 0;
		
		// saving the current partial states
		basic_states = new ArrayList<NodeState>(goal_state.keySet().size());
		for (Integer index : goal_state.keySet()){
			basic_states.add(i, new NodeStateImp(goal_state.get(index), planning_problem, true));
			++i;
		}
		
		if(basic_states.size() == 0)
			just_pre_in_first_ps = true;
		
		INDEX_WINDOW_FROM 	= window_from;

		conf_weights		= ((SingleSettings)settings).getConfWeights();
		
		long 	 startTime 	= System.currentTimeMillis();

		Object[] parameters = null;

		double 	 est_time		= 0.0;
		double 	 last_est_time	= 0.0;
		boolean  max_depth		= false;
		boolean  end	 		= false;
		int 	 depth;
		int 	 total_plan		= plan.size();
		int		 plan_window	= starting_plan_window(window_from, plan); // coordinated - not coordinated
				
		PriorityQueue<EstimatedTime> times = new PriorityQueue<EstimatedTime>(10, new EstimatedTimeComparator());
		
		if(total_plan == 1) plan_window = total_plan;

		if(debug)
			System.out.println("\n["+planning_problem.getAgentName()+"][CP: "+((SingleSettings)settings).is_central_planner_enable()+"] estimates the best settings from: "+window_from+", plan window: "+plan_window+", time limit: "+time_limit+", total plan: "+total_plan);
		
		while (!end){
			
			if(((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_FORWARD_APPROACH)
				forward_partial_states(INDEX_WINDOW_FROM, plan_window, plan);
			
			if(((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH)
			 	filtering_partial_state(INDEX_WINDOW_FROM, plan_window);
			
			/* calculating the function parameters */
			parameters  = function_parameters(plan_window); 
				
			depth 		= plan_window;
			max_depth	= false;
			while(!max_depth){
				++depth;
				
				if(((Hashtable<DSVariables, String>)parameters[0]).size()>0){ // if there are relevant variables
					est_time = estimating_time(depth, parameters);
					
					if(((SingleSettings)settings).getDifferentTimeOption()==false){
						if(est_time >= time_limit 
						  || depth >= max_value_m 
								// || last_est_time == est_time
						  ){
							last_est_time=0.0;
							max_depth = true;
						}
						last_est_time=est_time;
					}else{
						if(est_time >= time_limit || 
						(Math.abs(last_est_time-est_time)/time_limit) >= conf_weights[11]){ 
							last_est_time = 0.0;
							max_depth = true;
						}
						last_est_time=est_time;
					}
					
					if(!max_depth || times.size()==0)
						times.add(new EstimatedTime(est_time, plan_window, depth, est_b, generated_nodes, evaluated_Nodes, parameters));
					
				}else{ // if there are not relevant variables, there are only dummy actions
					max_depth = true;
					times.add(new EstimatedTime(time_limit, plan_window, depth,-1,-1,-1, parameters)); // we don't create the search space
				}
				
			}
			if(plan_window == 1 || depth == (plan_window+1)) 
				end = true;
			else{
				if(plan_window < total_plan && plan_window <= max_value_l) {
					if(next_plan_window(window_from, plan_window))
						++plan_window;
					else end=true;
				} else 
					end = true;
			}
		}

		time_find_bestW = System.currentTimeMillis() - startTime;
		
		try {
			/*if(debug)
				System.out.println("\n["+planning_problem.getAgentName()+"][ESTIMATNG] "+times.toString());
			*/
			best_size = times.remove();
			
			if(((SingleSettings)settings).getMoreActions() == SETTINGS_REACTIVESTRUCTURE.RP_REPAIR_LESS_ACTIONS){
				if(times.size()>0)
					best_size = times.peek();
			}
			
		} catch (NoSuchElementException e) { // if it is empty
			if(debug)
				System.out.println("\n["+planning_problem.getAgentName()+"][estimating_setting][Warning] not possible setting, estimated time ("+est_time+") too big.");

			if(plan_window != 1) plan_window=2;
			
			parameters = function_parameters(plan_window); 
			best_size = new EstimatedTime(time_limit, plan_window, 3,-1,-1,-1, parameters);
		}
		
		best_size.setTimeFindingBestSize(time_find_bestW);
		
		return best_size;
	}

	protected int starting_plan_window(int from, List<DSPlanAction> plan) {
		
		// if it is not coordinated
		if(!((SingleSettings)settings).is_central_planner_enable())
			return 2;
		
		// if it is a coordinated
		return coord_starting_plan_window(from, plan); // one or the plan_window when there are just dummy actions
		
	}
	
	protected int coord_starting_plan_window(int from, List<DSPlanAction> plan) {
		int i;
		int size;
		int own_actions=0;
		int plan_window=0;
		
		size = plan.size();
		
		// starting from a specific time, forall actions in the plan
		for(i=from;i<size;++i){
			
			// increase the plan window
			++plan_window;
			
			// if it is not a dummy or a fictitious action, return plan_window if own actions is > 1
			if(!(plan.get(i) instanceof DPlanAction || plan.get(i) instanceof FPlanAction)){
			// if(!(plan.get(i) instanceof DPlanAction || plan.get(i) instanceof FPlanAction)){
				++own_actions;
				if(own_actions > 1)
					return plan_window;
			}else{// if it is a dummy action and we have at least one own action, we return the window
				if(own_actions > 0)
					return --plan_window;
			}
		}
		return plan_window;
	}


	protected boolean next_plan_window(int from, int plan_window) {
		
		// if it is not a central planner
		if(!((SingleSettings)settings).is_central_planner_enable())
			return true;
		
		// if it is a coordinated
		ArrayList<DSPlanAction> plan_ds = planning_problem.getBestPlan().getActionPlan();
	
		if(!(plan_ds.get(from+plan_window) instanceof DPlanAction))
			return true;
		
		return false;
	}





	public void filtering_partial_state(int index_from, int plan_window) {

		ArrayList<DSPlanAction> op;
		DSAction act;

		int index_to = index_from + plan_window;
		
		// if last partial state 
		if(index_to == (basic_states.size()-1) || index_to == -1)
			return;
		
		for(int i = index_from; i <= index_to; ++i) { 
			op = basic_states.get(i).getOperator();
			if(op.size() > 0) {  
				act = this.planning_problem.getActions().get(op.get(0).getIndexGroundingAction());
				
				// removing in the root node all fluents f that are in pre() of the actions in the window.
				// such as f is not in the next action to execute (true).
				for(DSCondition cond : act.getConditions()){
					if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
						basic_states.get(index_to).removeFluent(cond, true);
					}
				}
			}
		}
		//System.out.println("New state: "+basicStates.get(window_to).toPddl().replace(">, <", ">, \n<"));
	}
	
	public void forward_partial_states(int from, int plan_window, List<DSPlanAction> plan_ds) {

		PS G_i=null;
		DSPlanAction op=null;
		Integer index_act;
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
				
			} catch (Exception e) {}
		}
		
		// if it is the final partial state and the last action was a dummy action, we change the final state with the goal state.
		forward_change_final_state(last_ps, plan_ds);
	}

	/**
	 * change the final partial state for the global goals if the last action is a dummy action
	 * @param last_ps last index partial state
	 * @param plan_ds the plan execution
	 */
	private void forward_change_final_state(int last_ps, List<DSPlanAction> plan_ds) {
		if(last_ps == plan_ds.size()){
			/*if(get_action(last_ps-1, plan_ds, "[Error]") instanceof DummyPlanAction){
				((PSImp)basic_states.get(basic_states.size()-1).getRPS()).setOwnGoalState(planning_problem.getGlobalGoalState());
			}else{
				((PSImp)basic_states.get(basic_states.size()-1).getRPS()).setOwnGoalState(planning_problem.getGlobalGoalState());
			}*/

			if(planning_problem.getGlobalGoalState().size() > 0)
				((PSImp)basic_states.get(basic_states.size()-1).getRPS()).setOwnGoalState(planning_problem.getGlobalGoalState());
		}
	}

	/**
	 * return a given action in the current plan
	 * @param act_index action index in the plan
	 * @param plan_ds current plan
	 * @param warning_t custom message if the action does not exist
	 * @return a given action in the plan
	 */
	private DSPlanAction get_action(int act_index, List<DSPlanAction> plan_ds,	String warning_t) {

		DSPlanAction op = null;
		try { op = plan_ds.get(act_index);
		} catch (Exception e) {if(debug) System.out.println(warning_t ); }
		
		return op;
	}

	/**
	 * the next partial state in the forward approach
	 * @param time_index of the plan
	 * @param act action of the plan execution
	 * @param plan_ds current plan 
	 * @param warning_t custom message if the action does not exist
	 * @return a partial state
	 */
	private PS forward_create_partial_state(int time_index, DSPlanAction act, List<DSPlanAction> plan_ds, String warning_t) {
		
		PS G_i;
		
		G_i = forward(basic_states.get(time_index-1).getRPS(), act);
		G_i.getOperator().clear();
		try {
			act = plan_ds.get(time_index);
			G_i.getOperator().add(act);
		} catch (Exception ef) { if(debug)System.out.println(warning_t); }
		
		return G_i;
		
	}

	/**
	 * the intial partial state G_0
	 * @param time_index initial of the plan
	 * @param act  first action of the plan execution
	 * @return a partial state 
	 * @throws NoInstantiatedActionException 
	 * @throws NullActionException 
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
	
	private Object[] function_parameters(int plan_window) {
		NodeState 				root=null;
		Hashtable<DSVariables, String> relevantVar=null;
		ArrayList<DSCondition> 	relevantRootVar=null;
		HashSet<Integer> 		productorsUnique = new HashSet<Integer>(); 
		int 					totalProductors=0;
		int 					domainRVar = 0;
		int 					index_to = INDEX_WINDOW_FROM+plan_window;
		
		Object[] 				parameters =new Object[6];
		
		// getting variables of the root node
		root = basic_states.get(index_to);
		
		// getting the relevant variables
		// System.out.println("[WINDOW] "+INDEX_WINDOW_FROM+" - [WINDOW_TO]"+plan_window);
		relevantVar = getRelevantVariables(basic_states, INDEX_WINDOW_FROM, plan_window); // dentro busca los indices 
		
		// getting the relevant variables that are in the root
		if(conf_weights[4]!=0)
			relevantRootVar = filterRelevantVar(relevantVar, root.getGoalState());
		
		// getting the total of producers and the total producer uniques
		if(conf_weights[6]!=0 || conf_weights[7]!=0){
			totalProductors = 0;
			productorsUnique = new HashSet<Integer>(); 
			for(DSVariables var : relevantVar.keySet()){
				totalProductors += var.getProducers();
				productorsUnique.addAll(planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var)));
			}
		}
		
		if(conf_weights[3]!=0)
			domainRVar = getDomainValue(relevantVar);
		
		parameters[0]=relevantVar; // relevant Variables
		parameters[1]=domainRVar; // domain of the relevant Variables
		parameters[2]=relevantRootVar; // relevant variables in the root
		parameters[3]=root.getGoalState(); // root variables
		parameters[4]=totalProductors; // total of producers
		parameters[5]=productorsUnique; // total of unique producers
		return parameters;
	}

	private double est_b;
	private double generated_nodes;
	private double evaluated_Nodes;
	private RModel r_model;

	private double estimating_time(int depth, Object[] parameters) {
		
		if(((SingleSettings)this.settings).getRSettings().exe_model_in_r==false){ // multivariable linear predictive model
			return modelsLineal(conf_weights, depth, parameters);
		}else{ // using models in R
			return modelsInR(conf_weights, depth, parameters);
		}
		
	}

	@SuppressWarnings("unchecked")
	private double modelsInR(float[] confWeights, int depth, Object[] parameters) {

		int RVar 		= ((Hashtable<DSVariables, String>)parameters[0]).size();
		int DRvar 		= (Integer)parameters[1];
		int RvarRoot 	= (parameters[2]==null)?0:((ArrayList<DSCondition>)parameters[2]).size();
		int fluentsRoot = ((ArrayList<DSCondition>)parameters[3]).size();
		int producersT 	= (Integer)parameters[4];
		int producersU 	= (parameters[5]==null)?0:((HashSet<DSAction>)parameters[5]).size();
		
		// getting the mean time and alfa 
		double meanTime 	= confWeights[8];
		double alfa			= confWeights[9];	
		
		switch (((SingleSettings)this.settings).getModelVariableToEstimate()){
			case RP_MODEL_ESTIMATING_B:
				
				est_b = estimatingR(confWeights,depth,RVar,DRvar,RvarRoot,fluentsRoot,producersT,producersU);
				
				generated_nodes = (Math.pow(est_b+confWeights[10], depth));// estimating the total of generated nodes in the Tree
				
				evaluated_Nodes = generated_nodes * alfa;// estimating the total of evaluated nodes
				
				return evaluated_Nodes * meanTime;// estimating the time
				
			case RP_MODEL_ESTIMATING_GENERATED_NODES:
				
				generated_nodes = estimatingR(confWeights,depth,RVar,DRvar,RvarRoot,fluentsRoot,producersT,producersU);
				
				evaluated_Nodes = generated_nodes * alfa;// estimating the total of evaluated nodes
				
				return evaluated_Nodes * meanTime;// estimating the time
			
			case RP_MODEL_ESTIMATING_EVALUATED_NODES:

				evaluated_Nodes = estimatingR(confWeights,depth,RVar,DRvar,RvarRoot,fluentsRoot,producersT,producersU);
				
				return evaluated_Nodes * meanTime;// estimating the time
			
			case RP_MODEL_ESTIMATING_TIME:
				return estimatingR(confWeights,depth,RVar,DRvar,RvarRoot,fluentsRoot,producersT,producersU);
				
			default: return 0;				
		}
	}
	
	@SuppressWarnings("unchecked")
	private double modelsLineal(float[] confWeights, int depth, Object[] parameters) {

		int RVar 		= ((Hashtable<DSVariables, String>)parameters[0]).size();
		int DRvar 		= (Integer)parameters[1];
		int RvarRoot 	= (parameters[2]==null)?0:((ArrayList<DSCondition>)parameters[2]).size();
		int fluentsRoot = ((ArrayList<DSCondition>)parameters[3]).size();
		int producersT 	= (Integer)parameters[4];
		int producersU 	= (parameters[5]==null)?0:((HashSet<DSAction>)parameters[5]).size();
		
		// getting the mean time and alfa 
		double meanTime 	= confWeights[8];
		double alfa			= confWeights[9];	
		
		switch (((SingleSettings)this.settings).getModelVariableToEstimate()){
			case RP_MODEL_ESTIMATING_B:
				est_b = confWeights[0]+(confWeights[1]*depth)+(confWeights[2]*RVar)
				+(confWeights[3]*DRvar)+(confWeights[4]*RvarRoot)+(confWeights[5]*fluentsRoot)
				+(confWeights[6]*producersT)+(confWeights[7]*producersU);
				
				generated_nodes = (Math.pow(est_b+confWeights[10], depth));// estimating the total of generated nodes in the Tree
				
				evaluated_Nodes = generated_nodes*alfa;// estimating the total of evaluated nodes
				
				return evaluated_Nodes*meanTime;// estimating the time
			
			case RP_MODEL_ESTIMATING_GENERATED_NODES:
				generated_nodes = confWeights[0]+(confWeights[1]*depth)+(confWeights[2]*RVar)
						+(confWeights[3]*DRvar)+(confWeights[4]*RvarRoot)+(confWeights[5]*fluentsRoot)
						+(confWeights[6]*producersT)+(confWeights[7]*producersU);
				
				evaluated_Nodes = generated_nodes*alfa;// estimating the total of evaluated nodes
				
				return evaluated_Nodes*meanTime;// estimating the time
			
			case RP_MODEL_ESTIMATING_EVALUATED_NODES:
				evaluated_Nodes = confWeights[0]+(confWeights[1]*depth)+(confWeights[2]*RVar)
						+(confWeights[3]*DRvar)+(confWeights[4]*RvarRoot)+(confWeights[5]*fluentsRoot)
						+(confWeights[6]*producersT)+(confWeights[7]*producersU);
				
				return evaluated_Nodes*meanTime;// estimating the time
			
			case RP_MODEL_ESTIMATING_TIME:
				return confWeights[0]+(confWeights[1]*depth)+(confWeights[2]*RVar)
						+(confWeights[3]*DRvar)+(confWeights[4]*RvarRoot)+(confWeights[5]*fluentsRoot)
						+(confWeights[6]*producersT)+(confWeights[7]*producersU);
			default: return 0;				
		}
	}

	private double estimatingR(float[] confWeights2, int depth, int rVar,
			int dRvar, int RvarRoot, int fluentsRoot, int producersT, int producersU) {
		
		RSettings r_settings = ((SingleSettings)this.settings).getRSettings();

		String input_eval = "";
		
		if(r_model == null){
			r_model = new RModel(r_settings.ip, r_settings.port, r_settings.start_server);
			
			if(!r_settings.model_type.equals("none"))
				input_eval = "library("+r_settings.model_type+")";
			input_eval = input_eval + "\nload('"+r_settings.model_path+r_settings.model_file+"')";
		}
		
		String input = "";
		if(confWeights2[1]!=0)	input = input + "depth="+depth;
		if(confWeights2[2]!=0)	input = input + ",Rvar="+rVar;
		if(confWeights2[3]!=0)	input = input + ",DRvar="+dRvar;
		if(confWeights2[4]!=0)	input = input + ",RvarRoot="+RvarRoot;
		if(confWeights2[5]!=0)	input = input + ",FluentsRoot="+fluentsRoot;
		if(confWeights2[6]!=0)	input = input + ",Producers="+producersT;
		if(confWeights2[7]!=0)	input = input + ",ProducersU="+producersU;
		
		input.replaceFirst(",", "");
		if(input.lastIndexOf(",")==(input.length()-1))
			input = input.substring(0, input.length()-1);
		
		input_eval = input_eval + "\npredict(prediction_model, list("+input+"))";
		
		return r_model.predict(input_eval);
	}
	
	private Hashtable<DSVariables, String> getRelevantVariables(List<NodeState> basicStates, int from, int lastWindowNode) {

		// getting the relevant actions
		List<Integer> relevantActions = get_relevant_actions(basicStates, from, lastWindowNode);
		
		if(((SingleSettings)this.settings).getJournal() == SETTINGS_REACTIVESTRUCTURE.JOURNAL_ICAE13){
			return getRelevantVariablesICAE(basicStates, from+lastWindowNode, relevantActions);
		} 
		
		return getRelevantVariablesOthers(basicStates, lastWindowNode, relevantActions);
		
	}
	
	private Hashtable<DSVariables, String> getRelevantVariablesICAE(List<NodeState> basicStates, int lastWindowNode, List<Integer> relevantActions) {

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
		boolean found=false;
		for(DSCondition goals : basicStates.get(basicStates.size()-1).getGoalState()){
			found=false;
			if (goals.getTime() == DSCondition.TT_AT_START || goals.getTime() == DSCondition.TT_OVER_ALL || 
					goals.getTime() == DSCondition.TT_NONE){
				for(DSCondition cond : basicStates.get(lastWindowNode).getGoalState()){
						if(cond.equals(goals))
						{found=true;break;}
				}
				if(found && !relevantVariables.containsKey(goals.getFunction())){
					relevantVariables.put(goals.getFunction(), goals.getValue());
				}
			}
		}
		
		// special case: keeping all fluent in the last partial state as relevant by a fictitious action
		if(lastWindowNode==(basicStates.size()-1)){
			for(DSCondition cond : basicStates.get(lastWindowNode).getGoalState()){
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
			
			System.out.println("\n[ICAE-Relevant variables]: "+relevantVariables.toString());
		}
		
		return relevantVariables;
	}
	
	private Hashtable<DSVariables, String> getRelevantVariablesOthers(List<NodeState> basicStates, int lastWindowNode, List<Integer> relevantActions) {

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
			
			if(i < relevantActions.size()-1) {
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
		if(lastWindowNode==(basicStates.size()-1)){
			for(DSCondition cond : basicStates.get(lastWindowNode).getGoalState()){
				if (cond.getTime() == DSCondition.TT_AT_START || cond.getTime() == DSCondition.TT_OVER_ALL || 
						cond.getTime() == DSCondition.TT_NONE){
					if(!relevantVariables.containsKey(cond.getFunction())){
						relevantVariables.put(cond.getFunction(), cond.getValue());
					}
				}
			}
		}
		
		//this.printAction(relevantActions);
		if(debug) System.out.println("\n[OTHERS-Relevant variables]: "+relevantVariables.toString());
		
		return relevantVariables;
	}

	/**
	 * return the relevant variables from index_from to the planning window
	 * @param basic_states
	 * @param index_from  instant time from
	 * @param planning_window, length of the planning window
	 * @return
	 */
	protected List<Integer> get_relevant_actions(List<NodeState> basic_states, int index_from, int planning_window) {
		
		List<Integer> relevantActions = new ArrayList<Integer>();
		List<Integer> op = null;
		int i=-1;
		try {
			for(i = index_from;i <= index_from+planning_window; ++i) { 
				op = basic_states.get(i).getOperatorIndex();
				if(op.size() > 0 && op.get(0) != -1) { // if it is not a dummy action 
					relevantActions.add(op.get(0));
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			if(debug) System.out.println("["+planning_problem.getAgentName()+"][Error] Possible action is not instantiated ("+((i!=-1)?i:"")+")");
		}
		
		return relevantActions;
	}

	private int getDomainValue(Hashtable<DSVariables, String> relevantVariables) {
		int total = 0;
		for(DSVariables var : relevantVariables.keySet()){
			total += (((PlanningProblemRP)planning_problem).getVariablesHelpful().get(planning_problem.getVariablesIndex().get(var)).size());
		}
		return total;
	}	
	
	private ArrayList<DSCondition> filterRelevantVar(Hashtable<DSVariables, String> relevantVar, ArrayList<DSCondition> goal_state) {
		
		ArrayList<DSCondition> goal_state_rVarIn = new ArrayList<DSCondition>(goal_state.size());
		for(DSCondition var : goal_state)
			if(relevantVar.containsKey(var.getFunction()))
				goal_state_rVarIn.add(var);
		
		return goal_state_rVarIn;
	}
	
	public EstimatedTime get_estimated_size(){
		return best_size;
	}

	public List<NodeState> get_plan_as_partial_states() {
		return basic_states;
	}
}
