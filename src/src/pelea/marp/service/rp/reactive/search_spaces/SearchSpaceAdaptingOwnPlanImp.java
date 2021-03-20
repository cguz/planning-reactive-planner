package pelea.marp.service.rp.reactive.search_spaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdaptingOwnPlan;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.service.rp.reactive.trie.TrieStruct;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSCondition;
import tools.files.Files;

/**
 * @author cguzman
 * implements a search space to apply the merge
 */
public class SearchSpaceAdaptingOwnPlanImp extends SearchSpaceAbstractImp implements SearchSpaceAdaptingOwnPlan { 	

	private ArrayList<DSCondition> current_state;
	
	// saving the several plans
	public ArrayList<ArrayList<Integer>> found_plans;

	
	long initial_time;
	
	/**
	 * multi-repairing process
	 * @param depth maximum
	 * @param planningProblem
	 * @param settings
	 * @param path_eval_benchmarks 
	 * @param root_node
	 */
	public SearchSpaceAdaptingOwnPlanImp(NodeState root, int depth, PlanningProblem planning_problem, GeneralSettings settings, String path_eval_benchmarks) {
		super(planning_problem,settings,path_eval_benchmarks);
		
		DEPTH_MAX = depth;
		root_node = root;
		
		MAX_TOTAL_PLANS = 1;
		
		total_results.repair_error_root = root_node.toPddl();
	}

	public SearchSpaceAdaptingOwnPlanImp(SETTINGS_REACTIVESTRUCTURE error, PlanningProblem planning_problem, GeneralSettings settings, String path_eval_benchmarks) {
		super(planning_problem,settings,path_eval_benchmarks);
		
		total_results.repair_error = error;
		total_results.repair_error_root = "";
	}

	@Override
	public ArrayList<Integer> find_plan(ArrayList<DSCondition> S) {

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
	
	protected boolean check_stop_condition(NodeState new_node) {

		if(new_node.isContainIn(current_state)){

       	 	NodeState parent=null;
       	 	ArrayList<Integer> temp_plan = new ArrayList<Integer>(); // a plan
			
            // finding the plan from the origin to the destiny
			while(new_node != null && !new_node.equals(root_node)){
				//We get the parent
				for (NodeState out: new_node.getOutComming().keySet()){
					for(int a: new_node.getOutComming().get(out)){
						parent = s_states.get(out);
						temp_plan.add(a);
					}
				}
				new_node = parent;
			}
			
			// we save several plans
        	if(found_plans.size()<MAX_TOTAL_PLANS){
        		// if(found_plans.size() == 0 || is_best_plan(found_plans, temp_plan))
        			found_plans.add(temp_plan);
        	}
        	
        	if(found_plans.size()==MAX_TOTAL_PLANS)
        		return true;

		}

		boolean exceed = exceeds_time();
		if(exceed)
        	total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_EXCEEDS_TIME;
		return exceed;
	}

	protected boolean exceeds_time() {

		long   elapsed_time 	= System.nanoTime()-initial_time;
		double sec_elapsed_time = (double)elapsed_time / 1000000000.0;
		
		double sec_exe_cycle = settings.get_execution_cycles()/1000;
		
		return (sec_elapsed_time>=sec_exe_cycle*1.5);
		
	}
	
	public List<Integer> get_plan_to_root() { 
		return plan;
	}

	@Override
	public void printGraph() {	
		String filename = Double.toString(System.currentTimeMillis());
		
		filename = "searchSpaceMerge-"+this.planning_problem.getDomainName()+"-"+this.planning_problem.getProblemName()
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

	@Override
	public NodeState get_root() {
		return root_node;
	}
}
