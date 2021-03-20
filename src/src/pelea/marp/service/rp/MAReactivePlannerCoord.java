/** *  
 *  @name-file 		bestGoalState.java
 *  @project   		AnyTimePlan_Adaptation - 10/01/2010
 *  @author    		Cesar Augusto Guzman Alvarez   
 *  @equipo 		zenshi
 *  @universidad 	Universidad Politecnica de Valencia
 *  @departamento 	DSIC
 */

package pelea.marp.service.rp;

import java.util.ArrayList;

import conf.xml2java.Conf;
import pelea.planning_task.common.plan.DSPlan;


/**
 * 
 * @author Cesar Augusto Guzman Alvarez - 10/01/2015
 * 
 */
public class MAReactivePlannerCoord extends MAReactivePlanner {
	
	// STARTING METHODS
	/**
	 * 16/01/2012 17:15:22	
	 * Constructor to initialize the planning problem struct and taking the decision
	 * @param domain_problem in pddl or xml
	 * @param plan action of the problem in xml
	 * @param configuration 
	 * @param path_results 
	 * @param id_sesion just to control the sesion in the web page
	 */
	public MAReactivePlannerCoord(String[] domain_problem, ArrayList<DSPlan> plan, Conf configuration, String path_results) {
		
		super(domain_problem, plan, configuration, path_results);
		
	}
}