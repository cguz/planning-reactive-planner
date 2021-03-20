package pelea.marp.service.rp.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import pelea.marp.common.rp.reactive.enums.Enums.ESTIMATED_TIME_STATUS;
import pelea.planning_task.common.pddl.DSAction;
import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;

public class EstimatedTime {
	
	private boolean debug = true;
	
	private double 	estimatedTime;
	private int 	planning_horizon;
	private int 	depth;
	private double 	est_branching_factor;
	private double 	est_total_nodes;
	private double 	est_evaluated_nodes;
	public Object[] parameters;
	private long 	timeFindingBestSize;
	
	protected ESTIMATED_TIME_STATUS status=ESTIMATED_TIME_STATUS.NOT_ESTIMATED;;
	

	/**
	 * 02/10/2013	
	 * @param estimatedTime
	 * @param l
	 * @param d
	 * @param estNodes 
	 * @param tree 
	 * @param b 
	 * @param parameters 
	 */
	public EstimatedTime(double estimatedTime, int l, int d, double b, double tree, double est_evaluated_nodes, Object[] parameters) {
		super();
		this.estimatedTime = estimatedTime;
		this.planning_horizon = l;
		this.depth = d;
		this.est_branching_factor=b;
		this.est_total_nodes=tree;
		this.est_evaluated_nodes=est_evaluated_nodes;
		this.parameters = parameters;
		
		if(est_evaluated_nodes!=-1 && parameters != null)
			status = ESTIMATED_TIME_STATUS.ESTIMATED;
		
		if(debug)
			System.out.println("("+l+","+d+") "+toString());
	}


	/**
	 * @return the estimatedTime
	 */
	public double getEstimatedTime() {
		return estimatedTime;
	}

	/**
	 * @return the window
	 */
	public int getPlanningHorizon() {
		return planning_horizon;
	}

	/**
	 * @return the height
	 */
	public int getDepth() {
		return depth;
	}

	public double getEst_Branching_factor() {
		return est_branching_factor;
	}


	public double getEst_Total_nodes() {
		return est_total_nodes;
	}


	public double getEst_evaluated_nodes() {
		return est_evaluated_nodes;
	}

	public long getTimeFindingBestSize() {
		return timeFindingBestSize;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String toString() {
		
		if(status==ESTIMATED_TIME_STATUS.NOT_ESTIMATED){
			return "[l="+ planning_horizon + ", d=" + depth + ","+ESTIMATED_TIME_STATUS.getName(status)+"]";
		}
		
		int RVar 		= ((Hashtable<DSVariables, String>)parameters[0]).size();
		int RvarRoot 	= (parameters[2]==null)?0:((ArrayList<DSCondition>)parameters[2]).size();
		int varRoot 	= ((ArrayList<DSCondition>)parameters[3]).size();
		// int producersT 	= (Integer)parameters[4];
		int producersU 	= (parameters[5]==null)?0:((HashSet<DSAction>)parameters[5]).size();
		
		
		return "[l="+ planning_horizon + ", d=" + depth + ", est time=" + estimatedTime
				 + ", est N=" + est_total_nodes
				 + ", rel.var.=" + RVar + ", rel.var. in root=" + RvarRoot
				 + ", f. in root=" + varRoot + ", uni.prod.=" + producersU+ ", est b=" + est_branching_factor
				 +","+ESTIMATED_TIME_STATUS.getName(status)+"]";
	}


	public void setDepth(int d) {
		depth = d;
	}

	public void setPlanningWindow(int size) {
		this.planning_horizon=size;
	}
	
	public void setTimeFindingBestSize(long timefindBestW) {
		timeFindingBestSize = timefindBestW;
	}
}
