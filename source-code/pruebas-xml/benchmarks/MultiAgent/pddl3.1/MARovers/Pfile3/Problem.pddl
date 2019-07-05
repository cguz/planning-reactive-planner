; spacecraft C
(define (problem pfile3)
(:domain MARovers)
(:objects
	C 			- spacecraft
	A B 		- rover
 	w1 w2 w3 	- waypoint
 	L 			- lander
	rock		- sample
)
(:init
	; Location rover A
 	; (= (loc A) w2)

	; Grid visible
	(vis_s w2 w1)
	(vis_s w1 w2)
	(vis_s w3 w1)
	(vis_s w1 w3)
	(vis_s w3 w2)
	(vis_s w2 w3)
	(vis_s w1 w1)
	(vis_s w2 w2)
	(vis_s w3 w3)

	(= (above C) w3)

	(not (link A w1 w1))
	(not (link A w1 w2))
	(not (link A w1 w3))
	(not (link A w2 w1))
	(not (link A w2 w2))
	(not (link A w2 w3))
	(not (link A w3 w1))
	(not (link A w3 w2))
	(not (link A w3 w3))

	(not (maps w1 w2))
	(not (maps w1 w3))
	(not (maps w2 w1))
	(not (maps w2 w3))
	(not (maps w3 w1))
	(not (maps w3 w2))
	(not (maps w1 w1))
	(not (maps w2 w2))
	(not (maps w3 w3))

)
(:global-goal (and
 	(link A w2 w3)
))
)
