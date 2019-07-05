(define (domain MARovers)
(:requirements :typing)
(:types 
	agent sample waypoint lander - object
	rover spacecraft - agent
	rock soil 	 - sample)

(:predicates (loc ?r - rover ?w - waypoint) 
             (link ?r - rover ?w - waypoint ?p - waypoint)
             (have ?sa - sample ?r - rover) ; have rock and soil analysis 
             (comm ?sa - sample ) ; communicated rock and soil data
	     (loc_sample ?sa - sample ?w - waypoint)	; at rock and soil sample at location
             (loc_l ?l - lander ?w - waypoint)
             ; (vis ?w - waypoint ?p - waypoint)
             (trans ?r - agent)
             
             ; spacecraft
	     (above ?s - spacecraft ?w - waypoint)
	     (vis_s ?w - waypoint ?p - waypoint)
	     (maps ?p - waypoint ?w - waypoint)
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;    Rover's Actions    ;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action Navigate
:parameters 	(?r - rover ?w - waypoint ?p - waypoint) 
:precondition
	(and
	 	(link ?r ?w ?p)
	 	(loc ?r ?w)            
	)
:effect
	(and
 		(loc ?r ?p)
 		(not (loc ?r ?w))
	)
)

(:action Analyze
:parameters 	(?r - rover ?sa - sample ?p - waypoint)
:precondition
	(and
		(loc ?r ?p)
		(loc_sample ?sa ?p)
	)
:effect
 	(and
 		(have ?sa ?r)
 		(not (loc_sample ?sa ?p))
	)
)

(:action Communicate
 :parameters 	(?r - rover ?sa - sample ?l - lander ?w - waypoint ?y - waypoint)
 :precondition
 	(and
 		(loc ?r ?w)
		(loc_l ?l ?y)
		(have ?sa ?r)
		(trans ?r)
		; (vis ?w ?y)
        )
 :effect
 	(and
		(comm ?sa)
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
	 	(above ?d ?w)
	)
:effect
	(and
		(above ?d ?p)
		(maps ?w ?p)
		(maps ?p ?w)
		(not (above ?d ?w))
	)
)

; the spacecraft will send good plans for the rovers.
(:action Send-msg-maps
	:parameters (?d - spacecraft ?r - rover ?w - waypoint ?p - waypoint) 
	:precondition
		(and 
	 		(vis_s ?w ?p)
	 		(above ?d ?w)  
			; (loc ?r ?p)
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
