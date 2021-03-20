(define (problem pfile2)
(:domain depot)
(:objects
 depot0 - depot
 distributor0 distributor1 - distributor
 truck0 truck1 - truck
 pallet0 pallet1 pallet2 - pallet
 crate0 crate1 crate2 crate3 - crate
 hoist0 hoist1 hoist2 - hoist
)
(:init
 (= (at crate0) depot0)
 (= (on crate0) pallet0)
 (clear crate0)
 (= (at crate1) distributor1)
 (= (on crate1) pallet2)
 (not (clear crate1))
 (= (at crate2) distributor1)
 (= (on crate2) crate1)
 (clear crate2)
 (= (at crate3) distributor0)
 (= (on crate3) pallet1)
 (clear crate3)
 (= (at truck0) depot0)
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
 (= (on crate0) pallet2)
 (= (on crate1) crate3)
 (= (on crate2) pallet0)
 (= (on crate3) pallet1)
))
)
