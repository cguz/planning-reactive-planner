#!/bin/bash

# for each test file
FILES=`ls Pfile*/* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	DIR=`dirname $NEWFILE`
	FILE=`basename $NEWFILE`
	
	if [ $FILE = "Problem.pddl" ]; then
		tr [:upper:] [:lower:] < $F > test	
		cp test $F
	fi
	#if [ $FILE = "DomainWoodworking.pddl" ]; then
		#cp $NEWFILE $DIR'/Domain.pddl'
	#fi
	
done

