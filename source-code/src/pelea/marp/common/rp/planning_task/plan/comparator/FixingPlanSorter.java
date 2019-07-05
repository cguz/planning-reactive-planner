package pelea.marp.common.rp.planning_task.plan.comparator;

import java.util.Comparator;

import pelea.marp.common.rp.planning_task.plan.FixingPlan;

public class FixingPlanSorter implements Comparator<FixingPlan> {

    private final Comparator<FixingPlan>[] comparators;
    
    @SuppressWarnings("unchecked")
	public FixingPlanSorter(Comparator<FixingPlan>... comp){
    	
        int i;
 
        this.comparators = new Comparator[comp.length];
 
		for (i = 0; i < comparators.length; i++)
            this.comparators[i] = comp[i];
    }
 
    public int compare(FixingPlan o1, FixingPlan o2){
    	int i, comp;
    	
    	for (i = 0; i < comparators.length; i++){
    		comp = comparators[i].compare(o1, o2);
    		if(comp!=0)
    			return comp;
    	}
    	
    	return 0;
    }
}
