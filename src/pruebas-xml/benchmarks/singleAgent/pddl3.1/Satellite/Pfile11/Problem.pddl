(define (problem pfile11)
(:domain satellite)
(:objects
 satellite0 satellite1 satellite2 satellite3 satellite4 - satellite
 instrument0 instrument1 instrument2 instrument3 instrument4 instrument5 instrument6 instrument7 instrument8 - instrument
 thermograph2 image3 infrared1 spectrograph4 infrared0 - mode
 star1 star4 star0 groundstation3 star2 star5 planet6 phenomenon7 star8 phenomenon9 star10 star11 star12 planet13 planet14 phenomenon15 planet16 star17 star18 planet19 - direction
)
(:init
 (power_avail satellite0)
 (= (pointing satellite0) star8)
 (= (on_board satellite0) {instrument0})
 (not (= (on_board satellite0) {instrument1 instrument2 instrument3 instrument4 instrument5 instrument6 instrument7 instrument8}))
 (power_avail satellite1)
 (= (pointing satellite1) groundstation3)
 (= (on_board satellite1) {instrument1 instrument2 instrument3})
 (not (= (on_board satellite1) {instrument0 instrument4 instrument5 instrument6 instrument7 instrument8}))
 (power_avail satellite2)
 (= (pointing satellite2) star4)
 (= (on_board satellite2) {instrument4 instrument5 instrument6})
 (not (= (on_board satellite2) {instrument0 instrument1 instrument2 instrument3 instrument7 instrument8}))
 (power_avail satellite3)
 (= (pointing satellite3) phenomenon9)
 (= (on_board satellite3) {instrument7})
 (not (= (on_board satellite3) {instrument0 instrument1 instrument2 instrument3 instrument4 instrument5 instrument6 instrument8}))
 (power_avail satellite4)
 (= (pointing satellite4) phenomenon9)
 (= (on_board satellite4) {instrument8})
 (not (= (on_board satellite4) {instrument0 instrument1 instrument2 instrument3 instrument4 instrument5 instrument6 instrument7}))
 (not (power_on instrument0))
 (not (calibrated instrument0))
 (= (calibration_target instrument0) star0)
 (not (power_on instrument1))
 (not (calibrated instrument1))
 (= (calibration_target instrument1) groundstation3)
 (not (power_on instrument2))
 (not (calibrated instrument2))
 (= (calibration_target instrument2) star2)
 (not (power_on instrument3))
 (not (calibrated instrument3))
 (= (calibration_target instrument3) star0)
 (not (power_on instrument4))
 (not (calibrated instrument4))
 (= (calibration_target instrument4) star2)
 (not (power_on instrument5))
 (not (calibrated instrument5))
 (= (calibration_target instrument5) star0)
 (not (power_on instrument6))
 (not (calibrated instrument6))
 (= (calibration_target instrument6) groundstation3)
 (not (power_on instrument7))
 (not (calibrated instrument7))
 (= (calibration_target instrument7) star2)
 (not (power_on instrument8))
 (not (calibrated instrument8))
 (= (calibration_target instrument8) star2)
 (not (have_image star5 image3))
 (not (have_image planet6 infrared1))
 (not (have_image phenomenon7 infrared1))
 (not (have_image star8 image3))
 (not (have_image star10 thermograph2))
 (not (have_image star11 infrared1))
 (not (have_image planet13 spectrograph4))
 (not (have_image planet14 thermograph2))
 (not (have_image phenomenon15 infrared0))
 (not (have_image planet16 image3))
 (not (have_image star17 infrared0))
 (= (supports instrument0) {spectrograph4})
 (not (= (supports instrument0) {thermograph2 image3 infrared1 infrared0}))
 (= (supports instrument1) {infrared1 infrared0})
 (not (= (supports instrument1) {thermograph2 image3 spectrograph4}))
 (= (supports instrument2) {infrared1 infrared0})
 (not (= (supports instrument2) {thermograph2 image3 spectrograph4}))
 (= (supports instrument3) {thermograph2 infrared1 spectrograph4})
 (not (= (supports instrument3) {image3 infrared0}))
 (= (supports instrument4) {image3 infrared1 infrared0})
 (not (= (supports instrument4) {thermograph2 spectrograph4}))
 (= (supports instrument5) {thermograph2 spectrograph4})
 (not (= (supports instrument5) {image3 infrared1 infrared0}))
 (= (supports instrument6) {infrared0})
 (not (= (supports instrument6) {thermograph2 image3 infrared1 spectrograph4}))
 (= (supports instrument7) {image3})
 (not (= (supports instrument7) {thermograph2 infrared1 spectrograph4 infrared0}))
 (= (supports instrument8) {infrared1 spectrograph4 infrared0})
 (not (= (supports instrument8) {thermograph2 image3}))
)
(:global-goal (and
 (= (pointing satellite0) phenomenon9)
 (= (pointing satellite1) star4)
 (= (pointing satellite4) star11)
 (have_image star5 image3)
 (have_image planet6 infrared1)
 (have_image phenomenon7 infrared1)
 (have_image star8 image3)
 (have_image star10 thermograph2)
 (have_image star11 infrared1)
 (have_image planet13 spectrograph4)
 (have_image planet14 thermograph2)
 (have_image phenomenon15 infrared0)
 (have_image planet16 image3)
 (have_image star17 infrared0)
))
)
