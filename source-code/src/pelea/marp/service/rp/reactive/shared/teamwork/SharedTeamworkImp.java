package pelea.marp.service.rp.reactive.shared.teamwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pelea.marp.common.rp.reactive.shared.pddl.SharedFluent;
import pelea.marp.common.rp.reactive.shared.pddl.SharedPartialState;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTeamwork;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;
import pelea.marp.common.rp.reactive.shared.teamwork.comparator.SharedTransitionComparator;
import pelea.marp.service.rp.reactive.shared.pddl.SharedPartialStateImp;
import pelea.planning_task.common.pddl.DSCondition;

/**
 * @author cguzman
 * 
 */
public class SharedTeamworkImp implements SharedTeamwork {

	private static final long serialVersionUID = 781930027696836889L;
	
	private List<String> agents;
	private List<Integer> agents_total_requester;
	private List<Integer> agents_total_committed;
	
	private List<SharedTransition> transitions;
	private HashMap<Integer,HashSet<Integer>> transitions_time;  // <time, transitions>
	private HashMap<Integer,List<Integer>> transitions_committed; // <agent, transitions>
	private HashMap<Integer,HashSet<Integer>> committed_to; // <agent, agents>

	private double created_at_time; // time where the teamwork was created
	private double last_removed_time; // final time where the transitions are removed

	// thus, the other agent may reschedule its plan actions
	private List<Integer> reschedule=null;
	

	public SharedTeamworkImp(double initial_time) {

		default_constructor(initial_time);
		
	}
	
	private void default_constructor(double initial_time) {
		
		created_at_time   = initial_time;
		last_removed_time = 0;
		
		agents = new ArrayList<String>(10);
		agents_total_requester = new ArrayList<Integer>(10);
		agents_total_committed = new ArrayList<Integer>(10);
		transitions = new ArrayList<SharedTransition>(10);

		transitions_time = new HashMap<Integer,HashSet<Integer>>(10);
		transitions_committed = new HashMap<Integer,List<Integer>>(10);
		committed_to = new HashMap<Integer,HashSet<Integer>>(10);
		
	}

	public SharedTeamworkImp(SharedTransition transition, double initial_time) {

		default_constructor(initial_time);
		
		add(transition);
		
	}
	
	public SharedTeamworkImp(List<SharedTransition> transition, double initial_time) {

		default_constructor(initial_time);
		
		for(SharedTransition t: transition)
			add(t);
		
	}

	private void add(SharedTransition transition) {

		// we add the committed agent and get the index
		int index_committed = addAgents(transition.getCommitted());

		// we add the requester agent and get the index
		int index_requester = addAgents(transition.getRequester());
		
		addTransition(index_committed, index_requester, transition);
		
	}

	private void increaseCommitted(int i_a) {
		
		int value = agents_total_committed.get(i_a);
		
		agents_total_committed.set(i_a, ++value);
		
	}
	
	private void increaseRequester(int i_a) {
		
		int value = agents_total_requester.get(i_a);
		
		agents_total_requester.set(i_a, ++value);
		
	}
	
	private int addAgents(String agent) {
		
		if(!agents.contains(agent)){
			agents.add(agent);
			agents_total_requester.add(0);
			agents_total_committed.add(0);
		}
				
		return agents.indexOf(agent);
	}

	private void addTransition(int i_committed, int i_requester, SharedTransition transition) {

		int i_tran = addTransition(transition);
		
		boolean increase=false;
		if(!transitions_committed.containsKey(i_committed)){
			transitions_committed.put(i_committed, new ArrayList<Integer>(Arrays.asList(i_tran)));
			committed_to.put(i_committed, new HashSet<Integer>(Arrays.asList(agents.indexOf(transition.getRequester()))));
			increase = true;
		}else{
			if(transitions_committed.get(i_committed).indexOf(i_tran)==-1){
				transitions_committed.get(i_committed).add(i_tran);
				committed_to.get(i_committed).add(agents.indexOf(transition.getRequester()));
				increase = true;
			}
		}
		if(increase){
			increaseCommitted(i_committed);
			increaseRequester(i_requester);
		}
		
		if(!transitions_time.containsKey((int) transition.getTimeInstant()))
			transitions_time.put((int) transition.getTimeInstant(), new HashSet<Integer>(Arrays.asList(i_tran)));
		else
			transitions_time.get((int) transition.getTimeInstant()).add(i_tran);
		
	}
	
	private int addTransition(SharedTransition transition) {

		int i_tran = find(transition, transitions, transitions_time);
		
		if(i_tran < 0){
			i_tran=transitions.size();
			transitions.add(transition);
		}else{
			HashSet<SharedFluent> temp = new HashSet<SharedFluent>(transitions.get(i_tran).getFluentsToReach().getFluents());
			temp.addAll(transition.getFluentsToReach().getFluents());
			transitions.get(i_tran).getFluentsToReach().getFluents().clear();
			transitions.get(i_tran).getFluentsToReach().getFluents().addAll(temp);
		}
		
		return i_tran;
	}
	
	/**
	 * find a given transition 
	 * @param transition
	 * @param transitions_time2 
	 * @return the index of the transtion
	 */
	private int find(SharedTransition transition, List<SharedTransition> l_t, HashMap<Integer, HashSet<Integer>> time_tran) {
		HashSet<Integer> temp;
		
		SharedTransitionComparator comparator = new SharedTransitionComparator();
		
		if(time_tran.containsKey((int)transition.getTimeInstant())){
			temp = time_tran.get((int)transition.getTimeInstant());
			for(Integer t:temp){
				if(comparator.CommittedComparator.compare(l_t.get(t), transition)==0 && comparator.RequesterComparator.compare(l_t.get(t), transition)==0)
					return t;
			}
		}
		
		return -1;
		
	}

	/**
	 * find a given transition
	 * deberia estar ordenada 
	 * @param transition
	 * @return the index of the transtion
	
	private int find(SharedTransition transition) {
		int t_comp = 3;

		SharedTransitionComparator comparator = new SharedTransitionComparator();
		
		// saving the comparators
		@SuppressWarnings("unchecked")
		Comparator<SharedTransition>[] comparators = new Comparator[t_comp];
		
		for(int i=0;i<t_comp;++i){
			comparators[i]=comparator.getComparator(i);
		}
		
		return Collections.binarySearch(transitions, transition, new SharedTransitionSorter(comparators));
		
	}
	
	/**
	 * order by time a set of transitions
	 * @param list, set of transitions
	 * @return ordered set of transitions by time
	 
	private List<SharedTransition> order_by_time(List<Integer> list) {

		List<SharedTransition> tran = new ArrayList<SharedTransition>(list.size());
		for(Integer l :list){
			tran.add(transitions.get(l));
		}
		
		int t_comp = 1;

		SharedTransitionComparator comparator = new SharedTransitionComparator();
		
		// saving the comparators
		@SuppressWarnings("unchecked")
		Comparator<SharedTransition>[] comparators = new Comparator[t_comp];
		
		for(int i=0;i<t_comp;++i){
			comparators[i]=comparator.getComparator(i);
		}
		
		Collections.sort(tran, new SharedTransitionSorter(comparators));
		
		return tran;
	}*/
	
	@Override
	public int get_last_time_assisted_fluents(String agent){
		int relative_time = 0;
		for(SharedTransition t : transitions){
			if(t!=null && t.getRequester().equals(agent))
				if(t.getTimeInstant() > relative_time)
					relative_time = (int)t.getTimeInstant();
		}
		return relative_time;
	}
	
	@Override
	public int get_first_time_assisted_fluents(String agent){
		int relative_time = 0;
		for(SharedTransition t : transitions){
			if(t!=null && t.getRequester().equals(agent))
				if(t.getTimeInstant() > relative_time)
					return (int)t.getTimeInstant();
		}
		return relative_time;
	}
	
	@Override
	public int get_last_time_helper_fluents(String agent){
		int relative_time = 0;
		for(SharedTransition t : transitions){
			if(t!=null && t.getCommitted().equals(agent)){
				if(relative_time <= (int)t.getTimeInstant())
					relative_time = (int)t.getTimeInstant();
			}
		}
		return relative_time;
	}

	@Override
	public List<String> getAgents() {
		return new ArrayList<String>(agents);
	}
	
	@Override
	public List<Integer> get_reschedule() {
		return reschedule;
	}

	@Override
	public List<String> get_commitments(String agent) {
		
		int i_agent = agents.indexOf(agent);
		List<String> tran;
		
		if(i_agent != -1)
			if(committed_to.containsKey(i_agent)){
				tran = new ArrayList<String>(committed_to.get(i_agent).size());
				for(Integer l : committed_to.get(i_agent))
					tran.add(agents.get(l));
				
				return tran;
			}
		
		return new ArrayList<String>(0);
	}
	
	@Override
	public List<String> get_commitments(int t, String agent) {
		
		int i_agent = agents.indexOf(agent);
		List<String> tran;
		if(i_agent != -1)
			if(transitions_committed.containsKey(i_agent)){
				tran = new ArrayList<String>(transitions_committed.get(i_agent).size());
				for(Integer l : transitions_committed.get(i_agent)){
					if((int)transitions.get(l).getTimeInstant()==t && !transitions.get(l).is_reached())
						tran.add(transitions.get(l).getRequester());
				}
				return tran;
			}
		
		return new ArrayList<String>(0);
	}

	@Override
	public SharedPartialState get_all_helper_fluents(String agent) {

		int i_agent = agents.indexOf(agent);
		
		Set<SharedFluent> tran = null;
		int max = 0;

		if(i_agent != -1)
			if(transitions_committed.containsKey(i_agent)){
				tran = new HashSet<SharedFluent>(transitions_committed.get(i_agent).size());
				for(Integer l : transitions_committed.get(i_agent)){
					if(transitions.get(l).getTimeInstant()>max)
						max = (int) transitions.get(l).getTimeInstant();
					tran.addAll(transitions.get(l).getFluentsToReach().getFluents());
				}
			}
		
		SharedPartialState ps;
		
		if(tran!=null){
			ps = new SharedPartialStateImp(new ArrayList<DSCondition>(tran.size()), max, false);
			ps.getFluents().addAll(tran);
			return ps;
		}
		
		return new SharedPartialStateImp(new ArrayList<DSCondition>(0), max, false);
	}
	
	@Override
	public SharedPartialState[] get_helper_fluents(String agent) {
		int i;
		
		int last_time = get_last_time_helper_fluents(agent);
		
		SharedPartialState[] set_fluents = new SharedPartialState[last_time];
		for(i=0; i < set_fluents.length; ++i)
			set_fluents[i]=null;
		
		if(last_time != 0)
			for(SharedTransition t : transitions){
				if(t!=null && t.getCommitted().equals(agent)){
					i = (int)t.getTimeInstant()-1;
					if(set_fluents[i] == null)
						set_fluents[i] = new SharedPartialStateImp(t.getFluentsToReach().getFluents(), t.getFluentsToReach().getTimeInstant());
					else
						set_fluents[i].getFluents().addAll(t.getFluentsToReach().getFluents());
				}
			}
		
		return set_fluents;
	}
	
	@Override
	public SharedPartialState[] get_assisted_fluents(String agent) {
		int i;
		
		int last_time = get_last_time_assisted_fluents(agent);
		
		SharedPartialState[] set_fluents = new SharedPartialState[last_time];
		for(i=0; i < set_fluents.length; ++i)
			set_fluents[i]=null;
		
		for(SharedTransition t : transitions){
			if(t!=null && t.getRequester().equals(agent)){
				i = (int)t.getTimeInstant()-1;
				if(set_fluents[i] == null)
					set_fluents[i] = new SharedPartialStateImp(t.getFluentsToReach().getFluents(), t.getFluentsToReach().getTimeInstant());
				else
					set_fluents[i].getFluents().addAll(t.getFluentsToReach().getFluents());
			}
		}
		
		return set_fluents;
	}
	
	@Override
	public SharedPartialState[] get_assisted_fluents(String agent, int last_time) {
		int i;
		SharedPartialState[] set_fluents = new SharedPartialState[last_time];
		for(i=0; i < set_fluents.length; ++i)
			set_fluents[i]=null;
		
		for(SharedTransition t : transitions){
			if(t!=null && t.getRequester().equals(agent)){
				i = (int)t.getTimeInstant()-1;
				if(set_fluents[i] == null)
					set_fluents[i] = new SharedPartialStateImp(t.getFluentsToReach().getFluents(), t.getFluentsToReach().getTimeInstant());
				else
					set_fluents[i].getFluents().addAll(t.getFluentsToReach().getFluents());
			}
		}
		
		return set_fluents;
	}
	
	@Override
	public void update(SharedTransition transition){
		add(transition);
	}

	@Override
	public void update(int time, List<Integer> reschedul) {

		// saving the reschedul
		reschedule = reschedul;
		
		HashMap<Integer,HashSet<Integer>> temp = new HashMap<Integer,HashSet<Integer>>(transitions_time.size());
		HashSet<Integer> tran;
		
		int relative_time = (int) (time - created_at_time);
		int delay = 0;
		int act = 0;
		
		boolean first = true;
		
		int temp_time=0;
		
		for(Integer i: reschedul){
			if(i == -1){
				++delay; // increase the delay
				continue;
			}
			if(first){ // as it is the first delay, we update the created time
				created_at_time+=delay;
				delay=0;
				first=false;
			}
			++act;
			
			// if there is a transition
			temp_time = (int) (act + relative_time);
			if(transitions_time.containsKey(temp_time)){
				tran = transitions_time.get(temp_time);
				// update the time in the transitions
				for(Integer t: tran){
					transitions.set(t, 
						new SharedTransitionImp(temp_time+delay, 
						transitions.get(t).getCommitted(), transitions.get(t).getRequester(), 
						transitions.get(t).getFluentsToReach()));
				}
				
				// saving the transitions
				temp.put((Integer)(temp_time+delay), tran);				
			}
		}
		
		if(first)
			created_at_time+=delay;
		else{
			// for the remaining transition, we update them with the last delay.
			Set<Integer> l_tran_time = transitions_time.keySet();
			for(Integer l: l_tran_time){
				if(l > temp_time && delay > 0){
					tran = transitions_time.get(l);
					// update the time in the transitions
					for(Integer t: tran){
						transitions.set(t, 
							new SharedTransitionImp(l+delay, 
							transitions.get(t).getCommitted(), transitions.get(t).getRequester(), 
							transitions.get(t).getFluentsToReach()));
					}
					
					// saving the transitions
					temp.put((Integer)(l+delay), tran);		
				}
			}
		}
		
		// replacing the new transitions
		if(!temp.isEmpty()){
			for( Integer t : temp.keySet()){
				transitions_time.put(t, temp.get(t));
			}
		}
	}
	
	@Override
	public boolean remove(int time) {
		HashSet<Integer> l_t = new HashSet<Integer>();
		
		time = (int) (time - created_at_time);
		
		// selecting all the
		// for(int i=0;i<=time;++i){
		int i = time;
		
		if(transitions_time.containsKey((Integer)i)){
			l_t.addAll(transitions_time.get((Integer)i));
	
			for(Integer i_t: l_t){
				if(transitions.get(i_t)!=null){ // if the transition is not null - we remove the transition setting it to null
					if(!transitions.get(i_t).is_reached())
						remove_transition(i_t);
					transitions_time.get((Integer)i).remove(i_t);
					transitions.set(i_t, null); // removing the transition
				}
			}
			
			if(transitions_time.get((Integer)i).isEmpty())
				transitions_time.remove(i);
		}
		
		// }
		
		if(agents.isEmpty())
			return true;
		return false;
	}
	
	@Override
	public boolean reached(int time) {
		HashSet<Integer> l_t = new HashSet<Integer>();
		
		time = (int) (time - created_at_time);
		
		if(transitions_time.containsKey((Integer)time)){
			l_t.addAll(transitions_time.get((Integer)time));
	
			for(Integer i_t: l_t){
				// if the transition is not reached
				if(!transitions.get(i_t).is_reached()){
					transitions.get(i_t).change_reached();
					remove_transition(i_t);
				}
			}
			
			return true;
		}
		
		return false;
	}

	private void remove_transition(int tran) {
		
		remove_transition_committed(tran);
	
		remove_transition_requester(transitions.get(tran).getRequester());
		
	}

	private void remove_transition_committed(int tran) {
		
		String agent=null;
		try {agent = transitions.get(tran).getCommitted();
		} catch (NullPointerException e) {System.out.println("id_tran="+tran+" - transitions: "+toString());}
		
		int i_a = agents.indexOf(agent);
		
		if(i_a!=-1){
			int value = agents_total_committed.get(i_a);
			
			agents_total_committed.set(i_a,--value);
			
			if(agents_total_committed.get(i_a)==0){ // if the agent is not committed, we remove the committed transitions and the committed agents with
				transitions_committed.remove((Integer)i_a);
				committed_to.remove((Integer)i_a);
			}else{
				transitions_committed.get(i_a).remove((Integer)tran); // we remove the transition for which the agent is committed
				
				// we search if we need to remove the requester agent
				boolean remove_agent = true;
				if(!transitions_committed.get(i_a).isEmpty()){
					// for all transition where the agent is committed
					for(Integer t : transitions_committed.get(i_a)){
						// if the requester is the same, we don't remove the agent
						if(transitions.get(t).getRequester().equals(transitions.get(tran).getRequester())){
							remove_agent=false;
							break;
						}
					}
				}
				
				// if so, we remove the index of the requester agent
				if(remove_agent){
					committed_to.get(i_a).remove((Integer)agents.indexOf(transitions.get(tran).getRequester()));
				}
			}
			
			// we remove the agent
			removeAgent(i_a);
			
		}
	}
	
	private void remove_transition_requester(String agent) {
		
		int i_a = agents.indexOf(agent);
		
		if(i_a!=-1){
			int value = agents_total_requester.get(i_a);
			
			agents_total_requester.set(i_a,--value);
	
			removeAgent(i_a);
		}
	}

	private void removeAgent(int i_a) {
		// if the agent is not committed
		if(agents_total_committed.get(i_a)==0){
			// if the agent is not requester
			if(agents_total_requester.get(i_a)==0){
				int size = agents.size();
				
				agents.remove(i_a);
				agents_total_committed.remove(i_a);
				agents_total_requester.remove(i_a);
				
				for(int i=i_a+1; i<size-1;++i){
					transitions_committed.put(i-1, transitions_committed.get(i)); // <agent, transitions>
					transitions_committed.remove(i);					
					committed_to.put(i-1, committed_to.get(i));
					committed_to.remove(i);
					List<Integer> list = new ArrayList<Integer>(committed_to.get(i-1));
					for(int j=0; j<list.size(); ++j){
						if(list.get(j)>i_a)
							list.set(j, list.get(j)-1);
					}
					committed_to.replace((int)(i-1), new HashSet<Integer>(list));
				}
			}
		}
	}

	@Override
	public int get_executed_time(double time) {
		return (int)(time-created_at_time);
	}
	
	@Override
	public double get_last_removed_time() {
		return last_removed_time;
	}

	@Override
	public void set_last_removed_time(double last_removed_time) {
		this.last_removed_time = last_removed_time;
	}

	@Override
	public int get_created_at_time() {
		return (int) created_at_time;
	}
	
	@Override
	public String toString() {
		return "created at time ["+created_at_time+"], last removed time ["+last_removed_time+"] AG=" + agents + ", commitments: " + transitions+((reschedule!=null)?", reschedule: "+reschedule.toString():"")
				+", total committed by agents: "+agents_total_committed.toString()+", total requester by agents: "+agents_total_requester.toString();
	}

	@Override
	public List<SharedTransition> get_transtions() {
		return transitions;
	}
}
