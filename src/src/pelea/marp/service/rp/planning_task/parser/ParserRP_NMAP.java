package pelea.marp.service.rp.planning_task.parser;

import org.agreement_technologies.ground.ParserPddl;

import pelea.marp.service.rp.planning_task.PlanningProblemRPImp;
import pelea.planning_task.service.parser.ParserImp;

public class ParserRP_NMAP extends ParserImp {

	public ParserRP_NMAP(String[] domainProblem, boolean own_actions) {
		super(domainProblem, "test_agent", own_actions);
	}

	public ParserRP_NMAP(String[] domainProblem, String agent, boolean own_actions) {
		super(domainProblem, agent, own_actions);
	}

	protected void create_planning_problem(ParserPddl parserPddl) {
		planningProblem = new PlanningProblemRPImp(parserPddl);
		System.out.println("\n["+planningProblem.getAgentName()+"] total number of instantiated actions: "+planningProblem.getActions().size());//+"\n["+planningProblem.getAgentName()+"] actions: "+planningProblem.getActionsIndex().toString()+"");
	}
}