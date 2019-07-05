package pelea.marp.service.rp.reactive.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import pelea.marp.common.rp.reactive.trie.Trie;
import pelea.marp.service.rp.reactive.trie.tools.Tools;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;

public class TrieVariable extends Tools implements Trie {

	private PlanningProblem 				planningProblem;
	private Hashtable<DSVariables, Integer> alphabetIndex;
	private ArrayList<DSVariables> 			alphabet;
	
	// make child nodes
	TrieVariable[]				c;
	
	// flag for end of a state
	Integer[]					stateIndex = null;
	DSCondition 				var;
	String[]					values;
	List<List<Integer>>			valuesTran;
	HashMap<String, Integer> 	valuesTranIndex;
	
	int 						printIndex;
	
	public TrieVariable(PlanningProblem planningProblem, ArrayList<DSVariables> a, Hashtable<DSVariables, Integer> ai, DSCondition val) {
		super();
		this.planningProblem 	= planningProblem;
		alphabet 				= a;
		alphabetIndex 			= ai;
		
		// initializing the child nodes
		c = new TrieVariable[alphabet.size()];
		for(int i=0;i<c.length;++i) c[i]=null;
		
		if (val!=null){
			initVar(val);
		}
	}

	private void initVar(DSCondition val) {
		var 				= val;
		values 				= new String[var.getFunction().getInitialFalseValues().size()+2];
		valuesTran 			= new ArrayList<List<Integer>>(values.length);
		valuesTranIndex 	= new HashMap<String, Integer>(values.length);
		stateIndex			= new Integer[values.length];
		
		// Initializing the values and index of the variable
		values[0]="?";
		values[1]=var.getFunction().getInitialTrueValue(); 
		valuesTran.add(0, null);
		valuesTran.add(0, null);
		stateIndex[0]=null;
		stateIndex[1]=null;
		valuesTranIndex.put(values[1], 1);
		int i = 2;
		for(String s: var.getFunction().getInitialFalseValues()){
			values[i]	 =s;
			valuesTran.add(i, null);
			stateIndex[i]=null;
			valuesTranIndex.put(values[i], i);
			++i;
		}
	}

	public void insert(int indexState, List<Variable> goal_state) {
		int valueIndex=0;
		int varIndex=0;
		int i;
		Variable nextVar, currentVar;
		
		// getting the index of the current variable
		varIndex = alphabetIndex.get(this.var.getFunction());
		
		// searching if the variable is in the goal-state
		currentVar = new Variable(varIndex, this.var);
		i = goal_state.indexOf(currentVar);
		if(i==-1){ // the variable is not in the goal_state - goto the value unknown
			i = 0;
		}
		
		// removing the current variable
		currentVar 	= goal_state.get(i);
		goal_state.remove(i);
		
		// selecting the next variable to transit with the current value
		valueIndex 	= valuesTranIndex.get(currentVar.var.getValue());
		List<Integer> next_tran =selectingTransition(valueIndex, goal_state);
		
		if(goal_state.size()!=0)
			nextVar = goal_state.get(next_tran.get(0)); // we can put here an heuristic
		else
			nextVar  = currentVar;
		
		// if the node does not exist we create it
		if (c[nextVar.indexVar]==null)
			c[nextVar.indexVar] = new TrieVariable(planningProblem, alphabet, alphabetIndex, nextVar.var);

		if (valueIndex==0){// if the value is unknown
			if(valuesTran.get(valueIndex) == null){
				valuesTran.set(valueIndex, new ArrayList<Integer>(5));
				valuesTran.get(valueIndex).add(nextVar.indexVar);
			}else{
				if(!valuesTran.get(valueIndex).contains(nextVar.indexVar))
					valuesTran.get(valueIndex).add(nextVar.indexVar);
			}
			c[nextVar.indexVar].insert(indexState, goal_state);
		}else{// if the value is known
			if(goal_state.size()>0){// if there are more variables in the goal-state
				if(valuesTran.get(valueIndex) == null){
					valuesTran.set(valueIndex, new ArrayList<Integer>(5));
					valuesTran.get(valueIndex).add(nextVar.indexVar); 
				}else{
					if(!valuesTran.get(valueIndex).contains(nextVar.indexVar))
						valuesTran.get(valueIndex).add(nextVar.indexVar);
				}
				c[nextVar.indexVar].insert(indexState, goal_state);
			}else // if not this is a target node and we mark it if it is not marked
				if (stateIndex[valueIndex]==null)
					stateIndex[valueIndex]=indexState;
		}
	}
	
	private List<Integer> selectingTransition(int valueIndex, List<Variable> goal_state) {
		List<Integer> toTran=null;
		int i = 0;
		// if there is a transition for this value
		if(valuesTran.get(valueIndex) != null){
			// we select all the transition for this value that stay in the goal_state
			toTran = new ArrayList<Integer>(valuesTran.get(valueIndex).size());
			for(Integer t: valuesTran.get(valueIndex)){
				i = goal_state.indexOf(new Variable(t, this.c[t].var));
				if(i!=-1) toTran.add(i);
			}
		}else{//if not
			toTran = new ArrayList<Integer>(1);
			// we search other value transition that variables are in the goal_state
			/*for(List<Integer> tran:valuesTran){
				if(tran==null)continue;
				for(Integer t:tran){
					i = goal_state.indexOf(new Var(t, this.c[t].var));
					if(i!=-1) toTran.add(i);
				}
			}*/
		}
		
		if (toTran.size()==0)
			toTran.add(0);
		
		return toTran;
	}

	/*
	 public void insert(int indexState, Var lastVar, List<Var> list) {
		int valueIndex=0;
		int varIndex=0, i;
		Var variable;
		
		for (i=0;i<list.size();++i){
			varIndex = alphabetIndex.get(list.get(i).var.getFunction());
			if (c[varIndex] != null)
				break;
		}
		
		if(list.size()==0)
			variable = lastVar;
		else if(i==list.size())
				variable = list.get(0);
			else
				variable = list.get(i);
		varIndex = alphabetIndex.get(variable.var.getFunction());
		
		if (c[varIndex]==null)
			c[varIndex] = new TrieVariable(planningProblem, alphabet, alphabetIndex, lastVar.var);
		
		valueIndex=c[varIndex].valuesAsChildIndex.get(lastVar.var.getValue());
		if (valueIndex==0){
			if(c[varIndex].valuesAsChild[valueIndex] == false)
				c[varIndex].valuesAsChild[valueIndex] = true; // lastVar
			c[varIndex].insert(indexState,lastVar, list);
		}else{
			if(list.size()>0){
				if(c[varIndex].valuesAsChild[valueIndex] == false)
					c[varIndex].valuesAsChild[valueIndex] = true; // list.get(0)
				list.remove(variable);
				c[varIndex].insert(indexState,variable, list);
			}else
				if (c[varIndex].stateIndex[valueIndex]==null)
					c[varIndex].stateIndex[valueIndex]=indexState;
		}
	}
	 * */
	

	public int has(List<Variable> goal_state) {
		int i;
		int varIndex=0;
		Variable currentVar;
		int index;
		int indexSubSet=-1;
		
		// getting the index of the current variable
		varIndex = alphabetIndex.get(this.var.getFunction());
		
		// searching if the variable is in the goal-state
		currentVar = new Variable(varIndex, this.var);
		i = goal_state.indexOf(currentVar);
		if(i!=-1){ // the variable is in the goal_state
			currentVar 	= goal_state.get(i);
			goal_state.remove(i);
			index 		= valuesTranIndex.get(currentVar.var.getValue());
			
			if (goal_state.size()==0){
				if (stateIndex[index]!=null)
	    			indexSubSet = stateIndex[index];
			}else{
				if(valuesTran.get(index)!=null){
					for(Integer tran: valuesTran.get(index)){
						if (c[tran]!=null && goal_state.size()>0){
							indexSubSet = c[tran].has(goal_state);
							if(indexSubSet!=-1)
								break;
						}
					}
				}
			}
		}
        
		return indexSubSet;
	}
	
	public int hasSubSet(List<Variable> goal_state) {
		int i;
		int varIndex=0;
		Variable currentVar;
		int index;
		int indexSubSet=-1;
		
		// getting the index of the current variable
		varIndex = alphabetIndex.get(this.var.getFunction());
		
		// searching if the variable is in the goal-state
		currentVar = new Variable(varIndex, this.var);
		i = goal_state.indexOf(currentVar);
		if(i!=-1){ // the variable is in the goal_state
			currentVar 	= goal_state.get(i);
			goal_state.remove(i);
			index 		= valuesTranIndex.get(currentVar.var.getValue());
			
			if (stateIndex[index]!=null)
    			indexSubSet = stateIndex[index];
			
			if(indexSubSet!=-1) return indexSubSet;
			
			if(valuesTran.get(index)!=null){
				for(Integer tran: valuesTran.get(index)){
					if (c[tran]!=null && goal_state.size()>0){
						indexSubSet = c[tran].hasSubSet(goal_state);
						if(indexSubSet!=-1)
							break;
					}
				}
			}
		}
		 
		return indexSubSet;
	}

	@Override
	public String toString() {
		return "[var=" + var+""//, indexState=" + stateIndex.toString()
				//+ ", values=" + Arrays.toString(values) 
		+ ", valuesTran="+ Arrays.toString(valuesTran.toArray()) + "]";//, indexValuesAsChild="
		//	+ valuesAsChildIndex + "]";
	}

	@Override
	public void insert(int indexState, List<Integer> firstNode, List<Variable> goal_state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int hasSubSet(List<Variable> goal_state, List<Integer> firstNode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printGraphTrie(String domain, String problem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalTrieNodes() {
		int cont = 0;
		for(DSVariables t:this.alphabet)
			if(t!=null)
				++cont;
		return cont;
	}

	@Override
	public void removePath(List<Variable> goal_state) {
		int i;
		int varIndex=0;
		Variable currentVar;
		int index;
		int indexSubSet=-1;
		
		// getting the index of the current variable
		varIndex = alphabetIndex.get(this.var.getFunction());
		
		// searching if the variable is in the goal-state
		currentVar = new Variable(varIndex, this.var);
		i = goal_state.indexOf(currentVar);
		if(i!=-1){ // the variable is in the goal_state
			currentVar 	= goal_state.get(i);
			goal_state.remove(i);
			index 		= valuesTranIndex.get(currentVar.var.getValue());
			
			if (goal_state.size()==0){
				if (stateIndex[index]!=null)
	    			stateIndex[index] = null;
			}else{
				if(valuesTran.get(index)!=null){
					for(Integer tran: valuesTran.get(index)){
						if (c[tran]!=null && goal_state.size()>0){
							indexSubSet = c[tran].has(goal_state);
							if(indexSubSet!=-1)
								break;
						}
					}
				}
			}
		}
	}

}
