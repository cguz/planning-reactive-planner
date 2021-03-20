package pelea.marp.service.rp.reactive.shared.pddl;

import java.util.List;

import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.common.rp.reactive.shared.pddl.SharedSolution;

/**
 * @author cguzman
 */
public class SharedSolutionImp implements SharedSolution {

	private static final long serialVersionUID = 781930027696836889L;
	
	
	private String helper;
	private List<String> tw_agents;
	private List<SharedPartialState> plan;
	

	public SharedSolutionImp(String helper, List<String> tw_agents, List<SharedPartialState> plan) {
		
		this.helper 	= helper;
		this.tw_agents 	= tw_agents;
		this.plan 		= plan;
	}
	
	@Override
	public String getHelper() {
		return helper;
	}

	@Override
	public List<String> getAgents() {
		return tw_agents;
	}

	@Override
	public List<SharedPartialState> getPlan() {
		return plan;
	}

	@Override
	public String toString() {
		return "<helper=" + helper + ", agents(tw)=" + tw_agents + ", solution=\n"+plan.toString()+">";
	}
}