package pelea.marp.common.rp.reactive.enums;

public class Enums{
	
	
/**
 * HEURISTIC conditions effects
 */
public static enum SETTINGS_REACTIVESTRUCTURE {
	RS_UNDEFINED, 
	RS_GTS_SEARCH_SPACE_BY_STATE,
	//RS_GTS_OPT,
	RS_NONE, 
	
	TR_DURATION, 
	
	TRIE_FLUENTS, 
	TRIE_REPEATED_FLUENTS, 
	TRIE_VARIABLE, 
	
	GTS_SIMPLETREE,
	GTS_INCREMENTALTREE, 

	// TRANSLATING APPROACH
	RP_REGRESSED_APPROACH,
	RP_FORWARD_APPROACH, 
	RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH,
	
	// SELF_REPAIR ERROR
	RESULTS_ERROR_NONE, 
	RESULTS_ERROR_N_FOUND_TARGET, 
	RESULTS_ERROR_N_FOUND_ORIGIN, 
	RESULTS_ERROR_N_FOUND_TARGET_AND_ORIGIN, 
	RESULTS_FOUND, 
	RESULTS_FOUND_ORIGIN_IS_SUPERSET,
	RESULTS_ERROR_SEARCH_SPACE_EXHAUSTED,
	RESULTS_ERROR_EXCEEDS_TIME,
	RESULTS_ERROR_CONFLICTS,
	
	GTS_H_HIGHEST_OCUR_ASC,

	RP_EST_LREQ_CONF_PARAMS2, 
	RP_EST_LREQ_CONF_PARAMS1, 
	RP_EST_LREQ_CONF_PARAMS3, 

	RP_TE_VERIFYING_PLAN_SOLUTIONS_MODE, 
	RP_TE_CREATING_DATASET_BENCHMARK_MODE,
	RP_TE_GENERATING_TREE_GRAPH1_MODE,
	RP_TE_GENERATING_TREE_GRAPH2_MODE, 
	RP_TE_NORMAL_MODE, 
	RP_TE_VERIFYING_PLANS_SEARCH_TREES_MODE,
	RP_TE_SIMULATING_FAILURE_RP, 
	RP_TE_APPLICATION_EXAMPLE_MODE, 
	RP_APPLICATION_EXAMPLE_MA, 
	RP_TE_SAVING_SERVICES_TEST,
	
	RP_REPAIR_MORE_ACTIONS, 
	RP_REPAIR_LESS_ACTIONS, 
	
	RP_MULT_ESTIMATING_TIME_ALFA_OPTION, 

	RP_TREE_RN_POS_EFFECTS,
	RP_TREE_RN_ALL_EFFECTS, 
	RP_TE_VERIFYING_RESULTS_PARTIAL_STATES_MODE, 
	
	RP_MODEL_ESTIMATING_GENERATED_NODES, 
	RP_MODEL_ESTIMATING_B, 
	RP_MODEL_ESTIMATING_EVALUATED_NODES, 
	RP_MODEL_ESTIMATING_TIME, 
	
	GTS_MULTI, GTS_SINGLE, 
	
	
	/**
	 * key to specify how to calculate the relavant variables, it depends of the journal
	 * **/
	JOURNAL_ICAE13, 
	JOURNAL_OTHERS, 
	
	RP_TE_REPLANNING_VS_REPAIR, 
	
	/**
	 * key to specify if we use the sort method
	 * **/
	RP_SS_YES, 
	RP_SS_NONE, 
	
	/**
	 * key to specify the sort method for the relevant actions
	 * **/
	RP_RELEVANT_ACTIONS_OWN_FIRST, 
	RP_RELEVANT_ACTIONS_OTHER_FIRST
	;
    
	private static final String sname[] = {"undefined"//,"GTS-var-relevants"
		, "GTS-planInteraction","none","","trie-fluents","trie-repeated-fluents","trie-variable","GTS-simple-tree"
		,"GTS-incremental-tree", "regressed","forward","regressed-filtering-pre-window","none", 
		"not found target", 
		"not found origin", 
		"not found target and origin", 
		"found plan", 
		"found plan and the origin is a superset",
		"search space exhausted",
		"exceeds the time limit",
		"there are conflicts in the root nodes."}; 
	
	/**
	 * Gets a description of the syntactic symbol
	 * @param  sym	Symbol
	 * @return Symbol description
	 */
    public static String getName(SETTINGS_REACTIVESTRUCTURE sym) {
        return sname[sym.ordinal()];
    }
}



/**
 * ESTIMATED SETTING STATUS
 */
public static enum ESTIMATED_TIME_STATUS {
	ESTIMATED,
	NOT_ESTIMATED;
    
	private static final String sname[] = {"estimated","not-estimated"}; 
	
	/**
	 * Gets a description of the syntactic symbol
	 * @param  sym	Symbol
	 * @return Symbol description
	 */
    public static String getName(ESTIMATED_TIME_STATUS sym) {
        return sname[sym.ordinal()];
    }
}

	
}
