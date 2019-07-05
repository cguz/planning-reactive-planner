package pelea.marp.service.rp.reactive.search_spaces;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdapting;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.service.rp.reactive.trie.TrieStruct;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.service.pddl.FAction;
import pelea.planning_task.service.pddl.GAction;
import tools.files.Files;

/**
 * @author cguzman
 * implements a search space to adapting plan
 */
public class SearchSpaceAdaptingImp extends SearchSpaceAbstractImp implements SearchSpaceAdapting { 	
	

	protected ArrayList<DSCondition> 	current_state;

	protected TreeSet<Integer> 			plan_other_agent;
	
	// saving the plan adapted with ficticios actions of other agents to use during the getPSToPublize()
	public ArrayList<Integer> 			temp_plan_adapt;

	// vector to reschedule the plan of other agents
	protected List<Integer> 			temp_rescheduled_plan=null;
	
	
	/**
	 * multi-repairing process
	 * @param depth maximum depth
	 * @param planning_problem
	 * @param settings
	 * @param path_eval_benchmarks 
	 * @param root_node
	 */
	public SearchSpaceAdaptingImp(NodeState root, int depth, PlanningProblem planning_problem, GeneralSettings settings, String path_eval_benchmarks) {
		super(planning_problem,settings,path_eval_benchmarks);
		
		DEPTH_MAX = depth;
		root_node = root;

		MAX_TOTAL_PLANS = 5;
		
		total_results.repair_error_root = root_node.toPddl();
	}

	public SearchSpaceAdaptingImp(SETTINGS_REACTIVESTRUCTURE error, PlanningProblem planning_problem, GeneralSettings settings, String path_eval_benchmarks) {
		super(planning_problem,settings,path_eval_benchmarks);

		total_results.repair_error = error;
		total_results.repair_error_root = "";
		
	}

	@Override
	public ArrayList<Integer> plan_adaptation(ArrayList<DSCondition> S, ArrayList<Integer> actions) {

		plan_other_agent = new TreeSet<Integer>();
		plan_other_agent.addAll(actions); 
		
		initial_time = System.nanoTime();

		current_state = S;
		plan = null;

		found_plans = new ArrayList<ArrayList<Integer>>();
		
		calculating(DEPTH_MAX);
        
        total_results.repair_total_time = System.nanoTime()-initial_time;
        
		if(found_plans.size()>0)
			plan = select_best_plan(found_plans);
        
        if(plan!=null){
        	total_results.repair_found_plan = SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND;
        	total_results.repair_plan_text  = parse_plans();
        	total_results.repair_plan_total = found_plans.size();
        }
        
		return plan;
	}
	
	protected void saving_root_node() {
		root_node.setIndex(t_states);
		s_states.put(root_node, root_node);
		s_statesIndex.put(root_node.getIndex(), root_node);

		if (root_var==null){
			root_var = new TrieStruct(planning_problem, settings);
			root_var.insert(root_node);
		}
	}
	
	protected boolean exists_failure_as_static(NodeState state) {
		
		ArrayList<Integer> producers;
		for (DSCondition var: state.getRPS().getOwnFluents()){
			if(!current_state.contains(var)){ // if it is a failed fluent
				producers = planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var.getFunction()));
				if(producers == null || producers.size()==0)
					return true;
			}
		}
		
		return false;
	}

	protected boolean check_stop_condition(NodeState new_node) {

		if(!new_node.getRPS().containsFictitious()){

			if(new_node.isContainIn(current_state)){

	       	 	NodeState parent=null;
	       	 	ArrayList<Integer> temp_plan = new ArrayList<Integer>(); // a plan
				
	            // finding the plan from the origin to the destiny
		        while(!new_node.equals(root_node)){
					//We get the parent
		        	for (NodeState out: new_node.getOutComming().keySet()){
		        		for(int a: new_node.getOutComming().get(out)){
		        			parent = s_states.get(out);
		        			temp_plan.add(a);
		        		}
		        	}
		        	new_node = parent;
		        }
		        
		        // is the new plan valid ?
		        if(isValid(plan_other_agent,temp_plan)){
		        	
		        	switch(((SingleSettings)settings).get_order_relevant_actions()){
						case RP_RELEVANT_ACTIONS_OWN_FIRST:
				        	// we save several plans
				        	if(found_plans.size()<MAX_TOTAL_PLANS)
				        		// if(found_plans.size() == 0 || is_best_plan(found_plans, temp_plan))
				        			found_plans.add(temp_plan);
				        	
				        	if(found_plans.size()==MAX_TOTAL_PLANS)
				        		return true;
							break;
						default:
			        		// if(found_plans.size() == 0 || is_best_plan(found_plans, temp_plan))
			        			found_plans.add(temp_plan);
			        		return true;
					}
		        }
			}
		}
		
		boolean exceed = exceeds_time();
		if(exceed)
        	total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_EXCEEDS_TIME;
		return exceed;
	}

	public List<Integer> get_plan_to_root() { 
		return plan;
	}
	
	
	@Override
	public ArrayList<Integer> parsing_plan(ArrayList<Integer> plan_other_agent, ArrayList<Integer> adapted_plan) {

		initial_time = System.nanoTime();
		
		temp_plan_adapt = new ArrayList<Integer>(adapted_plan);
		
		plan = new ArrayList<Integer>(adapted_plan.size());
		
		// for all actions of other agent
		for(int i=0;i<plan_other_agent.size();++i)
			plan.addAll(getting_original_index_actions(adapted_plan.get(i)));
		
		// for the remaining actions to the root
		for(int i=plan_other_agent.size();i<adapted_plan.size();++i)
			plan.addAll(getting_original_index_actions(adapted_plan.get(i)));

		System.out.println("["+planning_problem.getAgentName()+"][PARSING-PLAN] other agent plan: "+plan_other_agent.toString()+", adapted plan: "+adapted_plan.toString()+
		", new plan: "+plan.toString());
		
		// if it is a dummy action
		if(total_results.repair_plan_total==0 && total_results.repair_found_plan != SETTINGS_REACTIVESTRUCTURE.RESULTS_FOUND){
			total_results.repair_plan_total = 1;
        	total_results.repair_plan_text  = parse_plan(plan);
            total_results.repair_total_time = System.nanoTime()-initial_time;
		}
		
		return (ArrayList<Integer>) plan;
	}
	

	private String parse_plan(List<Integer> plan) {
		String temp = "<";
		for(Integer p: plan){
			// if it is a dummy action 
			if(p == -1)
				temp+="dummyaction, ";
			else // otherwise, it is a normal action
				temp+=planning_problem.getActions().get(p).toString()+", ";
		}
		int index = temp.lastIndexOf(",");
		if(index != -1)
			temp = temp.substring(0, temp.lastIndexOf(","));
		temp+= ">";
		return temp;
	}
	
	@Override
	public void reschedule(ArrayList<Integer> plan_other_agent, ArrayList<Integer> adapted_plan) {

		temp_rescheduled_plan = new ArrayList<Integer>(adapted_plan.size());
		
		// for all actions of other agent
		for(int i=0;i<plan_other_agent.size();++i)
			temp_rescheduled_plan.addAll(rescheduling_plan(adapted_plan.get(i)));
		
		// for the remaining actions to the root
		for(int i=plan_other_agent.size();i<adapted_plan.size();++i)
			temp_rescheduled_plan.addAll(rescheduling_plan(adapted_plan.get(i)));

		System.out.println("["+planning_problem.getAgentName()+"][PARSING-PLAN] reschedule: "+temp_rescheduled_plan);
		
	}
	
	protected List<Integer> rescheduling_plan(int index) {
		return new ArrayList<Integer>(0);
	}

	private List<Integer> getting_original_index_actions(int index) {
		
		List<Integer> plan = new ArrayList<Integer>(1);
		DSAction act;
		int dur;
		
		// if it is a fictitious action
		if(index == -1){
			plan.add(-1);
		}else{
			act = planning_problem.getActions().get(index);
			
			// if it is a grouped action
			if(act instanceof GAction){
				// adding own regular action from grouped action
				plan.add(((GAction)act).getIndex()[1]);
			}else{
				if(act instanceof FAction){
					// change other agent action for dummy action
					dur = (int)act.getDuration().toDouble();
					for(int j=0;j<dur;++j)				
						plan.add(-1);					
				}else 
					plan.add(planning_problem.getActionsIndex().get(act.toString()));	
			}
		}
		return plan;
	}

	/* (non-Javadoc)
	 * @see pelea.marp.service.rp.reactive.search_spaces.SearchSpaceAbstractImp#getPSToPublicize(int, boolean)
	 * we use temp_plan_adapt because the original plan contains dummy actions as -1 to represent ficticious actions
	 * the parameter exe is not used because we are not executing actions.
	 */
	@Override
	public List<SharedPartialState> get_ps_to_publicize(int exe, boolean regressed) {

		if(regressed)
			return regress_plan(new ArrayList<Integer>(temp_plan_adapt.subList(exe, temp_plan_adapt.size())));
		
		return forward_plan(new ArrayList<Integer>(temp_plan_adapt.subList(exe, temp_plan_adapt.size())));
		
	}

	protected List<Integer> getRelevantActionsTo(NodeState g) {

		ArrayList<Integer> 	l_own = new ArrayList<Integer>();
		ArrayList<Integer> 	l_other = new ArrayList<Integer>();
		ArrayList<Integer> 	l_grouped = new ArrayList<Integer>();
		DSAction 		act = null;
		DSAction 		other_act;
		DSAction 		own_act;
		boolean 		supports;
		
		// getting the total of relevant actions for each variable in the partial state g
		SortedSet<Integer> t = new TreeSet<Integer>();
		for (DSCondition var:g.getGoalState()){
			t.addAll(planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var.getFunction())));
		}
		
		// filtering the actions that support the partial state g
		while(!t.isEmpty()){
			int i = t.first();
			t.remove(i);
			act = planning_problem.getActions().get(i);

			// if it is a grouped action, we verify the both actions in parallel
			if(act instanceof GAction){
				other_act = planning_problem.getActions().get(((GAction) act).getIndex()[0]);
				own_act = planning_problem.getActions().get(((GAction) act).getIndex()[1]);
				supports = (g.supports_regression(own_act.getEffects(), own_act.getConditions()) && g.supports_regression(other_act.getEffects(), other_act.getConditions()));
			}else
				supports = (g.supports_regression(act.getEffects(), act.getConditions()));
			
			// if the action support g, we save it
			if(supports){
				// if it is an action for the other agent, we save it separately
				if(plan_other_agent.contains(i))
					l_other.add(i);
				else // otherwise, it is my action or a grouped action
					if(act instanceof GAction)
						l_grouped.add(i);
					else
						l_own.add(i);
			}
		}
		
		// verificar mutex entre las acciones del otro agente y las mias
		DSAction a_other;
		DSAction a_own;
		for(int i: l_other){

			a_other = planning_problem.getActions().get(i);
			
			for(int j: l_own){
				
				a_own = planning_problem.getActions().get(j);
				
				String s_gact = a_own.toString()+"-"+a_other.toString();
				
				// if the grouped action exists, we save if index
				if(planning_problem.getActionsIndex().containsKey(s_gact)){
					l_grouped.add(planning_problem.getActionsIndex().get(s_gact));
				}else{ // otherwise, we verify mutex bettween actions
					// if there is not mutex, we created the grouped action
					if(!planning_problem.mutex(a_other,a_own)){
						int[] index = new int[]{i,j}; // index: <other_agent, own>
						
						LinkedHashSet<DSCondition> l_eff = new LinkedHashSet<DSCondition>();
						l_eff.addAll(a_own.getEffects());
						l_eff.addAll(a_other.getEffects());
	
						LinkedHashSet<DSCondition> l_cond = new LinkedHashSet<DSCondition>();
						l_cond.addAll(a_own.getConditions());
						l_cond.addAll(a_other.getConditions());
						
						l_grouped.add(planning_problem.addGroupedAction(index, s_gact, null, new ArrayList<DSCondition>(l_cond), 
						new ArrayList<DSCondition>(l_eff),String.valueOf("1")));
					}
				}
			}
		}

		// guardamos todas las acciones by an specified order
		ArrayList<Integer> l_all_actions = new ArrayList<Integer>();
		switch(((SingleSettings)settings).get_order_relevant_actions()){
			case RP_RELEVANT_ACTIONS_OWN_FIRST:
				l_all_actions.addAll(l_own);
				l_all_actions.addAll(l_grouped);
				l_all_actions.addAll(l_other);
				break;
			default:
				l_all_actions.addAll(l_other);
				l_all_actions.addAll(l_grouped);
				l_all_actions.addAll(l_own);
		}
		
		t = null;
		
		return l_all_actions;
	}
	
	protected boolean isValid(TreeSet<Integer> plan_other_agent, ArrayList<Integer> plan_adapted) {
		
		List<Integer> l = new ArrayList<Integer>(plan_other_agent);

		// for all actions of other agent
		for(int i=0;i<plan_other_agent.size();++i){
			// if it is not at the same position in the adapted plan 
			if((int)l.get(i)!=(int)plan_adapted.get(i)){
				DSAction act = planning_problem.getActions().get(plan_adapted.get(i));
				// if it is a grouped action
				if(act instanceof GAction){
					// verifying if the other agent action is included in the grouped action
					if(((GAction)act).getIndex()[0]!=l.get(i))
						return false;
				}else
					return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void printGraph() {	
		String filename = Double.toString(System.currentTimeMillis());
		
		filename = "searchSpaceAdaptation-"+this.planning_problem.getDomainName()+"-"+this.planning_problem.getProblemName()
			+"-"+SETTINGS_REACTIVESTRUCTURE.getName(((SingleSettings)settings).getTranslatingApproach())+"-d"+DEPTH_MAX
			+"-"+filename.substring(filename.length()-5, filename.length());
		
		String path = settings.getPathToPrint()+"search-space/";

		File f = new File(path);
		
		if(!f.exists())
			f.mkdirs();
		
		Files files = new Files(path+filename, false);
				
		files.write("digraph workingcomputer {\n");	
		files.write("	rankdir=BT;\n");
		files.write("	size=\"8,5\"\n");   
		files.write("	node [shape=doublecircle];s0;\n");
		files.write("	node [shape=circle]\n");
		
		int size = t_states;
		for(int i=0;i<=size;++i){
			NodeState s = s_statesIndex.get(i); 
			if(s != null){
			String subSet ="";
			if (s.isSuperSet()){
				int index = s.getSubSet().getIndex();
				if (index!=-1)
					subSet = "-G"+index;
			}
			
			if (this.equals(s))
				files.write("s"+s.getIndex()+"[	 			color=\"red\" label=\""+s.toPddl().replaceAll(", ", "\\\\n").replaceAll("\\[", "").replaceAll("\\]", "")+"\\nG"+s.getIndex()+subSet+"\"]\n");
			else
				files.write("s"+s.getIndex()+"[	 			label=\""+s.toPddl().replaceAll(", ", "\\\\n").replaceAll("\\[", "").replaceAll("\\]", "")+"\\nG"+s.getIndex()+subSet+"\"]\n");
			}
		}
		
		size = t_states;
		for(int i=0;i<=size;++i){
			NodeState state = s_statesIndex.get(i);
			if(state != null){
				//System.out.println(state.getIndex());
				
				for (NodeState state1: state.getOutComming().keySet()){
					
					if (state1.getOutComming().containsKey(state)){
						if (state1.getIndex()>state.getIndex())
							files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+this.planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\" dir=\"both\"];\n");
					}else
						files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+this.planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\"];\n");
					
				}
			}
		}
		
		files.write("}");
		files.close();
		
		String temp = "cd "+path+" \n";
		temp = temp + "dot -Tpdf "+filename+" -O \n";
		executeScript(temp);
		temp = "cd "+path+" \n";
		//temp = temp + "rm "+filename;
		executeScript(temp);
		
	}
}
