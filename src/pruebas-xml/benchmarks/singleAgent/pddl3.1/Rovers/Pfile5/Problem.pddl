(define (problem pfile5)
(:domain rover)
(:objects
 rover0 - rover
 waypoint1 waypoint2 waypoint3 waypoint4 - waypoint
 rover0store - store
 camera1 - camera
 low_res - mode
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
 (can_traverse rover0 waypoint1 waypoint4)
 (can_traverse rover0 waypoint2 waypoint1)
 (not (can_traverse rover0 waypoint2 waypoint2))
 (can_traverse rover0 waypoint2 waypoint3)
 (can_traverse rover0 waypoint2 waypoint4)
 (can_traverse rover0 waypoint3 waypoint1)
 (can_traverse rover0 waypoint3 waypoint2)
 (not (can_traverse rover0 waypoint3 waypoint3))
 (can_traverse rover0 waypoint3 waypoint4)
 (can_traverse rover0 waypoint4 waypoint1)
 (can_traverse rover0 waypoint4 waypoint2)
 (can_traverse rover0 waypoint4 waypoint3)
 (not (can_traverse rover0 waypoint4 waypoint4))

 (= (have_rock_analysis rover0 waypoint1) Not)
 (= (have_rock_analysis rover0 waypoint2) Not)
 (= (have_rock_analysis rover0 waypoint3) Not)
 (= (have_rock_analysis rover0 waypoint4) Not)
 (= (have_soil_analysis rover0 waypoint1) Not)
 (= (have_soil_analysis rover0 waypoint2) Not)
 (= (have_soil_analysis rover0 waypoint3) Not)
 (= (have_soil_analysis rover0 waypoint4) Not)

 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (visible waypoint1 waypoint3)
 (visible waypoint1 waypoint4)
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint2 waypoint4)
 (visible waypoint3 waypoint1)
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (visible waypoint3 waypoint4)
 (visible waypoint4 waypoint1)
 (visible waypoint4 waypoint2)
 (visible waypoint4 waypoint3)
 (not (visible waypoint4 waypoint4))

 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_soil_data waypoint4))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))
 (not (communicated_rock_data waypoint4))

 (= (at_soil_sample waypoint1) Yes)
 (= (at_soil_sample waypoint2) Yes)
 (= (at_soil_sample waypoint3) Yes)
 (= (at_soil_sample waypoint4) Yes)
 (= (at_rock_sample waypoint1) Yes)
 (= (at_rock_sample waypoint2) Yes)
 (= (at_rock_sample waypoint3) Yes)
 (= (at_rock_sample waypoint4) Yes)

 (supports camera1 low_res)
 (= (calibration_target camera1) objective1)

 (= (calibrated camera1 rover0) Not)
 (= (have_image rover0 objective1 low_res) Not)
 (not (communicated_image_data objective1 low_res))

 (visible_from objective1 waypoint1)
 (visible_from objective1 waypoint2)
 (visible_from objective1 waypoint3)
 (visible_from objective1 waypoint4)

 (= (at rover0) waypoint3)
 (= (store_of rover0store) rover0)
 (= (on_board camera1) rover0)

 (= (at_lander general) waypoint3)

 (= (more_rocks waypoint1) Yes)
 (= (more_rocks waypoint2) Yes)
 (= (more_rocks waypoint3) Yes)
 (= (more_rocks waypoint4) Yes)
 (= (more_soils waypoint1) Yes)
 (= (more_soils waypoint2) Yes)
 (= (more_soils waypoint3) Yes)
 (= (more_soils waypoint4) Yes)
)
(:global-goal (and
 (communicated_soil_data waypoint3)
 (communicated_soil_data waypoint4)
 (communicated_rock_data waypoint1)
 (communicated_image_data objective1 low_res)
))
)
