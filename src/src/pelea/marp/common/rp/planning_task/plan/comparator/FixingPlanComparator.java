package pelea.marp.common.rp.planning_task.plan.comparator;

import java.util.Comparator;

import pelea.marp.common.rp.planning_task.plan.FixingPlan;
import pelea.marp.common.rp.reactive.enums.T_SOLUTION;

public class FixingPlanComparator {

	/**
	 * order by type of solution 
	 */
	public Comparator<FixingPlan> TypeSolutionComparator = new Comparator<FixingPlan>() {
		   
        public int compare(FixingPlan j1, FixingPlan j2) {
       
        	if ((j1.getTypeSolution()==T_SOLUTION.TOTAL && j2.getTypeSolution()==T_SOLUTION.TOTAL) 
                    || (j1.getTypeSolution()==T_SOLUTION.PARTIAL && j2.getTypeSolution()==T_SOLUTION.PARTIAL))
        		return 0;
        	return (j1.getTypeSolution()==T_SOLUTION.TOTAL)?1:-1;
        }

		@Override
		public String toString() {
			return "type of solution (total or partial)";
		}
 
    };
    
    /**
     * order by the time when reach the partial state
     */
    public Comparator<FixingPlan> TimeComparator = new Comparator<FixingPlan>() {

        public int compare(FixingPlan j1, FixingPlan j2) {
        	
	        if (j1.getTimeReachPS() == j2.getTimeReachPS())
	            return 0;
	        return (int) (j1.getTimeReachPS()-j2.getTimeReachPS());
        }
        
        @Override
		public String toString() {
			return "time (to reach the target)";
		}
    };
    
    /**
     * order by the number of fluents that need to be reached with this solution
     */
    public Comparator<FixingPlan> FluentsComparator = new Comparator<FixingPlan>() {

        public int compare(FixingPlan j1, FixingPlan j2) {
        	
	        if (j1.getFluents() == j2.getFluents())
	            return 0;
	        return j1.getFluents()-j2.getFluents();
        }
        
        @Override
		public String toString() {
			return "fluents (need to be reached)";
		}
    };

	public Comparator<FixingPlan> getComparator(int i) {
		
		switch(i){
			case 0: return TypeSolutionComparator;
			case 1: return TimeComparator;
			case 2: return FluentsComparator;
		}		
		return TypeSolutionComparator;
	}
}
