#!/bin/bash

# for each test file
for F in `seq 1 10` 
do
	#cp "p01.pddl" "p0"$F".pddl"
	cp "p01.pddl" "pfile$F"
done

