; rover A
(define (problem pfile1)
(:domain MARovers)
(:objects
 	A 			- rover
	rock			- sample
	soil			- sample
 	L 			- lander
	S 			- spacecraft
 	w1 w2 w3 		- waypoint
)
(:init
	; Location rover A
 	(= (loc A) w2)
 	;(= (loc A) w1)

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
	(trans A)

	; Grid can traverse A
	(link A w1 w2)
	(link A w1 w3)
	(link A w2 w1)
	(link A w2 w3)
	(link A w3 w1)
	(link A w3 w2)
	
	(not (link A w1 w1))
	(not (link A w2 w2))
	(not (link A w3 w3))

	(not (have rock A))
	(not (have soil A))

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
 	(= (comm rock) Yes)
 	(= (loc A) w2)
 	;(= (comm soil) Yes)
))
)
