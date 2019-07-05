/**
 * 
 */
package pelea.marp.service.rp.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pelea.marp.common.rp.reactive.shared.teamwork.SharedTeamwork;
import pelea.marp.common.rp.reactive.shared.teamwork.SharedTransition;
import pelea.marp.service.rp.reactive.shared.teamwork.SharedTeamworkImp;
import pelea.marp.service.rp.reactive.shared.teamwork.SharedTransitionImp;

/**
 * @author ceguzal
 *
 */
public class SharedTeamworkImpTest {

	/**
	 * Test method for {@link pelea.marp.service.rp.reactive.shared.teamwork.SharedTeamworkImp#SharedTeamworkImp(java.util.List, double)}.
	 */
	@Test
	public void testSharedTeamworkImpListOfSharedTransitionDouble() {
		
		SharedTeamwork tw = create_teamwork();

		List<String> agents = create_list("tru2", "apn1", "tru1");
	    assertEquals(agents, tw.getAgents());

	    // System.out.println(tw.get_fluents_to_reach("tru2"));
	    
	    // test_committed(tw, create_list("tru2", "apn1", "tru1"), create_list("tru2"), create_list("apn1"));
	}
	
	private void test_committed(SharedTeamwork tw, List<String> committed_p, List<String> committed_f, List<String> committed_a) {

	    assertEquals(committed_p, tw.get_commitments("P"));
	    assertEquals(committed_p, tw.get_commitments(3, "P"));
	    assertEquals(create_list(), tw.get_commitments(4, "P"));
	    

		
	    assertEquals(committed_f, tw.get_commitments("F"));
	    assertEquals(committed_f, tw.get_commitments(4, "F"));
	    assertEquals(create_list(), tw.get_commitments(7, "F"));
	    
	    
		
	    assertEquals(committed_a, tw.get_commitments("A"));
	    assertEquals(committed_a, tw.get_commitments(8, "A"));
	}
	
	/**
	 * Test method for {@link pelea.marp.service.rp.reactive.shared.teamwork.SharedTeamworkImp#remove(int)}.
	 */
	@Test
	public void testRemove() {
		SharedTeamwork tw = create_teamwork();
		
		// List<String> agents = create_list("P", "F", "A", "N");
		List<String> agents = create_list("tru2", "apn1", "tru1");

		System.out.println(tw);

		System.out.println(tw.remove(1));
		System.out.println(tw);
		System.out.println(tw.remove(2));
		System.out.println(tw);
		System.out.println(tw.remove(3));
		System.out.println(tw);
		System.out.println(tw.remove(4));
		System.out.println(tw);
		System.out.println(tw.remove(5));
		System.out.println(tw);
		System.out.println(tw.remove(6));
		System.out.println(tw);
		System.out.println(tw.remove(7));
		System.out.println(tw);
		System.out.println(tw.remove(8));
		System.out.println(tw);
		System.out.println(tw.remove(9));
		System.out.println(tw);
		System.out.println(tw.remove(10));
		System.out.println(tw);
		System.out.println(tw.remove(10));
		System.out.println(tw);
		
	    /*assertEquals(agents, tw.getAgents());
	    test_committed(tw, create_list("F", "A", "N"), create_list("A"), create_list("N"));
	    
	    tw.remove(3);
	    
	    agents.remove("P");
	    
	    assertEquals(agents, tw.getAgents());
	    test_committed(tw, create_list(), create_list("A"), create_list("N"));

	    tw.remove(4);
	    tw.remove(5);
	    
	    agents.remove("F");
	    
	    assertEquals(agents, tw.getAgents());
	    test_committed(tw, create_list(), create_list(), create_list("N"));
	    
	    tw.remove(8);

	    agents.remove("A");
	    agents.remove("N");
	    
	    assertEquals(agents, tw.getAgents());
	    test_committed(tw, create_list(), create_list(), create_list());*/
	    
	}

	private List<String> create_list(String ... name) {
		List<String> agents = new ArrayList<String>();
		for(String n: name)
			agents.add(n);
		return agents;
	}

	private SharedTeamwork create_teamwork() {
		List<SharedTransition> transitions = new ArrayList<SharedTransition>();
		
		/*
		// rescue domain
		transitions.add(new SharedTransitionImp(3, "P", "F", null));
		transitions.add(new SharedTransitionImp(3, "P", "A", null));
		transitions.add(new SharedTransitionImp(3, "P", "N", null));
		transitions.add(new SharedTransitionImp(4, "F", "A", null));
		transitions.add(new SharedTransitionImp(5, "A", "N", null));
		transitions.add(new SharedTransitionImp(8, "A", "N", null));
		*/
		
		/*
		// logistic domain
		 * 4.0 : tru2->apn1 [G4 [<in-obj21-apt2,yes>] ], 
		 * 5.0 : tru2->apn1 [G5 [<in-obj23-apt2,yes>] ], 
		 * 8.0 : apn1->tru1 [G8 [<in-obj21-apt1,yes>] ], 
		 * 9.0 : apn1->tru1 [G9 [<in-obj23-apt1,yes>]
		 * */
		transitions.add(new SharedTransitionImp(4, "tru2", "apn1", null));
		transitions.add(new SharedTransitionImp(5, "tru2", "apn1", null));
		transitions.add(new SharedTransitionImp(8, "apn1", "tru1", null));
		transitions.add(new SharedTransitionImp(9, "apn1", "tru1", null));
		
		
		return new SharedTeamworkImp(transitions, 0);
	}

}