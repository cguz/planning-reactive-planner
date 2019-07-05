#!/bin/bash
for i in `seq 3 20`; do
	mv Pfile$i/ProblemDriverlog.pddl Pfile$i/Problem.pddl
	mv Pfile$i/DomainDriverlog.pddl Pfile$i/Domain.pddl
	cat Pfile$i/Problem.pddl | sed 's/DLOG-[0-9]-[0-9]-[0-9]/pfile'$i'/g' > Pfile$i/Problem1.pddl
	#rm Pfile$i/Problem.pddl
	mv Pfile$i/Problem1.pddl Pfile$i/Problem.pddl
	echo ")" >> Pfile$i/Problem.pddl 
done
