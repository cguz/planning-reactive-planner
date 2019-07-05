/** *  
 *  @name-file 		Trie.java
 *  @project   		ReactivePlanner - 28/06/2013
 *  @author    		Cesar Augusto Guzman Alvarez   
 *  @equipo 		anubis
 *  @universidad 	Universidad Politecnica de Valencia
 *  @departamento 	DSIC
 */
package pelea.marp.common.rp.reactive.trie;

import java.util.List;

import pelea.marp.service.rp.reactive.trie.Variable;

/**
 * @author Cesar Augusto Guzman Alvarez
 * 		   ReactivePlanner - 28/06/2013
 *
 */
public interface Trie {
	
	void insert(int indexState, List<Integer> firstNode, List<Variable> goal_state);
	
	int has(List<Variable> goal_state);
	
	void removePath(List<Variable> goal_state);
	
	int hasSubSet(List<Variable> goal_state, List<Integer> firstNode);
	
	void printGraphTrie(String domain, String problem);

	int getTotalTrieNodes();

	//Hashtable<Integer, List<DSCondition>> isContainsIn(List<Variable> goal_state, List<DSCondition> path, Hashtable<Integer, List<DSCondition>> additional_info);
}
