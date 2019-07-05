(define (domain VRP)
(:requirements :typing)
(:types locatable locations - object
	depot distributor - locations
    truck package - locatable)

(:predicates (at ?x - locatable ?y - locations) 
             (in ?x - package ?y - truck)
             (link ?x - locations ?y - locations))


(:action Load
:parameters (?y - package ?z - truck ?p - locations)
:precondition 	(and (at ?z ?p) (at ?y ?p))
:effect (and (in ?y ?z) (not (at ?y ?p))))

(:action UnLoad
:parameters (?y - package ?z - truck ?p - locations)
:precondition (and (at ?z ?p) (in ?y ?z))
:effect (and (not (in ?y ?z)) (at ?y ?p)))

(:action Drive
:parameters (?x - truck ?y - locations ?z - locations) 
:precondition (and (at ?x ?y) (link ?y ?z))
:effect (and (not (at ?x ?y)) (at ?x ?z)))

)
