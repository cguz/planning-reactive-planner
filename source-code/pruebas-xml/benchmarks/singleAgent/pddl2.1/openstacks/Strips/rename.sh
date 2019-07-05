#!/bin/bash

# for each test file
FILES=`ls pfile* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	DIR=`dirname $NEWFILE`
	FILE=`basename $NEWFILE`


	echo $FILE	

	#sed -i "s/:action-costs//g" $F
	sed -i "s/$FILE/(problem $FILE/g" $F
	#sed -i "s/(:metric minimize (total-cost))//g" $F
	
	#sed -i "s/)//g" $F
	#sed -i "s/ (increase (total-cost) 1)//g" $F

	#tr [:upper:] [:lower:] < $F > test	
		#cp test $DIR'/Problem.pddl'
	
done

