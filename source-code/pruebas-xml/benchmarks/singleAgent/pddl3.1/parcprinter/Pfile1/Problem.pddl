;; printing a black image in a side of the sheet
(define (problem pfile1)
(:domain cPrint)
(:objects

		Black Color - color
		Front Back - side

		location_1
		location_2
		location_3
		location_4
		location_7
		location_8
		location_6
		location_5
		location_9
		location_10 - location

		Feeder1-RSRC
		Feeder2-RSRC
		ColorTransporter-RSRC
		BlackTransporter-RSRC
		ColorPrinter-RSRC
		BlackPrinter-RSRC
		Down-RSRC	- normal_r

		ColorContainer-RSRC
		BlackContainer-RSRC
		FinisherContainer-RSRC - always_active_r

		dummy-sheet-tray1
		sheet1 - sheet

		no-image
		image-1 - image
)
(:init
		;; *********************
		;; problem domain specification
		;; *********************
		(= (CanTurn Feeder1-RSRC) Off)
		(= (CanTurn Feeder2-RSRC) Off)
		(= (CanTurn Down-RSRC) Off)

		(= (In Feeder1-RSRC) location_1)
		(= (In Feeder2-RSRC) location_1)
		(= (In ColorTransporter-RSRC) location_2)
		(= (In BlackTransporter-RSRC) location_3)
		(= (In ColorPrinter-RSRC) location_4)
		(= (In BlackPrinter-RSRC) location_7)
		(= (In BlackContainer-RSRC) location_8)
		(= (In ColorContainer-RSRC) location_5)
		(= (In Down-RSRC) location_6)
		(= (In FinisherContainer-RSRC) location_9)

		;; moving from a location to other
		(Connection location_1 location_2)
		(Connection location_1 location_3)
		(Connection location_2 location_4)
		(Connection location_3 location_7)
		(Connection location_4 location_5)
		(Connection location_7 location_8)
		(Connection location_5 location_6)
		(Connection location_6 location_9)
		(Connection location_6 location_7)
		(Connection location_6 location_2)
		(Connection location_8 location_9)

		(CanStack FinisherContainer-RSRC location_10)



		;; printing and moving from a location to other
		(CanPrint ColorPrinter-RSRC Color location_5)
		(CanPrint ColorPrinter-RSRC Black location_5)
		(CanPrint BlackPrinter-RSRC Black location_8)

		(= (Oppositeside Front) Back)
		(= (Oppositeside Back) Front)

		(= (Is Feeder1-RSRC) Off)
		(= (Is Feeder2-RSRC) Off)
		(= (Is ColorTransporter-RSRC) Off)
		(= (Is BlackTransporter-RSRC) Off)
		(= (Is ColorPrinter-RSRC) Off)
		(= (Is BlackPrinter-RSRC) Off)
		(= (Is Down-RSRC) Off)


		(= (SideIs sheet1) Front)

		;; dummy-sheet in all finisherry
		(Stacked dummy-sheet-tray1)

		(Prevsheet sheet1 dummy-sheet-tray1)

		(= (Location dummy-sheet-tray1) location_10)
		(= (Location sheet1) location_1)

		(not (Stacked sheet1))

		(not (Hasimage sheet1 Front image-1))

		(not (Hasimage sheet1 Back image-1))

		(not (Printedwith sheet1 Front Color))
		(not (Printedwith sheet1 Front Black))
		(not (Printedwith sheet1 Back Black))
		(not (Printedwith sheet1 Back Color))

		(= (Imagecolor image-1) Black)
)
(:global-goal (and
		(Hasimage sheet1 Front image-1)

		(Stacked sheet1))
)
)