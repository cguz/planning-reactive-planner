package pelea.marp.service.rp.reactive.repairing_structures;

import java.util.List;

import pelea.marp.common.rp.reactive.repairing_structures.ManagerStructure;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByState;

public abstract class ManagerStructureAbstract implements ManagerStructure { 
	
	// associated search tree
	protected SearchSpace search_tree;
	
	// maximum depth of the search tree
	protected int depth = 0;
	
	// set of variables Q in the search tree
	protected List<Integer> var_Q;

	
	public ManagerStructureAbstract(SearchSpace _temp_search_space) {
		search_tree = _temp_search_space;
	}
	
	public ManagerStructureAbstract(SearchSpace _temp_search_space, int _depth) {
		search_tree = _temp_search_space;
		depth		= _depth;
	}

	@Override
	public SearchSpace getSearchTree() {
		return search_tree;
	}

	@Override
	public int getDepth() {
		
		if (depth == 0) 
			depth = ((SearchSpaceByState) search_tree).get_estimated_size().getDepth();
		
		return depth;
	}

	@Override
	public List<Integer> getVarQ() {
		
		if(var_Q == null) var_Q = search_tree.getTotalVariables(); 
		return var_Q;
		
	}
}
