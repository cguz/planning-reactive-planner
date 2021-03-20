/** *  
 *  @name-file 		Main.java
 *  @project   		PELEA-BPGS - 04/06/2010
 *  @author    		Cesar Augusto Guzman Alvarez   
 *  @equipo 		zenshi
 *  @universidad 	Universidad Politecnica de Valencia
 *  @departamento 	DSIC
 */
package pelea.marp.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.agreement_technologies.ground.ParserPddl;
import org.apache.log4j.Logger;

import pddl4j.exp.action.NotFeasibleActionException;
import pddl4j.state.Atom;
import pddl4j.state.PDDLState;
import pelea.atpa.common.ds.DecisionSupport;
import pelea.atpa.common.enums.Decision;
import pelea.atpa.common.heuristic.config.CONFIG;
import pelea.atpa.service.planners.AnyPlanner;
import pelea.atpa.service.planners.PLANNER;
import pelea.atpa.service.search.RGS_GH;
import pelea.marp.common.rp.ReactivePlanner;
import pelea.marp.common.rp.reactive.exceptions.GeneralException;
import pelea.marp.common.rp.reactive.exceptions.NoPosiblePlanException;
import pelea.marp.common.rp.reactive.exceptions.NoStatisticException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.service.rp.MAReactivePlanner;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.reactive.settings.SingleSettingsImp;
import pelea.marp.test.conf.TYPE_TEST;
import pelea.marp.test.conf.TypeTests;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.enums.T_FAILURE;
import pelea.planning_task.common.enums.T_FILTER;
import pelea.planning_task.common.enums.T_MODULES;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlan;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.PlanningProblemImp;
import pelea.planning_task.service.plan.NPlan;
import pelea.planning_task.service.plan.NPlanAction;
import pelea.planning_task.tools.ExecuteScript_With_Thread;
import tools.files.Files;
import xml2java.X2J;
import xml2java.configuration.General;
import JPDDLSim.plan.PlanAction;
import JPDDLSim.plan.PlanXML;
import JPDDLSim.sim.Simulator;
import conf.xml2java.Conf;
import conf.xml2java.enume.From;

/**
 * @author Cesar Augusto Guzman Alvarez
 * 		   04/06/2010
 *
 */
public class MainHelperToGUI {
	static Logger logger = Logger.getLogger(MainHelperToGUI.class);

	private static String 	separator = General.file_separator;
	
	private String 			path_plans 				= "solutions";
	private String 			path_results;
	private static int 		planner_max_cpu_time 	= 1;
	
	
	/** BOTH CONFIGURATION */
	static ArrayList<DSPlan> 	input_plan; 
	static String[] 			domain 			= {"Rovers","DriverLog", "logistics","Woodworking", "openstacks"}; //,"Logistics","Satellite"}; 
	//static String[] 			domain 			= {"Woodworking", "openstacks"}; //,"Logistics","Satellite"}; 
	static String[] 			type_domain 	= {"Strips", "SimpleTime", "Time", "Numeric", "HardNumeric"}; 
	static int 					input_planner 	= CONFIG.PLANNER_LAMA;
	static int 					n_sol_ori_plan 	= 5;
	static int 					n_sol_lpg_adapt = 3;
	static int 					index_origen	= 0;
	static int 					index_destiny	= 0;
	static TypeTests 			op_test			= null;
	
	static String[] 			pddl31 			= new String[3]; // domain, and problem in PDDL
	static String[] 			pddl21 			= new String[3];

	String[] 					benchmark 		= new String[7];	

	Conf 						configuration; //  = new Conf("pruebas-xml/benchmarks/configuration.xml");
	SingleSettingsImp 			settings;
	static X2J 					configuration_ds; //   = new X2J("pruebas-xml/benchmarks/configuration.xml");
	
	static ArrayList<DSCondition> filtered_var = null;
	/** BOTH CONFIGURATION */
	
	
	
	/** REACTIVE PLANNER CONFIGURATION */
	static PS 					origin_pre			= null;
	static PS 					origin 				= null;
	static NodeState 			targetNode;
	static ReactivePlanner 		rp 					= null;
	
	static ArrayList<DSCondition> goal_state 		= null;
	static ArrayList<DSCondition> current_state 	= null;

	static double 				initialTime			= index_origen;
	static double 				timeFindPlan;
	static int 					not_found_destiny 	= 0;
	static int 					not_found_origin 	= 0;
	static int 					not_found_plan 		= 0;
	static int 					num_adit_action		= 0;
	static int 					stability 			= 0;
	static String 				path_eval_rp		= null;
	static String 				path_analysis_rp	= null;
	static String 				path_failures_rp 	= null;
	/** REACTIVE PLANNER CONFIGURATION */
	
	
	
	/** DECISION SUPPORT CONFIGURATION */	
	static int 					output_planner 		= CONFIG.PLANNER_LAMA;
	static int 					currentInstantTime 	= 0;
	static DecisionSupport 		anytime 			= null;
	
	static String 				path_f_resul 		= null;
	static String 				path_eval_ds		= null;
	static String 				path_failures_ds	= null;	
	/** DECISION SUPPORT CONFIGURATION */
	
	
	
	/** To save the information for the statistics */
	static ArrayList<DSCondition> init_cur_state	= null;
	public static boolean 	debug_failures	= true;
	static Files evalRP			= null;
	static Files evalRP2 		= null;
	static Files evalDS			= null;
	static Files evalDS2		= null;
	static double timeT_total 	= 0;
	static double timeT			= 0;
	
	static EstimatedTime 		bestValue;

	int 	seed 				= 123456789;
	

	/**
	 * @param paths
		[0] = HelperFunctions.sep; // separation - sep
		[1] = System.getProperty("user.dir"); // path to the app root
		[2] = HelperFunctions.default_path; // folder for the reactive execution model - d_path
		[3] = "planning-tasks"; // folder for planning task
		[4] = "single-agent"; // folder for he single agent - path_agent
		[5] = "solutions"; // folder for solutions - path_planes = path_agent+sep+"solutions";
		// path_domains = paths[3]+paths[0]
		// path = paths[1]+paths[0]+paths[2]
	  @param benchmark
		[0] = // folder pddl3.1
		[1] = // folder Pfile pddl3.1
		[2] = // name domain pddl3.1
		[3] = // name problem pddl3.1
		[4] = // folder pddl2.1
		[5] = // name domain pddl2.1
		[6] = // name problem pddl2.1
	 * @param configuration
	 * @param seed
	 */
	public void execute(String[] paths, String benchmark[], Conf configuration, long seed) {
		
		settings = new SingleSettingsImp(configuration);

		this.path_plans = paths[2]+paths[3]+paths[0]+paths[4]+paths[0]+paths[5];
		this.configuration = configuration;
		this.benchmark = benchmark;
		this.seed = (int) seed;
		this.path_results = paths[1]+paths[0]+paths[2]+paths[6]+paths[0]+paths[7];
		
		/** GENERAL CONFIGURATION */
		pddl31[0] = ""; // domain
		pddl31[1] = ""; // problem
		/** GENERAL CONFIGURATION */
		
		/** DECISION SUPPORT CONFIGURATION */
		CONFIG.HEURISTIC_ALFA	= 0.15;	
		/** DECISION SUPPORT CONFIGURATION */
		
		switch(settings.getTypeExecution()){
			case RP_TE_CREATING_DATASET_BENCHMARK_MODE: 		creatingDatasetBenchmarkMode();	break;

			case RP_TE_VERIFYING_PLAN_SOLUTIONS_MODE: 			verifyingPlanSolutions();		break;
			case RP_TE_VERIFYING_PLANS_SEARCH_TREES_MODE: 		verifyingPlanSearchTrees();		break;
			case RP_TE_GENERATING_TREE_GRAPH1_MODE: 			testingGeneratingTreeGraph1();	break;
			case RP_TE_GENERATING_TREE_GRAPH2_MODE: 			testingGeneratingTreeGraph2();	break;
			case RP_TE_SIMULATING_FAILURE_RP: 					testingSimulatingFailureRP();	break;
			case RP_TE_APPLICATION_EXAMPLE_MODE: 				applicationExampleTreeGeneration();break;
			case RP_APPLICATION_EXAMPLE_MA: 
				benchmark[0] = paths[1]+paths[0]+paths[2]+paths[3]+paths[0]+"pruebas-xml/benchmarks/MultiAgent/pddl3.1"; // folder pddl3.1
				benchmark[4] = paths[1]+paths[0]+paths[2]+paths[3]+paths[0]+"pruebas-xml/benchmarks/MultiAgent/pddl2.1"; // folder pddl2.1
				marpTreeGeneration();			break;
			case RP_TE_VERIFYING_RESULTS_PARTIAL_STATES_MODE: 	verifyingResultsPartialStates();break;
			case RP_TE_REPLANNING_VS_REPAIR: 					replanningVsRepairing();		break;
			default:  											testingSimulatingFailureRP();
		}
	}
	
	/**
	 * 01/08/2013
	 * Test for the Journal : Reactive Planner
	 */
	private void applicationExampleTreeGeneration() {
		
		File 	file;
		long timeGenerateT_i;
		int time = 1000;
		int pfile = Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM"));
		
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 

		op_test = new TypeTests(TYPE_TEST.BYVALUE_NOISY_ACTIONS_ONE);
		
		
		op_test.setInitialTimeLimit(time);
		file = new File(op_test.path_eval_benchmarks);
		if(!file.exists()) file.mkdirs();

		op_test.domain = domain.length-1;
		op_test.problem = pfile;
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test.domain,op_test.problem);
		

		input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			
			// planning problem of pddl3.1
			pddl31Path(op_test.domain,op_test.problem);
			
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			rp.getPlanningProblem().printPlan();
			
			
			initialTime = System.currentTimeMillis();
			((MAReactivePlanner)rp).translating();
			bestValue = rp.estimating_setting(op_test.init_limit_time);
			
			bestValue.setPlanningWindow(3); 
			bestValue.setDepth(6);
			

			/** calculating the precompiled structure */
			if(bestValue!=null){
				timeGenerateT_i = System.currentTimeMillis();
				// EJECUCION COMPLETA
				rp.calculating(bestValue);
				op_test.res_time_T = System.currentTimeMillis()-timeGenerateT_i;	
			}else
				System.out.println("It is not possible to generate the structure");
			
			op_test.res_total_time_T = System.currentTimeMillis()-initialTime;
	        
	        path_eval_rp = op_test.path_eval_benchmarks+"/evalAppExample";
			path_analysis_rp = op_test.path_eval_benchmarks+"/analysisAppExample";										
			path_failures_rp = op_test.path_eval_benchmarks+"/failuresAppExample";
	        op_test.res_dir_evaluations	 = op_test.path_eval_benchmarks+"/evalAppExample";
			
			
			/** simulating a failure */
			simulatingFailure(op_test.technique, 0);
			++seed;
			
			
			/** printing the failure in a file */
			if(debug_failures) printingFailureRP(op_test.domain, op_test.problem);

	
			
			/** calculating and saving the repaired plan from the reactive planner ***/
			executingReactivePlanner(domain[op_test.domain], op_test.problem, bestValue.getPlanningHorizon(), bestValue.getDepth());
			
			
			
			op_test.res_planning_horizon = bestValue.getPlanningHorizon();
			op_test.res_depth = bestValue.getDepth();
			op_test.res_estimated_time = bestValue.getEstimatedTime();
			op_test.res_est_bran_factor = bestValue.getEst_Branching_factor();
			op_test.res_est_eval_nodes = bestValue.getEst_evaluated_nodes();
			op_test.res_est_total_nodes = bestValue.getEst_Total_nodes();
	        op_test.res_parameters = bestValue.parameters;
	        try {
				op_test.res_info_search_tree = rp.get_single_statistic();
			} catch (NoStatisticException e) {
				op_test.res_info_search_tree = null;
				e.printStackTrace();
			}
			op_test.res_t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();	
			
			print(domain[0], op_test);
			

			
			/*
			// EJECUCION COMPLETA
			// simulo la ejecución de las acciones hasta que queden dos acciones
			while(rp.getPlanningProblem().getBestPlan().getActionPlan().size()>2){
				rp.getPlanningProblem().simulateExecution();
			}
			trgs = new TRGSImp(rp.getPlanningProblem(), false);
			trgs.detecting(); 
			rp.initializingStructure(trgs.getGoal_state());
			bestWindow = rp.findingBestWindow(op_test.t_lim);
			if(bestWindow!=null){
				timeGenerateT_i = System.currentTimeMillis();
				rp.generatingReactiveStructure(bestWindow.getWindow(), bestWindow.getDepth(), op_test.time);
				timeT = System.currentTimeMillis()-timeGenerateT_i;	
			}else
				System.out.println("It is not possible to generate the structure");
			*/
		}
	}

	/**
	 * 01/08/2013
	 * Test for the Journal : Reactive Planner Multi-Agent
	 */
	private void marpTreeGeneration() {
		
		File 	file;
		long timeGenerateT_i;
		int time = 1000;
		int pfile = Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM"));
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 

		op_test = new TypeTests(TYPE_TEST.BYVALUE_NOISY_ACTIONS_ONE);
		op_test.setInitialTimeLimit(time);
		file = new File(op_test.path_eval_benchmarks);
		if(!file.exists()) file.mkdirs();

		op_test.domain = domain.length-1;
		op_test.problem = pfile;
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test.domain,op_test.problem);
		

		input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			
			// planning problem of pddl3.1
			pddl31Path(op_test.domain,op_test.problem);
			
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			rp.getPlanningProblem().printPlan();
			
			
			initialTime = System.currentTimeMillis();
			((MAReactivePlanner)rp).translating();
			bestValue = rp.estimating_setting(op_test.init_limit_time);
			
			
			/** calculating the pre-compiled structure */
			if(bestValue!=null){									
				timeGenerateT_i = System.currentTimeMillis();
				rp.calculating(bestValue);
				op_test.res_time_T = System.currentTimeMillis()-timeGenerateT_i;	
				
				
				// rp.extendsSearchTree(bestValue.getPlanningHorizon(), op_test.dir_time);
				
			}else
				System.out.println("It is not possible to generate the structure");
			
			op_test.res_total_time_T = System.currentTimeMillis()-initialTime;
	        
	        path_eval_rp = op_test.path_eval_benchmarks+"/evalAppExample";
			path_analysis_rp = op_test.path_eval_benchmarks+"/analysisAppExample";										
			path_failures_rp = op_test.path_eval_benchmarks+"/failuresAppExample";
	        
	        op_test.res_dir_evaluations = op_test.path_eval_benchmarks+"/evalAppExample";
			
			/** simulating a failure */
			simulatingFailure(op_test.technique,0);
			++seed;
			
			/** printing the failure in a file */
			if(debug_failures) printingFailureRP(op_test.domain, op_test.problem);

	
			/** calculating and saving the repaired plan from the reactive planner ***/
			executingReactivePlanner(domain[op_test.domain], op_test.problem, bestValue.getPlanningHorizon(), bestValue.getDepth());
			
			
			op_test.res_planning_horizon = bestValue.getPlanningHorizon();
			op_test.res_depth = bestValue.getDepth();
			op_test.res_estimated_time = bestValue.getEstimatedTime();

			op_test.res_est_bran_factor = bestValue.getEst_Branching_factor();
			op_test.res_est_eval_nodes = bestValue.getEst_evaluated_nodes();
			op_test.res_est_total_nodes = bestValue.getEst_Total_nodes();
	        op_test.res_parameters = bestValue.parameters;
			op_test.res_t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();	

	        try {
				op_test.res_info_search_tree = rp.get_single_statistic();
			} catch (NoStatisticException e) {
				op_test.res_info_search_tree = null;
				e.printStackTrace();
			}
	        
			print(domain[0], op_test);
			

			
			
			// EJECUCION COMPLETA
			// simulo la ejecución de las acciones hasta que queden dos acciones
			while(rp.getPlanningProblem().getBestPlan().getActionPlan().size()>2){
				rp.getPlanningProblem().simulateExecution(false);
			}
			
			((MAReactivePlanner)rp).translating();
			bestValue = rp.estimating_setting(op_test.init_limit_time);
			if(bestValue!=null){
				timeGenerateT_i = System.currentTimeMillis();
				rp.calculating(bestValue);
				timeT = System.currentTimeMillis()-timeGenerateT_i;	
			}else
				System.out.println("It is not possible to generate the structure");
			
		}
	}

	/**
	 * 01/08/2013
	 * Test for the Journal : Reactive Planner
	 */
	private void testingGeneratingTreeGraph1() {

		File 	file;
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 
		
		/** BOTH CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		int 	total_act_to_sim= 0;
		int 	total_same_exe 	= 10;
		int 	max_time		= 2000;
		/** BOTH CONFIGURATION */

		op_test = new TypeTests(TYPE_TEST.BYVALUE_NOISY_ACTIONS_ONE);
		op_test.total_act_to_sim 	= total_act_to_sim;
		op_test.total_number_executions 		= total_same_exe;
		
		for(int k=1;k<=op_test.total_number_executions;++k){
			
			// for each time
			for(int time=1000;time<max_time;time=time+1000){
				
				// for each domain 
				for (int j = 0; j < domain.length; ++j){
					
					// for each problem
					for (int i = init_problem; i < total_problem; ++i){
						
						op_test.setInitialTimeLimit(time);
						op_test.setTimeLimitTree(time);
						file = new File(op_test.path_eval_benchmarks);
						if(!file.exists()) file.mkdirs();
						
						op_test.domain 	= j;
						op_test.problem = i;
						
						testingGeneratingTreeGraph1(op_test);
					}

					evalRP = new Files(op_test.res_dir_evaluations, true);
					evalRP.write("\n");
					evalRP.close();
				}
			}
		}
	}

	private void testingGeneratingTreeGraph1(TypeTests op_test2) {
		
        boolean debugPrintTable = true;
        
		
		double 	timeGenerateT_i; 
        int 	no_search_tree=1;
        int 	t_action = 0;
        
		// System.out.println("\nPfile: "+i);
		if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = op_test2.problem;
		System.out.println("Pfile: "+op_test2.problem);
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test2.domain,op_test2.problem);
		
		input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			// planning problem of pddl3.1
			pddl31Path(op_test2.domain,op_test2.problem);			
						
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			
			
			t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();
			
			
			System.out.println("[PARTIAL STATES]");
			((MAReactivePlanner)rp).translating();
			
			do{	
				initialTime = System.currentTimeMillis();
				
				// rp.setWindowFrom((int)rp.getPlanningProblem().getBestPlan().getCurrentExecutionTime());
				
				bestValue = rp.estimating_setting(op_test.res_limit_time_tree);
				
				if(no_search_tree==1 && op_test2.problem==6)
					bestValue.setDepth(5);
	
				/** calculating the pre-compiled structure */
				if(bestValue!=null){
					timeGenerateT_i = System.currentTimeMillis();
					rp.calculating(bestValue);
					op_test2.res_time_T = System.currentTimeMillis()-timeGenerateT_i;	
				}else
					System.out.println("It is not possible to generate the structure");

		        op_test2.res_total_time_T = System.currentTimeMillis()-initialTime;
		        op_test2.res_time_finding_best_size = bestValue.getTimeFindingBestSize();
		        
		        op_test2.res_dir_evaluations = op_test.path_eval_benchmarks+"/evaluationsRP-tf"+op_test.type_failure+"-f"+op_test.noisy_actions+((MAReactivePlanner)rp).getSettingsR().model_name;
		        op_test2.res_planning_horizon = bestValue.getPlanningHorizon();
		        op_test2.res_depth = bestValue.getDepth();
		        op_test2.res_estimated_time = bestValue.getEstimatedTime();
		        op_test2.res_est_bran_factor = bestValue.getEst_Branching_factor();
		        op_test2.res_est_eval_nodes = bestValue.getEst_evaluated_nodes();
		        op_test2.res_est_total_nodes = bestValue.getEst_Total_nodes();
		        op_test2.res_parameters = bestValue.parameters;
				op_test2.res_no_search_tree = no_search_tree;	
				op_test2.res_t_action = t_action;	
				
		        try {
					op_test2.res_info_search_tree = rp.get_single_statistic();
				} catch (NoStatisticException e) {
					op_test.res_info_search_tree = null;
					e.printStackTrace();
				}
				
				if(debugPrintTable)
		        	printTable(domain[op_test2.domain], op_test2);
		        else
		        	print(domain[op_test2.domain], op_test2);
		        	
				// simulating the plan execution of total_sim_exec actions
				//rp.getPlanningProblem().simulateExecution();
				//++act_to_exec;
		        
				no_search_tree++;
				op_test2.setTimeLimitTree(op_test2.res_planning_horizon*1000);
				
				// simulating the plan execution
				/*do{
					((MAReactivePlanner)rp).simulateExecution();
					temp_exe++;
				}while(temp_exe <  op_test2.res_planning_horizon && t_action >= op_test2.res_planning_horizon);*/
				
				// saving the total of actions
				t_action = (int) rp.current_window();
				
			}while(t_action>0 && no_search_tree<=3);
		}
	}
	
	/**
	 * 16/04/2014
	 * Verifying the action plan in the search trees
	 */
	private void verifyingPlanSearchTrees() {

		File 	file;
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 

		
		/** BOTH CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		int 	total_act_to_sim= 0;
		int 	total_same_exe 	= 1;
		int 	max_time		= 2000;
		/** BOTH CONFIGURATION */

		op_test = new TypeTests(TYPE_TEST.BYVALUE_NOISY_ACTIONS_ONE);
		op_test.total_act_to_sim 	= total_act_to_sim;
		op_test.total_number_executions 		= total_same_exe;
		
		for(int k=1;k<=op_test.total_number_executions;++k){
			
			// for each time
			for(int time=1000;time<max_time;time=time+1000){
				
				// for each domain 
				for (int j = 0; j < domain.length; ++j){
					
					// for each problem
					for (int i = init_problem; i < total_problem; ++i){
						
						op_test.setInitialTimeLimit(time);
						op_test.setTimeLimitTree(time);
						file = new File(op_test.path_eval_benchmarks);
						if(!file.exists()) file.mkdirs();
						
						op_test.domain 	= j;
						op_test.problem = i;
						
						verifyingPlanSearchTrees(op_test);
					}

					evalRP = new Files(op_test.res_dir_evaluations, true);
					evalRP.write("\n");
					evalRP.close();
				}
			}
		}
	}

	private void verifyingPlanSearchTrees(TypeTests op_test2) {
		double 	timeGenerateT_i; 
        int 	no_search_tree=1;
        int 	temp_exe = 0;
        int 	t_action = 0;
        
		// System.out.println("\nPfile: "+i);
		if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = op_test2.problem;
		System.out.println("Pfile: "+op_test2.problem);
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test2.domain,op_test2.problem);
		
		input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			// planning problem of pddl3.1
			pddl31Path(op_test2.domain,op_test2.problem);					
						
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			
			
			t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();
			do{
				
				initialTime = System.currentTimeMillis();
				((MAReactivePlanner)rp).translating();
				bestValue = rp.estimating_setting(op_test.res_limit_time_tree);
	
				/** calculating the pre-compiled structure */
				if(bestValue!=null){
					timeGenerateT_i = System.currentTimeMillis();
					rp.calculating(bestValue);
					op_test2.res_time_T = System.currentTimeMillis()-timeGenerateT_i;	
				}else
					System.out.println("It is not possible to generate the structure");
				
		        op_test2.res_total_time_T = System.currentTimeMillis()-initialTime;
		        

		   		DSPlan plan_ori = rp.getPlanningProblem().getBestPlan();
				
				op_test2.res_orig_plan = plan_ori.toLPG_TD(bestValue.getPlanningHorizon());		        
		        op_test2.res_dir_evaluations = op_test.path_eval_benchmarks+"/verifyingPlanSearchTrees";
		        op_test2.res_planning_horizon = bestValue.getPlanningHorizon();
		        op_test2.res_depth = bestValue.getDepth();
				op_test2.res_no_search_tree = no_search_tree;	
				
		        printingPlanSearchTree(domain[op_test2.domain], op_test2);
				
				// simulating the plan execution of total_sim_exec actions
				//rp.getPlanningProblem().simulateExecution();
				//++act_to_exec;
		        
				no_search_tree++;
				op_test2.setTimeLimitTree(op_test2.res_planning_horizon*1000);
				temp_exe = 0;
				
				do{
					rp.getPlanningProblem().simulateExecution(false);
					temp_exe++;
				}while(temp_exe <  op_test2.res_planning_horizon && t_action >= op_test2.res_planning_horizon);
				
				t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();
				
			}while(t_action>0 && no_search_tree<=3);
		}
	}
	
	private void printingPlanSearchTree(String domain, TypeTests op_test2) {
		
		evalRP2 = new Files(op_test2.res_dir_evaluations, true);
		evalRP2.write("\n\n\n\n**********************");
		evalRP2.write("\n"+domain + " - pfile"+ op_test2.problem);
		evalRP2.write("\nSEARCH TREE="+op_test2.res_no_search_tree);
		evalRP2.write("\nWINDOW="+op_test2.res_planning_horizon);
		evalRP2.write("\nDEPTH="+op_test2.res_depth);
		evalRP2.write("\n**********************");

		evalRP2.write("\n\nOriginal plan:\n"+op_test2.res_orig_plan);
		// evalRP2.write("\n\nOriginal plan:\n"+plan_ori.toLPG_TD(WINDOW_SIZE));
		
		evalRP2.close();
		
	}
	
	/**
	 * 01/08/2013
	 * Test for the Journal : Reactive Planner Vs Decision Support
	 */
	private void testingGeneratingTreeGraph2() {

		File 	file;
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 

		
		
		/** BOTH CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		int 	total_act_to_sim= 0;
		int 	total_same_exe 	= 10;
		int 	max_time		= 2000;
		/** BOTH CONFIGURATION */
		
		for(int k=1;k<=total_same_exe;++k){

			op_test = new TypeTests(TYPE_TEST.BYVALUE_NOISY_ACTIONS_ONE);
			op_test.total_act_to_sim = total_act_to_sim;
			op_test.total_number_executions = total_same_exe;
			
			// for each time
			for(int time=1000;time<max_time;time=time+1000){
				op_test.setInitialTimeLimit(time);
				file = new File(op_test.path_eval_benchmarks);
				if(!file.exists()) file.mkdirs();
				
				// for each domain 
				for (int j = 0; j < domain.length; ++j){
					
					// for each problem
					for (int i = init_problem; i < total_problem; ++i){
						
						op_test.domain = j;
						op_test.problem = i;
						
						testingTreeGenerationRPGraph2(op_test);
					}

					evalRP 	= new Files(path_eval_rp, true);
					evalRP.write("\n");
					evalRP.close();
				}
			}
		}
	}

	private void testingTreeGenerationRPGraph2(TypeTests op_test2) {
		double 	timeGenerateT_i; 
		double 	stop;
		
		// System.out.println("\nPfile: "+i);
		if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = op_test2.problem;
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test2.domain,op_test2.problem);
		

		input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			
			// planning problem of pddl3.1
			pddl31Path(op_test2.domain,op_test2.problem);					
			
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			
			
			do{
				initialTime = System.currentTimeMillis();
				((MAReactivePlanner)rp).translating();
				bestValue = rp.estimating_setting(op_test.init_limit_time);
	
				/** calculating the precompiled structure */
				if(bestValue!=null){									
					timeGenerateT_i = System.currentTimeMillis();
					rp.calculating(bestValue);
					op_test2.res_time_T = System.currentTimeMillis()-timeGenerateT_i;	
				}else
					System.out.println("It is not possible to generate the structure");
				
				op_test2.res_total_time_T = System.currentTimeMillis()-initialTime;
		        
				op_test2.res_dir_evaluations = op_test.path_eval_benchmarks+"/evaluationsRP-tf"+op_test.type_failure+"-f"+op_test.noisy_actions;

		        op_test2.res_planning_horizon = bestValue.getPlanningHorizon();
		        op_test2.res_depth = bestValue.getDepth();
		        op_test2.res_estimated_time = bestValue.getEstimatedTime();
		        op_test2.res_est_bran_factor = bestValue.getEst_Branching_factor();
		        op_test2.res_est_eval_nodes = bestValue.getEst_evaluated_nodes();
		        op_test2.res_est_total_nodes = bestValue.getEst_Total_nodes();
		        op_test2.res_parameters = bestValue.parameters;
		        try {
					op_test2.res_info_search_tree = rp.get_single_statistic();
				} catch (NoStatisticException e) {
					op_test2.res_info_search_tree = null;
					e.printStackTrace();
				}
				op_test2.res_t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();	
				
				print(domain[op_test2.domain], op_test2);
				
				// simulating the plan execution of total_sim_exec actions
				rp.getPlanningProblem().simulateExecution(false);
				
				stop = rp.getPlanningProblem().getBestPlan().getActionPlan().size();//-rp.getPlanningProblem().getBestPlan().getCurrentExecutionTime();
			}while(stop > 6); // graph 2
		}
	}
	
	private void testingSimulatingFailureRP() {

		File 	file;
		T_MODULES 	technique = T_MODULES.MODULE_REACTIVEPLANNER;

		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 

		
		/** BOTH CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		int 	max_text_conf	= 3;
		int 	totalSimExec	= 0;
		int 	max_time		= 2000;
		/** BOTH CONFIGURATION */
		
		// for each test configuration
		for(int t=1;t<=max_text_conf;t=t+3){
			op_test = new TypeTests(TYPE_TEST.BYACT_TRGS_NOISY_ACTIONS_ONE.getType(t));/** Configuration of the test */
			op_test.technique=technique;
			op_test.total_number_executions=totalSimExec;
			
			// for each time
			for(int time=1000;time<max_time;time=time+1000){
				op_test.setInitialTimeLimit(time);
				file = new File(op_test.path_eval_benchmarks);
				if(!file.exists()) file.mkdirs();
				
				// for each domain 
				for (int j = 0; j < domain.length; ++j){
					
					// for each problem
					for (int i = init_problem; i < total_problem; ++i){
						
						op_test.domain = j;
						op_test.problem = i;
						
						testingSimulatingFailureRP(op_test);
					
					}
				}
			}
		}
	}

	private void testingSimulatingFailureRP(TypeTests op_test2) {
		double 	timeGenerateT_i; 
		
		// System.out.println("\nPfile: "+i);
		if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = op_test2.problem;
		
		// generating a plan with pddl2.1 and lpg
		pddl21Path(op_test2.domain,op_test2.problem);
		
		input_plan = searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
		if(input_plan.size()>=0){
			
			// planning problem of pddl3.1
			pddl31Path(op_test2.domain,op_test2.problem);
			
			/** initializing the Reactive Planner **/
			rp = new MAReactivePlanner(pddl31, input_plan, configuration, op_test.path_eval_benchmarks);
			//rp.getPlanningProblem().printPlan();
			
			initialTime = System.currentTimeMillis();
			((MAReactivePlanner)rp).translating();
			bestValue = rp.estimating_setting(op_test.init_limit_time);
			
			/*if(op_test2.problem==6){
				bestValue = new EstimatedTime(op_test.init_limit_time, 3, 5,-1,-1,-1,null);
			}*/
			
			if(bestValue!=null){
				timeGenerateT_i = System.currentTimeMillis();
				rp.calculating(bestValue);
				timeT =  System.currentTimeMillis()-timeGenerateT_i;			
			}else
				System.out.println("It is not possible to generate the structure");
			
	        timeT_total 	= System.currentTimeMillis()-initialTime;
			
	        ArrayList<DSCondition> previous;
	        
	        int timeStep = 0;
	        index_destiny=timeStep;
			do{
			
				if(timeStep!=0) 	rp.getPlanningProblem().simulateExecution(false);
				
				previous = new ArrayList<DSCondition>(rp.getPlanningProblem().getInitialState().size());
				previous.addAll(rp.getPlanningProblem().getInitialState());
				
		        // statictis
				// CONFIG.DEBUG_MET_STA 	= false;
				path_eval_rp	 = op_test2.path_eval_benchmarks+"/evaluationsRP-tf"+op_test2.type_failure+"-f"+op_test2.noisy_actions;
				path_analysis_rp = op_test2.path_eval_benchmarks+"/analysisRP-tf"+op_test2.type_failure+"-f"+op_test2.noisy_actions;										
				path_failures_rp = op_test2.path_eval_benchmarks+"/failuresRP-tf"+op_test2.type_failure+"-f"+op_test2.noisy_actions;
				
				
				/** simulating a failure */
				simulatingFailure(op_test2.technique,timeStep);
				++seed;
				
				
				/** printing the failure in a file */
				if(debug_failures) printingFailureRP(op_test.domain, op_test2.problem);

		
				/** calculating and saving the repaired plan from the reactive planner ***/
				executingReactivePlanner(domain[op_test2.domain], op_test2.problem, bestValue.getPlanningHorizon(), bestValue.getDepth());
				// print(op_test2.technique, domain[op_test2.domain], op_test2.problem, bestWindow.getWindow(), bestWindow.getDepth());
				
				rp.getPlanningProblem().getInitialState().clear();
				rp.getPlanningProblem().getInitialState().addAll(previous);
				
				++timeStep;
				index_destiny=timeStep;
			}while(timeStep<bestValue.getPlanningHorizon());	
		}
	}



	/**
	 * This function generates a set data real samples with different configurations. 
	 */
	private void creatingDatasetBenchmarkMode() {

		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 
		
		/** GENERAL CONFIGURATION */
		String[] temp = configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-");
		int 	init_problem	= Integer.parseInt(temp[0]);
		int 	total_problem	= (temp.length>1)?Integer.parseInt(temp[1]):init_problem+1;
		// int 	totalSimExec	= 8;
		/** GENERAL CONFIGURATION */
		
		/** REACTIVE PLANNER CONFIGURATION */
		int 	planning_horizon_from 		= 1;
		int 	planning_horizon_to			= 7;
		int 	max_distance_to				= 7;
		/** REACTIVE PLANNER CONFIGURATION */
		
		
		// for each domain 
		// for (int j = 0; j < 1; ++j){ 
		for (int j = 0; j < domain.length; ++j){
			
			for (int k = init_problem; k < total_problem; ++k){
				
				int window_to = planning_horizon_to;
				
				if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = k;
				
				// generating a plan with pddl2.1 and lpg
				pddl21Path(j,k);
				
				input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
				if(input_plan.size()>=0){
					
					// planning problem of pddl3.1
					pddl31Path(j,k);
					System.out.println("\ndomain: "+domain[j] +" - problem: "+k);
					
					/** initializing the Reactive Planner **/
					rp 	 = new MAReactivePlanner(pddl31, input_plan, configuration, path_results);
					//rp.getPlanningProblem().printPlan(path_results);
					
					
					/* 
					// PRUEBAS CON CUATRO SIMULACIONES. 
					
					int i = 0;
					do{
						trgs = new TRGSImp(rp.getPlanningProblem(), false);// generating all TRGS by regression
						trgs.detecting();
						
						// we perform different windows and levels
						for(int PLANNINGHORIZON_SIZE=planning_horizon_from;PLANNINGHORIZON_SIZE<planning_horizon_to;++PLANNINGHORIZON_SIZE){
							for(int DEPTH_MAX=PLANNINGHORIZON_SIZE;DEPTH_MAX<max_distance_to;++DEPTH_MAX){
								if(DEPTH_MAX==PLANNINGHORIZON_SIZE) continue;
								
								/** calculating the precompiled structure from the goals **
								rp.initializingStructure(trgs.getGoal_state());
								rp.generatingReactiveStructure(PLANNINGHORIZON_SIZE, DEPTH_MAX, "./results/");
							}
						}
						++i;
						rp.getPlanningProblem().simulateExecution();
					}while(i<totalSimExec);
					*/
					
					/**/
					
					// int no_search_tree = 1;
					
					int t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();
					if(t_action < window_to)
						window_to = t_action;

					int temp_exe = 0;
				    
					do{
						
						System.out.println("\nTotal actions: "+t_action);
						
						// we perform different windows and depth
						for(int PLANNING_HORIZON_SIZE=planning_horizon_from;PLANNING_HORIZON_SIZE<window_to;++PLANNING_HORIZON_SIZE){
							for(int DEPTH_MAX=PLANNING_HORIZON_SIZE;DEPTH_MAX<max_distance_to;++DEPTH_MAX){
								if(DEPTH_MAX==PLANNING_HORIZON_SIZE) continue;
								
								/** calculating the precompiled structure from the goals */
								((MAReactivePlanner)rp).translating();
								rp.calculating(PLANNING_HORIZON_SIZE, DEPTH_MAX, true);
							}
						}
						
						temp_exe = 1;
						do{
							((MAReactivePlanner)rp).simulateExecution();
							++temp_exe;
						}while(temp_exe < window_to && t_action >= window_to);	
						
						t_action = rp.getPlanningProblem().getBestPlan().getActionPlan().size();
					    
						if(t_action < window_to)
							window_to = t_action+1;

						Runtime garbage = Runtime.getRuntime();
					    garbage.gc();
					    
					}while(t_action>0);
					
				}
			}
		}
	}
	
	/**
	 * Verify if all the actions of plan solutions are instantiated
	 */
	private void verifyingPlanSolutions() {
		
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 
		
		/** GENERAL CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		/** GENERAL CONFIGURATION */
		
		
		// for each domain 
		// for (int j = 0; j < 1; ++j){ 
		for (int j = 0; j < domain.length; ++j){
			
			for (int k = init_problem; k < total_problem; ++k){
				
				if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = k;
				
				// generating a plan with pddl2.1 and lpg
				pddl21Path(j,k);
				
				input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
				if(input_plan.size()>=0){
					
					// planning problem of pddl3.1
					pddl31Path(j,k);
					
					
					/** initializing the Reactive Planner **/
					rp 	 = new MAReactivePlanner(pddl31, input_plan, configuration, ".");
					//rp.getPlanningProblem().printPlan();
					
					
					Integer index;
					for(DSPlan p: input_plan){
						for(DSPlanAction act:p.getActionPlan()){
							index = rp.getPlanningProblem().getActionsIndex().get(act.toString());
							if(index==null)
								System.out.println(act.toString()+" - null");
							else
								System.out.println(act.toString()+" - Ok");							
						}
					}
					
				}
			}
		}
	}
	

	private void replanningVsRepairing() {
		Simulator sim;
		String action;
		String name_pfile;
		String name_domain;
		String name_plan;
		String results = "";
		PDDLState state;
		
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 
		
		/** GENERAL CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);;
		/** GENERAL CONFIGURATION */
		
		// search spaces
		String[][] search = {
				{"failurePfile1Time0","failurePfile1Time1"},
				{"failurePfile2Time0","failurePfile2Time1","failurePfile2Time2","failurePfile2Time3"},
				{"failurePfile3Time0","failurePfile3Time1","failurePfile3Time2","failurePfile3Time3"},
				{"failurePfile4Time0","failurePfile4Time1"},
				{"failurePfile5Time0","failurePfile5Time1"},
				{"failurePfile6Time0","failurePfile6Time1","failurePfile6Time2"},
				{"failurePfile7Time0","failurePfile7Time1","failurePfile7Time2"},
				{"failurePfile8Time0","failurePfile8Time1"},
				{"failurePfile9Time0","failurePfile9Time1"},
				{"failurePfile10Time0","failurePfile10Time1"},
				{"failurePfile11Time0","failurePfile11Time1"},
				{"failurePfile12Time0","failurePfile12Time1"}};
		
		// for each domain 
		// for (int j = 0; j < 1; ++j){ 
		for (int j = 0; j < domain.length; ++j){
			
			for (int k = init_problem; k < total_problem; ++k){
				
				if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = k;
				
				// generating a plan with pddl2.1 and lpg
				pddl21[0] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/domain"+k+".pddl";
				pddl21[1] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/"+benchmark[6]+k;	
				
				name_domain = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/domain.pddl";
				
				for(int i = 0; i < search[k-1].length; ++i) { 
					
					// simulator
					sim = new Simulator(pddl21[0],pddl21[1]);
					
					input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);

					if(input_plan.size() >=0 ) { 
						
						rp 	 = new MAReactivePlanner(pddl21, input_plan, configuration, ".");
	
						// simulating the plan execution
						int t = i;
						while(t > 0){
							action = rp.getPlanningProblem().getBestPlan().getActionPlan().get(0).toString();
							state = updateState(sim, action);
							if(state == null)
								System.out.println("[ERROR] ");
							else{
								System.out.println(state.toString());
							}
							rp.getPlanningProblem().simulateExecution(false);
							--t;
						}
						
						
						// simulating the plan failure
						action = search[k-1][i];
						state = updateState(sim, action);
						if(state == null)
							System.out.println("[ERROR] ");
												
						// llamamos a lama y a lpg-adapt
						name_plan = System.getProperty("user.dir")+
								"/results/"+rp.getPlanningProblem().getDomainName() 
								+ "/plan-"+rp.getPlanningProblem().getProblemName()+"-T"+i;
						name_pfile =  System.getProperty("user.dir")+
								"/results/"+rp.getPlanningProblem().getDomainName()+"/"+rp.getPlanningProblem().getProblemName()+"-T"+i;
						toPddl21(rp.getPlanningProblem(), state, name_pfile);
						printPlan(rp.getPlanningProblem(), name_plan);

						
						double stability_repair, stability_replan;
						long time_repair = System.currentTimeMillis();
						
						ArrayList<DSPlan> plan_generated = repairingLpgAdapt(name_domain, name_pfile, name_plan);
						
						time_repair = System.currentTimeMillis() - time_repair;
						
						int repair_total_plan = 0;
						if(plan_generated.size() > 0) { 
							stability_repair = rp.getPlanningProblem().getBestPlan().getStability(plan_generated.get(plan_generated.size()-1));
							repair_total_plan = (int) plan_generated.get(plan_generated.size()-1).getTotalActions();
							// calculate stability
						} else {
							stability_repair = 0;
						}
						
						long time_replan = System.currentTimeMillis();
						
						plan_generated = replanLAMA(name_domain, name_pfile);
						
						time_replan = System.currentTimeMillis() - time_replan;
						
						int replan_total_plan = 0;
						if(plan_generated.size() > 0) { 
							stability_replan = rp.getPlanningProblem().getBestPlan().getStability(plan_generated.get(plan_generated.size()-1));
							replan_total_plan = (int) plan_generated.get(plan_generated.size()-1).getTotalActions();
						} else {
							stability_replan = 0;
						}
						
						results = rp.getPlanningProblem().getDomainName()+ " & " + rp.getPlanningProblem().getProblemName() + " & "
								+ i + " & "+ ((plan_generated.size()==0)?"not_solution":"solution") + " & "
								+ time_repair + " & " + time_replan + " & " 
								+ rp.getPlanningProblem().getBestPlan().getTotalActions() + " & "
								+ stability_repair + "/" + repair_total_plan + " & "
								+ stability_replan + "/" + replan_total_plan +"\n";

						Files files = new Files("./results/"+rp.getPlanningProblem().getDomainName()+"/results-"+rp.getPlanningProblem().getDomainName(), true);
						files.write(results);
						files.close();
					}
				}
			}
		}
		
	}
	
	private static void printPlan(PlanningProblem rp, String name) {
		String s_plan = "";
		DSPlan plan = rp.getBestPlan();
		//s_plan = s_plan + "Domain: "+plan.getDomain()+"\n";
		//s_plan = s_plan + "Problem: "+plan.getProblem()+"\n";
		//s_plan = s_plan + "Start time: "+plan.getStartTime()+"\n\n";
		s_plan = s_plan + plan.toLPG_TD();
		
		Files files = new Files(name, false);
		files.write(s_plan);
		files.close();
	}

	public static ArrayList<DSPlan> gettingPlanLPGADAPT(String dominio, String problema, String dir_problem, String dir_plan) {

		int 		total_actions 	= 0;
		int 		isInputPlan;
		double 		makespan 		= 0.0;
		double 		start			= 0.0;
		double 		min_start		= 10000.0;
		double 		t_planner		= 0.0;
		String[] 	temp, temp2 	= dir_problem.split(separator);
		Files 		fils 			= new Files();
		String 		n,plan_sol;
		StringTokenizer st1;
		DSPlanAction aplan;
		ArrayList<DSPlanAction> plan=null;
		
		int number_solution = getNumberSolutionLPGADAPT(dir_plan+"_");// We get the best solution of LPG
		ArrayList<DSPlan> plan_generated = new ArrayList<DSPlan>(number_solution);
		for(int i = 1; i <= number_solution; ++i){
			plan_sol = fils.read(dir_plan+"_"+i+".SOL");// Read the solution
			st1 = new StringTokenizer(plan_sol, "\n");//1. Using StringTokenizer constructor
			
			while(st1.hasMoreTokens()){//iterate through tokens
				String nextToken = st1.nextToken();
				
				if (nextToken.contains("; Time "))
					t_planner = Double.parseDouble(nextToken.replaceAll("; Time ", ""));
				
				if (nextToken.contains("; NrActions ") || nextToken.contains("; MetricValue ")
						 || nextToken.contains("; MakeSpan ")){
					if (nextToken.contains("; NrActions ")){
						total_actions = Integer.parseInt(nextToken.replaceAll("; NrActions ", ""));
						makespan = total_actions;
					}
					if (nextToken.contains("; MakeSpan ")){
						total_actions = 0;
						makespan = Double.parseDouble(nextToken.replaceAll("; MakeSpan ", ""));
					}
					logger.debug("- Makespan: "+makespan);
					logger.debug("- Total of actions: "+total_actions);
					
					if (0 == total_actions && 0 == makespan) return plan_generated;
					plan = new ArrayList<DSPlanAction>(total_actions);
					
					nextToken = st1.nextToken();
					
					boolean cont = false;
					if(total_actions==0)
						cont=true;
					// Obtenemos las actions
					while (st1.hasMoreTokens()){
						temp 		= st1.nextToken().split(":");
						temp2 		= temp[1].split("\\[");
						
						// Obtenemos el nombre de la acción
						temp2[0] 	= temp2[0].trim().replaceAll("\\(", ""); 
						temp2[0] 	= temp2[0].replaceAll("\\)", "");
						temp2[1]	= temp2[1].replaceAll("\\]", "");
						
						// Action of the plan
						n = temp2[0].toLowerCase();
						start = Math.round(Double.parseDouble(temp[0]));
						if (start < min_start) min_start = start;
						if (temp2[1].contains("InputAct")){
							  isInputPlan = DSPlanAction.ORIGINAL_PLAN;
							  temp2[1] = temp2[1].replaceAll(" ;; InputAct", "");
						}else  isInputPlan = DSPlanAction.NOT;
						aplan = new NPlanAction(n, start,
								Double.parseDouble(temp2[1]), (int)start
								, -1, -1, isInputPlan);
						plan.add(aplan);
						if(cont==true)
							++total_actions;
					}
				}
			}
		
			plan_generated.add(new NPlan(dominio, problema, makespan, min_start, plan, t_planner,total_actions));
		}
		return plan_generated;
	}
	
	public static int getNumberSolutionLPGADAPT(String solution) {
		int num_sol = 1;
		String path;
		File fichero;
		
		while (true){
			path = solution + num_sol+".SOL";
			fichero = new File(path);
			if (!fichero.exists()) return num_sol-1;
			++num_sol;
		}
	}
	
	private static ArrayList<DSPlan> replanLAMA(String name_domain, String name_pfile) {
		
		String[] s = name_pfile.split(separator);
		
		String solution = System.getProperty("user.dir")+"/solutions"+separator+"replan-"+s[s.length-1];

		String execute = General.user_dir+separator+CONFIG.PATH_PLANNERS+separator+"LAMA"+separator+"./plan "
				+ name_domain + " "+ name_pfile + " " + solution +"\n";
		
		String temp = "cd "+System.getProperty("user.dir")+"\n";
		temp = temp + execute ;
		
		Files files = new Files(System.getProperty("user.dir")+separator+"results"
		+separator+rp.getPlanningProblem().getDomainName()+separator+"execute", true);
		files.write(execute);
		files.close();
				
		executeScript(temp);

		return gettingPlanLAMA(rp.getPlanningProblem().getDomainName(), 
				rp.getPlanningProblem().getProblemName(), name_pfile, solution);
	}
	
	// String dominio, String problema
	public static ArrayList<DSPlan> gettingPlanLAMA(String dominio, String problema, String dir_problem, String dir_plan) {

		ArrayList<DSPlanAction> plan	= null;
		DSPlanAction 			aplan;

		int 			total_actions 	= 0;
		double 			makespan 		= 0.0;
		double 			t_planner		= 0.0;
		
		Files 			fils 			= new Files();
		String 			plan_sol;
		String 			tmp;
		StringTokenizer st1;

		int number_solution = getNumberSolutionLAMA(dir_plan);
		ArrayList<DSPlan> plan_generated = new ArrayList<DSPlan>(number_solution);
		
		for(int i = 1; i <= number_solution; ++i){
			tmp = dir_plan+"."+i;
			plan_sol = fils.read(tmp);// Read the solution
			st1 = new StringTokenizer(plan_sol, "\n");//1. Using StringTokenizer constructor

			plan = new ArrayList<DSPlanAction>(total_actions);
			
			//iterate through tokens
			double index = 0;
			while(st1.hasMoreTokens()){
				String nextToken = st1.nextToken();
				makespan		=0;
				
				// Obtenemos las actions					
				nextToken = nextToken.trim().replaceAll("\\(", "");
				nextToken = nextToken.replaceAll("\\)", "");
				
				aplan = new NPlanAction(nextToken.toLowerCase().trim(), index,
						1.0, (int)index, -1, -1, DSPlanAction.NOT);
				plan.add(aplan);
				
				index++;
			}

			plan_generated.add(new NPlan(dominio, problema, makespan, 0, plan, t_planner,(int)index));
			// fils.delete(tmp);
		}
		return plan_generated;
	}	
	

	public static int getNumberSolutionLAMA(String solution) {
		int num_sol = 1;
		String path;
		File fichero;
		
		while (true){
			path = solution +"."+num_sol;

			fichero = new File(path);

			// Verificamos si existe
			if (!fichero.exists())
				return num_sol-1;
			
			++num_sol;
		}
	}

	private static ArrayList<DSPlan> repairingLpgAdapt(String domain, String name_pfile, String plan) {
		String[] s = name_pfile.split(separator);
		
		String output_plan = System.getProperty("user.dir")+"/solutions"+separator+"repair-"+s[s.length-1];
		
		String execute = General.user_dir+separator+CONFIG.PATH_PLANNERS+separator+"LPG-ADAPT"+separator+"./lpg-adapt -o "
				+ domain + " -f "+ name_pfile + " -n 1 -cputime "+planner_max_cpu_time
				+ " -out "+output_plan +" -input_plan "+
				plan+"\n";
		
		String temp = "cd "+System.getProperty("user.dir")+"\n";
		temp = temp + execute;
		
		Files files = new Files(System.getProperty("user.dir")+separator+"results"
		+separator+rp.getPlanningProblem().getDomainName()+separator+"execute", true);
		files.write(execute);
		files.close();
		
		executeScript(temp);
		
		return gettingPlanLPGADAPT(rp.getPlanningProblem().getDomainName(), 
				rp.getPlanningProblem().getProblemName(), name_pfile, output_plan);
	}
	
	/**
	 * PELEA-BPGS-Prueba - 05/10/2010
	 * execute script con un proceso, para evitar los tiempos largos en la ejecución del planner
	 * @param temp
	 */
	public static void executeScript(String temp) {
		Thread thread = new ExecuteScript_With_Thread(temp); 
		thread.start(); 
		
		long startTime = System.currentTimeMillis();
		while(thread.isAlive())
		{
			long afterGrounding = System.currentTimeMillis();
			
			if (((afterGrounding - startTime)/1000.00)>=180)
				thread.interrupt();
		}
		
		thread = null;
	}

	public static String toPddl21(PlanningProblem p, PDDLState state, String name_pfile) {
		
		String objAll = "";
		objAll = objAll+"(define (problem "+p.getProblemName()+")";
		objAll = objAll+"\n\t(:domain "+p.getDomainName()+")";
		
		// Objects
		objAll = objAll+"\n\t(:objects ";
		String[] t;
		for(String n: p.getObjects()){
			t = p.getObjectTypes(n);
			if(t.length<2){
				if(t.length!=0 && !t[0].equals("boolean")&& !t[0].equals("number")){
					objAll = objAll+"\n\t"+n+" - ";
					for(String s: t)objAll = objAll+s;
				}
			}else{
				objAll = objAll+"\n\t"+n+" - (either ";
				for(String s: t)objAll = objAll+s;
				objAll = objAll+")";
			}
		}
		objAll = objAll+"\n\t)";
		
		
		// initial state
		objAll = objAll+"\n\t(:init";
		String tmp = null;
		for(Atom cond: state.getPredicates()){
			tmp = cond.toString();
			if (tmp.length()>0)
				objAll = objAll+"\n\t"+tmp;
		}
		objAll = objAll+"\n\t)";
		
		// goal state
		objAll = objAll+"\n\t(:goal (and";
		for(DSCondition cond: p.getGlobalGoalState()){
			tmp = cond.toPddl21();
			if (tmp.length()>0)
				objAll = objAll+"\n\t"+tmp;
		}
		objAll = objAll+"\n\t))\n)";
		
		Files files = new Files(name_pfile, false);
		files.write(objAll);
		files.close();
		
		return objAll;
	}
	
	private static PDDLState updateState(Simulator sim, String action) {

		try {
			return sim.getState(action);
		} catch (NotFeasibleActionException e) {}
		return null;
	}

	private void verifyingResultsPartialStates() {
		
		//domain	= new String[]{"DriverLog", "logistics","Rovers","Woodworking", "openstacks"}; 
		domain	= configuration.getValue(From.C_REACTIVEPLANNER, "DOMAIN_NAME").split(","); 
		
		/** GENERAL CONFIGURATION */
		int 	init_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[0]);
		int 	total_problem	= Integer.parseInt(configuration.getValue(From.C_REACTIVEPLANNER, "PROBLEM").split("-")[1]);
		/** GENERAL CONFIGURATION */
		
		
		// for each domain
		// for (int j = 0; j < 1; ++j){ 
		for (int j = 0; j < domain.length; ++j){
			
			for (int k = init_problem; k < total_problem; ++k){
				
				if(CONFIG.DEBUG_MET_STA) CONFIG.PFILE = k;
				
				// generating a plan with pddl2.1 and lpg
				pddl21Path(j,k);
				
				input_plan 	= searchPlanToAction(pddl21, input_planner, n_sol_ori_plan, PLANNER.GET_LAST_SOLUTION);
				if(input_plan.size()>=0){
					
					// planning problem of pddl3.1
					pddl31Path(j,k);
					
					
					/** initializing the Reactive Planner **/
					rp 	 = new MAReactivePlanner(pddl31, input_plan, configuration, ".");
					// rp.getPlanningProblem().printPlan();
					
					
					((MAReactivePlanner)rp).translating();
					rp.printResultsPartialStates(rp.getPlanningProblem().getBestPlan().getActionPlan().size(), "./results/");
					
				}
			}
		}
	}

	private void pddl31Path(int j, int k) {
		pddl31[0] = benchmark[0] +"/"+domain[j]+"/"+benchmark[1]+k+"/"+benchmark[2];
		pddl31[1] = benchmark[0] +"/"+domain[j]+"/"+benchmark[1]+k+"/"+benchmark[3];
		pddl31[2] = "";
	}

	private void pddl21Path(int j, int i) {
		if("openstacks".equals(domain[j])){
			pddl21[0] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/"+benchmark[6]+i+"-"+benchmark[5];
			pddl21[1] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/"+benchmark[6]+i;
		}else{
			pddl21[0] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/"+benchmark[5];
			pddl21[1] = benchmark[4] +"/"+domain[j]+"/"+type_domain[0]+"/"+benchmark[6]+i;					
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void print(String domain, TypeTests op_test2) {
		
		not_found_destiny	=0; 
		not_found_origin	=0; 
		not_found_plan		=0;
		num_adit_action		=0;
		stability			=0;
		
		evalRP = new Files(op_test2.res_dir_evaluations, true);
        evalRP.write(domain);
        evalRP.write("\t"+op_test2.res_no_search_tree);
        evalRP.write("\t"+op_test2.problem);
        evalRP.write("\t"+op_test2.res_t_action);
        evalRP.write("\t"+op_test2.res_planning_horizon);
        evalRP.write("\t"+op_test2.res_depth);
        evalRP.write("\t"+op_test2.res_limit_time_tree);
        evalRP.write("\t"+op_test2.res_estimated_time+" ms(est_T)");
        evalRP.write("\t"+op_test2.res_time_T+" ms(T)");
        evalRP.write("\t"+op_test2.res_total_time_T+" ms(alg+T)");//9
        evalRP.write("\t"+not_found_destiny+" des");
        evalRP.write("\t"+not_found_origin+" ori");//13
        evalRP.write("\t"+not_found_plan+" plan");
        evalRP.write("\t"+timeFindPlan+" ms");
        evalRP.write("\t"+num_adit_action+" add acts");
        evalRP.write("\t0 plan");
        evalRP.write("\t"+stability+" stab");
        evalRP.write("\t0 repaired");
        evalRP.write("\t"+op_test2.res_est_bran_factor+" est_b");
        double value = (double)1/op_test2.res_depth;
        evalRP.write("\t"+Double.toString((((double)Math.pow(op_test2.res_info_search_tree.generating_total_nodes,value)-0.34)))+" Realb*");
        evalRP.write("\t"+op_test2.res_est_total_nodes+" est_N");
        evalRP.write("\t"+op_test2.res_info_search_tree.generating_total_nodes+" RealN");
        evalRP.write("\t"+op_test2.res_est_eval_nodes+" est_ENodes");
        evalRP.write("\t"+op_test2.res_info_search_tree.generating_evaluated_nodes+" Real_ENodes");
        evalRP.write("\t"+((Hashtable<DSVariables, String>)op_test2.res_parameters[0]).size()+" RVar");
        evalRP.write("\t"+((op_test2.res_parameters[2]==null)?0:((ArrayList<DSCondition>)op_test2.res_parameters[2]).size())+" RvarRoot");
        evalRP.write("\t"+((ArrayList<DSCondition>)op_test2.res_parameters[3]).size()+" varRoot");
        evalRP.write("\t"+(Integer)op_test2.res_parameters[4]+" producersT");
        evalRP.write("\t"+((op_test2.res_parameters[5]==null)?0:((HashSet<DSAction>)op_test2.res_parameters[5]).size())+" producersTU\n");
                
        evalRP.close();
        
	}
	
	@SuppressWarnings("unchecked")
	private static void printTable(String domain, TypeTests op_test2) {
		
		not_found_destiny	=0; 
		not_found_origin	=0; 
		not_found_plan		=0;
		num_adit_action		=0;
		stability			=0;
		
		evalRP = new Files(op_test2.res_dir_evaluations, true);
        evalRP.write(domain);
        evalRP.write("\t"+op_test2.problem);
        evalRP.write("\t"+op_test2.res_t_action);
        evalRP.write("\t"+op_test2.res_no_search_tree);
        evalRP.write("\t"+op_test2.res_planning_horizon);
        evalRP.write("\t"+op_test2.res_depth);
        evalRP.write("\t"+op_test2.res_limit_time_tree);
        evalRP.write("\t"+op_test2.res_estimated_time);
        evalRP.write("\t"+op_test2.res_time_T);
        //evalRP.write("\t"+op_test2.res_time_finding_best_size);
        //evalRP.write("\t"+op_test2.res_total_time_T);
        // evalRP.write("\t"+op_test2.res_total_time_T+" ms(alg+T)");//9
        // evalRP.write("\t"+not_found_destiny+" des");
        // evalRP.write("\t"+not_found_origin+" ori");//13
        // evalRP.write("\t"+not_found_plan+" plan");
        // evalRP.write("\t"+timeFindPlan+" ms");
        // evalRP.write("\t"+num_adit_action+" add acts");
        // evalRP.write("\t0 plan");
        // evalRP.write("\t"+stability+" stab");
        // evalRP.write("\t0 repaired");
        evalRP.write("\t"+op_test2.res_est_bran_factor);
        double value = (double)1/op_test2.res_depth;
        evalRP.write("\t"+Double.toString((((double)Math.pow(op_test2.res_info_search_tree.generating_total_nodes,value)-0.34))));
        evalRP.write("\t"+op_test2.res_est_total_nodes);
        evalRP.write("\t"+op_test2.res_info_search_tree.generating_total_nodes);
        evalRP.write("\t"+op_test2.res_est_eval_nodes);
        evalRP.write("\t"+op_test2.res_info_search_tree.generating_evaluated_nodes);
        
        // relevant Variables
        evalRP.write("\t"+((Hashtable<DSVariables, String>)op_test2.res_parameters[0]).size());
        // relevant variables in the root
        evalRP.write("\t"+((op_test2.res_parameters[2]==null)?0:((ArrayList<DSCondition>)op_test2.res_parameters[2]).size()));
		// root variables
        evalRP.write("\t"+((ArrayList<DSCondition>)op_test2.res_parameters[3]).size());
		// total of producers
        evalRP.write("\t"+(Integer)op_test2.res_parameters[4]);
		// total of unique producers
        evalRP.write("\t"+((op_test2.res_parameters[5]==null)?0:((HashSet<DSAction>)op_test2.res_parameters[5]).size())+"\n");
        // evalRP.write("\t"+op_test2.res_parameters[1]+"\n");
                
        evalRP.close();
        
	}
	
	private static void executingReactivePlanner(String domain, int pfile, int WINDOW_SIZE, int MAX_DISTANCE) {
		
		not_found_destiny	=1; 
		not_found_origin	=1; 
		not_found_plan		=1;
		num_adit_action		=1;
		stability			=1;
		
		// finding and sending the origin and destiny with the reactive planner
		PlanXML 			plan_repaired ;
		DSPlan 				plan_ori		= null;
		int 				found 			= -1;
		int 				foundDestiny	= -1;


		evalRP2 = new Files(path_failures_rp, true);

    	System.out.println("\n\nPlanning problem: "+rp.getPlanningProblem().getProblemName());

		List<Integer> actions = rp.get_actions_current_window();
		evalRP2.write("\n\nPlan window:\t");
		if( actions != null) {
			for(Integer act : actions)
				evalRP2.write("\n"+rp.getPlanningProblem().getActions().get(act).toString());
		}else {
			evalRP2.write("EMPTY");
		}
		
		initialTime = System.nanoTime();
		
		try {
			plan_repaired = new PlanXML(rp.single_repairing(current_state, index_destiny));
		} catch (NoPosiblePlanException e) {
			plan_repaired = new PlanXML("");
			e.printStackTrace();
		}
		
        timeFindPlan = System.nanoTime();
        timeFindPlan = timeFindPlan-initialTime;

		evalRP2.write("\n\nRepaired plan:\t");
        if(plan_repaired.getActions().size()>0)
			for(PlanAction a : plan_repaired.getActions())
				evalRP2.write("\n"+a.toString());
        else evalRP2.write("\n\n No solution (RP).");
       
   		plan_ori  = rp.getPlanningProblem().getBestPlan();

		actions = rp.get_actions_current_window();
		evalRP2.write("\n\nNew Plan window:\t");
		if( actions != null) {
			for(Integer act : actions)
				evalRP2.write("\n"+rp.getPlanningProblem().getActions().get(act).toString());
		}else {
			evalRP2.write("EMPTY");
		}
		
		try {
			evalRP2.write("\n\nOrigin found:\t"+rp.get_single_statistic().repair_found_plan.toString());
		} catch (NoStatisticException e) {
			e.printStackTrace();
		}
		
		try {
			evalRP2.write("\n\nError info:\t"+rp.get_single_statistic().repair_error.toString());
		} catch (NoStatisticException e) {
			e.printStackTrace();
		}
		
        
        evalRP2.write("\n\nSearch time: " + timeFindPlan + " ns\n");
		evalRP2.close();
		
       	if (plan_repaired.getActions().size()==0)
       		not_found_plan=0;
       	else{
       		if (plan_ori.getActionPlan().size()==plan_repaired.getActions().size())
       			num_adit_action = 1;
       		else
       			num_adit_action=plan_repaired.getActions().size()-plan_ori.getActionPlan().size();
       		
       	}

		evalRP = new Files(path_eval_rp, true);
        evalRP.write(domain); // 1
        evalRP.write("\tpfile"+pfile); // 2
        evalRP.write("\tw"+WINDOW_SIZE); // 3
        evalRP.write("\tl"+MAX_DISTANCE); // 4
        evalRP.write("\t"+bestValue.getEstimatedTime()+" ms(est_T)"); // 5
        evalRP.write("\t"+timeT+" ms(T)"); // 6
        evalRP.write("\t"+timeT_total+" ms(alg+T)"); // 7
        evalRP.write("\t"+not_found_destiny+" des"); // 8
        evalRP.write("\t"+not_found_origin+" ori"); // 9
        evalRP.write("\t"+not_found_plan+" plan"); // 10
        evalRP.write("\t"+(timeFindPlan/1000000)+" ms"); // 11
        evalRP.write("\t"+num_adit_action+" add acts"); // 12
        evalRP.write("\t"+plan_ori.getActionPlan().size()+" plan_complete"); // 13
        evalRP.write("\t"+stability+" stab"); // 14
        evalRP.write("\t"+plan_repaired.getActions().size()+" repaired_complete"); // 15
        evalRP.write("\t"+(WINDOW_SIZE-index_destiny)+" plan_act_to_rn"); // 16
        if(not_found_plan!=0){
	        evalRP.write("\t"+rp.getRepairedStability(index_destiny, WINDOW_SIZE)+" stab_rep_act_to_rn"); // 17

			try {
				evalRP.write("\t"+rp.get_single_plan_to_root().size()+" repaired_act_to_rn\n");
			} catch (GeneralException e1) {
				e1.printStackTrace();
			}// 18
        }else{
	        evalRP.write("\t0 stab_rep_act_to_rn"); // 17
	        evalRP.write("\t0 repaired_act_to_rn\n"); // 18  	
        }
        evalRP.close();
        
        
        evalRP = new Files(path_analysis_rp, true);
        evalRP.write(domain);
        evalRP.write("\t"+pfile);
        evalRP.write("\t"+WINDOW_SIZE);
        evalRP.write("\t"+MAX_DISTANCE);
        //evalRP.write("\t"+relevant_var);
        //evalRP.write("\t"+rVar_root);
        evalRP.write("\t"+bestValue.getEstimatedTime()+" ms (est_T)");
        evalRP.write("\t"+timeT+" ms (T)");
        evalRP.write("\t"+timeT_total+" ms (alg+T)");
        evalRP.write("\n");
        evalRP.close();
	}

	/**
	 * SE ENCUENTRA EN GTSPLANINTERACTION
	 */
	private static RGS_GH changingCurrentValuesPre(ArrayList<DSCondition> current_state,ArrayList<DSCondition> target) {
		ArrayList<DSCondition> init = new ArrayList<DSCondition>();
		
		// for all preconditions
		for (DSCondition t1: target)
			for (DSCondition c: current_state){
				if (!t1.getFunction().equals(c.getFunction())) continue;
				init.add(c);
				break;
			}
		
		return new RGS_GH(init);
	}
	
	private static void printingFailure(T_MODULES technique, int pfile) {
		
		if(technique == T_MODULES.MODULE_ALL || technique == T_MODULES.MODULE_REACTIVEPLANNER){
			evalRP2 = new Files(path_failures_rp, true);								
			evalRP2.write("\n**********************");
			evalRP2.write("\n\nPfile :\t"+pfile);
			//System.out.print("\nCurrent state:\t"+current_state.toString());
			//System.out.print("\n\nOrigin GS:\t"+origin.getGoalState().toString());
			//System.out.print("\n\nDestiny GS "+index_destiny+":\t"+goal_state.toString());

			evalRP2.write("\n\nRelevant Vars in W: "+filtered_var.toString());
			evalRP2.write("\n\nb) Random failures:");
			evalRP2.write("\n\nInitial state Vs Current state (failures)\n\n");
			evalRP2.write(differents(init_cur_state, current_state));
			
			evalRP2.write("\n\nOrigin vs Destiny (RGS):\n");
			evalRP2.write(differents((ArrayList<DSCondition>) origin.getFluents(), goal_state));
			//System.out.print("\n\t\t -> [");
			//current(current_state, filtered_var);
			//System.out.print("]\n\n");
			evalRP2.close();
		}
		if(technique == T_MODULES.MODULE_ALL){
			evalDS2 = new Files(path_failures_ds, true);
			evalDS2.write("\n**********************");
			evalDS2.write("\n\nPfile :\t"+pfile);
			evalDS2.write("\n\nb) Random failures:");
			evalDS2.write("\n\nCurrent state Vs Current state with failure\n\n");
			evalDS2.write(differents(init_cur_state, current_state));
			evalDS2.write("\n\nCurrent state vs Destiny GS:\t\n");
			evalDS2.write(differents(current_state, goal_state));
			evalDS2.close();
		}
	}

	private static void printingFailureRP(int d, int pfile) {
		
		evalRP2 = new Files(path_failures_rp, true);			
		evalRP2.write("\n\n\n\n**********************");
		evalRP2.write("\n"+domain[d] + " - pfile"+ pfile);
		evalRP2.write("\nSEARCH TREE=1");
		evalRP2.write("\nTIME STEP="+index_destiny);
		evalRP2.write("\nWINDOW="+bestValue.getPlanningHorizon());
		evalRP2.write("\nDEPTH="+bestValue.getDepth());		
		evalRP2.write("\n**********************");
		//System.out.print("\nCurrent state:\t"+current_state.toString());
		//System.out.print("\n\nOrigin GS:\t"+origin.getGoalState().toString());
		//System.out.print("\n\nDestiny GS "+index_destiny+":\t"+goal_state.toString());

		// evalRP2.write("\n\nRelevant Vars in W: "+filtered_var.toString());
		
		String s_failure = "by action";
		if(op_test.type_failure==T_FAILURE.BYVALUE)
			s_failure="by exogenous events";
		
		evalRP2.write("\n\nb) Random failures: "+s_failure);
		//evalRP2.write("\n\nInitial state Vs Current state (failures)\n\n");
		//evalRP2.write(differents(init_cur_state, current_state));
		
		// evalRP2.write("\n\nTarget partial states vs Current value of the target partial state:\n");
		evalRP2.write("\n\n"+differents((ArrayList<DSCondition>) origin.getFluents(), goal_state));
		//System.out.print("\n\t\t -> [");
		//current(current_state, filtered_var);
		//System.out.print("]\n\n");
		evalRP2.close();
		
	}

	/**
	 * ReactivePlanner - 09/08/2013
	 * 
	// we simulate the failure by a random action or an exogenous event.
	// the failure is simulated by modifying both a) the value of a precondition 
	// of the next action and b) the value of either 1) the regressed goal state 
	// or 2) the relevant variables of the window specified by the reactive planner.
	// With a) we simulate a failure in the execution that is detected by the monitoring module. 
	 * @param technique
	 * @param timeStep 
	 */
	private void simulatingFailure(T_MODULES technique, int timeStep) {
		
		if(debug_failures){
			init_cur_state = new ArrayList<DSCondition>(rp.getPlanningProblem().getInitialState().size());
			init_cur_state.addAll(rp.getPlanningProblem().getInitialState());
		}
		
		// getting the goal state
		goal_state		= (ArrayList<DSCondition>) ((PS) rp.get_goal_state(index_destiny)).getFluents();
		
		
		// a) applying a random failure in the preconditions of the next action
		filtered_var	= rp.filterNextAction(goal_state,timeStep);
		((PlanningProblemImp)rp.getPlanningProblem()).simulateFailure(op_test.type_failure, op_test.noisy_actions, filtered_var,true,seed);
		
		
		// b) applying a random failure in either 1) the goal state or 2) the relevant variables in the window
		if(op_test.type_of_filter == T_FILTER.BY_TRGS){ 
			// 1) the goal state
			filtered_var.clear();
			filtered_var.addAll(goal_state);
		}else{
			if(op_test.type_of_filter == T_FILTER.BY_REL_VAR) 
				// 2) the relevant variables
				filtered_var = rp.filterRelevantVar(goal_state);
		}
		//rp.getPlanningProblem().simulateFailure(type_failure, noisy_actions, filtered_var, true,seed);

		
		// getting the initial state
		current_state 	= rp.getPlanningProblem().getInitialState();
		
		
		// changing the current state of the decision support TODO - cambiarlo despues de obtener el problema de la ventana
		if(technique == T_MODULES.MODULE_ALL)
			anytime.replaceInitialState(current_state);
		
		
		// changing the value of the state variables in the goal state with the failure value, return the goal state
		// origin 		= trgs.getGoal_state().get(index_origen).changingCurrentValues(current_state);
		
		
		// changing the value of the state variables in the goal state with the failure value, return the pre of the next action as a goal state 
		origin_pre 	= changingCurrentValuesPre(current_state, rp.filterNextAction(goal_state,timeStep));
		
		
		origin = origin_pre;
	}

	private void executingAnytime(String[] pddl21,
			ArrayList<DSPlan> input_plan, String path_f_resul, String domain, int pfile, int n_sol_lpg_adapt, String path_eval_ds) {

		DSPlan 	 ds_plan;
		Decision decision;
		int 	 decision_t;
		long 	 time;
		ArrayList<DSPlan> lpg_adapt_plan;
		double[] t_plan  = new double[3];	// estadistica;		
		
		/** calculating and saving the repaired plan from the decision support and lpg-adapt */
		time 		= System.currentTimeMillis();
		decision 	= anytime.getDecision();			// getting the decision
		t_plan[0] 	= System.currentTimeMillis()-time;
		time 		= System.currentTimeMillis();
		decision_t 	= anytime.getDecisionGoalState();	
		ds_plan 	= anytime.getPlanRepaired();		// getting the repaired plan
		t_plan[1] 	= System.currentTimeMillis()-time;
		if(ds_plan!=null){
			System.out.println("\nRepaired Plan AnyTime (DS): "+ds_plan.toLPG_TD());
			evalDS2 = new Files(path_failures_ds, true);				
			evalDS2.write("\nRepaired Plan AnyTime (DS): "+ds_plan.toLPG_TD());
			evalDS2.close();
		}else{
			System.out.println("\nRepaired Plan AnyTime (DS): No Solution!");
			evalDS2 = new Files(path_failures_ds, true);				
			evalDS2.write("\nRepaired Plan AnyTime (DS): No Solution!");
			evalDS2.close();
			decision 	= Decision.REPLANN;
			decision_t 	= 0;
		}
		
		// lpg-adapt
		time 			= System.currentTimeMillis();
		pddl21[2] 		= input_plan.get(input_plan.size()-1).toLPG_TD();// plan initial
		lpg_adapt_plan	= searchPlanToAction(pddl21, CONFIG.PLANNER_LPG_ADAPT, n_sol_lpg_adapt, PLANNER.NOT_GET_LAST_SOLUTION);
		t_plan[2] 	  	= System.currentTimeMillis()-time;
		
		// saving the results
		savingResults(pfile, input_plan, ds_plan, decision, decision_t, lpg_adapt_plan, path_f_resul, t_plan, path_eval_ds, domain);
	}
	

	private void executingLPGADAPT(String[] pddl21,
			ArrayList<DSPlan> input_plan, String path_f_resul, String domain,
			int pfile, int n_sol_lpg_adapt, String path) {

		long 	 time;
		ArrayList<DSPlan> lpg_adapt_plan;
		double[] t_plan = new double[3];	// estadistica;		
		
		t_plan[0] = -1;
		t_plan[1] = -1;
		
		// lpg-adapt
		time 			= System.currentTimeMillis();
		pddl21[2] 		= input_plan.get(input_plan.size()-1).toLPG_TD();// plan initial
		lpg_adapt_plan	= searchPlanToAction(pddl21, CONFIG.PLANNER_LPG_ADAPT, n_sol_lpg_adapt, PLANNER.NOT_GET_LAST_SOLUTION);
		t_plan[2] 	  	= System.currentTimeMillis()-time;
		
		// saving the results
		savingResults(pfile, input_plan, null, null, -1.0, lpg_adapt_plan, path_f_resul, t_plan, path, domain);
		
	}

	/**
	 * 20/04/2012 13:05:29
	 * initializing a file
	 * @param path_file	path of the file
	 * @param content	initial content of the file
	 */
	private static void initializing_file(String path_file, String content) {
		Files file = new Files(path_file, false);
		file.close();
		file = new Files(path_file, true);
		file.write(content);
		file.close();
	}
	
	/**
	 * 19/04/2012 17:49:18
	 * calculating and saving the metric and stability
	 * @param pfile 			number problem
	 * @param input_plan		original plan
	 * @param ds_plan			plan generated by the decision support
	 * @param decision 			decision taken
	 * @param decision_t 		decision time
	 * @param lpg_adapt_plan	a list of plans generated by lpg adapt
	 * @param path_f_resul 
	 * @param t_plan 
	 * @param evalDS 
	 */
	private static void savingResults(int pfile, ArrayList<DSPlan> input_plan,
			DSPlan ds_plan, Decision decision, double decision_t, ArrayList<DSPlan> lpg_adapt_plan
			, String path_f_resul, double[] t_plan, String evalDS, String domain) {
		
		// 0:metric of the original plan
		// 1:metric of the decision support plan
		// 2:metric of the lpg adapt plan 1
		// 3:metric of the lpg adapt plan n
		double[] met_plans 	= new double[4];
		double[] sta_plans 	= new double[3];
		double[] t_plans 	= new double[5];

		met_plans[0]		= 0;
		if(ds_plan!=null)
			met_plans[0] 	= ds_plan.getTotalActions();
		if (lpg_adapt_plan != null){
			met_plans[1] 	= lpg_adapt_plan.get(0).getTotalActions();
			met_plans[2] 	= lpg_adapt_plan.get(lpg_adapt_plan.size()-1).getTotalActions();
		}
		met_plans[3]		= input_plan.get(0).getTotalActions();

		sta_plans[0]		= 0;
		if(ds_plan!=null)
			sta_plans[0] 	= ds_plan.getStability(input_plan.get(0));
		if (lpg_adapt_plan != null){
			sta_plans[1] 	= lpg_adapt_plan.get(0).getStability(input_plan.get(0));
			sta_plans[2] 	= lpg_adapt_plan.get(lpg_adapt_plan.size()-1).getStability(input_plan.get(0));
		}
		
		t_plans[0] 			= t_plan[0];
		t_plans[1] 			= t_plan[1];
		t_plans[2] 			= t_plan[2];
		if (lpg_adapt_plan != null){
			t_plans[3] 		= lpg_adapt_plan.get(0).getExecutionTimePlanner();
			t_plans[4] 		= lpg_adapt_plan.get(lpg_adapt_plan.size()-1).getExecutionTimePlanner();
		}

		saving_table(pfile, decision, decision_t, met_plans, sta_plans, t_plans, path_f_resul);
		saving_table(pfile, decision, decision_t, met_plans, sta_plans, t_plans, path_f_resul, evalDS, domain);
	}
	

	/**
	 * ReactivePlanner - 05/08/2013
	 * saving the statistics to a not cvs file
	 * @param pfile
	 * @param decision
	 * @param decision_t
	 * @param met_plans
	 * @param sta_plans
	 * @param t_plans
	 * @param path_f_resul
	 * @param path_eval_ds
	 * @param domain
	 */
	private static void saving_table(int pfile, Decision decision,
			double decision_t, double[] met_plans, double[] sta_plans,
			double[] t_plans, String path_f_resul, String path_eval_ds, String domain) {
		
		Files evalDS = new Files(path_eval_ds, true);
		evalDS.write(domain);
		evalDS.write("\t"+pfile);
		evalDS.write("\t"+decision);
		evalDS.write("\t"+decision_t+" (ms)");
		evalDS.write("\t"+String.valueOf(met_plans[0])+" m_ds");
		evalDS.write("\t"+String.valueOf(met_plans[1])+" m_lpgAdapt_fst");
		evalDS.write("\t"+String.valueOf(met_plans[2])+" m_lpgAdapt_lst");
		evalDS.write("\t"+String.valueOf(met_plans[3])+" m_plan_original");
		evalDS.write("\t"+String.valueOf(sta_plans[0])+" stb_ds");
		evalDS.write("\t"+String.valueOf(sta_plans[1])+" stb_lpgAdapt_fst");
		evalDS.write("\t"+String.valueOf(sta_plans[2])+" stb_lpgAdapt_lst");
		evalDS.write("\t"+String.valueOf(t_plans[0])+" t_ds(ms)");
		evalDS.write("\t"+String.valueOf(t_plans[1])+" t_ds_plan(ms)");
		if(t_plans[0]==-1 && t_plans[1]==-1)
			evalDS.write("\t"+String.valueOf(-1)+" total_t_ds(ms)");
		else evalDS.write("\t"+String.valueOf(t_plans[0]+t_plans[1])+" t_ds(ms)");
		evalDS.write("\t"+String.valueOf(t_plans[2])+" t_lpgAdapt(ms)");
		evalDS.write("\t"+String.valueOf(t_plans[3])+" t_lpgAdapt_fst(s)");
		evalDS.write("\t"+String.valueOf(t_plans[4])+" t_lpgAdapt_lst(s)\n");
		evalDS.close();
	}

	// saving the statictis to a cvs file
	private static void saving_table(int pfile, Decision decision, double decision_t, double[] met_plans, double[] sta_plans, double[] t_plans, String path_f_resul) {
		
		Files file = new Files(path_f_resul, true);
		String tmp = "\n pfile \t !ds! \t !ds time! \t " +
				//"\t metric ds \t metric lpg adapt first \t metric lpg adapt last" +
				//"\t metric plan original " +
				"\t stability ds \t stability lpg adapt first" +
				"\t stability lpg adapt last \t time_ds \t time ds plan \t time decision \t " +
				"\t time lpg_adapt \t time lpg adapt first \t time lpg adapt last";
		tmp = tmp.replaceAll("pfile", String.valueOf(pfile));
		tmp = tmp.replaceAll("!ds!", String.valueOf(decision));
		tmp = tmp.replaceAll("!ds time!", String.valueOf(decision_t));
		tmp = tmp.replaceAll("metric ds", String.valueOf(met_plans[0]));
		tmp = tmp.replaceAll("metric lpg adapt first", String.valueOf(met_plans[1]));
		tmp = tmp.replaceAll("metric lpg adapt last", String.valueOf(met_plans[2]));
		tmp = tmp.replaceAll("metric plan original", String.valueOf(met_plans[3]));
		tmp = tmp.replaceAll("stability ds", String.valueOf(sta_plans[0]));
		tmp = tmp.replaceAll("stability lpg adapt first", String.valueOf(sta_plans[1]));
		tmp = tmp.replaceAll("stability lpg adapt last", String.valueOf(sta_plans[2]));
		tmp = tmp.replaceAll("time_ds", String.valueOf(t_plans[0]));
		tmp = tmp.replaceAll("time ds plan", String.valueOf(t_plans[1]));
		if(t_plans[0]==-1 && t_plans[1]==-1)
			tmp = tmp.replaceAll("time decision", String.valueOf(-1));
		else tmp = tmp.replaceAll("time decision", String.valueOf(t_plans[0]+t_plans[1]));
		tmp = tmp.replaceAll("time decision", String.valueOf(t_plans[0]+t_plans[1]));
		tmp = tmp.replaceAll("time lpg_adapt", String.valueOf(t_plans[2]));
		tmp = tmp.replaceAll("time lpg adapt first", String.valueOf(t_plans[3]));
		tmp = tmp.replaceAll("time lpg adapt last", String.valueOf(t_plans[4]));
		
		file.write(tmp);
		file.close();
	}
	
	private static String printAction(PlanningProblem planningProblem, ArrayList<Integer> plans) {
		double 	k = 0.0;
		String tmp = "\n\nRepaired plan (RP):";
		for(int i:plans){
			tmp = tmp+"\n"+ k+":"+planningProblem.getActions().get(i);
			++k;
		}
		return tmp;
	}
	
	
	private static String differents(ArrayList<DSCondition> origin, ArrayList<DSCondition> target) {
		String tmp="";
		boolean found = false;
		for(DSCondition c: origin){
			found = false;
			for(DSCondition t1: target){
				if (c.getFunction().equals(t1.getFunction())){
					found = true;
					if(c.getValue().equals(t1.getValue())){
						//tmp=tmp+"= "+c.getFunction()+" ["+c.getValue()+"]\n";
						tmp=tmp+"= "+c.toString()+"\n";
					}else{
						//tmp=tmp+"- "+c.getFunction()+"["+c.getValue()+","+t1.getValue()+"]\n";
						tmp=tmp+"- "+t1.toString()+" changed by "+c.toString()+"]\n";
					}
					break;
				}
			}
			if(!found){
				// tmp=tmp+"+ "+c.getFunction()+" ["+c.getValue()+"]\n";
				tmp=tmp+"+ "+c.toPddl21()+"\n";
			}
		}
		return tmp;
	}

	
	/*// cargar plan
	PlanXML plan = new PlanXML(planH);
	String actions = "NEWLINE";
	int t_act = 0;
	for(PlanAction acc : plan){
        actions += "\t"+acc.toString() +" NEWLINE";++t_act;
	}
	
	private static void calculatingMP(PlanningProblem planningProblem) throws Exception {
		
		
		InfoMonitor application = new InfoMonitor();
		
		application.findVarToMonitor(planningProblem);

		String var = "Monitor-parameters\n******************\n";
				var = var + application.getListVM().listIMToPddl();
		var = var.replaceAll("#", "\n");
		Files files = new Files("./results/"+planningProblem.getDomainName()+"/monPar-"+planningProblem.getProblemName(), false);
		files.write(var);
		files.close();
		
	}*/

	/**
	 * 09/11/2010 15:53:37
	 * generating a plan (plan library)
	 * @param dom_pro[] 	domain and problem
	 * @param planToBeUsed 	planner to be used
	 * @param n_sol			numero de soluciones
	 * @param last_sol 		get or not the last solution
	 * @return a list of plan
	 */
	private ArrayList<DSPlan> searchPlanToAction(String[] dom_pro, int planToBeUsed, int n_sol, PLANNER last_sol) {

		ParserPddl parserPddl 	= new ParserPddl();
		ParserPddl.domain 		= dom_pro[0];
		ParserPddl.problem 		= dom_pro[1];
		parserPddl.instantiate();
		
		ArrayList<DSPlan> plan;
		AnyPlanner original_plan = new AnyPlanner(dom_pro[0], dom_pro[1], n_sol, planner_max_cpu_time, "prueba", planToBeUsed, path_plans, dom_pro[2]);
		do{
			plan = original_plan.searchPlanToAction(last_sol, parserPddl.getGroundedTask().getDomainName(), parserPddl.getGroundedTask().getProblemName()); 
		}while(plan.size()<1);
		
		return plan;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}