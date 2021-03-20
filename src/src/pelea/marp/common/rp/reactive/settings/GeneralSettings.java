package pelea.marp.common.rp.reactive.settings;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;

/**
 * General settings for a search tree
 * @author cguzman
 * @version 0.1
 */
public interface GeneralSettings {  
	
	public SETTINGS_REACTIVESTRUCTURE getTypeStructure();
	
	public Boolean getDebug();

	public SETTINGS_REACTIVESTRUCTURE getClassToUseSearchTree();
	
	public SETTINGS_REACTIVESTRUCTURE getTypeExecution();

	public String getConf_agent_dir();
	
	public SETTINGS_REACTIVESTRUCTURE getJournal();

	String getPathToPrint();

	public String getDirRP();
	
	public void set_execution_cycles(long rate);
	
	public long get_execution_cycles();
	
}
