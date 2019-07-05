package pelea.marp.common.rp.reactive.shared.teamwork.comparator;

import java.util.Comparator;

import pelea.marp.common.rp.reactive.shared.pddl.SharedFluent;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;

public class SharedTransitionComparator {

    
    /**
     * order by the time when reach the partial state
     */
    public Comparator<SharedTransition> TimeComparator = new Comparator<SharedTransition>() {

        public int compare(SharedTransition j1, SharedTransition j2) {
        	
	        if (j1.getTimeInstant() == j2.getTimeInstant())
	            return 0;
	        return (int) (j1.getTimeInstant()-j2.getTimeInstant());
        }
        
        @Override
		public String toString() {
			return "time of the transition";
		}
    };
    
    public Comparator<SharedTransition> CommittedComparator = new Comparator<SharedTransition>() {

        public int compare(SharedTransition j1, SharedTransition j2) {
        	
	        return j1.getCommitted().compareTo(j2.getCommitted());
	        
        }
        
        @Override
		public String toString() {
			return "committed of the transition";
		}
    };
    
    public Comparator<SharedTransition> RequesterComparator = new Comparator<SharedTransition>() {

        public int compare(SharedTransition j1, SharedTransition j2) {
        	
	        return j1.getRequester().compareTo(j2.getRequester());
	        
        }
        
        @Override
		public String toString() {
			return "requester of the transition";
		}
    };
    
    public Comparator<SharedFluent> FluentsComparator = new Comparator<SharedFluent>() {

        public int compare(SharedFluent j1, SharedFluent j2) {
        	
	        return j1.toString().compareTo(j2.toString());
	        
        }
        
        @Override
		public String toString() {
			return "requester of the transition";
		}
    };
    

	public Comparator<SharedTransition> getComparator(int i) {
		
		switch(i){
			case 0: return TimeComparator;
			case 1: return CommittedComparator;
			case 2: return RequesterComparator;
		}
		
		return TimeComparator;
	}
}
