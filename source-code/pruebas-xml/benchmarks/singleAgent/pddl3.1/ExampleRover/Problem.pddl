(define (problem pfile6)
(:domain rover)
(:objects
 B - rover
 W1 W2 W3 W4 - waypoint
 Bstore - store
 cam1 - camera
 low_res - mode
 L - lander
 obj1 - objective
)
(:init

 (not (vis W1 W1))
 (vis W1 W2)
 (vis W1 W3)
 (vis W1 W4)
 (vis W2 W1)
 (not (vis W2 W2))
 (vis W2 W3)
 (vis W2 W4)
 (vis W3 W1)
 (vis W3 W2)
 (not (vis W3 W3))
 (vis W3 W4)
 (vis W4 W1)
 (vis W4 W2)
 (vis W4 W3)
 (not (vis W4 W4))

 (= (loc_soil W1) Yes)
 (= (loc_soil W2) Yes)
 (= (loc_soil W3) Yes)
 (= (loc_soil W4) Yes)
 (= (loc_rock W1) Yes)
 (= (loc_rock W2) Yes)
 (= (loc_rock W3) Yes)
 (= (loc_rock W4) Yes)

 ;(= (status Bstore) Empty)

 (= (equipped_for_soil_analysis B) Yes)
 (= (equipped_for_rock_analysis B) Yes)
 (= (equipped_for_imaging B) Yes)

 (not (link B W1 W1))
 (link B W1 W2)
 (link B W1 W3)
 (link B W1 W4)
 (link B W2 W1)
 (not (link B W2 W2))
 (link B W2 W3)
 (link B W2 W4)
 (link B W3 W1)
 (link B W3 W2)
 (not (link B W3 W3))
 (link B W3 W4)
 (link B W4 W1)
 (link B W4 W2)
 (link B W4 W3)
 (not (link B W4 W4))

 (= (have_rock B W1) Not)
 (= (have_rock B W2) Not)
 (= (have_rock B W3) Not)
 (= (have_rock B W4) Not)
 (= (have_soil B W1) Not)
 (= (have_soil B W2) Not)
 (= (have_soil B W3) Not)
 (= (have_soil B W4) Not)


 (not (com_soil W1))
 (not (com_soil W2))
 (not (com_soil W3))
 (not (com_soil W4))
 (not (com_rock W1))
 (not (com_rock W2))
 (not (com_rock W3))
 (not (com_rock W4))

 (supports cam1 low_res)
 (= (calibration_target cam1) obj1)

 (= (calibrated cam1 B) Not)
 (= (have_image B obj1 low_res) Not)
 (not (com_image obj1 low_res))

 (vis_from obj1 W1)
 (vis_from obj1 W2)
 (vis_from obj1 W3)
 (vis_from obj1 W4)

 (= (loc B) W1)
 (= (store_of Bstore) B)
 (= (on_board cam1) B)

 (= (loc_lander L) W3)

 (= (more_rocks W1) Yes)
 (= (more_rocks W2) Yes)
 (= (more_rocks W3) Yes)
 (= (more_rocks W4) Yes)
 (= (more_soils W1) Yes)
 (= (more_soils W2) Yes)
 (= (more_soils W3) Yes)
 (= (more_soils W4) Yes)
)
(:global-goal (and
 (com_soil W1)
 ;(com_rock W4)
 (com_image obj1 low_res)
))
)
