package pelea.marp.common.rp.reactive.search_spaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlanAction;

public interface NodeState {
	
	// general methods

	boolean supports_regression(ArrayList<DSCondition> effects, ArrayList<DSCondition> conditions);

	boolean isContainIn(ArrayList<DSCondition> oriState);
	
	boolean isContainInDest(ArrayList<DSCondition> oriState);

	Integer getOutOperator();

	NodeState[] getChilds(ArrayList<DSCondition> oriState, SETTINGS_REACTIVESTRUCTURE gtsHHighestOcurAsc);

	NodeState getSubSet();
	void setSubSet(NodeState nodeState);

	PS getRPS();
	
	boolean isEquals(ArrayList<DSCondition> goalState);
	String toPddl();

	void setOutComming(Integer index, NodeState n_g);
	void setInComming(Integer index, NodeState n_g);
	boolean isInComming(NodeState n_g);
	boolean isOutComming(NodeState n_g);
	
	
	
	// self-repair 
	
	PS regress(DSAction dsAction);
	// reordering
	List<NodeState> getPartialStatesToRoot();

	
	
	
	// multi-repair 
	boolean conflicts(List<Integer> G_t);

	boolean conflicts(List<Integer> G_t, List<Integer> G_n);
	
	
	
	


	
	
	
	HashMap<NodeState, List<Integer>> getOutComming();

	HashMap<NodeState, List<Integer>> getInComming();

	void addTransitions(Integer i, NodeState n_g);

	ArrayList<DSCondition> getExtraVars(ArrayList<DSCondition> originInfo);
	
	public boolean hasChilds();
	
	public ArrayList<NodeState> getChilds();
	
	public void deleteChild(NodeState nodeState);
	
	public NodeState getParent();
	
	public void deleteParent();
	
	public int getDepth();

	boolean isSuperSet();

	void setIndex(int size);

	int getIndex();

	ArrayList<DSCondition> getGoalState();

	ArrayList<DSPlanAction> getOperator();

	void removeFluent(DSCondition fluent, boolean pre);

	double getDurationOutOperator();

	List<Integer> getOperatorIndex();

}
