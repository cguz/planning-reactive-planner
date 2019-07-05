(define (domain DriverLog)
  (:requirements :typing) 
  (:types    location truck obj driver - object)
  (:predicates 
		(link ?x ?y - location) 
		(path ?x ?y - location)
		(empty ?v - truck)
		(at ?obj - driver ?loc - (either location truck))
		(pos ?t - truck ?loc - location)
		(in ?obj1 - obj ?obj - (either location truck))
)

(:action load
  :parameters
   (?obj - obj ?truck - truck  ?loc - location)
  :precondition
   (and (pos ?truck ?loc) (in ?obj ?loc))
  :effect
   (and (not (in ?obj ?loc)) (in ?obj ?truck)))

(:action unload
  :parameters
   (?obj - obj
    ?truck - truck
    ?loc - location)
  :precondition
   (and (pos ?truck ?loc) (in ?obj ?truck))
  :effect
   (and (not (in ?obj ?truck)) (in ?obj ?loc)))

(:action board
  :parameters
   (?driver - driver
    ?truck - truck
    ?loc - location)
  :precondition
   (and (pos ?truck ?loc) (at ?driver ?loc) (empty ?truck))
  :effect
   (and (not (at ?driver ?loc)) (at ?driver ?truck) (not (empty ?truck))))

(:action disembark
  :parameters
   (?driver - driver
    ?truck - truck
    ?loc - location)
  :precondition
   (and (pos ?truck ?loc) (at ?driver ?truck))
  :effect
   (and (not (at ?driver ?truck)) (at ?driver ?loc) (empty ?truck)))

(:action drive
  :parameters
   (?truck - truck
    ?loc-from - location
    ?loc-to - location
    ?driver - driver)
  :precondition
   (and (pos ?truck ?loc-from)
   (at ?driver ?truck) (link ?loc-from ?loc-to))
  :effect
   (and (not (pos ?truck ?loc-from)) (pos ?truck ?loc-to)))

(:action walk
  :parameters
   (?driver - driver
    ?loc-from - location
    ?loc-to - location)
  :precondition
   (and (at ?driver ?loc-from) (path ?loc-from ?loc-to))
  :effect
   (and (not (at ?driver ?loc-from)) (at ?driver ?loc-to)))

 
)
