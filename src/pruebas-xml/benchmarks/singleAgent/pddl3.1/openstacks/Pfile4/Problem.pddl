(define (problem pfile4)
(:domain openstacks)
(:objects
 n0 n1 n2 n3 n4 n5 n6 n7 n8 n9 n10 - count
)
(:init
 (= (next-count n0) n1)
 (= (stacks-avail) n0)
 (= (next-count n1) n2)
 (= (next-count n2) n3)
 (= (next-count n3) n4)
 (= (next-count n4) n5)
 (= (next-count n5) n6)
 (= (next-count n6) n7)
 (= (next-count n7) n8)
 (= (next-count n8) n9)
 (= (next-count n9) n10)
 (waiting o1)
 (not (started o1))
 (not (shipped o1))
 (not (includes o1 p1))
 (not (includes o1 p2))
 (not (includes o1 p3))
 (not (includes o1 p4))
 (not (includes o1 p5))
 (includes o1 p6)
 (not (includes o1 p7))
 (not (includes o1 p8))
 (includes o1 p9)
 (includes o1 p10)
 (waiting o2)
 (not (started o2))
 (not (shipped o2))
 (not (includes o2 p1))
 (includes o2 p2)
 (not (includes o2 p3))
 (not (includes o2 p4))
 (not (includes o2 p5))
 (not (includes o2 p6))
 (not (includes o2 p7))
 (not (includes o2 p8))
 (not (includes o2 p9))
 (includes o2 p10)
 (waiting o3)
 (not (started o3))
 (not (shipped o3))
 (includes o3 p1)
 (not (includes o3 p2))
 (not (includes o3 p3))
 (not (includes o3 p4))
 (not (includes o3 p5))
 (not (includes o3 p6))
 (includes o3 p7)
 (includes o3 p8)
 (not (includes o3 p9))
 (not (includes o3 p10))
 (waiting o4)
 (not (started o4))
 (not (shipped o4))
 (not (includes o4 p1))
 (not (includes o4 p2))
 (not (includes o4 p3))
 (includes o4 p4)
 (not (includes o4 p5))
 (not (includes o4 p6))
 (not (includes o4 p7))
 (not (includes o4 p8))
 (not (includes o4 p9))
 (not (includes o4 p10))
 (waiting o5)
 (not (started o5))
 (not (shipped o5))
 (not (includes o5 p1))
 (not (includes o5 p2))
 (includes o5 p3)
 (not (includes o5 p4))
 (not (includes o5 p5))
 (not (includes o5 p6))
 (not (includes o5 p7))
 (not (includes o5 p8))
 (not (includes o5 p9))
 (not (includes o5 p10))
 (waiting o6)
 (not (started o6))
 (not (shipped o6))
 (not (includes o6 p1))
 (not (includes o6 p2))
 (not (includes o6 p3))
 (not (includes o6 p4))
 (includes o6 p5)
 (not (includes o6 p6))
 (not (includes o6 p7))
 (not (includes o6 p8))
 (not (includes o6 p9))
 (not (includes o6 p10))
 (waiting o7)
 (not (started o7))
 (not (shipped o7))
 (not (includes o7 p1))
 (not (includes o7 p2))
 (not (includes o7 p3))
 (not (includes o7 p4))
 (includes o7 p5)
 (not (includes o7 p6))
 (not (includes o7 p7))
 (not (includes o7 p8))
 (not (includes o7 p9))
 (not (includes o7 p10))
 (waiting o8)
 (not (started o8))
 (not (shipped o8))
 (not (includes o8 p1))
 (not (includes o8 p2))
 (includes o8 p3)
 (not (includes o8 p4))
 (not (includes o8 p5))
 (not (includes o8 p6))
 (not (includes o8 p7))
 (not (includes o8 p8))
 (not (includes o8 p9))
 (not (includes o8 p10))
 (waiting o9)
 (not (started o9))
 (not (shipped o9))
 (includes o9 p1)
 (not (includes o9 p2))
 (not (includes o9 p3))
 (not (includes o9 p4))
 (not (includes o9 p5))
 (not (includes o9 p6))
 (not (includes o9 p7))
 (not (includes o9 p8))
 (not (includes o9 p9))
 (not (includes o9 p10))
 (waiting o10)
 (not (started o10))
 (not (shipped o10))
 (not (includes o10 p1))
 (not (includes o10 p2))
 (not (includes o10 p3))
 (not (includes o10 p4))
 (not (includes o10 p5))
 (not (includes o10 p6))
 (not (includes o10 p7))
 (not (includes o10 p8))
 (includes o10 p9)
 (not (includes o10 p10))
 (not (made p1))
 (not (made p2))
 (not (made p3))
 (not (made p4))
 (not (made p5))
 (not (made p6))
 (not (made p7))
 (not (made p8))
 (not (made p9))
 (not (made p10))
)
(:global-goal (and
 (shipped o1)
 (shipped o2)
 (shipped o3)
 (shipped o4)
 (shipped o5)
 (shipped o6)
 (shipped o7)
 (shipped o8)
 (shipped o9)
 (shipped o10)
))
)
