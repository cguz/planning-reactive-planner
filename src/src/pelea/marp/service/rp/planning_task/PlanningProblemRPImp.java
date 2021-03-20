package pelea.marp.service.rp.planning_task;


import gnu.trove.set.hash.THashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.agreement_technologies.common.map_grounding.GroundedTask;
import org.agreement_technologies.common.map_grounding.GroundedVar;
import org.agreement_technologies.common.map_grounding.comparator.GroundTaskComparator;
import org.agreement_technologies.ground.ParserPddl;

import pelea.marp.common.rp.planning_task.PlanningProblemRP;
import pelea.planning_task.common.enums.T_SORT;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;
import pelea.planning_task.service.PlanningProblemImp;
import pelea.planning_task.service.pddl.NVariables;

public class PlanningProblemRPImp extends PlanningProblemImp implements PlanningProblemRP{

	private THashSet<String>[] hash_fluents_services;
	
	private int variables_helpful_size;
	private Hashtable<Integer, Hashtable<String, Integer>> variables_helpful;
	
	
	/**
	 * 07/02/2012 09:19:09	
	 * Constructor of the planning problem implementation
	 * @param parserPddl parsed and instantiated PDDL
	 */
	public PlanningProblemRPImp(ParserPddl parserPddl) {
		super(parserPddl);
	}
	
	protected void finish_initializing() {
	
		sortVarBurbuja(this.initialState, T_SORT.MENOR_MAYOR);
    	sortVarBurbuja(this.globalGoalState, T_SORT.MENOR_MAYOR);
    
	}
    
	/**
	 * procesamos y creamos las variables
	 * 29/1/2015 10:55:14
	 * @param groundedTask 
	 */
	protected void creatingVariables(GroundedTask groundedTask) {
		
		int index;
		NVariables var;

		variables_helpful = new Hashtable<Integer, Hashtable<String, Integer>>(groundedTask.getVars().length);
        variables_helpful_size = 0;
        
        // we sort the variables in a descendant way
        GroundedVar[] vars = groundedTask.getVars();
        Arrays.sort(vars,  new GroundTaskComparator().GroundedVarComparatorDes);
        
        // saving the variables in our structure
        for(int i = 0; i < vars.length; ++i) {
        	var =  new NVariables(vars[i]);
            variables.add(var);
            index = variables.indexOf(var);
            variablesIndex.put(var, index);
            
            helpful_variables(index);
        }
	}

	private void helpful_variables(int index) {
        
		Hashtable<String, Integer> values = getHelpful(variables.get(index));
        
		// adding the value of the variable to an helpful structure
		variables_helpful.put(index, values);
		variables_helpful_size += values.size();
		
	}
	
	/**
	 * 02/03/2012 09:40:46
	 * to generate the producers of a precondition
	 * @param groundedTask
	 */
	@SuppressWarnings("unchecked")
	protected void producersPreprocess(GroundedTask groundedTask) {
		hash_fluents_services = new THashSet[variables.size()];

		producers(groundedTask);
		
		if(getDomainName().toLowerCase().contains("cprint"))
			activatingAllService();
		
	}
	
	private void producers(GroundedTask groundedTask) {
		ArrayList<Integer> producers;
		DSAction ac;
		THashSet<String> values;
		int index;
		
		for(DSVariables p: this.variables) {
			values = new THashSet<String>();
            producers = new ArrayList<Integer>();
            
            for(int i=0; i < this.actions.size();++i) {
            	ac = actions.get(i);
                for(DSCondition ef: ac.getEffects()) {
                    if(ef.getFunction().hashCode() == p.hashCode()){
                        producers.add(i);
                        values.add(ef.getValue());
                    }
                }
            }
            p.setProducers(producers.size());
            
            index = variablesIndex.get(p);
            hashProducersVar.put(index, producers);
            if(values.size()==0) values=null;
            hash_fluents_services[index]=values;
        }
	}

	private void activatingAllService() {		
		for(int j=0; j < initialState.size(); ++j)
			if(initialState.get(j).getFunction().getName().toLowerCase().equals("canturn")||
					initialState.get(j).getFunction().getName().toLowerCase().equals("is"))
				changeValue(j, "on");
			
	}

	
	protected void addProducersVar(int index_act, DSCondition cond) {

		ArrayList<Integer> producers;
		int index = variablesIndex.get(cond.getFunction());
		
		DSVariables p = variables.get(index);
		
		if(hashProducersVar.containsKey(index)){
			producers = hashProducersVar.get(index);
		}else{
	        producers = new ArrayList<Integer>();
		}
		if(!producers.contains((int)index_act)){
			producers.add(index_act);
	        p.setProducers(producers.size()); 
	        hashProducersVar.put(index, producers);
		}
        
		// adding the value of the variable to an helpful structure
		if(variables_helpful.containsKey(index)){
	        Hashtable<String, Integer> values = getHelpful(p);
			variables_helpful.put(index, values);
			variables_helpful_size = variables_helpful_size + values.size();
		}
	}

	protected void removeProducersVar(Integer index_action, DSCondition cond) {
		ArrayList<Integer> producers;
		int index = (int) variablesIndex.get(cond.getFunction());
		DSVariables p = variables.get(index);
		
		if(hashProducersVar.containsKey(index)){
			producers = hashProducersVar.get(index);
			producers.remove((Integer)index_action);
			p.setProducers(producers.size());
	        hashProducersVar.put(index, producers);
		}
		
		// removing the value of the variable in helpful structure
		if(variables_helpful.containsKey(index)){
			Hashtable<String, Integer> values = variables_helpful.get(index);
			variables_helpful_size = variables_helpful_size - values.size();
			variables_helpful.remove(index);
		}
	}	
	
	@Override
	public THashSet<String>[] getServices() {
		return hash_fluents_services;
	}
	
	@Override
	public int getVariablesHelpfulSize(){
		return variables_helpful_size;
	}

	@Override
	public Hashtable<Integer, Hashtable<String, Integer>> getVariablesHelpful() {
		return variables_helpful;
	}
}