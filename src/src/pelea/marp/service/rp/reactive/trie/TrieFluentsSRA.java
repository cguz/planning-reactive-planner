package pelea.marp.service.rp.reactive.trie;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import pelea.marp.common.rp.reactive.trie.Trie;
import pelea.marp.service.rp.reactive.trie.tools.Tools;
import tools.files.Files;

public class TrieFluentsSRA extends Tools implements Trie {
	
	// node tags
	Variable 								tag			= null;
	
	// make child nodes
	Trie[]									alpha;
	
	// flag for end of a state
	Hashtable<List<Integer>,Integer> 		stateIndex;

	// list of node to transite
	List<Integer> 							transitionList;
	
	
	/**
	 * 01/07/2013	
	 * Sin replicar nodos
	 * @param size
	 */
	public TrieFluentsSRA(int size) {
		super();
		
		// initializing the child nodes
		alpha = new TrieFluentsSRA[size];
		
		// list of node to transite
		transitionList		= new ArrayList<Integer>(10);
		
		// flag for end of a state
		stateIndex 			= new Hashtable<List<Integer>, Integer>(10); // <index of the first node after the root, stateIndex of the GTS>

	}

	public TrieFluentsSRA(Variable v, Trie[] alpha) {
		// node tags
		tag					= v;

		// make child nodes
		this.alpha 			= alpha;

		// flag for end of a state
		stateIndex 			= new Hashtable<List<Integer>, Integer>(10); // <index of the first node after the root, stateIndex of the GTS>

		// list of node to transite
		transitionList		= new ArrayList<Integer>(10);
	}

	public void insert(int indexState, List<Integer> path, List<Variable> goal_state) {
		Variable currentVar;

		if(path==null)
			path = new ArrayList<Integer>(goal_state.size());
		
		// getting the first variable
		currentVar = goal_state.remove(0);

		if (alpha[currentVar.indexAlphabet]==null)
			alpha[currentVar.indexAlphabet]=new TrieFluentsSRA(currentVar, alpha);
		
		path.add(currentVar.indexAlphabet);
		
		// adding the transition
		int index = transitionList.indexOf(currentVar.indexAlphabet);
		if(index==-1)
			transitionList.add(currentVar.indexAlphabet);

		if(goal_state.size()>0){// if this is not the last variable
			alpha[currentVar.indexAlphabet].insert(indexState, path, goal_state);
		}else
			((TrieFluentsSRA)alpha[currentVar.indexAlphabet]).addStateIndex(path, indexState);
	}
	
	private void addStateIndex(List<Integer> path, int indexState) {
		if (!stateIndex.containsKey(path)){
			stateIndex.put(path, indexState);
		}
	}

	public int has(List<Variable> goal_state) {
		Variable currentVar;
		int indexSubSet=-1;
		
		// getting the current variable in the goal-state
		currentVar = goal_state.get(goal_state.size()-1);

		List<Integer> key = new ArrayList<Integer>(goal_state.size());
		for(Variable v:goal_state)
			key.add(v.indexAlphabet);
		
		TrieFluentsSRA f = (TrieFluentsSRA)alpha[currentVar.indexAlphabet];
		if (f!=null && f.stateIndex!=null)
			if(f.stateIndex.containsKey(key))
				indexSubSet = f.stateIndex.get(key);
	
		
		return indexSubSet;
	}
	
	public void removePath(List<Variable> goal_state) {
		Variable currentVar;
		
		// getting the current variable in the goal-state
		currentVar = goal_state.get(goal_state.size()-1);

		List<Integer> key = new ArrayList<Integer>(goal_state.size());
		for(Variable v:goal_state)
			key.add(v.indexAlphabet);
		
		
		TrieFluentsSRA f = (TrieFluentsSRA)alpha[currentVar.indexAlphabet];
		if (f!=null && f.stateIndex!=null)
			if(f.stateIndex.containsKey(key))
				f.stateIndex.remove(key);	
	}
	
	public int hasSubSet(List<Variable> goal_state, List<Integer> path) {
		List<Variable> 	ListNextVar = new ArrayList<Variable>(goal_state.size());
		int indexSubSet	=-1;
		TrieFluentsSRA f;

		if(path==null)
			path = new ArrayList<Integer>(goal_state.size());
		
		// getting the variables to transit in the goal-state
		ListNextVar = getTransitions(goal_state);

		// for each transition
		for (Variable t :ListNextVar){
			// verifying if is a regressed goal state from the first goal-state
			f = (TrieFluentsSRA)alpha[t.indexAlphabet];
			if(f.isStateIndex()){
				path.add((int)t.indexAlphabet);
				//if(alpha[t.indexAlphabet].stateIndex.containsKey(firstNode)){
				if(f.stateIndex.containsKey(path)){
					indexSubSet = f.stateIndex.get(path);
					break;
				}
				//}
				path.remove(path.size()-1);
			}
			f = null;
		}
		
		if(indexSubSet!=-1)
			return indexSubSet;
		
		// for each next transition
		for (Variable t :ListNextVar){
			
			// getting the next variable
			goal_state.remove(t);
	
			path.add(t.indexAlphabet);
			
			// if this is the last variable
			if (goal_state.size()==0){
	    		indexSubSet = -1;
			}else{
				indexSubSet = alpha[t.indexAlphabet].hasSubSet(goal_state, path);
			}
			
			if(indexSubSet!=-1)
				return indexSubSet;
		
		}
		
		return indexSubSet;
	}
	
	/*public Hashtable<Integer, List<DSCondition>> isContainsIn(List<Variable> goal_state, 
			List<DSCondition> path, Hashtable<Integer, List<DSCondition>> additional_info){
		List<Variable> 	ListNextVar = new ArrayList<Variable>(goal_state.size());
		TrieFluentsSRA f;

		if(path==null){
			path = new ArrayList<DSCondition>(goal_state.size());
			additional_info = new Hashtable<Integer, List<DSCondition>>();
		}
		
		// getting the variables to transit in the goal-state
		ListNextVar = getTransitions(goal_state);

		// for each transition
		for (Variable t :ListNextVar){
			// verifying if is a regressed goal state from the first goal-state
			f = (TrieFluentsSRA)alpha[t.indexAlphabet];
			if(f.isStateIndex()){
				path.add(t.var);
				if (goal_state.size()==0){
					if(f.stateIndex.containsKey(path)){
						additional_info.put(f.stateIndex.get(path), path);
					}
				}
				path.remove(path.size()-1);
			}
			f = null;
		}
		
		// for each next transition
		for (Variable t :ListNextVar){
			
			// getting the next variable
			goal_state.remove(t);
	
			path.add(t.var);
			
			// if this is the last variable
			additional_info = alpha[t.indexAlphabet].isContainsIn(goal_state, path, additional_info);		
		}
		
		return additional_info;
	}*/

	private List<Variable> getTransitions(List<Variable> goal_state) {
		List<Variable> 	ListNextVar = new ArrayList<Variable>(goal_state.size());
		
		if(transitionList != null){
			for(Variable t: goal_state){
				int i = transitionList.indexOf((int)t.indexAlphabet);
				if(i!=-1) ListNextVar.add(t);
				if(transitionList.size()==ListNextVar.size())
					break;
			}
		}
		
		return ListNextVar;
	}
	
	@Override
	public String toString() {
		return "["+tag.indexAlphabet+":<" +tag.toString()+">, flagEnd="+ stateIndex + ", Transition="+transitionList.toString()+"]";
	}

	/**
	 * @return the isStateIndex
	 */
	public boolean isStateIndex() {
		return stateIndex.size()>0;
	}

	@Override
	public void printGraphTrie(String d, String p) {
		Files files = new Files("./results/"+d+"/trieVarNode-"+d+"-"+p, false);
		
		files.write("digraph workingcomputer {\n");	
		files.write("	rankdir=TB;\n");
		files.write("	size=\"8,5\"\n");   
		files.write("	node [shape=doublecircle];root;\n");
		files.write("	node [shape=circle]\n");
		
		TrieFluentsSRA next;
		files.write("root[label=\"\"]\n");
		for (int i = 0;i<this.alpha.length;++i){
			next = (TrieFluentsSRA)this.alpha[i];
			
			if (next!=null){
				String n = "";
				for (List<Integer> j:next.stateIndex.keySet()){
					n = n + "<"+j.toString()+","+next.stateIndex.get(j)+">\\n";
				}
				if (n.length()==0)
					files.write("s"+next.tag.indexAlphabet+"[label=\""+next.tag.toString()+"\\nT"+next.tag.indexAlphabet+"\"]\n");
				else{
					files.write("s"+next.tag.indexAlphabet+"[label=\""+next.tag.toString()+"\\n"+n+"T"+next.tag.indexAlphabet+"\"]\n");
				}
			}
		}


		for (int j :this.transitionList){
			next = (TrieFluentsSRA)this.alpha[j];
			files.write("root	->	s"+next.tag.indexAlphabet+"[label=\"\"];\n");
		}
		
		for(Trie t : this.alpha){
			next = (TrieFluentsSRA)t;
			if(t!=null)
				if(next.transitionList!=null)
					for (int j :next.transitionList)
						if(next.tag==null)
							files.write("root	->	s"+((TrieFluentsSRA)next.alpha[j]).tag.indexAlphabet+"[label=\"\"];\n");
						else
							files.write("s"+next.tag.indexAlphabet+"	->	s"+((TrieFluentsSRA)next.alpha[j]).tag.indexAlphabet+"[label=\"\"];\n");
		}
		
		files.write("}");
		files.close();
		
		String temp = "cd "+System.getProperty("user.dir")+"/results/"+d+" \n";
		temp = temp + "dot -Tpdf trieVarNode-"+d+"-"+p+" -O \n";
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
}
