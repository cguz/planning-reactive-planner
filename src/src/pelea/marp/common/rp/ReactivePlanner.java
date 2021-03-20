/** *  
 *  @name-file 		ReactivePlanner
 *  @project   		Multi-Agent Reactive Planner - 14/06/2013
 *  @author    		Cesar Augusto Guzman Alvarez   
 *  @equipo 		anubis
 *  @universidad 	Universidad Politecnica de Valencia
 *  @departamento 	DSIC
 */

package pelea.marp.common.rp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pelea.marp.common.rp.planning_task.pddl.Fluent;
import pelea.marp.common.rp.planning_task.pddl.PartialState;
import pelea.marp.common.rp.planning_task.plan.FixingPlan;
import pelea.marp.common.rp.reactive.enums.FILTER;
import pelea.marp.common.rp.reactive.enums.SEARCH_SPACE_FILTER;
import pelea.marp.common.rp.reactive.exceptions.BadProblemDefinitionException;
import pelea.marp.common.rp.reactive.exceptions.GeneralException;
import pelea.marp.common.rp.reactive.exceptions.NoPosiblePlanException;
import pelea.marp.common.rp.reactive.exceptions.NoRootException;
import pelea.marp.common.rp.reactive.exceptions.NoSearchSpaceException;
import pelea.marp.common.rp.reactive.exceptions.NoStatisticException;
import pelea.marp.common.rp.reactive.exceptions.NotExecutingPlanException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.common.rp.reactive.shared.pddl.SharedVariable;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTeamwork;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.helper.Statistics;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlan;
import pelea.planning_task.common.plan.DSPlanAction;

/**
 * Reactive Planner interface
 * @author Cesar Augusto Guzman Alvarez - 10/01/2010
 */
public interface ReactivePlanner {
	
	/**
	 * @return if the services are activated or not
	 */
	boolean services_activated();

    
	
	
	
	

	
	// single-repair : translating
	/**
	 * single-repair
	 * translating the plan as partial states from o to window
	 * @param window_size the plan window
	 * @return list of partial states
	 */
	HashMap<Integer, PS> translating(ArrayList<DSPlan> plan);

	
	// single-repair : estimating size
	/**
	 * single-repair
	 * 02/10/2013
	 * estimating the best size of structure's window and length 
	 * 
	 * @param time_limit	time limit to generate the structure
	 * @return 				either a window and a length of the structure with its estimated time
	 */
	EstimatedTime estimating_setting(double time_limit);

	
	// single-repair : calculating (extending) the search space
	/**
	 * single-repair
	 * 03/02/2013 14:43:00
	 * Generating a reactive structure from a root goal state
	 * 
	 * @param window_size 	window size to be used
	 * @param max_length 	max length of the structure
	 */
	void calculating(int Window, int level, boolean calculating_ps);
	void calculating(EstimatedTime best_setting);

    
	
	
	
	// single-repair : interrupt
	/**
	 * single-repair
	 * to verify is the first search tree is created
	 * @return
	 */
	public boolean first_search_tree();

    
	
	
		
	// single-repair : stop decision
	/**
	 * single-repair
	 * we get the last planning horizon
	 * @return time span
	 */
	double current_window();

	/**
	 * single-repair
	 * is the plan window complete
	 * @return true / false
	 */
	boolean is_plan_window_completed();

    
	
	
		
	// single-repair
	/**
	 * single-repair
	 * getting the variables involved in the last search tree
	 * @return set of index of the variables
	 */
	public List<Integer> get_var_in_last_search_tree();
	
	/**
	 * single-repair
	 * @return set of actions in the current plan window
	 */
	public List<Integer> get_actions_current_window();

    
	
	
	
	// single-repair method
	/**
	 * single-repair
	 * implements the single-repair process
	 * @param S variables in the search trees with its current values
	 * @param executed_actions number of executed actions
	 * @return an action plan
	 * @throws NoPosiblePlanException 
	 */
	String single_repairing(ArrayList<DSCondition> S, Integer executed_actions) throws NoPosiblePlanException;
	
	/**
	 * @return if the single repair mechanism is activated or not
	 */
	boolean single_repair_activated();

	/**
	 * single-repair 
	 * determine if we need to verify the conflicts during the self-repair process
	 * @return true/false
	 */
	boolean verify_conflicts_self_repair();
	
	/**
	 * @return the repaired plan to the root node
	 * @throws NoPosiblePlanException 
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	List<Integer> get_single_plan_to_root() throws NoPosiblePlanException, NoRootException, GeneralException;
	
	/**
	 * @return the statistics of generating the search space
	 * @throws NoStatisticException 
	 */
	Statistics get_single_statistic() throws NoStatisticException;

	/**
	 * @return the current plan as a sequence of shared partial states
	 */
	List<SharedPartialState> get_single_shared_plan();
	
	
	
	// multi-repairing method
	/**
	 * multi-repair
	 * partial states to publicizes
	 * @return set of partial states to publicizes
	 * @throws NoSearchSpaceException 
	 */
	List<SharedPartialState> get_ps_to_publicize(int executed_actions, SEARCH_SPACE_FILTER filter) throws NotExecutingPlanException, NoRootException;

	
	/**
	 * update the current_root_node and current_plan_window, remove the actions in the plan to execute and 
	 * current_plan_window and return the current_root_node 
	 * @param executed_actions
	 * @return the current_root_node
	 * @throws NoRootException
	 */
	SharedPartialState coord_get_current_root(int executed_actions) throws NoRootException;
	
	
	/**
	 * multi-repair
	 * Reactive multi-repairing process
	 * 20/07/2012
	 * @param solution, join state plan or emptyset
	 * @param partial_state, set of fluents to reach
	 * @param executed_actions,	set of executed actions
	 * @param S, variables in the search trees with its current values
	 * @param depth, maximum depth
	 * @return a new plan / not possible plan solution
	 * @throws NoPosiblePlanException 
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	public List<SharedPartialState> adapting_own_plan(FixingPlan solution, PartialState partial_state, int actions_executed, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException;

	/**
	 * @return if the multi-repair protocol is activated or not
	 */
	boolean multi_protocol_activated();
	
	/**
	 * multi-repair : merge-plan
	 * @return the merge plans
	 * @throws NoSearchSpaceException 
	 */
	String get_own_adapted_plan() throws NoSearchSpaceException;
	
	/**
	 * multi-repair : merge-plans
	 * determine if we need to verify the conflicts during the plan adaptation
	 * @return true/false
	 */
	boolean verify_conflicts_multi_repair();
	
	/**
	 * multi-repair : merge-plans
	 * remove the merge plan solution and put the multi-repair task to false
	 */
	void remove_own_adapted_plan();

	/**
	 * sort the received solutions
	 * @param fixed_plans set of received solutions 
	 * @return the fixed plans ordered
	 */
	List<FixingPlan> sort_solutions(List<FixingPlan> fixed_plans);
	
	/**
	 * multi-repair : merge-plan
	 * @return the statistics of merge plans
	 */
	Statistics get_own_adapted_plan_statistic();

	
	
	
	
	
	// multi-repair : adapting plan 
	/**
	 * multi-repair : adapting plan
	 * 20/07/2012
	 * @param plan, solution to consider when adapting plan
	 * @param target, set of fluents to reach of other agent
	 * @param root, set of own fluents to reach
	 * @param S variables in the search trees with its current values
	 * @param depth maximum depth
	 * @return a new plan / not possible plan solution
	 * @throws NoPosiblePlanException 
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	List<SharedPartialState> adapting_plan(FixingPlan plan, PartialState target, PartialState root, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException;

	/**
	 * multi-repair : adapted-plan
	 * @return the results of adapting plan
	 */
	Statistics get_adapting_plan_statistics();
	
	/**
	 * multi-repair : adapted-plan
	 * return the adapted plan with dummy actions inclusive
	 * @return new adapted plan
	 * @throws NoRootException 
	 * @throws NoSearchSpaceException 
	 * @throws GeneralException 
	 */
	String get_adapted_plan() throws NoRootException, NoSearchSpaceException, GeneralException;

	/**
	 * multi-repair : adapted-plan
	 * determine if we need to verify the conflicts during the plan adaptation
	 * @return true/false
	 */
	boolean verify_conflicts_adapted_plan();

	/**
	 * multi-repair : teamwork
	 * determine if we need to verify the conflicts during the teamwork process
	 * @return true/false
	 */
	boolean verify_conflicts_teamwork();
	

	
	
	


	// multi-repair : adapting plan relaxed
	/**
	 * multi-repair : adapting plan relaxed
	 * 20/07/2012
	 * @param plan, solution to consider when adapting plan
	 * @param G_t, set of fluents to reach of other agent
	 * @param G_r, set of own fluents to reach
	 * @param S variables in the search trees with its current values
	 * @param depth maximum depth
	 * @return a new plan / not possible plan solution
	 * @throws NoPosiblePlanException 
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	List<SharedPartialState> adapting_plan_relaxed(FixingPlan plan, PartialState G_t, PartialState G_r, ArrayList<DSCondition> S, int depth) throws NoPosiblePlanException, NoRootException, GeneralException;

	/**
	 * multi-repair : adapted-plan relaxed
	 * @return the results of adapting plan relaxed
	 */
	Statistics get_adapting_plan_relaxed_statistics();
	
	/**
	 * multi-repair : adapted-plan relaxed
	 * return the adapted plan relaxed with own actions
	 * @return new adapted plan relaxed
	 * @throws NoSearchSpaceException 
	 * @throws GeneralException 
	 * @throws NoRootException 
	 */
	String get_adapted_plan_relaxed() throws NoSearchSpaceException, NoRootException, GeneralException;

	/**
	 * multi-repair : adapted-plan relaxed
	 * information to reschedule the plan of other agents
	 * @return list of integer, 1 = the agent execute its action, -1 = the agent executes a dummy action
	 */
	List<Integer> get_rescheduling_info();
	
	
	
	
	

	public void coord_update_associated_plan_relaxed_adapted_plan(int executed_actions, int delay, ArrayList<DSPlan> plan);
	
	
	
	// multi:repair teamwork
	/**
	 * multi-repair : teamwork
	 * @return the current teamwork
	 */
	SharedTeamwork get_teamwork();
	
	/**
	 * multi-repair : teamwork
	 * create or update the teamwork
	 * @param transition to add in the teamwork
	 * @param time_created,	where the teamwork is created
	 * @return	the teamwork to share
	 */
	SharedTeamwork update_teamwork(SharedTransition transition,	double time_created);

	/**
	 * multi-repair : teamwork
	 * remove the transition in the teamwork until the given time
	 * @param time
	 * @return	true if the teamwork was removed
	 */
	boolean update_teamwork(double time);
	
	/**
	 * multi-repair : teamwork
	 * remove the teamwork
	 */
	void remove_teamwork();

	/**
	 * multi-repair : teamwork
	 * replace the current teamwork
	 * @param teamwork received from other agent
	 */
	void update_teamwork(SharedTeamwork teamwork);
	
	/**
	 * multi-repair : teamwork committed
	 * update the current teamwork
	 * @param current time
	 * @param info to reschedule
	 */
	void update_teamwork(int time, List<Integer> reschedul);
	
	/**
	 * multi-repair : teamwork committed
	 * verify if the partial state Gi_t is reached during the remaining plan execution
	 * @param Gi_t, the partial state that of the requester agent
	 * @param exe_act, total of executed actions
	 * @param S, current world state
	 * @return sequence of partial states that reach the partial state Gi_t
	 * @throws NoRootException 
	 * @throws NotExecutingPlanException 
	 * @throws GeneralException 
	 */
	List<SharedPartialState> check_synergy(ArrayList<PartialState> Gi_t, int exe_act, ArrayList<DSCondition> s) throws NotExecutingPlanException, NoRootException, GeneralException;
	

	boolean is_central_planner_enable();
	
	
	
	
	
	
	// general methods
	/**
	 * multi-repair 
	 * verifying conflicts between the received partial states and the current plan
	 * @param partial_states set of partial states (fluents)
	 * @param executed_actions
	 * @param simulate if we simulate the state or not
	 * @return boolean 
	 */
	boolean conflicts(ArrayList<PartialState> partial_states, int executed_actions, boolean simulate);

	/**
	 * multi-repair 
	 * verifying conflicts between the received partial states and the current plan
	 * - solo se llama cuando se ha adaptado el plan con own_adapted_plan
	 * @param partial_state set of partial states (fluents)
	 * @param simulate if we simulate the state or not
	 * @return
	 */
	boolean conflicts(ArrayList<PartialState> partial_state, boolean simulate);
	
	/**
	 * removing the unknown fluent
	 * @param partial state
	 * @param filter to specify if the filter is either by fluents or variables
	 * @param add_fluent 
	 * @return known partial state ( by_variables: set of fluents <v,d> in the effects , where d may be unknown , byfluents: set of fluents <v,d> in the effects , where d may be unknown )
	 */
	PartialState filter(SharedPartialState v, FILTER filter, boolean add_fluent);
	
	/**
	 * removing the unknown fluent
	 * @param partial state
	 * @param filter to specify if the filter is either by fluents or variables
	 * @return known shared partial state by fluents 
	 */
	SharedPartialState filter(SharedPartialState v, FILTER filter);

	/**
	 * @return if the saving services mode activated
	 */
	boolean is_saving_services_mode();
	
	/**
	 * @return the set of fluents that may achieve the agent
	 */
	ArrayList<SharedVariable> get_services();
	
	/**
	 * We remove the unknown fluents
	 * @param 	services information. We received it in the form <variable, D_v>, where D_v is the domain of value of the variable. 
	 * 			each value v in D_v means the value v that the agent that informs its service can reach for each variable.
	 * @return 	set of known fluents <v,d>
	 * @throws 	BadProblemDefinitionException 
	 */
	Fluent[] filter_services(ArrayList<SharedVariable> services) throws BadProblemDefinitionException;

	/**
	 * generate the plan as a sequence of regressed partial state
	 *   
	 * @param root partial state
	 * @param plan set of actions
	 * @return a set of partial states
	 */
	ArrayList<NodeState> regress_plan(NodeState root, ArrayList<DSPlanAction> plan);
	
	/**
	 * return the planning problem (domain and problem)
	 * @return PlanningProblem
	 */
	PlanningProblem getPlanningProblem();
	
	/**
	 * return the general settings of the reactive planner
	 * @return GeneralSettings
	 */
	GeneralSettings get_settings();
	
	
	
	
	
	// OTHERS
	/**
	 * 11/04/2014
	 * @return the total of actions of the repaired plan that are in the original plan
	 */
	Integer getRepairedStability();
	Integer getRepairedStability(int from, int Window);
	
	// MAIN
	/**
	 * 21/03/2014
	 * @return the information of the search tree
	 */
	
	PS get_goal_state(int index_destiny);
	
	void printResultsPartialStates(int Window, String path_dir);
	
	
	// GUI AND TEST - 11/04/2014
	/**
	 * To simulate a failure
	 * Filtering relevant variables
	 * 19/07/2013
	 * @param goal_state a set of variables
	 * @return	a list of filtered relevant variables
	 */
	ArrayList<DSCondition> filterRelevantVar(ArrayList<DSCondition> goal_state);
	ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state);	
	ArrayList<DSCondition> filterNextAction(ArrayList<DSCondition> goal_state, int timeStep);

	



















}