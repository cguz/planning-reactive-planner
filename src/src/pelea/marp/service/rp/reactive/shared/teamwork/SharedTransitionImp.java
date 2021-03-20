package pelea.marp.service.rp.reactive.shared.teamwork;

import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;

/**
 * @author cguzman@dsic.upv.es 
 */
public class SharedTransitionImp implements SharedTransition {

	private static final long serialVersionUID = 781930027696836889L;
	
	private double time_instant;
	protected String committed;
	protected String requester;
	protected boolean status;
	private SharedPartialState fluents_to_reach;

	public SharedTransitionImp(double time_instant, String committed, String requester, SharedPartialState ps) {

		this.committed=committed;
		this.requester=requester;
		this.time_instant =time_instant;
		this.fluents_to_reach=ps;
		status=false;
		
	}

	@Override
	public double getTimeInstant() {
		return time_instant;
	}

	@Override
	public String getCommitted() {
		return committed;
	}

	@Override
	public String getRequester() {
		return requester;
	}
	
	@Override
	public String toString() {
		return time_instant + " : " + committed + "->"+requester+" ["+((fluents_to_reach!=null)?fluents_to_reach.toString():"")+"] reached ["+status+"]";
	}

	@Override
	public SharedPartialState getFluentsToReach() {
		return fluents_to_reach;
	}

	@Override
	public boolean is_reached() {
		return status;
	}

	@Override
	public void change_reached() {
		status = true;
	}
}
