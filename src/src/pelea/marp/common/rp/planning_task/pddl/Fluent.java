package pelea.marp.common.rp.planning_task.pddl;

public interface Fluent {	

	/**
	 * @return the index of the fluent
	 */
	int getIndex_fluent();
	
	/**
	 * @return the index of the fluent's variable
	 */
	int getIndexVariable();

	/**
	 * @return value that reach
	 */
	String getValue();

	/**
	 * DSCondition
	 * @return type of fluents, by default equal
	 */
	int getCondition();

}
