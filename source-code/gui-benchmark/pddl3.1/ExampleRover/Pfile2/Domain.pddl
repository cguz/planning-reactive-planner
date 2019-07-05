(define (domain ExampleRover)
(:requirements :typing :equality :fluents)
(:types 
	agent sample 	- object
	rover 		- agent 
	rock soil 	- sample
	waypoint lander option
)
(:constants
	Yes Not		- option
)
(:predicates
	  (can_traverse ?r - rover ?x - waypoint ?y - waypoint) ; multi value - w0 -> w1, w0 -> w2
	  (have_analysis ?sa - sample ?r - rover) 		; multi value
	  (visible ?w - waypoint ?p - waypoint)			; multi value	
	  (at_sample ?sa - sample ?w - waypoint)		; multi value
)

(:functions
	  (communicated_sample_data ?sa - sample) - option
	  (at ?x - rover) - waypoint
	  (at_lander ?x - lander) - waypoint
)

(:action Navigate
 :parameters (?x - rover ?y - waypoint ?z - waypoint)
 :precondition (and 	(can_traverse ?x ?y ?z)
   			(= (at ?x) ?y) 
		)
 :effect (and 
		(assign (at ?x) ?z)
	)
)

(:action Sample
 :parameters (?x - rover ?sa - sample ?p - waypoint)
 :precondition (and 
		(= (at ?x) ?p) 
		(at_sample ?sa ?p)
	)
 :effect (and 
		(have_analysis ?sa ?x)
   		(not (at_sample ?sa ?p))
	)
)

(:action Communicate
 :parameters (?r - rover ?sa - sample ?l - lander ?x - waypoint ?y - waypoint)
 :precondition (and 
		(= (at ?r) ?x)
		(= (at_lander ?l) ?y)
		(have_analysis ?sa ?r)
		(visible ?x ?y) )
 :effect (and 
	 	(assign (communicated_sample_data ?sa) Yes)
	)
)
)
