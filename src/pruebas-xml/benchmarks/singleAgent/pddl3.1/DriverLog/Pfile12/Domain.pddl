(define (domain DriverLog)
(:requirements :typing :equality :fluents)
(:types location truck obj driver - object)
(:predicates (link ?x ?y - location)
             (path ?x ?y - location)
             (empty ?v - truck))
(:functions  (at ?d - driver) - (either location truck)
          	 (pos ?t - truck) - location
          	 (in ?o - obj) - (either location truck))
(:action load
 :parameters    (?obj - obj ?truck - truck ?loc - location)
 :precondition  (and (= (pos ?truck) ?loc) (= (in ?obj) ?loc))
 :effect        (assign (in ?obj) ?truck))
(:action unload
 :parameters   (?obj - obj ?truck - truck ?loc - location)
 :precondition (and (= (pos ?truck) ?loc) (= (in ?obj) ?truck))
 :effect       (assign (in ?obj) ?loc))
(:action board
 :parameters   (?driver - driver ?truck - truck ?loc - location)
 :precondition (and (= (pos ?truck) ?loc)
                    (= (at ?driver) ?loc) (empty ?truck))
 :effect       (and (assign (at ?driver) ?truck) (not (empty ?truck))))
(:action disembark
 :parameters   (?driver - driver ?truck - truck ?loc - location)
 :precondition (and (= (pos ?truck) ?loc)
                    (= (at ?driver) ?truck))
 :effect       (and (assign (at ?driver) ?loc) (empty ?truck)))
(:action drive
 :parameters (?truck - truck ?loc-from - location ?loc-to - location ?driver - driver)
 :precondition (and (= (pos ?truck) ?loc-from)
               (= (at ?driver) ?truck) (link ?loc-from ?loc-to))
 :effect (assign (pos ?truck) ?loc-to))
(:action walk
 :parameters (?driver - driver ?loc-from - location ?loc-to - location)
 :precondition (and (= (at ?driver) ?loc-from)
                    (path ?loc-from ?loc-to))
 :effect       (assign (at ?driver) ?loc-to)))
