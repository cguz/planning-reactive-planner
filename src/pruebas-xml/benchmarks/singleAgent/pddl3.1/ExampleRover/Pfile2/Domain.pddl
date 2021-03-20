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
	  (link ?r - rover ?x - waypoint ?y - waypoint) ; multi value - w0 -> w1, w0 -> w2
	  (have ?sa - sample ?r - rover) 		; multi value
	  (vis ?w - waypoint ?p - waypoint)			; multi value	
	  (loc_rock ?sa - sample ?w - waypoint)		; multi value
)

(:functions
	  (comm ?sa - sample ?y - waypoint) - option
	  (loc ?x - rover) - waypoint
	  (loc_lander ?x - lander) - waypoint
)

(:action Navigate
 :parameters (?x - rover ?y - waypoint ?z - waypoint)
 :precondition (and 	(link ?x ?y ?z)
   			(= (loc ?x) ?y) 
		)
 :effect (and 
		(assign (loc ?x) ?z)
	)
)

(:action Analyze
 :parameters (?x - rover ?sa - sample ?p - waypoint)
 :precondition (and 
		(= (loc ?x) ?p) 
		(loc_rock ?sa ?p)
	)
 :effect (and 
		(have ?sa ?x)
   		(not (loc_rock ?sa ?p))
	)
)

(:action Communicate
 :parameters (?r - rover ?sa - sample ?l - lander ?x - waypoint ?y - waypoint)
 :precondition (and 
		(= (loc ?r) ?x)
		(= (loc_lander ?l) ?y)
		(have ?sa ?r)
		(vis ?x ?y) )
 :effect (and 
	 	(assign (comm ?sa ?x) Yes)
	)
)
)
