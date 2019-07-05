package pelea.marp.service.rp.reactive.search_spaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceAdaptingRelaxed;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.service.pddl.FAction;
import pelea.planning_task.service.pddl.GAction;
import tools.files.Files;

/**
 * @author cguzman
 * implements a search space to apply the adapting plan relaxed
 */
public class SearchSpaceAdaptingRelaxedImp extends SearchSpaceAdaptingImp implements SearchSpaceAdaptingRelaxed { 	
	
	/**
	 * multi-repairing process
	 * @param depth maximum depth
	 * @param planningProblem
	 * @param settings
	 * @param path_eval_benchmarks 
	 * @param root_node
	 */
	public SearchSpaceAdaptingRelaxedImp(NodeState root, int depth, PlanningProblem planningProblem, GeneralSettings settings, String path_eval_benchmarks) {
		super(root, depth, planningProblem,settings,path_eval_benchmarks);
		
		MAX_TOTAL_PLANS = 1;
	}

	public SearchSpaceAdaptingRelaxedImp(SETTINGS_REACTIVESTRUCTURE error, PlanningProblem planning_problem, GeneralSettings settings, String path_eval_benchmarks) {
		super(error, planning_problem,settings,path_eval_benchmarks);		
	}

	protected boolean isValid(TreeSet<Integer> plan_other_agent, ArrayList<Integer> plan_adapted) {
		
		List<Integer> l = new ArrayList<Integer>(plan_other_agent);

		// for all actions of other agent
		for(int i=0;i<plan_other_agent.size();++i){
			boolean found = false;
			for(Integer j : plan_adapted){
				if(l.get(i)==j){
					found = true;
					break;
				}
				else{
					DSAction act = planning_problem.getActions().get(j);
					// if it is a grouped action
					if(act instanceof GAction){
						// verifying if the other agent action is included in the grouped action
						if(((GAction)act).getIndex()[0]==l.get(i)){
							found = true;
							break;
						}
					}
				}
			}
			if(!found)
				return false;
		}
		
		return true;
	}
	
	protected List<Integer> rescheduling_plan(int index) {
		List<Integer> plan = new ArrayList<Integer>(1);
		DSAction act;
		int dur;
		
		// if it is a fictitious action
		if(index == -1){
			plan.add(1);
		}else{
			act = planning_problem.getActions().get(index);
			
			// if it is a grouped action
			if(act instanceof GAction){
				// adding own regular action from grouped action
				plan.add(1);
			}else{
				if(act instanceof FAction){
					// change other agent action for dummy action
					dur = (int)act.getDuration().toDouble();
					for(int j=0;j<dur;++j)
						plan.add(1);					
				}else	
					plan.add(-1);
			}
		}
		
		return plan;
	}

	@Override
	public List<Integer> get_rescheduling_plan() {
		return temp_rescheduled_plan;
	}
	
	@Override
	public void printGraph() {	
		String filename = Double.toString(System.currentTimeMillis());
		
		filename = "searchSpaceAdaptationRelaxed-"+planning_problem.getDomainName()+"-"+planning_problem.getProblemName()
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
							files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\" dir=\"both\"];\n");
					}else
						files.write("s"+state.getIndex()+"	->	s"+state1.getIndex()+"[label=\""+planning_problem.getActions().get(state.getOutComming().get(state1).get(0))+"\"];\n");
					
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
