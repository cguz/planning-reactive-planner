package pelea.marp.service.rp.planning_task.plan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pelea.marp.common.rp.planning_task.pddl.PartialState;
import pelea.marp.common.rp.planning_task.plan.FixingPlan;
import pelea.marp.common.rp.reactive.enums.T_SOLUTION;

public class FixingPlanImp implements FixingPlan {

	protected final List<Integer> 	target;
	protected List<Integer>			fluents_reached;

	private String 					helper;
	private List<String>			agents;
	private ArrayList<PartialState> solution;
	private T_SOLUTION				type_solution;
	private double					time;

	public FixingPlanImp(List<Integer> target_failure, String agent, ArrayList<PartialState> solution, List<String> agents){
		this.target 	= target_failure;
		this.solution 	= solution;
		this.helper  	= agent;
		this.agents		= new ArrayList<String>(agents);
		
		calculating_sort_criterias();
	}
	
	public FixingPlanImp(List<Integer> target_failure, ArrayList<PartialState> solution, List<String> agents){
		this.target 	= target_failure;
		this.solution 	= solution;
		this.helper  	= null;
		this.agents		= new ArrayList<String>(agents);

		calculating_sort_criterias();
		// time 			= solution.get(solution.size()-1).getTimeInstant();
		type_solution = T_SOLUTION.PARTIAL;
	}
	
	public FixingPlanImp(ArrayList<PartialState> solution, List<String> agents){
		this.target 	= null;
		this.solution 	= solution;
		this.helper  	= null;
		this.agents		= new ArrayList<String>(agents);

		// calculating_sort_criterias();
		time 			= get_last_solution_time();
		type_solution = T_SOLUTION.PARTIAL;
	}

	/**
	 * calculating the criterias for the sort
	 */
	private void calculating_sort_criterias() {
		PartialState ps;
		fluents_reached = new ArrayList<Integer>();
		boolean found;

		// determining if the solution is partial or total 
		if(solution.get(solution.size()-1).contains(target)){
			fluents_reached.addAll(target);
			type_solution = T_SOLUTION.TOTAL;
		}else {
			// calculating reached fluents - 3. criteria
			for(int i=solution.size()-1;i>=0;--i){
				ps = solution.get(i);
				
				for(Integer j: target){
					if(fluents_reached.contains(j))
						continue;
					if(ps.getFluents().contains(j)){
						fluents_reached.add(j);
					}
				}
			}
			type_solution = T_SOLUTION.PARTIAL;
			
		}
		
		// calculating when the solution is reached  - 2. criteria
		time = get_last_solution_time();
		if(solution.size()!=1){
			
			// looking the ps with all the fluents_reached
			int index_reached=solution.size()-1;
			for(int i=solution.size()-1;i>=0;--i){
				ps = solution.get(i);
				
				found=true;
				for(Integer j: fluents_reached){
					if(!ps.getFluents().contains(j)){
						found=false;
						break;
					}
				}
				
				if(found){
					index_reached=i;
					break;
				}
			}
			
			// looking for the last time where the fluents are reached
			for(int i=index_reached;i>=0;--i){
				ps = solution.get(i);
				
				found=true;
				for(Integer j: fluents_reached){
					if(!ps.getFluents().contains(j)){
						found=false;
						break;
					}
				}
				
				if(!found){
					time=solution.get(i+1).getTimeInstant();
					break;
				}
			}
		}
	}

	private double get_last_solution_time() {
		return solution.get(solution.size()-1).getTimeInstant();
	}

	@Override
	public String getHelper() {
		return helper;
	}
	
	@Override
	public List<String> getAgents() {
		return agents;
	}

	@Override
	public ArrayList<PartialState> getSolution() {
		
		ArrayList<PartialState> sol = new ArrayList<PartialState>((int)(time+1));
		for(PartialState ps : solution){
			sol.add(ps);
			if(ps.getTimeInstant()==time)
				break;
		}
		
		return sol;
	}

	@Override
	public T_SOLUTION getTypeSolution() {
		return type_solution;
	}

	@Override
	public double getTimeReachPS() { 
		return time;
	}

	@Override
	public int getFluents() {
		return target.size()-fluents_reached.size();
	}
	
	@Override
	public List<Integer> getFluentsReached() {
		return fluents_reached;
	}
	
	@Override
	public boolean filter_fluents_reached(){
	
		ArrayList<PartialState> sol = getSolution();
		
		boolean remove = false;
		
		for(PartialState t : sol){
			for(Integer f: fluents_reached)
				if(t.getFluents().contains(f)){
					remove = true;
					int index = t.getFluents().indexOf(f);
					t.getFluents().remove(index);
					t.getFluentsName().remove(index);
				}
		}
		
		return remove;
	}

	@Override
	public void mixed(ArrayList<PartialState> mixed) {
		for(PartialState ps : mixed){
			for(PartialState sol_ps : solution){
				if(ps.getTimeInstant()==sol_ps.getTimeInstant()){
					Set<Integer> temp = new HashSet<Integer>(ps.getFluents());
					temp.addAll(sol_ps.getFluents());
					sol_ps.getFluents().clear();
					sol_ps.getFluents().addAll(temp);
					break;
				}
				if(ps.getTimeInstant()>sol_ps.getTimeInstant()){
					solution.add(ps);
					break;
				}
			}
		}
	}	
}
