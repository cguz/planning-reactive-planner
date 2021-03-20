;; printing a color image in a side of the sheet
(define (problem pfile2)
(:domain cPrint)
(:objects

		Black Color - color_t
		Front Back - side_t

		location_1
		location_2
		location_3
		location_4
		location_5
		location_6
		location_7
		location_8
		location_9
		location_10
		location_11 - location_t

		ColorFeeder-RSRC
		BlackFeeder-RSRC
		ColorTransporter-RSRC
		BlackTransporter-RSRC
		ColorPrinter-RSRC
		BlackPrinter-RSRC
		ColorContainer-RSRC
		BlackContainer-RSRC
		Down-RSRC
		FinisherContainer-RSRC - resource_t

		dummy-sheet-tray2
		dummy-sheet-tray1
		sheet1
		sheet2
		sheet3 - sheet_t

		no-image
		image-1
		image-2
		image-3
		image-4
		image-5
		image-6
		image-7 - image_t
)
(:init
		;; *********************
		;; problem specification
		(= (CanTurn ColorFeeder-RSRC) Off)
		(= (CanTurn BlackFeeder-RSRC) Off)
		(= (CanTurn Down-RSRC) Off)

		(= (In ColorFeeder-RSRC) location_1)
		(= (In BlackFeeder-RSRC) location_1)
		(= (In ColorTransporter-RSRC) location_2)
		(= (In BlackTransporter-RSRC) location_3)
		(= (In ColorPrinter-RSRC) location_4)
		(= (In BlackPrinter-RSRC) location_5)
		(= (In BlackContainer-RSRC) location_6)
		(= (In ColorContainer-RSRC) location_8)
		(= (In Down-RSRC) location_7)
		(= (In FinisherContainer-RSRC) location_9)

		;; moving from a location to other
		(Connection location_1 location_2)
		(Connection location_1 location_3)
		(Connection location_2 location_4)
		(Connection location_3 location_5)
		(Connection location_6 location_7)
		(Connection location_7 location_9)
		(Connection location_7 location_4)
		(Connection location_7 location_3)
		(Connection location_8 location_9)
		(Connection location_8 location_2)

		(CanStack FinisherContainer-RSRC location_10)
		(CanStack FinisherContainer-RSRC location_11)

		;; printing and moving from a location to other
		(CanPrint ColorPrinter-RSRC Color location_8)
		(CanPrint ColorPrinter-RSRC Black location_8)
		(CanPrint BlackPrinter-RSRC Black location_6)

		(= (Oppositeside Front) Back)
		(= (Oppositeside Back) Front)


		;; *********************
		;; problem specification

		(= (ServiceOn ColorFeeder-RSRC) Off)
		(= (ServiceOn BlackFeeder-RSRC) Off)
		(= (ServiceOn ColorTransporter-RSRC) Off)
		(= (ServiceOn BlackTransporter-RSRC) Off)
		(= (ServiceOn ColorPrinter-RSRC) Off)
		(= (ServiceOn BlackPrinter-RSRC) Off)
		(= (ServiceOn ColorContainer-RSRC) Off)
		(= (ServiceOn BlackContainer-RSRC) Off)
		(= (ServiceOn Down-RSRC) Off)
		(= (ServiceOn FinisherContainer-RSRC) Off)

		(Available ColorFeeder-RSRC)
		(Available BlackFeeder-RSRC)
		(Available ColorTransporter-RSRC)
		(Available BlackTransporter-RSRC)
		(Available ColorPrinter-RSRC)
		(Available BlackPrinter-RSRC)
		(Available ColorContainer-RSRC)
		(Available BlackContainer-RSRC)
		(Available Down-RSRC)
		(Available FinisherContainer-RSRC)

		(= (Imagecolor image-1) Black)
		(= (Imagecolor image-2) Black)
		(= (Imagecolor image-3) Black)
		(= (Imagecolor image-4) Black)
		(= (Imagecolor image-5) Black)
		(= (Imagecolor image-6) Color)
		(= (Imagecolor image-7) Color)

		(= (Sideup sheet1) Back)
		(= (Sideup sheet2) Back)
		(= (Sideup sheet3) Back)

		;; dummy-sheet in all finisher_try
		(Stacked dummy-sheet-tray1)
		(Stacked dummy-sheet-tray2)

		(Prevsheet sheet1 dummy-sheet-tray1)
		(Prevsheet sheet1 dummy-sheet-tray2)
		(Prevsheet sheet2 sheet1)
		(Prevsheet sheet3 sheet2)

		(= (Location dummy-sheet-tray1) location_10)
		(= (Location dummy-sheet-tray2) location_11)
		(= (Location sheet1) location_1)
		(= (Location sheet2) location_1)
		(= (Location sheet3) location_1)

		(not (Stacked sheet1))
		(not (Stacked sheet2))
		(not (Stacked sheet3))
		
		(not (Hasimage sheet1 Front image-1))
		(not (Hasimage sheet1 Back image-1))
		(not (Hasimage sheet1 back image-2))
		(not (Hasimage sheet1 front image-2))
		(not (Hasimage sheet1 back image-3))
		(not (Hasimage sheet1 front image-3))
		(not (Hasimage sheet1 back image-4))
		(not (Hasimage sheet1 front image-4))
		(not (Hasimage sheet1 back image-5))
		(not (Hasimage sheet1 front image-5))
		(not (Hasimage sheet1 back image-6))
		(not (Hasimage sheet1 front image-6))
		(not (Hasimage sheet1 back image-7))
		(not (Hasimage sheet1 front image-7))

		(not (Hasimage sheet2 back image-1))
		(not (Hasimage sheet2 front image-1))
		(not (Hasimage sheet2 Back image-2))
		(not (Hasimage sheet2 Front image-2))
		(not (Hasimage sheet2 back image-3))
		(not (Hasimage sheet2 front image-3))
		(not (Hasimage sheet2 back image-4))
		(not (Hasimage sheet2 front image-4))
		(not (Hasimage sheet2 back image-5))
		(not (Hasimage sheet2 front image-5))
		(not (Hasimage sheet2 back image-6))
		(not (Hasimage sheet2 front image-6))
		(not (Hasimage sheet2 back image-7))
		(not (Hasimage sheet2 front image-7))

		(not (Hasimage sheet3 back image-1))
		(not (Hasimage sheet3 front image-1))
		(not (Hasimage sheet3 back image-2))
		(not (Hasimage sheet3 front image-2))
		(not (Hasimage sheet3 back image-3))
		(not (Hasimage sheet3 front image-3))
		(not (Hasimage sheet3 back image-4))
		(not (Hasimage sheet3 front image-4))
		(not (Hasimage sheet3 back image-5))
		(not (Hasimage sheet3 front image-5))
		(not (Hasimage sheet3 Front image-6))
		(not (Hasimage sheet3 Back image-6))
		(not (Hasimage sheet3 back image-7))
		(not (Hasimage sheet3 front image-7))

		(not (Printedwith sheet1 Front Color))
		(not (Printedwith sheet2 Front Color))
		(not (Printedwith sheet3 Front Color))
		(not (Printedwith sheet1 Front Black))
		(not (Printedwith sheet2 Front Black))
		(not (Printedwith sheet3 Front Black))
		(not (Printedwith sheet1 Back Black))
		(not (Printedwith sheet2 Back Black))
		(not (Printedwith sheet3 Back Black))
		(not (Printedwith sheet1 Back Color))
		(not (Printedwith sheet2 Back Color))
		(not (Printedwith sheet3 Back Color))
)
(:global-goal (and
		(Hasimage sheet1 Front image-1)
		(Hasimage sheet2 Front image-7)
		(Hasimage sheet3 Front image-6)

		(Stacked sheet1)
		(Stacked sheet2)
		(Stacked sheet3))
))
