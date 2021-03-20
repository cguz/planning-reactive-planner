package pelea.marp.service.rp.reactive.settings;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.MultiSettings;

/**
 * Multi settings implementation for a search tree by fluents
 * @author cguzman
 * @version 0.1
 */
public class MultiSettingsImp implements MultiSettings {
	
	// to specify if the structure is single or multi
	private SETTINGS_REACTIVESTRUCTURE 	type_structure;
	
	// to set up different reactive structures
	private SETTINGS_REACTIVESTRUCTURE 	class_to_use_search_tree;
	private SETTINGS_REACTIVESTRUCTURE 	type_execution;
	private Boolean						debug_print_seacrh_tree;
	private String 						conf_dir;
	private String 						dir_rp;
	private String 						path_to_print;
	
	private SETTINGS_REACTIVESTRUCTURE journal_relevant_variables;

	private int fluent;

	// to set the duration of one execution cycle
	private long execution_cycles;



	public MultiSettingsImp(GeneralSettings settings, int fluent) {
		type_structure = SETTINGS_REACTIVESTRUCTURE.GTS_MULTI;

		class_to_use_search_tree = settings.getClassToUseSearchTree();
		type_execution = settings.getTypeExecution();
		debug_print_seacrh_tree = settings.getDebug();
		conf_dir = settings.getConf_agent_dir();
		dir_rp = settings.getDirRP();
		path_to_print = settings.getPathToPrint();
		journal_relevant_variables = settings.getJournal();
		
		this.fluent = fluent;
	}
	

	@Override
	public SETTINGS_REACTIVESTRUCTURE getTypeStructure() {
		return this.type_structure;
		
	}

	@Override
	public Boolean getDebug() {
		return debug_print_seacrh_tree;
		
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
	public Integer getFluent() {
		return fluent;
	}

	@Override
	public String getConf_agent_dir() {
		return conf_dir;
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
	public String getDirRP() {
		return dir_rp;
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
