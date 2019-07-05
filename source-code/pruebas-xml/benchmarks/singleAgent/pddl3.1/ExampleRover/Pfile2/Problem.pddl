(define (problem pfile2)
(:domain ExampleRover)
(:objects
 	B 			- rover
	r			- Rock
 	L 			- lander
 	w1 w2 w3 		- waypoint
)
(:init

	; B equipped_for_analysis r B
 	(= (loc B) w2)

	; L
 	(= (loc_lander L) w2)

	; Grid visible
	(vis w2 w1)
	(vis w1 w2)
	(vis w3 w1)
	(vis w1 w3)
	(vis w3 w2)
	(vis w2 w3)
	(vis w1 w1)
	(vis w2 w2)
	(vis w3 w3)

	(link B w1 w2)
	(link B w2 w1)
	(link B w1 w3)
	(link B w3 w1)
	(link B w2 w3)

	(not (link B w3 w2))
	(not (link B w1 w1))
	(not (link B w2 w2))
	(not (link B w3 w3))

	(not (have r B))

	(= (comm r w1) Not)
	(= (comm r w2) Not)
	(= (comm r w3) Not)

	(loc_rock r w1)
	(loc_rock r w3)
	(not (loc_rock r w2))
)
(:global-goal (and
 	(= (comm r w3) Yes)
 	(= (comm r w1) Yes)
	(= (loc B) w2)
))
)
