# Reactive plan execution in multi-agent environments

[![Java](https://img.shields.io/badge/Java-8-red)](https://www.java.org/)

Author: Guzmán-Álvarez, César A. [@cguz](https://github.com/cguz)

Advisor: Eva Onaindia

The present repository contains the source code of the (single agent) Reactive Planner (RP) of the Ph.D. dissertation entitled [Reactive plan execution in multi-agent environments](https://riunet.upv.es/bitstream/handle/10251/120457/G%c3%bazman%20-%20Reactive%20plan%20execution%20in%20multi-agent%20environments.pdf?sequence=4&isAllowed=y).

The RP allows execution agents to reactively and collaboratively attend a plan failure during execution. Specifically, it is a collaborative RP that employs bounded-structures to respond in a timely fashion to a somewhat dynamic and unpredictable environment. 

The Multi-Agent RP allows execution agents to perform a general model, which enables a group of two agents to act coherently, overcoming the uncertainties of complex, dynamic environments to repair failures or inconsistent views of the world state.

## Abstract 

We propose an architecture that comprises a general reactive planning and execution model that endows a single-agent with monitoring and execution capabilities. The model also comprises a reactive planner module that provides the agent with fast responsiveness to recover from plan failures. Thus, the mission of an execution agent is to monitor, execute and repair a plan, if a failure occurs during the plan execution.

The reactive planner builds on a time-bounded search process that seeks a recovery plan in a solution space that encodes potential fixes for a failure. The agent generates the search space at runtime with an iterative time-bounded construction that guarantees that a solution space will always be available for attending an immediate plan failure. Thus, the only operation that needs to be done when a failure occurs is to search over the solution space until a recovery path is found. We evaluated the performance and reactiveness of our single-agent reactive planner by conducting two experiments. We have evaluated the reactiveness of the single-agent reactive planner when building solution spaces within a given time limit as well as the performance and quality of the found solutions when compared with two deliberative planning methods.

## Architecture

![image](https://user-images.githubusercontent.com/15159632/111842903-7a4ffd80-8900-11eb-8bcf-a55584bd9297.png)

## Source code

The folder "source-code" contains the code of the reactive planner in Java. It is executable only for a single agent. 

Please, if something fails or is missing email me : cguzman at cguz dot org.

## Publications

The whole work of this thesis have led to a series of publications, which we referenced throughout the memory. Of these, the
following stand out:

* Cesar Guzman, Pablo Castejon, Eva Onaindia, and Jeremy Frank. Reactive execution for solving plan failures in planning control applications. Journal of Integrated Computer-Aided Engineering, 22(4):343–360, 2015.
* Cesar Guzman, Pablo Castejon, Eva Onaindia, and Jeremy Frank. Robust plan execution in multi-agent environments. In 26th IEEE International Conference on Tools with Artificial Intelligence (ICTAI), pages 384–391, 2014.
* Cesar Guzman-Alvarez, Pablo Castejon, Eva Onaindia, and Jeremy Frank. Multi-agent reactive planning for solving plan failures. In Hybrid Artificial Intelligent Systems - 8th International Conference, HAIS 2013. Volume 8073 of Lecture Notes in Computer Science, pages 530–539. Springer, 2013.
* Thomas Reinbacher, Cesar Guzman. Template-Based Synthesis of Plan Execution Monitors. In Hybrid Artificial Intelligent Systems - 8th International Conference, HAIS 2013. Volume 8073 of Lecture Notes in Computer Science, pages 451–461. Springer, 2013.
* Cesar Guzman, Vidal Alcazar, David Prior, Eva Onaindia, Daniel Borrajo, Juan Fdez-Olivares, and Ezequiel Quintero. Pelea: a domain-independent architecture for planning, execution and learning. In ICAPS 6th Scheduling and Planning Applications woRKshop (SPARK), pages 38–45, 2012.
* Cesar Guzman-Alvarez, Vidal Alcazar, David Prior, Eva Onaindia, Daniel Borrajo, Juan Fdez-Olivares. Building a Domain-Independent Architecture for Planning, Learning and Execution (PELEA). 21th International Conference on Automated Planning and Scheduling (ICAPS) - Systems Demo. pages 27-30, Freiburg (Germany), 2011
* Ezequiel Quintero, Vidal Alcazar, Daniel Borrajo, Juan Fdez-Olivares, Fernando Fernandez, Angel Garcia-Olaya, Cesar Guzman-Alvarez, Eva Onaindia, David Prior. Autonomous Mobile Robot Control and Learning with PELEA Architecture. AAAI-11 Workshop on Automated Action Planning for Autonomous Mobile Robots (PAMR). pages 51-56, San francisco (USA), 2011.
* Antonio Garrido, Cesar Guzman, and Eva Onaindia. Anytime plan-adaptation for continuous planning. In 28th Workshop of the UK Planning and Scheduling Special Interest Group (PlanSIG'10), Brescia (Italia), 2010.
* PELEA: Planning, Learning and Execution Architecture. Vidal Alcazar, Cesar Guzman-Alvarez, David Prior, Daniel Borrajo, Luis Castillo, Eva Onaindia. In 28th Workshop of the UK Planning and Scheduling Special Interest Group (PlanSIG’10), Brescia (Italia), 2010.
