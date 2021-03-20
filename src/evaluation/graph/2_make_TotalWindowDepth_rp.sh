#!/bin/bash

cd 'TOOLS-MEAN'
chmod +x *.sh
TOOLS=`pwd`
cd ..


#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`
FINALDIR=`echo 'result_files'`
HISTOGRAMFILE=`echo 'histogram'`
TIMEFILE=`echo '1seg'`


NEWDIR=`echo 'graph_files'`
rm -R $NEWDIR
mkdir $NEWDIR
mkdir $FINALDIR


# for each test file
FILES=`ls $RESULTADOS/tes*/$TIMEFILE/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	# format 2: preparing data to perform the histogram
	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | sed s/" l"/" "/g | sed s/"w"//g | sed /^$/d | cut -d " " -f 3,4 | awk -v t=$TIMEFILE '{printf("%s,%s %s \n", $1, $2, t);}' >> $NEWDIR'/'$HISTOGRAMFILE

	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | sed s/" l"/" "/g | sed s/"w"//g | sed /^$/d | cut -d " " -f 2,3,4,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27 | awk -v t=$TIMEFILE '{printf("%s \t %s,%s  \t\t %s %s %s %s %s %s %s %s \t %s %s %s \t %s %s %s %s %s %s \n",$1, $2,$3 ,$8,$9,$4,$5,$6,$7,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$21);}' >> $FINALDIR'/relationWindowDepth'

done


# calculating the histogram window and depth 
FLI=`ls $NEWDIR'/'$HISTOGRAMFILE 2>/dev/null`
for FILE in $FLI; do
	CONF=`cut -d " " -f 1 $FILE | sort -u`;
	SEGTIME=`cut -d " " -f 2 $FILE | sort -u`;
	for T in $CONF; do
		FILTER=`echo $T`
		COUNT=`grep -w "$FILTER" $FILE | wc -l`
		echo $FILTER $COUNT $SEGTIME >> $NEWDIR'/graph-'$HISTOGRAMFILE
	done 
done

rm $NEWDIR'/'$HISTOGRAMFILE

# generating pdf files
$TOOLS/plotHistogramWD.sh $NEWDIR'/graph-'$HISTOGRAMFILE $FINALDIR'/'$HISTOGRAMFILE'-WD-'$TIMEFILE "selected optimal windows and depths generating the tree" $TIMEFILE

