(define (domain MARovers)
(:requirements :typing :equality :fluents)
(:types 
	agent sample waypoint lander option - object
	rover spacecraft - agent 
	rock soil 	 - sample
)
(:constants
	Yes Not		- option
)
(:predicates
	  (link ?r - rover ?x - waypoint ?y - waypoint) ; multi value - w0 -> w1, w0 -> w2
	  (have ?sa - sample ?r - rover) 		 ; multi value
	  ;(vis ?w - waypoint ?p - waypoint)		 ; multi value	
	  (trans ?r - rover)
	  (loc_sample ?sa - sample ?w - waypoint); multi value
	  
	  ; spacecraft
	  (vis_s ?w - waypoint ?p - waypoint); multi value
	  (maps ?p - waypoint ?w - waypoint)	  ; multi value
)

(:functions
	  (comm ?sa - sample) - option
	  (loc ?x - rover) - waypoint
	  (loc_l ?x - lander) - waypoint
	  
	  ; spacecraft
	  (above ?d - spacecraft) - waypoint
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;    Rover's Actions    ;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action Navigate
 :parameters (?x - rover ?y - waypoint ?z - waypoint)
 :precondition (and 	
 			(link ?x ?y ?z)
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
		(loc_sample ?sa ?p)
	)
 :effect (and 
		(have ?sa ?x)
   		(not (loc_sample ?sa ?p))
	)
)

(:action Communicate
 :parameters (?r - rover ?sa - sample ?l - lander ?x - waypoint ?y - waypoint)
 :precondition (and 
		(= (loc ?r) ?x)
		(= (loc_l ?l) ?y)
		(have ?sa ?r)
		;(vis ?x ?y) 
		(trans ?r)
	)
 :effect (and 
	 	(assign (comm ?sa) Yes)
	)
)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;    Spacecraft's Actions    ;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action Fly
:parameters (?d - spacecraft ?w - waypoint ?p - waypoint) 
:precondition
	(and     
	 	(vis_s ?w ?p)
	 	(= (above ?d) ?w)
	)
:effect
	(and
		(assign (above ?d) ?p)
		(maps ?w ?p)
		(maps ?p ?w)
	)
)

(:action Send-msg-maps	; the spacecraft will send good plans for the rovers.
	:parameters 	(?d - spacecraft ?r - rover ?w - waypoint ?p - waypoint) 
	:precondition
		(and 
	 		(vis_s ?w ?p)
	 		(= (above ?d) ?w)
			;(= (loc ?r) ?p)
 			(maps ?w ?p)
 			(maps ?p ?w)
		)
	:effect
		(and
 			(link ?r ?w ?p)
 			(link ?r ?p ?w)
		)
)
)
