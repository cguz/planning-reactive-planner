(define (problem pfile1)
(:domain ExampleRover)
(:objects
 	RoverA 			- rover
	rock			- Rock
 	lander 			- lander
 	w1 w2 w3 		- waypoint
)
(:init

	; RoverA equipped_for_analysis rock RoverA
 	(= (at RoverA) w2)

	; Lander
 	(= (at_lander lander) w2)

	; Grid visible
	(visible w2 w1)
	(visible w1 w2)
	(visible w3 w1)
	(visible w1 w3)
	(visible w3 w2)
	(visible w2 w3)
	(visible w1 w1)
	(visible w2 w2)
	(visible w3 w3)

	(can_traverse RoverA w1 w2)
	(can_traverse RoverA w2 w1)
	(can_traverse RoverA w1 w3)
	(can_traverse RoverA w3 w1)
	(can_traverse RoverA w2 w3)

	(not (can_traverse RoverA w3 w2))
	(not (can_traverse RoverA w1 w1))
	(not (can_traverse RoverA w2 w2))
	(not (can_traverse RoverA w3 w3))

	(not (have_analysis rock RoverA))

	(= (communicated_sample_data rock) Not)

	(at_sample rock w1)
	(at_sample rock w3)
	(not (at_sample rock w2))
)
(:global-goal (and
 	(= (communicated_sample_data rock) Yes)
))
)
