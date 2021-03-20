#!/bin/bash

# for each test file
FILES=`ls pfile* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	DIR=`dirname $NEWFILE`
	FILE=`basename $NEWFILE`

	echo $NEWFILE

	# eliminamos lineas con un patron
	#grep -v "highspeed" $F > test
	#cp test $F
	
	# reemplazar texto
	sed -i "s/woo-prob/$FILE/g" $F 

	# convertimos de mayusculas a minusculas
	tr [:upper:] [:lower:] < $F > test	
	cp test $F
done

