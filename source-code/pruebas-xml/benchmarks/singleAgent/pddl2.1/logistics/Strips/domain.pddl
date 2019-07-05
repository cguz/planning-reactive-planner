(define (domain logistics)
  (:requirements :strips :typing) 

(:types city place package vehicle - object
        truck airplane - vehicle
        airport location - place)
  
(:predicates (in-city ?loc - place ?city - city)
		 (at ?obj - vehicle ?loc - place)
		 (in ?pkg - package ?veh - (either place vehicle)))
  
(:action LoadTruck
   :parameters    (?pkg - package ?truck - truck ?loc - place)
   :precondition  (and (at ?truck ?loc) (in ?pkg ?loc))
   :effect        (and (not (in ?pkg ?loc)) (in ?pkg ?truck)))

(:action LoadAirplane
  :parameters   (?pkg - package ?airplane - airplane ?loc - place)
  :precondition (and (in ?pkg ?loc) (at ?airplane ?loc))
  :effect       (and (not (in ?pkg ?loc)) (in ?pkg ?airplane)))

(:action UnloadTruck
  :parameters   (?pkg - package ?truck - truck ?loc - place)
  :precondition (and (at ?truck ?loc) (in ?pkg ?truck))
  :effect       (and (not (in ?pkg ?truck)) (in ?pkg ?loc)))

(:action UnloadAirplane
  :parameters    (?pkg - package ?airplane - airplane ?loc - place)
  :precondition  (and (in ?pkg ?airplane) (at ?airplane ?loc))
  :effect        (and (not (in ?pkg ?airplane)) (in ?pkg ?loc)))

(:action DriveTruck
  :parameters (?truck - truck ?loc-from - place ?loc-to - place ?city - city)
  :precondition
   (and (at ?truck ?loc-from) (in-city ?loc-from ?city) (in-city ?loc-to ?city))
  :effect
   (and (not (at ?truck ?loc-from)) (at ?truck ?loc-to)))

(:action FlyAirplane
  :parameters (?airplane - airplane ?loc-from - airport ?loc-to - airport)
  :precondition
   (at ?airplane ?loc-from)
  :effect
   (and (not (at ?airplane ?loc-from)) (at ?airplane ?loc-to)))
)
