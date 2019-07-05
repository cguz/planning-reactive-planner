package pelea.marp.service.rp.reactive.shared.pddl;

import java.util.ArrayList;
import java.util.List;

import pelea.marp.common.rp.reactive.shared.pddl.SharedFluent;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.planning_task.common.pddl.DSCondition;

/**
 * @author cguzman
 * 
 */
public class SharedPartialStateImp implements SharedPartialState {

	private static final long serialVersionUID = 781930027696836889L;
	
	
	private int time_instant;
	private List<SharedFluent> fluents;

	public SharedPartialStateImp(DSCondition c, int time_instant, boolean keep_real_values) {
		default_values(1, time_instant);
		
		add(c, keep_real_values);
		
	}

	public SharedPartialStateImp(ArrayList<DSCondition> goalState, int time_instant, boolean keep_real_values) {
		default_values(goalState.size(),time_instant);
		
		for(DSCondition c : goalState)
			add(c, keep_real_values);
		
	}

	public SharedPartialStateImp(List<SharedFluent> fluents2, int timeInstant) {
		fluents = new ArrayList<SharedFluent>(fluents2);
		time_instant = timeInstant;
	}

	private void default_values(int size, int time_instant) {

		fluents = new ArrayList<SharedFluent>(size);
		this.time_instant = time_instant;
		
	}
	
	private void add(DSCondition c, boolean keep_real_values) {
		String s;
		String value = c.getValue();
		ArrayList<String> reached_values = c.getFunction().getReachableValues();
		
		// if it is not a fictitious fluent
		if(!c.getFunction().isFictitious()){
			
			// replacing the values for yes / not
			if(!keep_real_values){
				value = value.replaceAll("true", "yes").replaceAll("false", "not");
				
				for(int i=0;i<reached_values.size();++i){
					s = reached_values.get(i).replaceAll("true", "yes").replaceAll("false", "not");
					reached_values.set(i, s);
				}
			}
			
			fluents.add(new SharedFluentImp(
					new SharedVariableImp(c.getFunction().getName(),c.getFunction().getParams(),
							reached_values,c.getFunction().getValueTypes()), value, 
							c.getCondition()));
		}
	}

	@Override
	public int getTimeInstant() {
		return time_instant;
	}

	@Override
	public List<SharedFluent> getFluents() {
		return fluents;
	}
	
	@Override
	public String toString() {
		return "G" + time_instant + " " + fluents+" ";
	}
}
