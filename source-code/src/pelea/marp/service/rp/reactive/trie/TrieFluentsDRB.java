package pelea.marp.service.rp.reactive.trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pelea.marp.common.rp.reactive.trie.Trie;
import pelea.marp.service.rp.reactive.trie.tools.Tools;
import tools.files.Files;

public class TrieFluentsDRB extends Tools implements Trie {
	
	// node tags
	Variable 								tag			= null;
	Integer 								indexOfNode = null;
	
	// make child nodes
	TrieFluentsDRB[]							alpha;
	
	// flag for end of a state
	Integer	 								stateIndex;

	// list of node to transite
	List<Integer> 							transitionList;

	public TrieFluentsDRB(Integer size) {
		super();
		
		// initializing the child nodes
		alpha = new TrieFluentsDRB[size];
		for(int i=0;i<alpha.length;++i) alpha[i]=null;
		
		// list of node to transite
		transitionList		= new ArrayList<Integer>(10);
	}

	public TrieFluentsDRB(List<Variable> a) {
		super();
		
		// initializing the child nodes
		alpha = new TrieFluentsDRB[a.size()];
		for(int i=0;i<alpha.length;++i) alpha[i]=null;
		
		// list of node to transite
		transitionList		= new ArrayList<Integer>(10);
	}

	public TrieFluentsDRB(Variable v, TrieFluentsDRB[] alpha) {
		// node tags
		tag					= v;
		indexOfNode			= v.indexAlphabet;

		// make child nodes
		//this.alpha 			= alpha;
		this.alpha = new TrieFluentsDRB[alpha.length];
		for(int i=0;i<this.alpha.length;++i) this.alpha[i]=null;

		// flag for end of a state
		stateIndex 			= null; // <index of the first node after the root, stateIndex of the GTS>

		// list of node to transite
		transitionList		= new ArrayList<Integer>(10);
	}

	public void insert(int indexState, List<Integer> path, List<Variable> goal_state) {
		Variable currentVar;
		
		// getting the first variable
		currentVar = goal_state.remove(0);
		
		if (alpha[currentVar.indexAlphabet]==null)
			alpha[currentVar.indexAlphabet]=new TrieFluentsDRB(currentVar, alpha);
				
		// adding the transition
		int index = transitionList.indexOf(currentVar.indexAlphabet);
		if(index==-1)
			transitionList.add(currentVar.indexAlphabet);

		if(goal_state.size()>0){// if this is not the last variable
			alpha[currentVar.indexAlphabet].insert(indexState, path, goal_state);
		}else
			((TrieFluentsDRB)alpha[currentVar.indexAlphabet]).stateIndex = indexState;
	}
	/*
	private void addStateIndex(Integer firstNode, int indexState) {
		if(stateIndex==null)
			stateIndex = new HashMap<Integer,Integer>(5);
		
		if (!stateIndex.containsKey(firstNode)){
			stateIndex.put(firstNode, indexState);
		}
	}*/

	public int has(List<Variable> goal_state) {
		Variable nextVar;
		int indexHas=-1;
		
		// getting the next variable in the goal-state
		nextVar = goal_state.remove(0);

		// if there is a transition from the current variable (this) to the next variable (nextVar)
		int index = transitionList.indexOf(nextVar.indexAlphabet);
		if(index!=-1){
			// if this is the last variable
			if (goal_state.size()==0){
				// saving the index of the variable
    			indexHas = -1;
			}else{ 
				// we transit to the next variable
				indexHas = alpha[nextVar.indexAlphabet].has(goal_state);
			}
		}
        
		return indexHas;
	}
	
	public int hasSubSet(List<Variable> goal_state, List<Integer> firstNode) {
		List<Variable> 	ListNextVar = new ArrayList<Variable>(goal_state.size());
		int indexSubSet	=-1;

		// getting the variables to transit in the goal-state
		ListNextVar = getTransitions(goal_state);

		// for each transition
		for (Variable t :ListNextVar){
			// verifying if is a regressed goal state from the first goal-state
			if(alpha[t.indexAlphabet].isAGoalState()){
				//if(alpha[t.indexAlphabet].stateIndex.containsKey(firstNode)){
					indexSubSet = alpha[t.indexAlphabet].stateIndex;//.get(firstNode);
					break;
				//}
			}
		}
		
		if(indexSubSet!=-1)
			return indexSubSet;
		
		// for each next transition
		for (Variable t :ListNextVar){
		
			// getting the next variable
			goal_state.remove(t);
			
			// if this is the last variable
			if (goal_state.size()==0){
				// saving the index of the variable
	    		indexSubSet = -1;
			}else{
				indexSubSet = alpha[t.indexAlphabet].hasSubSet(goal_state, firstNode);
			}
			
			if(indexSubSet!=-1)
				return indexSubSet;
		
		}
		return indexSubSet;
	}

	private List<Variable> getTransitions(List<Variable> goal_state) {
		List<Variable> 	ListNextVar = new ArrayList<Variable>(goal_state.size());
		
		if(transitionList != null){
			for(Variable t: goal_state){
				int i = transitionList.indexOf(t.indexAlphabet);
				if(i!=-1) ListNextVar.add(t);
				if(transitionList.size()==ListNextVar.size())
					break;
			}
		}
		
		return ListNextVar;
	}

	@Override
	public String toString() {
		return "["+indexOfNode+":<" +tag.toString()+">, flagEnd="+ stateIndex + ", Transition="+transitionList.toString()+"]";
	}

	/**
	 * @return the isStateIndex
	 */
	public boolean isAGoalState() {
		return stateIndex != null;
	}

	@Override
	public void printGraphTrie(String domain, String problem) {
		Files files = new Files("./results/"+domain+"/trieVarNode-"+domain+"-"+problem, false);
		
		files.write("digraph workingcomputer {\n");	
		files.write("	rankdir=TB;\n");
		files.write("	size=\"8,5\"\n");   
		files.write("	node [shape=doublecircle];root;\n");
		files.write("	node [shape=circle]\n");
		
		TrieFluentsDRB next;
		files.write("root[label=\"\"]\n");
		
		Queue<TrieFluentsDRB> 	openGStates = new LinkedList<TrieFluentsDRB>();
		if(transitionList!=null)
			for (int j :this.transitionList)
				openGStates.add(this.alpha[j]);
		
		int i = 1;
		while (!openGStates.isEmpty()){
			next = openGStates.remove();
			((TrieFluentsDRB)next).indexOfNode =i;
			
			String n = "";
			//for (int j:next.stateIndex.keySet()){
			if(((TrieFluentsDRB)next).stateIndex!=null)
				n = n + "<"+((TrieFluentsDRB)next).stateIndex+">\\n";
			//}
			if (n.length()==0)
				files.write("s"+((TrieFluentsDRB)next).indexOfNode+"[label=\""+((TrieFluentsDRB)next).tag.toString()+"\\nT"+((TrieFluentsDRB)next).indexOfNode+"\"]\n");
			else{
				files.write("s"+((TrieFluentsDRB)next).indexOfNode+"[label=\""+((TrieFluentsDRB)next).tag.toString()+"\\n"+n+"T"+((TrieFluentsDRB)next).indexOfNode+"\"]\n");
			}
			
			if(((TrieFluentsDRB)next).transitionList!=null)
				for (int j :((TrieFluentsDRB)next).transitionList)
					openGStates.add(((TrieFluentsDRB)next).alpha[j]);
			++i;
		}

		openGStates.clear();
		for (int j :transitionList){
			openGStates.add(alpha[j]);
			files.write("root	->	s"+alpha[j].indexOfNode+"[label=\"\"];\n");
		}

		while (!openGStates.isEmpty()){
			next = openGStates.remove();
			
			if(next.transitionList!=null)
				for (int j :next.transitionList){
					files.write("s"+next.indexOfNode+"	->	s"+next.alpha[j].indexOfNode+"[label=\"\"];\n");
					openGStates.add(next.alpha[j]);
				}
		}
		
		files.write("}");
		files.close();
		
		String temp = "cd "+System.getProperty("user.dir")+"/results/"+domain+" \n";
		temp = temp + "dot -Tpdf trieVarNode-"+domain+"-"+problem+" -O \n";
		executeScript(temp);
	}

	@Override
	public int getTotalTrieNodes() {
		int cont = 0;
		for(Trie t:alpha)
			if(t!=null)
				++cont;
		return cont;
	}

	@Override
	public void removePath(List<Variable> goal_state) {
		Variable nextVar;
		int indexHas=-1;
		
		// getting the next variable in the goal-state
		nextVar = goal_state.remove(0);

		// if there is a transition from the current variable (this) to the next variable (nextVar)
		int index = transitionList.indexOf(nextVar.indexAlphabet);
		if(index!=-1){
			// if this is the last variable
			if (goal_state.size()==0){
				// saving the index of the variable
    			indexHas = -1;
			}else{ 
				// we transit to the next variable
				indexHas = alpha[nextVar.indexAlphabet].has(goal_state);
			}
		}else
			transitionList.remove(nextVar.indexAlphabet);
        
	}

}
