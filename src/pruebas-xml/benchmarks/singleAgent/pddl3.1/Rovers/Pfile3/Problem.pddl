(define (problem pfile3)
(:domain rover)
(:objects
 rover0 - rover
 waypoint1 waypoint2 waypoint3 - waypoint
 rover0store - store
 camera0 - camera
 colour high_res low_res - mode
 general - lander
 objective1 - objective
)
(:init
 ;(= (status rover0store) Empty)
 (= (equipped_for_soil_analysis rover0) Yes)
 (= (equipped_for_rock_analysis rover0) Yes)
 (= (equipped_for_imaging rover0) Yes)

 (not (can_traverse rover0 waypoint1 waypoint1))
 (can_traverse rover0 waypoint1 waypoint2)
 (can_traverse rover0 waypoint1 waypoint3)
 (can_traverse rover0 waypoint2 waypoint1)
 (not (can_traverse rover0 waypoint2 waypoint2))
 (can_traverse rover0 waypoint2 waypoint3)
 (can_traverse rover0 waypoint3 waypoint1)
 (can_traverse rover0 waypoint3 waypoint2)
 (not (can_traverse rover0 waypoint3 waypoint3))

 (= (have_rock_analysis rover0 waypoint1) Not)
 (= (have_rock_analysis rover0 waypoint2) Not)
 (= (have_rock_analysis rover0 waypoint3) Not)
 (= (have_soil_analysis rover0 waypoint1) Not)
 (= (have_soil_analysis rover0 waypoint2) Not)
 (= (have_soil_analysis rover0 waypoint3) Not)

 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (visible waypoint1 waypoint3)
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint3 waypoint1)
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))

 (= (at_soil_sample waypoint1) Yes)
 (= (at_soil_sample waypoint2) Yes)
 (= (at_soil_sample waypoint3) Yes)
 (= (at_rock_sample waypoint1) Not)
 (= (at_rock_sample waypoint2) Yes)
 (= (at_rock_sample waypoint3) Yes)

 (= (calibrated camera0 rover0) Not)

 (supports camera0 colour)
 (supports camera0 high_res)

 (= (have_image rover0 objective1 colour) Not)
 (= (have_image rover0 objective1 high_res) Not)

 (not (communicated_image_data objective1 colour))
 (not (communicated_image_data objective1 high_res))

 (visible_from objective1 waypoint1)
 (visible_from objective1 waypoint2)
 (visible_from objective1 waypoint3)

 (= (at rover0) waypoint3)
 (= (at_lander general) waypoint1)
 (= (store_of rover0store) rover0)
 (= (calibration_target camera0) objective1)
 (= (on_board camera0) rover0)

 (= (more_rocks waypoint1) Yes)
 (= (more_rocks waypoint2) Yes)
 (= (more_rocks waypoint3) Yes)
 (= (more_soils waypoint1) Yes)
 (= (more_soils waypoint2) Yes)
 (= (more_soils waypoint3) Yes)
)
(:global-goal (and
 (communicated_rock_data waypoint2)
 (communicated_soil_data waypoint1)
 (communicated_image_data objective1 high_res)
))
)
