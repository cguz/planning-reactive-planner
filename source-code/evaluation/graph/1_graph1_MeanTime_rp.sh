#!/bin/bash

cd 'TOOLS-MEAN'
chmod +x *.sh
TOOLS=`pwd`
cd ..

#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`
FINALDIR=`echo 'result_files'`
MEANFILE=`echo 'MEAN'`
TIMEFILE=`echo '1seg'`


NEWDIR=`echo 'graph_files'`
rm -R $NEWDIR
rm -R $FINALDIR
mkdir $NEWDIR
mkdir $FINALDIR


# for each test file
FILES=`ls $RESULTADOS/tes*/$TIMEFILE/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	# format 1: preparing the format file and saving it in NEW_DIR
	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | sed s/" l"/" "/g | sed s/"w"//g | cut -d " " -f 1,2,5,7,9,11,13,15,17,19,22,24,26,3,4 >> $NEWDIR'/'$FINALDIR

	# splitting it by domains 
	i=0
	for DOMAIN in `(cut -d " " -f 1 $NEWDIR'/'$FINALDIR | sort -u )`; do 
		grep $DOMAIN $NEWDIR'/'$FINALDIR > $NEWDIR'/'$FINALDIR'-'$DOMAIN
		DOMAINS[$i]=$DOMAIN
		i=$(($i+1))
	done
done

# saving the format file 1
# mv $NEWDIR'/'$FINALDIR $NEWDIR'/allTest'

# removing the format file 1
#rm $NEWDIR'/'$FINALDIR


# calculating the mean of the times by domains
FLI=`ls $NEWDIR'/'$FINALDIR* 2>/dev/null`
i=0
for FILE in $FLI; do
	CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-2 | sort -u`; 
	for T in $CONF; do
		FILTER=`echo $T | sed 's/*/ /g'`
		grep -w "$FILTER" $FILE | awk '{estT+=$5;realT+=$6;algT+=$7;findPlan+=$11;next}END{printf("%s %s %0.2f %0.2f %0.2f %0.2f \n",$1,$2,estT/NR,realT/NR,algT/NR,findPlan/NR);}'; 
	done >> $NEWDIR'/'$FINALDIR'-'$MEANFILE'-'${DOMAINS[i]}
	i=$i+1;
       rm $FILE
done

# saving the name of the domains
FLI=`ls $NEWDIR'/'$FINALDIR'-'$MEANFILE* 2>/dev/null`
i=0
for FILE in $FLI; do
	sort -n -k 2 $FILE > aux
	mv aux $FILE
	DATA[$i]=$FILE
	i=$i+1
done

# generating pdf files
$TOOLS/plotPlanTimeFindPlan-icpas.sh ${DATA[0]} ${DATA[1]} $FINALDIR'/'$MEANFILE'TIME-REPAIR-'$TIMEFILE "mean time to repair a plan" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE
$TOOLS/plotPlanTimeT-icpas.sh ${DATA[0]} ${DATA[1]} $FINALDIR'/'$MEANFILE'TIME-ExtTree-'$TIMEFILE "mean time to generate the tree T" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE
$TOOLS/plotPlanTimeTAll-icpas.sh ${DATA[0]} ${DATA[1]} $FINALDIR'/'$MEANFILE'TIMEAll-ExtTree-'$TIMEFILE "mean time to generate the tree T" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE

# one domain without window and depth
$TOOLS/graph1DomainOneDomain.sh ${DATA[0]} $FINALDIR'/'$MEANFILE'TIMEHistogram-'$TIMEFILE ${DOMAINS[1]}
# one domain with window and depth
$TOOLS/graph1DomainOneDomainWD.sh ${DATA[0]} $FINALDIR'/'$MEANFILE'TIMEHistogramWD-'$TIMEFILE ${DOMAINS[1]}
#$TOOLS/meanTimesDomain.sh ${DATA[0]} ${DATA[1]} ${DATA[2]} ${DATA[3]} ${DATA[4]} $FINALDIR'/'$MEANFILE'TIMEHistogram-'$TIMEFILE
