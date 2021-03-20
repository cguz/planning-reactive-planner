package pelea.marp.service.rp.reactive.search_spaces;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

import pelea.atpa.common.enums.Enums.APPROACH;
import pelea.marp.common.rp.reactive.enums.Enums.SETTINGS_REACTIVESTRUCTURE;
import pelea.marp.common.rp.reactive.exceptions.GeneralException;
import pelea.marp.common.rp.reactive.exceptions.NoRootException;
import pelea.marp.common.rp.reactive.search_spaces.NodeState;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;
import pelea.marp.common.rp.reactive.settings.GeneralSettings;
import pelea.marp.common.rp.reactive.settings.MultiSettings;
import pelea.marp.common.rp.reactive.settings.SingleSettings;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.service.rp.helper.Statistics;
import pelea.marp.service.rp.reactive.shared.pddl.SharedPartialStateImp;
import pelea.marp.service.rp.reactive.trie.TrieStruct;
import pelea.planning_task.common.PlanningProblem;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.partial_state.PS;
import pelea.planning_task.service.pddl.FAction;
import pelea.planning_task.service.pddl.partial_state.PSImp;
import pelea.planning_task.tools.ExecuteScript_With_Thread;
import tools.files.Files;

/* This is implemented as a tree */
public abstract class SearchSpaceAbstractImp implements SearchSpace { 

	protected static 	SETTINGS_REACTIVESTRUCTURE 	METHOD = SETTINGS_REACTIVESTRUCTURE.GTS_INCREMENTALTREE;		

	public PlanningProblem 			planning_problem;
	
	protected ArrayList<Integer> 	plan;

	// saving the several plans
	protected ArrayList<ArrayList<Integer>> found_plans;
	protected static int MAX_TOTAL_PLANS = 5;
	
	// initial time
	protected long					initial_time;
		
	
	/**
	 * The root node of the Goal-State Transition System and the basic GTS.
	 * */
	List<NodeState> 				basic_states;
	NodeState 						root_node;
	int 							index_root;
	public NodeState get_root() {return root_node;}
	
	
	/**
	 * Represents a Trie structure to find in a fast way
	 * if a node (goal-state) is a superset of another node. 
	 * */
	TrieStruct 						root_var;

	/**
	 * Describes the leaf nodes that need to be deleted 
	 * in each iteration, when the window is increased
	 * */
	HashSet<Integer> 				leaf_nodes = new HashSet<Integer>();
	
	
	
	
	//Saving all the extended nodes
	HashMap<NodeState, NodeState> 	s_states 		= new HashMap<NodeState, NodeState>();
	HashMap<Integer, NodeState> 	s_statesIndex 	= new HashMap<Integer, NodeState>(10);
	int 							t_states 		= 0;
	
	// variables for generating repairing structures multi process
	protected int 					DEPTH_MAX 		= 4;
	
	
	
	
	/**
	 * founded plan to the root node
	 * */
	protected ArrayList<ArrayList<Integer>> self_repair_planToRoot 	= null;
	protected NodeState 					self_repair_origin 		= null;
	
	
	
	
	/**
	 * total results for generating, self-repair and multi-repair
	 */
	protected Statistics 				total_results = null;
	protected int 						no_iteration  = 0;
	
	
	
	
	protected GeneralSettings 			settings;
	
	
	
	
	// evaluation and results
	protected Files 					eval;
	protected boolean					debug_eval=false;
	protected Files 					eval_additional;
	protected boolean					debug_eval_ad=false;
	protected Files 					eval_training;
	protected boolean					debug_evalan=false;
	protected String 					path_eval_benchmarks;
	
	
	
	
	/**
	 * multi-repairing process
	 * @param planningProblem
	 * @param goal_state
	 * @param settings
	 * @param path 
	 * @param window_current 
	 */
	public SearchSpaceAbstractImp(PlanningProblem planningProblem, GeneralSettings settings, String path) {
		this.settings		=	settings;
		
		total_results		=	new Statistics();

		planning_problem 	= 	planningProblem;
		
		path_eval_benchmarks = path;
		
		if(settings!= null && settings.getTypeExecution() == SETTINGS_REACTIVESTRUCTURE.RP_TE_CREATING_DATASET_BENCHMARK_MODE)
			debug_evalan=true;
		
		defaultStatistic();
		
	}




	// calculating
	public void calculating(int depth) {

		// initializing variables to create the repairing structure
		default_values();
		
		DEPTH_MAX = depth;
		
		startingStatistic();
		
		extendingGraph(root_node);

		endingStatistic();
		
	}
	
	protected void default_values() { 
		s_states 		= new HashMap<NodeState, NodeState>();
		s_statesIndex 	= new HashMap<Integer, NodeState>(10);
		leaf_nodes 		= new HashSet<Integer>();
		root_var		= null;
		t_states 		= 0;
		no_iteration	= 0;
		
		// saving the root node
		saving_root_node();
	}

	protected void saving_root_node() {
		root_node = new NodeStateImp(((MultiSettings)settings).getFluent(), this.planning_problem);
		root_node.setIndex(t_states);
		s_states.put(root_node, root_node);
		s_statesIndex.put(root_node.getIndex(), root_node);

		if (root_var==null){
			root_var = new TrieStruct(planning_problem, settings);
			root_var.insert(root_node);
		}
	}

	protected void startingStatistic() { 
		if(debug_eval) { 
			eval.write("no_iteration");
			eval.write("\tdomain");
			// System.out.print("\t"+rootNode.getIndex());
			eval.write("\tms");
			//eval.write("\test-GSs");
			eval.write("\tGSs");
			eval.write("\tleaf-states");
			eval.write("\ttrie");
			eval.write("\trVar");//+relevantVariables.toString());
			//eval.write("\tdomain-rVar");//+relevantVariables.toString());
			eval.write("\troot-var");//+nodeState.toPddl());
			//eval.write("\test-prod");
			//eval.write("\test-prod-uni");
			//eval.write("\tw");
			eval.write("\tl");
			//eval.write("\t"+ramificationFactor() + " ram_fact");
			//eval.write("\t"+getNumNewPontentialNodes2() + " est2 nodes");
			//eval.write("\t"+String.valueOf(cleverMethod(nodeState)) + " est clever");
			eval.write("\n");
		}
		
		if(debug_eval_ad) { 
			eval_additional = new Files("./results/"+planning_problem.getDomainName()+"/infoAditional-"+planning_problem.getDomainName()+"-"+((MultiSettings)settings).getFluent(), true);
			eval_additional.write("\nDepth = "+DEPTH_MAX);
		}
		
		if(debug_evalan){
			eval_training 	= new Files(path_eval_benchmarks+"/training-"+planning_problem.getDomainName()+"-"+((MultiSettings)settings).getFluent(), true);
		}
			
	}
	
	protected void defaultStatistic() {		
		if(debug_evalan){
			String path = path_eval_benchmarks+"/training-"+planning_problem.getDomainName()+"-"+((MultiSettings)settings).getFluent();
			if(!eval_training.fileExist(path)) {
			
				eval_training = new Files(path, true);

				eval_training.write("domain");
				//eval_training.write("\tw"); // w
				eval_training.write("\td"); // l
				eval_training.write("\tpfile");
				eval_training.write("\trealTime"); // ms
				eval_training.write("\tN");// GSs
				//eval_training.write("\trVar");//+relevantVariables.toString()); 
				eval_training.write("\tfluentRoot");//+nodeState.toPddl());+" root var"
				eval_training.write("\ttrie");//  + " trie-nodes"
				eval_training.write("\tevaluatedNodes"); // evaluated nodes
				//eval_analysis.write("\t"+(bestWindow.getEstimatedTime()/0.767433)); // estimated nodes to generate the gts
				eval_training.write("\n");
				eval_training.close();
				
			}
		}
	}

	protected void endingStatistic() { 
		
		if(debug_eval) 		eval.close();
		if(debug_eval_ad) 	eval_additional.close();
		if(debug_evalan) 	eval_training.close();
		
	}




	protected void extendingGraph(NodeState root_state) {
		
		//STATISTICAL DATA VARIABLES
		int freq = 10;
		int nodesCreated=freq;
		int multiply = 1;
		long relativeTime 		= System.currentTimeMillis();
		long finalTime = 0;
		int evaluated_nodes = 0;
		int mean_fluents = 0;
		//boolean expandedRoot = false;
		//boolean ok = true;
		++no_iteration;
		// int combinations = getNumNewPontentialNodes(nodeState);
		
		long 				startTime 		= System.currentTimeMillis();	
		NodeState 			current_G 	= null, new_G = null;
		List<Integer> 		actions;
		HashSet<Integer> 	visited 		= new HashSet<Integer>();
		int index = 0;
		
		Queue<Integer> 		open_states_Q 	= new LinkedList<Integer>();	
		Queue<Integer> 		newOpenGStates 	= new LinkedList<Integer>();
		open_states_Q.add(root_state.getIndex());

		// printGraph();
		// printGraphTrie();

		boolean stopCondition = false;
		
		int reached_depth = 0;
		
		if(!exists_failure_as_static(root_node)){
		
			//ALGORITHM BEGINNING
			while(!stopCondition && !open_states_Q.isEmpty()){
				
				// printGraph();
				
				current_G = s_statesIndex.get(open_states_Q.remove());
				visited.add(current_G.getIndex());
				
				// if the depth is not reached
				reached_depth = current_G.getDepth();
				// if(current_G.getDepth() < DEPTH_MAX){
				
					actions = getRelevantActionsTo(current_G);
					/*System.out.println("[Actions]");
					for (Integer act: actions){
						System.out.println("->"+planning_problem.getActions().get(act).toString());
					}*/
					
					
					// for all relevant actions to G
					for (Integer act: actions){
						
						// applying regression transition function
						new_G = new NodeStateImp(planning_problem.regress(current_G.getRPS(), planning_problem.getActions().get(act)), planning_problem,false);
	
						evaluated_nodes++;
						
						// if new_G is not in T
						root_var.setNewGState(new_G.getGoalState());
						index = root_var.has();
						if (index == -1){
							
							mean_fluents += new_G.getGoalState().size();
							
							// if new_G is super set of a state in T
							index = root_var.hasSubSet();
							
							if (index!=-1){// it is a super set
								new_G.setSubSet(s_statesIndex.get(index));
								if (!new_G.getSubSet().equals(this)
										&& !current_G.isOutComming(new_G.getSubSet())){
										//Es necesario asociar el indice antes de la transicion
										new_G.setIndex(++t_states);
										new_G.addTransitions(act,current_G);
										s_states.put(new_G, new_G);
										s_statesIndex.put(new_G.getIndex(), new_G);
										root_var.insert(new_G);// to avoid repeated subsets
										--nodesCreated;
	
										if(current_G.getDepth() >= DEPTH_MAX) {										
											visited.add(new_G.getIndex());
											leaf_nodes.add(new_G.getIndex());
										}
										
								}
							}else{// if it is no super set we get it to expand		
								//Es necesario asociar el indice antes de la transicion
								new_G.setIndex(++t_states);	
								new_G.addTransitions(act,current_G);
								s_states.put(new_G, new_G);	
								s_statesIndex.put(new_G.getIndex(), new_G);
								root_var.insert(new_G);
								--nodesCreated;
								
								//METODO2
								if(current_G.getDepth() < DEPTH_MAX) {
									open_states_Q.add(new_G.getIndex());
								}
								else{
									visited.add(new_G.getIndex());
									leaf_nodes.add(new_G.getIndex());
								}
								
							}
							
							//STATISTICAL EXECUTION DATA EVERY freq NODES
							if(nodesCreated==0 && debug_eval_ad){
								printData(relativeTime, freq, multiply, evaluated_nodes, mean_fluents);
								relativeTime = finalTime;
								multiply++;
								nodesCreated=freq;
								//evaluatedNodes=0;
								mean_fluents=0;
							}
							
							if(check_stop_condition(new_G)){
								stopCondition = true;
								break;
							}
								
						}else{
							if(!new_G.equals(current_G)){
								NodeState oldGState = s_statesIndex.get(index);
								if(!visited.contains(oldGState.getIndex())){
		//								DE ESTA FORMA TRATAMOS DE EXPANDIR ÚNICAMENTE LOS NUEVOS NODOS HOJA
		//								DESCENDIENTES DEL ESTADO REPETIDO
									newOpenGStates.clear();
									newOpenGStates.add(oldGState.getIndex());
									while(!newOpenGStates.isEmpty()){
										
										oldGState = s_statesIndex.get(newOpenGStates.remove());
										if(oldGState.hasChilds()){
											for(NodeState child : oldGState.getChilds()){
													if(!visited.contains(child.getIndex()))
															newOpenGStates.add(child.getIndex());
											}
											visited.add(oldGState.getIndex());
										}else{
											open_states_Q.add(oldGState.getIndex());
										}
									}
								}
							}
						}
						
						new_G	= null;
						//printGraphTrie();
					}
					current_G 	= null;
					actions		= null;
					
				/*}else{
					open_states_Q.clear();
				}*/
			}
		}
		
		long endTime 	= System.currentTimeMillis() - startTime;

		total_results.generating_depth_reached = reached_depth;
		
		statistics(endTime, root_state, evaluated_nodes);
		
		if(!stopCondition)
			total_results.repair_error= SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_SEARCH_SPACE_EXHAUSTED;
		
		
		if (this.settings.getDebug()) 
			printGraph();
		
		Runtime garbage = Runtime.getRuntime();
	    garbage.gc();
	}

	private String parse_plan;
	protected ArrayList<Integer> select_best_plan(ArrayList<ArrayList<Integer>> plans) {

		String temp = "";
		
		int index_best_plan = 0;
		int count_actions = 100;
		int temp_count;
		int index;
		
		for(int i = 0; i < plans.size(); ++i){
			temp+="<";
			
			String[] temp_all = count_total_actions(plans.get(i)).split(";");
			temp_count = Integer.parseInt(temp_all[1]); 
			temp+=temp_all[0];
					
			index = temp.lastIndexOf(",");
			if(index != -1)
				temp=temp.substring(0, index)+">["+temp_count+"/"+count_actions+"]";
			
			if(temp_count < count_actions){
				count_actions = temp_count;
				index_best_plan = i;
			}
		}
		
		parse_plan = temp;
		
		return plans.get(index_best_plan);
	}
	
	private String count_total_actions(ArrayList<Integer> plans) {

		int temp_count = 0;
		DSAction act;
		String temp="";
		
		for(Integer j: plans){
			act = planning_problem.getActions().get(j);
			// if it is a grouped action
			temp+=act.toString()+",";
			if(act instanceof FAction)
				continue;
			++temp_count;
		}
		
		return temp+";"+temp_count;
	}
	
	private int count_total(ArrayList<Integer> plans) {

		int temp_count = 0;
		DSAction act;
		
		for(Integer j: plans){
			act = planning_problem.getActions().get(j);
			if(act instanceof FAction)
				continue;
			++temp_count;
		}
		
		return temp_count;
	}
	
	protected boolean is_best_plan(ArrayList<ArrayList<Integer>> found_plans2, ArrayList<Integer> temp_plan) {

		int actions_plan_1 = count_total(found_plans2.get(found_plans2.size()-1));
		int actions_plan_2 = count_total(temp_plan);
		
		return (actions_plan_2 < actions_plan_1);
		
	}


	protected String parse_plans() {
		
		return parse_plan;
		
	}

	protected boolean exceeds_time() {
		long   elapsed_time 	= System.nanoTime()-initial_time;
		double sec_elapsed_time = (double)elapsed_time / 1000000000.0;
		
		double sec_exe_cycle = settings.get_execution_cycles()/1000;
		
		return (sec_elapsed_time>=sec_exe_cycle);
	}
	
	protected boolean exists_failure_as_static(NodeState state) {
		return false;
	}

	protected boolean check_stop_condition(NodeState newGState) {
		boolean exceed = exceeds_time();
		if(exceed)
        	total_results.repair_error = SETTINGS_REACTIVESTRUCTURE.RESULTS_ERROR_EXCEEDS_TIME;
		return exceed;
	}
	
	protected void statistics(long endTime, NodeState nodeState, int evaluatedNodes) {
		
		if(debug_eval){
			eval.write(""+no_iteration);
			eval.write("\t"+this.planning_problem.getProblemName());
			eval.write("\t"+endTime);//  + " ms"
			eval.write("\t"+s_states.size()); //  + " GSs"
			eval.write("\t"+leaf_nodes.size()); //  + " leaf-states"
			eval.write("\t"+root_var.getTotalTrieNodes()); //  + " trie-nodes"
			//eval.write("\t"+relevant_variables.size());//+relevantVariables.toString()); +" relevant var"
			eval.write("\t"+nodeState.getGoalState().size());//+nodeState.toPddl()); +" root var"
			//eval.write("\t"+WINDOW_SIZE); //  + " w"
			eval.write("\t"+DEPTH_MAX); //  + " l"
			eval.write("\n");
		}
		
		if(debug_evalan){
			eval_training.write(""+this.planning_problem.getDomainName());
			// eval_training.write("\t"+WINDOW_SIZE); // w
			eval_training.write("\t"+DEPTH_MAX); // l
			eval_training.write("\t"+this.planning_problem.getProblemName().replaceAll("pfile", ""));
			eval_training.write("\t"+endTime); // ms
			eval_training.write("\t"+s_states.size());// GSs
			// eval_training.write("\t"+relevant_variables.size());//+relevantVariables.toString()); 
			eval_training.write("\t"+nodeState.getGoalState().size());//+nodeState.toPddl());+" root var"
			eval_training.write("\t"+root_var.getTotalTrieNodes());//  + " trie-nodes"
			eval_training.write("\t"+evaluatedNodes); // evaluated nodes
			//eval_analysis.write("\t"+(bestWindow.getEstimatedTime()/0.767433)); // estimated nodes to generate the gts
			eval_training.write("\n");
		}

		// saving total results of generating the search tree
		total_results.generating_total_nodes=s_states.size();
		//total_results.generating_relevant_vars = relevant_variables.size();
		total_results.generating_leaf_nodes = leaf_nodes.size();
		total_results.generating_total_trie_nodes = root_var.getTotalTrieNodes();
		total_results.generating_evaluated_nodes = evaluatedNodes;	
		total_results.generating_total_time = endTime;
	}

	/**
	 * for all {a ∈ A | relevant(G, a) is true} do
	 * @param g
	 * @return
	 */
	protected List<Integer> getRelevantActionsTo(NodeState g) {
		
		List<Integer> 	l_a = new ArrayList<Integer>();
		DSAction 		act = null;
		
		SortedSet<Integer> t = new TreeSet<Integer>();
		for (DSCondition var:g.getGoalState()){
			t.addAll(planning_problem.getHashProducersVar().get(planning_problem.getVariablesIndex().get(var.getFunction())));
		}

		while(!t.isEmpty()){
			int i = t.first();
			t.remove(i);
			act = planning_problem.getActions().get(i);
			if(g.supports_regression(act.getEffects(),act.getConditions()))
				l_a.add(i);
		}
		
		t = null;
		
		return l_a;
	}
	
	private void printData(long relativeTime, int freq, int multiply, int evaluatedNodes, int meanFluents){
		long finalTime = System.currentTimeMillis() ;
		String out = "Num. of states (x"+freq+"): "+multiply;
		out += "\tTrie Size: "+root_var.getTotalTrieNodes();
		out += "\tEvaluated nodes: "+evaluatedNodes;
		DecimalFormat df2 = new DecimalFormat( "#.##" );
		String r = df2.format(meanFluents/((double)evaluatedNodes));
		out += "\tMean fluents: "+r;
		out += "\tTime: "+(finalTime - relativeTime)+" ms";
//		out += "\tStates index: "+s_statesIndex.size();
//		out += "\tSuperSets: "+(s_states.size()-(freq*multiply));
//		out += "\tSearch Time: "+df2.format(totalTimeTime/((double)evaluatedNodes))+" ms";
//		out += "\tGeneration Time: "+df2.format(totalGenerationTime/((double)evaluatedNodes))+" ms";
		out += "\n";
		eval_additional.write(out);
	}
	
	@Override
	public List<Integer> getTotalVariables() {
		return root_var.getListVar();
	}
	
	
	
	@Override
	public List<SharedPartialState> get_ps_to_publicize() {
		
		return get_ps_to_publicize(0, true);
		
	}

	@Override
	public List<SharedPartialState> get_ps_to_publicize(int exe, boolean regressed) {

		if(plan==null){
			System.out.println("["+planning_problem.getAgentName()+"][PS-PUBLICIZES] the agent is planning but has not yet the plan. root: "+root_node.toPddl());
			return new ArrayList<SharedPartialState>(0);
		}
		
		if(regressed)
			return regress_plan(new ArrayList<Integer>(plan.subList(exe, plan.size())));
		
		return forward_plan(new ArrayList<Integer>(plan.subList(exe, plan.size())));
		
	}
	
	@Override
	public List<SharedPartialState> regress_plan(List<Integer> plan){
		
		return regress_plan(root_node, plan);
		
	}
	
	private List<SharedPartialState> regress_plan(NodeState root, List<Integer> plan){
		
		List<SharedPartialState> partial_states = new ArrayList<SharedPartialState>((plan!=null)?plan.size()+1:0);
		
		if((plan!=null)){
			DSAction act;
			PS new_state;
			
			new_state = root.getRPS();
			partial_states.add(new SharedPartialStateImp((ArrayList<DSCondition>) new_state.getOwnFluents(), plan.size(), true));
			
			int index;
			
			for(int i = plan.size()-1; i >= 0; --i) {
				
				index = plan.get(i);
				
				// if it is not a dummy action
				if(index != -1){
					
					// we regress the partial state
					act = planning_problem.getActions().get(index);
					new_state = planning_problem.regress(new_state,act);
					
					partial_states.add(0, new SharedPartialStateImp((ArrayList<DSCondition>) new_state.getOwnFluents(), i, true));
					
				}else{ // if it is a dummy action we save an empty state
					
					partial_states.add(0, new SharedPartialStateImp(new ArrayList<DSCondition>(), i, true));
					
				}
			}
		}
		
		return partial_states;
	}
	
	@Override
	public List<SharedPartialState> forward_plan(List<Integer> plan){
		
		return forward_plan(root_node, plan);
		
	}
	
	private List<SharedPartialState> forward_plan(NodeState root, List<Integer> plan){
		
		List<SharedPartialState> partial_states = new ArrayList<SharedPartialState>((plan!=null)?plan.size()+1:0);

		APPROACH app_pos_effs = (((SingleSettings)this.settings).getFilteringRootNodeEffects() == SETTINGS_REACTIVESTRUCTURE.RP_TREE_RN_POS_EFFECTS)?APPROACH.APP_POS_EFFECTS:APPROACH.APP_ALL_EFFECTS;
		
		if(plan != null){
			DSAction act;
			PS new_state;
			
			int index = plan.get(0);

			act = planning_problem.getActions().get(index);

			new_state= new PSImp(act.getConditions(), null);
			
			partial_states.add(0, new SharedPartialStateImp((ArrayList<DSCondition>) new_state.getFluents(), 0, true));

			try {
				new_state = planning_problem.forward(act, new_state, app_pos_effs);			
				partial_states.add(new SharedPartialStateImp((ArrayList<DSCondition>) new_state.getFluents(), 1, true));
			} catch (Exception ef) { act = null; System.out.println("Warning: there is not more action in the plan"); }
			
			for(int i = 1; i <= plan.size()-1; ++i) {
				
				// if it is not a dummy action
				if(index != -1){
					// regressing the partial state
					try {
						act = planning_problem.getActions().get(index);
					} catch (Exception ef) { act = null; System.out.println("Warning: there is not more action in the plan"); }
					
					new_state = planning_problem.forward(act, new_state, app_pos_effs);		
				}				
				partial_states.add(new SharedPartialStateImp((ArrayList<DSCondition>) new_state.getOwnFluents(), i, true));		
			}
		}
		
		return partial_states;
	}
	
	
	

	// self-repair
	
	protected PS changingCurrentValuesPre(ArrayList<DSCondition> current_state,ArrayList<DSCondition> target) {
		ArrayList<DSCondition> init = new ArrayList<DSCondition>();
		
		// for all preconditions
		for (DSCondition t1: target)
			for (DSCondition c: current_state){
				if (!t1.getFunction().equals(c.getFunction())) continue;
				init.add(c);
				break;
			}
		
		return new PSImp(init, null);
	}
	
	protected ArrayList<ArrayList<Integer>> get_paths(NodeState origin_node, NodeState destination_node) {
		
		self_repair_planToRoot=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> plans=new ArrayList<ArrayList<Integer>>();
		plans.add(getCompletePlans(origin_node, destination_node));

		if(origin_node.isSuperSet())
			plans.add(getCompletePlans(origin_node.getSubSet(), destination_node));

        return plans;
        
	}

	private ArrayList<Integer> getCompletePlans(NodeState originNode, NodeState destinationNode) {

        ArrayList<Integer> 	p = new ArrayList<Integer>(); // a plan
		
		NodeState index=originNode;
		NodeState current;
        
        if(originNode.equals(root_node)){
        	getTail(p, root_node);
        }else{
            current = originNode;
            // finding the plan from the origin to the destiny
	        while(!current.equals(destinationNode) && !current.equals(root_node)){
	        	 //We get the parent
	        	for (NodeState out: current.getOutComming().keySet()){
	        		for(int a: current.getOutComming().get(out)){
	        			index = s_states.get(out);
	        			p.add(a);
	        		}
	        	}
	        	current = index;
	        }
	
	        if(current.equals(destinationNode)) {
	        		// adding the plan from the destination node to the final action plan
	           	 	getTail(p, destinationNode);
	        }else{
	        	destinationNode=destinationNode.getParent();
	    		if(destinationNode!=null)
	    			p = getCompletePlans(originNode, destinationNode);
	        }
        }
        return p;
	}
	
	private void getTail(ArrayList<Integer> p2, NodeState target) {
		
		NodeState current = target;
		// finding the plan from the destiny to the root
        while(!current.equals(root_node)){
        	 // we get the parent
        	for (NodeState out: current.getOutComming().keySet()){
        		for(int a: current.getOutComming().get(out)){
        			current = s_states.get(out);
        			p2.add(a);
        		}
        	}
        }
		
        // saving plan to root node
        if(self_repair_planToRoot.isEmpty()){
			if(current.equals(root_node)){
				self_repair_planToRoot.add(new ArrayList<Integer>(p2));
			}
		}
        
	}
	
	protected NodeState get_target_node(ArrayList<DSCondition> goalState, ArrayList<DSCondition> current_state) {
		
		if(goalState==null)
			return null;
		
		int index;
		
		// searching the node of the basic structure in the extended graph
		root_var.setNewGState(goalState);
		index = root_var.has();
		if (index!=-1)  return s_statesIndex.get(index);

		// we search for a sub set of the current state
		return searchOriginNodeDest(goalState, root_node, current_state, null);
	}

	protected NodeState getOriginNode(ArrayList<DSCondition> oriState, NodeState destIndex, ArrayList<DSCondition> curState, NodeState banned) {
		
		// the node is equal to other in the structure T
		root_var.setNewGState(oriState);
		int index = root_var.hasSubSet();
		if (index!=-1)
			return s_statesIndex.get(index);
		
		// we search for a sub set of the current state
		return searchOriginNode(oriState, destIndex, curState, banned);
	}

	private NodeState searchOriginNode(ArrayList<DSCondition> oriState, NodeState targetIndex, ArrayList<DSCondition> currentState, NodeState banned) {
		Queue<NodeState> 	openGStates 	= new LinkedList<NodeState>();	
		NodeState[]			incomming		;
		NodeState 			currentOpenGS 	= null;
		boolean 			ok				= false;
		
		// if the node is not in the GTS searching in the child of the destiny node
		openGStates.add(targetIndex);
		while(!openGStates.isEmpty()){
			currentOpenGS = openGStates.remove();
			
			// if oriState is a sub set of current state
			if(currentOpenGS.isEquals(oriState))
					return currentOpenGS;
			
			if(currentOpenGS.isContainIn(oriState)){
				// ASK FOR ADITIONAL INFO TO THE MONITORING MODULE
				ok = askingAdditionalInfo(currentOpenGS.getExtraVars(oriState), currentState);
				if (ok)	return currentOpenGS;
			}
			
			// adding the child nodes in the open goal-states
			// with a heuristic to determinate the order of the node to visit
			// heuristic: highest number occurrences go first
			incomming = currentOpenGS.getChilds(oriState, SETTINGS_REACTIVESTRUCTURE.GTS_H_HIGHEST_OCUR_ASC);
			
			for (NodeState node:incomming)
				if(!node.equals(banned))
					openGStates.add(node);
			
			incomming=null;
		}
		
		NodeState outcomming=targetIndex.getParent();
		if(outcomming!=null){
			oriState = updatePreconditions(outcomming, currentState);
			return searchOriginNode(oriState, outcomming, currentState, targetIndex);
		}
		return null;
	}
	
	private NodeState searchOriginNodeDest(ArrayList<DSCondition> oriState,
			NodeState destIndex, ArrayList<DSCondition> currentState, NodeState banned) {
		Queue<NodeState> 	openGStates 	= new LinkedList<NodeState>();	
		NodeState[]			incomming		;
		NodeState 			currentOpenGS 	= null;
		boolean 			ok				= false;
		
		// if the node is not in the GTS searching in the child of the destiny node
		openGStates.add(destIndex);
		while(!openGStates.isEmpty()){
			currentOpenGS = openGStates.remove();
			
			// if oriState is a sub set of current state
			if(currentOpenGS.isEquals(oriState))
				return currentOpenGS;
			
			if(currentOpenGS.isContainInDest(oriState)){
				// ASK FOR ADITIONAL INFO TO THE MONITORING MODULE
				ok = askingAdditionalInfo(currentOpenGS.getExtraVars(oriState), currentState);
				if (ok)	return currentOpenGS;
			}
			
			// adding the child nodes in the open goal-states
			// with a heuristic to determinate the order of the node to visit
			// heuristic: highest number occurrences go first
			incomming = currentOpenGS.getChilds(oriState, SETTINGS_REACTIVESTRUCTURE.GTS_H_HIGHEST_OCUR_ASC);
			
			for (NodeState node:incomming)
				if(!node.equals(banned))
					openGStates.add(node);
			
			incomming=null;
		}
		
		/*for (NodeState outcomming:banned.getOutComming().keySet()){
			destIndex= outcomming; 
			return searchOriginNode(oriState, destIndex, currentState, banned);
		}*/
		NodeState outcomming=destIndex.getParent();
		if(outcomming!=null){
			oriState = updatePreconditions(outcomming, currentState);				
			return searchOriginNode(oriState, outcomming, currentState, destIndex);
		}
		return null;
	}
	
	private boolean askingAdditionalInfo(ArrayList<DSCondition> vars, ArrayList<DSCondition> currentState) {

		boolean found = true;
		for (DSCondition v : vars){
			found = false;
			for(DSCondition c : currentState)
				if(v.getFunction().equals(c.getFunction()) && v.getValue().equals(c.getValue())){
					found = true;
					break;
				}
			
			if (!found)break;
		}
		
		if (found) return true;

		return false;
	}
	
	/**
	 * Updating the origin to search with the new preconditions of the action
	 * @param targetIndex		Target node
	 * @param currentState		current value of the state variables in the window
	 * @return	a set of preconditions of the next action with its current values.
	 */
	private ArrayList<DSCondition> updatePreconditions(NodeState targetIndex, ArrayList<DSCondition> currentState) { 
		Integer operator = targetIndex.getOutOperator();
		DSAction nextAction;
		ArrayList<DSCondition> origin;
		ArrayList<DSCondition> temp;
		if(operator != null) { 
			  nextAction = this.planning_problem.getActions().get(operator);
			  temp = nextAction.getConditions();
		}else temp = targetIndex.getGoalState();

		origin = new ArrayList<DSCondition>(temp.size());		
		for(DSCondition pre :temp)
			for (DSCondition c: currentState){
				if(!c.getFunction().equals(pre.getFunction())) continue;
				origin.add(c);break;
			}
		
		// System.out.println(origin);
		
		return origin;
	}

	@Override
	public List<Integer> get_plan_to_root() throws NoRootException, GeneralException { 
		
		if(self_repair_planToRoot == null || self_repair_planToRoot.size()==0){
			if(root_node!=null)
				throw new GeneralException("["+planning_problem.getAgentName()+"] plan to root node not found ! root node : "+root_node.toPddl());
			throw new NoRootException("["+planning_problem.getAgentName()+"] there is not root node ! ");
		}
		
		if(self_repair_planToRoot.size() > 1 )
			return self_repair_planToRoot.get(1);
		
		return self_repair_planToRoot.get(0);
	}
	
	
	
	
	// statistics
	@Override
	public Statistics get_statistics() {
		return total_results;
	}
	
	@Override
	public void printGraph() {	
		String filename = Double.toString(System.currentTimeMillis());
		
		filename = "searchSpaceByFluent-"+this.planning_problem.getDomainName()+"-"+this.planning_problem.getProblemName()
				+"-F"+((MultiSettings)settings).getFluent()+"-d"+DEPTH_MAX
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
			if (s.isSuperSet()) { 
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
	
	// print debug
	public void printGraphTrie(){
		root_var.printGraphTrie();
	}
	
	/**
	 * execute script con un proceso, para evitar los tiempos largos en la ejecución 
	 * @param temp
	 */
	protected void executeScript(String temp) {
		Thread thread = new ExecuteScript_With_Thread(temp); 
		thread.start(); 
		
		long startTime = System.currentTimeMillis();
		while(thread.isAlive()){
			long afterGrounding = System.currentTimeMillis();
			
			if (((afterGrounding - startTime)/1000.00)>=180)
				thread.interrupt();
		}
		
		thread = null;
	}
}