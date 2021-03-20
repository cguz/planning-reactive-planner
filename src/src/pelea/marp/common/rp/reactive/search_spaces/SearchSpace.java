package pelea.marp.common.rp.reactive.search_spaces;

import java.util.List;

import pelea.marp.common.rp.reactive.exceptions.GeneralException;
import pelea.marp.common.rp.reactive.exceptions.NoRootException;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.service.rp.helper.Statistics;

/**
 * General and common search space. It is used for Single and Multi repairing.
 * @author cguzman
 * 
 */
public interface SearchSpace {
	// calculating repairing structures	
	List<Integer> getTotalVariables();


	/**
	 * self-agent repair to request for a multi-agent repairing
	 * partial states to publicizes
	 * @return set of partial states to publicizes
	 */
	List<SharedPartialState> get_ps_to_publicize();
	List<SharedPartialState> get_ps_to_publicize(int current_window_ex, boolean regressed);


	/**
	 * @return the root node
	 */
	NodeState get_root();
	
	
	
	
	// repairing method
	/**
	 * @return the repaired plan to the root node
	 * @throws NoRootException 
	 * @throws GeneralException 
	 */
	List<Integer> get_plan_to_root() throws NoRootException, GeneralException;


	List<SharedPartialState> regress_plan(List<Integer> plan);


	List<SharedPartialState> forward_plan(List<Integer> plan);
	
	
	// statistic	
	/**
	 * return the total of results for generating, self-repair and multi-repair
	 * @return
	 */
	Statistics get_statistics();
	
	/**
	 * print the search space to a pdf file
	 * using dot
	 */
	void printGraph();




	
}
