(define (domain openstacks)
(:requirements :typing :equality :fluents)
(:types
    order product count - object)
(:constants
 p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 - product
 o1 o2 o3 o4 o5 o6 o7 o8 o9 o10 - order
)
(:predicates
  (includes ?o - order ?p - product)
  (waiting ?o - order)
  (started ?o - order)
  (shipped ?o - order)
  (made ?p - product))
(:functions
  (stacks-avail) - count
  (next-count ?s) - count)
(:action open-new-stack
 :parameters (?open ?new-open - count)
 :precondition (and (= (stacks-avail) ?open) (= (next-count ?open) ?new-open))
 :effect (assign (stacks-avail) ?new-open))
(:action start-order
 :parameters (?o - order ?avail ?new-avail - count)
 :precondition (and (waiting ?o) (= (stacks-avail) ?avail)
               (= (next-count ?new-avail) ?avail))
 :effect (and (not (waiting ?o)) (started ?o) (assign (stacks-avail) ?new-avail)))
(:action make-product-p1
 :parameters ()
 :precondition (and
(not (made p1))
(started o2)
)
 :effect (and
(made p1)
))
(:action make-product-p2
 :parameters ()
 :precondition (and
(not (made p2))
(started o8)
(started o10)
)
 :effect (and
(made p2)
))
(:action make-product-p3
 :parameters ()
 :precondition (and
(not (made p3))
(started o1)
(started o4)
)
 :effect (and
(made p3)
))
(:action make-product-p4
 :parameters ()
 :precondition (and
(not (made p4))
(started o1)
(started o5)
)
 :effect (and
(made p4)
))
(:action make-product-p5
 :parameters ()
 :precondition (and
(not (made p5))
(started o6)
)
 :effect (and
(made p5)
))
(:action make-product-p6
 :parameters ()
 :precondition (and
(not (made p6))
(started o2)
)
 :effect (and
(made p6)
))
(:action make-product-p7
 :parameters ()
 :precondition (and
(not (made p7))
(started o9)
)
 :effect (and
(made p7)
))
(:action make-product-p8
 :parameters ()
 :precondition (and
(not (made p8))
(started o3)
)
 :effect (and
(made p8)
))
(:action make-product-p9
 :parameters ()
 :precondition (and
(not (made p9))
(started o7)
)
 :effect (and
(made p9)
))
(:action make-product-p10
 :parameters ()
 :precondition (and
(not (made p10))
(started o6)
(started o9)
)
 :effect (and
(made p10)
))
(:action ship-order-o1
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o1)
(made p3)
(made p4)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o1)
)
(shipped o1)
))
(:action ship-order-o2
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o2)
(made p1)
(made p6)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o2)
)
(shipped o2)
))
(:action ship-order-o3
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o3)
(made p8)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o3)
)
(shipped o3)
))
(:action ship-order-o4
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o4)
(made p3)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o4)
)
(shipped o4)
))
(:action ship-order-o5
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o5)
(made p4)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o5)
)
(shipped o5)
))
(:action ship-order-o6
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o6)
(made p5)
(made p10)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o6)
)
(shipped o6)
))
(:action ship-order-o7
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o7)
(made p9)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o7)
)
(shipped o7)
))
(:action ship-order-o8
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o8)
(made p2)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o8)
)
(shipped o8)
))
(:action ship-order-o9
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o9)
(made p7)
(made p10)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o9)
)
(shipped o9)
))
(:action ship-order-o10
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o10)
(made p2)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o10)
)
(shipped o10)
))
)
