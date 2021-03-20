(define (problem pfile8)
(:domain rover)
(:objects
 rover0 rover1 rover2 rover3 - rover
 waypoint0 waypoint1 waypoint2 waypoint3 waypoint4 waypoint5 - waypoint
 rover0store rover1store rover2store rover3store - store
 camera0 camera1 camera2 camera3 - camera
 colour high_res low_res - mode
 general - lander
 objective0 objective1 objective2 - objective
)
(:init
 (empty rover0store)
 (empty rover1store)
 (empty rover2store)
 (empty rover3store)
 (not (full rover0store))
 (not (full rover1store))
 (not (full rover2store))
 (not (full rover3store))
 (equipped_for_soil_analysis rover0)
 (equipped_for_rock_analysis rover0)
 (not (equipped_for_imaging rover0))
 (not (can_traverse rover0 waypoint0 waypoint0))
 (can_traverse rover0 waypoint0 waypoint1)
 (can_traverse rover0 waypoint0 waypoint2)
 (not (can_traverse rover0 waypoint0 waypoint3))
 (not (can_traverse rover0 waypoint0 waypoint4))
 (not (can_traverse rover0 waypoint0 waypoint5))
 (can_traverse rover0 waypoint1 waypoint0)
 (not (can_traverse rover0 waypoint1 waypoint1))
 (not (can_traverse rover0 waypoint1 waypoint2))
 (not (can_traverse rover0 waypoint1 waypoint3))
 (not (can_traverse rover0 waypoint1 waypoint4))
 (not (can_traverse rover0 waypoint1 waypoint5))
 (can_traverse rover0 waypoint2 waypoint0)
 (not (can_traverse rover0 waypoint2 waypoint1))
 (not (can_traverse rover0 waypoint2 waypoint2))
 (can_traverse rover0 waypoint2 waypoint3)
 (can_traverse rover0 waypoint2 waypoint4)
 (not (can_traverse rover0 waypoint2 waypoint5))
 (not (can_traverse rover0 waypoint3 waypoint0))
 (not (can_traverse rover0 waypoint3 waypoint1))
 (can_traverse rover0 waypoint3 waypoint2)
 (not (can_traverse rover0 waypoint3 waypoint3))
 (not (can_traverse rover0 waypoint3 waypoint4))
 (can_traverse rover0 waypoint3 waypoint5)
 (not (can_traverse rover0 waypoint4 waypoint0))
 (not (can_traverse rover0 waypoint4 waypoint1))
 (can_traverse rover0 waypoint4 waypoint2)
 (not (can_traverse rover0 waypoint4 waypoint3))
 (not (can_traverse rover0 waypoint4 waypoint4))
 (not (can_traverse rover0 waypoint4 waypoint5))
 (not (can_traverse rover0 waypoint5 waypoint0))
 (not (can_traverse rover0 waypoint5 waypoint1))
 (not (can_traverse rover0 waypoint5 waypoint2))
 (can_traverse rover0 waypoint5 waypoint3)
 (not (can_traverse rover0 waypoint5 waypoint4))
 (not (can_traverse rover0 waypoint5 waypoint5))
 (not (have_rock_analysis rover0 waypoint0))
 (not (have_rock_analysis rover0 waypoint1))
 (not (have_rock_analysis rover0 waypoint2))
 (not (have_rock_analysis rover0 waypoint3))
 (not (have_rock_analysis rover0 waypoint4))
 (not (have_rock_analysis rover0 waypoint5))
 (not (have_soil_analysis rover0 waypoint0))
 (not (have_soil_analysis rover0 waypoint1))
 (not (have_soil_analysis rover0 waypoint2))
 (not (have_soil_analysis rover0 waypoint3))
 (not (have_soil_analysis rover0 waypoint4))
 (not (have_soil_analysis rover0 waypoint5))
 (not (equipped_for_soil_analysis rover1))
 (equipped_for_rock_analysis rover1)
 (equipped_for_imaging rover1)
 (not (can_traverse rover1 waypoint0 waypoint0))
 (can_traverse rover1 waypoint0 waypoint1)
 (can_traverse rover1 waypoint0 waypoint2)
 (not (can_traverse rover1 waypoint0 waypoint3))
 (not (can_traverse rover1 waypoint0 waypoint4))
 (not (can_traverse rover1 waypoint0 waypoint5))
 (can_traverse rover1 waypoint1 waypoint0)
 (not (can_traverse rover1 waypoint1 waypoint1))
 (not (can_traverse rover1 waypoint1 waypoint2))
 (not (can_traverse rover1 waypoint1 waypoint3))
 (not (can_traverse rover1 waypoint1 waypoint4))
 (not (can_traverse rover1 waypoint1 waypoint5))
 (can_traverse rover1 waypoint2 waypoint0)
 (not (can_traverse rover1 waypoint2 waypoint1))
 (not (can_traverse rover1 waypoint2 waypoint2))
 (can_traverse rover1 waypoint2 waypoint3)
 (not (can_traverse rover1 waypoint2 waypoint4))
 (can_traverse rover1 waypoint2 waypoint5)
 (not (can_traverse rover1 waypoint3 waypoint0))
 (not (can_traverse rover1 waypoint3 waypoint1))
 (can_traverse rover1 waypoint3 waypoint2)
 (not (can_traverse rover1 waypoint3 waypoint3))
 (can_traverse rover1 waypoint3 waypoint4)
 (not (can_traverse rover1 waypoint3 waypoint5))
 (not (can_traverse rover1 waypoint4 waypoint0))
 (not (can_traverse rover1 waypoint4 waypoint1))
 (not (can_traverse rover1 waypoint4 waypoint2))
 (can_traverse rover1 waypoint4 waypoint3)
 (not (can_traverse rover1 waypoint4 waypoint4))
 (not (can_traverse rover1 waypoint4 waypoint5))
 (not (can_traverse rover1 waypoint5 waypoint0))
 (not (can_traverse rover1 waypoint5 waypoint1))
 (can_traverse rover1 waypoint5 waypoint2)
 (not (can_traverse rover1 waypoint5 waypoint3))
 (not (can_traverse rover1 waypoint5 waypoint4))
 (not (can_traverse rover1 waypoint5 waypoint5))
 (not (have_rock_analysis rover1 waypoint0))
 (not (have_rock_analysis rover1 waypoint1))
 (not (have_rock_analysis rover1 waypoint2))
 (not (have_rock_analysis rover1 waypoint3))
 (not (have_rock_analysis rover1 waypoint4))
 (not (have_rock_analysis rover1 waypoint5))
 (not (have_soil_analysis rover1 waypoint0))
 (not (have_soil_analysis rover1 waypoint1))
 (not (have_soil_analysis rover1 waypoint2))
 (not (have_soil_analysis rover1 waypoint3))
 (not (have_soil_analysis rover1 waypoint4))
 (not (have_soil_analysis rover1 waypoint5))
 (not (equipped_for_soil_analysis rover2))
 (equipped_for_rock_analysis rover2)
 (equipped_for_imaging rover2)
 (not (can_traverse rover2 waypoint0 waypoint0))
 (not (can_traverse rover2 waypoint0 waypoint1))
 (can_traverse rover2 waypoint0 waypoint2)
 (can_traverse rover2 waypoint0 waypoint3)
 (not (can_traverse rover2 waypoint0 waypoint4))
 (not (can_traverse rover2 waypoint0 waypoint5))
 (not (can_traverse rover2 waypoint1 waypoint0))
 (not (can_traverse rover2 waypoint1 waypoint1))
 (can_traverse rover2 waypoint1 waypoint2)
 (not (can_traverse rover2 waypoint1 waypoint3))
 (not (can_traverse rover2 waypoint1 waypoint4))
 (not (can_traverse rover2 waypoint1 waypoint5))
 (can_traverse rover2 waypoint2 waypoint0)
 (can_traverse rover2 waypoint2 waypoint1)
 (not (can_traverse rover2 waypoint2 waypoint2))
 (not (can_traverse rover2 waypoint2 waypoint3))
 (can_traverse rover2 waypoint2 waypoint4)
 (can_traverse rover2 waypoint2 waypoint5)
 (can_traverse rover2 waypoint3 waypoint0)
 (not (can_traverse rover2 waypoint3 waypoint1))
 (not (can_traverse rover2 waypoint3 waypoint2))
 (not (can_traverse rover2 waypoint3 waypoint3))
 (not (can_traverse rover2 waypoint3 waypoint4))
 (not (can_traverse rover2 waypoint3 waypoint5))
 (not (can_traverse rover2 waypoint4 waypoint0))
 (not (can_traverse rover2 waypoint4 waypoint1))
 (can_traverse rover2 waypoint4 waypoint2)
 (not (can_traverse rover2 waypoint4 waypoint3))
 (not (can_traverse rover2 waypoint4 waypoint4))
 (not (can_traverse rover2 waypoint4 waypoint5))
 (not (can_traverse rover2 waypoint5 waypoint0))
 (not (can_traverse rover2 waypoint5 waypoint1))
 (can_traverse rover2 waypoint5 waypoint2)
 (not (can_traverse rover2 waypoint5 waypoint3))
 (not (can_traverse rover2 waypoint5 waypoint4))
 (not (can_traverse rover2 waypoint5 waypoint5))
 (not (have_rock_analysis rover2 waypoint0))
 (not (have_rock_analysis rover2 waypoint1))
 (not (have_rock_analysis rover2 waypoint2))
 (not (have_rock_analysis rover2 waypoint3))
 (not (have_rock_analysis rover2 waypoint4))
 (not (have_rock_analysis rover2 waypoint5))
 (not (have_soil_analysis rover2 waypoint0))
 (not (have_soil_analysis rover2 waypoint1))
 (not (have_soil_analysis rover2 waypoint2))
 (not (have_soil_analysis rover2 waypoint3))
 (not (have_soil_analysis rover2 waypoint4))
 (not (have_soil_analysis rover2 waypoint5))
 (equipped_for_soil_analysis rover3)
 (equipped_for_rock_analysis rover3)
 (equipped_for_imaging rover3)
 (not (can_traverse rover3 waypoint0 waypoint0))
 (can_traverse rover3 waypoint0 waypoint1)
 (not (can_traverse rover3 waypoint0 waypoint2))
 (can_traverse rover3 waypoint0 waypoint3)
 (not (can_traverse rover3 waypoint0 waypoint4))
 (not (can_traverse rover3 waypoint0 waypoint5))
 (can_traverse rover3 waypoint1 waypoint0)
 (not (can_traverse rover3 waypoint1 waypoint1))
 (not (can_traverse rover3 waypoint1 waypoint2))
 (not (can_traverse rover3 waypoint1 waypoint3))
 (not (can_traverse rover3 waypoint1 waypoint4))
 (not (can_traverse rover3 waypoint1 waypoint5))
 (not (can_traverse rover3 waypoint2 waypoint0))
 (not (can_traverse rover3 waypoint2 waypoint1))
 (not (can_traverse rover3 waypoint2 waypoint2))
 (can_traverse rover3 waypoint2 waypoint3)
 (not (can_traverse rover3 waypoint2 waypoint4))
 (can_traverse rover3 waypoint2 waypoint5)
 (can_traverse rover3 waypoint3 waypoint0)
 (not (can_traverse rover3 waypoint3 waypoint1))
 (can_traverse rover3 waypoint3 waypoint2)
 (not (can_traverse rover3 waypoint3 waypoint3))
 (can_traverse rover3 waypoint3 waypoint4)
 (not (can_traverse rover3 waypoint3 waypoint5))
 (not (can_traverse rover3 waypoint4 waypoint0))
 (not (can_traverse rover3 waypoint4 waypoint1))
 (not (can_traverse rover3 waypoint4 waypoint2))
 (can_traverse rover3 waypoint4 waypoint3)
 (not (can_traverse rover3 waypoint4 waypoint4))
 (not (can_traverse rover3 waypoint4 waypoint5))
 (not (can_traverse rover3 waypoint5 waypoint0))
 (not (can_traverse rover3 waypoint5 waypoint1))
 (can_traverse rover3 waypoint5 waypoint2)
 (not (can_traverse rover3 waypoint5 waypoint3))
 (not (can_traverse rover3 waypoint5 waypoint4))
 (not (can_traverse rover3 waypoint5 waypoint5))
 (not (have_rock_analysis rover3 waypoint0))
 (not (have_rock_analysis rover3 waypoint1))
 (not (have_rock_analysis rover3 waypoint2))
 (not (have_rock_analysis rover3 waypoint3))
 (not (have_rock_analysis rover3 waypoint4))
 (not (have_rock_analysis rover3 waypoint5))
 (not (have_soil_analysis rover3 waypoint0))
 (not (have_soil_analysis rover3 waypoint1))
 (not (have_soil_analysis rover3 waypoint2))
 (not (have_soil_analysis rover3 waypoint3))
 (not (have_soil_analysis rover3 waypoint4))
 (not (have_soil_analysis rover3 waypoint5))
 (not (visible waypoint0 waypoint0))
 (visible waypoint0 waypoint1)
 (visible waypoint0 waypoint2)
 (visible waypoint0 waypoint3)
 (visible waypoint0 waypoint4)
 (not (visible waypoint0 waypoint5))
 (visible waypoint1 waypoint0)
 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (not (visible waypoint1 waypoint3))
 (visible waypoint1 waypoint4)
 (visible waypoint1 waypoint5)
 (visible waypoint2 waypoint0)
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint2 waypoint4)
 (visible waypoint2 waypoint5)
 (visible waypoint3 waypoint0)
 (not (visible waypoint3 waypoint1))
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (visible waypoint3 waypoint4)
 (visible waypoint3 waypoint5)
 (visible waypoint4 waypoint0)
 (visible waypoint4 waypoint1)
 (visible waypoint4 waypoint2)
 (visible waypoint4 waypoint3)
 (not (visible waypoint4 waypoint4))
 (visible waypoint4 waypoint5)
 (not (visible waypoint5 waypoint0))
 (visible waypoint5 waypoint1)
 (visible waypoint5 waypoint2)
 (visible waypoint5 waypoint3)
 (visible waypoint5 waypoint4)
 (not (visible waypoint5 waypoint5))
 (not (communicated_soil_data waypoint0))
 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_soil_data waypoint4))
 (not (communicated_soil_data waypoint5))
 (not (communicated_rock_data waypoint0))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))
 (not (communicated_rock_data waypoint4))
 (not (communicated_rock_data waypoint5))
 (not (at_soil_sample waypoint0))
 (at_soil_sample waypoint1)
 (not (at_soil_sample waypoint2))
 (at_soil_sample waypoint3)
 (at_soil_sample waypoint4)
 (not (at_soil_sample waypoint5))
 (not (at_rock_sample waypoint0))
 (not (at_rock_sample waypoint1))
 (at_rock_sample waypoint2)
 (at_rock_sample waypoint3)
 (at_rock_sample waypoint4)
 (at_rock_sample waypoint5)
 (not (calibrated camera0 rover0))
 (not (calibrated camera1 rover0))
 (not (calibrated camera2 rover0))
 (not (calibrated camera3 rover0))
 (not (calibrated camera0 rover1))
 (not (calibrated camera1 rover1))
 (not (calibrated camera2 rover1))
 (not (calibrated camera3 rover1))
 (not (calibrated camera0 rover2))
 (not (calibrated camera1 rover2))
 (not (calibrated camera2 rover2))
 (not (calibrated camera3 rover2))
 (not (calibrated camera0 rover3))
 (not (calibrated camera1 rover3))
 (not (calibrated camera2 rover3))
 (not (calibrated camera3 rover3))
 (supports camera0 colour)
 (supports camera0 high_res)
 (supports camera0 low_res)
 (supports camera1 colour)
 (supports camera1 high_res)
 (not (supports camera1 low_res))
 (supports camera2 colour)
 (supports camera2 high_res)
 (supports camera2 low_res)
 (supports camera3 colour)
 (not (supports camera3 high_res))
 (supports camera3 low_res)
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
 (not (have_image rover2 objective0 colour))
 (not (have_image rover2 objective0 high_res))
 (not (have_image rover2 objective0 low_res))
 (not (have_image rover2 objective1 colour))
 (not (have_image rover2 objective1 high_res))
 (not (have_image rover2 objective1 low_res))
 (not (have_image rover2 objective2 colour))
 (not (have_image rover2 objective2 high_res))
 (not (have_image rover2 objective2 low_res))
 (not (have_image rover3 objective0 colour))
 (not (have_image rover3 objective0 high_res))
 (not (have_image rover3 objective0 low_res))
 (not (have_image rover3 objective1 colour))
 (not (have_image rover3 objective1 high_res))
 (not (have_image rover3 objective1 low_res))
 (not (have_image rover3 objective2 colour))
 (not (have_image rover3 objective2 high_res))
 (not (have_image rover3 objective2 low_res))
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
 (not (visible_from objective0 waypoint3))
 (not (visible_from objective0 waypoint4))
 (not (visible_from objective0 waypoint5))
 (visible_from objective1 waypoint0)
 (not (visible_from objective1 waypoint1))
 (not (visible_from objective1 waypoint2))
 (not (visible_from objective1 waypoint3))
 (not (visible_from objective1 waypoint4))
 (not (visible_from objective1 waypoint5))
 (visible_from objective2 waypoint0)
 (visible_from objective2 waypoint1)
 (visible_from objective2 waypoint2)
 (not (visible_from objective2 waypoint3))
 (not (visible_from objective2 waypoint4))
 (not (visible_from objective2 waypoint5))
 (= (at rover0) waypoint2)
 (= (at rover1) waypoint2)
 (= (at rover2) waypoint2)
 (= (at rover3) waypoint3)
 (= (at_lander general) waypoint0)
 (= (store_of rover0store) rover0)
 (= (store_of rover1store) rover1)
 (= (store_of rover2store) rover2)
 (= (store_of rover3store) rover3)
 (= (calibration_target camera0) objective1)
 (= (calibration_target camera1) objective0)
 (= (calibration_target camera2) objective0)
 (= (calibration_target camera3) objective1)
 (= (on_board camera0) rover3)
 (= (on_board camera1) rover3)
 (= (on_board camera2) rover1)
 (= (on_board camera3) rover2)
)
(:global-goal (and
 (communicated_soil_data waypoint1)
 (communicated_soil_data waypoint3)
 (communicated_soil_data waypoint4)
 (communicated_rock_data waypoint5)
 (communicated_rock_data waypoint4)
 (communicated_image_data objective0 low_res)
 (communicated_image_data objective0 high_res)
 (communicated_image_data objective2 low_res)
))
)
