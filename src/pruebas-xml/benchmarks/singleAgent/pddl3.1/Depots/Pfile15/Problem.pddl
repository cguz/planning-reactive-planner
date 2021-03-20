(define (problem pfile15)
(:domain depot)
(:objects
 depot0 depot1 depot2 - depot
 distributor0 distributor1 distributor2 - distributor
 truck0 truck1 - truck
 pallet0 pallet1 pallet2 pallet3 pallet4 pallet5 pallet6 pallet7 pallet8 pallet9 - pallet
 crate0 crate1 crate2 crate3 crate4 crate5 crate6 crate7 crate8 crate9 crate10 crate11 crate12 crate13 crate14 - crate
 hoist0 hoist1 hoist2 hoist3 hoist4 hoist5 - hoist
)
(:init
 (= (at crate0) distributor2)
 (= (on crate0) pallet5)
 (not (clear crate0))
 (= (at crate1) distributor1)
 (= (on crate1) pallet4)
 (not (clear crate1))
 (= (at crate2) depot2)
 (= (on crate2) pallet8)
 (not (clear crate2))
 (= (at crate3) depot2)
 (= (on crate3) crate2)
 (not (clear crate3))
 (= (at crate4) depot1)
 (= (on crate4) pallet6)
 (clear crate4)
 (= (at crate5) distributor2)
 (= (on crate5) crate0)
 (not (clear crate5))
 (= (at crate6) depot1)
 (= (on crate6) pallet1)
 (not (clear crate6))
 (= (at crate7) depot1)
 (= (on crate7) crate6)
 (clear crate7)
 (= (at crate8) distributor0)
 (= (on crate8) pallet3)
 (clear crate8)
 (= (at crate9) distributor0)
 (= (on crate9) pallet7)
 (clear crate9)
 (= (at crate10) distributor1)
 (= (on crate10) crate1)
 (not (clear crate10))
 (= (at crate11) distributor2)
 (= (on crate11) crate5)
 (clear crate11)
 (= (at crate12) distributor1)
 (= (on crate12) crate10)
 (clear crate12)
 (= (at crate13) depot2)
 (= (on crate13) crate3)
 (clear crate13)
 (= (at crate14) distributor0)
 (= (on crate14) pallet9)
 (clear crate14)
 (= (at truck0) distributor1)
 (= (at truck1) distributor2)
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
 (clear pallet0)
 (= (at pallet1) depot1)
 (not (clear pallet1))
 (= (at pallet2) depot2)
 (clear pallet2)
 (= (at pallet3) distributor0)
 (not (clear pallet3))
 (= (at pallet4) distributor1)
 (not (clear pallet4))
 (= (at pallet5) distributor2)
 (not (clear pallet5))
 (= (at pallet6) depot1)
 (not (clear pallet6))
 (= (at pallet7) distributor0)
 (not (clear pallet7))
 (= (at pallet8) depot2)
 (not (clear pallet8))
 (= (at pallet9) distributor0)
 (not (clear pallet9))
)
(:global-goal (and
 (= (on crate0) crate8)
 (= (on crate1) crate10)
 (= (on crate2) pallet0)
 (= (on crate3) pallet1)
 (= (on crate4) crate7)
 (= (on crate5) pallet5)
 (= (on crate6) pallet6)
 (= (on crate7) pallet4)
 (= (on crate8) pallet7)
 (= (on crate9) crate4)
 (= (on crate10) crate11)
 (= (on crate11) crate9)
 (= (on crate12) crate5)
 (= (on crate13) pallet8)
 (= (on crate14) pallet9)
))
)
