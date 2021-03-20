;; pfile agent 2
;; Nota: Especificar que objecto o entidad representa el agente, y con que otros agentes se encuentran ejecutando 
;; Necesita ayuda de: 	Agente 3
;; Puede ayudar a: 	Agente 1
(define (problem pfile2)
(:domain vrp)
(:objects
	D 	- depot
	A B C 	- distributor
	P1 P2 	- package
	T2 	- truck	
)
(:init
	(=(at P1) A) 
	(=(at P2) B) 
	(=(loc T2) B)
	(link A B)
	(link B A)
	(link C A)
	(link A C)
)
(:global-goal (and
       (=(at P2) C)
       (=(at P1) A)
       ;(link A C)
       ;(link C A)
       )
))
