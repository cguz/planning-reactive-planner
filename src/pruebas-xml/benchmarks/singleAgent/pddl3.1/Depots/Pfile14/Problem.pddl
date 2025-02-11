(define (problem pfile14)
(:domain depot)
(:objects
 depot0 depot1 depot2 - depot
 distributor0 distributor1 distributor2 - distributor
 truck0 truck1 - truck
 pallet0 pallet1 pallet2 pallet3 pallet4 pallet5 pallet6 pallet7 pallet8 pallet9 - pallet
 crate0 crate1 crate2 crate3 crate4 crate5 crate6 crate7 crate8 crate9 - crate
 hoist0 hoist1 hoist2 hoist3 hoist4 hoist5 - hoist
)
(:init
 (= (at crate0) distributor1)
 (= (on crate0) pallet8)
 (clear crate0)
 (= (at crate1) depot0)
 (= (on crate1) pallet9)
 (not (clear crate1))
 (= (at crate2) distributor0)
 (= (on crate2) pallet3)
 (not (clear crate2))
 (= (at crate3) distributor2)
 (= (on crate3) pallet6)
 (clear crate3)
 (= (at crate4) depot0)
 (= (on crate4) pallet0)
 (clear crate4)
 (= (at crate5) depot0)
 (= (on crate5) crate1)
 (clear crate5)
 (= (at crate6) distributor1)
 (= (on crate6) pallet4)
 (not (clear crate6))
 (= (at crate7) distributor1)
 (= (on crate7) crate6)
 (clear crate7)
 (= (at crate8) depot1)
 (= (on crate8) pallet1)
 (clear crate8)
 (= (at crate9) distributor0)
 (= (on crate9) crate2)
 (clear crate9)
 (= (at truck0) depot1)
 (= (at truck1) depot2)
 (= (at hoist0) depot0)
 (clear hoist0)
 (= (at hoist1) depot1)
 (clear hoist1)
 (= (at hoist2) depot2)
 (clear hoist2)
 (= (at hoist3) distributor0)
 (clear hoist3)
 (= (at hoist4) distributor1)
 (clear hoist4)
 (= (at hoist5) distributor2)
 (clear hoist5)
 (= (at pallet0) depot0)
 (not (clear pallet0))
 (= (at pallet1) depot1)
 (not (clear pallet1))
 (= (at pallet2) depot2)
 (clear pallet2)
 (= (at pallet3) distributor0)
 (not (clear pallet3))
 (= (at pallet4) distributor1)
 (not (clear pallet4))
 (= (at pallet5) distributor2)
 (clear pallet5)
 (= (at pallet6) distributor2)
 (not (clear pallet6))
 (= (at pallet7) depot1)
 (clear pallet7)
 (= (at pallet8) distributor1)
 (not (clear pallet8))
 (= (at pallet9) depot0)
 (not (clear pallet9))
)
(:global-goal (and
 (= (on crate1) pallet8)
 (= (on crate2) pallet3)
 (= (on crate4) pallet0)
 (= (on crate5) pallet5)
 (= (on crate6) pallet1)
 (= (on crate7) crate6)
 (= (on crate9) crate7)
))
)
