#!/bin/bash
for i in `seq 0 20`; do
	mv Pfile$i/ProblemLogistics.pddl Pfile$i/Problem.pddl
	mv Pfile$i/DomainLogistics.pddl Pfile$i/Domain.pddl
	cat Pfile$i/Problem.pddl | sed 's/logistics-[0-9]*-[0-9]*/pfile'$i'/g' > Pfile$i/Problem1.pddl
	#rm Pfile$i/Problem.pddl
	mv Pfile$i/Problem1.pddl Pfile$i/Problem.pddl
	echo ")" >> Pfile$i/Problem.pddl 
done
