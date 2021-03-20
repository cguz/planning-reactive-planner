package pelea.marp.service.rp.planning_task.pddl;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.planning_task.pddl.PartialState;

/**
 * @author cguzman
 * Partial states
 */
public class PartialStateImp implements PartialState {
	
	private int time_instant;
	private List<Integer> fluents;
	private List<String> s_fluents;

	public PartialStateImp(List<Integer> fluents, List<String> s_fluents, int time_instant) {

		this.fluents	=fluents;
		this.s_fluents	=s_fluents;
		
		this.time_instant = time_instant;
	}

	public PartialStateImp(int time_instant) {

		fluents=new ArrayList<Integer>(0);
		s_fluents=new ArrayList<String>(0);
		
		this.time_instant = time_instant;
	}

	@Override
	public int getTimeInstant() {
		return time_instant;
	}

	@Override
	public List<Integer> getFluents() {
		return fluents;
	}
	
	@Override
	public String toString() {
		return "G" + time_instant + " " + s_fluents;
	}
	
	@Override
	public boolean contains(List<Integer> f){
		if(fluents.size() < f.size()) return false;
		for(Integer  i: f){
            if(!fluents.contains(i)) 
            	return false;
        }
        return true;
	}

	@Override
	public List<String> getFluentsName() {
		return s_fluents;
	}
}
