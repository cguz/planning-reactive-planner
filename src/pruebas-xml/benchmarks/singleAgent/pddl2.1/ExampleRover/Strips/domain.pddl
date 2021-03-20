(define (domain ExampleRover)
(:requirements :typing)
(:types 
	agent sample    	- object
	rover			- agent	
	rock soil 		- sample
	waypoint lander)

(:predicates (at ?r - rover ?w - waypoint) 
             (can_traverse ?r - rover ?w - waypoint ?p - waypoint)
             (have_analysis ?sa - sample ?r - rover) 			; have rock and soil analysis 
             (communicated_sample_data ?sa - sample )			; communicated rock and soil data
	     	 (at_sample ?sa - sample ?w - waypoint)			; at rock and soil sample at location
             (at_lander ?l - lander ?w - waypoint)
             (visible ?w - waypoint ?p - waypoint)
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;    Rover's Actions    ;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action Navigate
:parameters 	(?r - rover ?w - waypoint ?p - waypoint) 
:precondition
	(and
	 	(can_traverse ?r ?w ?p)
	 	(at ?r ?w)            
	)
:effect
	(and
 		(at ?r ?p)
 		(not (at ?r ?w))
	)
)

(:action Sample
:parameters 	(?r - rover ?sa - sample ?p - waypoint)
:precondition
	(and
		(at ?r ?p)
		(at_sample ?sa ?p)
	)
:effect
 	(and
 		(have_analysis ?sa ?r)
 		(not (at_sample ?sa ?p))
	)
)

(:action Communicate
 :parameters 	(?r - rover ?sa - sample ?l - lander ?w - waypoint ?y - waypoint)
 :precondition
 	(and
 		(at ?r ?w)
		(at_lander ?l ?y)
		(have_analysis ?sa ?r)
		(visible ?w ?y)
        )
 :effect
 	(and
		(communicated_sample_data ?sa)
	)
)
)
