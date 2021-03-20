package pelea.marp.common.rp.reactive.settings;

import pelea.marp.common.r.RSettings;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;

/**
 * Single settings for a search tree
 * @author cguzman
 * @version 0.1
 */
public interface SingleSettings extends GeneralSettings {  
	
	public RSettings getRSettings();

	public Boolean getDifferentTimeOption();

	public SETTINGS_REACTIVESTRUCTURE getMoreActions();
	
	public SETTINGS_REACTIVESTRUCTURE getModelVariableToEstimate();
	
	public SETTINGS_REACTIVESTRUCTURE getTranslatingApproach();
	
	public SETTINGS_REACTIVESTRUCTURE getFilteringRootNodeEffects();
	
	public float[] getConfWeights();
	
	public String getAgentName();

	SETTINGS_REACTIVESTRUCTURE hasSortMethod();
	
	int[] sortMethods();

	int orderBySolution();

	int orderByTimeReachPS();

	int orderByFluentsToReach();

	int orderBySpecialCase();

	public Boolean is_central_planner_enable();

	public Boolean is_deliberative_planner_enable();
	
	public Boolean get_filter_own_fluents();

	public Boolean get_filter_grounding_own_actions();

	SETTINGS_REACTIVESTRUCTURE verify_conflicts_self_repair();
	
	SETTINGS_REACTIVESTRUCTURE verify_conflicts_adapted_plan();

	SETTINGS_REACTIVESTRUCTURE verify_conflicts_multi_repair();

	SETTINGS_REACTIVESTRUCTURE verify_conflicts_teamwork();

	SETTINGS_REACTIVESTRUCTURE getTranslating_publicizes_approach();

	SETTINGS_REACTIVESTRUCTURE is_services_activate();

	SETTINGS_REACTIVESTRUCTURE single_repair_activated();

	SETTINGS_REACTIVESTRUCTURE is_multi_protocol_activate();

	public void set_order_relevant_actions(SETTINGS_REACTIVESTRUCTURE order_first);
	
	public SETTINGS_REACTIVESTRUCTURE get_order_relevant_actions();

}
