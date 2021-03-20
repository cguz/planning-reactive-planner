(define (problem pfile1)
(:domain woodworking)
(:objects
 grinder0 - grinder
 glazer0 - glazer
 immersion-varnisher0 - immersion-varnisher
 planer0 - planer
 highspeed-saw0 - highspeed-saw
 spray-varnisher0 - spray-varnisher
 saw0 - saw
 red black - acolour
 pine teak - awood
 p0 p1 p2 - part
 b0 - board
 s0 s1 s2 s3 - aboardsize
)
(:init
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

 (has-colour glazer0 natural)
 (has-colour glazer0 red)
 (has-colour immersion-varnisher0 natural)
 (has-colour immersion-varnisher0 red)
 (empty highspeed-saw0)
 (has-colour spray-varnisher0 natural)
 (has-colour spray-varnisher0 red)
 (not (has-colour grinder0 natural))
 (not (has-colour grinder0 red))
 (not (has-colour grinder0 black))
 (not (has-colour glazer0 black))
 (not (has-colour immersion-varnisher0 black))
 (not (has-colour planer0 natural))
 (not (has-colour planer0 red))
 (not (has-colour planer0 black))
 (not (has-colour highspeed-saw0 natural))
 (not (has-colour highspeed-saw0 red))
 (not (has-colour highspeed-saw0 black))
 (not (has-colour spray-varnisher0 black))
 (not (has-colour saw0 natural))
 (not (has-colour saw0 red))
 (not (has-colour saw0 black))

 (available p0)
 (= (colour p0) red)
 (= (wood p0) pine)
 (= (surface-condition p0) smooth)
 (= (treatment p0) varnished)
 (= (goalsize p0) small)

 (unused p0)

 (unused p1)
 (= (goalsize p1) medium)
 (not (available p1))

 (available p2)
 (= (colour p2) natural)
 (= (wood p2) teak)
 (= (surface-condition p2) verysmooth)
 (= (treatment p2) varnished)
 (= (goalsize p2) large)
 (= (boardsize b0) s3)
 (= (wood b0) pine)
 (= (surface-condition b0) rough)
 (available b0)

 (= (surface-condition p1) smooth)
 (= (colour p1) natural)
 (= (wood p1) unknown-wood)
 (= (treatment p1) untreated)
 (unused p2)
 (= (in-highspeed-saw highspeed-saw0) no-board)
)
(:global-goal (and
 (available p0)
 (= (colour p0) natural)
 (= (wood p0) pine)
 (available p1)
 (= (colour p1) natural)
 (= (wood p1) pine)
 (= (surface-condition p1) smooth)
 (= (treatment p1) varnished)
 (available p2)
 (= (colour p2) red)
 (= (wood p2) teak)
))
)
