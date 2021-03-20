#!/bin/bash

cd TOOLS-MEAN
chmod +x *.sh
TOOLS=`pwd`
cd ..

#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`

MEANFILE=`echo 'MEANTIME-FINDPLAN'`
OVERALLERRORFILE=`echo 'A-OVERALLERROR'`
REALVSESTIMATEDFILE=`echo 'A-ESTIMATEDVSREALTIME'`
REALVSLIMITTIMEFILE=`echo 'B-LIMITTIMEVSREAL'`

TIMEDIR=`echo '1seg'`
LIMITTIME=`echo '1000'`
TEMPFILE=`echo 'evaluation_files'`
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
FILES=`ls $RESULTADOS/tes*/$TIMEDIR/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | cut -d " " -f 1,2,5,7,9,11,13,15,17,19,22,24,26,28,30,32 >> $TEMPDIR'/'$TEMPFILE
	
	# splitting it by domains 
	i=0
	for PFILE in `(cut -d " " -f 1 $TEMPDIR'/'$TEMPFILE | sort -u )`; do 
		grep $PFILE $TEMPDIR'/'$TEMPFILE > $TEMPDIR'/'$TEMPFILE'-'$PFILE
		DOMAINS[$i]=$PFILE
		i=$(($i+1))
	done
done


# ********************
# calculating the total of overestimate and underestimate with its average absolute error
# ********************
mv $TEMPDIR'/'$TEMPFILE $TEMPDIR'/allEvaluations'

TEMP=$TEMPDIR'/'$TEMPFILE
echo $TEMP

FLI=`ls $TEMP 2>/dev/null`

for FILE in $FLI; do
	sed /^$/d $FILE | awk 'BEGIN{
	u_mape=0;
	o_e=0;
	u_e=0;
	}
	{
	if($3 > $4){ # overestimating
		o_e+=1;
		o_temp=$4-$3;(o_temp<0)? o_mad+=o_temp*-1:o_mad+=o_temp;

		#o_tempMSE=$4-$3;o_tempMSE=o_tempMSE*o_tempMSE;o_mse+=o_tempMSE;
		#o_tempMAPE=$4-$3;(o_tempMAPE<0)?o_mape+=100*((o_tempMAPE*-1)/$4):o_mape+=100*(o_tempMAPE/$4);
		#printf("overestimate: %s > %s \n",$3,$4);
	}else{ # underestimating
		u_e+=1;
		u_temp=$4-$3;(u_temp<0)?u_mad+=u_temp*-1:u_mad+=u_temp;

		#u_tempMSE=$4-$3;u_tempMSE=u_tempMSE*u_tempMSE;u_mse+=u_tempMSE;
		#u_tempMAPE=$4-$3;(u_tempMAPE<0)?u_mape+=100*((u_tempMAPE*-1)/$4):u_mape+=100*(u_tempMAPE/$4);
		#printf("underestimate: %s < %s \n",$3,$4);	
	}
	next}END{
	#printf("%i %.0f %0.2f %0.2f %0.2f %i %.0f %0.2f %0.2f %0.2f \n",
	#o_e,(100*o_e/NR),o_mad/o_e,o_mse/o_e,o_mape/o_e,u_e,(100*u_e/NR),u_mad/u_e,u_mse/u_e,u_mape/u_e);
	printf("%i %.0f %0.2f %i %.0f %0.2f \n", o_e,(100*o_e/NR),o_mad/o_e,u_e,(100*u_e/NR),u_mad/u_e);
	}'; 
done >> $TEMPDIR'/'$REALVSESTIMATEDFILE

# adding legends in the file
sed -i "1i o_e % o_mad u_e % u_mad" $TEMPDIR'/'$REALVSESTIMATEDFILE




# ********************
# calculating the total number of times that the tree is generated on time 
# ********************
FLI=`ls $TEMP 2>/dev/null`
for FILE in $FLI; do
	sed /^$/d $FILE | awk -v limit=$LIMITTIME 'BEGIN{
			onTime=0;
			madOnTime=0;
			madOnTime=0;
	}
	{
		if($4 <= limit){
			onTime=onTime+1;
			madOnTime+=limit-$4;
		}else{
			madNotOnTime+=$4-limit;
		}
		if($3 < $4){// si subestimo
			subEstimation=subEstimation+1
			if($4 > limit){
				exceeds=exceeds+1;
			}
		}
		next
	}END{
		printf("%s %0f %0.2f %0.2f %0f %0f %0.2f, %0.2f, %0.2f\n",
		$1,NR,100*onTime/NR,madOnTime/onTime,100*(NR-onTime)/NR,madNotOnTime/(NR-onTime),subEstimation,exceeds,100*exceeds/subEstimation);
	}';
done >> $TEMPDIR'/'$REALVSLIMITTIMEFILE'-'${DOMAINS[i]}

# adding legends in the file
sed -i "1i domain t_exec %OnTime mad_OnTime %NotOnTime madNotOntime subestimation exceeds %" $TEMPDIR'/'$REALVSLIMITTIMEFILE'-'${DOMAINS[i]}


# ********************
# calculating the mean of the times 
# ********************
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
		}
		{
		estT+=$3;
		realT+=$4;
		algT+=$5;
		if($8 == 1){
			not+=1;
			NotfindPlan+=$9;
			NotfindPlanSQ+=$9*$9;
		}else{
			findPlan+=$9;
			findPlanSQ+=$9*$9;
		}
		next}
		END{
		#//printf("%s %s %s %0.2f %0.2f %0.2f %0.2f %s %0.2f %s \n",
		#//$1,$2,NR,estT/NR,realT/NR,algT/NR,findPlan/NR,(NR-not),NotfindPlan/NR,not);
		printf("%s %s %s %s %0.2f %0.2f %s %0.2f %0.2f \n",
		$1,$2,NR,(NR-not),findPlan/NR,sqrt(findPlanSQ/NR - ((findPlan/NR)*(findPlan/NR))),not,NotfindPlan/NR,sqrt(NotfindPlanSQ/NR - ((NotfindPlan/NR)*(NotfindPlan/NR))));
	}'; 
	done >> $TEMPDIR'/'$MEANFILE'-'${DOMAINS[i]}
	i=$i+1;	
done

# ********************
# calculating the overall error 
# ********************
FLI=`ls $TEMP* 2>/dev/null`
i=0
for FILE in $FLI; do
	CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-2 | sort -u`; 
	for T in $CONF; do
		FILTER=`echo $T | sed 's/*/ /g'`
		grep -w "$FILTER" $FILE | awk 'BEGIN{
			u_mape=0;
		}
		{
		temp=$4-$3;(temp<0)?mad+=temp*-1:mad+=temp;
		tempMSE=$4-$3;tempMSE=tempMSE*tempMSE;mse+=tempMSE;
		tempMAPE=$4-$3;(tempMAPE<0)?mape+=100*((tempMAPE*-1)/$4):mape+=100*(tempMAPE/$4);
		if($3 > $4){
			o_e+=1;
			o_temp=$4-$3;(o_temp<0)?o_mad+=o_temp*-1:o_mad+=o_temp;
			o_tempMSE=$4-$3;o_tempMSE=o_tempMSE*o_tempMSE;o_mse+=o_tempMSE;
			o_tempMAPE=$4-$3;(o_tempMAPE<0)?o_mape+=100*((o_tempMAPE*-1)/$4):o_mape+=100*(o_tempMAPE/$4);
		}else{
			u_e+=1;
			u_temp=$4-$3;(u_temp<0)?u_mad+=u_temp*-1:u_mad+=u_temp;
			u_tempMSE=$4-$3;u_tempMSE=u_tempMSE*u_tempMSE;u_mse+=u_tempMSE;
			u_tempMAPE=$4-$3;(u_tempMAPE<0)?u_mape+=100*((u_tempMAPE*-1)/$4):u_mape+=100*(u_tempMAPE/$4);	
		}
		next}END{printf("%s %s %0.2f %0.2f %0.2f %0.2f %0.2f %0.2f %0.2f %0.2f %0.2f \n",
		$1,$2,mad/NR,mse/NR,mape/NR,(o_e>0)?o_mad/o_e:0,(o_e>0)?o_mse/o_e:0,(o_e>0)?o_mape/o_e:0,(u_e>0)?u_mad/u_e:0,(u_e>0)?u_mse/u_e:0,(u_e>0)?u_mape/u_e:0);}'; 
	done | awk '
		BEGIN{for(i=3;i<=NF;i++){sum[i]=0;cont[i]}}
		{for(i=3;i<=NF;i++){
			sum[i]+=$i;
			if($i>0)cont[i]++;
		}printf("%s\n",$0)}
		END{for(i=3;i<=NF;i++)printf("%0.2f ",sum[i]/cont[i]);printf("\n");}' > $TEMPDIR'/'$OVERALLERRORFILE'-'${DOMAINS[i]}
	i=$i+1;
done

# calculating the number of times that the tree is generated on time 
FLI=`ls $TEMPDIR'/'$TEMPFILE* 2>/dev/null`
i=0
for FILE in $FLI; do
	CONF=`sed 's/[ \t]/*/g' $FILE | cut -d '*' -f 1-2 | sort -u`; 
	for T in $CONF; do
		FILTER=`echo $T | sed 's/*/ /g'`
		grep -w "$FILTER" $FILE | awk '{
		time=1000
		if($4 < time){
			onTime=onTime+1;
		}else{
			temp=$4-time;(temp<0)?mad+=temp*-1:mad+=temp;
			tempMSE=$4-time;tempMSE=tempMSE*tempMSE;mse+=tempMSE;
			tempMAPE=$4-time;(tempMAPE<0)?mape+=100*((tempMAPE*-1)/$4):mape+=100*(tempMAPE/$4);
		}
		next}END{printf("%s %s %0.2f %0.2f %0.2f %0.2f \n",
		$1,$2,100*onTime/NR,mad/NR,mse/NR,mape/NR);}';
	done >> $TEMPDIR'/'$REALVSLIMITTIMEFILE'-'${DOMAINS[i]}
	i=$i+1;
        rm $FILE	
done

