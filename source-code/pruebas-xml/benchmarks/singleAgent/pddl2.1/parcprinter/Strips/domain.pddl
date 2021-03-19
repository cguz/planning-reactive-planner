;; Domain file generated by Cesar Guzman
;; on Thu Nov 28 15:41:08 2013
(define (domain cPrint)
(:requirements :typing)
(:types  size location side color image sheet
	resource - object
	always_active_r normal_r - resource
)
(:constants

)
(:predicates
		;; resources' properties
		(Is ?resource - resource)
		(CanTurn ?resource - resource)		
		(In ?resource - resource ?location - location)
		(CanStack ?resource - resource ?l2 - location)
		(CanPrint ?resource - resource ?color - color ?l2 - location)

		;; properties of the side
		(Oppositeside ?side1 - side ?side2 - side)

		;; properties of the image
		(Imagecolor ?image - image ?color - color)

		;; sheet properties
		(SideIs ?sheet - sheet ?side - side)
		(Stacked ?sheet - sheet)
		(Printedwith ?sheet - sheet ?side - side ?color - color)
		(Hasimage ?sheet - sheet ?side - side ?image - image)
		(Prevsheet ?sheet1 - sheet ?sheet2 - sheet)
		(Location ?sheet - sheet ?location - location) ;; where is the sheet ?

		;; Connection bettween locations
		(Connection ?l1 ?l2 - location)
)
(:action RepairResource
 :parameters (?resource - resource)
 :precondition (and
		(not (Is ?resource)))
 :effect (and
		(Is ?resource))
)
(:action RepairCanTurn
 :parameters (?resource - resource)
 :precondition (and
		(not (CanTurn ?resource))
	)
 :effect (and
		(CanTurn ?resource)
	)
)
(:action Move-Sheet
 :parameters (?resource - resource ?sheet - sheet ?l1 ?l2 - location)
 :precondition (and
		(In ?resource ?l1)
		(Location ?sheet ?l1)
		(Connection ?l1 ?l2))
 :effect (and
		(Location ?sheet ?l2)
		(not (Location ?sheet ?l1)))
)
(:action MoveAndInvert-Sheet
 :parameters (?resource - resource ?sheet - sheet ?l1 ?l2 - location ?face - side ?o_face - side)
 :precondition (and
		(CanTurn ?resource)
		(SideIs ?sheet ?face)
		(Oppositeside ?face ?o_face)
		(In ?resource ?l1)
		(Location ?sheet ?l1)
		(Connection ?l1 ?l2))
 :effect (and
		(Location ?sheet ?l2)
		(SideIs ?sheet ?o_face)
		(not (Location ?sheet ?l1))
		(not (SideIs ?sheet ?face)))
)
(:action Print-Simplex-Sheet
 :parameters ( ?resource - resource ?sheet - sheet ?face - side ?color - color ?image - image ?l1 - location ?l2 - location)
 :precondition (and
		(Imagecolor ?image ?color)
		(CanPrint ?resource ?color ?l2)
		(In ?resource ?l1)
		(SideIs ?sheet ?face)
		(Location ?sheet ?l1)
		(not (Printedwith ?sheet ?face ?color))
	)
 :effect (and
		(Location ?sheet ?l2)
		(Hasimage ?sheet ?face ?image)
		(Printedwith ?sheet ?face ?color)
		(not (Location ?sheet ?l1)))
)
(:action Print-SimplexAndInvert-Sheet
 :parameters ( 	?resource - resource ?sheet - sheet ?color - color ?image - image 
		?face - side ?otherside - side ?l1 - location ?l2 - location)
 :precondition (and
		(CanTurn ?resource)
		(CanPrint ?resource ?color ?l2)
		(In ?resource ?l1)
		(SideIs ?sheet ?face)
		(Location ?sheet ?l1)
		(Oppositeside ?face ?otherside)
		(Imagecolor ?image ?color)
		(not (Printedwith ?sheet ?face ?color)))
 :effect (and
		(SideIs ?sheet ?otherside)
		(Hasimage ?sheet ?face ?image)
		(Location ?sheet ?l2)
		(Printedwith ?sheet ?face ?color)
		(not (Location ?sheet ?l1))
		(not (SideIs ?sheet ?face)))
)
(:action Stack-Sheet
 :parameters ( ?resource - resource ?sheet - sheet ?prevsheet - sheet ?l1 - location ?l2 - location)
 :precondition (and
		(Prevsheet ?sheet ?prevsheet)
		(Stacked ?prevsheet)
		(Location ?prevsheet ?l2)
		(CanStack ?resource ?l2)
		(In ?resource ?l1)
		(Location ?sheet ?l1))
 :effect (and
		(Location ?sheet ?l2)
		(Stacked ?sheet)
		(not (Location ?sheet ?l1)))
))