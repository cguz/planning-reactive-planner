package pelea.marp.common.rp.reactive.repairing_structures;

import java.util.List;

import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;

public interface ManagerStructure {
	/**
	 * getting the search tree or repairing structure
	 * @return root node of the search tree
	 */
	public SearchSpace getSearchTree();

	/**
	 * depth of the search tree
	 * @return max depth
	 */
	public int getDepth();

	/**
	 * set of variables in the search tree
	 * @return list of variables (index)
	 */
	public List<Integer> getVarQ();

}
