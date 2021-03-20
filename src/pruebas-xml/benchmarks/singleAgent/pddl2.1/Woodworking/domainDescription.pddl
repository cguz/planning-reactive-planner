;; Woodworking
(define (domain woodworking)
  (:requirements :typing)
  (:types
	acolour 	;; colores de la madera
	awood 		;; tipo de maderas
	woodobj 	;; objetos de madera, la materia prima (board) y piezas (parts) 
	machine 	;; 1.) maquinas o tools
      	surface 	;; 2.) tipo de la superficie de madera
	treatmentstatus ;; 3.) estado de la madera (piezas)
      	aboardsize apartsize - object
      	highspeed-saw glazer grinder immersion-varnisher planer saw spray-varnisher - machine ;; 1.) maquinas o tools, 7 en total
      	board 		;; tabla o materia prima
	part - woodobj	;; piezas o moldes para cortar las tablas
  )

  (:constants
              verysmooth smooth rough - surface 				;; 2.) tipo de la superficie de madera
              varnished glazed untreated colourfragments - treatmentstatus	;; 3.) estado de la madera (piezas)
              natural - acolour							;; tipo de color
              small medium large - apartsize)					;; tamaño de las piezas

  (:predicates 
            (unused ?obj - part)					;; no se ha usado la pieza
            (available ?obj - woodobj)					;; tabla o pieza disponible

            (surface-condition ?obj - woodobj ?surface - surface)	;; condición de la madera
            (treatment ?obj - part ?treatment - treatmentstatus)	;; estado de la pieza
            (colour ?obj - part ?colour - acolour)			;; color de la pieza
            (wood ?obj - woodobj ?wood - awood)				;; tipo de madera
            (boardsize ?board - board ?size - aboardsize)		;; tamaño de la tabla
            (goalsize ?part - part ?size - apartsize)			;; tamaño que se puede conseguir con la pieza
            (boardsize-successor ?size1 ?size2 - aboardsize)		;; tamaño al que se puede cortar una tabla

            (in-highspeed-saw ?b - board ?m - highspeed-saw)		;; la tabla se encuentra en la cierra de alta velocidad
            (empty ?m - highspeed-saw)					;; no hay nada en la cierra de alta velocidad
            (has-colour ?machine - machine ?colour - acolour)		;; la maquina tiene color
            (contains-part ?b - board ?p - part)			;; la mtabla contiene la pieza
            (grind-treatment-change ?old ?new - treatmentstatus)	;; cambiar el estado del grind
            (is-smooth ?surface - surface))				;; la superficie es smooth

  (:action do-immersion-varnish			;; aplicar varnish con inmersion
    :parameters (?x - part ?m - immersion-varnisher ?newcolour - acolour ?surface - surface)
    :precondition (and
            (available ?x)			;; esta disponible la pieza
            (has-colour ?m ?newcolour)		;; la maquina tiene el color
            (surface-condition ?x ?surface)	;; es la pieza igual a la superficie
            (is-smooth ?surface)		;; la superficie es smooth
            (treatment ?x untreated))		;; el estado de la pieza es untreated
    :effect (and
            (not (treatment ?x untreated))	
            (treatment ?x varnished)		;; cambiamos el estado de la pieza a varnished
            (not (colour ?x natural))
            (colour ?x ?newcolour)))		;; cambiamos el color de la pieza

  (:action do-spray-varnish			;; aplicar varnish con spray
    :parameters (?x - part ?m - spray-varnisher ?newcolour - acolour ?surface - surface)
    :precondition (and
            (available ?x)			;; esta disponible la pieza
            (has-colour ?m ?newcolour)		;; la maquina tiene el color
            (surface-condition ?x ?surface)	;; es la pieza igual a la superficie
            (is-smooth ?surface)		;; la superficie es smooth
            (treatment ?x untreated))		;; el estado de la pieza es untreated
    :effect (and 
            (not (treatment ?x untreated))
            (treatment ?x varnished)		;; cambiamos el estado de la pieza a varnished
            (not (colour ?x natural))
            (colour ?x ?newcolour)))		;; cambiamos el color de la pieza

  (:action do-glaze				;; aplicar abrillantadora
    :parameters (?x - part ?m - glazer ?newcolour - acolour)
    :precondition (and
            (available ?x)			;; esta disponible la pieza
            (has-colour ?m ?newcolour)		;; la maquina tiene el color
            (treatment ?x untreated))		;; el estado de la pieza es untreated
    :effect (and 
            (not (treatment ?x untreated))
            (treatment ?x glazed)		;; cambiamos el estado de la pieza a glazed
            (not (colour ?x natural))
            (colour ?x ?newcolour)))		;; cambiamos el color de la pieza

  (:action do-grind							;; aplicar pulidora para verysmooth (quitar el color de la pieza)
    :parameters (?x - part ?m - grinder ?oldsurface - surface
                 ?oldcolour - acolour 
		 ?oldtreatment ?newtreatment - treatmentstatus) 
    :precondition (and 
            (available ?x)						;; esta disponible la pieza
            (surface-condition ?x ?oldsurface)				;; es la pieza igual a la superficie
            (is-smooth ?oldsurface)					;; la superficie es smooth
            (treatment ?x ?oldtreatment)				;; estado anterior de la pieza
            (colour ?x ?oldcolour)					;; color a quitar
            (grind-treatment-change ?oldtreatment ?newtreatment))
    :effect (and
            (not (surface-condition ?x ?oldsurface))
            (surface-condition ?x verysmooth)				;; la superficie de la pieza es verysmooth
            (not (treatment ?x ?oldtreatment))
            (treatment ?x ?newtreatment)				;; cambiamos el estado actual de la pieza
            (not (colour ?x ?oldcolour))
            (colour ?x natural)))					;; cambiamos el color de la pieza a natural

  (:action do-plane							;; aplicar pulidora para smooth
    :parameters (?x - part ?m - planer ?oldsurface - surface
                 ?oldcolour - acolour 
		 ?oldtreatment - treatmentstatus) 
    :precondition (and 
            (available ?x)						;; esta disponible la pieza
            (surface-condition ?x ?oldsurface))				;; es la pieza igual a la superficie
            (treatment ?x ?oldtreatment)				;; estado anterior de la pieza
            (colour ?x ?oldcolour))					;; color a quitar
    :effect (and
            (not (surface-condition ?x ?oldsurface))
            (surface-condition ?x smooth)				;; la superficie de la pieza es smooth
            (not (treatment ?x ?oldtreatment))
            (treatment ?x untreated)					;; cambiamos el estado actual de la pieza
            (not (colour ?x ?oldcolour))
            (colour ?x natural)))					;; cambiamos el color de la pieza a natural

  (:action load-highspeed-saw					;; carga la tabla a la maquina de alta velocidad
    :parameters (?b - board ?m - highspeed-saw)
    :precondition (and
            (empty ?m)
            (available ?b))
    :effect (and
            (not (available ?b))
            (not (empty ?m))
            (in-highspeed-saw ?b ?m)))
            
  (:action unload-highspeed-saw					;; descarga la tabla de la maquina de alta velocidad
    :parameters (?b - board ?m - highspeed-saw)
    :precondition (in-highspeed-saw ?b ?m)
    :effect (and
            (available ?b)
            (not (in-highspeed-saw ?b ?m))
            (empty ?m)))
            
  (:action cut-board-small					;; cortar la tabla en una pieza pequeña, alta velocidad
    :parameters (?b - board ?p - part ?m - highspeed-saw ?w - awood ?surface - surface ?size_before ?size_after - aboardsize)
    :precondition (and
            (unused ?p)						;; la pieza no se ha usado
            (goalsize ?p small)					;; el mode de la pieza es pequeño
            (in-highspeed-saw ?b ?m)				;; la pieza se encuentra en la maquina de alta velocidad
            (wood ?b ?w)					;; la tabla es del tipo de madera
            (surface-condition ?b ?surface)			;; la tabla tiene la superficie que se necesita
            (boardsize ?b ?size_before)				;; el tamaño de la tabla es el adecuado
            (boardsize-successor ?size_after ?size_before))	;; se puede cortar la tabla al tamaño deseado
    :effect (and
            (not (unused ?p))					;; la pieza ya no se puede usar
            (available ?p)					;; esta disponible la pieza
            (wood ?p ?w)					;; ponemos la pieza del mismo tipo de la tabla
            (surface-condition ?p ?surface)			;; ponemos la superficie de la pieza del mismo tipo de la tabla
            (colour ?p natural)					;; la pieza queda de color natural
            (treatment ?p untreated)				;; el estado de la pieza es untreated
            (boardsize ?b ?size_after)))			;; se reduce el tamaño de la tabla, el tamaño anterior no se borra

  (:action cut-board-medium					;; cortar la tabla en una pieza mediana, alta velocidad
    :parameters (?b - board ?p - part ?m - highspeed-saw ?w - awood ?surface - surface ?size_before ?s1 ?size_after - aboardsize)
    :precondition (and
            (unused ?p)
            (goalsize ?p medium)
            (in-highspeed-saw ?b ?m)
            (wood ?b ?w)
            (surface-condition ?b ?surface)
            (boardsize ?b ?size_before)
            (boardsize-successor ?size_after ?s1)
            (boardsize-successor ?s1 ?size_before))
    :effect (and
            (not (unused ?p))
            (available ?p)
            (wood ?p ?w)
            (surface-condition ?p ?surface)
            (colour ?p natural)
            (treatment ?p untreated)
            (boardsize ?b ?size_after)))

  (:action cut-board-large
    :parameters (?b - board ?p - part ?m - highspeed-saw ?w - awood ?surface - surface ?size_before ?s1 ?s2 ?size_after - aboardsize)
    :precondition (and
            (unused ?p)
            (goalsize ?p large)
            (in-highspeed-saw ?b ?m)
            (wood ?b ?w)
            (surface-condition ?b ?surface)
            (boardsize ?b ?size_before)
            (boardsize-successor ?size_after ?s1)
            (boardsize-successor ?s1 ?s2)
            (boardsize-successor ?s2 ?size_before))
    :effect (and
            (not (unused ?p))
            (available ?p)
            (wood ?p ?w)
            (surface-condition ?p ?surface)
            (colour ?p natural)
            (treatment ?p untreated)
            (boardsize ?b ?size_after)))

  (:action do-saw-small					;; cortar la tabla en una pieza pequeña
    :parameters (?b - board ?p - part ?m - saw ?w - awood
                 ?surface - surface ?size_before ?size_after - aboardsize) 
    :precondition (and 
            (unused ?p)
            (goalsize ?p small)
            (available ?b)
            (wood ?b ?w)
            (surface-condition ?b ?surface)
            (boardsize ?b ?size_before)
            (boardsize-successor ?size_after ?size_before))
    :effect (and
            (not (unused ?p))
            (available ?p)
            (wood ?p ?w)
            (surface-condition ?p ?surface)
            (colour ?p natural) 
            (treatment ?p untreated)
            (boardsize ?b ?size_after)))

  (:action do-saw-medium
    :parameters (?b - board ?p - part ?m - saw ?w - awood
                 ?surface - surface 
                 ?size_before ?s1 ?size_after - aboardsize) 
    :precondition (and 
            (unused ?p)
            (goalsize ?p medium)
            (available ?b)
            (wood ?b ?w)
            (surface-condition ?b ?surface)
            (boardsize ?b ?size_before)
            (boardsize-successor ?size_after ?s1)
            (boardsize-successor ?s1 ?size_before))
    :effect (and
            (not (unused ?p))
            (available ?p)
            (wood ?p ?w)
            (surface-condition ?p ?surface)
            (colour ?p natural) 
            (treatment ?p untreated)
            (boardsize ?b ?size_after)))

  (:action do-saw-large
    :parameters (?b - board ?p - part ?m - saw ?w - awood
                 ?surface - surface 
                 ?size_before ?s1 ?s2 ?size_after - aboardsize) 
    :precondition (and 
            (unused ?p)
            (goalsize ?p large)
            (available ?b)
            (wood ?b ?w)
            (surface-condition ?b ?surface)
            (boardsize ?b ?size_before)
            (boardsize-successor ?size_after ?s1)
            (boardsize-successor ?s1 ?s2)
            (boardsize-successor ?s2 ?size_before))
    :effect (and
            (not (unused ?p))
            (available ?p)
            (wood ?p ?w)
            (surface-condition ?p ?surface)
            (colour ?p natural) 
            (treatment ?p untreated)
            (boardsize ?b ?size_after)))
)
