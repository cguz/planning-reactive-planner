package pelea.marp.service.rp.helper;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;

public class Statistics {

	// generating search trees statistic
	public int generating_total_nodes=0;
	public int generating_relevant_vars=0;
	public int generating_relevant_vars_domain;
	public int generating_leaf_nodes=0;
	public int generating_total_trie_nodes=0;
	public int generating_evaluated_nodes=0;
	public long generating_total_time=0;
	public int 	generating_depth_reached=0;
	
	
	// self-repair statistics
	public SETTINGS_REACTIVESTRUCTURE 	repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_NONE;
	public String 						repair_error_root;
	public SETTINGS_REACTIVESTRUCTURE 	repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_NONE;
	public long 						repair_total_time = 0; // nano time
	public int 							repair_plan_total = 0;
	public String 						repair_plan_text  = "";
	public SETTINGS_REACTIVESTRUCTURE   repair_exceeds_limit_time = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_EXCEEDS_TIME;

	
	public Statistics() {
		super();
	}
	
	public Statistics(int total_nodes, int leaf_nodes, int total_trieNodes, int rvar, int evaluatedNodes) {
		generating_total_nodes = total_nodes;
		generating_leaf_nodes = leaf_nodes;
		generating_total_trie_nodes = total_trieNodes;
		generating_relevant_vars = rvar;
		generating_evaluated_nodes = evaluatedNodes;
	}
	
}
