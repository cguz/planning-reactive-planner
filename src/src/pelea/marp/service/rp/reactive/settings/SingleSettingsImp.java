package pelea.marp.service.rp.reactive.settings;

import conf.xml2java.Conf;
import conf.xml2java.enume.From;
import pelea.marp.common.r.RSettings;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.settings.SingleSettings;

/**
 * General settings for a search tree
 * @author cguzman
 * @version 0.1
 */
public class SingleSettingsImp implements SingleSettings {

	/** single configuration **/
	private String 						agent_name;

	private Conf 						conf; // configuration file
	private RSettings 					r_settings; // settings to R access 
	private String 						conf_agent_dir;
	private String 						dir_rp;
		
	private SETTINGS_REACTIVESTRUCTURE 	repair_actions;
	private SETTINGS_REACTIVESTRUCTURE 	translating_root_approach;
	private SETTINGS_REACTIVESTRUCTURE 	translating_publicizes_approach;
	private SETTINGS_REACTIVESTRUCTURE 	translating_filtering_effects;
	private SETTINGS_REACTIVESTRUCTURE 	model_variable_to_estimate;
	private Boolean 					different_time_opt;

	private SETTINGS_REACTIVESTRUCTURE 	journal_relevant_variables;


	// to set up the central planner
	private Boolean 					cp_enable;
	private Boolean 					dp_enable;
	private Boolean 					filter_own_fluents;
	private Boolean 					filter_grounding_own_actions;



	/** single and multi configuration **/
	
	// to specify if the structure is single or multi
	private SETTINGS_REACTIVESTRUCTURE 	type_structure;
	
	// to set up different reactive structures
	private SETTINGS_REACTIVESTRUCTURE 	class_to_use_search_tree;
	private SETTINGS_REACTIVESTRUCTURE 	type_execution;
	
	// to set up different sort methods
	private SETTINGS_REACTIVESTRUCTURE 	ss_has_sort_method;
	private int[] 						ss_sort_methods; // [total_partial,time,fluents,special_case]

	// to set up the verifying of conflicts
	private SETTINGS_REACTIVESTRUCTURE 	sr_verify_conflicts;
	private SETTINGS_REACTIVESTRUCTURE 	ap_verify_conflicts;
	private SETTINGS_REACTIVESTRUCTURE 	mrt_verify_conflicts;
	private SETTINGS_REACTIVESTRUCTURE 	tw_verify_conflicts;
	
	private SETTINGS_REACTIVESTRUCTURE 	rp_activate_services;
	private SETTINGS_REACTIVESTRUCTURE 	rp_activate_single_repair;
	private SETTINGS_REACTIVESTRUCTURE 	rp_activate_multi_protocol;
	
	private Boolean						debug_print_search_tree;

	private String 						path_to_print;

	// to set the first order in the relevant actions
	private SETTINGS_REACTIVESTRUCTURE order_relevant_actions;

	// to set the duration of one execution cycle
	private long execution_cycles;






	public SingleSettingsImp(Conf configuration) {
		type_structure = SETTINGS_REACTIVESTRUCTURE.GTS_SINGLE;
		conf = configuration;

		agent_name = conf.getValue(From.C_REACTIVEPLANNER, "AGENT_NAME");
		
		debug();
		journalRelevantVariables();
		classToUseSearchTree();
		typeExecution();
		moreActions();
		translatingRootApproach();
		translatingPublicizesApproach();
		filteringRootNodeEffects();
		differentTimeOption();
		modelVariableToEstimate();
		calculatingRSettings();
		sortSolution();
		verifyConflicts();
		central_planner_enable();
		deliberative_planner_enable();
		filter_own_fluents();
		filter_grounding_own_actions();
		
		order_relevant_actions = SETTINGS_REACTIVESTRUCTURE.RP_RELEVANT_ACTIONS_OTHER_FIRST; 
	}

	private void debug() {
		debug_print_search_tree = conf.getBoolean(From.C_REACTIVEPLANNER, "RP_GTS_PRINT_SEARCH_TREE");
		if(debug_print_search_tree){
			path_to_print = conf.getValue(From.C_REACTIVEPLANNER, "RP_GTS_PATH_PRINT");
			if(path_to_print.isEmpty()){
				path_to_print = "./";
				if(conf.fileOrContent.contains("configuration.xml")){
					path_to_print = conf.fileOrContent.replaceAll("configuration.xml", "");
				}
			}else{
				path_to_print=System.getProperty("user.dir")+path_to_print;
			}
		}
	}

	private void classToUseSearchTree() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_SEARCH_TREE_TYPE");
		switch(type){
			case 0: class_to_use_search_tree= SETTINGS_REACTIVESTRUCTURE.RS_GTS_SEARCH_SPACE_BY_STATE;
		}
	}

	private void journalRelevantVariables() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "JOURNAL_RELEVANT_VARIABLES");
		switch(type){
			case 0: journal_relevant_variables= SETTINGS_REACTIVESTRUCTURE.JOURNAL_ICAE13;break;
			case 1: journal_relevant_variables= SETTINGS_REACTIVESTRUCTURE.JOURNAL_OTHERS;
		}
	}

	private void calculatingRSettings() {
		
		
		Boolean exe_model_in_r = conf.getBoolean(From.C_REACTIVEPLANNER, "RP_EXECUTE_MODEL_IN_R");
		String ip = conf.getValue(From.C_REACTIVEPLANNER, "RP_CONR_SERVER_IP");
		Integer port = conf.getInt(From.C_REACTIVEPLANNER, "RP_CONR_SERVER_PORT");
		String c_s = conf.getValue(From.C_REACTIVEPLANNER, "RP_CONR_SERVER_START");
		String m_p = conf.getValue(From.C_REACTIVEPLANNER, "RP_CONR_MODEL_PATH");
		String m_t = conf.getValue(From.C_REACTIVEPLANNER, "RP_CONR_MODEL_TYPE");
		String model = conf.getValue(From.C_REACTIVEPLANNER, "RP_CONR_MODEL");
		Boolean path = conf.getBoolean(From.C_REACTIVEPLANNER, "RP_CONR_MODEL_PATH_CONF_FILE");
		
		if(path){
			conf_agent_dir = conf.fileOrContent.replaceAll("configuration.xml", "");
		}
		
		r_settings = new RSettings(exe_model_in_r, ip, port, c_s, m_p, m_t, model, conf_agent_dir);
	}	

	private void differentTimeOption() {
		different_time_opt = conf.getBoolean(From.C_REACTIVEPLANNER, "RP_ESTIMATING_SIZE_TIME_DIFFERENCES");
	}

	private void central_planner_enable() {
		cp_enable = conf.getBoolean(From.C_EXECUTION, "CENTRAL_PLANNER");
	}
	
	private void deliberative_planner_enable() {
		dp_enable = conf.getBoolean(From.C_EXECUTION, "DELIBERATIVE_PLANNER");
	}

	private void filter_own_fluents() {
		filter_own_fluents = conf.getBoolean(From.C_EXECUTION, "FILTER_OWN_FLUENTS");
	}

	private void filter_grounding_own_actions() {
		filter_grounding_own_actions = conf.getBoolean(From.C_EXECUTION, "GROUNDING_OWN_ACTIONS");
	}
	
	private void moreActions() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_REPAIR_ACTIONS");
		switch(type){
			case 0: repair_actions = SETTINGS_REACTIVESTRUCTURE.RP_REPAIR_MORE_ACTIONS;break;
			case 1: repair_actions = SETTINGS_REACTIVESTRUCTURE.RP_REPAIR_LESS_ACTIONS;break;
		}
	}
	
	private void modelVariableToEstimate() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_MODEL_VARIABLE_TO_ESTIMATE");
		switch(type){
			case 0: model_variable_to_estimate = SETTINGS_REACTIVESTRUCTURE.RP_MODEL_ESTIMATING_B;break;
			case 1: model_variable_to_estimate = SETTINGS_REACTIVESTRUCTURE.RP_MODEL_ESTIMATING_GENERATED_NODES;break;
			case 2: model_variable_to_estimate = SETTINGS_REACTIVESTRUCTURE.RP_MODEL_ESTIMATING_EVALUATED_NODES;break;
			case 3: model_variable_to_estimate = SETTINGS_REACTIVESTRUCTURE.RP_MODEL_ESTIMATING_TIME;break;
		}
	}
	
	private void translatingRootApproach() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_TRANSLATING_ROOT_APPROACH");
		switch(type){
			case 0: translating_root_approach = SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_APPROACH;break;
			case 1: translating_root_approach = SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_FILTERING_PRE_ACTIONS_PLAN_WINDOW_APPROACH;break;
			case 2: translating_root_approach = SETTINGS_REACTIVESTRUCTURE.RP_FORWARD_APPROACH;break;
		}
	}
	
	private void translatingPublicizesApproach() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_TRANSLATING_PUBLICIZES_APPROACH");
		switch(type){
			case 0: translating_publicizes_approach=SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;break;
			case 1: translating_publicizes_approach=SETTINGS_REACTIVESTRUCTURE.RP_REGRESSED_APPROACH;break;
		}
	}
	
	private void verifyConflicts() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_SR_VERIFY_CONFLICTS");
		switch(type){
			case 0: sr_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES;	break;
			case 1: sr_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_AP_VERIFY_CONFLICTS");
		switch(type){
			case 0: ap_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES;	break;
			case 1: ap_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_MRT_VERIFY_CONFLICTS");
		switch(type){
			case 0: mrt_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES; break;
			case 1: mrt_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_TW_VERIFY_CONFLICTS");
		switch(type){
			case 0: tw_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES;	break;
			case 1: tw_verify_conflicts = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_ACTIVATE_SERVICES");
		switch(type){
			case 0: rp_activate_services = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES; break;
			case 1: rp_activate_services = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_ACTIVATE_SINGLE_REPAIR");
		switch(type){
			case 0: rp_activate_single_repair = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES; break;
			case 1: rp_activate_single_repair = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
		
		type = conf.getInt(From.C_REACTIVEPLANNER, "RP_ACTIVATE_MULTI_PROTOCOL");
		switch(type){
			case 0: rp_activate_multi_protocol = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES; break;
			case 1: rp_activate_multi_protocol = SETTINGS_REACTIVESTRUCTURE.RP_SS_NONE;
		}
	}

	private void sortSolution() {
		
		ss_sort_methods = new int[3];
		ss_sort_methods[0] = conf.getInt(From.C_REACTIVEPLANNER, "RP_SS_TOTAL_PARTIAL");
		ss_sort_methods[1] = conf.getInt(From.C_REACTIVEPLANNER, "RP_SS_EXECUTION_CYCLES");
		ss_sort_methods[2] = conf.getInt(From.C_REACTIVEPLANNER, "RP_SS_FLUENTS_TO_REACH");
		// ss_sort_methods[3] = conf.getInt(From.C_REACTIVEPLANNER, "RP_SS_SPECIAL_CASE");
		
		if(ss_sort_methods[0]!=0 || ss_sort_methods[1]!=0 || ss_sort_methods[2]!=0)// || ss_sort_methods[3]!=0)	
			ss_has_sort_method = SETTINGS_REACTIVESTRUCTURE.RP_SS_YES;
		
		/*if(ss_sort_methods[3]!=0){
			ss_sort_methods[0]=1;
			ss_sort_methods[1]=2;
			ss_sort_methods[2]=0;
		}*/
	}
	
	private void filteringRootNodeEffects() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_TRANSLATING_FILTERING_EFFECTS");
		switch(type){
			case 0: translating_filtering_effects= SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_ALL_EFFECTS;break;
			case 1: translating_filtering_effects= SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_POS_EFFECTS;
		}
	}
	
	private void typeExecution() {
		int type = conf.getInt(From.C_REACTIVEPLANNER, "RP_TYPE_EXECUTION");
		switch(type){
			case 0: type_execution = SETTINGS_REACTIVESTRUCTURE.RP_TE_CREATING_DATASET_BENCHMARK_MODE;break;
			
			case 1: type_execution = SETTINGS_REACTIVESTRUCTURE.RP_TE_VERIFYING_PLAN_SOLUTIONS_MODE;break;
			case 2: type_execution = SETTINGS_REACTIVESTRUCTURE.RP_TE_GENERATING_TREE_GRAPH1_MODE;break;
			case 3: type_execution = SETTINGS_REACTIVESTRUCTURE.RP_TE_GENERATING_TREE_GRAPH2_MODE;break;
			case 4: type_execution = SETTINGS_REACTIVESTRUCTURE.RP_TE_SIMULATING_FAILURE_RP;break;
			case 5: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_TE_APPLICATION_EXAMPLE_MODE;break;
			case 6: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_APPLICATION_EXAMPLE_MA;break;
			case 7: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_TE_NORMAL_MODE;break;
			case 8: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_TE_VERIFYING_PLANS_SEARCH_TREES_MODE;break;
			case 9: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_TE_VERIFYING_RESULTS_PARTIAL_STATES_MODE;break;
			case 10: type_execution =  SETTINGS_REACTIVESTRUCTURE.RP_TE_SAVING_SERVICES_TEST;break;
		}
	}

	@Override
	public float[] getConfWeights() {
		float[] weights =null;
		
		int total_param = conf.getInt(From.C_REACTIVEPLANNER,"RP_EST_LREQ_TOTAL_PARAMS")+4;
		weights = new float[total_param];
		weights[0] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_A");
		weights[1] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_D");
		weights[2] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_Rvar");
		weights[3] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_DRvar");
		weights[4] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_RvarRoot");
		weights[5] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_FluentsRoot");
		weights[6] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_Producers");
		weights[7] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_WEIGHT_ProducersU");
		weights[8] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_MEAN_TIME");
		weights[9] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_ALFA");
		weights[10] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_LREQ_FACTOR");
		weights[11] = conf.getFloat(From.C_REACTIVEPLANNER,"RP_MULT_ESTIMATING_TIME_ALFA");

		return weights;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getTypeStructure() {
		return this.type_structure;
		
	}

	@Override
	public Boolean getDebug() {
		return debug_print_search_tree;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getClassToUseSearchTree() {
		return class_to_use_search_tree;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getTypeExecution() {
		return type_execution;
		
	}

	@Override
	public RSettings getRSettings() {
		return r_settings;
		
	}

	@Override
	public Boolean getDifferentTimeOption() {
		return different_time_opt;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getMoreActions() {
		return repair_actions;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getModelVariableToEstimate() {
		return model_variable_to_estimate;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getTranslatingApproach() {
		return translating_root_approach;
		
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE getFilteringRootNodeEffects() {
		return translating_filtering_effects;
		
	}

	@Override
	public String getConf_agent_dir() {
		return conf_agent_dir;
	}

	@Override
	public String getAgentName() { 
		return agent_name;
	}
	
	@Override
	public SETTINGS_REACTIVESTRUCTURE getJournal() {
		return journal_relevant_variables;
	}
	
	@Override
	public String getPathToPrint() {
		return path_to_print;
	}
	
	@Override
	public SETTINGS_REACTIVESTRUCTURE hasSortMethod() {
		return ss_has_sort_method;
	}
	
	@Override
	public int[] sortMethods() {
		return ss_sort_methods;
	}
	
	@Override
	public int orderBySolution() {
		return ss_sort_methods[0];
	}

	@Override
	public int orderByTimeReachPS() {
		return ss_sort_methods[1];
	}

	@Override
	public int orderByFluentsToReach() {
		return ss_sort_methods[2];
	}

	@Override
	public int orderBySpecialCase() {
		return ss_sort_methods[3];
	}

	@Override
	public Boolean is_central_planner_enable() {
		return cp_enable;
	}
	
	@Override
	public Boolean is_deliberative_planner_enable() {
		return dp_enable;
	}

	@Override
	public Boolean get_filter_own_fluents() {
		return filter_own_fluents;
	}
	
	@Override
	public Boolean get_filter_grounding_own_actions() {
		return filter_grounding_own_actions;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE verify_conflicts_self_repair() {
		return sr_verify_conflicts;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE verify_conflicts_adapted_plan() {
		return ap_verify_conflicts;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE verify_conflicts_multi_repair() {
		return mrt_verify_conflicts;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE verify_conflicts_teamwork() {
		return tw_verify_conflicts;
	}
	
	@Override
	public SETTINGS_REACTIVESTRUCTURE getTranslating_publicizes_approach() {
		return translating_publicizes_approach;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE is_services_activate() {
		return rp_activate_services;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE single_repair_activated() {
		return rp_activate_single_repair;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE is_multi_protocol_activate() {
		return rp_activate_multi_protocol;
	}

	@Override
	public String getDirRP() {
		if(dir_rp!=null)
			return dir_rp;
		StringBuilder dir = new StringBuilder(conf_agent_dir);
		int index = conf_agent_dir.lastIndexOf("/");
		dir = dir.replace( index , dir.length(), "");
		index = dir.lastIndexOf("/");
		return dir_rp = dir.replace( index , dir.length(), "").toString();
	}

	@Override
	public void set_order_relevant_actions(SETTINGS_REACTIVESTRUCTURE order_first) {
		order_relevant_actions = order_first;
	}

	@Override
	public SETTINGS_REACTIVESTRUCTURE get_order_relevant_actions() {
		return order_relevant_actions;
	}

	@Override
	public void set_execution_cycles(long rate) {
		execution_cycles = rate;
	}

	@Override
	public long get_execution_cycles() {
		return execution_cycles;
	}
}
