(define (domain rover)
(:requirements :typing :equality :fluents)
(:types rover waypoint store camera mode lander objective roverstore option - object)
(:predicates
  (can_traverse ?r - rover ?x - waypoint ?y - waypoint)
  ;(full ?s - store)
  ;(empty ?s - store)
  ;(calibrated ?c - camera ?r - rover)
  ;(have_image ?r - rover ?o - objective ?m - mode)
  (supports ?c - camera ?m - mode)
  (visible ?w - waypoint ?p - waypoint)
  (communicated_soil_data ?w - waypoint)
  (communicated_rock_data ?w - waypoint)
  (communicated_image_data ?o - objective ?m - mode)
  (visible_from ?o - objective ?w - waypoint))
(:constants
	Empty Full	- roverstore
	Yes Not		- option
)
(:functions
  (equipped_for_soil_analysis ?r - rover) - option
  (equipped_for_rock_analysis ?r - rover) - option
  (equipped_for_imaging ?r - rover) - option
  (at ?x - rover) - waypoint
  (at_lander ?x - lander) - waypoint
  (store_of ?s - store) - rover
  (calibration_target ?i - camera) - objective
  (on_board ?i - camera) - rover
  (calibrated ?c - camera ?r - rover) - option
  (have_image ?r - rover ?o - objective ?m - mode) - option
  (have_rock_analysis ?r - rover ?w - waypoint) - option
  (have_soil_analysis ?r - rover ?w - waypoint) - option
  (more_soils ?w - waypoint) - option
  (more_rocks ?w - waypoint) - option
  (at_soil_sample ?w - waypoint) - option
  (at_rock_sample ?w - waypoint) - option
  ; (status ?s - store) - roverstore
)

(:action seek_rocks
 :parameters (?r - rover ?w - waypoint)
 :precondition (and 
	(=(at_rock_sample ?w) Not)
	(=(more_rocks ?w) Yes)
   	(=(at ?r) ?w))
 :effect (assign (at_rock_sample ?w) Yes)
)

(:action seek_soils
 :parameters (?r - rover ?w - waypoint)
 :precondition (and 
	(=(at_soil_sample ?w) Not)
	(=(more_soils ?w) Yes)
   	(=(at ?r) ?w))
 :effect (assign (at_soil_sample ?w) Yes)
)

(:action navigate
 :parameters (?x - rover ?y - waypoint ?z - waypoint)
 :precondition (and (can_traverse ?x ?y ?z)
   (= (at ?x) ?y))
 :effect (assign (at ?x) ?z)
)

(:action sample_soil
 :parameters (?x - rover ?s - store ?p - waypoint)
 :precondition (and 
	(= (at ?x) ?p) 
	(= (at_soil_sample ?p) Yes)
   	(= (equipped_for_soil_analysis ?x) Yes)
	(= (store_of ?s) ?x) 
	;(= (have_soil_analysis ?x ?p) Not) 
;	(= (status ?s) Empty))
)
 :effect (and 
;	(assign (status ?s) Full) 
	(assign (have_soil_analysis ?x ?p) Yes)
   	(assign (at_soil_sample ?p) Not)))

(:action sample_rock
 :parameters (?x - rover ?s - store ?p - waypoint)
 :precondition (and 
	(= (at ?x) ?p) 
	(= (at_rock_sample ?p) Yes)
	(= (equipped_for_rock_analysis ?x) Yes) 
	(= (store_of ?s) ?x) 
	;(= (have_rock_analysis ?x ?p) Not) 
;	(= (status ?s) Empty))
)
 :effect (and 
;	(assign (status ?s) Full) 
	(assign (have_rock_analysis ?x ?p) Yes)
   	(assign (at_rock_sample ?p) Not)))

;(:action drop
; :parameters (?x - rover ?y - store)
; :precondition (and (= (store_of ?y) ?x) (= (status ?y) Full))
; :effect (and (assign (status ?y) Empty)))

(:action calibrate
 :parameters (?r - rover ?i - camera ?t - objective ?w - waypoint)
 :precondition (and (= (equipped_for_imaging ?r) Yes)
   ;(= (calibrated ?i ?r) Not)
   (= (calibration_target ?i) ?t) 
   (= (at ?r) ?w) 
   (visible_from ?t ?w)
   (= (on_board ?i) ?r))
 :effect (assign (calibrated ?i ?r) Yes)

(:action take_image
 :parameters (?r - rover ?p - waypoint ?o - objective ?i - camera ?m - mode)
 :precondition (and (= (calibrated ?i ?r) Yes) 
	;(= (have_image ?r ?o ?m) Not) 
	(= (on_board ?i) ?r)
   (= (equipped_for_imaging ?r) Yes) (supports ?i ?m) (visible_from ?o ?p) (= (at ?r) ?p))
 :effect (and (assign (have_image ?r ?o ?m) Yes) (assign (calibrated ?i ?r) Not)))

(:action communicate_soil_data
 :parameters (?r - rover ?l - lander ?p - waypoint ?x - waypoint ?y - waypoint)
 :precondition (and (= (at ?r) ?x) (= (at_lander ?l) ?y)
   (= (have_soil_analysis ?r ?p) Yes) (visible ?x ?y))
 :effect (and 
	 (communicated_soil_data ?p)))

(:action communicate_rock_data
 :parameters (?r - rover ?l - lander ?p - waypoint ?x - waypoint ?y - waypoint)
 :precondition (and (= (at ?r) ?x) (= (at_lander ?l) ?y)
   (= (have_rock_analysis ?r ?p) Yes) (visible ?x ?y))
 :effect (and
   (communicated_rock_data ?p)))

(:action communicate_image_data
 :parameters (?r - rover ?l - lander ?o - objective ?m - mode ?x - waypoint ?y - waypoint)
 :precondition (and (= (at ?r) ?x) (= (at_lander ?l) ?y)
   (= (have_image ?r ?o ?m) Yes) (visible ?x ?y))
 :effect (and 
   (communicated_image_data ?o ?m))))
