package pelea.marp.service.rp.reactive.search_spaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import pelea.planning_task.service.plan.NPlanAction;

public class NodeStateImp implements NodeState{

	protected PlanningProblem 					planning_problem;

	protected PS 								rg_state;
	
	private NodeState 							sub_set;
	
	private int 								index 	= 0;
	private String 								_pddl 	= null;
	private int 								_pddl_hc 	= -1;
	
	
	private HashMap<NodeState, List<Integer>> 	in_label;
	private HashMap<NodeState, List<Integer>> 	out_label;
	private HashMap<Integer, NodeState> 		in_comming;
	private HashMap<Integer, NodeState> 		out_comming;
	
	public NodeStateImp(PS g_i, PlanningProblem planningProblem, boolean saveOperator) {
		this.planning_problem = planningProblem;
		if(saveOperator)
			rg_state = new PSImp((ArrayList<DSCondition>)g_i.getOwnFluents(), (ArrayList<DSCondition>)g_i.getOtherFluents(), g_i.getOperator(), g_i.getState(),g_i.getTimeInstant(), g_i.containsFictitious());
		else
			rg_state = new PSImp((ArrayList<DSCondition>)g_i.getOwnFluents(), (ArrayList<DSCondition>)g_i.getOtherFluents(), new ArrayList<DSPlanAction>(), g_i.getState(),g_i.getTimeInstant(), g_i.containsFictitious());
		_pddl 		= null;
		sub_set 	= null;
		index		= -1;
	}

	public NodeStateImp(List<Integer> fluents_own, List<Integer> fluents_other, PlanningProblem planning_problem) {

		ArrayList<DSCondition> l_own = new ArrayList<DSCondition>(fluents_own.size());
		for(Integer i: fluents_own)
			l_own.add(planning_problem.getConditions().get(i));
		
		ArrayList<DSCondition> l_other=null;
		if(fluents_other!=null){
			l_other = new ArrayList<DSCondition>(fluents_other.size());
			for(Integer i: fluents_other)
				l_other.add(planning_problem.getConditions().get(i));
		}
		
		this.planning_problem = planning_problem;
		rg_state 	= new PSImp(l_own,l_other,new ArrayList<DSPlanAction>(),-1,-1, true);
		_pddl 		= null;
		sub_set 	= null;
		index		= -1;
		
	}
	
	public NodeStateImp(ArrayList<DSCondition> fluents_own, PlanningProblem planning_problem) {
		
		this.planning_problem = planning_problem;
		rg_state 	= new PSImp(fluents_own,null,new ArrayList<DSPlanAction>(),-1,-1, true);
		_pddl 		= null;
		sub_set 	= null;
		index		= -1;
		
	}
	
	public NodeStateImp(final Integer fluent, PlanningProblem planning_problem) {
		this.planning_problem = planning_problem;
		
		ArrayList<DSCondition> f_state = new ArrayList<DSCondition>();
		f_state.add(planning_problem.getConditions().get(fluent));
		
		rg_state 	= new PSImp(f_state,null, new ArrayList<DSPlanAction>(), fluent,fluent, false);
		
		_pddl 		= null;
		sub_set 	= null;
		index		= -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void addTransitions(Integer i, NodeState n_g) {
		setOutComming(i, n_g);
		n_g.setInComming(i, this);
	}
	

	
	@Override
	public void setOutComming(Integer index, NodeState n_g) {
		if (out_comming == null) out_comming 	= new HashMap<Integer, NodeState>(10);
		if (out_label == null) 	out_label 	= new HashMap<NodeState, List<Integer>>(10);
		if(!out_comming.containsKey(n_g.getIndex())){
			out_comming.put(n_g.getIndex(), n_g);
			List<Integer> l = null;
			if (!out_label.containsKey(n_g)){
				l = new ArrayList<Integer>();
			}else
				l = out_label.get(n_g);
			l.add(index);
			out_label.put(n_g, l);
		}
	}
	
	

	@Override
 	public void setInComming(Integer index, NodeState n_g) {
		if (in_comming == null) 	in_comming 	= new HashMap<Integer, NodeState>(10);
		if (in_label == null) 	in_label 	= new HashMap<NodeState, List<Integer>>();
		if(!in_comming.containsKey(n_g.getIndex())){
			in_comming.put(n_g.getIndex(), n_g);
			List<Integer> l = null;
			if (!in_label.containsKey(n_g)){
				l = new ArrayList<Integer>();
			}else
				l = in_label.get(n_g);
			l.add(index);
			in_label.put(n_g, l);
		}
	}

	@Override
	public boolean conflicts(List<Integer> G_t) {

		ArrayList<DSCondition> l_cond = new ArrayList<DSCondition>(G_t.size());
		for(Integer i : G_t)
			l_cond.add(planning_problem.getConditions().get(i));
		
		return planning_problem.conflicts((ArrayList<DSCondition>) rg_state.getFluents(), l_cond);
		
	}
	
	@Override
	public boolean conflicts(List<Integer> G_t, List<Integer> G_n) {

		ArrayList<DSCondition> g_t_cond = new ArrayList<DSCondition>(G_t.size());
		for(Integer i : G_t)
			g_t_cond.add(planning_problem.getConditions().get(i));
		
		ArrayList<DSCondition> g_n_cond = new ArrayList<DSCondition>(G_n.size());
		for(Integer i : G_n)
			g_n_cond.add(planning_problem.getConditions().get(i));
		
		return planning_problem.conflicts((ArrayList<DSCondition>) rg_state.getFluents(), g_t_cond) 
				&& planning_problem.conflicts((ArrayList<DSCondition>) rg_state.getFluents(), g_n_cond)
				&& planning_problem.conflicts(g_t_cond, g_n_cond);
		
	}
	
	public boolean isContainIn(ArrayList<DSCondition> nodeState) {
		
		HashMap<DSVariables, DSCondition> hash = ((PSImp)this.rg_state).getGoalStateHashMap();
		
		for(DSCondition  f: nodeState){
            if(hash.containsKey(f.getFunction())){
            	if(!this.rg_state.getFluents().contains(f))
            		return false;
            }
        }
        return true;
	}
	
	public boolean isContainInDest(ArrayList<DSCondition> nodeState) {
		
		int t_contained=0;
		HashMap<DSVariables, DSCondition> hash = ((PSImp)this.rg_state).getGoalStateHashMap();
		
		for(DSCondition  f: nodeState){
            if(hash.containsKey(f.getFunction())){
            	if(!this.rg_state.getFluents().contains(f))
            		return false;
            	++t_contained;
            }
        }
		if(t_contained==nodeState.size())
			return true;
        return false;
	}

	@Override
	public boolean supports_regression(ArrayList<DSCondition> effects, ArrayList<DSCondition> cond) {
		return planning_problem.supports_regression((ArrayList<DSCondition>)rg_state.getFluents(),effects, cond);
	}

	@Override
	public PS regress(DSAction act) {		
		return (PS) planning_problem.regress(rg_state,act);
	}

	@Override
	public boolean isOutComming(NodeState n_g) {
		if (out_label == null){ 	
			out_label 	= new HashMap<NodeState, List<Integer>>(10);
			return false;
		}
		return this.out_label.containsKey(n_g);
	}

	@Override
	public boolean isInComming(NodeState n_g) {
		if (in_label == null){
			in_label 	= new HashMap<NodeState, List<Integer>>(10);
			return false;
		}
		return this.in_label.containsKey(n_g);
	}
	
	@Override
	public ArrayList<DSCondition> getGoalState() {
		return (ArrayList<DSCondition>) this.rg_state.getFluents();
	}

	@Override
	public ArrayList<DSPlanAction> getOperator() {
		
		List<DSPlanAction> p2 = new ArrayList<DSPlanAction>();
		
		ArrayList<DSPlanAction> actions = rg_state.getOperator();
		
		DSAction act;
		Integer index_act;
		
		if(actions!= null && actions.size()>0)
			p2.addAll(actions);
		else{
			index_act = getOutOperator();
			if(index_act != null){
				act = planning_problem.getActions().get(index_act);
				p2.add(new NPlanAction(act.toString(), (double)1, (double)1, index, index_act, 1, DSPlanAction.CURRENT_ONGOING));
			}
		}
		
		return (ArrayList<DSPlanAction>) p2;
	}

	@Override
	public List<Integer> getOperatorIndex() {
		
		Integer act_index;
		
		List<Integer> p2=new ArrayList<Integer>();
		ArrayList<DSPlanAction> actions = rg_state.getOperator();
		
		if(actions!= null && actions.size()>0)
			for(DSPlanAction act: actions)
				p2.add(act.getIndexGroundingAction());
		else{
			act_index = getOutOperator();
			if(act_index != null)
				p2.add(act_index);
		}
		
		return p2;
	}
	
	public void setHash(){
		((PSImp)this.rg_state).setHashMap();
	}
	
	/** to Compare */
	@Override
	public int hashCode() {
		if (_pddl_hc == -1){
			if (_pddl == null)
				_pddl = rg_state.getFluents().toString();
			_pddl_hc = this._pddl.hashCode()*1024;
		}
		return _pddl_hc;
	}

	@Override
	public String toPddl() {
		return _pddl = "own["+rg_state.getOwnFluents().toString()+"]"+((rg_state.getOtherFluents()!=null)?"other["+rg_state.getOtherFluents().toString()+"]":"");
	}

	@Override
	public boolean isEquals(ArrayList<DSCondition> goalState) {
		return (_pddl.equals(goalState.toString()))?true:false;
	}

	@Override
	public boolean equals(Object o) {

		if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof NodeState)) return false;

        NodeState v = (NodeState) o;
        if(getGoalState().size()!=v.getGoalState().size()) return false;
        if(containsState(v.getGoalState())) return true;
        else return false;
        //return this.hashCode() == v.hashCode();
	}

	private boolean containsState(ArrayList<DSCondition> nodeState) {
		
		if(this.rg_state.getFluents().size() < nodeState.size()) return false;
		for(DSCondition  f: nodeState){
            if(!this.rg_state.getFluents().contains(f)) 
            	return false;
        }
        return true;
	}
	
	@Override
	public String toString() {
		if(this.sub_set!=null)
			return "node "+this.getIndex()+":"+this.sub_set.getIndex()+" "+toPddl()+toString(getOperatorIndex());
		return "node "+this.getIndex()+" "+toPddl()+toString(getOperatorIndex());
	}
	
	private String toString(List<Integer> outOperatorList) {
		String temp = "[";
		String act;
		for(Integer i:outOperatorList){
			
			if(i == -1)	act = "dummy-action";
			else act = planning_problem.getActions().get(i).toString();
			
			temp += i+":"+act+" ";
			
		}
		temp+="]";
		return temp;
	}

	@Override
	public HashMap<NodeState, List<Integer>> getOutComming() {
		return (out_label!=null)? out_label: new HashMap<NodeState, List<Integer>>(10);
	}

	public boolean hasChilds(){
		return in_comming != null;
	}
	
	public ArrayList<NodeState> getChilds(){
		if(in_comming!=null) {
			return  new ArrayList<NodeState>(in_comming.values());
		}
		return null;
	}
	
	@Override
	public void deleteChild(NodeState n_g) {
		if(in_comming!=null){
			if(in_comming.containsKey(n_g.getIndex())){
				in_comming.remove(n_g.getIndex());
				in_label.remove(n_g);
			}
			if(in_comming.isEmpty()) {
				in_comming = null;
				in_label = null;
			}
		}
	}
	
	public NodeState getParent(){
		if(out_comming!=null) return out_comming.get(out_comming.keySet().toArray()[0]);
		else return null;
	}
	
	public void deleteParent(){
		out_comming = null;
		out_label = null;
	}
	
	public int getDepth(){
		int level = 0;
		NodeState 		auxGState 	= this;
		
		while(auxGState!=null){
			auxGState 	= auxGState.getParent();
			level++;
		}
		
		return level;
	}

	@Override
	public List<NodeState> getPartialStatesToRoot() {
		
		List<NodeState> aux_list = new ArrayList<NodeState>(getDepth()+1);
		aux_list.add(this);
		
		boolean inside = false;
		do{
			aux_list.add(aux_list.get(aux_list.size()-1).getParent());
			inside = true;
		}while(aux_list.get(aux_list.size()-1)!=null);
		
		if(inside)
			aux_list.remove(aux_list.size()-1);
		
		return aux_list;
	}
	
	@Override
	public HashMap<NodeState, List<Integer>> getInComming() {
		return (in_label!=null)?in_label: new HashMap<NodeState, List<Integer>>(10);
	}

	@Override
	public NodeState getSubSet() {
		return sub_set;
	}

	@Override
	public void setSubSet(NodeState subSet) {
		this.sub_set = subSet;
	}
	
	@Override
	public boolean isSuperSet(){
		return (sub_set!=null)?true:false;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public ArrayList<DSCondition> getExtraVars(ArrayList<DSCondition> originInfo) {
		ArrayList<DSCondition> vars = new ArrayList<DSCondition>();
        vars.addAll(this.getGoalState());
        for(DSCondition f : originInfo)
            if(vars.contains(f)) vars.remove(f);
		return vars;
	}

	@Override
	public NodeState[] getChilds(ArrayList<DSCondition> oriState, SETTINGS_REACTIVESTRUCTURE heuristic) {
		
		NodeState[] incomming = null;
		switch(heuristic){
			case GTS_H_HIGHEST_OCUR_ASC:
			default:
				incomming = determinateOrderToVisit(this.getInComming().keySet(), oriState);
				break;
		}
		
		return incomming;
	}
	
	private NodeState[] determinateOrderToVisit(Set<NodeState> keySet, ArrayList<DSCondition> oriState) {
		NodeState[] incomming 	= new NodeState[keySet.size()];
		int[] 		numOcur 	= new int[keySet.size()];
		
		int i = 0;
		for(NodeState s: keySet){
			incomming[i]  	= s;
			numOcur[i] 		= 0;
			++i;
		}
		
		if(incomming.length<2)
			return incomming;
		
		for(DSCondition  f: oriState)
			for(i=0;i<incomming.length;++i)
				if(incomming[i].getGoalState().contains(f)) 
					numOcur[i]+=1;
		
		quicksort(incomming, numOcur, 0, numOcur.length-1);
		
		return incomming;
	}
	
	private void quicksort(NodeState[] incomming, int[] numOcur, int low, int high) {
	    int i = low, j = high;
	    // Get the pivot element from the middle of the list
	    int pivot = numOcur[low + (high-low)/2];

	    // Divide into two lists
	    while (i <= j) {
	      // If the current value from the left list is smaller then the pivot
	      // element then get the next element from the left list
	      while (numOcur[i] > pivot) {
	        i++;
	      }
	      // If the current value from the right list is larger then the pivot
	      // element then get the next element from the right list
	      while (numOcur[j] < pivot) {
	        j--;
	      }

	      // If we have found a values in the left list which is larger then
	      // the pivot element and if we have found a value in the right list
	      // which is smaller then the pivot element then we exchange the
	      // values.
	      // As we are done we can increase i and j
	      if (i <= j) {
	        exchange(incomming, numOcur,i, j);
	        i++;
	        j--;
	      }
	    }
	    // Recursion
	    if (low < j)
	      quicksort(incomming, numOcur,low, j);
	    if (i < high)
	      quicksort(incomming, numOcur,i, high);
  }

  private void exchange(NodeState[] incomming, int[] numOcur, int i, int j) {
	  int temp = numOcur[i];
	    numOcur[i] = numOcur[j];
	    numOcur[j] = temp;
	    
	    NodeState node = incomming[i];
	    incomming[i] = incomming[j];
	    incomming[j] = node;
  }

	
	@Override
	public Integer getOutOperator() {
		if (this.out_label!=null)
			for (NodeState n: this.out_label.keySet())
				return this.out_label.get(n).get(0);
		return null;
	}
	
	@Override
	public PS getRPS() {
		return this.rg_state;
	}

	@Override
	public void removeFluent(DSCondition fluent, boolean pre) {
		int index = -1;
		for(int i = 0; i < this.rg_state.getFluents().size(); ++i) { 
			if(rg_state.getFluents().get(i).equals(fluent)){
				index = i;
				break;
			}
		}
		
		/* if exists as precondition of the next action do not remove */
		if(pre && index != -1){
			ArrayList<DSPlanAction> op = rg_state.getOperator();
			if(op.size()>0){
				DSAction act = this.planning_problem.getActions().get(op.get(0).getIndexGroundingAction());
				for(int i=0; i < act.getConditions().size();++i){
					if(act.getConditions().get(i).equals(fluent)){
						index = -1;
						break;
					}					
				}
			}
		}
		
		if(index!=-1){
			rg_state.getFluents().remove(index);
			((PSImp)rg_state).setHashMap();
		}		
	}

	@Override
	public double getDurationOutOperator() {

		if (getRPS().getOperator()!=null && getRPS().getOperator().size()>0)
			return getRPS().getOperator().get(0).getDuration();
		else{
			if (getOutOperator()!=null)
				return this.planning_problem.getActions().get(getOutOperator()).getDuration().toDouble();
		}
		return 0;
	}
}