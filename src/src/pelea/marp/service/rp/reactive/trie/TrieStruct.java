package pelea.marp.service.rp.reactive.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import pelea.marp.common.rp.planning_task.PlanningProblemRP;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.exceptions.BadProblemDefinitionException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.trie.Trie;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;

public class TrieStruct {
	
	private GeneralSettings 				 settings; 
	
	private PlanningProblem 				 planningProblem;
	private Hashtable<DSVariables, Integer>  variablesIndex; // variables to create the alphabet
	
	private Hashtable<Integer, Hashtable<String, Integer>> helpfulWithVariables; // an helpful structure to get the index of the values

	private List<Variable> 			 		 fluents; 		// alphabet of the TrieFluents as variables with an assignment value
	private Hashtable<Variable, Integer> 	 fluentsIndex;
	
	private Trie root;
	
	// using to avoid the duplicate conversition from a goal-state (toVarList)
	private List<Variable> 					 goal_state;
	
	// ordering by input goal-states
	private List<Variable> 					 tempList;
	
	// true = by variables in the domain, false = by input goal-states
	private boolean							 ordered=false;
	
	private SETTINGS_REACTIVESTRUCTURE 				 type_trie = SETTINGS_REACTIVESTRUCTURE.TRIE_REPEATED_FLUENTS;
	
	
	
	public TrieStruct(PlanningProblem planningProblem, GeneralSettings settings) {
		super();
		
		this.settings 			= settings;
		this.planningProblem 	= planningProblem;
		variablesIndex 			= planningProblem.getVariablesIndex();
		helpfulWithVariables	= new Hashtable<Integer,Hashtable<String, Integer>>(variablesIndex.size());
		
		// generating the alphabet
		int size=0, sizeFalseValues;		
		if(ordered){// ordering by the variables in the domain
			Variable 					var;
			Hashtable<String, Integer> 	values;
			fluents 		= new ArrayList<Variable>(10);
			fluentsIndex 	= new Hashtable<Variable, Integer>(10);
			for(DSVariables variable : planningProblem.getVariables()){
				sizeFalseValues = variable.getInitialFalseValues().size();
				size 	= size+sizeFalseValues+1;
				var 	= new Variable(fluents.size(), variablesIndex.get(variable), variable, 0);
				values 	= new Hashtable<String, Integer>(sizeFalseValues+1);
				
				// adding the variable with its first assignment value to the alphabet
				addToAlphabet(var);
				
				// adding the values of the variable
				values.put(variable.getInitialTrueValue(), 0);
				
				// adding the variable with the remaining assignment values to the alphabet
				for(int i=0;i<sizeFalseValues;++i){
					var = new Variable(fluents.size(), variablesIndex.get(variable), variable, i+1);
					addToAlphabet(var);
					values.put(variable.getInitialFalseValues().get(i), i+1);
				}
				
				// adding the value of the variable to an helpful structure
				helpfulWithVariables.put(var.indexVar, values);
			}
		}else{// ordering by input goal-states
			fluents 		= new ArrayList<Variable>(((PlanningProblemRP)planningProblem).getVariablesHelpfulSize());
			fluentsIndex 	= new Hashtable<Variable, Integer>(((PlanningProblemRP)planningProblem).getVariablesHelpfulSize());
			tempList		= new ArrayList<Variable>(3);
		}
		
		switch(type_trie){ 
			case TRIE_FLUENTS : // trie without repeated fluents
				root = new TrieFluentsDRB(size);
				break;
			case TRIE_REPEATED_FLUENTS : // trie with repeated fluents
				root = new TrieFluentsSRA(((PlanningProblemRP)planningProblem).getVariablesHelpfulSize());
				break;
			/*case TRIE_VARIABLE : // trie with repeated fluents
				root = new TrieFluentsSRA(size);
			*/	
			default: break;
		}
	}
	
	public TrieStruct(PlanningProblem planningProblem, ArrayList<Variable> fluents, 
			Hashtable<Variable, Integer> fluentsIndex, Hashtable<Integer, Hashtable<String, Integer>> helpfulWithVariables) {
		super();
		
		this.planningProblem 		= planningProblem;
		variablesIndex 				= planningProblem.getVariablesIndex();
		this.helpfulWithVariables	= helpfulWithVariables;
		
		this.fluents 				= fluents;
		this.fluentsIndex 			= fluentsIndex;
		
		// generating the alphabet
		if(!ordered){// ordering by input goal-states
			tempList= new ArrayList<Variable>(3);
		}
		
		switch(type_trie){ 
			case TRIE_FLUENTS : // trie without repeated fluents
				root = new TrieFluentsDRB(((PlanningProblemRP)planningProblem).getVariablesHelpfulSize());
				break;
			case TRIE_REPEATED_FLUENTS : // trie with repeated fluents
				root = new TrieFluentsSRA(((PlanningProblemRP)planningProblem).getVariablesHelpfulSize());
				break;
			/*case TRIE_VARIABLE : // trie with repeated fluents
				root = new TrieVariable(planningProblem.getVariablesHelpfulSize());
			*/	
			default: break;
		}
		
	}
	
	public void insert(NodeState r) {
		root.insert(r.getIndex(), null, toListVar(r.getGoalState()));
	}

	public void insert(int index, Hashtable<Integer, Variable> list) {
		root.insert(index, null, new ArrayList<Variable>(list.values()));
	}
	
	public int has() {
		List<Variable> gs = new ArrayList<Variable>(this.goal_state.size());
		gs.addAll(goal_state);
		return root.has(gs);
	}
	
	public void removePath() {
		List<Variable> gs = new ArrayList<Variable>(this.goal_state.size());
		gs.addAll(goal_state);
		root.removePath(gs);
	}
	
	public int hasSubSet() {
		List<Variable> gs = new ArrayList<Variable>(this.goal_state.size());
		gs.addAll(goal_state);
		
		int i = root.hasSubSet(gs, null);
		
		if(!ordered)// ordering by input goal-states
			if(i!=-1){
				switch(settings.getJournal()){
					case JOURNAL_ICAE13:
						for(Variable t: this.tempList){
							if(this.fluentsIndex.contains(t)){
								this.fluents.remove((int)fluentsIndex.get(t));
								this.fluentsIndex.remove(t);
							}
						}
					break;
					case JOURNAL_OTHERS:
					default:
						for(Variable t: this.tempList){
							if(this.fluentsIndex.containsKey(t)){
								int index = fluentsIndex.get(t);
		
								fluents.remove(index);
								fluentsIndex.remove(t);
								
								for(int j = index; j < fluents.size(); ++j){
									Variable temp = this.fluents.get(j);
									temp.indexAlphabet=j;
									fluentsIndex.put(temp, temp.indexAlphabet);
								}
							}
						}
						break;
				}
			}
		
		return i;
	}

	/*public Hashtable<Integer, List<DSCondition>> isContainsIn() {
		List<Variable> gs = new ArrayList<Variable>(this.goal_state.size());
		gs.addAll(goal_state);
		
		return root.isContainsIn(gs, null, null);
	}*/

	/**
	 * ReactivePlanner - 29/06/2013
	 * Generating an ordered list of Variable
	 * @param List of fluents <DSCondition>
	 * @return an ordered list of variable by the index of the alphabet
	 */
	private List<Variable> toListVar(List<DSCondition> r) {
		
		List<Variable> list = new ArrayList<Variable>(r.size());
		Variable variable;
		DSVariables v;
		Integer indexValue;
		if(!ordered)tempList.clear();
		for(DSCondition var :r){			
			// creating the variable
			if(ordered){// ordering by the variables in the domain
				v = var.getFunction();
				
				// using the helpful structure
				indexValue = helpfulWithVariables.get(variablesIndex.get(v)).get(var.getValue());
				
				// adding the variable with the same instance previously created
				list.add(fluents.get(fluentsIndex.get(new Variable(variablesIndex.get(v), v, indexValue))));
			}else{// ordering by input goal-states
				try {
					variable = addToAlphabet(var);
					// adding the variable with the same instance previously created
					list.add(variable);
				} catch (BadProblemDefinitionException e) {e.printStackTrace();}	
			}
		}
		//System.out.println(r.toString());
		//System.out.println(list.toString());
		Collections.sort(list);
		return list;
	}

	private Variable addToAlphabet(DSCondition v) throws BadProblemDefinitionException {
		
		// getting the possible index of the variable
		int indexAlphabet	= fluents.size();
		int indexValue		= -1;
		int indexVar		;
		
		int size = v.getFunction().getInitialFalseValues().size()+1;
		Hashtable<String, Integer> values;
		Variable var = null;
		
		indexVar = variablesIndex.get(v.getFunction());
		if(!helpfulWithVariables.containsKey(indexVar)){

			// creating the assignment values of the variable
			values = new Hashtable<String, Integer>(size);
			
			try {
				values.put(v.getFunction().getInitialTrueValue(), 0);
				for(int i=0;i<v.getFunction().getInitialFalseValues().size();++i){
					values.put(v.getFunction().getInitialFalseValues().get(i), i+1);
				}
				
			} catch (Exception e) {
				throw new BadProblemDefinitionException(" possible bad definition in the problem 3.1. with the fluent "+v.toPddl21());
			}
			
			// adding the variable with its values to an helpful structure
			helpfulWithVariables.put(indexVar, values);
			
			// getting the index of the value
			if(values.containsKey(v.getValue()))
				indexValue=values.get(v.getValue());
			
		}

		// adding the variable with its value to the alphabet 
		// (we use here the helpful structure to get the index value)
		values = helpfulWithVariables.get(indexVar);
		boolean found = false;
		switch(settings.getJournal()){
				case JOURNAL_ICAE13:
					if(values.containsKey(v.getValue()))
						found = true;
				break;
				case JOURNAL_OTHERS:
				default:
					if(values.containsKey(v.getValue())){
						found = true;
					}else{
						values.put("true", values.get("yes"));
						values.remove("yes");
						values.put("false", values.get("not"));
						values.remove("not");
						if(values.containsKey(v.getValue()))
							found = true;
					}
				break;
		}
				
		if(found){
			if(indexValue==-1)
				indexValue = values.get(v.getValue());
			var = new Variable(indexAlphabet, indexVar, v.getFunction(), indexValue);
			if (!fluentsIndex.containsKey(var)){
				tempList.add(var);
				addToAlphabet(var);
			}else
				var = fluents.get(fluentsIndex.get(var));
			
			//variablesValues.get(indexVar).put(v.getValue(), variablesValues.get(indexVar).size());
		}
		return var;
	}
	
	private void addToAlphabet(Variable var) {
		fluents.add(var);
		fluentsIndex.put(var,var.indexAlphabet);
	}

	@Override
	public String toString() {
		return "[root=" + root.toString() + "]";
	}

	public void printGraphTrie() {
		this.root.printGraphTrie(this.planningProblem.getDomainName(),this.planningProblem.getProblemName());
	}
	
	public void setNewGState(ArrayList<DSCondition> goalState) {
		this.goal_state = toListVar(goalState);
	}
	
	public void setNewGState(List<Variable> list) {
		Collections.sort(list);
		this.goal_state = list;
	}

	public int getTotalTrieNodes() {
		return this.root.getTotalTrieNodes();
	}

	public List<Integer> getListVar() {
		ArrayList<Integer> l_var = new ArrayList<Integer>();
		l_var.addAll(helpfulWithVariables.keySet());
		return l_var;
	}
}
