(define (problem pfile8)
(:domain rover)
(:objects
 rover0 rover1 - rover
 waypoint1 waypoint2 waypoint3 waypoint4 waypoint5 - waypoint
 rover0store rover1store - store
 camera1 - camera
 low_res - mode
 general - lander
 objective1 - objective
)
(:init

 (= (status rover1store) Empty)
 (= (status rover0store) Empty)

 (= (equipped_for_soil_analysis rover0) Yes)
 (= (equipped_for_rock_analysis rover0) Yes)
 (= (equipped_for_imaging rover0) Yes)

 (not (can_traverse rover0 waypoint1 waypoint1))
 (can_traverse rover0 waypoint1 waypoint2)
 (can_traverse rover0 waypoint1 waypoint3)
 (can_traverse rover0 waypoint1 waypoint4)
 (can_traverse rover0 waypoint1 waypoint5)
 (can_traverse rover0 waypoint2 waypoint1)
 (not (can_traverse rover0 waypoint2 waypoint2))
 (can_traverse rover0 waypoint2 waypoint3)
 (can_traverse rover0 waypoint2 waypoint4)
 (can_traverse rover0 waypoint2 waypoint5)
 (can_traverse rover0 waypoint3 waypoint1)
 (can_traverse rover0 waypoint3 waypoint2)
 (not (can_traverse rover0 waypoint3 waypoint3))
 (can_traverse rover0 waypoint3 waypoint4)
 (can_traverse rover0 waypoint3 waypoint5)
 (can_traverse rover0 waypoint4 waypoint1)
 (can_traverse rover0 waypoint4 waypoint2)
 (can_traverse rover0 waypoint4 waypoint3)
 (not (can_traverse rover0 waypoint4 waypoint4))
 (can_traverse rover0 waypoint4 waypoint5)
 (can_traverse rover0 waypoint5 waypoint1)
 (can_traverse rover0 waypoint5 waypoint2)
 (can_traverse rover0 waypoint5 waypoint3)
 (can_traverse rover0 waypoint5 waypoint4)
 (not (can_traverse rover0 waypoint5 waypoint5))

 (= (have_rock_analysis rover0 waypoint1) Not)
 (= (have_rock_analysis rover0 waypoint2) Not)
 (= (have_rock_analysis rover0 waypoint3) Not)
 (= (have_rock_analysis rover0 waypoint4) Not)
 (= (have_rock_analysis rover0 waypoint5) Not)
 (= (have_soil_analysis rover0 waypoint1) Not)
 (= (have_soil_analysis rover0 waypoint2) Not)
 (= (have_soil_analysis rover0 waypoint3) Not)
 (= (have_soil_analysis rover0 waypoint4) Not)
 (= (have_soil_analysis rover0 waypoint5) Not)


 (= (equipped_for_soil_analysis rover1) Yes)
 (= (equipped_for_rock_analysis rover1) Yes)
 (= (equipped_for_imaging rover1) Not)

 (not (can_traverse rover1 waypoint1 waypoint1))
 (can_traverse rover1 waypoint1 waypoint2)
 (can_traverse rover1 waypoint1 waypoint3)
 (can_traverse rover1 waypoint1 waypoint4)
 (can_traverse rover1 waypoint1 waypoint5)
 (can_traverse rover1 waypoint2 waypoint1)
 (not (can_traverse rover1 waypoint2 waypoint2))
 (can_traverse rover1 waypoint2 waypoint3)
 (can_traverse rover1 waypoint2 waypoint4)
 (can_traverse rover1 waypoint2 waypoint5)
 (can_traverse rover1 waypoint3 waypoint1)
 (can_traverse rover1 waypoint3 waypoint2)
 (not (can_traverse rover1 waypoint3 waypoint3))
 (can_traverse rover1 waypoint3 waypoint4)
 (can_traverse rover1 waypoint3 waypoint5)
 (can_traverse rover1 waypoint4 waypoint1)
 (can_traverse rover1 waypoint4 waypoint2)
 (can_traverse rover1 waypoint4 waypoint3)
 (not (can_traverse rover1 waypoint4 waypoint4))
 (can_traverse rover1 waypoint4 waypoint5)
 (can_traverse rover1 waypoint5 waypoint1)
 (can_traverse rover1 waypoint5 waypoint2)
 (can_traverse rover1 waypoint5 waypoint3)
 (can_traverse rover1 waypoint5 waypoint4)
 (not (can_traverse rover1 waypoint5 waypoint5))

 (= (have_rock_analysis rover1 waypoint1) Not)
 (= (have_rock_analysis rover1 waypoint2) Not)
 (= (have_rock_analysis rover1 waypoint3) Not)
 (= (have_rock_analysis rover1 waypoint4) Not)
 (= (have_rock_analysis rover1 waypoint5) Not)
 (= (have_soil_analysis rover1 waypoint1) Not)
 (= (have_soil_analysis rover1 waypoint2) Not)
 (= (have_soil_analysis rover1 waypoint3) Not)
 (= (have_soil_analysis rover1 waypoint4) Not)
 (= (have_soil_analysis rover1 waypoint5) Not)

 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (visible waypoint1 waypoint3)
 (visible waypoint1 waypoint4)
 (visible waypoint1 waypoint5)
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint2 waypoint4)
 (visible waypoint2 waypoint5)
 (visible waypoint3 waypoint1)
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (visible waypoint3 waypoint4)
 (visible waypoint3 waypoint5)
 (visible waypoint4 waypoint1)
 (visible waypoint4 waypoint2)
 (visible waypoint4 waypoint3)
 (not (visible waypoint4 waypoint4))
 (visible waypoint4 waypoint5)
 (visible waypoint5 waypoint1)
 (visible waypoint5 waypoint2)
 (visible waypoint5 waypoint3)
 (visible waypoint5 waypoint4)
 (not (visible waypoint5 waypoint5))

 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_soil_data waypoint4))
 (not (communicated_soil_data waypoint5))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))
 (not (communicated_rock_data waypoint4))
 (not (communicated_rock_data waypoint5))

 (= (at_soil_sample waypoint1) Yes)
 (= (at_soil_sample waypoint2) Yes)
 (= (at_soil_sample waypoint3) Yes)
 (= (at_soil_sample waypoint4) Yes)
 (= (at_soil_sample waypoint5) Yes)
 (= (at_rock_sample waypoint1) Yes)
 (= (at_rock_sample waypoint2) Yes)
 (= (at_rock_sample waypoint3) Yes)
 (= (at_rock_sample waypoint4) Yes)
 (= (at_rock_sample waypoint5) Yes)

 (supports camera1 low_res)
 (= (calibration_target camera1) objective1)

 (= (calibrated camera1 rover0) Not)
 (= (have_image rover0 objective1 low_res) Not)
 (not (communicated_image_data objective1 low_res))

 (visible_from objective1 waypoint1)
 (visible_from objective1 waypoint2)
 (visible_from objective1 waypoint3)
 (visible_from objective1 waypoint4)
 (visible_from objective1 waypoint5)

 (= (at rover0) waypoint4)
 (= (store_of rover0store) rover0)
 (= (on_board camera1) rover0)

 (= (at rover1) waypoint4)
 (= (store_of rover1store) rover1)

 (= (at_lander general) waypoint3)

 (= (more_rocks waypoint1) Yes)
 (= (more_rocks waypoint2) Yes)
 (= (more_rocks waypoint3) Yes)
 (= (more_rocks waypoint4) Yes)
 (= (more_rocks waypoint5) Yes)
 (= (more_soils waypoint1) Yes)
 (= (more_soils waypoint2) Yes)
 (= (more_soils waypoint3) Yes)
 (= (more_soils waypoint4) Yes)
 (= (more_soils waypoint5) Yes)
)
(:global-goal (and
 (communicated_soil_data waypoint5)
 (communicated_soil_data waypoint4)
 (communicated_rock_data waypoint1)
 (communicated_rock_data waypoint3)
 (communicated_image_data objective1 low_res)
))
)
