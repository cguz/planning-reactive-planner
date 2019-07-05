(define (problem logistics-4-0)
(:domain logistics)
(:objects
 apn1 - airplane
 apt1 apt2 - airport
 pos2 pos1 - location
 cit2 cit1 - city
 tru2 tru1 - truck
 obj23 obj22 obj21 obj13 obj12 obj11 - package
)
(:shared-data
    ((in ?pkg - package) - (either place agent)) - 
(either tru2 tru1)
)
(:init (myAgent apn1)
 (= (at apn1) apt2)
 (= (in obj23) pos2)
 (= (in obj22) pos2)
 (= (in obj21) pos2)
 (= (in obj13) pos1)
 (= (in obj12) pos1)
 (= (in obj11) pos1)
 (not (in-city apt1 cit2))
 (in-city apt1 cit1)
 (in-city apt2 cit2)
 (not (in-city apt2 cit1))
 (in-city pos2 cit2)
 (not (in-city pos2 cit1))
 (not (in-city pos1 cit2))
 (in-city pos1 cit1)
)
(:global-goal (and
 (= (in obj11) apt1)
 (= (in obj23) pos1)
 (= (in obj13) apt1)
 (= (in obj21) pos1)
))
