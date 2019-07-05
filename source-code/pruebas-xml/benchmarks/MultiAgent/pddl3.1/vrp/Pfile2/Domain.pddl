(define (domain VRP)
(:requirements :typing)
(:types 
	locatable locations - object
	depot distributor - locations
	truck package - locatable)

(:predicates
	(link ?x - locations ?y - locations)
)

(:functions
	  (loc ?x - truck) - locations
	  (at ?x - package) - (either locations truck)
)


(:action Load
:parameters (?y - package ?z - truck ?p - locations)
:precondition 	(and 
	(= (loc ?z) ?p) 
	(= (at ?y) ?p))
:effect (and 
		(assign (at ?y) ?z)
))

(:action UnLoad
:parameters (?y - package ?z - truck ?p - locations)
:precondition (and 
	(= (loc ?z) ?p) 
	(= (at ?y) ?z))
:effect (and 
		(assign (at ?y) ?p)
))

(:action Drive
:parameters (?x - truck ?y - locations ?z - locations) 
:precondition (and 
	(= (loc ?x) ?y) (link ?y ?z))
:effect (and 
	(assign (loc ?x) ?z)
)))
