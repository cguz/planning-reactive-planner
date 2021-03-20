Información general
-------------------

Instalar R y libreria RServe

	- aptitude install R
	- R -> install.packages(RServe)





PLANS ARE SAVED IN THE FOLDER "solutions"




1. FITTING THE MODEL
   
	* It is essential to carry out the training.

	Domains:Driverlog, logistics, Rovers | woodworking, openstacks (all)
	Pfiles: 1-14	

	
	GENERATING THE DATASET SAMPLE
	

		1. Specify in the file "pruebas-xml/benchmarks/configuration.xml" the option "training mode":
	
			RP_TYPE_EXECUTION=REAL_TRAINNING_MODE=1
	
		2. Executing the MainTest.

		3. Unifiyng the created real execution files (analysis-DOMAIN-with-rgs) and saving it in the folder "analysis".
	
			- Por cada dominio tenemos un fichero de analysis, de esta manera evitamos repetir la ejecución para el mismo dominio.
	
		4. Filtering the elements that perform noise.
	
			a.) window = depth (lo hace previamente la ejecución del MainTest)
	
			b.) awk '{if($5/$NF < 0.2) print $0}' analysisAll > analysisAll-Filtered
	
				Donde $5 es el Time y $NF es el número de nodos evaluados ENodes
	
		5.) Putting the labels on the generated file "NUEVOFICHEROANALYSIS"

		Domain	window	depth	pfile	Time	N	Rvar	DRvar	FluentsRoot	RvarRoot	Producers	ProducersU	Trie_nodes ENodes

		
	FITTING THE MODEL
	
		5. Executing the R commands of the file "comandos.txt" to calculate the model.
	
			Look at the settings to CHANGE.
	
			1. Tener en cuenta el alfa: obtener los valores de meanAlfa y meanTime, dependiendo del tipo de modelo a utilizar.
	
			2. No tener el cuenta el alfa: cambiar la formula de meanTime por mean(Time/N) y dejar el valor de alfa=1 
	
		6. Changing the new information of the model in "configuration.xml". 
	
		7. Sí se ejecuta usando las librerias de R, se debe cargar el server de R
	
	
	

2. Performing generating test.

	Domains:Driverlog, logistics, rovers, openstacks, woodworking
	Pfiles: 1-14

	1. Specifying in the file "benchmarks/singleAgent/configuration.xml" the generating tree mode:

		RP_TYPE_EXECUTION=TESTING_GENERATING_TREE_MODE_GRAPH1=2

	2. Executing the Main. Genera (test_exo_1) carpeta con diferentes ejecuciones y tiempos limites:
		
	3. Datos para el paper:

		a.) Table 1: generating from the "test_exo_1/file evaluationsRP-tf2-f1"

			-- Salvar la información en el fichero "generatingTree-selectingArgMaxComplexity.xls"
			- Resumir la información importante en el fichero "table1generatingTree-selectingArgMaxComplexity.xls" (solo se tiene que copiar las columnas). 

		b.) Figure 7 - estimated time vs real time: executing the script "graph/generatingFigureMedianTimeT1_rp.sh"

		c.) Pag. 14, col. 2: 

			- cambiar de posición las columnas pfile y search tree
			- executing the script "graph/generatingOverAllErrorAllSearchTreesTable2-min.sh"

			


3. Simulating plan failures. 

	
	1. Specifying in the file "benchmarks/singleAgent/configuration.xml" the simulating failure mode:

		RP_TYPE_EXECUTION=SIMULATING_FAILURE_RP_MODE=4

	2. Executing the Main. Genera 2 carpetas para diferentes tipos de fallos "test_exo_1" y "test_action_1":
		
	3. Datos para el paper:

		a.) Table 3 - mean time to find a plan with is standart desviation: 

			1.executing the script "graph/simulatingFailureTable3_rp.sh" to generate the file "temp_files/MEANTIME-FINDPLAN-Rovers".

			2. Open the file "generatingTreeSimulatingFailure/meanTime.ods" and replace the values with the generated file "temp_files/MEANTIME-FINDPLAN-Rovers".

		b.) Table 2 - original and repaired actions, with its reused actions: 

			1. "temp_files/evaluation_files-Rovers"



