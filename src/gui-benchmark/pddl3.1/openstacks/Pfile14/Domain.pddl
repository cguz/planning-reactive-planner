(define (domain openstacks)
(:requirements :typing :equality :fluents)
(:types
    order product count - object)
(:constants
 p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15 p16 p17 p18 p19 p20 p21 p22 p23 p24 p25 p26 p27 p28 p29 p30 - product
 o1 o2 o3 o4 o5 o6 o7 o8 o9 o10 o11 o12 o13 o14 o15 o16 o17 o18 o19 o20 o21 o22 o23 o24 o25 o26 o27 o28 o29 o30 - order
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
(started o26)
)
 :effect (and
(made p1)
))
(:action make-product-p2
 :parameters ()
 :precondition (and
(not (made p2))
(started o9)
(started o12)
(started o25)
(started o28)
)
 :effect (and
(made p2)
))
(:action make-product-p3
 :parameters ()
 :precondition (and
(not (made p3))
(started o1)
(started o12)
(started o15)
)
 :effect (and
(made p3)
))
(:action make-product-p4
 :parameters ()
 :precondition (and
(not (made p4))
(started o8)
)
 :effect (and
(made p4)
))
(:action make-product-p5
 :parameters ()
 :precondition (and
(not (made p5))
(started o12)
(started o15)
)
 :effect (and
(made p5)
))
(:action make-product-p6
 :parameters ()
 :precondition (and
(not (made p6))
(started o15)
)
 :effect (and
(made p6)
))
(:action make-product-p7
 :parameters ()
 :precondition (and
(not (made p7))
(started o24)
)
 :effect (and
(made p7)
))
(:action make-product-p8
 :parameters ()
 :precondition (and
(not (made p8))
(started o9)
(started o15)
(started o28)
)
 :effect (and
(made p8)
))
(:action make-product-p9
 :parameters ()
 :precondition (and
(not (made p9))
(started o11)
)
 :effect (and
(made p9)
))
(:action make-product-p10
 :parameters ()
 :precondition (and
(not (made p10))
(started o2)
(started o13)
(started o24)
)
 :effect (and
(made p10)
))
(:action make-product-p11
 :parameters ()
 :precondition (and
(not (made p11))
(started o1)
(started o18)
)
 :effect (and
(made p11)
))
(:action make-product-p12
 :parameters ()
 :precondition (and
(not (made p12))
(started o7)
(started o16)
(started o29)
)
 :effect (and
(made p12)
))
(:action make-product-p13
 :parameters ()
 :precondition (and
(not (made p13))
(started o22)
)
 :effect (and
(made p13)
))
(:action make-product-p14
 :parameters ()
 :precondition (and
(not (made p14))
(started o20)
(started o22)
)
 :effect (and
(made p14)
))
(:action make-product-p15
 :parameters ()
 :precondition (and
(not (made p15))
(started o4)
(started o24)
)
 :effect (and
(made p15)
))
(:action make-product-p16
 :parameters ()
 :precondition (and
(not (made p16))
(started o3)
(started o26)
)
 :effect (and
(made p16)
))
(:action make-product-p17
 :parameters ()
 :precondition (and
(not (made p17))
(started o22)
(started o27)
)
 :effect (and
(made p17)
))
(:action make-product-p18
 :parameters ()
 :precondition (and
(not (made p18))
(started o5)
(started o18)
(started o21)
)
 :effect (and
(made p18)
))
(:action make-product-p19
 :parameters ()
 :precondition (and
(not (made p19))
(started o3)
)
 :effect (and
(made p19)
))
(:action make-product-p20
 :parameters ()
 :precondition (and
(not (made p20))
(started o10)
)
 :effect (and
(made p20)
))
(:action make-product-p21
 :parameters ()
 :precondition (and
(not (made p21))
(started o3)
(started o23)
(started o24)
)
 :effect (and
(made p21)
))
(:action make-product-p22
 :parameters ()
 :precondition (and
(not (made p22))
(started o22)
)
 :effect (and
(made p22)
))
(:action make-product-p23
 :parameters ()
 :precondition (and
(not (made p23))
(started o5)
)
 :effect (and
(made p23)
))
(:action make-product-p24
 :parameters ()
 :precondition (and
(not (made p24))
(started o27)
)
 :effect (and
(made p24)
))
(:action make-product-p25
 :parameters ()
 :precondition (and
(not (made p25))
(started o11)
(started o17)
)
 :effect (and
(made p25)
))
(:action make-product-p26
 :parameters ()
 :precondition (and
(not (made p26))
(started o6)
(started o22)
(started o30)
)
 :effect (and
(made p26)
))
(:action make-product-p27
 :parameters ()
 :precondition (and
(not (made p27))
(started o10)
)
 :effect (and
(made p27)
))
(:action make-product-p28
 :parameters ()
 :precondition (and
(not (made p28))
(started o19)
(started o27)
(started o28)
)
 :effect (and
(made p28)
))
(:action make-product-p29
 :parameters ()
 :precondition (and
(not (made p29))
(started o8)
(started o14)
)
 :effect (and
(made p29)
))
(:action make-product-p30
 :parameters ()
 :precondition (and
(not (made p30))
(started o22)
)
 :effect (and
(made p30)
))
(:action ship-order-o1
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o1)
(made p3)
(made p11)
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
(made p10)
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
(made p16)
(made p19)
(made p21)
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
(made p15)
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
(made p18)
(made p23)
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
(made p26)
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
(made p12)
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
(made p4)
(made p29)
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
(made p2)
(made p8)
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
(made p20)
(made p27)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o10)
)
(shipped o10)
))
(:action ship-order-o11
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o11)
(made p9)
(made p25)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o11)
)
(shipped o11)
))
(:action ship-order-o12
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o12)
(made p2)
(made p3)
(made p5)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o12)
)
(shipped o12)
))
(:action ship-order-o13
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o13)
(made p10)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o13)
)
(shipped o13)
))
(:action ship-order-o14
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o14)
(made p29)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o14)
)
(shipped o14)
))
(:action ship-order-o15
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o15)
(made p3)
(made p5)
(made p6)
(made p8)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o15)
)
(shipped o15)
))
(:action ship-order-o16
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o16)
(made p12)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o16)
)
(shipped o16)
))
(:action ship-order-o17
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o17)
(made p25)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o17)
)
(shipped o17)
))
(:action ship-order-o18
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o18)
(made p11)
(made p18)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o18)
)
(shipped o18)
))
(:action ship-order-o19
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o19)
(made p28)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o19)
)
(shipped o19)
))
(:action ship-order-o20
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o20)
(made p14)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o20)
)
(shipped o20)
))
(:action ship-order-o21
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o21)
(made p18)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o21)
)
(shipped o21)
))
(:action ship-order-o22
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o22)
(made p13)
(made p14)
(made p17)
(made p22)
(made p26)
(made p30)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o22)
)
(shipped o22)
))
(:action ship-order-o23
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o23)
(made p21)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o23)
)
(shipped o23)
))
(:action ship-order-o24
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o24)
(made p7)
(made p10)
(made p15)
(made p21)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o24)
)
(shipped o24)
))
(:action ship-order-o25
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o25)
(made p2)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o25)
)
(shipped o25)
))
(:action ship-order-o26
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o26)
(made p1)
(made p16)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o26)
)
(shipped o26)
))
(:action ship-order-o27
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o27)
(made p17)
(made p24)
(made p28)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o27)
)
(shipped o27)
))
(:action ship-order-o28
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o28)
(made p2)
(made p8)
(made p28)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o28)
)
(shipped o28)
))
(:action ship-order-o29
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o29)
(made p12)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o29)
)
(shipped o29)
))
(:action ship-order-o30
 :parameters (?avail ?new-avail - count)
 :precondition (and (= (stacks-avail) ?avail) (= (next-count ?avail) ?new-avail)
(started o30)
(made p26)
)
 :effect (and (assign (stacks-avail) ?new-avail)
(not 
(started o30)
)
(shipped o30)
))
)
