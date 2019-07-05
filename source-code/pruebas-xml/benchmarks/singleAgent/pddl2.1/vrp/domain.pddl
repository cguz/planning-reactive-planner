(define (domain VRP)
(:requirements :typing)
(:types locatable locations - object
	depot distributor - locations
        truck package - locatable)

(:predicates (at ?x - locatable ?y - locations) 
             (in ?x - package ?y - truck)
             (link ?x - locations ?y - locations)
             (available ?x - package)
             (in-use ?x - truck)
             (goal ?x - package ?y - locations))

(:functions
	(total-cost) - number
	(distance ?x - locations ?y - locations) - number
	(maxCargo ?x - truck) - number
	(cargo ?x - truck) - number
	(weight ?x - package) - number
)
	
(:action UseTruck
:parameters (?x - truck y? - locations) 
:precondition (and (at ?x ?y))
:effect (and  (in-use ?x - truck)))

(:action Load
:parameters (?y - package ?z - truck ?p - locations)
:precondition 	(and (in-use ?z) (available ?y) (at ?z ?p) (at ?y ?p) 
		(<= (+ (cargo ?z) (weight ?y)) (maxCargo ?z)))
:effect (and (in ?y ?z) (not (at ?y ? p)) (not (available ?y)) (= (cargo ?z) (+ (cargo ?z) (weight ?y))))) 

(:action UnLoad
:parameters (?y - package ?z - truck ?p - locations)
:precondition (and (at ?z ?p) (in ?y ?z) (goal ?y ?p))
:effect (and (not (in ?y ?z)) (available ?y) (at ?y ? p) (= (cargo ?z) (- (cargo ?z) (weight ?y)))))

(:action Drive
:parameters (?x - truck ?y - place ?z - place) 
:precondition (and (in-use ?t) (at ?x ?y) (link ?y ?z))
:effect (and (not (at ?x ?y)) (at ?x ?z)))

)
