package pelea.marp.common.rp.reactive.shared.pddl;

public interface SharedFluent extends java.io.Serializable {
	
	static final int EQUAL 			= 0;
	static final int DISTINCT 		= 1;
	static final int MEMBER 		= 2;
	static final int NOT_MEMBER 	= 3;
	static final int ASSIGN     	= 4;
	static final int ADD 			= 5;
	static final int DEL 			= 6;
	static final int INCREASE   	= 7;
	static final int DECREASE   	= 8;
	static final int MORE_EQUAL 	= 9;
	static final int LESS_EQUAL 	= 10;
	static final int MORE     		= 11;
	static final int LESS     		= 12;
	static final int NOT_ASSIGN     = 13;
	static final int NOT_MORE_EQUAL = 14;
	static final int NOT_LESS_EQUAL = 15;
	static final int NOT_MORE     	= 16;
	static final int NOT_LESS     	= 17;
	
	
	/**
	 * @return variable of the fluent
	 */
	public SharedVariable getVariable();

	/**
	 * @return value that reach
	 */
	String getValue();

	/**
	 * @return type of fluents, by default equal
	 */
	int getCondition();

	
}
