package pelea.marp.common.rp.reactive.shared.teamwork.comparator;

import java.util.Comparator;

import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;

public class SharedTransitionSorter implements Comparator<SharedTransition> {

    private final Comparator<SharedTransition>[] comparators;
    
    @SuppressWarnings("unchecked")
	public SharedTransitionSorter(Comparator<SharedTransition>... comp){
    	
        int i;
 
        this.comparators = new Comparator[comp.length];
 
		for (i = 0; i < comparators.length; i++)
            this.comparators[i] = comp[i];
    }
 
    public int compare(SharedTransition o1, SharedTransition o2){
    	int i, comp;
    	
    	for (i = 0; i < comparators.length; i++){
    		comp = comparators[i].compare(o1, o2);
    		if(comp!=0)
    			return comp;
    	}
    	
    	return 0;
    }
}
