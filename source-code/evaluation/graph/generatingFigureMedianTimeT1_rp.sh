#!/bin/bash

cd 'TOOLS-MEAN'
chmod +x *.sh
TOOLS=`pwd`
cd ..

#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`

TEMPDIRFILES=`echo 'temp_files'`
TEMPFILE=`echo 'evaluation_files'`

TEMPDIRFIGURES=`echo 'temp_figures'`
MEDIANFILE=`echo 'MEDIAN'`
TIMEFILE=`echo '1seg'`

rm -R $TEMPDIRFILES
rm -R $TEMPDIRFIGURES
mkdir $TEMPDIRFILES
mkdir $TEMPDIRFIGURES


##################################################################### 
# preparing the correct format of the files and saving it in a temporal dir
# for each test file in the results folder
#####################################################################
# 1: domain name
# 2: number of the search tree
# 3: pfile
# 4: 5: selected root partial state
# 5: 6: depth 
# 6: 8: estimated time to generate T
# 7: 10: real time to generate T
# 8: 12: real time to generate T + algorithm to select the root partial state and the depth
# 9: 14: not found destiny
# 10: 16: not found origin
# 11: 18: not found plan
# 12: 20: time to repairing a plan failure
# 13: 22: actions that add when find a plan, it can be negative or positive
# 14: 25: total of actions of the original plan
# 15: 27: stability of the plan
# 16: 29: total of actions of the repaired plan
# 17: 37: real nodes
FILES=`ls $RESULTADOS/tes*/$TIMEFILE/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | sed s/" l"/" "/g | sed s/"w"//g | cut -d " " -f 1,2,3,5,6,8,10,12,14,16,18,20,22,25,27,29,37 >> $TEMPDIRFILES'/'$TEMPFILE

	# splitting it by domains 
	i=0
	j=0
	for TREE in `(cut -d " " -f 2 $TEMPDIRFILES'/'$TEMPFILE | sort -u )`; do 
		TREES[$j]=$TREE
		j=$(($j+1))
		for DOMAIN in `(cut -d " " -f 1 $TEMPDIRFILES'/'$TEMPFILE | sort -u )`; do 
			grep $DOMAIN' '$TREE $TEMPDIRFILES'/'$TEMPFILE | sed s/$DOMAIN' '$TREE/$DOMAIN/g > $TEMPDIRFILES'/'$TEMPFILE'-'$DOMAIN'-'$TREE
			DOMAINS[$i]=$DOMAIN
			i=$(($i+1))
		done
	done
done


#for each search space
#for x in `seq 1 $j`; do
x=0
	# for each domain with search space 1
	FLI=`ls $TEMPDIRFILES'/'$TEMPFILE*'-'${TREES[x]} 2>/dev/null`
	i=0
	for FILE in $FLI; do
		CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-2 | sort -u`; 
		for T in $CONF; do
			FILTER=`echo $T | sed 's/*/ /g'`
			grep -w "$FILTER" $FILE | sort -n -k 6 | awk '
	{
		estT[NR]=$5;
		realT[NR]=$6;
		algT[NR]=$7;
		findPlanT[NR]=$11;
	}
	END{
		if(NR%2){
			printf("%s %s %0.2f %0.2f %0.2f %0.2f %s,%s %s \n",$1,$2,estT[(NR+1)/2],realT[(NR+1)/2],algT[(NR+1)/2],findPlanT[(NR+1)/2],$3,$4,$16);
		}else{
		printf("%s %s %0.2f %0.2f %0.2f %0.2f %s,%s %s \n",$1,$2,(estT[(NR / 2)]+estT[(NR / 2)+1])/2.0,(realT[(NR / 2)]+realT[(NR / 2)+1])/2.0,(algT[(NR / 2)]+algT[(NR / 2)+1])/2.0,(findPlanT[(NR / 2)]+findPlanT[(NR / 2)+1])/2.0,$3,$4,$16);		
		}
	}'; 
	done >> $TEMPDIRFILES'/'$TEMPFILE'-'$MEDIANFILE'-'${DOMAINS[i]}'-'${TREES[x]}
	i=$i+1;
	done

	# saving the name of the domains
	FLI=`ls $TEMPDIRFILES'/'$TEMPFILE'-'$MEDIANFILE*'-'${TREES[x]} 2>/dev/null`
	i=0
	for FILE in $FLI; do
		sort -n -k 2 $FILE > aux
		mv aux $FILE
		DATA[$i]=$FILE
		i=$i+1
	done

	# generating pdf files
	#$TOOLS/plotPlanTimeFindPlan-icpas.sh ${DATA[0]} ${DATA[1]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIME-REPAIR-'$TIMEFILE "MEDIAN time to repair a plan" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE
	#$TOOLS/plotPlanTimeT-icpas.sh ${DATA[0]} ${DATA[1]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIME-ExtTree-'$TIMEFILE "MEDIAN time to generate the tree T" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE
	#$TOOLS/plotPlanTimeTAll-icpas.sh ${DATA[0]} ${DATA[1]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIMEAll-ExtTree-'$TIMEFILE "MEDIAN time to generate the tree T" ${DOMAINS[0]} ${DOMAINS[1]} $TIMEFILE

	# one domain without window and depth
	$TOOLS/graph1DomainOneDomain.sh ${DATA[0]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIMEHistogram-'$TIMEFILE ${DOMAINS[0]} ${TREES[x]}
	# one domain with window and depth
	$TOOLS/graph1DomainOneDomainWD.sh ${DATA[0]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIMEHistogramWD-'$TIMEFILE ${DOMAINS[0]} ${TREES[x]}
	#$TOOLS/MEDIANTimesDomain.sh ${DATA[0]} ${DATA[1]} ${DATA[2]} ${DATA[3]} ${DATA[4]} $TEMPDIRFIGURES'/'$MEDIANFILE'TIMEHistogram-'$TIMEFILE

#done
