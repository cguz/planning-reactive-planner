#!/bin/bash
for i in `seq 1 20`; do
	mv Pfile$i/ProblemSatellite.pddl Pfile$i/Problem.pddl
	mv Pfile$i/DomainSatellite.pddl Pfile$i/Domain.pddl
	cat Pfile$i/Problem.pddl | sed 's/strips-sat-x-[0-9]/pfile'$i'/g' > Pfile$i/Problem1.pddl
	#rm Pfile$i/Problem.pddl
	mv Pfile$i/Problem1.pddl Pfile$i/Problem.pddl
	echo ")" >> Pfile$i/Problem.pddl 
done
