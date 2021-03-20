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
	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | sed s/" l"/" "/g | sed s/"w"//g | cut -d " " -f 1,5,7,9,11,13,15,17,19,22,24,26,3,4 >> $NEWDIR'/'$FINALDIR

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
	CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-3 | sort -u`; 
	for T in $CONF; do
		let i=$i+1;
		FILTER=`echo $T | sed 's/*/ /g'`
		grep -w "$FILTER" $FILE | sort -n -k 6 | awk -v Var=$i '{estT[NR]=$4;realT[NR]=$5;algT[NR]=$6;findPlan[NR]=$10;}
END{
	if(NR%2){
		printf("%s %s %s,%s %0.2f %0.2f %0.2f %0.2f %s \n",$1,Var,$2,$3,estT[(NR+1)/2],realT[(NR+1)/2],algT[(NR+1)/2],findPlan[(NR+1)/2],NR);
	}else{
		printf("%s %s %s,%s %0.2f %0.2f %0.2f %0.2f %s \n",$1,Var,$2,$3,(estT[(NR / 2)]+estT[(NR / 2)+1])/2.0,(realT[(NR / 2)]+realT[(NR / 2)+1])/2.0,(algT[(NR / 2)]+algT[(NR / 2)+1])/2.0,(findPlan[(NR / 2)]+findPlan[(NR / 2)+1])/2.0,NR);		
	}
}' ; 
	done >> $NEWDIR'/'$FINALDIR'-'$MEANFILE'-'${DOMAINS[i]}
	
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
$TOOLS/graph2DomainOneDomain.sh ${DATA[0]} $FINALDIR'/'$MEANFILE'TIMEHistogram-'$TIMEFILE
# one domain with window and depth
#$TOOLS/graph1DomainOneDomainWD.sh ${DATA[0]} $FINALDIR'/'$MEANFILE'TIMEHistogramWD-'$TIMEFILE
#$TOOLS/meanTimesDomain.sh ${DATA[0]} ${DATA[1]} ${DATA[2]} ${DATA[3]} ${DATA[4]} $FINALDIR'/'$MEANFILE'TIMEHistogram-'$TIMEFILE
