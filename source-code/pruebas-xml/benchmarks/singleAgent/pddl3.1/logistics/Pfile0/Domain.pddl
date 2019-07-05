(define (domain logistics)
  (:requirements :typing :equality :fluents) 
  (:types 
  	package location agent - object
  	truck - agent)

  (:predicates 	
		(link ?pos-from - location ?pos-to - location))

  (:functions 
	(p ?truck - truck) - location
	(pk ?package - package) - (either truck location)
  )

  (:action load
	:parameters(?obj - package ?truck - truck ?pos - location)
	:precondition
		(and 	(= (p ?truck) ?pos) 
			(= (pk ?obj) ?pos))
	:effect
		(and 	(assign (pk ?obj) ?truck)))

  (:action unload
	:parameters (?obj - package ?truck - truck ?pos - location)
	:precondition
		(and    (= (p ?truck) ?pos) 
			(= (pk ?obj) ?truck))
	:effect
		(and	(assign (pk ?obj) ?pos)))

 (:action drive
	:parameters (?truck - truck ?pos-from - location ?pos-to - location)
	:precondition
		(and 	
			(link ?pos-from ?pos-to)
			(= (p ?truck) ?pos-from))
	:effect
		(and 	(assign (p ?truck) ?pos-to)))

)
