(define (problem pfile12)
(:domain rover)
(:objects
 rover0 rover1 rover2 rover3 - rover
 waypoint0 waypoint1 waypoint2 waypoint3 waypoint4 waypoint5 waypoint6 waypoint7 waypoint8 waypoint9 - waypoint
 rover0store rover1store rover2store rover3store - store
 camera0 camera1 camera2 camera3 camera4 - camera
 colour high_res low_res - mode
 general - lander
 objective0 objective1 objective2 objective3 - objective
)
(:init 
 
 (= (status rover0store) Empty)
 (= (status rover1store) Empty)
 (= (status rover2store) Empty)
 (= (status rover3store) Empty)
 
 (= (equipped_for_soil_analysis rover0) Yes)
 (= (equipped_for_rock_analysis rover0) Not)
 (= (equipped_for_imaging rover0) Yes)
 
 (not (can_traverse rover0 waypoint0 waypoint0))
 (not (can_traverse rover0 waypoint0 waypoint1))
 (not (can_traverse rover0 waypoint0 waypoint2))
 (not (can_traverse rover0 waypoint0 waypoint3))
 (can_traverse rover0 waypoint0 waypoint4)
 (not (can_traverse rover0 waypoint0 waypoint5))
 (not (can_traverse rover0 waypoint0 waypoint6))
 (not (can_traverse rover0 waypoint0 waypoint7))
 (not (can_traverse rover0 waypoint0 waypoint8))
 (not (can_traverse rover0 waypoint0 waypoint9))
 (not (can_traverse rover0 waypoint1 waypoint0))
 (not (can_traverse rover0 waypoint1 waypoint1))
 (can_traverse rover0 waypoint1 waypoint2)
 (not (can_traverse rover0 waypoint1 waypoint3))
 (can_traverse rover0 waypoint1 waypoint4)
 (not (can_traverse rover0 waypoint1 waypoint5))
 (can_traverse rover0 waypoint1 waypoint6)
 (not (can_traverse rover0 waypoint1 waypoint7))
 (can_traverse rover0 waypoint1 waypoint8)
 (not (can_traverse rover0 waypoint1 waypoint9))
 (not (can_traverse rover0 waypoint2 waypoint0))
 (can_traverse rover0 waypoint2 waypoint1)
 (not (can_traverse rover0 waypoint2 waypoint2))
 (can_traverse rover0 waypoint2 waypoint3)
 (not (can_traverse rover0 waypoint2 waypoint4))
 (not (can_traverse rover0 waypoint2 waypoint5))
 (not (can_traverse rover0 waypoint2 waypoint6))
 (not (can_traverse rover0 waypoint2 waypoint7))
 (not (can_traverse rover0 waypoint2 waypoint8))
 (not (can_traverse rover0 waypoint2 waypoint9))
 (not (can_traverse rover0 waypoint3 waypoint0))
 (not (can_traverse rover0 waypoint3 waypoint1))
 (can_traverse rover0 waypoint3 waypoint2)
 (not (can_traverse rover0 waypoint3 waypoint3))
 (not (can_traverse rover0 waypoint3 waypoint4))
 (not (can_traverse rover0 waypoint3 waypoint5))
 (not (can_traverse rover0 waypoint3 waypoint6))
 (not (can_traverse rover0 waypoint3 waypoint7))
 (not (can_traverse rover0 waypoint3 waypoint8))
 (not (can_traverse rover0 waypoint3 waypoint9))
 (can_traverse rover0 waypoint4 waypoint0)
 (can_traverse rover0 waypoint4 waypoint1)
 (not (can_traverse rover0 waypoint4 waypoint2))
 (not (can_traverse rover0 waypoint4 waypoint3))
 (not (can_traverse rover0 waypoint4 waypoint4))
 (can_traverse rover0 waypoint4 waypoint5)
 (not (can_traverse rover0 waypoint4 waypoint6))
 (not (can_traverse rover0 waypoint4 waypoint7))
 (not (can_traverse rover0 waypoint4 waypoint8))
 (can_traverse rover0 waypoint4 waypoint9)
 (not (can_traverse rover0 waypoint5 waypoint0))
 (not (can_traverse rover0 waypoint5 waypoint1))
 (not (can_traverse rover0 waypoint5 waypoint2))
 (not (can_traverse rover0 waypoint5 waypoint3))
 (can_traverse rover0 waypoint5 waypoint4)
 (not (can_traverse rover0 waypoint5 waypoint5))
 (not (can_traverse rover0 waypoint5 waypoint6))
 (not (can_traverse rover0 waypoint5 waypoint7))
 (not (can_traverse rover0 waypoint5 waypoint8))
 (not (can_traverse rover0 waypoint5 waypoint9))
 (not (can_traverse rover0 waypoint6 waypoint0))
 (can_traverse rover0 waypoint6 waypoint1)
 (not (can_traverse rover0 waypoint6 waypoint2))
 (not (can_traverse rover0 waypoint6 waypoint3))
 (not (can_traverse rover0 waypoint6 waypoint4))
 (not (can_traverse rover0 waypoint6 waypoint5))
 (not (can_traverse rover0 waypoint6 waypoint6))
 (not (can_traverse rover0 waypoint6 waypoint7))
 (not (can_traverse rover0 waypoint6 waypoint8))
 (not (can_traverse rover0 waypoint6 waypoint9))
 (not (can_traverse rover0 waypoint7 waypoint0))
 (not (can_traverse rover0 waypoint7 waypoint1))
 (not (can_traverse rover0 waypoint7 waypoint2))
 (not (can_traverse rover0 waypoint7 waypoint3))
 (not (can_traverse rover0 waypoint7 waypoint4))
 (not (can_traverse rover0 waypoint7 waypoint5))
 (not (can_traverse rover0 waypoint7 waypoint6))
 (not (can_traverse rover0 waypoint7 waypoint7))
 (can_traverse rover0 waypoint7 waypoint8)
 (not (can_traverse rover0 waypoint7 waypoint9))
 (not (can_traverse rover0 waypoint8 waypoint0))
 (can_traverse rover0 waypoint8 waypoint1)
 (not (can_traverse rover0 waypoint8 waypoint2))
 (not (can_traverse rover0 waypoint8 waypoint3))
 (not (can_traverse rover0 waypoint8 waypoint4))
 (not (can_traverse rover0 waypoint8 waypoint5))
 (not (can_traverse rover0 waypoint8 waypoint6))
 (can_traverse rover0 waypoint8 waypoint7)
 (not (can_traverse rover0 waypoint8 waypoint8))
 (not (can_traverse rover0 waypoint8 waypoint9))
 (not (can_traverse rover0 waypoint9 waypoint0))
 (not (can_traverse rover0 waypoint9 waypoint1))
 (not (can_traverse rover0 waypoint9 waypoint2))
 (not (can_traverse rover0 waypoint9 waypoint3))
 (can_traverse rover0 waypoint9 waypoint4)
 (not (can_traverse rover0 waypoint9 waypoint5))
 (not (can_traverse rover0 waypoint9 waypoint6))
 (not (can_traverse rover0 waypoint9 waypoint7))
 (not (can_traverse rover0 waypoint9 waypoint8))
 (not (can_traverse rover0 waypoint9 waypoint9))
 
 (= (have_rock_analysis rover0 waypoint0) Not)
 (= (have_rock_analysis rover0 waypoint1) Not)
 (= (have_rock_analysis rover0 waypoint2) Not)
 (= (have_rock_analysis rover0 waypoint3) Not)
 (= (have_rock_analysis rover0 waypoint4) Not)
 (= (have_rock_analysis rover0 waypoint5) Not)
 (= (have_rock_analysis rover0 waypoint6) Not)
 (= (have_rock_analysis rover0 waypoint7) Not)
 (= (have_rock_analysis rover0 waypoint8) Not)
 (= (have_rock_analysis rover0 waypoint9) Not)
 (= (have_soil_analysis rover0 waypoint0) Not)
 (= (have_soil_analysis rover0 waypoint1) Not)
 (= (have_soil_analysis rover0 waypoint2) Not)
 (= (have_soil_analysis rover0 waypoint3) Not)
 (= (have_soil_analysis rover0 waypoint4) Not)
 (= (have_soil_analysis rover0 waypoint5) Not)
 (= (have_soil_analysis rover0 waypoint6) Not)
 (= (have_soil_analysis rover0 waypoint7) Not)
 (= (have_soil_analysis rover0 waypoint8) Not)
 (= (have_soil_analysis rover0 waypoint9) Not)
 
 (= (equipped_for_soil_analysis rover1) Yes)
 (= (equipped_for_rock_analysis rover1) Yes)
 (= (equipped_for_imaging rover1) Yes)
 
 (not (can_traverse rover1 waypoint0 waypoint0))
 (not (can_traverse rover1 waypoint0 waypoint1))
 (not (can_traverse rover1 waypoint0 waypoint2))
 (can_traverse rover1 waypoint0 waypoint3)
 (can_traverse rover1 waypoint0 waypoint4)
 (not (can_traverse rover1 waypoint0 waypoint5))
 (not (can_traverse rover1 waypoint0 waypoint6))
 (not (can_traverse rover1 waypoint0 waypoint7))
 (not (can_traverse rover1 waypoint0 waypoint8))
 (not (can_traverse rover1 waypoint0 waypoint9))
 (not (can_traverse rover1 waypoint1 waypoint0))
 (not (can_traverse rover1 waypoint1 waypoint1))
 (can_traverse rover1 waypoint1 waypoint2)
 (not (can_traverse rover1 waypoint1 waypoint3))
 (not (can_traverse rover1 waypoint1 waypoint4))
 (not (can_traverse rover1 waypoint1 waypoint5))
 (not (can_traverse rover1 waypoint1 waypoint6))
 (not (can_traverse rover1 waypoint1 waypoint7))
 (not (can_traverse rover1 waypoint1 waypoint8))
 (not (can_traverse rover1 waypoint1 waypoint9))
 (not (can_traverse rover1 waypoint2 waypoint0))
 (can_traverse rover1 waypoint2 waypoint1)
 (not (can_traverse rover1 waypoint2 waypoint2))
 (not (can_traverse rover1 waypoint2 waypoint3))
 (can_traverse rover1 waypoint2 waypoint4)
 (not (can_traverse rover1 waypoint2 waypoint5))
 (not (can_traverse rover1 waypoint2 waypoint6))
 (not (can_traverse rover1 waypoint2 waypoint7))
 (not (can_traverse rover1 waypoint2 waypoint8))
 (not (can_traverse rover1 waypoint2 waypoint9))
 (can_traverse rover1 waypoint3 waypoint0)
 (not (can_traverse rover1 waypoint3 waypoint1))
 (not (can_traverse rover1 waypoint3 waypoint2))
 (not (can_traverse rover1 waypoint3 waypoint3))
 (not (can_traverse rover1 waypoint3 waypoint4))
 (not (can_traverse rover1 waypoint3 waypoint5))
 (not (can_traverse rover1 waypoint3 waypoint6))
 (not (can_traverse rover1 waypoint3 waypoint7))
 (not (can_traverse rover1 waypoint3 waypoint8))
 (not (can_traverse rover1 waypoint3 waypoint9))
 (can_traverse rover1 waypoint4 waypoint0)
 (not (can_traverse rover1 waypoint4 waypoint1))
 (can_traverse rover1 waypoint4 waypoint2)
 (not (can_traverse rover1 waypoint4 waypoint3))
 (not (can_traverse rover1 waypoint4 waypoint4))
 (can_traverse rover1 waypoint4 waypoint5)
 (can_traverse rover1 waypoint4 waypoint6)
 (not (can_traverse rover1 waypoint4 waypoint7))
 (not (can_traverse rover1 waypoint4 waypoint8))
 (can_traverse rover1 waypoint4 waypoint9)
 (not (can_traverse rover1 waypoint5 waypoint0))
 (not (can_traverse rover1 waypoint5 waypoint1))
 (not (can_traverse rover1 waypoint5 waypoint2))
 (not (can_traverse rover1 waypoint5 waypoint3))
 (can_traverse rover1 waypoint5 waypoint4)
 (not (can_traverse rover1 waypoint5 waypoint5))
 (not (can_traverse rover1 waypoint5 waypoint6))
 (can_traverse rover1 waypoint5 waypoint7)
 (not (can_traverse rover1 waypoint5 waypoint8))
 (not (can_traverse rover1 waypoint5 waypoint9))
 (not (can_traverse rover1 waypoint6 waypoint0))
 (not (can_traverse rover1 waypoint6 waypoint1))
 (not (can_traverse rover1 waypoint6 waypoint2))
 (not (can_traverse rover1 waypoint6 waypoint3))
 (can_traverse rover1 waypoint6 waypoint4)
 (not (can_traverse rover1 waypoint6 waypoint5))
 (not (can_traverse rover1 waypoint6 waypoint6))
 (not (can_traverse rover1 waypoint6 waypoint7))
 (can_traverse rover1 waypoint6 waypoint8)
 (not (can_traverse rover1 waypoint6 waypoint9))
 (not (can_traverse rover1 waypoint7 waypoint0))
 (not (can_traverse rover1 waypoint7 waypoint1))
 (not (can_traverse rover1 waypoint7 waypoint2))
 (not (can_traverse rover1 waypoint7 waypoint3))
 (not (can_traverse rover1 waypoint7 waypoint4))
 (can_traverse rover1 waypoint7 waypoint5)
 (not (can_traverse rover1 waypoint7 waypoint6))
 (not (can_traverse rover1 waypoint7 waypoint7))
 (not (can_traverse rover1 waypoint7 waypoint8))
 (not (can_traverse rover1 waypoint7 waypoint9))
 (not (can_traverse rover1 waypoint8 waypoint0))
 (not (can_traverse rover1 waypoint8 waypoint1))
 (not (can_traverse rover1 waypoint8 waypoint2))
 (not (can_traverse rover1 waypoint8 waypoint3))
 (not (can_traverse rover1 waypoint8 waypoint4))
 (not (can_traverse rover1 waypoint8 waypoint5))
 (can_traverse rover1 waypoint8 waypoint6)
 (not (can_traverse rover1 waypoint8 waypoint7))
 (not (can_traverse rover1 waypoint8 waypoint8))
 (not (can_traverse rover1 waypoint8 waypoint9))
 (not (can_traverse rover1 waypoint9 waypoint0))
 (not (can_traverse rover1 waypoint9 waypoint1))
 (not (can_traverse rover1 waypoint9 waypoint2))
 (not (can_traverse rover1 waypoint9 waypoint3))
 (can_traverse rover1 waypoint9 waypoint4)
 (not (can_traverse rover1 waypoint9 waypoint5))
 (not (can_traverse rover1 waypoint9 waypoint6))
 (not (can_traverse rover1 waypoint9 waypoint7))
 (not (can_traverse rover1 waypoint9 waypoint8))
 (not (can_traverse rover1 waypoint9 waypoint9))
 
 (= (have_rock_analysis rover1 waypoint0) Not)
 (= (have_rock_analysis rover1 waypoint1) Not)
 (= (have_rock_analysis rover1 waypoint2) Not)
 (= (have_rock_analysis rover1 waypoint3) Not)
 (= (have_rock_analysis rover1 waypoint4) Not)
 (= (have_rock_analysis rover1 waypoint5) Not)
 (= (have_rock_analysis rover1 waypoint6) Not)
 (= (have_rock_analysis rover1 waypoint7) Not)
 (= (have_rock_analysis rover1 waypoint8) Not)
 (= (have_rock_analysis rover1 waypoint9) Not)
 (= (have_soil_analysis rover1 waypoint0) Not)
 (= (have_soil_analysis rover1 waypoint1) Not)
 (= (have_soil_analysis rover1 waypoint2) Not)
 (= (have_soil_analysis rover1 waypoint3) Not)
 (= (have_soil_analysis rover1 waypoint4) Not)
 (= (have_soil_analysis rover1 waypoint5) Not)
 (= (have_soil_analysis rover1 waypoint6) Not)
 (= (have_soil_analysis rover1 waypoint7) Not)
 (= (have_soil_analysis rover1 waypoint8) Not)
 (= (have_soil_analysis rover1 waypoint9) Not)
 
 (= (equipped_for_soil_analysis rover2) Not)
 (= (equipped_for_rock_analysis rover2) Not)
 (= (equipped_for_imaging rover2) Yes)
 
 (not (can_traverse rover2 waypoint0 waypoint0))
 (not (can_traverse rover2 waypoint0 waypoint1))
 (not (can_traverse rover2 waypoint0 waypoint2))
 (can_traverse rover2 waypoint0 waypoint3)
 (can_traverse rover2 waypoint0 waypoint4)
 (not (can_traverse rover2 waypoint0 waypoint5))
 (can_traverse rover2 waypoint0 waypoint6)
 (not (can_traverse rover2 waypoint0 waypoint7))
 (not (can_traverse rover2 waypoint0 waypoint8))
 (can_traverse rover2 waypoint0 waypoint9)
 (not (can_traverse rover2 waypoint1 waypoint0))
 (not (can_traverse rover2 waypoint1 waypoint1))
 (not (can_traverse rover2 waypoint1 waypoint2))
 (not (can_traverse rover2 waypoint1 waypoint3))
 (can_traverse rover2 waypoint1 waypoint4)
 (not (can_traverse rover2 waypoint1 waypoint5))
 (not (can_traverse rover2 waypoint1 waypoint6))
 (not (can_traverse rover2 waypoint1 waypoint7))
 (not (can_traverse rover2 waypoint1 waypoint8))
 (not (can_traverse rover2 waypoint1 waypoint9))
 (not (can_traverse rover2 waypoint2 waypoint0))
 (not (can_traverse rover2 waypoint2 waypoint1))
 (not (can_traverse rover2 waypoint2 waypoint2))
 (can_traverse rover2 waypoint2 waypoint3)
 (not (can_traverse rover2 waypoint2 waypoint4))
 (not (can_traverse rover2 waypoint2 waypoint5))
 (not (can_traverse rover2 waypoint2 waypoint6))
 (not (can_traverse rover2 waypoint2 waypoint7))
 (not (can_traverse rover2 waypoint2 waypoint8))
 (not (can_traverse rover2 waypoint2 waypoint9))
 (can_traverse rover2 waypoint3 waypoint0)
 (not (can_traverse rover2 waypoint3 waypoint1))
 (can_traverse rover2 waypoint3 waypoint2)
 (not (can_traverse rover2 waypoint3 waypoint3))
 (not (can_traverse rover2 waypoint3 waypoint4))
 (not (can_traverse rover2 waypoint3 waypoint5))
 (not (can_traverse rover2 waypoint3 waypoint6))
 (not (can_traverse rover2 waypoint3 waypoint7))
 (not (can_traverse rover2 waypoint3 waypoint8))
 (not (can_traverse rover2 waypoint3 waypoint9))
 (can_traverse rover2 waypoint4 waypoint0)
 (can_traverse rover2 waypoint4 waypoint1)
 (not (can_traverse rover2 waypoint4 waypoint2))
 (not (can_traverse rover2 waypoint4 waypoint3))
 (not (can_traverse rover2 waypoint4 waypoint4))
 (can_traverse rover2 waypoint4 waypoint5)
 (not (can_traverse rover2 waypoint4 waypoint6))
 (not (can_traverse rover2 waypoint4 waypoint7))
 (can_traverse rover2 waypoint4 waypoint8)
 (not (can_traverse rover2 waypoint4 waypoint9))
 (not (can_traverse rover2 waypoint5 waypoint0))
 (not (can_traverse rover2 waypoint5 waypoint1))
 (not (can_traverse rover2 waypoint5 waypoint2))
 (not (can_traverse rover2 waypoint5 waypoint3))
 (can_traverse rover2 waypoint5 waypoint4)
 (not (can_traverse rover2 waypoint5 waypoint5))
 (not (can_traverse rover2 waypoint5 waypoint6))
 (not (can_traverse rover2 waypoint5 waypoint7))
 (not (can_traverse rover2 waypoint5 waypoint8))
 (not (can_traverse rover2 waypoint5 waypoint9))
 (can_traverse rover2 waypoint6 waypoint0)
 (not (can_traverse rover2 waypoint6 waypoint1))
 (not (can_traverse rover2 waypoint6 waypoint2))
 (not (can_traverse rover2 waypoint6 waypoint3))
 (not (can_traverse rover2 waypoint6 waypoint4))
 (not (can_traverse rover2 waypoint6 waypoint5))
 (not (can_traverse rover2 waypoint6 waypoint6))
 (not (can_traverse rover2 waypoint6 waypoint7))
 (not (can_traverse rover2 waypoint6 waypoint8))
 (not (can_traverse rover2 waypoint6 waypoint9))
 (not (can_traverse rover2 waypoint7 waypoint0))
 (not (can_traverse rover2 waypoint7 waypoint1))
 (not (can_traverse rover2 waypoint7 waypoint2))
 (not (can_traverse rover2 waypoint7 waypoint3))
 (not (can_traverse rover2 waypoint7 waypoint4))
 (not (can_traverse rover2 waypoint7 waypoint5))
 (not (can_traverse rover2 waypoint7 waypoint6))
 (not (can_traverse rover2 waypoint7 waypoint7))
 (not (can_traverse rover2 waypoint7 waypoint8))
 (not (can_traverse rover2 waypoint7 waypoint9))
 (not (can_traverse rover2 waypoint8 waypoint0))
 (not (can_traverse rover2 waypoint8 waypoint1))
 (not (can_traverse rover2 waypoint8 waypoint2))
 (not (can_traverse rover2 waypoint8 waypoint3))
 (can_traverse rover2 waypoint8 waypoint4)
 (not (can_traverse rover2 waypoint8 waypoint5))
 (not (can_traverse rover2 waypoint8 waypoint6))
 (not (can_traverse rover2 waypoint8 waypoint7))
 (not (can_traverse rover2 waypoint8 waypoint8))
 (not (can_traverse rover2 waypoint8 waypoint9))
 (can_traverse rover2 waypoint9 waypoint0)
 (not (can_traverse rover2 waypoint9 waypoint1))
 (not (can_traverse rover2 waypoint9 waypoint2))
 (not (can_traverse rover2 waypoint9 waypoint3))
 (not (can_traverse rover2 waypoint9 waypoint4))
 (not (can_traverse rover2 waypoint9 waypoint5))
 (not (can_traverse rover2 waypoint9 waypoint6))
 (not (can_traverse rover2 waypoint9 waypoint7))
 (not (can_traverse rover2 waypoint9 waypoint8))
 (not (can_traverse rover2 waypoint9 waypoint9))
 
 (= (have_rock_analysis rover2 waypoint0) Not)
 (= (have_rock_analysis rover2 waypoint1) Not)
 (= (have_rock_analysis rover2 waypoint2) Not)
 (= (have_rock_analysis rover2 waypoint3) Not)
 (= (have_rock_analysis rover2 waypoint4) Not)
 (= (have_rock_analysis rover2 waypoint5) Not)
 (= (have_rock_analysis rover2 waypoint6) Not)
 (= (have_rock_analysis rover2 waypoint7) Not)
 (= (have_rock_analysis rover2 waypoint8) Not)
 (= (have_rock_analysis rover2 waypoint9) Not)
 (= (have_soil_analysis rover2 waypoint0) Not)
 (= (have_soil_analysis rover2 waypoint1) Not)
 (= (have_soil_analysis rover2 waypoint2) Not)
 (= (have_soil_analysis rover2 waypoint3) Not)
 (= (have_soil_analysis rover2 waypoint4) Not)
 (= (have_soil_analysis rover2 waypoint5) Not)
 (= (have_soil_analysis rover2 waypoint6) Not)
 (= (have_soil_analysis rover2 waypoint7) Not)
 (= (have_soil_analysis rover2 waypoint8) Not)
 (= (have_soil_analysis rover2 waypoint9) Not)
 
 (= (equipped_for_soil_analysis rover3) Not)
 (= (equipped_for_rock_analysis rover3) Not)
 (= (equipped_for_imaging rover3) Yes)
 
 (not (can_traverse rover3 waypoint0 waypoint0))
 (not (can_traverse rover3 waypoint0 waypoint1))
 (not (can_traverse rover3 waypoint0 waypoint2))
 (not (can_traverse rover3 waypoint0 waypoint3))
 (not (can_traverse rover3 waypoint0 waypoint4))
 (not (can_traverse rover3 waypoint0 waypoint5))
 (can_traverse rover3 waypoint0 waypoint6)
 (not (can_traverse rover3 waypoint0 waypoint7))
 (not (can_traverse rover3 waypoint0 waypoint8))
 (not (can_traverse rover3 waypoint0 waypoint9))
 (not (can_traverse rover3 waypoint1 waypoint0))
 (not (can_traverse rover3 waypoint1 waypoint1))
 (can_traverse rover3 waypoint1 waypoint2)
 (not (can_traverse rover3 waypoint1 waypoint3))
 (can_traverse rover3 waypoint1 waypoint4)
 (can_traverse rover3 waypoint1 waypoint5)
 (not (can_traverse rover3 waypoint1 waypoint6))
 (can_traverse rover3 waypoint1 waypoint7)
 (not (can_traverse rover3 waypoint1 waypoint8))
 (not (can_traverse rover3 waypoint1 waypoint9))
 (not (can_traverse rover3 waypoint2 waypoint0))
 (can_traverse rover3 waypoint2 waypoint1)
 (not (can_traverse rover3 waypoint2 waypoint2))
 (not (can_traverse rover3 waypoint2 waypoint3))
 (not (can_traverse rover3 waypoint2 waypoint4))
 (not (can_traverse rover3 waypoint2 waypoint5))
 (can_traverse rover3 waypoint2 waypoint6)
 (not (can_traverse rover3 waypoint2 waypoint7))
 (not (can_traverse rover3 waypoint2 waypoint8))
 (can_traverse rover3 waypoint2 waypoint9)
 (not (can_traverse rover3 waypoint3 waypoint0))
 (not (can_traverse rover3 waypoint3 waypoint1))
 (not (can_traverse rover3 waypoint3 waypoint2))
 (not (can_traverse rover3 waypoint3 waypoint3))
 (not (can_traverse rover3 waypoint3 waypoint4))
 (not (can_traverse rover3 waypoint3 waypoint5))
 (can_traverse rover3 waypoint3 waypoint6)
 (not (can_traverse rover3 waypoint3 waypoint7))
 (not (can_traverse rover3 waypoint3 waypoint8))
 (not (can_traverse rover3 waypoint3 waypoint9))
 (not (can_traverse rover3 waypoint4 waypoint0))
 (can_traverse rover3 waypoint4 waypoint1)
 (not (can_traverse rover3 waypoint4 waypoint2))
 (not (can_traverse rover3 waypoint4 waypoint3))
 (not (can_traverse rover3 waypoint4 waypoint4))
 (not (can_traverse rover3 waypoint4 waypoint5))
 (not (can_traverse rover3 waypoint4 waypoint6))
 (not (can_traverse rover3 waypoint4 waypoint7))
 (not (can_traverse rover3 waypoint4 waypoint8))
 (not (can_traverse rover3 waypoint4 waypoint9))
 (not (can_traverse rover3 waypoint5 waypoint0))
 (can_traverse rover3 waypoint5 waypoint1)
 (not (can_traverse rover3 waypoint5 waypoint2))
 (not (can_traverse rover3 waypoint5 waypoint3))
 (not (can_traverse rover3 waypoint5 waypoint4))
 (not (can_traverse rover3 waypoint5 waypoint5))
 (not (can_traverse rover3 waypoint5 waypoint6))
 (not (can_traverse rover3 waypoint5 waypoint7))
 (not (can_traverse rover3 waypoint5 waypoint8))
 (not (can_traverse rover3 waypoint5 waypoint9))
 (can_traverse rover3 waypoint6 waypoint0)
 (not (can_traverse rover3 waypoint6 waypoint1))
 (can_traverse rover3 waypoint6 waypoint2)
 (can_traverse rover3 waypoint6 waypoint3)
 (not (can_traverse rover3 waypoint6 waypoint4))
 (not (can_traverse rover3 waypoint6 waypoint5))
 (not (can_traverse rover3 waypoint6 waypoint6))
 (not (can_traverse rover3 waypoint6 waypoint7))
 (can_traverse rover3 waypoint6 waypoint8)
 (not (can_traverse rover3 waypoint6 waypoint9))
 (not (can_traverse rover3 waypoint7 waypoint0))
 (can_traverse rover3 waypoint7 waypoint1)
 (not (can_traverse rover3 waypoint7 waypoint2))
 (not (can_traverse rover3 waypoint7 waypoint3))
 (not (can_traverse rover3 waypoint7 waypoint4))
 (not (can_traverse rover3 waypoint7 waypoint5))
 (not (can_traverse rover3 waypoint7 waypoint6))
 (not (can_traverse rover3 waypoint7 waypoint7))
 (not (can_traverse rover3 waypoint7 waypoint8))
 (not (can_traverse rover3 waypoint7 waypoint9))
 (not (can_traverse rover3 waypoint8 waypoint0))
 (not (can_traverse rover3 waypoint8 waypoint1))
 (not (can_traverse rover3 waypoint8 waypoint2))
 (not (can_traverse rover3 waypoint8 waypoint3))
 (not (can_traverse rover3 waypoint8 waypoint4))
 (not (can_traverse rover3 waypoint8 waypoint5))
 (can_traverse rover3 waypoint8 waypoint6)
 (not (can_traverse rover3 waypoint8 waypoint7))
 (not (can_traverse rover3 waypoint8 waypoint8))
 (not (can_traverse rover3 waypoint8 waypoint9))
 (not (can_traverse rover3 waypoint9 waypoint0))
 (not (can_traverse rover3 waypoint9 waypoint1))
 (can_traverse rover3 waypoint9 waypoint2)
 (not (can_traverse rover3 waypoint9 waypoint3))
 (not (can_traverse rover3 waypoint9 waypoint4))
 (not (can_traverse rover3 waypoint9 waypoint5))
 (not (can_traverse rover3 waypoint9 waypoint6))
 (not (can_traverse rover3 waypoint9 waypoint7))
 (not (can_traverse rover3 waypoint9 waypoint8))
 (not (can_traverse rover3 waypoint9 waypoint9))
 
 (= (have_rock_analysis rover3 waypoint0) Not)
 (= (have_rock_analysis rover3 waypoint1) Not)
 (= (have_rock_analysis rover3 waypoint2) Not)
 (= (have_rock_analysis rover3 waypoint3) Not)
 (= (have_rock_analysis rover3 waypoint4) Not)
 (= (have_rock_analysis rover3 waypoint5) Not)
 (= (have_rock_analysis rover3 waypoint6) Not)
 (= (have_rock_analysis rover3 waypoint7) Not)
 (= (have_rock_analysis rover3 waypoint8) Not)
 (= (have_rock_analysis rover3 waypoint9) Not)
 (= (have_soil_analysis rover3 waypoint0) Not)
 (= (have_soil_analysis rover3 waypoint1) Not)
 (= (have_soil_analysis rover3 waypoint2) Not)
 (= (have_soil_analysis rover3 waypoint3) Not)
 (= (have_soil_analysis rover3 waypoint4) Not)
 (= (have_soil_analysis rover3 waypoint5) Not)
 (= (have_soil_analysis rover3 waypoint6) Not)
 (= (have_soil_analysis rover3 waypoint7) Not)
 (= (have_soil_analysis rover3 waypoint8) Not)
 (= (have_soil_analysis rover3 waypoint9) Not)
 
 (not (visible waypoint0 waypoint0))
 (not (visible waypoint0 waypoint1))
 (not (visible waypoint0 waypoint2))
 (visible waypoint0 waypoint3)
 (visible waypoint0 waypoint4)
 (not (visible waypoint0 waypoint5))
 (visible waypoint0 waypoint6)
 (not (visible waypoint0 waypoint7))
 (not (visible waypoint0 waypoint8))
 (visible waypoint0 waypoint9)
 (not (visible waypoint1 waypoint0))
 (not (visible waypoint1 waypoint1))
 (visible waypoint1 waypoint2)
 (not (visible waypoint1 waypoint3))
 (visible waypoint1 waypoint4)
 (visible waypoint1 waypoint5)
 (visible waypoint1 waypoint6)
 (visible waypoint1 waypoint7)
 (visible waypoint1 waypoint8)
 (not (visible waypoint1 waypoint9))
 (not (visible waypoint2 waypoint0))
 (visible waypoint2 waypoint1)
 (not (visible waypoint2 waypoint2))
 (visible waypoint2 waypoint3)
 (visible waypoint2 waypoint4)
 (visible waypoint2 waypoint5)
 (visible waypoint2 waypoint6)
 (not (visible waypoint2 waypoint7))
 (not (visible waypoint2 waypoint8))
 (visible waypoint2 waypoint9)
 (visible waypoint3 waypoint0)
 (not (visible waypoint3 waypoint1))
 (visible waypoint3 waypoint2)
 (not (visible waypoint3 waypoint3))
 (not (visible waypoint3 waypoint4))
 (visible waypoint3 waypoint5)
 (visible waypoint3 waypoint6)
 (visible waypoint3 waypoint7)
 (not (visible waypoint3 waypoint8))
 (visible waypoint3 waypoint9)
 (visible waypoint4 waypoint0)
 (visible waypoint4 waypoint1)
 (visible waypoint4 waypoint2)
 (not (visible waypoint4 waypoint3))
 (not (visible waypoint4 waypoint4))
 (visible waypoint4 waypoint5)
 (visible waypoint4 waypoint6)
 (not (visible waypoint4 waypoint7))
 (visible waypoint4 waypoint8)
 (visible waypoint4 waypoint9)
 (not (visible waypoint5 waypoint0))
 (visible waypoint5 waypoint1)
 (visible waypoint5 waypoint2)
 (visible waypoint5 waypoint3)
 (visible waypoint5 waypoint4)
 (not (visible waypoint5 waypoint5))
 (visible waypoint5 waypoint6)
 (visible waypoint5 waypoint7)
 (not (visible waypoint5 waypoint8))
 (not (visible waypoint5 waypoint9))
 (visible waypoint6 waypoint0)
 (visible waypoint6 waypoint1)
 (visible waypoint6 waypoint2)
 (visible waypoint6 waypoint3)
 (visible waypoint6 waypoint4)
 (visible waypoint6 waypoint5)
 (not (visible waypoint6 waypoint6))
 (not (visible waypoint6 waypoint7))
 (visible waypoint6 waypoint8)
 (visible waypoint6 waypoint9)
 (not (visible waypoint7 waypoint0))
 (visible waypoint7 waypoint1)
 (not (visible waypoint7 waypoint2))
 (visible waypoint7 waypoint3)
 (not (visible waypoint7 waypoint4))
 (visible waypoint7 waypoint5)
 (not (visible waypoint7 waypoint6))
 (not (visible waypoint7 waypoint7))
 (visible waypoint7 waypoint8)
 (not (visible waypoint7 waypoint9))
 (not (visible waypoint8 waypoint0))
 (visible waypoint8 waypoint1)
 (not (visible waypoint8 waypoint2))
 (not (visible waypoint8 waypoint3))
 (visible waypoint8 waypoint4)
 (not (visible waypoint8 waypoint5))
 (visible waypoint8 waypoint6)
 (visible waypoint8 waypoint7)
 (not (visible waypoint8 waypoint8))
 (visible waypoint8 waypoint9)
 (visible waypoint9 waypoint0)
 (not (visible waypoint9 waypoint1))
 (visible waypoint9 waypoint2)
 (visible waypoint9 waypoint3)
 (visible waypoint9 waypoint4)
 (not (visible waypoint9 waypoint5))
 (visible waypoint9 waypoint6)
 (not (visible waypoint9 waypoint7))
 (visible waypoint9 waypoint8)
 (not (visible waypoint9 waypoint9))
 
 (not (communicated_soil_data waypoint0))
 (not (communicated_soil_data waypoint1))
 (not (communicated_soil_data waypoint2))
 (not (communicated_soil_data waypoint3))
 (not (communicated_soil_data waypoint4))
 (not (communicated_soil_data waypoint5))
 (not (communicated_soil_data waypoint6))
 (not (communicated_soil_data waypoint7))
 (not (communicated_soil_data waypoint8))
 (not (communicated_soil_data waypoint9))
 (not (communicated_rock_data waypoint0))
 (not (communicated_rock_data waypoint1))
 (not (communicated_rock_data waypoint2))
 (not (communicated_rock_data waypoint3))
 (not (communicated_rock_data waypoint4))
 (not (communicated_rock_data waypoint5))
 (not (communicated_rock_data waypoint6))
 (not (communicated_rock_data waypoint7))
 (not (communicated_rock_data waypoint8))
 (not (communicated_rock_data waypoint9))
 
 (= (at_soil_sample waypoint0) Not)
 (= (at_soil_sample waypoint1) Not)
 (= (at_soil_sample waypoint2) Not)
 (= (at_soil_sample waypoint3) Yes)
 (= (at_soil_sample waypoint4) Yes)
 (= (at_soil_sample waypoint5) Not)
 (= (at_soil_sample waypoint6) Yes)
 (= (at_soil_sample waypoint7) Yes)
 (= (at_soil_sample waypoint8) Not)
 (= (at_soil_sample waypoint9) Yes)
 (= (at_rock_sample waypoint0) Not)
 (= (at_rock_sample waypoint1) Yes)
 (= (at_rock_sample waypoint2) Yes)
 (= (at_rock_sample waypoint3) Yes)
 (= (at_rock_sample waypoint4) Yes)
 (= (at_rock_sample waypoint5) Yes)
 (= (at_rock_sample waypoint6) Not)
 (= (at_rock_sample waypoint7) Not)
 (= (at_rock_sample waypoint8) Yes)
 (= (at_rock_sample waypoint9) Not)
 
 (=(calibrated camera0 rover0) Not)
 (=(calibrated camera1 rover0) Not)
 (=(calibrated camera2 rover0) Not)
 (=(calibrated camera3 rover0) Not)
 (=(calibrated camera4 rover0) Not)
 (=(calibrated camera0 rover1) Not)
 (=(calibrated camera1 rover1) Not)
 (=(calibrated camera2 rover1) Not)
 (=(calibrated camera3 rover1) Not)
 (=(calibrated camera4 rover1) Not)
 (=(calibrated camera0 rover2) Not)
 (=(calibrated camera1 rover2) Not)
 (=(calibrated camera2 rover2) Not)
 (=(calibrated camera3 rover2) Not)
 (=(calibrated camera4 rover2) Not)
 (=(calibrated camera0 rover3) Not)
 (=(calibrated camera1 rover3) Not)
 (=(calibrated camera2 rover3) Not)
 (=(calibrated camera3 rover3) Not)
 (=(calibrated camera4 rover3) Not)
 
 (supports camera0 colour)
 (not (supports camera0 high_res))
 (supports camera0 low_res)
 (supports camera1 colour)
 (not (supports camera1 high_res))
 (not (supports camera1 low_res))
 (not (supports camera2 colour))
 (not (supports camera2 high_res))
 (supports camera2 low_res)
 (supports camera3 colour)
 (not (supports camera3 high_res))
 (supports camera3 low_res)
 (supports camera4 colour)
 (not (supports camera4 high_res))
 (supports camera4 low_res)
 
 (= (have_image rover0 objective0 colour) Not)
 (= (have_image rover0 objective0 high_res) Not)
 (= (have_image rover0 objective0 low_res) Not)
 (= (have_image rover0 objective1 colour) Not)
 (= (have_image rover0 objective1 high_res) Not)
 (= (have_image rover0 objective1 low_res) Not)
 (= (have_image rover0 objective2 colour) Not)
 (= (have_image rover0 objective2 high_res) Not)
 (= (have_image rover0 objective2 low_res) Not)
 (= (have_image rover0 objective3 colour) Not)
 (= (have_image rover0 objective3 high_res) Not)
 (= (have_image rover0 objective3 low_res) Not)
 (= (have_image rover1 objective0 colour) Not)
 (= (have_image rover1 objective0 high_res) Not)
 (= (have_image rover1 objective0 low_res) Not)
 (= (have_image rover1 objective1 colour) Not)
 (= (have_image rover1 objective1 high_res) Not)
 (= (have_image rover1 objective1 low_res) Not)
 (= (have_image rover1 objective2 colour) Not)
 (= (have_image rover1 objective2 high_res) Not)
 (= (have_image rover1 objective2 low_res) Not)
 (= (have_image rover1 objective3 colour) Not)
 (= (have_image rover1 objective3 high_res) Not)
 (= (have_image rover1 objective3 low_res) Not)
 (= (have_image rover2 objective0 colour) Not)
 (= (have_image rover2 objective0 high_res) Not)
 (= (have_image rover2 objective0 low_res) Not)
 (= (have_image rover2 objective1 colour) Not)
 (= (have_image rover2 objective1 high_res) Not)
 (= (have_image rover2 objective1 low_res) Not)
 (= (have_image rover2 objective2 colour) Not)
 (= (have_image rover2 objective2 high_res) Not)
 (= (have_image rover2 objective2 low_res) Not)
 (= (have_image rover2 objective3 colour) Not)
 (= (have_image rover2 objective3 high_res) Not)
 (= (have_image rover2 objective3 low_res) Not)
 (= (have_image rover3 objective0 colour) Not)
 (= (have_image rover3 objective0 high_res) Not)
 (= (have_image rover3 objective0 low_res) Not)
 (= (have_image rover3 objective1 colour) Not)
 (= (have_image rover3 objective1 high_res) Not)
 (= (have_image rover3 objective1 low_res) Not)
 (= (have_image rover3 objective2 colour) Not)
 (= (have_image rover3 objective2 high_res) Not)
 (= (have_image rover3 objective2 low_res) Not)
 (= (have_image rover3 objective3 colour) Not)
 (= (have_image rover3 objective3 high_res) Not)
 (= (have_image rover3 objective3 low_res) Not)
 
 (not (communicated_image_data objective0 colour))
 (not (communicated_image_data objective0 high_res))
 (not (communicated_image_data objective0 low_res))
 (not (communicated_image_data objective1 colour))
 (not (communicated_image_data objective1 high_res))
 (not (communicated_image_data objective1 low_res))
 (not (communicated_image_data objective2 colour))
 (not (communicated_image_data objective2 high_res))
 (not (communicated_image_data objective2 low_res))
 (not (communicated_image_data objective3 colour))
 (not (communicated_image_data objective3 high_res))
 (not (communicated_image_data objective3 low_res))
 
 (visible_from objective0 waypoint0)
 (visible_from objective0 waypoint1)
 (visible_from objective0 waypoint2)
 (visible_from objective0 waypoint3)
 (visible_from objective0 waypoint4)
 (visible_from objective0 waypoint5)
 (visible_from objective0 waypoint6)
 (not (visible_from objective0 waypoint7))
 (not (visible_from objective0 waypoint8))
 (not (visible_from objective0 waypoint9))
 (visible_from objective1 waypoint0)
 (visible_from objective1 waypoint1)
 (visible_from objective1 waypoint2)
 (visible_from objective1 waypoint3)
 (visible_from objective1 waypoint4)
 (visible_from objective1 waypoint5)
 (visible_from objective1 waypoint6)
 (not (visible_from objective1 waypoint7))
 (not (visible_from objective1 waypoint8))
 (not (visible_from objective1 waypoint9))
 (visible_from objective2 waypoint0)
 (visible_from objective2 waypoint1)
 (visible_from objective2 waypoint2)
 (visible_from objective2 waypoint3)
 (visible_from objective2 waypoint4)
 (visible_from objective2 waypoint5)
 (visible_from objective2 waypoint6)
 (visible_from objective2 waypoint7)
 (visible_from objective2 waypoint8)
 (not (visible_from objective2 waypoint9))
 (visible_from objective3 waypoint0)
 (visible_from objective3 waypoint1)
 (visible_from objective3 waypoint2)
 (visible_from objective3 waypoint3)
 (visible_from objective3 waypoint4)
 (visible_from objective3 waypoint5)
 (visible_from objective3 waypoint6)
 (not (visible_from objective3 waypoint7))
 (not (visible_from objective3 waypoint8))
 (not (visible_from objective3 waypoint9))
 
 (= (at rover0) waypoint1)
 (= (at rover1) waypoint4)
 (= (at rover2) waypoint2)
 (= (at rover3) waypoint7)
 (= (at_lander general) waypoint1)
 (= (store_of rover0store) rover0)
 (= (store_of rover1store) rover1)
 (= (store_of rover2store) rover2)
 (= (store_of rover3store) rover3)
 
 (= (calibration_target camera0) objective0)
 (= (calibration_target camera1) objective3)
 (= (calibration_target camera2) objective3)
 (= (calibration_target camera3) objective0)
 (= (calibration_target camera4) objective3)
 
 (= (on_board camera0) rover3)
 (= (on_board camera1) rover2)
 (= (on_board camera2) rover1)
 (= (on_board camera3) rover1)
 (= (on_board camera4) rover0)
 
 (= (more_rocks waypoint0) Yes)
 (= (more_rocks waypoint1) Yes)
 (= (more_rocks waypoint2) Yes)
 (= (more_rocks waypoint3) Yes)
 (= (more_rocks waypoint4) Yes)
 (= (more_rocks waypoint5) Yes)
 (= (more_rocks waypoint6) Yes)
 (= (more_rocks waypoint7) Yes)
 (= (more_rocks waypoint8) Yes)
 (= (more_rocks waypoint9) Yes)
 (= (more_soils waypoint0) Yes)
 (= (more_soils waypoint1) Yes)
 (= (more_soils waypoint2) Yes)
 (= (more_soils waypoint3) Yes)
 (= (more_soils waypoint4) Yes)
 (= (more_soils waypoint5) Yes)
 (= (more_soils waypoint6) Yes)
 (= (more_soils waypoint7) Yes)
 (= (more_soils waypoint8) Yes)
 (= (more_soils waypoint9) Yes)
)
(:global-goal (and
 (communicated_soil_data waypoint6)
 (communicated_soil_data waypoint3)
 (communicated_soil_data waypoint7)
 (communicated_rock_data waypoint2)
 (communicated_rock_data waypoint5)
 (communicated_rock_data waypoint4)
 (communicated_rock_data waypoint8)
 (communicated_image_data objective0 colour)
 (communicated_image_data objective2 low_res)
 (communicated_image_data objective0 low_res)
))
)
