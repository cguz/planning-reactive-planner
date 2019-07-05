(define (domain logistics)
  (:requirements :strips :typing) 
  (:types 
	package location agent - object
  	truck - agent
  )
  
(:predicates (link ?loc - location ?city - location)
		 (p ?obj - truck ?loc - location)
		 (pk ?pkg - package ?veh - (either truck location)))
  
(:action load
   :parameters    (?pkg - package ?truck - truck ?loc - location)
   :precondition  (and (p ?truck ?loc) (pk ?pkg ?loc))
   :effect        (and (not (pk ?pkg ?loc)) (pk ?pkg ?truck)))

(:action unload
  :parameters   (?pkg - package ?truck - truck ?loc - location)
  :precondition (and (p ?truck ?loc) (pk ?pkg ?truck))
  :effect       (and (not (pk ?pkg ?truck)) (pk ?pkg ?loc)))

(:action drive
  :parameters (?truck - truck ?loc-from - location ?loc-to - location)
  :precondition
   (and (p ?truck ?loc-from) (link ?loc-from ?loc-to))
  :effect
   (and (not (p ?truck ?loc-from)) (p ?truck ?loc-to)))

)
