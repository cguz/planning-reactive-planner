package pelea.marp.service.rp.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import JPDDLSim.plan.DummyAction;
import JPDDLSim.plan.PlanAction;
import JPDDLSim.plan.PlanXML;
import conf.xml2java.Conf;
import conf.xml2java.enume.From;
import pelea.marp.common.rp.reactive.enums.SEARCH_SPACE_FILTER;
import pelea.marp.common.rp.reactive.exceptions.NoPosiblePlanException;
import pelea.marp.common.rp.reactive.exceptions.NoRootException;
import pelea.marp.common.rp.reactive.exceptions.NotExecutingPlanException;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTeamwork;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;
import pelea.marp.service.rp.MAReactivePlanner;
import pelea.marp.service.rp.MAReactivePlannerCoord;
import pelea.marp.service.rp.helper.EstimatedTime;
import pelea.marp.service.rp.reactive.shared.pddl.SharedPartialStateImp;
import pelea.marp.service.rp.reactive.shared.teamwork.SharedTeamworkImp;
import pelea.marp.service.rp.reactive.shared.teamwork.SharedTransitionImp;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.plan.DSPlan;
import pelea.planning_task.common.plan.DSPlanAction;
import pelea.planning_task.service.plan.DPlanAction;
import pelea.planning_task.service.plan.NPlan;
import pelea.planning_task.service.plan.NPlanAction;

public class MAReactivePlannerTest {

	// String path = System.getProperty("user.dir")+"/../planInteraction/domains/rovers/advanced/nasa/planetary-analyze/pfile2/agent1/";
	String path = System.getProperty("user.dir")+"/../planInteraction/domains/benchmarks/multi-agent/non-coordinated/vhr/vhr-tw/R1/pfile4/agent2/";
	MAReactivePlanner rp;

	/**
	 * Test method for {@link pelea.marp.service.rp.reactive.MAReactivePlanner#testEstimating_setting()}.
	 */
	@Test
	public void test_scheduling() {
		// load the configuracion file
		Conf _settings = new Conf(path+"configuration.xml");
		
		// creating the reactive planner
		rp = create_reactive_planner(_settings);

		// loading the test plan
		String domain_path = _settings.getValue(From.C_REACTIVEPLANNER, "DOMAIN_PATH"); 
		String file = path +domain_path+"test_plan.xml";
		
		rp.update_teamwork(create_teamwork(1));
		
		// creamos el plan.xml 
		rp.translating(toDSPlan(new PlanXML(new File(file))));
		
		// creating search spaces
		creating_search_spaces((double)50);

		rp.update_teamwork(1);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(2);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(3);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(4);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(5);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(6);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(7);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(8);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(9);
		System.out.println(rp.get_teamwork().toString());
		rp.update_teamwork(10);
		System.out.println(rp.get_teamwork().toString());
		
		// rp.update_teamwork(6, Arrays.asList(-1,1,1,-1,-1));
		
	}
	
	/**
	 * Test method for {@link pelea.marp.service.rp.reactive.MAReactivePlanner#testEstimating_setting()}.
	 */
	@Test
	public void testEstimating_setting() {

		// load the configuracion file
		Conf _settings = new Conf(path+"configuration.xml");
		
		// creating the reactive planner
		rp = create_reactive_planner(_settings);
		
		// loading the test plan
		String domain_path = _settings.getValue(From.C_REACTIVEPLANNER, "DOMAIN_PATH"); 
		String file = path +domain_path+"test_plan.xml";
		
		rp.update_teamwork(create_teamwork(0));

		// creamos el plan.xml 
		rp.translating(toDSPlan(new PlanXML(new File(file))));
		
		// creating search spaces
		creating_search_spaces((double)50);
		
	}
	
	private SharedTeamwork create_teamwork(int created_at_time) {
		List<SharedTransition> transitions = new ArrayList<SharedTransition>();
		
		/*
		// rescue domain
		transitions.add(new SharedTransitionImp(3, "P", "F", null));
		transitions.add(new SharedTransitionImp(3, "P", "A", null));
		transitions.add(new SharedTransitionImp(3, "P", "N", null));
		transitions.add(new SharedTransitionImp(4, "F", "A", null));
		transitions.add(new SharedTransitionImp(5, "A", "N", null));
		transitions.add(new SharedTransitionImp(8, "A", "N", null));
		*/
		
		/*
		// logistic domain
		 * 4.0 : tru2->apn1 [G4 [<in-obj21-apt2,yes>] ], 
		 * 5.0 : tru2->apn1 [G5 [<in-obj23-apt2,yes>] ], 
		 * 8.0 : apn1->tru1 [G8 [<in-obj21-apt1,yes>] ], 
		 * 9.0 : apn1->tru1 [G9 [<in-obj23-apt1,yes>]
		 * */
		transitions.add(new SharedTransitionImp(4, "tru2", "apn1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj21", "apt2", false)), 4, false)));
		transitions.add(new SharedTransitionImp(5, "tru2", "apn1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj23", "apt2", false)), 5, false)));
		transitions.add(new SharedTransitionImp(8, "apn1", "tru1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj21", "apt1", false)), 8, false)));
		transitions.add(new SharedTransitionImp(9, "apn1", "tru1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj23", "apt1", false)), 9, false)));
		/*transitions.add(new SharedTransitionImp(12, "tru1", "apn1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj21", "pos1", false)), 12, false)));
		transitions.add(new SharedTransitionImp(13, "tru1", "apn1", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj23", "pos1", false)), 13, false)));
		transitions.add(new SharedTransitionImp(12, "tru1", "tru2", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj21", "pos1", false)), 12, false)));
		transitions.add(new SharedTransitionImp(13, "tru1", "tru2", new SharedPartialStateImp(rp.getPlanningProblem().getConditions().get(rp.getPlanningProblem().addCondition("in-obj23", "pos1", false)), 13, false)));
		*/
		
		return new SharedTeamworkImp(transitions, created_at_time);
	}
	
	@Test
	public void testCreatting_reactive_planner() {

		// load the configuracion file
		Conf _settings = new Conf(path+"configuration.xml");
		
		// creating the reactive planner
		rp = create_reactive_planner(_settings);
		
		// loading the test plan
		
		System.out.println("Actions: "+ rp.getPlanningProblem().getActions().toString().replaceAll(",", "\n"));
	}
	
	@Test
	public void testSingle_repairing() {

		testEstimating_setting();
		
		// single_repairing
		String plan = null;
		ArrayList<DSCondition> world_state;
		try {
			rp.getPlanningProblem().simulateExecution(false);
			rp.getPlanningProblem().simulateExecution(false);
			world_state = rp.getPlanningProblem().getInitialState();
			
			plan = rp.single_repairing(world_state, 1);
		} catch (NoPosiblePlanException e) {
			e.printStackTrace();
		}
		
	}

	private void creating_search_spaces(double time_limit) {
		
		// estimating the size of the first search space
		EstimatedTime size = rp.estimating_setting(7000);
		
		// calculating the first search space
		rp.calculating(size);
		
		if(!rp.is_plan_window_completed()){
			// estimating the size of the second search space
			size = rp.estimating_setting(time_limit);
			
			// calculating the second search space
			rp.calculating(size);
			
		}
		
		if(!rp.is_plan_window_completed()){
			// estimating the size of the third search space
			size = rp.estimating_setting(time_limit);
			
			// calculating the second search space
			rp.calculating(size);
			
		}
		
		/*try {
			for(int i = 0; i <= 6; ++i){
				System.out.println("\n[EXE-ACTIONS] ["+i+"][SELF-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.SELF_TREE));
				System.out.println("\n[EXE-ACTIONS] ["+i+"][ADAPTING-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.ADAPTING_TREE));
				System.out.println("\n[EXE-ACTIONS] ["+i+"][OWN-ADAPTING]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.OWN_ADAPTING_TREE));
			}
		} catch (NotExecutingPlanException | NoRootException e) {
			e.printStackTrace();
		}
		
		try {
			rp.coord_get_current_root(6);
		} catch (NoRootException e1) {
			e1.printStackTrace();
		}

		try {
			for(int i = 0; i <= 2; ++i){
				System.out.println("\n[EXE-ACTIONS] ["+i+"][SELF-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.SELF_TREE));
				System.out.println("\n[EXE-ACTIONS] ["+i+"][ADAPTING-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.ADAPTING_TREE));
				System.out.println("\n[EXE-ACTIONS] ["+i+"][OWN-ADAPTING]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.OWN_ADAPTING_TREE));
			}
		} catch (NotExecutingPlanException | NoRootException e) {
			e.printStackTrace();
		}*/
		
		if(!rp.is_plan_window_completed()){
			// estimating the size of the third search space
			size = rp.estimating_setting(time_limit);
			
			// calculating the second search space
			rp.calculating(size);
			
			try {
				for(int i = 0; i <= 2; ++i){
					// System.out.println("[EXE-ACTIONS] ["+i+"][SELF-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.SELF_TREE));
					System.out.println("[EXE-ACTIONS] ["+i+"][ADAPTING-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.ADAPTING_TREE));
					System.out.println("[EXE-ACTIONS] ["+i+"][OWN-ADAPTING]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.OWN_ADAPTING_TREE));
				}
			} catch (NotExecutingPlanException | NoRootException e) {
				e.printStackTrace();
			}
		}
		
		if(!rp.is_plan_window_completed()){
			// estimating the size of the third search space
			size = rp.estimating_setting(time_limit);
			
			// calculating the second search space
			rp.calculating(size);
			
			try {
				for(int i = 0; i <= 2; ++i){
					System.out.println("[EXE-ACTIONS] ["+i+"][SELF-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.SELF_TREE));
					System.out.println("[EXE-ACTIONS] ["+i+"][ADAPTING-TREE]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.ADAPTING_TREE));
					System.out.println("[EXE-ACTIONS] ["+i+"][OWN-ADAPTING]: "+rp.get_ps_to_publicize(i, SEARCH_SPACE_FILTER.OWN_ADAPTING_TREE));
				}
			} catch (NotExecutingPlanException | NoRootException e) {
				e.printStackTrace();
			}
		}
	}

	private MAReactivePlanner create_reactive_planner(Conf _settings) {
		
		String domain_path = _settings.getValue(From.C_REACTIVEPLANNER, "DOMAIN_PATH"); 
		String domain_file = _settings.getValue(From.C_INITIALIZE, "DOMAIN"); 
		String problem_file = _settings.getValue(From.C_INITIALIZE, "PROBLEM"); 
		
		String[] _pddl31 = new String[3];
		_pddl31 [0] = path +domain_path+domain_file;
		_pddl31[1] = path +domain_path+problem_file;
		_pddl31[2] = "";
		
		if(!_settings.getBoolean(From.C_EXECUTION, "CENTRAL_PLANNER"))
			return new MAReactivePlanner(_pddl31, null, _settings, path);
		else
			return new MAReactivePlannerCoord(_pddl31, null, _settings, path);
	}

	private ArrayList<DSPlan> toDSPlan(PlanXML plan_xml) {
		
		ArrayList<PlanAction> actions = plan_xml.getActions();
		ArrayList<DSPlanAction> plan_act = new ArrayList<DSPlanAction>(actions.size());
		ArrayList<DSPlan> plan = new ArrayList<DSPlan>(1);

		DSPlanAction act;
		String 		 act_s;
		
		double start_time=0;
		
		for (PlanAction a :actions){
			if(a instanceof DummyAction)
				act = new DPlanAction(start_time);
			else{
				act_s = a.getAction().toLowerCase().replaceAll("\\)", "").replaceAll("\\(", "");
				act = new NPlanAction(act_s, start_time,
					a.getDuration(), a.getIndex()
					, rp.getPlanningProblem().getActionsIndex().get(act_s), 
					-1, DSPlanAction.CURRENT_ONGOING);
			}
			start_time+=a.getDuration();
			plan_act.add(act);
		}
		
		plan.add(new NPlan(rp.getPlanningProblem().getDomainName(), rp.getPlanningProblem().getProblemName(), Integer.toString(0),
				Integer.toString(0), plan_act, actions.size()));
		
		return plan;
	}
}
