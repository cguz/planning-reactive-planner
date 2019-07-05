(define (problem pfile9)
(:domain driverlog)
(:objects
 driver1 driver2 - driver
 truck1 truck2 truck3 - truck
 package1 package2 package3 package4 package5 package6 - obj
 s0 s1 s2 s3 s4 p0-1 p1-2 p1-3 p3-2 p3-4 p4-0 - location
)
(:init
 (= (at driver1) s4)
 (= (at driver2) s1)
 (= (pos truck1) s2)
 (empty truck1)
 (= (pos truck2) s0)
 (empty truck2)
 (= (pos truck3) s3)
 (empty truck3)
 (= (in package1) s2)
 (= (in package2) s1)
 (= (in package3) s3)
 (= (in package4) s0)
 (= (in package5) s1)
 (= (in package6) s1)
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
 (not (link s0 p0-1))
 (path s0 p0-1)
 (not (link s0 p1-2))
 (not (path s0 p1-2))
 (not (link s0 p1-3))
 (not (path s0 p1-3))
 (not (link s0 p3-2))
 (not (path s0 p3-2))
 (not (link s0 p3-4))
 (not (path s0 p3-4))
 (not (link s0 p4-0))
 (path s0 p4-0)
 (link s1 s0)
 (not (path s1 s0))
 (not (link s1 s1))
 (not (path s1 s1))
 (link s1 s2)
 (not (path s1 s2))
 (not (link s1 s3))
 (not (path s1 s3))
 (not (link s1 s4))
 (not (path s1 s4))
 (not (link s1 p0-1))
 (path s1 p0-1)
 (not (link s1 p1-2))
 (path s1 p1-2)
 (not (link s1 p1-3))
 (path s1 p1-3)
 (not (link s1 p3-2))
 (not (path s1 p3-2))
 (not (link s1 p3-4))
 (not (path s1 p3-4))
 (not (link s1 p4-0))
 (not (path s1 p4-0))
 (link s2 s0)
 (not (path s2 s0))
 (link s2 s1)
 (not (path s2 s1))
 (not (link s2 s2))
 (not (path s2 s2))
 (link s2 s3)
 (not (path s2 s3))
 (link s2 s4)
 (not (path s2 s4))
 (not (link s2 p0-1))
 (not (path s2 p0-1))
 (not (link s2 p1-2))
 (path s2 p1-2)
 (not (link s2 p1-3))
 (not (path s2 p1-3))
 (not (link s2 p3-2))
 (path s2 p3-2)
 (not (link s2 p3-4))
 (not (path s2 p3-4))
 (not (link s2 p4-0))
 (not (path s2 p4-0))
 (link s3 s0)
 (not (path s3 s0))
 (not (link s3 s1))
 (not (path s3 s1))
 (link s3 s2)
 (not (path s3 s2))
 (not (link s3 s3))
 (not (path s3 s3))
 (link s3 s4)
 (not (path s3 s4))
 (not (link s3 p0-1))
 (not (path s3 p0-1))
 (not (link s3 p1-2))
 (not (path s3 p1-2))
 (not (link s3 p1-3))
 (path s3 p1-3)
 (not (link s3 p3-2))
 (path s3 p3-2)
 (not (link s3 p3-4))
 (path s3 p3-4)
 (not (link s3 p4-0))
 (not (path s3 p4-0))
 (link s4 s0)
 (not (path s4 s0))
 (not (link s4 s1))
 (not (path s4 s1))
 (link s4 s2)
 (not (path s4 s2))
 (link s4 s3)
 (not (path s4 s3))
 (not (link s4 s4))
 (not (path s4 s4))
 (not (link s4 p0-1))
 (not (path s4 p0-1))
 (not (link s4 p1-2))
 (not (path s4 p1-2))
 (not (link s4 p1-3))
 (not (path s4 p1-3))
 (not (link s4 p3-2))
 (not (path s4 p3-2))
 (not (link s4 p3-4))
 (path s4 p3-4)
 (not (link s4 p4-0))
 (path s4 p4-0)
 (not (link p0-1 s0))
 (path p0-1 s0)
 (not (link p0-1 s1))
 (path p0-1 s1)
 (not (link p0-1 s2))
 (not (path p0-1 s2))
 (not (link p0-1 s3))
 (not (path p0-1 s3))
 (not (link p0-1 s4))
 (not (path p0-1 s4))
 (not (link p0-1 p0-1))
 (not (path p0-1 p0-1))
 (not (link p0-1 p1-2))
 (not (path p0-1 p1-2))
 (not (link p0-1 p1-3))
 (not (path p0-1 p1-3))
 (not (link p0-1 p3-2))
 (not (path p0-1 p3-2))
 (not (link p0-1 p3-4))
 (not (path p0-1 p3-4))
 (not (link p0-1 p4-0))
 (not (path p0-1 p4-0))
 (not (link p1-2 s0))
 (not (path p1-2 s0))
 (not (link p1-2 s1))
 (path p1-2 s1)
 (not (link p1-2 s2))
 (path p1-2 s2)
 (not (link p1-2 s3))
 (not (path p1-2 s3))
 (not (link p1-2 s4))
 (not (path p1-2 s4))
 (not (link p1-2 p0-1))
 (not (path p1-2 p0-1))
 (not (link p1-2 p1-2))
 (not (path p1-2 p1-2))
 (not (link p1-2 p1-3))
 (not (path p1-2 p1-3))
 (not (link p1-2 p3-2))
 (not (path p1-2 p3-2))
 (not (link p1-2 p3-4))
 (not (path p1-2 p3-4))
 (not (link p1-2 p4-0))
 (not (path p1-2 p4-0))
 (not (link p1-3 s0))
 (not (path p1-3 s0))
 (not (link p1-3 s1))
 (path p1-3 s1)
 (not (link p1-3 s2))
 (not (path p1-3 s2))
 (not (link p1-3 s3))
 (path p1-3 s3)
 (not (link p1-3 s4))
 (not (path p1-3 s4))
 (not (link p1-3 p0-1))
 (not (path p1-3 p0-1))
 (not (link p1-3 p1-2))
 (not (path p1-3 p1-2))
 (not (link p1-3 p1-3))
 (not (path p1-3 p1-3))
 (not (link p1-3 p3-2))
 (not (path p1-3 p3-2))
 (not (link p1-3 p3-4))
 (not (path p1-3 p3-4))
 (not (link p1-3 p4-0))
 (not (path p1-3 p4-0))
 (not (link p3-2 s0))
 (not (path p3-2 s0))
 (not (link p3-2 s1))
 (not (path p3-2 s1))
 (not (link p3-2 s2))
 (path p3-2 s2)
 (not (link p3-2 s3))
 (path p3-2 s3)
 (not (link p3-2 s4))
 (not (path p3-2 s4))
 (not (link p3-2 p0-1))
 (not (path p3-2 p0-1))
 (not (link p3-2 p1-2))
 (not (path p3-2 p1-2))
 (not (link p3-2 p1-3))
 (not (path p3-2 p1-3))
 (not (link p3-2 p3-2))
 (not (path p3-2 p3-2))
 (not (link p3-2 p3-4))
 (not (path p3-2 p3-4))
 (not (link p3-2 p4-0))
 (not (path p3-2 p4-0))
 (not (link p3-4 s0))
 (not (path p3-4 s0))
 (not (link p3-4 s1))
 (not (path p3-4 s1))
 (not (link p3-4 s2))
 (not (path p3-4 s2))
 (not (link p3-4 s3))
 (path p3-4 s3)
 (not (link p3-4 s4))
 (path p3-4 s4)
 (not (link p3-4 p0-1))
 (not (path p3-4 p0-1))
 (not (link p3-4 p1-2))
 (not (path p3-4 p1-2))
 (not (link p3-4 p1-3))
 (not (path p3-4 p1-3))
 (not (link p3-4 p3-2))
 (not (path p3-4 p3-2))
 (not (link p3-4 p3-4))
 (not (path p3-4 p3-4))
 (not (link p3-4 p4-0))
 (not (path p3-4 p4-0))
 (not (link p4-0 s0))
 (path p4-0 s0)
 (not (link p4-0 s1))
 (not (path p4-0 s1))
 (not (link p4-0 s2))
 (not (path p4-0 s2))
 (not (link p4-0 s3))
 (not (path p4-0 s3))
 (not (link p4-0 s4))
 (path p4-0 s4)
 (not (link p4-0 p0-1))
 (not (path p4-0 p0-1))
 (not (link p4-0 p1-2))
 (not (path p4-0 p1-2))
 (not (link p4-0 p1-3))
 (not (path p4-0 p1-3))
 (not (link p4-0 p3-2))
 (not (path p4-0 p3-2))
 (not (link p4-0 p3-4))
 (not (path p4-0 p3-4))
 (not (link p4-0 p4-0))
 (not (path p4-0 p4-0))
)
(:global-goal (and
 (= (at driver1) s3)
 (= (at driver2) s4)
 (= (pos truck1) s3)
 (= (pos truck2) s3)
 (= (in package1) s3)
 (= (in package2) s2)
 (= (in package3) s1)
 (= (in package4) s0)
 (= (in package5) s1)
 (= (in package6) s1)
))
)
