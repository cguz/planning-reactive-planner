#!/bin/bash

# for each test file
for F in `seq 1 10` 
do
	mkdir "Pfile$F"
	cp "domain.pddl" "Pfile$F/Domain.pddl"
	# mv "pfile$F" "Pfile$F/Problem.pddl"
done

