(define (problem pfile0)
(:domain driverlog)
(:objects
 d1  - driver
 t1 - truck
 p1 p2 - obj
 a b c - location
)
(:init
 (= (at d1) a)
 (= (pos t1) a)
 (empty t1)
 (= (in p1) a)
 (= (in p2) a)
 (not (link a a))
 (link a b)
 (link a c)
 (link b a)
 (not (link b b))
 (link b c)
 (link c a)
 (link c b)
 (not (link c c))
)
(:global-goal (and
 		(= (pos t1) a)
 		(= (in p1) b)
 		(= (in p2) b)
 		(= (at d1) a)
	)
))
