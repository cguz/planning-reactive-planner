(define (problem pfile1)
(:domain depot)
(:objects
 depot0 - depot
 distributor0 distributor1 - distributor
 truck0 truck1 - truck
 pallet0 pallet1 pallet2 - pallet
 crate0 crate1 - crate
 hoist0 hoist1 hoist2 - hoist
)
(:init
 (= (at crate0) distributor0)
 (= (on crate0) pallet1)
 (clear crate0)
 (= (at crate1) depot0)
 (= (on crate1) pallet0)
 (clear crate1)
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
 (clear pallet2)
)
(:global-goal (and
 (= (on crate0) pallet2)
 (= (on crate1) pallet1)
))
)
