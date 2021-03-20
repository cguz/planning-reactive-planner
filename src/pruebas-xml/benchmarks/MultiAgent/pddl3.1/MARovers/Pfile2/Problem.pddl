; rover B
(define (problem pfile2)
(:domain MARovers)
(:objects
 	B 			- rover
	rock			- sample
	soil			- sample
 	L 			- lander
	S 			- spacecraft
 	w1 w2 w3 		- waypoint
)
(:init
	; Location rover B
 	(= (loc B) w2)
 	;(= (loc B) w3)

	; location Lander
 	(= (loc_l L) w2)

	; Grid visible
	; (vis w1 w1)
	; (vis w1 w2)
	; (vis w1 w3)
	; (vis w2 w1)
	; (vis w2 w2)
	; (vis w2 w3)
	; (vis w3 w1)
	; (vis w3 w2)
	; (vis w3 w3)
	(trans B)

	; Grid can traverse B
	(link B w1 w2)
	(link B w1 w3)
	(link B w2 w1)
	(link B w2 w3)
	(link B w3 w1)
	(link B w3 w2)
	
	(not (link B w1 w1))
	(not (link B w2 w2))
	(not (link B w3 w3))

	(not (have rock B))
	(not (have soil B))

	(= (comm rock) Not)
	(= (comm soil) Not)

	(loc_sample soil w1)
	(loc_sample rock w3)
	(not (loc_sample rock w1))
	(not (loc_sample rock w2))
	(not (loc_sample soil w3))
	(not (loc_sample soil w2))
)
(:global-goal (and
 	(= (comm soil) Yes)
 	(= (loc B) w3)
	;(= (comm rock) Yes)
))
)
