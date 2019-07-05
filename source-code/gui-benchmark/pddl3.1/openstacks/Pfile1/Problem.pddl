(define (problem pfile1)
(:domain openstacks)
(:objects
 n0 n1 n2 n3 n4 n5 - count
)
(:init
 (= (next-count n0) n1)
 (= (stacks-avail) n0)
 (= (next-count n1) n2)
 (= (next-count n2) n3)
 (= (next-count n3) n4)
 (= (next-count n4) n5)
 (waiting o1)
 (not (started o1))
 (not (shipped o1))
 (not (includes o1 p1))
 (includes o1 p2)
 (not (includes o1 p3))
 (not (includes o1 p4))
 (not (includes o1 p5))
 (waiting o2)
 (not (started o2))
 (not (shipped o2))
 (includes o2 p1)
 (includes o2 p2)
 (not (includes o2 p3))
 (not (includes o2 p4))
 (not (includes o2 p5))
 (waiting o3)
 (not (started o3))
 (not (shipped o3))
 (not (includes o3 p1))
 (not (includes o3 p2))
 (includes o3 p3)
 (not (includes o3 p4))
 (not (includes o3 p5))
 (waiting o4)
 (not (started o4))
 (not (shipped o4))
 (not (includes o4 p1))
 (not (includes o4 p2))
 (includes o4 p3)
 (includes o4 p4)
 (not (includes o4 p5))
 (waiting o5)
 (not (started o5))
 (not (shipped o5))
 (not (includes o5 p1))
 (not (includes o5 p2))
 (not (includes o5 p3))
 (not (includes o5 p4))
 (includes o5 p5)
 (not (made p1))
 (not (made p2))
 (not (made p3))
 (not (made p4))
 (not (made p5))
)
(:global-goal (and
 (shipped o1)
 (shipped o2)
 (shipped o3)
 (shipped o4)
 (shipped o5)
))
)
