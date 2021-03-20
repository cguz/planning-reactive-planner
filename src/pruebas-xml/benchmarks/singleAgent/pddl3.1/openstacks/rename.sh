#!/bin/bash

# for each test file
FILES=`ls Pfile*/* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	DIR=`dirname $NEWFILE`
	FILE=`basename $NEWFILE`
	
	if [ $FILE = "Problem.pddl" ]; then
		sed -i "s/os-sequencedstrips-p[0-9]*/$DIR/g" $F	

		#agregar nuevo contenido
		#echo ")" >> $F
		#rm $F
	fi
	
done

