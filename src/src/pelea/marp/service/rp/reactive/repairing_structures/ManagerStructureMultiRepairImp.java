package pelea.marp.service.rp.reactive.repairing_structures;


import pelea.marp.common.rp.reactive.repairing_structures.ManagerStructureMultiRepair;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpace;
import pelea.marp.common.rp.reactive.search_spaces.SearchSpaceByFluent;

public class ManagerStructureMultiRepairImp extends ManagerStructureAbstract implements ManagerStructureMultiRepair {
	
	public ManagerStructureMultiRepairImp(SearchSpace _temp_search_space, int _depth) {
		super(_temp_search_space, _depth);
	}

	@Override
	public int getRoot() {
		return ((SearchSpaceByFluent)search_tree).getRootIndex();
	} 
	
}
