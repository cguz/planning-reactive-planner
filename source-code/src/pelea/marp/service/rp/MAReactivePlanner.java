/** *  
 *  @name-file 		bestGoalState.java
 *  @project   		AnyTimePlan_Adaptation - 10/01/2010
 *  @author    		Cesar Augusto Guzman Alvarez   
 *  @equipo 		zenshi
 *  @universidad 	Universidad Politecnica de Valencia
 *  @departamento 	DSIC
 */

package pelea.marp.service.rp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import conf.xml2java.Conf;
import gnu.trove.set.hash.THashSet;
import pelea.atpa.common.enums.Enums.PDDL_PROCESS;
import pelea.marp.common.r.RSettings;
import pelea.marp.common.rp.ReactivePlanner;
import pelea.marp.common.rp.planning_task.pddl.Fluent;
import pelea.marp.common.rp.planning_task.pddl.PartialState;
import pelea.marp.common.rp.planning_task.plan.FixingPlan;
import pelea.marp.common.rp.planning_task.plan.comparator.FixingPlanComparator;
import pelea.marp.common.rp.planning_task.plan.comparator.FixingPlanSorter;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.enums.FILTER;
import pelea.marp.common.rp.reactive.enums.SEARCH_SPACE_FILTER;
import pelea.marp.common.rp.reactive.enums.T_SOLUTION;
import pelea.marp.common.rp.reactive.exceptions.BadProblemDefinitionException;
import pelea.marp.common.rp.reactive.exceptions.GeneralException;
import pelea.marp.common.rp.reactive.exceptions.NoPosiblePlanException;
import pelea.marp.common.rp.reactive.exceptions.NoRootException;
import pelea.marp.common.rp.reactive.exceptions.NoSearchSpaceException;
import pelea.marp.common.rp.reactive.exceptions.NoStatisticException;
import pelea.marp.common.rp.reactive.exceptions.NotExecutingPlanException;
import pelea.marp.common.rp.reactive.repairing_structures.ManagerStructureSelfRepair;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdapting;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdaptingOwnPlan;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdaptingRelaxed;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByState;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.common.rp.reactive.shared.pddl.SharedFluent;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.common.rp.reactive.shared.pddl.SharedVariable;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTeamwork;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.helper.Statistics;
import pelea.marp.service.rp.planning_task.PlanningProblemRPImp;
import pelea.marp.service.rp.planning_task.parser.ParserRP_NMAP;
import pelea.marp.service.rp.planning_task.pddl.FluentImp;
import pelea.marp.service.rp.planning_task.pddl.PartialStateImp;
import pelea.marp.service.rp.reactive.repairing_structures.ManagerStructureSelfRepairImp;
import pelea.marp.service.rp.reactive.search_spaces.Estimating;
import pelea.marp.service.rp.reactive.search_spaces.NodeStateImp;
import pelea.marp.service.rp.reactive.search_spaces.SearchSpaceAdaptingImp;
import pelea.marp.service.rp.reactive.search_spaces.SearchSpaceAdaptingOwnPlanImp;
import pelea.marp.service.rp.reactive.search_spaces.SearchSpaceAdaptingRelaxedImp;
import pelea.marp.service.rp.reactive.search_spaces.SearchSpaceByStateImp;
import pelea.marp.service.rp.reactive.search_spaces.SearchSpaceDummy;
import pelea.marp.service.rp.reactive.settings.SingleSettingsImp;
import pelea.marp.service.rp.reactive.shared.pddl.SharedPartialStateImp;
import pelea.marp.service.rp.reactive.shared.pddl.SharedVariableImp;
import pelea.marp.service.rp.reactive.shared.teamwork.SharedTeamworkImp;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.enums.T_SORT;
import pelea.planning_task.common.parser.Parser;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSValueExpression;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlan;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.pddl.FAction;
import pelea.planning_task.service.pddl.GAction;
import pelea.planning_task.service.pddl.NVariables;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import tools.files.Files;


/**
 * 
 * @author Cesar Augusto Guzman Alvarez - 10/01/2010
 * 
 */
public class MAReactivePlanner implements ReactivePlanner {
	
	/** STARTING VARIABLES **/
	
	protected boolean 								debug_system = false;

	protected String								agent_name;
	
	// planning task	
	private PDDL_PROCESS 							type_parser=PDDL_PROCESS.PP_NORMAL;
	protected PlanningProblem 						planning_problem = null;

	// general settings
	protected GeneralSettings						settings;

	// current root node and plan to shared partial state
	private NodeState 								current_root=null;
	private ArrayList<Integer> 						current_plan=null;
	
	// path of the evaluated benchmark
	private String 									path_eval_benchmarks;	
	
	
	

	/** single-repair: calculating search spaces variables, current plan to translate and sequence of partial states **/
	public ArrayList<DSPlanAction> 					_trans_current_plan=null;
	HashMap<Integer, PS> 							_trans_seq_ps = new HashMap<Integer, PS>(0);
    
	// temporal estimating variable	
	protected Estimating 							_temp_estimating;
	
	protected ArrayList<ManagerStructureSelfRepair> self_structures;// list of self repairing structures
	
	
	
	
	
	// self-repair: search tree used to repair plan failure
	private int 									_self_repair_index_structure=-1;
	protected SearchSpace 							_temp_current_tree_to_create=null;

	private List<SharedPartialState> 				single_shared_plan;

    
	
	
	
	

	/** multi-repair: variables **/	
	// teamwork
	private SharedTeamwork 							tw = null;
	
	// search space to adapting own plan	
	private SearchSpaceAdaptingOwnPlan 				own_adapting_tree = null;

	// search space to adapting plan
	private SearchSpaceAdapting 					adapting_tree = null;
	
	// search space to adapting plan relaxed
	private SearchSpaceAdapting 					relaxed_adapting_tree = null;

	

    
	
	
	
	

	
	// STARTING METHODS
	/**
	 * 16/01/2012 17:15:22	
	 * Constructor to initialize the planning problem struct and taking the decision
	 * @param domain_problem in pddl or xml
	 * @param plan action of the problem in xml
	 * @param configuration 
	 * @param path_results 
	 * @param id_sesion just to control the sesion in the web page
	 */
	public MAReactivePlanner(String[] domain_problem, ArrayList<DSPlan> plan, Conf configuration, String path_results) {
		
		super();
		
		// saving settings of the reactive planner
		settings = new SingleSettingsImp(configuration);
		
		// initializing values 
		self_structures = new ArrayList<ManagerStructureSelfRepair>(1);
		_temp_current_tree_to_create=null;
		_self_repair_index_structure=-1;
		
		// configuration by default
		path_eval_benchmarks = path_results;
		
		// initializing the planning problems
		planning_problem = planning_tasks_parser(domain_problem, ((SingleSettings)settings).getAgentName(), ((SingleSettings)settings).get_filter_own_fluents());
		
		if (plan!=null) planning_problem.preprocess_plan(plan);
		
		// saving the agent name
		agent_name = planning_problem.getAgentName();
		
	}

	/**
	 * 17/01/2012 11:11:56
	 * generating the planning problem struct
	 * @param domain_problem string of the domain and problem
	 * @param agent 
	 * @param boolean1 
	 * @return struct of the planning problem
	 */
	private PlanningProblem planning_tasks_parser(String[] domain_problem, String agent, Boolean own_fluents) {
		
		Parser parser = null;
		switch(type_parser){
			default: 
			case PP_NORMAL:
				parser = new ParserRP_NMAP(domain_problem, agent, own_fluents);
			break;
		}
		
		return parser.getPlanningProblem();
	}

    
	
	
	
	

	
	@Override
	public boolean services_activated() {
		return (((SingleSettings)settings).is_services_activate()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}

    
	
	
	
	

	
	// single-repair
	/**
	 * 1. translating the plan actions to partial states
	 * backward: we translate all the plan actions to a sequences of partial states
	 * forward:  we do not translate now, but during the estimating proccess
	 * @return a sequence of partial states
	 */
	@Override
	public HashMap<Integer, PS> translating(ArrayList<DSPlan> plan) {
		
		// saving the new plan
		if (plan != null) planning_problem.setPlan(plan); 
		
		// starting the default values
		default_values();
		
		// removing the garbage collector
		Runtime garbage = Runtime.getRuntime();
	    garbage.gc();
		
	    // translating the plan to sequences of partial states
		return translating(); 
		
	}

	/**
	 * 1. translating the plan actions to partial states
	 * backward: we translate all the plan actions to a sequences of partial states
	 * forward:  we do not translate now, but during the estimating process
	 * @return a sequence of partial states
	 */
	public HashMap<Integer, PS> translating() {
		String temp;
		DSPlan plan;
		
		
		
		
		// initializing the sequences of partial states (used to calculate the root node)
		_trans_seq_ps 		= new HashMap<Integer, PS>(0);
		
		
		
		// get the current plan
		plan = planning_problem.getBestPlan();
		plan.sort(T_SORT.MENOR_MAYOR, T_SORT.BURBUJA);
		
		
		
		// saving current plan
		_trans_current_plan = copy(plan.getActionPlan());
		
		if(debug_system)
			System.out.println("["+agent_name+"][CURRENT-PLAN]: "+_trans_current_plan.toString());
		
		switch(((SingleSettings)settings).getTranslatingApproach()){
			// if it is a regressed approach, we generate all the sequences of partial states by regression
			case RP_REGRESSED_APPROACH:
			case RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH: 
				_trans_seq_ps = planning_problem.regress_plan((new NodeStateImp(planning_problem.getGlobalGoalState(), planning_problem)).getRPS(), _trans_current_plan);
				
				if(debug_system){
					temp = "\n["+agent_name+"][translating:251]\n[";
					for(PS seq : _trans_seq_ps.values()) temp += seq.toString()+",";
					temp += "]";
					System.out.print(temp);
				}
				
				break;
			// otherwise, it is a forward approach: sequences of partial states will be generated during the estimating process
			default: 
		}

		// we instantiated only one time this variable
		estimating(planning_problem, settings);
		
		// until here, we have : _trans_current_plan, and _trans_seq_ps
		return _trans_seq_ps;
	}

	protected void estimating(PlanningProblem planning_problem2, GeneralSettings settings2) {
		_temp_estimating = new Estimating(planning_problem, settings);
	}

	protected void coord_update_current_plan_info_tw(ArrayList<DSPlanAction> _trans_current_plan2, SharedTeamwork tw2) {
		
	}
	

	/**
	 * single-repair
	 * default values of the single-repair
	 */
	private void default_values() {

		_temp_current_tree_to_create=null;
		
		_self_repair_index_structure = -1;
		self_structures.clear();
		
	}
	
	/**
	 * single-repair
	 * translating the current plan to a list of integer
	 */
	private ArrayList<DSPlanAction> copy(ArrayList<DSPlanAction> plan) {
		List<DSPlanAction> _plan = new ArrayList<DSPlanAction>(plan.size());
		
		for(DSPlanAction act: plan)
			_plan.add(act);
		
		return (ArrayList<DSPlanAction>) _plan;
	}

	
	
	
	/**
	 * 1. estimating settings
	 * @input plan as a sequence of ps
	 * 	backward: all the sequences of partial states
	 * 	forward:  it is created during the estimating proccess
	 * @return the best setting <l,d> to calculate the search space
	 */
	@Override
	public EstimatedTime estimating_setting(double time_limit) {
		String to_print;
		
		List<NodeState> temp;
		EstimatedTime 	best_setting = null;
		int i = 0;
		
		// estimating the size
		best_setting = _temp_estimating.estimating_setting(time_limit, _trans_seq_ps, _trans_current_plan);

		to_print = "\n["+agent_name+"] the best setting is: "+best_setting.toString();
		
		// forward: saving the sequence of partial states 
		if(((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_FORWARD_APPROACH){

			_trans_seq_ps.clear();
			temp = _temp_estimating.get_plan_as_partial_states();
			for(NodeState ps : temp){
				_trans_seq_ps.put(i, ps.getRPS());
				++i;
			}

			to_print+= " [FORWARD] sequence of partial states: "+_trans_seq_ps.toString()+"\n";
		}

		if(debug_system)
			System.out.println(to_print);
		
		return best_setting;
	}





    
	// single-repair : TEST calculating (extending) the search space
	@Override
	public void calculating(int plan_window, int depth, boolean calculating_ps) {

		// if there is not search space, we try to create it
		if (create_if_not_exists(_trans_seq_ps, _trans_current_plan, plan_window)) {
			
			// if there is forward or backward + filtering, we translating the plan as partial states
			if(calculating_ps == true 
					&& (((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_FORWARD_APPROACH 
					|| ((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH)){
				if(plan_window >= _trans_seq_ps.size()) { 
					((SearchSpaceByState)_temp_current_tree_to_create).translate_plan_as_partial_states(plan_window);
					
					for(NodeState ps : ((SearchSpaceByState)_temp_current_tree_to_create).get_plan_as_partial_states()){
						_trans_seq_ps.put(ps.getRPS().getState(), ps.getRPS());
					}
				}
			}
		}
		
		calculating(new EstimatedTime(1000, plan_window, depth,-1,-1,-1,null));
	}

	@Override
	public void calculating(EstimatedTime best_setting) {

		// if there is not search space, we try to create it
		create_if_not_exists(_trans_seq_ps, _trans_current_plan, best_setting.getPlanningHorizon());
		
		// calculating the search space
		((SearchSpaceByState)_temp_current_tree_to_create).calculating(best_setting);

		ManagerStructureSelfRepairImp t = ((ManagerStructureSelfRepairImp)self_structures.get(self_structures.size()-1));
		
		// saving the data in the manager of the structure
		t.saving();
		
		// if it is the first structure, saving the current root node 
		if(first_search_tree()){
			
			// saving the current root node and current plan 
			updating_current_root_and_plan(self_structures.get(0).getSearchTree().get_root(), self_structures.get(0).get_actions_current_window());
		}
	}

	/**
	 * save the current root and plan
	 * @param root {@link NodeState} of the agent
	 * @param plan {@link List<Integer>} of the agent
	 */
	protected void updating_current_root_and_plan(NodeState root, List<Integer> plan) {

		current_root = new NodeStateImp(root.getRPS(), planning_problem, true);
		current_plan = new ArrayList<Integer>(plan);
		
		if(debug_system){
			String temp = "";
			
			for(Integer a : plan){
				if(a == -1)	temp+="dummy-action, ";
				else		temp+=planning_problem.getActions().get(a).toString()+", ";
			}
			
			System.out.println("\n["+agent_name+"][UPDATE][ROOT-NODE] "+current_root.toPddl()+"\n["+agent_name+"][UPDATE][CURRENT-PLAN] <"+temp+">\n");
		}
	}

	/**
	 * @param plan_window 
	 * @param plan 
	 * @param seq_ps 
	 * @return true if the structure is created, otherwise false
	 */
	private boolean create_if_not_exists(HashMap<Integer, PS> seq_ps, ArrayList<DSPlanAction> plan, int plan_window) {
		
		if (seq_ps != null) {
			
			if(debug_system) 
				System.out.println("\n\n["+agent_name+"][create_if_not_exists] no search spaces...we create the search space and remove the first elements in the temporal plan and seq_ps.\n");
			
			creating_structure(seq_ps, plan, plan_window);

			// removing the first plan and partial states in the temporal structures
			do{
				--plan_window;
				seq_ps.remove((Integer)plan_window);
				plan.remove(0);
			}while(plan_window > 0);
			
			
			// BACKWARD : PARA ACTUALIZAR LA INFORMACIÓN DEL CURRENT REGRESSED SEQUENCE PLAN
			if(((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_APPROACH || ((SingleSettings)settings).getTranslatingApproach() == SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH){
				seq_ps.clear();
				seq_ps.putAll(planning_problem.regress_plan((new NodeStateImp(planning_problem.getGlobalGoalState(), planning_problem)).getRPS(), _trans_current_plan));
			}
						
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * single-repair
	 * 02/10/2013
	 * creating a new repairing structure
	 * @param goal_state plan as a linear sequence of goal states
	 * @param plan 
	 * @param plan_window 
	 */
	protected void creating_structure(HashMap<Integer, PS> goal_states, ArrayList<DSPlanAction> plan, int plan_window) {
		
		boolean goals_as_relevant = false;
	
		if(plan.size() != 0){
			switch(settings.getClassToUseSearchTree()){ // we can have different types of reactive structure 
				case RS_GTS_SEARCH_SPACE_BY_STATE : 
				
					if(plan_window == plan.size())
						goals_as_relevant = true;
					
					self_structures.add(
					new ManagerStructureSelfRepairImp(
					new SearchSpaceByStateImp(planning_problem, goal_states.get((Integer)plan_window), plan.subList(0, plan_window), plan.subList(plan_window, plan.size()), settings, path_eval_benchmarks, goals_as_relevant)));
					break;
					
				default: break;
			}
			
			_temp_current_tree_to_create = self_structures.get(self_structures.size()-1).getSearchTree();
		}
	}




    
	// single-repair : interrupt 
	@Override
	public boolean first_search_tree() {
		
		if(self_structures.size() < 2) return true;
		return false;
		
	}





    
	// single-repair : stop decision
	@Override
	public double current_window() {
		
		double window = 0;
		for(ManagerStructureSelfRepair s : self_structures)
			window+=s.get_plan_window();
		
		return window;
	}
	
	@Override
	public boolean is_plan_window_completed(){

		// the current plan
		int size = _trans_current_plan.size();
		
		if(debug_system)
			System.err.println("["+agent_name+"] is plan window completed ? current plan size = "+size);
		
		if(size <= 0)
			return true;
		
		return false;
		
	}
	




    
	// single-repair 
	@Override
	public List<Integer> get_var_in_last_search_tree() {
		
		List<Integer> varQ = new ArrayList<Integer>();
				
		if(self_structures.size() > 0) 
			varQ.addAll(self_structures.get(self_structures.size()-1).getVarQ());
		
		return varQ;
		
	}
	
	@Override
	public List<Integer> get_actions_current_window() {
		
		if (_self_repair_index_structure != -1)
			return self_structures.get(_self_repair_index_structure).get_actions_current_window();

		if(self_structures.size() > 0)
			return self_structures.get(0).get_actions_current_window();
				
		return null;
	}





	
	
    
	// single-repair method
	@Override
	public String single_repairing(ArrayList<DSCondition> S, Integer exe_actions) throws NoPosiblePlanException{

		if(debug_system)
			System.out.println("\n["+agent_name+"][SELF-REPAIR]");
		
		if(((SingleSettings)settings).single_repair_activated()==SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE){
			System.out.println("["+agent_name+"] - single-repairing is disabled");
			throw new NoPosiblePlanException("["+agent_name+"][Warning] the single repair is disabled");	
		}
		
		int exe_act_T = find_reactive_structure_removing_update_root_plan(exe_actions);
		
		if(exe_act_T!=-1){
			_self_repair_index_structure=0;

			if(debug_system)
				System.out.println("["+agent_name+"] - executed actions/executed actions in T: ["+exe_actions+"/"+exe_act_T+"], search space index: "+((_self_repair_index_structure==-1)?"there is not any search space =(":_self_repair_index_structure));

			return single_repairing(S, exe_act_T, exe_actions);
		}
		
		throw new NoPosiblePlanException("["+agent_name+"][Error] not found possible plan.");
		
	}
	
	/**
	 * single-repair: finding the manager repairing structure for a set of executed actions
	 * @param exe_actions, total of executed actions
	 * @return [the index of the search space, the updated executed actions]
	 */
	private int[] find_reactive_structure_no_removing(Integer exe_actions) {
		
		int t_supported_act;
		ManagerStructureSelfRepair rs;
		
		int[] i = {0,exe_actions};// [search space, exe_actions]
		while(self_structures.size() > i[0]) { 
			
			rs = self_structures.get(i[0]);
			
			try {
				t_supported_act = rs.get_total_act_supported(); // the associated plan includes the last partial state
			} catch (Exception e) { return new int[]{0,0}; }
			
			if(debug_system)
				System.out.println("["+agent_name+"][FIND-SEARCH-SPACE][NO-REM][TREE-"+i[0]+"] - actions supported: ["
				+t_supported_act+"] [executed actions >= supported actions ("+i[1]+" >= "+t_supported_act+")? "+(i[1] >= t_supported_act)+"]");
			
			if(i[1] >= t_supported_act) { 
				i[1]-=t_supported_act; // executed actions
				i[0]++;
			} else 
				return i;
		}
		
		return new int[]{-1,-1};
	}
	
	private int find_reactive_structure_removing(Integer exe_actions) {
		
		int t_supported_act;
		ManagerStructureSelfRepair rs;
		
		while(self_structures.size() > 0) { 
			
			rs = self_structures.get(0);
			
			try {
				t_supported_act = rs.get_total_act_supported(); // the associated plan includes the last partial state
			} catch (Exception e) { return 0; }
			
			if(debug_system)
				System.out.println("["+agent_name+"][FIND-SEARCH-SPACE][REM][TREE-0] - actions supported: ["
				+t_supported_act+"] [executed actions >= supported actions ("+exe_actions+" >= "+t_supported_act+")? "+(exe_actions >= t_supported_act)+"]");
			
			if(exe_actions >= t_supported_act) { 
				exe_actions-=t_supported_act;
				self_structures.remove(0);
			} else {
				return exe_actions;
			}
		}
		
		return -1;
	}

	private int find_reactive_structure_removing_update_root_plan(Integer exe_actions) {
		
		int t_supported_act;
		ManagerStructureSelfRepair rs=null;
		SearchSpace c_search_space;

		boolean add_last = false;
		while(self_structures.size() > 0) { 
			
			rs = self_structures.get(0);
			c_search_space = rs.getSearchTree();
			
			try {
				t_supported_act = rs.get_total_act_supported(); // the associated plan includes the last partial state
			} catch (Exception e) { return 0; }
			
			if(debug_system)
				System.out.println("\n["+agent_name+"][FIND-SEARCH-SPACE][REM-ROOT-PLAN][TREE-0] - actions supported: ["
			+t_supported_act+"] [executed actions >= supported actions ("+exe_actions+" >= "+t_supported_act+"? "+(exe_actions >= t_supported_act)+"]");
			

			// saving current plan and saving the current root node
	    	updating_current_root_and_plan(c_search_space.get_root(), rs.get_actions_current_window());
			
			if(exe_actions >= t_supported_act) { 
				exe_actions-=t_supported_act;
				self_structures.remove(0);
				
		    	// removing actions in the current plan
				removing_actions_current_plan(t_supported_act, t_supported_act);
				
				add_last = true;
			} else {
				
		    	// removing actions in the current plan
				removing_actions_current_plan(exe_actions, exe_actions);
				
				return exe_actions;
			}
		}
		
		if(add_last){
			self_structures.add(rs);
			return 0;
		}
		
		return -1;
	}
	
	/**
	 * single-repair: implements the self-repair process
	 * @param S variables in the search trees with its current values
	 * @param exe_actions 
	 * @param time
	 * @return {@link String} plan solution
	 * @throws NoPosiblePlanException 
	 */
	private String single_repairing(ArrayList<DSCondition> S, int exe_actions, int time) throws NoPosiblePlanException {

		SearchSpaceByState 			c_search_space;
		ManagerStructureSelfRepair 	c_manager;
		
		ArrayList<Integer> plan;
		
		c_manager = self_structures.get(_self_repair_index_structure);
		c_search_space = (SearchSpaceByState) c_manager.getSearchTree();
		
		// self-repair 
		plan = c_search_space.getPlan(S, exe_actions);

        if(plan != null) { 

	    	// reordering new partial states
	    	reordering_self_structure(plan);
	    	
	        // concatenating the remaining actions
			plan.addAll(c_search_space.get_plan_remaining()); 
			
        	// reordering the time instant
	    	updating_plan(plan, 0, 0, 0); 
	    	
	    	if(debug_system)
	    		System.out.println("\n["+agent_name+"] new plan: "+planning_problem.getBestPlan().toLPG_TD());
	    	
    		// saving current plan and saving the current root node
        	updating_current_root_and_plan(c_search_space.get_root(), c_manager.get_actions_current_window());
        	
	    	// single plan as a sequence of shared partial states 
	    	single_shared_plan = new SearchSpaceDummy(current_root, planning_problem).regress_plan(current_plan);
	    	
			return planning_problem.getBestPlan().toXml();
			
        }
       
		throw new NoPosiblePlanException("["+agent_name+"] not found possible plan.");
	}

	/**
	 * single-repair: reordering the search space
	 * @param plan 
	 */
    private void reordering_self_structure(ArrayList<Integer> plan) { 
       
        self_structures.get(_self_repair_index_structure).reordering(plan);
    	
	}

	private void removing_actions_current_plan(int exe_actions_T, int exe_actions) {

		DSPlan cur_plan = planning_problem.getBestPlan();
		
		for(int i = 0; i < exe_actions; ++i){
			if(i<exe_actions_T && current_plan.size()>0)
				current_plan.remove(0);
			cur_plan.getActionPlan().remove(0);
		}
		
		if(debug_system){
			String temp = "";
			
			for(Integer a : current_plan){
				if(a == -1)	temp+="dummy-action, ";
				else		temp+=planning_problem.getActions().get(a).toString()+", ";
			}
			
			System.out.println("["+agent_name+"][UPDATE][REM][CURRENT-PLAN] current plan: <"+temp+">\n");
		}
		
	}

	@Override
	public List<SharedPartialState> get_single_shared_plan() {
		return single_shared_plan;
	}

	@Override
	public boolean verify_conflicts_self_repair() {
		return (((SingleSettings)settings).verify_conflicts_self_repair()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}
	
	@Override
	public boolean single_repair_activated() {
		return (((SingleSettings)settings).single_repair_activated()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}
	
	/**
	 * single-repair: updating the execution time of the new plan
	 * @param plan			set of actions of the plan
	 * @param start			initial time
	 * @param index			initial index of the plan
	 * @param current_ex	current time execution
	 */
	private void updating_plan(ArrayList<Integer> plan, int start, int index, int current_ex) {
		planning_problem.setPlan(planning_problem.creating_plan(plan, start, index, current_ex));
	}    
	
	@Override
	public List<Integer> get_single_plan_to_root() throws NoPosiblePlanException, NoRootException, GeneralException {
		
		if(_self_repair_index_structure!=-1){
			return self_structures.get(_self_repair_index_structure).getSearchTree().get_plan_to_root();
		}else
			if(_temp_current_tree_to_create!=null)
				return _temp_current_tree_to_create.get_plan_to_root();
		
		throw new NoPosiblePlanException("["+agent_name+"][Error] there is not single repairing search space. size self structure: "+self_structures.size());
		
	}
	
	@Override
	public Statistics get_single_statistic() throws NoStatisticException {
		if(_self_repair_index_structure!=-1 && self_structures.size() > 0){
			return self_structures.get(_self_repair_index_structure).getSearchTree().get_statistics();
		}else
			if(_temp_current_tree_to_create!=null)
				return _temp_current_tree_to_create.get_statistics();
		
		throw new NoStatisticException("["+agent_name+"][Error] there is not single repairing search space. size self structure: "+self_structures.size());

	}




	
	

    
	// multi-repair method	
	@Override
	public List<SharedPartialState> get_ps_to_publicize(int exe_actions, SEARCH_SPACE_FILTER filter) throws NotExecutingPlanException, NoRootException { 

		NodeState 			root = current_root;
		ArrayList<Integer> 	plan = current_plan;
		int tree = 0;
		int[] tree_actions;
		
		switch(filter){
			case SELF_TREE:
				// if during the self-repair the agent didn't find a search space
				if (_self_repair_index_structure == -1){
					
					// statistic
					System.out.println("["+agent_name+"][PS-TO-PUBLICIZE][SELF_TREE] the agent didn't find a search space. Using last ps of partial states as root");
					
					// using the last ps of the partial states as root node
					for(int i: _trans_seq_ps.keySet()) 
						tree = Math.max(i, tree);
					root = new NodeStateImp(_trans_seq_ps.get(tree), planning_problem, false);
				}
				break;
				
			// used in coodinated domains, in a sense it should be the same for the non-coordinated domains
			case SELF_TREE_COORD_CURRENT_ROOT_AND_PLAN_NO_UPDATING:
				tree_actions = find_reactive_structure_no_removing(exe_actions);
				
				// if we find a self-structure
				if(tree_actions[0]!=-1 && self_structures.size() > 0){
					
					// we use the root and associated plan
					root = self_structures.get(tree_actions[0]).getSearchTree().get_root(); 
					plan = (ArrayList<Integer>) self_structures.get(tree_actions[0]).get_actions_current_window();
					
				}
				
				exe_actions = tree_actions[1];
				break;
				
			case SELF_TREE_COORD_CURRENT_ROOT_AND_PLAN_UPDATING:
				exe_actions = find_reactive_structure_removing_update_root_plan(exe_actions);
				
				if(exe_actions!=-1){
					_self_repair_index_structure=0;
					
					// we use the root and associated plan
					root = self_structures.get(_self_repair_index_structure).getSearchTree().get_root(); 
					plan = (ArrayList<Integer>) self_structures.get(_self_repair_index_structure).get_actions_current_window();
				
				}// else{
					// plan = null;
				// }
				
				break;
			
			default: break;
			// case OWN_ADAPTING_TREE: case ADAPTING_TREE:	break;
			
		}

		if(plan==null) 
			throw new NotExecutingPlanException("["+agent_name+"][FILTER:"+filter.toString()+"][PS-TO-PUBLICIZE] has not plan to execute. filter: "+filter.name());
		if(root==null) 
			throw new NoRootException("["+agent_name+"][FILTER:"+filter.toString()+"][PS-TO-PUBLICIZE] has not plan to execute. filter: "+filter.name());
		
		// statistic
		if(debug_system)
			System.out.println("\n["+agent_name+"][FILTER:"+filter.toString()+"][PS-TO-PUBLICIZE][CURRENT-ROOT] current root: "+root.toPddl()+"\n["+agent_name+"][PS-TO-PUBLICIZE][CURRENT-PLAN] current plan: "+parse_plan(plan)+" [EXE-ACTIONS]"+exe_actions);

		
		if(exe_actions==-1){
			return new SearchSpaceDummy(root, planning_problem).regress_plan(
					new ArrayList<Integer>(plan.subList(0, plan.size())));			
		}
		
		if(exe_actions > plan.size())
			exe_actions = plan.size();

		// we remove the helper and assisted fluents if there exists a TW
		return coord_remove_fluents(exe_actions, 
				new SearchSpaceDummy(root, planning_problem).regress_plan(
				new ArrayList<Integer>(plan.subList(exe_actions, plan.size()))), tw);
	
	}
	
	
	protected List<SharedPartialState> coord_remove_fluents(int exe_actions, List<SharedPartialState> regress_plan, SharedTeamwork tw2) {
		
		// if TW info exists
		if(tw2 != null){

			// statistic: System.out.println("["+agent_name+"][EXECUTED-ACTIONS]"+exe_actions);
			System.out.println("\n\n["+agent_name+"][TEAMWORK-INFO]"+tw2.toString()+
					"\n["+agent_name+"][REGRESS-PLAN]"+regress_plan.toString());
			
			// we remove the assisted fluents
			coord_remove_assisted_fluents_tw(exe_actions, regress_plan, tw2);

			// statistic
			System.out.println("\n["+agent_name+"][REGRESS-PLAN-REMOVE[ASSISTED-FLUENTS]"+regress_plan.toString());

			// we remove the helper fluents
			coord_remove_helper_fluents_tw(exe_actions, regress_plan, tw2);
			
			// statistic
			System.out.println("\n["+agent_name+"][REGRESS-PLAN-REMOVE][HELPER-FLUENTS]"+regress_plan.toString());

		}
		
		return regress_plan;
	}
	
	private void coord_remove_assisted_fluents_tw(int exe_actions, List<SharedPartialState> regress_plan, SharedTeamwork tw2) {
		
		// selecting the set of assisted fluents that receives the agent from other agents
		SharedPartialState[] set_assisted_fluents = tw2.get_assisted_fluents(agent_name);

		// for each set of assisted fluents
		for(int i = 0; i < set_assisted_fluents.length; ++i){

			// if the set of assisted fluents is not empty
			if(set_assisted_fluents[i] != null){

				// get absolute relative time to the plan execution
				int r_t = set_assisted_fluents[i].getTimeInstant() - exe_actions;

				if(r_t >= 0){
					// removing asssited fluents in the last partial states to r_t
					rem_first(set_assisted_fluents[i].getFluents(), regress_plan, r_t);
				}
				
			}
		}
	}

	private void rem_first(List<SharedFluent> set_assisted_fluents, List<SharedPartialState> regress_plan, int r_t) {
		
		for(SharedFluent sf : set_assisted_fluents)
			for(int j = 0; j <= r_t && j < regress_plan.size(); ++j)
				regress_plan.get(j).getFluents().remove(sf);
		
	}

	private void coord_remove_helper_fluents_tw(int exe_actions, List<SharedPartialState> regress_plan, SharedTeamwork tw2) {
		
		// selecting the set of helper fluents that reach the agent to other agents
		SharedPartialState[] set_helper_fluents = tw2.get_helper_fluents(agent_name);
		
		// for each set of helper fluents
		for(int i = 0; i < set_helper_fluents.length; ++i){

			// if the set of helper fluents is not empty
			if(set_helper_fluents[i] != null){
				
				// get absolute relative time to the plan execution
				int r_t = set_helper_fluents[i].getTimeInstant() - exe_actions;

				if(r_t < 0)
					r_t = -1;
				
				// removing helper fluents in the next partial states from r_t
				rem_last(set_helper_fluents[i].getFluents(), regress_plan, r_t);
			
			}
		}
	}

	private void rem_last(List<SharedFluent> fluents, List<SharedPartialState> regress_plan, int r_t) {

		for(SharedFluent sf : fluents)
			for(int j = r_t+1; j < regress_plan.size(); ++j)
				regress_plan.get(j).getFluents().remove(sf);
		
	}


	@Override
	public SharedPartialState coord_get_current_root(int executed_actions) throws NoRootException {
		
		// coordinated scenario
		List<SharedPartialState> pl = coord_update_acts_get_root(executed_actions);
		
		// non-coordinated scenario
		if(pl == null)
			pl = new SearchSpaceDummy(current_root, planning_problem).regress_plan(new ArrayList<Integer>(current_plan.subList(executed_actions, current_plan.size())));
		
		return pl.get(pl.size()-1);
		
	}
	
	/**
	 * 2. requester(A): utilizado para obtener el root node cuando se haga el get_adapted_plan()
	 * @param executed_actions
	 * @return
	 */
	private List<SharedPartialState> coord_update_acts_get_root(int executed_actions) {

		// SearchSpaceByState 			c_search_space;
		// ManagerStructureSelfRepair 	c_manager;
		List<SharedPartialState> 	pl = null;
		
		// removing the actions in the self structures and actions in the current plan
		int exe_act_T = find_reactive_structure_removing_update_root_plan(executed_actions);
		
		if(exe_act_T!=-1){
			
			// assigns the first self structure 2. requester(A): utilizado para obtener el root node cuando se haga el get_adapted_plan()
			_self_repair_index_structure = 0;

			// if(debug_system)
			// 	System.out.println("["+agent_name+"][GET-CURRENT-ROOT] - executed actions/executed actions in T: ["+executed_actions+"/"+exe_act_T+"], search space index: "+((_self_repair_index_structure==-1)?"there is not any search space =(":_self_repair_index_structure));
			
			// c_manager = self_structures.get(_self_repair_index_structure);
			// c_search_space = (SearchSpaceByState) c_manager.getSearchTree();

			// saving current plan and saving the current root node
	    	// updating_current_root_and_plan(c_search_space.get_root(), c_manager.get_actions_current_window());
	    	
	    	// removing actions
			// removing_actions_current_plan(exe_act_T, executed_actions);
			
			pl = new SearchSpaceDummy(current_root, planning_problem).regress_plan(new ArrayList<Integer>(current_plan));
			
		}
		
		return pl;
	}

	@Override
	public boolean multi_protocol_activated() {
		return (((SingleSettings)settings).is_multi_protocol_activate()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}

	@Override
	public boolean verify_conflicts_multi_repair() {
		return (((SingleSettings)settings).verify_conflicts_multi_repair()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}
	
	/**
	 * adapting-own plan : solution is null
	 * adapting plan 	 : solution is not null
	 */
	int saving_executed_actions = 0;
	@Override
	public List<SharedPartialState> adapting_own_plan(FixingPlan solution, PartialState G_t, int exe_actions, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException {
		
		if(((SingleSettings)settings).is_multi_protocol_activate()==SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE)
			throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-OWN-PLAN] multi-repairing is disabled");	
		
		// getting the root node, if we are executing a plan
		NodeState G_r = getting_root_node(exe_actions);

		saving_executed_actions = exe_actions;
		
		if(solution!=null){ // 2. / 4. requester
			return adapting_plan(solution, G_t, to_partial_state(G_r), S, depth);
		}
		
		return adapting_own_plan(solution, G_t, to_partial_state(G_r), S, depth); // TODO WROTE IN THESIS
		
	}
	
	private NodeState getting_root_node(int exe_actions) {
		NodeState G_r = new NodeStateImp(new ArrayList<DSCondition>(),planning_problem);
		
		// we search for a self-structure
		// if(_self_repair_index_structure==-1) OJO 
		_self_repair_index_structure = find_reactive_structure_no_removing(exe_actions)[0];
		
		// if we find a self-structure, we get the root node
		if(_self_repair_index_structure!=-1 && self_structures.size()>0){

			if(debug_system)
				System.out.println("["+agent_name+"][ADAPTING-OWN-PLAN|ADAPTING-PLAN] executed "+exe_actions+" actions in the plan.\n["+agent_name+"][ADAPTING-OWN-PLAN|ADAPTING-PLAN] current plan: (it has not to be updated...!!) "+parse_plan(current_plan));
			
			G_r = self_structures.get(_self_repair_index_structure).getSearchTree().get_root();
			
		}
		
		return G_r;
	}

	private PartialState to_partial_state(NodeState g_r) {
		List<Integer> fluents = new ArrayList<Integer>(g_r.getGoalState().size());
		List<String> n_fluents = new ArrayList<String>(g_r.getGoalState().size());
		
		for(DSCondition ps : g_r.getGoalState()){
			fluents.add(planning_problem.getIndexCondition(ps));
			n_fluents.add(ps.toString());
		}
		
		return new PartialStateImp(fluents, n_fluents, (int)g_r.getRPS().getTimeInstant());
		
	}
	
	private List<SharedPartialState> adapting_own_plan(FixingPlan solution, PartialState G_t, PartialState G_r, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException {

		HashSet<Integer> f_own;
		
		// if there is not conflicts, we apply the search space
		if(!verify_conflicts(G_r, G_t, solution)){ 
			
	
			// statistic
			if(debug_system)
				System.out.println("["+agent_name+"][ADAPTING-OWN-PLAN] there is not conflict between the root nodes:\n\n\tG_r: "+G_r.toString()+"\n\tG_t: "+(G_t != null?G_t:"null")
					+"\n\tG_n: "+(solution==null?"null":solution.getSolution().get(solution.getSolution().size()-1).getFluents())+"\n"+
					"["+agent_name+"][ADAPTING-OWN-PLAN] generating search space (S,d).");


			// we mixed the target partial state
			f_own = new HashSet<Integer>(G_t.getFluents());
			
			// we mixed our partial states
			f_own.addAll(G_r.getFluents());
			
			// we mixed the root node of the joint state plan
			if(solution!=null)
				f_own.addAll(solution.getSolution().get(solution.getSolution().size()-1).getFluents());

			
			return generate_search_space(new NodeStateImp(new ArrayList<Integer>(f_own), null, planning_problem), S, depth);
			
		}
		
		own_adapting_tree = new SearchSpaceAdaptingOwnPlanImp(SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_CONFLICTS, planning_problem, settings, path_eval_benchmarks);

		if(debug_system)
			System.out.println("["+agent_name+"][ADAPTING-OWN-PLAN] there is conflict between the root nodes:\n\n\tG_r: "+G_r.toString()+"\n\tG_t: "+(G_t != null?G_t:"null")
				+"\n\tG_n: "+(solution==null?"null":solution.getSolution().get(solution.getSolution().size()-1).getFluents())+"\n");

		throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-OWN-PLAN] there is not plan solution (conflict = true).");	
	}

	/**
	 * merge the plans by a backward search
	 * @param root {@link NodeState} root node of the search space
	 * @param S {@link ArrayList<DSCondition>} current world state
	 * @param d {@link Integer} maximum depth
	 * @return
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	private List<SharedPartialState> generate_search_space(NodeState root, ArrayList<DSCondition> S, int d) throws NoRootException, GeneralException {
		
		// creating a search space
		own_adapting_tree = new SearchSpaceAdaptingOwnPlanImp(root, d, planning_problem, settings, path_eval_benchmarks);
		
		// calculating the search space finding a plan
		own_adapting_tree.find_plan(S);
		
		// found plan 
		return own_adapting_tree.regress_plan(own_adapting_tree.get_plan_to_root());
		
	}

	@Override
	public String get_own_adapted_plan() throws NoSearchSpaceException {
		ArrayList<Integer> plan_act;
		ManagerStructureSelfRepair c_manager;
		SearchSpaceByState c_search_space;
		
		if(own_adapting_tree == null) throw new NoSearchSpaceException("["+agent_name+"] the own-adapted tree is null.");
		
		// saving current plan and saving the current root node
		try {
			updating_current_root_and_plan(own_adapting_tree.get_root(), own_adapting_tree.get_plan_to_root());
		} catch (GeneralException e) { e.printStackTrace(); }

		plan_act = new ArrayList<Integer>(current_plan);

		// removing search spaces
		saving_executed_actions = find_reactive_structure_removing(saving_executed_actions);
		
 		// if there is self-structure, we concatenate the remaining actions 
 		// and change the total actions supported of the activated search space
 		// otherwise, the agent was iddle and we just save the plan
		_self_repair_index_structure = 0;
		if(self_structures.size() > 0){
			
			// change the total actions supported
			c_manager = self_structures.get(_self_repair_index_structure);
	    	((ManagerStructureSelfRepairImp)c_manager).set_total_act_supported((plan_act!=null)?plan_act.size():0);
		
			// concatenate the remaining actions
			c_search_space 	= (SearchSpaceByState)c_manager.getSearchTree();
			plan_act.addAll(c_search_space.get_plan_remaining()); 
		}

		// reordering time and updating plan in the planning problem variable
	    updating_plan(plan_act, 0, 0, 0); 
	    
    	// sending the new complete plan
		return planning_problem.getBestPlan().toXml();
		
	}
	
	@Override
	public Statistics get_own_adapted_plan_statistic() {
		if(own_adapting_tree!=null)
			return own_adapting_tree.get_statistics();
		return get_adapting_plan_statistics();		
	}

	@Override
	public void remove_own_adapted_plan() {

		// removing fictitious and grouped actions
		for(int i = 0; i<planning_problem.getActions().size();++i){
			if(planning_problem.getActions().get(i) instanceof GAction || planning_problem.getActions().get(i) instanceof FAction){
				planning_problem.removeAction(i);
				--i;
			}
		}
		
		// 1.2 own-adapted plan (receives request)
		own_adapting_tree = null;
		
		// 4. requester(A) adapted plan (receives request)
		adapting_tree = null;
	}
	
	
	
	
	
	
	
	// sort received solutions
	@SuppressWarnings("unchecked")
	@Override
	public List<FixingPlan> sort_solutions(List<FixingPlan> fixed_plans) {
		
		if(((SingleSettings)settings).hasSortMethod()==SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE){
			if(debug_system)
				System.out.println("["+agent_name+"][SORT SOLUTION] Don't apply sort methods. =(");
			return fixed_plans;
		}
		
		FixingPlanComparator comparator = new FixingPlanComparator();

		int sort[] = ((SingleSettings)settings).sortMethods();
		
		// finding the total of comparators (0 = none)
		int t_comp = 0;
		for(int i: sort){
			if(i!=0)
				++t_comp;
		}
		
		// saving the comparators
		Comparator<FixingPlan>[] comparators = new Comparator[t_comp];
		String temp="";
		for(int i=0;i<sort.length;++i){
			if(sort[i]!=0){
				comparators[sort[i]-1]=comparator.getComparator(i);
				temp+=comparators[sort[i]-1].toString()+" ";
			}
		}
		
		Collections.sort(fixed_plans, new FixingPlanSorter(comparators));
		
		if(debug_system)
			System.out.println("["+agent_name+"][SORT SOLUTION] we apply the sort methods: ["+temp+"]");
		
		return fixed_plans;
	}
	
	// este metodo es igual que adapting_plan_relaxed
	// 4. requester (A)
	@Override
	public List<SharedPartialState> adapting_plan(FixingPlan solution, PartialState G_t, PartialState G_r, ArrayList<DSCondition> S, int depth) throws  NoPosiblePlanException, NoRootException, GeneralException{
		
		NodeStateImp root_n;
		
	   	List<SharedPartialState> n_plan = new ArrayList<SharedPartialState>();
	   	
	   	List<Integer> received_plan, plan;
	   	
	   	// we verify conflicts
		if(!verify_conflicts(G_r, G_t, solution)){ // applying search space

			if(debug_system) System.out.println("["+agent_name+"][ADAPTING-PLAN] there is not conflict between the root nodes G_r: "+G_r.toString()+" - G_t: "+(G_t != null?G_t:"null")
					+" - G_n: "+(solution==null?"null":solution.getSolution().get(solution.getSolution().size()-1).getFluents())+"\n");
		   	
		   	ArrayList<PartialState> sol = solution.getSolution();
		   	
	    	// fluents of the root node (own fluents)
			HashSet<Integer> f_own = new HashSet<Integer>(G_r.getFluents());
			
			// adding the fictitious fluent of the received solution (other fluents)
			PartialState ps = sol.get(sol.size()-1);
			HashSet<Integer> f_other = new HashSet<Integer>(ps.getFluents());
			
			// removing other fluents from own fluents
			for(Integer i : ps.getFluents())
				if(f_own.contains(i))
					f_other.remove(i);

			// NEW 3. commitments(B): relaxed adapted plan (failure) and 4. requester(A): adapted plan (receives request)
			node_relaxed_plan=null;
			boolean not_to_root = false;
			if(G_t != null){ // 6. agent is requester and receives a request - adapted plan
				f_own.addAll(G_t.getFluents());

				List<String> temp_s = new ArrayList<String>(f_own.size());
				for(Integer i: f_own)
					temp_s.add(planning_problem.getConditions().get(i).toString());
				
				G_r = new PartialStateImp(new ArrayList<Integer>(f_own), temp_s, G_r.getTimeInstant());

				// used in the get_relaxed_plan() and get_adapted_plan() method
				node_relaxed_plan = new NodeStateImp(new ArrayList<Integer>(f_own), new ArrayList<Integer>(f_other), planning_problem);

				not_to_root=true;
				
				((SingleSettings)settings).set_order_relevant_actions(SETTINGS_REACTIVESTRUCTURE.RP_RELEVANT_ACTIONS_OWN_FIRST);
				
			}else // 1.3. adapted plan, 2. agent as requester and fails , 3. agent as comitted and fails
				((SingleSettings)settings).set_order_relevant_actions(SETTINGS_REACTIVESTRUCTURE.RP_RELEVANT_ACTIONS_OTHER_FIRST);
			
			// preparing new actions
			received_plan = to_fictitious_actions(sol);
			
			// we get a plan.
			root_n = new NodeStateImp(new ArrayList<Integer>(f_own), new ArrayList<Integer>(f_other), planning_problem);
			if(debug_system) System.out.println("["+agent_name+"][ADAPTING-PLAN] generate search space (Pi_n, G_r, S, d) ("+received_plan.toString()+","+ root_n.toPddl()+","+S.toString()+","+depth+")");
			plan = generate_search_space(received_plan, root_n, S, solution.getTypeSolution(), depth);
			
			if(plan!=null){

				String temp = "["+agent_name+"] current plan: "+current_plan.toString()+", G_r time instant: "+G_r.toString()+", plan (before remaining actions): "+parse_plan(plan);
				
				System.out.println(temp);
				
				// agregamos el resto de acciones hasta el root node G_r, G_r es el target partial state pero no precisamente el root node del search space T
				if(!not_to_root){ // 2.2. adapted plan, 3. agent as requester and fails 
					plan.addAll(get_remaining_actions_to_root(G_r.getTimeInstant()));
				}
				
				// total or partial solution: get shared partial states with dummy action or normal actions from the root
				n_plan = get_shared_partial_states(solution, sol.size(), G_r, plan, not_to_root);
				
				// sustituimos las acciones del otro agente o las mias en paralelo
				adapting_tree.parsing_plan((ArrayList<Integer>) received_plan, (ArrayList<Integer>) plan);
				
				if(debug_system){
					
					temp = parse_plan(plan);
						
					System.out.println("\n["+agent_name+"][ADAPTING-PLAN] solution found to the root: "+temp+", current root: "+current_root.toPddl()
							+", current plan to change: "+planning_problem.getBestPlan().getActionPlan().toString()+", G_r: "+G_r.toString()+", ps plan: "+n_plan.toString());
				}

				return n_plan;
			} 
		}else{

			adapting_tree = new SearchSpaceAdaptingImp(SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_CONFLICTS, planning_problem, settings, path_eval_benchmarks);
			throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN] there is not plan solution (conflict = true).");
		
		}
		
		throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN] there is not plan solution.");
		
	}
	
	private String parse_plan(List<Integer> plan) {
		String temp = "<";
		for(Integer p: plan){
			// if it is a dummy action 
			if(p == -1)
				temp+="dummyaction, ";
			else // otherwise, it is a normal action
				temp+=planning_problem.getActions().get(p).toString()+", ";
		}
		int index = temp.lastIndexOf(",");
		if(index != -1)
			temp = temp.substring(0, temp.lastIndexOf(","));
		temp+= ">";
		return temp;
	}

	private List<Integer> get_remaining_actions_to_root(int time) {

		List<Integer> plan = new ArrayList<Integer>((current_plan!=null)?current_plan.size()+1:0);

		for(int i =0; i < current_plan.size();++i){
			if(i>=time){
				try {
					plan.add(current_plan.get(i));
				} catch (Exception e) {	}
			}
		}
		
		System.out.println("["+agent_name+"][GET-REMAINING-ACTIONS][TEST] "+plan); // +" VS. "+current_plan.subList(time, current_plan.size()));
		
		return plan;
		
	}
	
	
	/** 
	 * last version of the method: 2015-11-13
	 * 
	 private List<Integer> get_remaining_actions_to_root(int time) {

		List<Integer> 	plan = new ArrayList<Integer>();

		// we generate the regress plan as a set of state node
		List<NodeState> associated_plan = (new SearchSpaceDummy(current_root, planning_problem)).regress_plan_node_integer(current_plan);
		
		for(NodeState s: associated_plan){
			if(s.getRPS().getTimeInstant()>=time)
				try {
					plan.add(s.getOperator().get(0).getIndexGroundingAction());
				} catch (Exception e) {	}
		}
		
		System.out.println("["+agent_name+"][GET-REMAINING-ACTIONS][TEST] "+plan+" vs "+current_plan.subList(time, current_plan.size()));
		
		return plan;
		
	}
	 * */

	/**
	 * get the regressed partial state from the root node of its current search space ( if it exists ) or the root node of the adapted solution
	 * we use the current adapted plan until the root node, the remaining plan is not considered.
	 * @param solution
	 * @param sol_size
	 * @param G_r root node of the adapted solution
	 * @param plan current adapted plan until de root node
	 * @return shared regressed partial states
	 */
	private List<SharedPartialState> get_shared_partial_states(FixingPlan solution, int sol_size, PartialState G_r, List<Integer> plan, boolean relaxed) {

		ArrayList<SharedPartialState> n_plan;

		NodeState root;
		
		if(solution.getTypeSolution()==T_SOLUTION.TOTAL) 
	   		n_plan = new ArrayList<SharedPartialState>(sol_size);
		else{ // partial solution: with own actions, grouped actions, and other agent actions (dummy actions).
		
			// ya lo hemos obtenido en el self-repair
			if(_self_repair_index_structure!=-1 && !self_structures.isEmpty() && !relaxed){
				
				root = self_structures.get(_self_repair_index_structure).getSearchTree().get_root();
				
			}else
				root = new NodeStateImp(G_r.getFluents(), null, planning_problem);
			
			n_plan = (ArrayList<SharedPartialState>) new SearchSpaceDummy(root, planning_problem).regress_plan(plan);
		}
		
		return n_plan;
	}

	/**
	 * verifying conflicts between the three elements
	 * @param G_r root node of the own agent
	 * @param G_t partial state of the other agent
	 * @param solution plan received from other agents
	 * @return true if there is conflicts and false otherwise
	 */
	private boolean verify_conflicts(PartialState G_r, PartialState G_t, FixingPlan solution) {		
		if(G_r!=null && solution!=null && G_t != null){
			return planning_problem.conflicts(G_r.getFluents(), G_t.getFluents()) 
			|| planning_problem.conflicts(G_r.getFluents(), solution.getSolution().get(solution.getSolution().size()-1).getFluents())
			|| planning_problem.conflicts(G_t.getFluents(), solution.getSolution().get(solution.getSolution().size()-1).getFluents());
		} else { 
			if(G_r!=null && solution!=null){
				return planning_problem.conflicts(G_r.getFluents(), solution.getSolution().get(solution.getSolution().size()-1).getFluents());
			}else{
				return planning_problem.conflicts(G_r.getFluents(), G_t.getFluents());
			}
		}
	}

	@Override
	public Statistics get_adapting_plan_statistics(){
		return adapting_tree.get_statistics();
	}

	private List<Integer> generate_search_space(List<Integer> received_plan, NodeStateImp root, ArrayList<DSCondition> S, T_SOLUTION type, int depth) throws NoPosiblePlanException, NoRootException, GeneralException {
		List<Integer> plan;
		
		// creating a search space
		adapting_tree = new SearchSpaceAdaptingImp(root, depth, planning_problem, settings, path_eval_benchmarks);
		
		switch(type){
			case TOTAL:
				// we return the plan 
				return new ArrayList<Integer>(received_plan);
				
			case PARTIAL:
				// calculating the search space finding a plan
				adapting_tree.plan_adaptation(S, (ArrayList<Integer>) received_plan);
				
				// found plan 
				plan = (ArrayList<Integer>) adapting_tree.get_plan_to_root();

		        if(plan != null) return plan;
		        else throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN] there is not adaptation plan solution =( ");
		}
		
		throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN] there is not adaptation plan solution =(, type of solution is not well defined.");
	}

	private List<Integer> to_fictitious_actions(List<PartialState> partial_state) {
		
		List<Integer> received_plan = new ArrayList<Integer>(partial_state.size());
		int dur;
		String name;
		
		for(int i=0;i<partial_state.size()-1;++i){
			
			dur = partial_state.get(i+1).getTimeInstant()-partial_state.get(i).getTimeInstant();
			name = "act-unknow-"+planning_problem.getActions().size();
			
			received_plan.add(planning_problem.addFictitiousAction(name, null, (ArrayList<Integer>) partial_state.get(i).getFluents(), 
					(ArrayList<Integer>) partial_state.get(i+1).getFluents(),String.valueOf(dur)));
			
		}
		
		return received_plan;
	}
	
	@Override
	public boolean verify_conflicts_adapted_plan() {
		return (((SingleSettings)settings).verify_conflicts_adapted_plan()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}
	
	@Override
	public String get_adapted_plan() throws NoRootException, NoSearchSpaceException, GeneralException {
		
		if(adapting_tree == null)
			throw new NoSearchSpaceException("["+agent_name+"] the adapting search space is null.");
		if(self_structures.isEmpty())
			throw new NoRootException("["+agent_name+"] self structure is empty, there is not root node. ");

		ArrayList<Integer> plan_act;

		ManagerStructureSelfRepair c_manager;
		SearchSpaceByState c_search_space;
		
		// the self structure should never be an empty value		
		c_manager 		= self_structures.get(_self_repair_index_structure);
		c_search_space 	= (SearchSpaceByState)c_manager.getSearchTree();
		
		// saving the plan with fictitious actions
		if(node_relaxed_plan!=null){ // 3.commitments, 4. requester(A): adapted plan (receives request)
			updating_current_root_and_plan(node_relaxed_plan, ((SearchSpaceAdaptingImp)adapting_tree).temp_plan_adapt);
		}else // others
			updating_current_root_and_plan(c_search_space.get_root(), ((SearchSpaceAdaptingImp)adapting_tree).temp_plan_adapt);
		
		plan_act = new ArrayList<Integer>(adapting_tree.get_plan_to_root()); 

		System.out.println("["+agent_name+"][ADAPTED-PLAN]plan to root: "+parse_plan(plan_act)+", remaining plan to goals: "+parse_plan(c_search_space.get_plan_remaining())+", root: "+c_search_space.get_root().getRPS().toString());
		
    	// concatenating the remaining actions and changing the associated plan of the activated search space
		plan_act.addAll(c_search_space.get_plan_remaining()); 
			
	    ((ManagerStructureSelfRepairImp)c_manager).set_total_act_supported((current_plan!=null)?current_plan.size():0);
		
		// reordering time and updating plan in the planning problem variable
    	updating_plan(plan_act, 0, 0, 0); 
    	
    	// sending the new complete plan
		return planning_problem.getBestPlan().toXml();
		
	}

	
	
	

	
	
	
	// multi-repair : teamwork committed - adapting plan relaxed
	NodeState node_relaxed_plan;
	@Override
	public List<SharedPartialState> adapting_plan_relaxed(FixingPlan solution, PartialState G_t, PartialState G_r, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException{

	   	NodeStateImp root_n;
	   	
	   	List<SharedPartialState> n_plan = new ArrayList<SharedPartialState>();

	   	List<Integer> received_plan, plan;
		
	   	// we verify conflicts
		if(!verify_conflicts(G_r, G_t, solution)){ // applying search space

			if(debug_system) System.out.println("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is not conflict between the root nodes G_r: "+G_r.toString()+" - G_t: "+(G_t != null?G_t:"null")
					+" - G_n: "+(solution==null?"null":solution.getSolution().get(solution.getSolution().size()-1).getFluents())+"\n");
		   	
		   	ArrayList<PartialState> sol = solution.getSolution();
		   	
	    	// fluents of the root node (own fluents)
			HashSet<Integer> f_own = new HashSet<Integer>(G_r.getFluents());
			
			// adding the fictitious fluent of the received solution (other fluents)
			PartialState ps = sol.get(sol.size()-1);
			HashSet<Integer> f_other = new HashSet<Integer>(ps.getFluents());
			
			// removing other fluents from own fluents
			for(Integer i : ps.getFluents())
				if(f_own.contains(i))
					f_other.remove(i);

			// NEW 3. commitments(B): relaxed adapted plan (failure) and 4. requester(A): adapted plan (receives request)
			node_relaxed_plan=null;
			if(G_t != null){
				f_own.addAll(G_t.getFluents());

				List<String> temp_s = new ArrayList<String>(f_own.size());
				for(Integer i: f_own)
					temp_s.add(planning_problem.getConditions().get(i).toString());
				
				G_r = new PartialStateImp(new ArrayList<Integer>(f_own), temp_s, G_r.getTimeInstant());

				// used in the get_relaxed_plan() method
				node_relaxed_plan = new NodeStateImp(new ArrayList<Integer>(f_own), new ArrayList<Integer>(f_other), planning_problem);
			}
			
			// preparing new actions
			received_plan = to_fictitious_actions(sol);

			// we get a plan.
			root_n = new NodeStateImp(new ArrayList<Integer>(f_own), new ArrayList<Integer>(f_other), planning_problem);
			if(debug_system) System.out.println("["+agent_name+"][ADAPTING-PLAN-RELAXED] generate search space (Pi_n, G_r, S,d) from the root: "+root_n.toPddl()
				+" received plan: "+received_plan.toString()+" type solution: "+solution.getTypeSolution()+" depth: "+depth);
			plan = generate_search_space_relaxed(received_plan, root_n, S, solution.getTypeSolution(), depth);
			
			if(plan!=null){
				
				String temp = "["+agent_name+"] current plan: "+current_plan.toString()+", G_r time instant: "+G_r.toString()+", plan (before remaining actions): "+parse_plan(plan);
				
				// no se agregan el resto de acciones hasta el root node porque utiliza
				// directamente un root node
				
				// total or partial solution: get shared partial states with dummy action or normal actions from the root
				// G_r = GownB U GtTW
				n_plan = get_shared_partial_states(solution, sol.size(), G_r, plan, true);
				
				// NEW FOR RELAXED - generating the rescheduling info 
				relaxed_adapting_tree.reschedule((ArrayList<Integer>) received_plan, (ArrayList<Integer>) plan);

				// sustituimos las acciones del otro agente o las mias en paralelo 
				relaxed_adapting_tree.parsing_plan((ArrayList<Integer>) received_plan, (ArrayList<Integer>) plan);
				
				if(debug_system){
					
					temp = parse_plan(plan);
						
					System.out.println("\n["+agent_name+"][ADAPTING-PLAN-RELAXED] solution found to the root: "+temp+", current root: "+current_root.toPddl()
							+", current plan to change: "+planning_problem.getBestPlan().getActionPlan().toString()+", G_r: "+G_r.toString()+", ps plan: "+n_plan.toString());
				}
				
				return n_plan;
			}
		}else{
			
			if(debug_system) System.out.println("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is conflict between the root nodes G_r: "+G_r.toString()+" - G_t: "+(G_t != null?G_t:"null")
					+" - G_n: "+(solution==null?"null":solution.getSolution().get(solution.getSolution().size()-1).getFluents())+"\n");
		   	
			relaxed_adapting_tree = new SearchSpaceAdaptingRelaxedImp(SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_CONFLICTS, planning_problem, settings, path_eval_benchmarks);
			throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is not plan solution (conflitc = true).");
		
		}
		
		throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is not plan solution.");
		
	}

	private List<Integer> generate_search_space_relaxed(List<Integer> received_plan, NodeStateImp root, ArrayList<DSCondition> S, T_SOLUTION type, int depth) throws NoPosiblePlanException, NoRootException, GeneralException {
		List<Integer> plan;
		
		// creating a search space
		((SingleSettings)settings).set_order_relevant_actions(SETTINGS_REACTIVESTRUCTURE.RP_RELEVANT_ACTIONS_OTHER_FIRST);
		relaxed_adapting_tree = new SearchSpaceAdaptingRelaxedImp(root, depth, planning_problem, settings, path_eval_benchmarks);
		
		switch(type){
			case TOTAL:
				// we return the plan 
				return new ArrayList<Integer>(received_plan);
				
			case PARTIAL:
				// calculating the search space finding a plan
				relaxed_adapting_tree.plan_adaptation(S, (ArrayList<Integer>) received_plan);
				
				// found plan 
				plan = (ArrayList<Integer>) relaxed_adapting_tree.get_plan_to_root();

		        if(plan != null) return plan;
		        else throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is not plan solution =( ");				
		}
		
		throw new NoPosiblePlanException("["+agent_name+"][ADAPTING-PLAN-RELAXED] there is not plan solution =(, type of solution is not well defined.");
	}

	@Override
	public Statistics get_adapting_plan_relaxed_statistics(){
		return relaxed_adapting_tree.get_statistics();
	}
	
	@Override
	public String get_adapted_plan_relaxed() throws NoRootException, GeneralException, NoSearchSpaceException {

		if(relaxed_adapting_tree == null)
			throw new NoSearchSpaceException("["+agent_name+"][RELAXED-ADAPTED-PLAN] the adapting search space is null.");

		ArrayList<Integer> plan_act;

		ManagerStructureSelfRepair c_manager;
		SearchSpaceByState c_search_space;

		// saving the plan with fictitious actions to the root node GownB U GrTW
		updating_current_root_and_plan(node_relaxed_plan,  ((SearchSpaceAdaptingImp)relaxed_adapting_tree).temp_plan_adapt);
		
		plan_act = new ArrayList<Integer>(relaxed_adapting_tree.get_plan_to_root());

		// if there is self-structure, we concatenate the remaining actions 
		// and change the total actions supported of the activated search space
		// otherwise, the agent was iddle and we just save the plan
		if(_self_repair_index_structure!=-1){
			
			// change the total actions supported
			c_manager = self_structures.get(_self_repair_index_structure);
			((ManagerStructureSelfRepairImp)c_manager).set_total_act_supported((plan_act!=null)?plan_act.size():0);
			
			// concatenating the remaining actions
			c_search_space 	= (SearchSpaceByState)c_manager.getSearchTree();
			plan_act.addAll(c_search_space.get_plan_remaining());
			
			System.out.println("["+agent_name+"][RELAXED-ADAPTED-PLAN]plan to root: "+parse_plan(plan_act)+", remaining plan to goals: "+parse_plan(c_search_space.get_plan_remaining())+", root: "+c_search_space.get_root().getRPS().toString());
			
		}else 
			System.out.println("["+agent_name+"][RELAXED-ADAPTED-PLAN]plan to root: "+parse_plan(plan_act));
		
		// reordering time and updating plan in the planning problem variable
    	updating_plan(plan_act, 0, 0, 0); 

    	// sending the new complete plan
		return planning_problem.getBestPlan().toXml();
	}

	@Override
	public List<Integer> get_rescheduling_info() {
		
		if(relaxed_adapting_tree!=null)
			return ((SearchSpaceAdaptingRelaxed)relaxed_adapting_tree).get_rescheduling_plan(); 
		
		return null;
		
	}

	
	@Override
	public void coord_update_associated_plan_relaxed_adapted_plan(int executed_actions, int delay, ArrayList<DSPlan> plan) {
		
		// updating the current plan
		planning_problem.setPlan(plan);
		
		List<Integer> l_plan = planning_problem.getBestPlan().to_list_int(); 
		
		System.out.println("["+agent_name+"]PLAN AS LIST OF INTEGER: "+l_plan.toString());
		
		// there is not a self-structure
		if(self_structures.size() == 0){
			
			System.out.println("["+agent_name+"][NO-SELF-STRUCTURE]");
			
			// updating the current associated plan
			updating_current_root_and_plan(current_root, l_plan);
			
		}else{
			
			System.out.println("["+agent_name+"][EXECUTED-ACTIONS]"+executed_actions+"[DELAY]"+delay);
			
			// there is a self-structure
			executed_actions = this.find_reactive_structure_removing_update_root_plan(executed_actions);
			
			int act_supported = self_structures.get(0).get_total_act_supported() - executed_actions + delay;
			
			System.out.println("["+agent_name+"][EXECUTED-ACTIONS]"+executed_actions+"[act_supported]"+act_supported);
			
			self_structures.get(0).reordering(new ArrayList<Integer>(l_plan.subList(0, act_supported)));
			
		}
	}
	

	
	// multi-repair : teamwork
	@Override
	public SharedTeamwork get_teamwork() {
		return tw;
	}

	@Override
	public SharedTeamwork update_teamwork(SharedTransition transition, double time_created) {
		if(tw !=null){
			tw.update(transition);
		}else{
			tw = new SharedTeamworkImp(transition, time_created);
		}
		return tw;
	}

	@Override
	public void update_teamwork(SharedTeamwork teamwork) {
		
		// teamwork = coord_update_teamwork(teamwork);
		
		tw = teamwork;
	}
	
	@Override
	public boolean update_teamwork(double time) {
		boolean remove = true;
		if(tw !=null){

			System.out.println("\n\n\n["+agent_name+"][UPDATE-TEAMWORK][REMOVE][TIME] "+time);
			
			// for all agents in the TW we remove the transitions, if we remove all transitions of our agent then we remove the TW
			if(remove_last_transitions(tw, time)) return true;
			
			// if there is not self-structure
			if(remove_no_self_structure(tw, (int) time)) return true;

			// if there are self-structures
			if(remove_self_structures(tw, (int)time)) return true;
			
			remove = false;
		}
		
		return remove;
	}

	private boolean remove_last_transitions(SharedTeamwork tw2, double time) {

		boolean remove = false;

		int from;
		int to;
		int last_time_helper_fluents=0;
		int last_time_assisted_fluents=0;
		int executed_time = tw.get_executed_time(time);
		
		// for all agents in TW
		List<String> agents = tw.getAgents();
		for(String ag: agents){
			
			// get last time where the agent is assisted or helper
			last_time_assisted_fluents = tw.get_last_time_assisted_fluents(ag);
			last_time_helper_fluents = tw.get_last_time_helper_fluents(ag);
			
			// if time relative to TW (executed time) is bigger, then we remove transitions of the agent  
			if(last_time_helper_fluents <= executed_time && last_time_assisted_fluents <= executed_time){
				System.out.println("\n["+agent_name+"][UPDATE-TEAMWORK][REMOVE-TW][YES] last transition for agent "+ag+" ("+remove+").");

				// if it is the last transition for our agent we remove TW					
				if(ag.equals(agent_name)){
					remove = (tw.remove((int)time) || tw.getAgents().indexOf(agent_name)==-1);
				}else{
					// otherwise, we remove transitions of other agents
					from 	= (int) tw.get_last_removed_time();
					to 		= from + executed_time;
					for(int i=from;i<=to;++i)
						tw.remove(i+tw.get_created_at_time());
				}
				
				// if TW is empty, we remove the TW
				if(tw.getAgents().isEmpty() || remove){
					remove_teamwork();
					remove=true;
					break;
				}
			}
		}
		return remove;
	}

	private boolean remove_no_self_structure(SharedTeamwork tw2, int time) {
		boolean remove = false;
		// if there is not self-structure, then we remove the transition
		if(self_structures.size() == 0){
			remove = tw.remove(time) || tw.getAgents().indexOf(agent_name)==-1;
			System.out.println("\n["+agent_name+"][UPDATE-TEAMWORK][NO-REMOVE-TW] there is not self-structure ("+remove+").");
		}
		return remove;
	}
	
	private boolean remove_self_structures(SharedTeamwork tw2, int time) {

		int executed_time=0;
		int act_supported = 0;
		int from;
		int to;
		int selected_t = 0;
		boolean remove=false;
		
		executed_time 	= tw.get_executed_time(time);
		from 			= (int) tw.get_last_removed_time();
		
		// for each self-structure
		for(ManagerStructureSelfRepair t : self_structures){

			act_supported = t.get_total_act_supported();
			
			// we search for the current search space
			if(selected_t < from){
				selected_t+=act_supported;
				executed_time-=act_supported;
				continue;
			}
			
			remove = false;

			// if the executed action is bigger that actions supported in T then, we remove the transitions
			if(executed_time >= act_supported){
				tw.set_last_removed_time(from+act_supported);
				executed_time-=act_supported;
				
				for(int i=from;i<=tw.get_last_removed_time();++i){
					remove = tw.remove(i+tw.get_created_at_time()) || tw.getAgents().indexOf(agent_name)==-1;
				}
			}else { 
				// otherwise, we mark the transition as reached
				to = from + executed_time;
				for(int i=from;i<=to;++i)
					tw.reached(i+tw.get_created_at_time());

				System.out.println("["+agent_name+"][UPDATE-TEAMWORK][REMOVE-TW][NO] there is a self-structure. actions supported ["+act_supported+"] bigger as executed_time ["+executed_time+"] ("+remove+").");
				break;
			}
			
			// if TW is empty, we remove the TW
			if(tw.getAgents().isEmpty()){
				remove_teamwork();
				remove=true;
				break;
			}
		}
		
		return remove;
	}

	@Override
	public void update_teamwork(int time, List<Integer> reschedul) {
		tw.update(time, reschedul);
	}
	
	@Override
	public void remove_teamwork() {
		tw = null;
	}

	@Override
	public boolean verify_conflicts_teamwork() {
		return (((SingleSettings)settings).verify_conflicts_teamwork()==SETTINGS_REACTIVESTRUCTURE.RP_SS_YES)?true:false;
	}

	// multi-repair: teamwork committed
	@Override
	public List<SharedPartialState> check_synergy(ArrayList<PartialState> Gi_t, int exe_act, ArrayList<DSCondition> S) throws GeneralException, NotExecutingPlanException, NoRootException {
		
		List<Integer> plan; 

		if(relaxed_adapting_tree!=null){ // the agent adapted its plan in a relaxed way to continue helping other agent
			plan = relaxed_adapting_tree.get_plan_to_root();
		}else {
			if(own_adapting_tree != null) // the agent adapted its plan to help other agent
				plan = own_adapting_tree.get_plan_to_root();
			else{ // the agent helps other agent but it receives the info tw from the CP
				
				// finding the search space and updating current plan window, root and plan to execute
				this.find_reactive_structure_removing_update_root_plan(exe_act);
				
				plan = new ArrayList<Integer>(current_plan);
			}
		}

		plan = new ArrayList<Integer>(plan.subList(exe_act, plan.size()));
		
		if(debug_system){
			String temp = "";
			
			for(Integer p :plan)
				temp+=planning_problem.getActions().get(p).toString()+"\t";
			
			temp = "Gi_t: "+Gi_t.toString()+", current plan: "+parse_plan(current_plan)+", plan (actions not executed yet): "+
			temp + ", executed actions: "+exe_act+", current world-state: "+S.toString();
	
			if(debug_system)
				System.out.println("["+agent_name+"][SYNERGY] " + temp);
		}
		
		return check_synergy(Gi_t.get(0).getFluents(), plan, S, exe_act);
	}
	
	private List<SharedPartialState> check_synergy(List<Integer> g_t, List<Integer> plan, ArrayList<DSCondition> S, int exe_act) throws NotExecutingPlanException, NoRootException {
		
		boolean found = false;
		int act;

		int size = g_t.size();
		int count = 0;
		
		// we avaluate in the first current state
		count = 0;
		for(int f=0; f < size; ++f){
			if (S.contains(planning_problem.getConditions().get(g_t.get(f)))){
				++count;
				if(count == size){
					found = true;
					break;
				}
			}
		}
		
		// we simulate the plan execution
		while(!plan.isEmpty() && !found){

			act = plan.remove(0);
			if(act != -1)
				S = planning_problem.simulateExecution(S, act);
			
			count = 0;
			for(int f=0; f < size; ++f){
				if (S.contains(planning_problem.getConditions().get(g_t.get(f)))){
					++count;
					if(count == size){
						found = true;
						break;
					}
				}
			}
		}
		
		if(!found)
			return null;
		
		return get_ps_to_publicize(exe_act, SEARCH_SPACE_FILTER.OWN_ADAPTING_TREE);
		
	}



	
	
	@Override
	public boolean is_central_planner_enable() {
		return ((SingleSettings)settings).is_central_planner_enable();
	}
	
	
	
	
	
	// general get method
	@Override
	public boolean conflicts(ArrayList<PartialState> partial_state, int executed_actions, boolean simulate) {

		boolean conflict = false;
		int time_instant;
		List<NodeState> associated_plan;

		_self_repair_index_structure = find_reactive_structure_no_removing(executed_actions)[0];
		
		// there is not plan conflict if there is current plan execution
		if(_self_repair_index_structure!=-1 && self_structures.size() > 0){		
			
			associated_plan = self_structures.get(_self_repair_index_structure).get_associated_plan();

			// statistic
			if(debug_system){
				System.out.println("["+agent_name+"][VERIFY-CONFLICTS-RECEIVE] executed actions: "+executed_actions
				+", we are executing a plan, associated plan: "+associated_plan.toString()
				+", partial states: "+partial_state.toString());
			}
			
			for(int i = 0; i < partial_state.size() && !conflict; ++i){
				
				time_instant = partial_state.get(i).getTimeInstant();
				
				int t_associated_plan = (simulate)?time_instant+1:time_instant;
				
				// System.out.println("["+agent_name+"][VERIFY-CONFLICTS-RECEIVE] time associated plan: "+t_associated_plan+", associated: "+associated_plan.get(t_associated_plan).toPddl()+" vs partial state: "+partial_state.get(i).toString());
				
				// SI ES ACCION FICTICIA NO TIENE CONFLICTOS PORQUE EL PS SERÍA VACIO.
				if(t_associated_plan < associated_plan.size()){
					
					conflict = associated_plan.get(t_associated_plan).conflicts(partial_state.get(i).getFluents());
				}
			}
		}
		
		return conflict;
	}
	
	@Override
	public boolean conflicts(ArrayList<PartialState> partial_state, boolean simulate) {
		
		boolean conflict = false;
		int time_instant;
		List<SharedPartialState> associated_plan = own_adapting_tree.get_ps_to_publicize();

		for(int i = 0; i < partial_state.size() && !conflict; ++i){
			
			time_instant = partial_state.get(i).getTimeInstant();
			
			int t_associated_plan = (simulate)?time_instant+1:time_instant;
			
			if(t_associated_plan < associated_plan.size()){
			
				PartialState p = filter(associated_plan.get(t_associated_plan), FILTER.BYFLUENTS, false);
				
				conflict = planning_problem.conflicts(p.getFluents(), partial_state.get(i).getFluents());
			
			}
		}
		
		return conflict;
	}

	@Override
	public ArrayList<NodeState> regress_plan(NodeState root, ArrayList<DSPlanAction> plan) {

		ArrayList<NodeState> node = new ArrayList<NodeState>((plan!=null)?plan.size()+1:0);
		
		if((plan!=null)){
			DSAction act;
			PS new_state;
			
			new_state = root.getRPS();
			((PSImp)new_state).time_instant=plan.size(); // necesario para saber el last time instant
			node.add(new NodeStateImp(new_state,planning_problem, true));
			
			for(int i = plan.size()-1; i >= 0; --i) {
				act = planning_problem.getActions().get(plan.get(i).getIndexGroundingAction());
				
				new_state = planning_problem.regress(new_state,act);
				((PSImp)new_state).time_instant=i;
				node.add(0,new NodeStateImp(new_state,planning_problem, true));
				
				// we add the operator
				node.get(0).getOperator().clear();
				node.get(0).getOperator().add(plan.get(i));
			}
		}
		
		return node;
	}

	public PlanningProblem getPlanningProblem() {
		return planning_problem;
	}
	
	public RSettings getSettingsR() {
		return ((SingleSettings)settings).getRSettings();
	}

	@Override
	public GeneralSettings get_settings() {
		return settings;
	}

	@Override
	public PartialState filter(SharedPartialState v, FILTER filter, boolean add_fluent) {

		ArrayList<Integer> t_fluents = filter_shared_ps(v, filter, add_fluent);

		ArrayList<String>  s_fluents = new ArrayList<String>(t_fluents.size());
		for(Integer f: t_fluents)
			s_fluents.add(planning_problem.getConditions().get(f).toString());
		
		if(t_fluents.size()!=0)
			return new PartialStateImp(t_fluents, s_fluents, v.getTimeInstant());
		else
			return new PartialStateImp(v.getTimeInstant());

	}
	
	@Override
	public SharedPartialState filter(SharedPartialState v, FILTER filter) {

		ArrayList<DSCondition>  s_fluents = new ArrayList<DSCondition>();
		
		// filtering the shared ps
		ArrayList<Integer> t_fluents = filter_shared_ps(v, filter, false);
		for(Integer f: t_fluents)
			s_fluents.add(planning_problem.getConditions().get(f));
		
		return new SharedPartialStateImp(s_fluents, v.getTimeInstant(), true);

	}

	private ArrayList<Integer> filter_shared_ps(SharedPartialState sharedVariable, FILTER filterType,  boolean add_fluent) {
		List<SharedFluent> sharedFluents;
		SharedFluent sharedFluent;
		
		Set<Integer> setOfFluentsFiltered;
		DSCondition effectCondition;
		int indexFluentInEffectOrCondition; 
		
		sharedFluents = sharedVariable.getFluents();
		setOfFluentsFiltered   = new TreeSet<Integer>();
		
		int i = sharedFluents.size()-1;
		while(i >= 0) { 
			sharedFluent = sharedFluents.get(i);

			/** non-coordinated if the agent knows the variable of a given fluent **/
			int isVariableOfFluentKnow = non_coordIsVariableOfFluentKnow(sharedFluent);
			
			/** if we known the variable **/
			if(isVariableOfFluentKnow != -1){

				/** We search the fluent first in the effects and then in the conditions of the actions of the agent
				 ** we get the index of the fluent or -1 if the fluent does not exist **/
				effectCondition = createFluent(sharedFluent, isVariableOfFluentKnow);
				indexFluentInEffectOrCondition = planning_problem.getIndexEffects(effectCondition);
				
				switch(filterType){
					default:
					case BYFLUENTS:
						if(indexFluentInEffectOrCondition != -1) 
							setOfFluentsFiltered.add(indexFluentInEffectOrCondition);
						break;
					// verifying just in the effects 
					// : to generate the search spaces by fluents - cuando nos llegan los partial states de los planes que van a ejecutar los otros agentes
					case BYFLUENTS_JUST_IN_EFFECTS: 
						if(planning_problem.getConditions().get(indexFluentInEffectOrCondition).getCondition()==DSCondition.ASSIGN)
							setOfFluentsFiltered.add(indexFluentInEffectOrCondition);
						break;
					case BYVARIABLES:
						if(indexFluentInEffectOrCondition==-1){ 
							
							// we know the variable but we don't know the value, we change fluent's value to unknown value
							effectCondition.setValueExpression(planning_problem.createValueExpression("unknown", DSValueExpression.CONSTANT_OBJECT));
							
							// we add the fluent to our knowledge
							indexFluentInEffectOrCondition = planning_problem.addCondition(effectCondition);
						}
						
						setOfFluentsFiltered.add(indexFluentInEffectOrCondition);
						
						break;

					case BYVARIABLES_SAVE_FLUENT:
						if(indexFluentInEffectOrCondition==-1){ 
							
							// if we do not know the value, we change it by unknown
							if(planning_problem.getObjects().indexOf(effectCondition.getValueExpression().toString())==-1){
							
								// we change fluent's value to unknown value
								effectCondition.setValueExpression(planning_problem.createValueExpression("unknown", DSValueExpression.CONSTANT_OBJECT));
							}
							
							// we add the fluent to our knowledge
							indexFluentInEffectOrCondition = planning_problem.addCondition(effectCondition);
						}
						
						setOfFluentsFiltered.add(indexFluentInEffectOrCondition);
						break;
						
				}
				
				effectCondition = null;
			}
			--i;
		}

		// adding fictitious fluent
		if(add_fluent){
			indexFluentInEffectOrCondition = planning_problem.addCondition(sharedVariable.getTimeInstant()+"-"+planning_problem.getVariables().size(), "unknown", add_fluent);
			setOfFluentsFiltered.add(indexFluentInEffectOrCondition);
		}
		
		return new ArrayList<Integer>(setOfFluentsFiltered);
	}

	private DSCondition createFluent(SharedFluent fluent, int isVariableOfFluentKnow) {
		return planning_problem.createGroundedCondition(fluent.getCondition(), 
				planning_problem.getVariables().get(isVariableOfFluentKnow), 
				planning_problem.createValueExpression(fluent.getValue(), DSValueExpression.CONSTANT_OBJECT));
	}

	/**
	 * function that check if the agent knows the variable of a given fluent
	 * 
	 * @param fluent SharedFluent received from other agents
	 * @return Integer value with the index position of the variable. -1 if the agent does not know the variable
	 */
	private int non_coordIsVariableOfFluentKnow(SharedFluent fluent) {

		DSVariables var = new NVariables(fluent.getVariable().getName(), fluent.getVariable().getParams());
		if(planning_problem.getVariablesIndex().containsKey(var)){
			return planning_problem.getVariablesIndex().get(var);
		}
		
		return -1;
	}

	@Override
	public ArrayList<SharedVariable> get_services(){

		ArrayList<SharedVariable> services = new ArrayList<SharedVariable>();
		THashSet<String>[] var = ((PlanningProblemRPImp)planning_problem).getServices();
		DSVariables var_to_save;
		
		for(int i = 0; i < var.length;++i){
			if(var[i]==null) continue;
			var_to_save = planning_problem.getVariables().get(i);
			
			services.add(new SharedVariableImp(var_to_save.getName(), var_to_save.getParams(), new ArrayList<String>(var[i]),null));
		}
		
		if(settings.getTypeExecution()==SETTINGS_REACTIVESTRUCTURE.RP_TE_SAVING_SERVICES_TEST && saved_services == false){
			saving_services(services);
		}
		
		return services;
	}
	
	@Override
	public boolean is_saving_services_mode() {
		return (settings.getTypeExecution()==SETTINGS_REACTIVESTRUCTURE.RP_TE_SAVING_SERVICES_TEST);
	}

	private boolean saved_services=false;
	private void saving_services(ArrayList<SharedVariable> services) {

		String file_name = settings.getDirRP();
		Files files = new Files(file_name+"/services.txt", true);
		
		for(SharedVariable v:services){
			for(String s:v.getReachableValues()){
				files.write(planning_problem.getDomainName()+" "+planning_problem.getProblemName()+" "+agent_name+" "
					+v.toPddl() +" "+s+" <"+v.toPddl()+","+s+">\n");
			}
		}
		files.close();
		saved_services = true;
	}

	@Override
	public Fluent[] filter_services(ArrayList<SharedVariable> servicesAsFluents) throws BadProblemDefinitionException {
		
		ArrayList<Fluent> f_services = new ArrayList<Fluent>(servicesAsFluents.size());
		
		NVariables T_VARIABLE_OF_FLUENT;
		DSVariables fluentVariable = null;
		int indexVariable;
		int indexReachedValue;
		DSCondition cond;
		
		/** for each received services **/
		for(SharedVariable fluent : servicesAsFluents){
			
			try {
				
				T_VARIABLE_OF_FLUENT = new NVariables(fluent.getName(), fluent.getParams());
				
				/** if we known the variable **/
				if(planning_problem.getVariablesIndex().containsKey(T_VARIABLE_OF_FLUENT)){
					
					/** we get the variable in the agent knowledge **/
					indexVariable = planning_problem.getVariablesIndex().get(T_VARIABLE_OF_FLUENT);
					fluentVariable = planning_problem.getVariables().get(indexVariable);
					
					/** we iterate throughout each reached value, removing the unknown values **/
					indexReachedValue = 0;
					while(indexReachedValue < fluent.getReachableValues().size()){
						/** if the name of the value is unknown to the agent, we remove from the received services **/
						if(!fluentVariable.getInitialTrueValue().equals(fluent.getReachableValues().get(indexReachedValue)) 
							&& !fluentVariable.getInitialFalseValues().contains(fluent.getReachableValues().get(indexReachedValue))){
							fluent.getReachableValues().remove(indexReachedValue);
						}else /** otherwise, we iterate to the next element **/
							++indexReachedValue;
					}
					
					/** if we do not remove all values **/
					if(fluent.getReachableValues().size()!=0){
						/** we create tuples <v,p> of fluents **/
						for(String value : fluent.getReachableValues()){
							cond =  planning_problem.createGroundedCondition(DSCondition.EQUAL, 
									fluentVariable, planning_problem.createValueExpression(value, DSValueExpression.CONSTANT_OBJECT));
							
							f_services.add(new FluentImp(planning_problem.getIndexCondition(cond), indexVariable, fluentVariable.getVariable().toString(), value, DSCondition.EQUAL));
						}
					}
				}
			} catch (NullPointerException e) {
				throw new BadProblemDefinitionException(" possible bad definition in the problem 3.1. with the fluent "+((fluentVariable!=null)?fluentVariable.toString():"null"));
			}
		}
		
		/** we prepare the fluents to return **/
		Fluent[] fluentsKnown = new Fluent[f_services.size()];
		for(indexReachedValue=0;indexReachedValue < f_services.size(); ++indexReachedValue)
			fluentsKnown[indexReachedValue] = f_services.get(indexReachedValue);
		
		return fluentsKnown;
		
	}

	
	
	

	
	
	
	
	// MAIN methods

	// GUI AND TEST - 11/04/2014
	@Override
	public ArrayList<DSCondition> filterRelevantVar(ArrayList<DSCondition> goal_state) {
		
		ArrayList<DSCondition> filtered_goal_state=null;
		if (_temp_current_tree_to_create != null)
			filtered_goal_state = ((SearchSpaceByState)_temp_current_tree_to_create).filterRelevantVar(goal_state);
		
		return filtered_goal_state;
	}
	
	public ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state) {
		
		ArrayList<DSCondition> filtered_goal_state=null;
		if (_temp_current_tree_to_create != null)
			filtered_goal_state = ((SearchSpaceByState)_temp_current_tree_to_create).filterNextAction(goal_state);
		
		return filtered_goal_state;
	}
	
	public ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state, int timeStep) {
		
		ArrayList<DSCondition> filtered_goal_state=null;
		if (_temp_current_tree_to_create != null)
			filtered_goal_state = ((SearchSpaceByState)_temp_current_tree_to_create).filter_next_action(goal_state, timeStep);
		
		return filtered_goal_state;
	}

	
	
	
	
	@Override
	public Integer getRepairedStability() {
		if(_self_repair_index_structure!=-1){
			return ((SearchSpaceByState)self_structures.get(_self_repair_index_structure).getSearchTree()).getRepairedStability();
		}else
			if(_temp_current_tree_to_create!=null)
				return ((SearchSpaceByState)_temp_current_tree_to_create).getRepairedStability();
		return null;
	}

	@Override
	public Integer getRepairedStability(int from, int WINDOW_SIZE) {
		if(_self_repair_index_structure!=-1){
			return ((SearchSpaceByState)self_structures.get(_self_repair_index_structure).getSearchTree()).getRepairedStability(from, WINDOW_SIZE);
		}else
			if(_temp_current_tree_to_create!=null)
				return ((SearchSpaceByState)_temp_current_tree_to_create).getRepairedStability(from, WINDOW_SIZE);
		return null;
	}

	@Override
	public PS get_goal_state(int exe_actions) {
		
		int exe_act_T = find_reactive_structure_no_removing(exe_actions)[1];
		List<NodeState> aso_plan;
		
		if(exe_act_T!=-1){
			_self_repair_index_structure=0;
			aso_plan = self_structures.get(_self_repair_index_structure).get_associated_plan();
			return aso_plan.get(exe_act_T).getRPS();
		}
		
		return null;
	}

	
	
	
	
	/**
	 * MainTest
	 * TEST 
	 * @return
	 */
	@Override
	public void printResultsPartialStates(int Window, String path_dir) {
		
		_trans_seq_ps.clear();
		((SearchSpaceByState)_temp_current_tree_to_create).translate_plan_as_partial_states(Window);
		
		Files files = new Files(path_dir+this.planning_problem.getDomainName()+"/"+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach())+"-ps-"+this.planning_problem.getProblemName(), false);
		for(NodeState ps : ((SearchSpaceByState)_temp_current_tree_to_create).get_plan_as_partial_states()){
			files.write("partial state"+ps.getRPS().getState()+":\t"+ps.toPddl()+"\n\n");
			files.write("\taction: "+printOperator(ps.getOperator())+"\n\n");
		}
		files.close();
	}

	private String printOperator(ArrayList<DSPlanAction> operator) {
		
		String temp="";
		DSAction act=null;
		for(DSPlanAction op: operator){
			act = this.planning_problem.getActions().get(op.getIndexGroundingAction());
			temp = temp + "["+act.toString()+"]";
			temp = temp + "\n\t\tpre: "+act.getConditions();
			temp = temp + "\n\t\teff: "+act.getEffects()+"\n";
		}
		
		return temp;
	}
	
	public void simulateExecution() {
		if (_temp_current_tree_to_create != null){
			getPlanningProblem().simulateExecution(false);
			((SearchSpaceByState)_temp_current_tree_to_create).setWindowFrom((int)getPlanningProblem().getBestPlan().getCurrentTime());
			List<SharedPartialState> l_ps = ((SearchSpaceByState)_temp_current_tree_to_create).get_ps_to_publicize();
			if(l_ps.size() == 1){
				_temp_current_tree_to_create = null;
			}
		}
	}
	
}