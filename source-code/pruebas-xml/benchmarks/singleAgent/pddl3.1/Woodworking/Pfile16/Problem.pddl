(define (problem pfile16)
(:domain woodworking)
(:objects
 grinder0 - grinder
 glazer0 - glazer
 immersion-varnisher0 - immersion-varnisher
 planer0 - planer
 highspeed-saw0 - highspeed-saw
 spray-varnisher0 - spray-varnisher
 saw0 - saw
 red green mauve black blue white - acolour
 walnut teak cherry beech oak - awood
 p0 p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15 p16 p17 - part
 b0 b1 b2 b3 b4 b5 b6 - board
 s0 s1 s2 s3 s4 s5 s6 s7 s8 s9 - aboardsize
)
(:init
 (= (colour p0) natural)
 (unused p0)
 (= (goalsize p0) large)
 (not (available p0))
 (= (wood p0) unknown-wood)
 (= (surface-condition p0) smooth)
 (= (treatment p0) untreated)
 (= (colour p1) natural)
 (unused p1)
 (= (goalsize p1) small)
 (not (available p1))
 (= (wood p1) unknown-wood)
 (= (surface-condition p1) smooth)
 (= (treatment p1) untreated)
 (= (colour p2) mauve)
 (unused p2)
 (= (goalsize p2) small)
 (available p2)
 (= (wood p2) oak)
 (= (surface-condition p2) rough)
 (= (treatment p2) glazed)
 (= (colour p3) natural)
 (unused p3)
 (= (goalsize p3) large)
 (not (available p3))
 (= (wood p3) unknown-wood)
 (= (surface-condition p3) smooth)
 (= (treatment p3) untreated)
 (= (colour p4) natural)
 (unused p4)
 (= (goalsize p4) medium)
 (not (available p4))
 (= (wood p4) unknown-wood)
 (= (surface-condition p4) smooth)
 (= (treatment p4) untreated)
 (= (colour p5) natural)
 (unused p5)
 (= (goalsize p5) large)
 (not (available p5))
 (= (wood p5) unknown-wood)
 (= (surface-condition p5) smooth)
 (= (treatment p5) untreated)
 (= (colour p6) natural)
 (unused p6)
 (= (goalsize p6) small)
 (not (available p6))
 (= (wood p6) unknown-wood)
 (= (surface-condition p6) smooth)
 (= (treatment p6) untreated)
 (= (colour p7) natural)
 (unused p7)
 (= (goalsize p7) large)
 (not (available p7))
 (= (wood p7) unknown-wood)
 (= (surface-condition p7) smooth)
 (= (treatment p7) untreated)
 (= (colour p8) black)
 (unused p8)
 (= (goalsize p8) small)
 (available p8)
 (= (wood p8) walnut)
 (= (surface-condition p8) verysmooth)
 (= (treatment p8) colourfragments)
 (= (colour p9) natural)
 (unused p9)
 (= (goalsize p9) medium)
 (not (available p9))
 (= (wood p9) unknown-wood)
 (= (surface-condition p9) smooth)
 (= (treatment p9) untreated)
 (= (colour p10) natural)
 (unused p10)
 (= (goalsize p10) small)
 (not (available p10))
 (= (wood p10) unknown-wood)
 (= (surface-condition p10) smooth)
 (= (treatment p10) untreated)
 (= (colour p11) natural)
 (unused p11)
 (= (goalsize p11) large)
 (not (available p11))
 (= (wood p11) unknown-wood)
 (= (surface-condition p11) smooth)
 (= (treatment p11) untreated)
 (= (colour p12) red)
 (unused p12)
 (= (goalsize p12) small)
 (available p12)
 (= (wood p12) walnut)
 (= (surface-condition p12) verysmooth)
 (= (treatment p12) glazed)
 (= (colour p13) natural)
 (unused p13)
 (= (goalsize p13) small)
 (not (available p13))
 (= (wood p13) unknown-wood)
 (= (surface-condition p13) smooth)
 (= (treatment p13) untreated)
 (= (colour p14) natural)
 (unused p14)
 (= (goalsize p14) small)
 (not (available p14))
 (= (wood p14) unknown-wood)
 (= (surface-condition p14) smooth)
 (= (treatment p14) untreated)
 (= (colour p15) natural)
 (unused p15)
 (= (goalsize p15) large)
 (not (available p15))
 (= (wood p15) unknown-wood)
 (= (surface-condition p15) smooth)
 (= (treatment p15) untreated)
 (= (colour p16) natural)
 (unused p16)
 (= (goalsize p16) large)
 (not (available p16))
 (= (wood p16) unknown-wood)
 (= (surface-condition p16) smooth)
 (= (treatment p16) untreated)
 (= (colour p17) natural)
 (unused p17)
 (= (goalsize p17) large)
 (available p17)
 (= (wood p17) teak)
 (= (surface-condition p17) verysmooth)
 (= (treatment p17) varnished)
 (= (grind-treatment-change varnished) colourfragments)
 (= (grind-treatment-change glazed) untreated)
 (= (grind-treatment-change untreated) untreated)
 (= (grind-treatment-change colourfragments) untreated)
 (is-smooth verysmooth)
 (is-smooth smooth)
 (not (is-smooth rough))
 (= (boardsize-successor s0) s1)
 (= (boardsize-successor s1) s2)
 (= (boardsize-successor s2) s3)
 (= (boardsize-successor s3) s4)
 (= (boardsize-successor s4) s5)
 (= (boardsize-successor s5) s6)
 (= (boardsize-successor s6) s7)
 (= (boardsize-successor s7) s8)
 (= (boardsize-successor s8) s9)
 (not (has-colour grinder0 natural))
 (not (has-colour grinder0 red))
 (not (has-colour grinder0 green))
 (not (has-colour grinder0 mauve))
 (not (has-colour grinder0 black))
 (not (has-colour grinder0 blue))
 (not (has-colour grinder0 white))
 (has-colour glazer0 natural)
 (not (has-colour glazer0 red))
 (has-colour glazer0 green)
 (has-colour glazer0 mauve)
 (not (has-colour glazer0 black))
 (has-colour glazer0 blue)
 (has-colour glazer0 white)
 (has-colour immersion-varnisher0 natural)
 (not (has-colour immersion-varnisher0 red))
 (has-colour immersion-varnisher0 green)
 (has-colour immersion-varnisher0 mauve)
 (has-colour immersion-varnisher0 black)
 (has-colour immersion-varnisher0 blue)
 (has-colour immersion-varnisher0 white)
 (not (has-colour planer0 natural))
 (not (has-colour planer0 red))
 (not (has-colour planer0 green))
 (not (has-colour planer0 mauve))
 (not (has-colour planer0 black))
 (not (has-colour planer0 blue))
 (not (has-colour planer0 white))
 (not (has-colour highspeed-saw0 natural))
 (not (has-colour highspeed-saw0 red))
 (not (has-colour highspeed-saw0 green))
 (not (has-colour highspeed-saw0 mauve))
 (not (has-colour highspeed-saw0 black))
 (not (has-colour highspeed-saw0 blue))
 (not (has-colour highspeed-saw0 white))
 (has-colour spray-varnisher0 natural)
 (not (has-colour spray-varnisher0 red))
 (has-colour spray-varnisher0 green)
 (has-colour spray-varnisher0 mauve)
 (has-colour spray-varnisher0 black)
 (has-colour spray-varnisher0 blue)
 (has-colour spray-varnisher0 white)
 (not (has-colour saw0 natural))
 (not (has-colour saw0 red))
 (not (has-colour saw0 green))
 (not (has-colour saw0 mauve))
 (not (has-colour saw0 black))
 (not (has-colour saw0 blue))
 (not (has-colour saw0 white))
 (empty highspeed-saw0)
 (= (in-highspeed-saw highspeed-saw0) no-board)
 (= (boardsize b0) s9)
 (= (wood b0) beech)
 (= (surface-condition b0) smooth)
 (available b0)
 (= (boardsize b1) s3)
 (= (wood b1) beech)
 (= (surface-condition b1) rough)
 (available b1)
 (= (boardsize b2) s4)
 (= (wood b2) teak)
 (= (surface-condition b2) rough)
 (available b2)
 (= (boardsize b3) s9)
 (= (wood b3) oak)
 (= (surface-condition b3) rough)
 (available b3)
 (= (boardsize b4) s2)
 (= (wood b4) oak)
 (= (surface-condition b4) rough)
 (available b4)
 (= (boardsize b5) s4)
 (= (wood b5) cherry)
 (= (surface-condition b5) rough)
 (available b5)
 (= (boardsize b6) s6)
 (= (wood b6) walnut)
 (= (surface-condition b6) rough)
 (available b6)
)
(:global-goal (and
 (available p0)
 (= (surface-condition p0) verysmooth)
 (= (treatment p0) varnished)
 (available p1)
 (= (surface-condition p1) smooth)
 (= (treatment p1) varnished)
 (available p2)
 (= (colour p2) green)
 (= (surface-condition p2) smooth)
 (= (treatment p2) glazed)
 (available p3)
 (= (colour p3) natural)
 (= (wood p3) walnut)
 (= (surface-condition p3) smooth)
 (= (treatment p3) glazed)
 (available p4)
 (= (colour p4) white)
 (= (wood p4) teak)
 (available p5)
 (= (colour p5) mauve)
 (= (wood p5) beech)
 (available p6)
 (= (colour p6) green)
 (= (wood p6) oak)
 (available p7)
 (= (surface-condition p7) verysmooth)
 (= (treatment p7) glazed)
 (available p8)
 (= (colour p8) natural)
 (= (treatment p8) varnished)
 (available p9)
 (= (wood p9) oak)
 (= (surface-condition p9) verysmooth)
 (available p10)
 (= (colour p10) blue)
 (= (surface-condition p10) verysmooth)
 (available p11)
 (= (colour p11) blue)
 (= (wood p11) beech)
 (available p12)
 (= (colour p12) green)
 (= (wood p12) walnut)
 (= (surface-condition p12) verysmooth)
 (= (treatment p12) glazed)
 (available p13)
 (= (colour p13) black)
 (= (surface-condition p13) verysmooth)
 (= (treatment p13) varnished)
 (available p14)
 (= (colour p14) blue)
 (= (wood p14) beech)
 (available p15)
 (= (colour p15) mauve)
 (= (wood p15) beech)
 (available p16)
 (= (surface-condition p16) smooth)
 (= (treatment p16) glazed)
 (available p17)
 (= (colour p17) blue)
 (= (wood p17) teak)
))
)
