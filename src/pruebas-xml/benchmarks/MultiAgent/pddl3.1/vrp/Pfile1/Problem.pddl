;; pfile agent 1
;; Nota: Especificar que objecto o entidad representa el agente, y con que otros agentes se encuentran ejecutando 
;; Necesita ayuda de: 	Agente 2
;; Puede ayudar a: 	Ningún agente
(define (problem pfile1)
(:domain vrp)
(:objects
	D 	- depot
	A B C 	- distributor
	P1 	- package
	T1 	- truck	
)
(:init
	(= (at P1) A) 
	(= (loc T1) A)
	(link A C)
	(link C A)
	(link C D)
	(link D C)
)
(:global-goal (and
       (= (at P1) C))
))
