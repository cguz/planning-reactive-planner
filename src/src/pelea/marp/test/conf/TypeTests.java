package pelea.marp.test.conf;

import pelea.marp.service.rp.helper.Statistics;
import pelea.planning_task.common.enums.T_FAILURE;
import pelea.planning_task.common.enums.T_FILTER;
import pelea.planning_task.common.enums.T_MODULES;

public class TypeTests {

	public String dir_default="./results/";
	public String dir_name;
	public String path_eval_benchmarks;
	public T_MODULES technique;
	public int noisy_actions;
	public T_FAILURE type_failure;
	public T_FILTER type_of_filter;
	public int init_limit_time;
	public int problem;
	public int domain;
	public int total_number_executions;
	public int total_act_to_sim;
	
	/* testing to generate the search spaces */
	public String 	res_dir_evaluations;
	public int 		res_depth;
	public int 		res_planning_horizon;
	public double 	res_total_time_T;
	public double 	res_time_T;
	public double 	res_estimated_time;
	public long 	res_time_finding_best_size;
	public int 		res_no_search_tree;
	public int 		res_limit_time_tree;
	public double 	res_est_total_nodes;
	public double 	res_est_eval_nodes;
	public double 	res_est_bran_factor;
	public int 		res_t_action;
	public Statistics res_info_search_tree;
	public Object[] res_parameters;
	public String 	res_orig_plan;

	public TypeTests(TYPE_TEST type) {		
		type_of_filter	= T_FILTER.BY_TRGS;
		init_limit_time = 1000;
		path_eval_benchmarks = init_limit_time/1000+"seg";
		if (type.value >= 4){
			type_failure= T_FAILURE.BYACT_AND_TRGS;
			switch(type){
				case BYACT_TRGS_NOISY_ACTIONS_ONE:	
					dir_name		= "test_action_1";
					noisy_actions	= 1;break;
				case BYACT_TRGS_NOISY_ACTIONS_TWO:	
					dir_name 		= "test_action_2";
					noisy_actions	= 2;break;
				case BYACT_TRGS_NOISY_ACTIONS_THREE:
					dir_name 		= "test_action_3";
					noisy_actions	= 3;break;
				default:break;
			}
		}else{
			type_failure= T_FAILURE.BYVALUE;
			switch(type){
				case BYVALUE_NOISY_ACTIONS_ONE:	
					dir_name 		= "test_exo_1";
					noisy_actions	= 1;break;
				case BYVALUE_NOISY_ACTIONS_TWO:	
					dir_name 		= "test_exo_2";
					noisy_actions	= 2;break;
				case BYVALUE_NOISY_ACTIONS_THREE:	
					dir_name 		= "test_exo_3";
					noisy_actions	= 3;break;
				default: break;
			}
		}
		dir_name = dir_default+dir_name;
		path_eval_benchmarks = dir_name+"/"+path_eval_benchmarks;
	}

	public void setInitialTimeLimit(int t_lim) {
		this.init_limit_time 	= t_lim;
		path_eval_benchmarks 		= t_lim/1000+"seg";
		path_eval_benchmarks 		= dir_name+"/"+path_eval_benchmarks;

		path_eval_benchmarks		= dir_name+"/1seg";
	}

	public void setTimeLimitTree(int time2) {
		res_limit_time_tree = time2;
	}	
}