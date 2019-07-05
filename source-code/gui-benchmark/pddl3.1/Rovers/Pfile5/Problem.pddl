(define (problem pfile5)
(:domain rover)
(:objects
 rover0 rover1 - rover
 waypoint0 waypoint1 waypoint2 waypoint3 - waypoint
 rover0store rover1store - store
 camera0 camera1 camera2 - camera
 colour high_res low_res - mode
 general - lander
 objective0 objective1 objective2 - objective
)
(:init
 (empty rover0store)
 (empty rover1store)
 (not (full rover0store))
 (not (full rover1store))
 (not (equipped_for_soil_analysis rover0))
 (equipped_for_rock_analysis rover0)
 (equipped_for_imaging rover0)
 (not (can_traverse rover0 waypoint0 waypoint0))
 (can_traverse rover0 waypoint0 waypoint1)
 (not (can_traverse rover0 waypoint0 waypoint2))
 (can_traverse rover0 waypoint0 waypoint3)
 (can_traverse rover0 waypoint1 waypoint0)
 (not (can_traverse rover0 waypoint1 waypoint1))
 (not (can_traverse rover0 waypoint1 waypoint2))
 (not (can_traverse rover0 waypoint1 waypoint3))
 (not (can_traverse rover0 waypoint2 waypoint0))
 (not (can_traverse rover0 waypoint2 waypoint1))
 (not (can_traverse rover0 waypoint2 waypoint2))
 (not (can_traverse rover0 waypoint2 waypoint3))
 (can_traverse rover0 waypoint3 waypoint0)
 (not (can_traverse rover0 waypoint3 waypoint1))
 (not (can_traverse rover0 waypoint3 waypoint2))
 (not (can_traverse rover0 waypoint3 waypoint3))
 (not (have_rock_analysis rover0 waypoint0))
 (not (have_rock_analysis rover0 waypoint1))
 (not (have_rock_analysis rover0 waypoint2))
 (not (have_rock_analysis rover0 waypoint3))
 (not (have_soil_analysis rover0 waypoint0))
 (not (have_soil_analysis rover0 waypoint1))
 (not (have_soil_analysis rover0 waypoint2))
 (not (have_soil_analysis rover0 waypoint3))
 (equipped_for_soil_analysis rover1)
 (not (equipped_for_rock_analysis rover1))
 (equipped_for_imaging rover1)
 (not (can_traverse rover1 waypoint0 waypoint0))
 (can_traverse rover1 waypoint0 waypoint1)
 (not (can_traverse rover1 waypoint0 waypoint2))
 (not (can_traverse rover1 waypoint0 waypoint3))
 (can_traverse rover1 waypoint1 waypoint0)
 (not (can_traverse rover1 waypoint1 waypoint1))
 (can_traverse rover1 waypoint1 waypoint2)
 (can_traverse rover1 waypoint1 waypoint3)
 (not (can_traverse rover1 waypoint2 waypoint0))
 (can_traverse rover1 waypoint2 waypoint1)
 (not (can_traverse rover1 waypoint2 waypoint2))
 (not (can_traverse rover1 waypoint2 waypoint3))
 (not (can_traverse rover1 waypoint3 waypoint0))
 (can_traverse rover1 waypoint3 waypoint1)
 (not (can_traverse rover1 waypoint3 waypoint2))
 (not (can_traverse rover1 waypoint3 waypoint3))
 (not (have_rock_analysis rover1 waypoint0))
 (not (have_rock_analysis rover1 waypoint1))
 (not (have_rock_analysis rover1 waypoint2))
 (not (have_rock_analysis rover1 waypoint3))
 (not (have_soil_analysis rover1 waypoint0))
 (not (have_soil_analysis rover1 waypoint1))
 (not (have_soil_analysis rover1 waypoint2))
 (not (have_soil_analysis rover1 waypoint3))
 (not (visible waypoint0 waypoint0))
 (visible waypoint0 waypoint1)
 (visible waypoint0 waypoint2)
 (visible waypoint0 waypoint3)
 (visible waypoint1 waypoint0)
 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (visible waypoint1 waypoint3)
 (visible waypoint2 waypoint0)
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint3 waypoint0)
 (visible waypoint3 waypoint1)
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (not (communicated_soil_data waypoint0))
 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_rock_data waypoint0))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))
 (not (at_soil_sample waypoint0))
 (at_soil_sample waypoint1)
 (at_soil_sample waypoint2)
 (at_soil_sample waypoint3)
 (at_rock_sample waypoint0)
 (at_rock_sample waypoint1)
 (not (at_rock_sample waypoint2))
 (not (at_rock_sample waypoint3))
 (not (calibrated camera0 rover0))
 (not (calibrated camera1 rover0))
 (not (calibrated camera2 rover0))
 (not (calibrated camera0 rover1))
 (not (calibrated camera1 rover1))
 (not (calibrated camera2 rover1))
 (not (supports camera0 colour))
 (supports camera0 high_res)
 (supports camera0 low_res)
 (supports camera1 colour)
 (supports camera1 high_res)
 (not (supports camera1 low_res))
 (supports camera2 colour)
 (supports camera2 high_res)
 (supports camera2 low_res)
 (not (have_image rover0 objective0 colour))
 (not (have_image rover0 objective0 high_res))
 (not (have_image rover0 objective0 low_res))
 (not (have_image rover0 objective1 colour))
 (not (have_image rover0 objective1 high_res))
 (not (have_image rover0 objective1 low_res))
 (not (have_image rover0 objective2 colour))
 (not (have_image rover0 objective2 high_res))
 (not (have_image rover0 objective2 low_res))
 (not (have_image rover1 objective0 colour))
 (not (have_image rover1 objective0 high_res))
 (not (have_image rover1 objective0 low_res))
 (not (have_image rover1 objective1 colour))
 (not (have_image rover1 objective1 high_res))
 (not (have_image rover1 objective1 low_res))
 (not (have_image rover1 objective2 colour))
 (not (have_image rover1 objective2 high_res))
 (not (have_image rover1 objective2 low_res))
 (not (communicated_image_data objective0 colour))
 (not (communicated_image_data objective0 high_res))
 (not (communicated_image_data objective0 low_res))
 (not (communicated_image_data objective1 colour))
 (not (communicated_image_data objective1 high_res))
 (not (communicated_image_data objective1 low_res))
 (not (communicated_image_data objective2 colour))
 (not (communicated_image_data objective2 high_res))
 (not (communicated_image_data objective2 low_res))
 (visible_from objective0 waypoint0)
 (visible_from objective0 waypoint1)
 (visible_from objective0 waypoint2)
 (visible_from objective0 waypoint3)
 (visible_from objective1 waypoint0)
 (visible_from objective1 waypoint1)
 (visible_from objective1 waypoint2)
 (not (visible_from objective1 waypoint3))
 (visible_from objective2 waypoint0)
 (visible_from objective2 waypoint1)
 (visible_from objective2 waypoint2)
 (not (visible_from objective2 waypoint3))
 (= (at rover0) waypoint0)
 (= (at rover1) waypoint0)
 (= (at_lander general) waypoint3)
 (= (store_of rover0store) rover0)
 (= (store_of rover1store) rover1)
 (= (calibration_target camera0) objective1)
 (= (calibration_target camera1) objective1)
 (= (calibration_target camera2) objective1)
 (= (on_board camera0) rover1)
 (= (on_board camera1) rover1)
 (= (on_board camera2) rover0)
)
(:global-goal (and
 (communicated_soil_data waypoint1)
 (communicated_soil_data waypoint2)
 (communicated_rock_data waypoint0)
 (communicated_rock_data waypoint1)
 (communicated_image_data objective0 high_res)
 (communicated_image_data objective2 high_res)
 (communicated_image_data objective0 colour)
))
)
