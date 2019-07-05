(define (problem pfile10)
(:domain driverlog)
(:objects
 driver1 driver2 - driver
 truck1 truck2 truck3 - truck
 package1 package2 package3 package4 package5 package6 - obj
 s0 s1 s2 s3 s4 s5 p1-0 p1-4 p1-5 p2-1 p2-3 p3-0 p3-1 p3-5 p4-5 - location
)
(:init
 (= (at driver1) s4)
 (= (at driver2) s0)
 (= (pos truck1) s0)
 (empty truck1)
 (= (pos truck2) s4)
 (empty truck2)
 (= (pos truck3) s5)
 (empty truck3)
 (= (in package1) s1)
 (= (in package2) s0)
 (= (in package3) s4)
 (= (in package4) s4)
 (= (in package5) s4)
 (= (in package6) s2)
 (not (link s0 s0))
 (not (path s0 s0))
 (link s0 s1)
 (not (path s0 s1))
 (link s0 s2)
 (not (path s0 s2))
 (link s0 s3)
 (not (path s0 s3))
 (link s0 s4)
 (not (path s0 s4))
 (link s0 s5)
 (not (path s0 s5))
 (not (link s0 p1-0))
 (path s0 p1-0)
 (not (link s0 p1-4))
 (not (path s0 p1-4))
 (not (link s0 p1-5))
 (not (path s0 p1-5))
 (not (link s0 p2-1))
 (not (path s0 p2-1))
 (not (link s0 p2-3))
 (not (path s0 p2-3))
 (not (link s0 p3-0))
 (path s0 p3-0)
 (not (link s0 p3-1))
 (not (path s0 p3-1))
 (not (link s0 p3-5))
 (not (path s0 p3-5))
 (not (link s0 p4-5))
 (not (path s0 p4-5))
 (link s1 s0)
 (not (path s1 s0))
 (not (link s1 s1))
 (not (path s1 s1))
 (link s1 s2)
 (not (path s1 s2))
 (link s1 s3)
 (not (path s1 s3))
 (link s1 s4)
 (not (path s1 s4))
 (link s1 s5)
 (not (path s1 s5))
 (not (link s1 p1-0))
 (path s1 p1-0)
 (not (link s1 p1-4))
 (path s1 p1-4)
 (not (link s1 p1-5))
 (path s1 p1-5)
 (not (link s1 p2-1))
 (path s1 p2-1)
 (not (link s1 p2-3))
 (not (path s1 p2-3))
 (not (link s1 p3-0))
 (not (path s1 p3-0))
 (not (link s1 p3-1))
 (path s1 p3-1)
 (not (link s1 p3-5))
 (not (path s1 p3-5))
 (not (link s1 p4-5))
 (not (path s1 p4-5))
 (link s2 s0)
 (not (path s2 s0))
 (link s2 s1)
 (not (path s2 s1))
 (not (link s2 s2))
 (not (path s2 s2))
 (link s2 s3)
 (not (path s2 s3))
 (not (link s2 s4))
 (not (path s2 s4))
 (not (link s2 s5))
 (not (path s2 s5))
 (not (link s2 p1-0))
 (not (path s2 p1-0))
 (not (link s2 p1-4))
 (not (path s2 p1-4))
 (not (link s2 p1-5))
 (not (path s2 p1-5))
 (not (link s2 p2-1))
 (path s2 p2-1)
 (not (link s2 p2-3))
 (path s2 p2-3)
 (not (link s2 p3-0))
 (not (path s2 p3-0))
 (not (link s2 p3-1))
 (not (path s2 p3-1))
 (not (link s2 p3-5))
 (not (path s2 p3-5))
 (not (link s2 p4-5))
 (not (path s2 p4-5))
 (link s3 s0)
 (not (path s3 s0))
 (link s3 s1)
 (not (path s3 s1))
 (link s3 s2)
 (not (path s3 s2))
 (not (link s3 s3))
 (not (path s3 s3))
 (link s3 s4)
 (not (path s3 s4))
 (link s3 s5)
 (not (path s3 s5))
 (not (link s3 p1-0))
 (not (path s3 p1-0))
 (not (link s3 p1-4))
 (not (path s3 p1-4))
 (not (link s3 p1-5))
 (not (path s3 p1-5))
 (not (link s3 p2-1))
 (not (path s3 p2-1))
 (not (link s3 p2-3))
 (path s3 p2-3)
 (not (link s3 p3-0))
 (path s3 p3-0)
 (not (link s3 p3-1))
 (path s3 p3-1)
 (not (link s3 p3-5))
 (path s3 p3-5)
 (not (link s3 p4-5))
 (not (path s3 p4-5))
 (link s4 s0)
 (not (path s4 s0))
 (link s4 s1)
 (not (path s4 s1))
 (not (link s4 s2))
 (not (path s4 s2))
 (link s4 s3)
 (not (path s4 s3))
 (not (link s4 s4))
 (not (path s4 s4))
 (link s4 s5)
 (not (path s4 s5))
 (not (link s4 p1-0))
 (not (path s4 p1-0))
 (not (link s4 p1-4))
 (path s4 p1-4)
 (not (link s4 p1-5))
 (not (path s4 p1-5))
 (not (link s4 p2-1))
 (not (path s4 p2-1))
 (not (link s4 p2-3))
 (not (path s4 p2-3))
 (not (link s4 p3-0))
 (not (path s4 p3-0))
 (not (link s4 p3-1))
 (not (path s4 p3-1))
 (not (link s4 p3-5))
 (not (path s4 p3-5))
 (not (link s4 p4-5))
 (path s4 p4-5)
 (link s5 s0)
 (not (path s5 s0))
 (link s5 s1)
 (not (path s5 s1))
 (not (link s5 s2))
 (not (path s5 s2))
 (link s5 s3)
 (not (path s5 s3))
 (link s5 s4)
 (not (path s5 s4))
 (not (link s5 s5))
 (not (path s5 s5))
 (not (link s5 p1-0))
 (not (path s5 p1-0))
 (not (link s5 p1-4))
 (not (path s5 p1-4))
 (not (link s5 p1-5))
 (path s5 p1-5)
 (not (link s5 p2-1))
 (not (path s5 p2-1))
 (not (link s5 p2-3))
 (not (path s5 p2-3))
 (not (link s5 p3-0))
 (not (path s5 p3-0))
 (not (link s5 p3-1))
 (not (path s5 p3-1))
 (not (link s5 p3-5))
 (path s5 p3-5)
 (not (link s5 p4-5))
 (path s5 p4-5)
 (not (link p1-0 s0))
 (path p1-0 s0)
 (not (link p1-0 s1))
 (path p1-0 s1)
 (not (link p1-0 s2))
 (not (path p1-0 s2))
 (not (link p1-0 s3))
 (not (path p1-0 s3))
 (not (link p1-0 s4))
 (not (path p1-0 s4))
 (not (link p1-0 s5))
 (not (path p1-0 s5))
 (not (link p1-0 p1-0))
 (not (path p1-0 p1-0))
 (not (link p1-0 p1-4))
 (not (path p1-0 p1-4))
 (not (link p1-0 p1-5))
 (not (path p1-0 p1-5))
 (not (link p1-0 p2-1))
 (not (path p1-0 p2-1))
 (not (link p1-0 p2-3))
 (not (path p1-0 p2-3))
 (not (link p1-0 p3-0))
 (not (path p1-0 p3-0))
 (not (link p1-0 p3-1))
 (not (path p1-0 p3-1))
 (not (link p1-0 p3-5))
 (not (path p1-0 p3-5))
 (not (link p1-0 p4-5))
 (not (path p1-0 p4-5))
 (not (link p1-4 s0))
 (not (path p1-4 s0))
 (not (link p1-4 s1))
 (path p1-4 s1)
 (not (link p1-4 s2))
 (not (path p1-4 s2))
 (not (link p1-4 s3))
 (not (path p1-4 s3))
 (not (link p1-4 s4))
 (path p1-4 s4)
 (not (link p1-4 s5))
 (not (path p1-4 s5))
 (not (link p1-4 p1-0))
 (not (path p1-4 p1-0))
 (not (link p1-4 p1-4))
 (not (path p1-4 p1-4))
 (not (link p1-4 p1-5))
 (not (path p1-4 p1-5))
 (not (link p1-4 p2-1))
 (not (path p1-4 p2-1))
 (not (link p1-4 p2-3))
 (not (path p1-4 p2-3))
 (not (link p1-4 p3-0))
 (not (path p1-4 p3-0))
 (not (link p1-4 p3-1))
 (not (path p1-4 p3-1))
 (not (link p1-4 p3-5))
 (not (path p1-4 p3-5))
 (not (link p1-4 p4-5))
 (not (path p1-4 p4-5))
 (not (link p1-5 s0))
 (not (path p1-5 s0))
 (not (link p1-5 s1))
 (path p1-5 s1)
 (not (link p1-5 s2))
 (not (path p1-5 s2))
 (not (link p1-5 s3))
 (not (path p1-5 s3))
 (not (link p1-5 s4))
 (not (path p1-5 s4))
 (not (link p1-5 s5))
 (path p1-5 s5)
 (not (link p1-5 p1-0))
 (not (path p1-5 p1-0))
 (not (link p1-5 p1-4))
 (not (path p1-5 p1-4))
 (not (link p1-5 p1-5))
 (not (path p1-5 p1-5))
 (not (link p1-5 p2-1))
 (not (path p1-5 p2-1))
 (not (link p1-5 p2-3))
 (not (path p1-5 p2-3))
 (not (link p1-5 p3-0))
 (not (path p1-5 p3-0))
 (not (link p1-5 p3-1))
 (not (path p1-5 p3-1))
 (not (link p1-5 p3-5))
 (not (path p1-5 p3-5))
 (not (link p1-5 p4-5))
 (not (path p1-5 p4-5))
 (not (link p2-1 s0))
 (not (path p2-1 s0))
 (not (link p2-1 s1))
 (path p2-1 s1)
 (not (link p2-1 s2))
 (path p2-1 s2)
 (not (link p2-1 s3))
 (not (path p2-1 s3))
 (not (link p2-1 s4))
 (not (path p2-1 s4))
 (not (link p2-1 s5))
 (not (path p2-1 s5))
 (not (link p2-1 p1-0))
 (not (path p2-1 p1-0))
 (not (link p2-1 p1-4))
 (not (path p2-1 p1-4))
 (not (link p2-1 p1-5))
 (not (path p2-1 p1-5))
 (not (link p2-1 p2-1))
 (not (path p2-1 p2-1))
 (not (link p2-1 p2-3))
 (not (path p2-1 p2-3))
 (not (link p2-1 p3-0))
 (not (path p2-1 p3-0))
 (not (link p2-1 p3-1))
 (not (path p2-1 p3-1))
 (not (link p2-1 p3-5))
 (not (path p2-1 p3-5))
 (not (link p2-1 p4-5))
 (not (path p2-1 p4-5))
 (not (link p2-3 s0))
 (not (path p2-3 s0))
 (not (link p2-3 s1))
 (not (path p2-3 s1))
 (not (link p2-3 s2))
 (path p2-3 s2)
 (not (link p2-3 s3))
 (path p2-3 s3)
 (not (link p2-3 s4))
 (not (path p2-3 s4))
 (not (link p2-3 s5))
 (not (path p2-3 s5))
 (not (link p2-3 p1-0))
 (not (path p2-3 p1-0))
 (not (link p2-3 p1-4))
 (not (path p2-3 p1-4))
 (not (link p2-3 p1-5))
 (not (path p2-3 p1-5))
 (not (link p2-3 p2-1))
 (not (path p2-3 p2-1))
 (not (link p2-3 p2-3))
 (not (path p2-3 p2-3))
 (not (link p2-3 p3-0))
 (not (path p2-3 p3-0))
 (not (link p2-3 p3-1))
 (not (path p2-3 p3-1))
 (not (link p2-3 p3-5))
 (not (path p2-3 p3-5))
 (not (link p2-3 p4-5))
 (not (path p2-3 p4-5))
 (not (link p3-0 s0))
 (path p3-0 s0)
 (not (link p3-0 s1))
 (not (path p3-0 s1))
 (not (link p3-0 s2))
 (not (path p3-0 s2))
 (not (link p3-0 s3))
 (path p3-0 s3)
 (not (link p3-0 s4))
 (not (path p3-0 s4))
 (not (link p3-0 s5))
 (not (path p3-0 s5))
 (not (link p3-0 p1-0))
 (not (path p3-0 p1-0))
 (not (link p3-0 p1-4))
 (not (path p3-0 p1-4))
 (not (link p3-0 p1-5))
 (not (path p3-0 p1-5))
 (not (link p3-0 p2-1))
 (not (path p3-0 p2-1))
 (not (link p3-0 p2-3))
 (not (path p3-0 p2-3))
 (not (link p3-0 p3-0))
 (not (path p3-0 p3-0))
 (not (link p3-0 p3-1))
 (not (path p3-0 p3-1))
 (not (link p3-0 p3-5))
 (not (path p3-0 p3-5))
 (not (link p3-0 p4-5))
 (not (path p3-0 p4-5))
 (not (link p3-1 s0))
 (not (path p3-1 s0))
 (not (link p3-1 s1))
 (path p3-1 s1)
 (not (link p3-1 s2))
 (not (path p3-1 s2))
 (not (link p3-1 s3))
 (path p3-1 s3)
 (not (link p3-1 s4))
 (not (path p3-1 s4))
 (not (link p3-1 s5))
 (not (path p3-1 s5))
 (not (link p3-1 p1-0))
 (not (path p3-1 p1-0))
 (not (link p3-1 p1-4))
 (not (path p3-1 p1-4))
 (not (link p3-1 p1-5))
 (not (path p3-1 p1-5))
 (not (link p3-1 p2-1))
 (not (path p3-1 p2-1))
 (not (link p3-1 p2-3))
 (not (path p3-1 p2-3))
 (not (link p3-1 p3-0))
 (not (path p3-1 p3-0))
 (not (link p3-1 p3-1))
 (not (path p3-1 p3-1))
 (not (link p3-1 p3-5))
 (not (path p3-1 p3-5))
 (not (link p3-1 p4-5))
 (not (path p3-1 p4-5))
 (not (link p3-5 s0))
 (not (path p3-5 s0))
 (not (link p3-5 s1))
 (not (path p3-5 s1))
 (not (link p3-5 s2))
 (not (path p3-5 s2))
 (not (link p3-5 s3))
 (path p3-5 s3)
 (not (link p3-5 s4))
 (not (path p3-5 s4))
 (not (link p3-5 s5))
 (path p3-5 s5)
 (not (link p3-5 p1-0))
 (not (path p3-5 p1-0))
 (not (link p3-5 p1-4))
 (not (path p3-5 p1-4))
 (not (link p3-5 p1-5))
 (not (path p3-5 p1-5))
 (not (link p3-5 p2-1))
 (not (path p3-5 p2-1))
 (not (link p3-5 p2-3))
 (not (path p3-5 p2-3))
 (not (link p3-5 p3-0))
 (not (path p3-5 p3-0))
 (not (link p3-5 p3-1))
 (not (path p3-5 p3-1))
 (not (link p3-5 p3-5))
 (not (path p3-5 p3-5))
 (not (link p3-5 p4-5))
 (not (path p3-5 p4-5))
 (not (link p4-5 s0))
 (not (path p4-5 s0))
 (not (link p4-5 s1))
 (not (path p4-5 s1))
 (not (link p4-5 s2))
 (not (path p4-5 s2))
 (not (link p4-5 s3))
 (not (path p4-5 s3))
 (not (link p4-5 s4))
 (path p4-5 s4)
 (not (link p4-5 s5))
 (path p4-5 s5)
 (not (link p4-5 p1-0))
 (not (path p4-5 p1-0))
 (not (link p4-5 p1-4))
 (not (path p4-5 p1-4))
 (not (link p4-5 p1-5))
 (not (path p4-5 p1-5))
 (not (link p4-5 p2-1))
 (not (path p4-5 p2-1))
 (not (link p4-5 p2-3))
 (not (path p4-5 p2-3))
 (not (link p4-5 p3-0))
 (not (path p4-5 p3-0))
 (not (link p4-5 p3-1))
 (not (path p4-5 p3-1))
 (not (link p4-5 p3-5))
 (not (path p4-5 p3-5))
 (not (link p4-5 p4-5))
 (not (path p4-5 p4-5))
)
(:global-goal (and
 (= (at driver1) s1)
 (= (at driver2) s0)
 (= (in package1) s5)
 (= (in package2) s1)
 (= (in package3) s5)
 (= (in package4) s1)
 (= (in package5) s3)
 (= (in package6) s2)
))
)
