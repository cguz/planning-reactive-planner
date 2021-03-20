#!/bin/bash

cd TOOLS-MEAN
chmod +x *.sh
TOOLS=`pwd`
cd ..

#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`

MEANFILE=`echo 'MEANTIME-FINDPLAN'`
MEDIANFILE=`echo 'MEDIAN'`

TIMEDIR=`echo '1seg'`
LIMITTIME=`echo '1000'`

TEMPFILE=`echo 'allEvaluationFiles'`
TEMPFILEBYDOMAIN=`echo 'evaluation_files'`
TEMPDIR=`echo 'temp_files'`
rm -R $TEMPDIR
mkdir $TEMPDIR

##################################################################### 
# preparing the correct format of the files and saving it in a temporal dir
# for each test file in the results folder
#####################################################################
# 1: domain name
# 2: pfile
# 3: 5: estimated time to generate T
# 4: 7: real time to generate T
# 5: 9: real time to generate T + algorithm to select the root partial state and the depth
# 6: 11: not found destiny
# 7: 13: not found origin
# 8: 15: not found plan
# 9: 17: time to repairing a plan failure
# 10: 19: actions that add when find a plan, it can be negative or positive
# 11: 22: total of actions of the original plan
# 12: 24: stability of the plan
# 13: 26: total of actions of the repaired plan

# 14: 28: actions in the repairing window of the original plan
# 15: 30: actions of the repairing window reused to repair the plan
# 16: 32: total of actions of the repaired plan to the root node
echo "-> preparing format - ok";
FILES=`ls $RESULTADOS/tes*/$TIMEDIR/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | cut -d " " -f 1,2,5,7,9,11,13,15,17,19,22,24,26,28,30,32 >> $TEMPDIR'/'$TEMPFILE
	
	# splitting it by domains 
	i=0
	for PFILE in `(cut -d " " -f 1 $TEMPDIR'/'$TEMPFILE | sort -u )`; do 
		grep $PFILE $TEMPDIR'/'$TEMPFILE > $TEMPDIR'/'$TEMPFILEBYDOMAIN'-'$PFILE
		DOMAINS[$i]=$PFILE
		i=$(($i+1))
	done
done


TEMP=$TEMPDIR'/'$TEMPFILEBYDOMAIN

# ********************
# calculating the mean times 
# ********************
echo "-> calculating the mean real time to find a plan - ...";
FLI=`ls $TEMP* 2>/dev/null`
i=0
for FILE in $FLI; do
	CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-2 | sort -u`; 
	for T in $CONF; do
		FILTER=`echo $T | sed 's/*/ /g'`
		grep -w "$FILTER" $FILE | awk '
		BEGIN{
			not=0;
			estT=0;
			realT=0;
			algT=0;
			NotfindPlan=0;
			findPlan=0;
			NotfindPlanSQ=0;
			findPlanSQ=0;
		}{
		estT+=$3;
		realT+=$4;
		algT+=$5;
		if($8 == 1){ # found plan
			findPlan+=$9;
			findPlanSQ+=$9*$9;
		}else{
			not+=1;
			NotfindPlan+=$9;
			NotfindPlanSQ+=$9*$9;
		}
		next}
		END{
			printf("%s %s %s %s %0.2f %0.2f %s %0.2f %0.2f \n",
		$1,$2,NR,(NR-not),findPlan/(NR-not),sqrt(findPlanSQ/(NR-not) - ((findPlan/(NR-not))*(findPlan/(NR-not)))),not,NotfindPlan/not,sqrt(NotfindPlanSQ/not - ((NotfindPlan/not)*(NotfindPlan/not))));
	}'; 
	done >> $TEMPDIR'/'$MEANFILE'-'${DOMAINS[i]}
	i=$i+1;	
done
echo "-> calculating the mean real time to find a plan - ok";
