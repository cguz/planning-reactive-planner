(define (domain depot)
(:requirements :typing :equality :fluents)
(:types place locatable - object
        depot distributor - place
        hoist truck surface - locatable
        crate pallet - surface)
(:constants IN-TRUCK - place)
(:predicates
  (clear ?x - (either surface hoist)))
(:functions
  (at ?x - locatable) - place
  (on ?x - crate) - locatable)
(:action Drive
 :parameters (?x - truck ?y ?z - place)
 :precondition (and (= (at ?x) ?y))
 :effect (and (assign (at ?x) ?z)))
(:action Load
 :parameters (?x - hoist ?y - crate ?z - truck ?p - place)
 :precondition (and (= (at ?x) ?p) (= (at ?z) ?p) (not (clear ?x))
                    (not (clear ?y)) (= (on ?y) ?x) (= (at ?y) ?p))
 :effect (and (clear ?x) (clear ?y) (assign (on ?y) ?z) (assign (at ?y) IN-TRUCK)))
(:action Unload
:parameters (?x - hoist ?y - crate ?z - truck ?p - place)
:precondition (and (= (at ?x) ?p) (= (at ?z) ?p) (= (at ?y) IN-TRUCK)
                   (= (on ?y) ?z) (clear ?x) (clear ?y))
:effect (and (assign (at ?y) ?p) (assign (on ?y) ?x)
             (not (clear ?y)) (not (clear ?x))))
(:action Lift
 :parameters (?x - hoist ?y - crate ?z - (either pallet crate) ?p - place)
 :precondition (and (= (at ?x) ?p) (clear ?x) (= (at ?y) ?p)
                    (= (on ?y) ?z) (clear ?y))
 :effect (and (assign (on ?y) ?x) (not (clear ?y)) (not (clear ?x))
              (clear ?z)))
(:action Drop
 :parameters (?x - hoist ?y - crate ?z - surface ?p - place)
 :precondition (and (= (at ?x) ?p) (= (at ?z) ?p) (clear ?z)
                    (= (on ?y) ?x) (not (clear ?y)) (not (clear ?x)))
 :effect (and (clear ?x) (clear ?y) (not (clear ?z))
              (assign (on ?y) ?z)))))
