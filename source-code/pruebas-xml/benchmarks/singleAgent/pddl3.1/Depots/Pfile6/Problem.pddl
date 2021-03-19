(define (problem pfile6)
(:domain depot)
(:objects
 depot0 - depot
 distributor0 distributor1 - distributor
 truck0 truck1 - truck
 pallet0 pallet1 pallet2 - pallet
 crate0 crate1 crate2 crate3 crate4 crate5 crate6 crate7 crate8 crate9 crate10 crate11 crate12 crate13 crate14 - crate
 hoist0 hoist1 hoist2 - hoist
)
(:init
 (= (at crate0) distributor1)
 (= (on crate0) pallet2)
 (not (clear crate0))
 (= (at crate1) depot0)
 (= (on crate1) pallet0)
 (not (clear crate1))
 (= (at crate2) distributor1)
 (= (on crate2) crate0)
 (not (clear crate2))
 (= (at crate3) distributor0)
 (= (on crate3) pallet1)
 (not (clear crate3))
 (= (at crate4) distributor0)
 (= (on crate4) crate3)
 (not (clear crate4))
 (= (at crate5) distributor1)
 (= (on crate5) crate2)
 (not (clear crate5))
 (= (at crate6) depot0)
 (= (on crate6) crate1)
 (not (clear crate6))
 (= (at crate7) distributor0)
 (= (on crate7) crate4)
 (not (clear crate7))
 (= (at crate8) distributor0)
 (= (on crate8) crate7)
 (not (clear crate8))
 (= (at crate9) distributor0)
 (= (on crate9) crate8)
 (not (clear crate9))
 (= (at crate10) distributor1)
 (= (on crate10) crate5)
 (clear crate10)
 (= (at crate11) depot0)
 (= (on crate11) crate6)
 (clear crate11)
 (= (at crate12) distributor0)
 (= (on crate12) crate9)
 (not (clear crate12))
 (= (at crate13) distributor0)
 (= (on crate13) crate12)
 (not (clear crate13))
 (= (at crate14) distributor0)
 (= (on crate14) crate13)
 (clear crate14)
 (= (at truck0) distributor1)
 (= (at truck1) depot0)
 (= (at hoist0) depot0)
 (clear hoist0)
 (= (at hoist1) distributor0)
 (clear hoist1)
 (= (at hoist2) distributor1)
 (clear hoist2)
 (= (at pallet0) depot0)
 (not (clear pallet0))
 (= (at pallet1) distributor0)
 (not (clear pallet1))
 (= (at pallet2) distributor1)
 (not (clear pallet2))
)
(:global-goal (and
 (= (on crate0) crate8)
 (= (on crate1) crate9)
 (= (on crate2) crate1)
 (= (on crate3) crate12)
 (= (on crate4) crate11)
 (= (on crate5) crate0)
 (= (on crate8) pallet0)
 (= (on crate9) pallet1)
 (= (on crate10) crate4)
 (= (on crate11) crate5)
 (= (on crate12) pallet2)
))
)