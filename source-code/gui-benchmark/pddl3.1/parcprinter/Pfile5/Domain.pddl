;; Domain file generated by Cesar Guzman
;; on Thu Nov 28 15:41:08 2013
(define (domain cPrint)
(:requirements :typing)
(:types  size_t location_t side_t color_t image_t resource_t sheet_t service)
(:constants
	On Off - service
)
(:predicates
		;; resources' properties
		(Available ?resource - resource_t)
		(CanStack ?resource - resource_t ?l2 - location_t)
		(CanPrint ?resource - resource_t ?color - color_t ?l2 - location_t)

		;; sheet properties
		(Stacked ?sheet - sheet_t)
		(Printedwith ?sheet - sheet_t ?side - side_t ?color - color_t)	; multi-valued
		(Hasimage ?sheet - sheet_t ?side - side_t ?image - image_t)	; multi-valued
		(Prevsheet ?sheet1 - sheet_t ?sheet2 - sheet_t)			; multi-valued

		;; Connection bettween locations
		(Connection ?l1 ?l2 - location_t)
)
(:functions
	;; resources' properties
	(CanTurn ?resource - resource_t) - service
	(ServiceOn ?resource - resource_t) - service
	(In ?resource - resource_t) - location_t

	;; properties of the side
	(Oppositeside ?side1 - side_t) - side_t

	;; properties of the image
	(Imagecolor ?image - image_t) - color_t

	;; sheet properties
	(Sideup ?sheet - sheet_t) - side_t
	(Location ?sheet - sheet_t) - location_t ;; where is the sheet ?
)
(:action RepairResource
 :parameters (?resource - resource_t)
 :precondition (and
		(= (ServiceOn ?resource) Off)
	)
 :effect (and
		(assign (ServiceOn ?resource) On)
	)
)
(:action RepairCanTurn
 :parameters (?resource - resource_t)
 :precondition (and
		(= (CanTurn ?resource) Off)
	)
 :effect (and
		(assign (CanTurn ?resource) On)
	)
)
(:action Move-Sheet
 :parameters (?resource - resource_t ?sheet - sheet_t ?l1 ?l2 - location_t)
 :precondition (and
		(Available ?resource)
		(= (ServiceOn ?resource) On)
		(= (In ?resource) ?l1)
		(= (Location ?sheet) ?l1)
		(Connection ?l1 ?l2))
 :effect (and
		;(not (Available ?resource))
		(assign (Location ?sheet) ?l2))
		;(Available ?resource))
)
(:action MoveAndInvert-Sheet
 :parameters (?resource - resource_t ?sheet - sheet_t ?l1 ?l2 - location_t ?face - side_t ?o_face - side_t)
 :precondition (and
		(Available ?resource)
		(= (ServiceOn ?resource) On)
		(= (CanTurn ?resource) On)
		(= (Sideup ?sheet) ?face)
		(= (Oppositeside ?face) ?o_face)
		(= (In ?resource) ?l1)
		(= (Location ?sheet) ?l1)
		(Connection ?l1 ?l2))
 :effect (and
		;(not (Available ?resource))
		(assign (Location ?sheet) ?l2)
		(assign (Sideup ?sheet) ?o_face))
		;(Available ?resource))
)
(:action Print-Simplex-Sheet
 :parameters ( ?resource - resource_t ?sheet - sheet_t ?face - side_t ?color - color_t ?image - image_t ?l1 - location_t ?l2 - location_t)
 :precondition (and
		(Available ?resource)
		(= (ServiceOn ?resource) On)
		(CanPrint ?resource ?color ?l2)
		(= (Imagecolor ?image) ?color)
		(= (In ?resource) ?l1)
		(= (Sideup ?sheet) ?face)
		(= (Location ?sheet) ?l1)
		(not (Printedwith ?sheet ?face ?color))
	)
 :effect (and
		;(not (Available ?resource))
		(assign (Location ?sheet) ?l2)
		(Hasimage ?sheet ?face ?image)
		(Printedwith ?sheet ?face ?color))
		;(Available ?resource))
)
(:action Print-SimplexAndInvert-Sheet
 :parameters ( 	?resource - resource_t ?sheet - sheet_t ?color - color_t ?image - image_t 
		?face - side_t ?otherside - side_t ?l1 - location_t ?l2 - location_t)
 :precondition (and
		(Available ?resource)
		(= (ServiceOn ?resource) On)
		(CanPrint ?resource ?color ?l2)
		(= (Imagecolor ?image) ?color)
		(= (In ?resource) ?l1)
		(= (Sideup ?sheet) ?face)
		(= (Location ?sheet) ?l1)
		(= (CanTurn ?resource) On)
		(= (Oppositeside ?face) ?otherside)
		(not (Printedwith ?sheet ?face ?color)))
 :effect (and
		;(not (Available ?resource))
		(assign (Sideup ?sheet) ?otherside)
		(Hasimage ?sheet ?face ?image)
		(assign (Location ?sheet) ?l2)
		(Printedwith ?sheet ?face ?color))
		;(Available ?resource))
)
(:action Stack-Sheet
 :parameters ( ?resource - resource_t ?sheet - sheet_t ?prevsheet - sheet_t ?l1 - location_t ?l2 - location_t)
 :precondition (and
		(Available ?resource)
		(= (ServiceOn ?resource) On)
		(Stacked ?prevsheet)
		(CanStack ?resource ?l2)
		(Prevsheet ?sheet ?prevsheet)
		(= (Location ?prevsheet) ?l2)
		(= (In ?resource) ?l1)
		(= (Location ?sheet) ?l1))
 :effect (and
		;(not (Available ?resource))
		(assign (Location ?sheet) ?l2)
		(Stacked ?sheet))
		;(Available ?resource))
))
