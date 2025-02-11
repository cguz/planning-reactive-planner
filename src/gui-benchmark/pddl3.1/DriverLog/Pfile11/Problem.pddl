(define (problem pfile11)
(:domain driverlog)
(:objects
 driver1 driver2 - driver
 truck1 truck2 truck3 - truck
 package1 package2 package3 package4 package5 package6 - obj
 s0 s1 s2 s3 s4 s5 s6 p0-3 p1-0 p1-5 p2-3 p2-6 p3-4 p4-0 p4-1 p4-2 p5-0 p6-3 - location
)
(:init
 (= (at driver1) s6)
 (= (at driver2) s0)
 (= (pos truck1) s0)
 (empty truck1)
 (= (pos truck2) s2)
 (empty truck2)
 (= (pos truck3) s2)
 (empty truck3)
 (= (in package1) s6)
 (= (in package2) s2)
 (= (in package3) s3)
 (= (in package4) s0)
 (= (in package5) s5)
 (= (in package6) s5)
 (not (link s0 s0))
 (not (path s0 s0))
 (link s0 s1)
 (not (path s0 s1))
 (link s0 s2)
 (not (path s0 s2))
 (not (link s0 s3))
 (not (path s0 s3))
 (link s0 s4)
 (not (path s0 s4))
 (link s0 s5)
 (not (path s0 s5))
 (link s0 s6)
 (not (path s0 s6))
 (not (link s0 p0-3))
 (path s0 p0-3)
 (not (link s0 p1-0))
 (path s0 p1-0)
 (not (link s0 p1-5))
 (not (path s0 p1-5))
 (not (link s0 p2-3))
 (not (path s0 p2-3))
 (not (link s0 p2-6))
 (not (path s0 p2-6))
 (not (link s0 p3-4))
 (not (path s0 p3-4))
 (not (link s0 p4-0))
 (path s0 p4-0)
 (not (link s0 p4-1))
 (not (path s0 p4-1))
 (not (link s0 p4-2))
 (not (path s0 p4-2))
 (not (link s0 p5-0))
 (path s0 p5-0)
 (not (link s0 p6-3))
 (not (path s0 p6-3))
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
 (link s1 s6)
 (not (path s1 s6))
 (not (link s1 p0-3))
 (not (path s1 p0-3))
 (not (link s1 p1-0))
 (path s1 p1-0)
 (not (link s1 p1-5))
 (path s1 p1-5)
 (not (link s1 p2-3))
 (not (path s1 p2-3))
 (not (link s1 p2-6))
 (not (path s1 p2-6))
 (not (link s1 p3-4))
 (not (path s1 p3-4))
 (not (link s1 p4-0))
 (not (path s1 p4-0))
 (not (link s1 p4-1))
 (path s1 p4-1)
 (not (link s1 p4-2))
 (not (path s1 p4-2))
 (not (link s1 p5-0))
 (not (path s1 p5-0))
 (not (link s1 p6-3))
 (not (path s1 p6-3))
 (link s2 s0)
 (not (path s2 s0))
 (link s2 s1)
 (not (path s2 s1))
 (not (link s2 s2))
 (not (path s2 s2))
 (not (link s2 s3))
 (not (path s2 s3))
 (link s2 s4)
 (not (path s2 s4))
 (link s2 s5)
 (not (path s2 s5))
 (link s2 s6)
 (not (path s2 s6))
 (not (link s2 p0-3))
 (not (path s2 p0-3))
 (not (link s2 p1-0))
 (not (path s2 p1-0))
 (not (link s2 p1-5))
 (not (path s2 p1-5))
 (not (link s2 p2-3))
 (path s2 p2-3)
 (not (link s2 p2-6))
 (path s2 p2-6)
 (not (link s2 p3-4))
 (not (path s2 p3-4))
 (not (link s2 p4-0))
 (not (path s2 p4-0))
 (not (link s2 p4-1))
 (not (path s2 p4-1))
 (not (link s2 p4-2))
 (path s2 p4-2)
 (not (link s2 p5-0))
 (not (path s2 p5-0))
 (not (link s2 p6-3))
 (not (path s2 p6-3))
 (not (link s3 s0))
 (not (path s3 s0))
 (link s3 s1)
 (not (path s3 s1))
 (not (link s3 s2))
 (not (path s3 s2))
 (not (link s3 s3))
 (not (path s3 s3))
 (not (link s3 s4))
 (not (path s3 s4))
 (not (link s3 s5))
 (not (path s3 s5))
 (link s3 s6)
 (not (path s3 s6))
 (not (link s3 p0-3))
 (path s3 p0-3)
 (not (link s3 p1-0))
 (not (path s3 p1-0))
 (not (link s3 p1-5))
 (not (path s3 p1-5))
 (not (link s3 p2-3))
 (path s3 p2-3)
 (not (link s3 p2-6))
 (not (path s3 p2-6))
 (not (link s3 p3-4))
 (path s3 p3-4)
 (not (link s3 p4-0))
 (not (path s3 p4-0))
 (not (link s3 p4-1))
 (not (path s3 p4-1))
 (not (link s3 p4-2))
 (not (path s3 p4-2))
 (not (link s3 p5-0))
 (not (path s3 p5-0))
 (not (link s3 p6-3))
 (path s3 p6-3)
 (link s4 s0)
 (not (path s4 s0))
 (link s4 s1)
 (not (path s4 s1))
 (link s4 s2)
 (not (path s4 s2))
 (not (link s4 s3))
 (not (path s4 s3))
 (not (link s4 s4))
 (not (path s4 s4))
 (not (link s4 s5))
 (not (path s4 s5))
 (link s4 s6)
 (not (path s4 s6))
 (not (link s4 p0-3))
 (not (path s4 p0-3))
 (not (link s4 p1-0))
 (not (path s4 p1-0))
 (not (link s4 p1-5))
 (not (path s4 p1-5))
 (not (link s4 p2-3))
 (not (path s4 p2-3))
 (not (link s4 p2-6))
 (not (path s4 p2-6))
 (not (link s4 p3-4))
 (path s4 p3-4)
 (not (link s4 p4-0))
 (path s4 p4-0)
 (not (link s4 p4-1))
 (path s4 p4-1)
 (not (link s4 p4-2))
 (path s4 p4-2)
 (not (link s4 p5-0))
 (not (path s4 p5-0))
 (not (link s4 p6-3))
 (not (path s4 p6-3))
 (link s5 s0)
 (not (path s5 s0))
 (link s5 s1)
 (not (path s5 s1))
 (link s5 s2)
 (not (path s5 s2))
 (not (link s5 s3))
 (not (path s5 s3))
 (not (link s5 s4))
 (not (path s5 s4))
 (not (link s5 s5))
 (not (path s5 s5))
 (link s5 s6)
 (not (path s5 s6))
 (not (link s5 p0-3))
 (not (path s5 p0-3))
 (not (link s5 p1-0))
 (not (path s5 p1-0))
 (not (link s5 p1-5))
 (path s5 p1-5)
 (not (link s5 p2-3))
 (not (path s5 p2-3))
 (not (link s5 p2-6))
 (not (path s5 p2-6))
 (not (link s5 p3-4))
 (not (path s5 p3-4))
 (not (link s5 p4-0))
 (not (path s5 p4-0))
 (not (link s5 p4-1))
 (not (path s5 p4-1))
 (not (link s5 p4-2))
 (not (path s5 p4-2))
 (not (link s5 p5-0))
 (path s5 p5-0)
 (not (link s5 p6-3))
 (not (path s5 p6-3))
 (link s6 s0)
 (not (path s6 s0))
 (link s6 s1)
 (not (path s6 s1))
 (link s6 s2)
 (not (path s6 s2))
 (link s6 s3)
 (not (path s6 s3))
 (link s6 s4)
 (not (path s6 s4))
 (link s6 s5)
 (not (path s6 s5))
 (not (link s6 s6))
 (not (path s6 s6))
 (not (link s6 p0-3))
 (not (path s6 p0-3))
 (not (link s6 p1-0))
 (not (path s6 p1-0))
 (not (link s6 p1-5))
 (not (path s6 p1-5))
 (not (link s6 p2-3))
 (not (path s6 p2-3))
 (not (link s6 p2-6))
 (path s6 p2-6)
 (not (link s6 p3-4))
 (not (path s6 p3-4))
 (not (link s6 p4-0))
 (not (path s6 p4-0))
 (not (link s6 p4-1))
 (not (path s6 p4-1))
 (not (link s6 p4-2))
 (not (path s6 p4-2))
 (not (link s6 p5-0))
 (not (path s6 p5-0))
 (not (link s6 p6-3))
 (path s6 p6-3)
 (not (link p0-3 s0))
 (path p0-3 s0)
 (not (link p0-3 s1))
 (not (path p0-3 s1))
 (not (link p0-3 s2))
 (not (path p0-3 s2))
 (not (link p0-3 s3))
 (path p0-3 s3)
 (not (link p0-3 s4))
 (not (path p0-3 s4))
 (not (link p0-3 s5))
 (not (path p0-3 s5))
 (not (link p0-3 s6))
 (not (path p0-3 s6))
 (not (link p0-3 p0-3))
 (not (path p0-3 p0-3))
 (not (link p0-3 p1-0))
 (not (path p0-3 p1-0))
 (not (link p0-3 p1-5))
 (not (path p0-3 p1-5))
 (not (link p0-3 p2-3))
 (not (path p0-3 p2-3))
 (not (link p0-3 p2-6))
 (not (path p0-3 p2-6))
 (not (link p0-3 p3-4))
 (not (path p0-3 p3-4))
 (not (link p0-3 p4-0))
 (not (path p0-3 p4-0))
 (not (link p0-3 p4-1))
 (not (path p0-3 p4-1))
 (not (link p0-3 p4-2))
 (not (path p0-3 p4-2))
 (not (link p0-3 p5-0))
 (not (path p0-3 p5-0))
 (not (link p0-3 p6-3))
 (not (path p0-3 p6-3))
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
 (not (link p1-0 s6))
 (not (path p1-0 s6))
 (not (link p1-0 p0-3))
 (not (path p1-0 p0-3))
 (not (link p1-0 p1-0))
 (not (path p1-0 p1-0))
 (not (link p1-0 p1-5))
 (not (path p1-0 p1-5))
 (not (link p1-0 p2-3))
 (not (path p1-0 p2-3))
 (not (link p1-0 p2-6))
 (not (path p1-0 p2-6))
 (not (link p1-0 p3-4))
 (not (path p1-0 p3-4))
 (not (link p1-0 p4-0))
 (not (path p1-0 p4-0))
 (not (link p1-0 p4-1))
 (not (path p1-0 p4-1))
 (not (link p1-0 p4-2))
 (not (path p1-0 p4-2))
 (not (link p1-0 p5-0))
 (not (path p1-0 p5-0))
 (not (link p1-0 p6-3))
 (not (path p1-0 p6-3))
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
 (not (link p1-5 s6))
 (not (path p1-5 s6))
 (not (link p1-5 p0-3))
 (not (path p1-5 p0-3))
 (not (link p1-5 p1-0))
 (not (path p1-5 p1-0))
 (not (link p1-5 p1-5))
 (not (path p1-5 p1-5))
 (not (link p1-5 p2-3))
 (not (path p1-5 p2-3))
 (not (link p1-5 p2-6))
 (not (path p1-5 p2-6))
 (not (link p1-5 p3-4))
 (not (path p1-5 p3-4))
 (not (link p1-5 p4-0))
 (not (path p1-5 p4-0))
 (not (link p1-5 p4-1))
 (not (path p1-5 p4-1))
 (not (link p1-5 p4-2))
 (not (path p1-5 p4-2))
 (not (link p1-5 p5-0))
 (not (path p1-5 p5-0))
 (not (link p1-5 p6-3))
 (not (path p1-5 p6-3))
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
 (not (link p2-3 s6))
 (not (path p2-3 s6))
 (not (link p2-3 p0-3))
 (not (path p2-3 p0-3))
 (not (link p2-3 p1-0))
 (not (path p2-3 p1-0))
 (not (link p2-3 p1-5))
 (not (path p2-3 p1-5))
 (not (link p2-3 p2-3))
 (not (path p2-3 p2-3))
 (not (link p2-3 p2-6))
 (not (path p2-3 p2-6))
 (not (link p2-3 p3-4))
 (not (path p2-3 p3-4))
 (not (link p2-3 p4-0))
 (not (path p2-3 p4-0))
 (not (link p2-3 p4-1))
 (not (path p2-3 p4-1))
 (not (link p2-3 p4-2))
 (not (path p2-3 p4-2))
 (not (link p2-3 p5-0))
 (not (path p2-3 p5-0))
 (not (link p2-3 p6-3))
 (not (path p2-3 p6-3))
 (not (link p2-6 s0))
 (not (path p2-6 s0))
 (not (link p2-6 s1))
 (not (path p2-6 s1))
 (not (link p2-6 s2))
 (path p2-6 s2)
 (not (link p2-6 s3))
 (not (path p2-6 s3))
 (not (link p2-6 s4))
 (not (path p2-6 s4))
 (not (link p2-6 s5))
 (not (path p2-6 s5))
 (not (link p2-6 s6))
 (path p2-6 s6)
 (not (link p2-6 p0-3))
 (not (path p2-6 p0-3))
 (not (link p2-6 p1-0))
 (not (path p2-6 p1-0))
 (not (link p2-6 p1-5))
 (not (path p2-6 p1-5))
 (not (link p2-6 p2-3))
 (not (path p2-6 p2-3))
 (not (link p2-6 p2-6))
 (not (path p2-6 p2-6))
 (not (link p2-6 p3-4))
 (not (path p2-6 p3-4))
 (not (link p2-6 p4-0))
 (not (path p2-6 p4-0))
 (not (link p2-6 p4-1))
 (not (path p2-6 p4-1))
 (not (link p2-6 p4-2))
 (not (path p2-6 p4-2))
 (not (link p2-6 p5-0))
 (not (path p2-6 p5-0))
 (not (link p2-6 p6-3))
 (not (path p2-6 p6-3))
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
 (not (link p3-4 s5))
 (not (path p3-4 s5))
 (not (link p3-4 s6))
 (not (path p3-4 s6))
 (not (link p3-4 p0-3))
 (not (path p3-4 p0-3))
 (not (link p3-4 p1-0))
 (not (path p3-4 p1-0))
 (not (link p3-4 p1-5))
 (not (path p3-4 p1-5))
 (not (link p3-4 p2-3))
 (not (path p3-4 p2-3))
 (not (link p3-4 p2-6))
 (not (path p3-4 p2-6))
 (not (link p3-4 p3-4))
 (not (path p3-4 p3-4))
 (not (link p3-4 p4-0))
 (not (path p3-4 p4-0))
 (not (link p3-4 p4-1))
 (not (path p3-4 p4-1))
 (not (link p3-4 p4-2))
 (not (path p3-4 p4-2))
 (not (link p3-4 p5-0))
 (not (path p3-4 p5-0))
 (not (link p3-4 p6-3))
 (not (path p3-4 p6-3))
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
 (not (link p4-0 s5))
 (not (path p4-0 s5))
 (not (link p4-0 s6))
 (not (path p4-0 s6))
 (not (link p4-0 p0-3))
 (not (path p4-0 p0-3))
 (not (link p4-0 p1-0))
 (not (path p4-0 p1-0))
 (not (link p4-0 p1-5))
 (not (path p4-0 p1-5))
 (not (link p4-0 p2-3))
 (not (path p4-0 p2-3))
 (not (link p4-0 p2-6))
 (not (path p4-0 p2-6))
 (not (link p4-0 p3-4))
 (not (path p4-0 p3-4))
 (not (link p4-0 p4-0))
 (not (path p4-0 p4-0))
 (not (link p4-0 p4-1))
 (not (path p4-0 p4-1))
 (not (link p4-0 p4-2))
 (not (path p4-0 p4-2))
 (not (link p4-0 p5-0))
 (not (path p4-0 p5-0))
 (not (link p4-0 p6-3))
 (not (path p4-0 p6-3))
 (not (link p4-1 s0))
 (not (path p4-1 s0))
 (not (link p4-1 s1))
 (path p4-1 s1)
 (not (link p4-1 s2))
 (not (path p4-1 s2))
 (not (link p4-1 s3))
 (not (path p4-1 s3))
 (not (link p4-1 s4))
 (path p4-1 s4)
 (not (link p4-1 s5))
 (not (path p4-1 s5))
 (not (link p4-1 s6))
 (not (path p4-1 s6))
 (not (link p4-1 p0-3))
 (not (path p4-1 p0-3))
 (not (link p4-1 p1-0))
 (not (path p4-1 p1-0))
 (not (link p4-1 p1-5))
 (not (path p4-1 p1-5))
 (not (link p4-1 p2-3))
 (not (path p4-1 p2-3))
 (not (link p4-1 p2-6))
 (not (path p4-1 p2-6))
 (not (link p4-1 p3-4))
 (not (path p4-1 p3-4))
 (not (link p4-1 p4-0))
 (not (path p4-1 p4-0))
 (not (link p4-1 p4-1))
 (not (path p4-1 p4-1))
 (not (link p4-1 p4-2))
 (not (path p4-1 p4-2))
 (not (link p4-1 p5-0))
 (not (path p4-1 p5-0))
 (not (link p4-1 p6-3))
 (not (path p4-1 p6-3))
 (not (link p4-2 s0))
 (not (path p4-2 s0))
 (not (link p4-2 s1))
 (not (path p4-2 s1))
 (not (link p4-2 s2))
 (path p4-2 s2)
 (not (link p4-2 s3))
 (not (path p4-2 s3))
 (not (link p4-2 s4))
 (path p4-2 s4)
 (not (link p4-2 s5))
 (not (path p4-2 s5))
 (not (link p4-2 s6))
 (not (path p4-2 s6))
 (not (link p4-2 p0-3))
 (not (path p4-2 p0-3))
 (not (link p4-2 p1-0))
 (not (path p4-2 p1-0))
 (not (link p4-2 p1-5))
 (not (path p4-2 p1-5))
 (not (link p4-2 p2-3))
 (not (path p4-2 p2-3))
 (not (link p4-2 p2-6))
 (not (path p4-2 p2-6))
 (not (link p4-2 p3-4))
 (not (path p4-2 p3-4))
 (not (link p4-2 p4-0))
 (not (path p4-2 p4-0))
 (not (link p4-2 p4-1))
 (not (path p4-2 p4-1))
 (not (link p4-2 p4-2))
 (not (path p4-2 p4-2))
 (not (link p4-2 p5-0))
 (not (path p4-2 p5-0))
 (not (link p4-2 p6-3))
 (not (path p4-2 p6-3))
 (not (link p5-0 s0))
 (path p5-0 s0)
 (not (link p5-0 s1))
 (not (path p5-0 s1))
 (not (link p5-0 s2))
 (not (path p5-0 s2))
 (not (link p5-0 s3))
 (not (path p5-0 s3))
 (not (link p5-0 s4))
 (not (path p5-0 s4))
 (not (link p5-0 s5))
 (path p5-0 s5)
 (not (link p5-0 s6))
 (not (path p5-0 s6))
 (not (link p5-0 p0-3))
 (not (path p5-0 p0-3))
 (not (link p5-0 p1-0))
 (not (path p5-0 p1-0))
 (not (link p5-0 p1-5))
 (not (path p5-0 p1-5))
 (not (link p5-0 p2-3))
 (not (path p5-0 p2-3))
 (not (link p5-0 p2-6))
 (not (path p5-0 p2-6))
 (not (link p5-0 p3-4))
 (not (path p5-0 p3-4))
 (not (link p5-0 p4-0))
 (not (path p5-0 p4-0))
 (not (link p5-0 p4-1))
 (not (path p5-0 p4-1))
 (not (link p5-0 p4-2))
 (not (path p5-0 p4-2))
 (not (link p5-0 p5-0))
 (not (path p5-0 p5-0))
 (not (link p5-0 p6-3))
 (not (path p5-0 p6-3))
 (not (link p6-3 s0))
 (not (path p6-3 s0))
 (not (link p6-3 s1))
 (not (path p6-3 s1))
 (not (link p6-3 s2))
 (not (path p6-3 s2))
 (not (link p6-3 s3))
 (path p6-3 s3)
 (not (link p6-3 s4))
 (not (path p6-3 s4))
 (not (link p6-3 s5))
 (not (path p6-3 s5))
 (not (link p6-3 s6))
 (path p6-3 s6)
 (not (link p6-3 p0-3))
 (not (path p6-3 p0-3))
 (not (link p6-3 p1-0))
 (not (path p6-3 p1-0))
 (not (link p6-3 p1-5))
 (not (path p6-3 p1-5))
 (not (link p6-3 p2-3))
 (not (path p6-3 p2-3))
 (not (link p6-3 p2-6))
 (not (path p6-3 p2-6))
 (not (link p6-3 p3-4))
 (not (path p6-3 p3-4))
 (not (link p6-3 p4-0))
 (not (path p6-3 p4-0))
 (not (link p6-3 p4-1))
 (not (path p6-3 p4-1))
 (not (link p6-3 p4-2))
 (not (path p6-3 p4-2))
 (not (link p6-3 p5-0))
 (not (path p6-3 p5-0))
 (not (link p6-3 p6-3))
 (not (path p6-3 p6-3))
)
(:global-goal (and
 (= (at driver2) s4)
 (= (pos truck2) s4)
 (= (in package1) s6)
 (= (in package2) s3)
 (= (in package3) s4)
 (= (in package4) s6)
 (= (in package5) s1)
))
)
