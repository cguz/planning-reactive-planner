#!/bin/bash

cd TOOLS-MEAN
chmod +x *.sh
TOOLS=`pwd`
cd ..

#RESULTADOS=`echo '/home/zenshi/Trabajos/java/Doctorado/ReactivePlanner/results'`
RESULTADOS=`echo '/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/results'`

MEANFILE=`echo 'MEANTIME-findingPlan'`
OVERALLERRORFILE=`echo 'MEANAbsoluteERROR'`
REALVSESTIMATEDFILE=`echo 'REALVSEstimatedTime'`
REALVSLIMITTIMEFILE=`echo 'REALVSLimitTime'`

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
# 2: number of the search tree
# 3: pfile
# 4: 7: limit time
# 5: 8: estimated time to generate T
# 6: 9: real time to generate T
FILES=`ls $RESULTADOS/tes*/$TIMEDIR/evaluationsRP* 2>/dev/null`
for F in $FILES; do
	NEWFILE=`echo $F`

	sed 's/[ \t][ \t]*/ /g' $F | sed s/"pfile"//g | cut -d " " -f 1,2,3,7,8,9 >> $TEMPDIR'/'$TEMPFILE
	
	# splitting it by domains 
	i=0
	j=0
	for TREE in `(cut -d " " -f 2 $TEMPDIR'/'$TEMPFILE | sort -u )`; do 
		TREES[$j]=$TREE
		j=$(($j+1))
		for DOMAIN in `(cut -d " " -f 1 $TEMPDIR'/'$TEMPFILE | sort -u )`; do 
			grep $DOMAIN' '$TREE $TEMPDIR'/'$TEMPFILE | sed s/$DOMAIN' '$TREE/$DOMAIN/g > $TEMPDIR'/'$TEMPFILE'-'$DOMAIN'-'$TREE
			DOMAINS[$i]=$DOMAIN
			i=$(($i+1))
		done
		echo "preparing file"$TREE $DOMAIN
	done
done

# format 1: TEMPDIRFILES
# 1: domain name
# 2: pfile
# 3: 7: limit time
# 4: 8: estimated time to generate T
# 5: 9: real time to generate T
mv $TEMPDIR'/'$TEMPFILE $TEMPDIR'/allEvaluations'
TEMP=$TEMPDIR'/'$TEMPFILE
echo ""

#for each search space
for x in `seq 0 $(($j-1))`; do


	echo "working with the tree" ${TREES[$x]}

	# ********************
	# calculating the total of overestimate and underestimate with its average absolute error
	# ********************
	echo "-> calculating overall error real time vs estimated time"
	FLI=`ls $TEMP*'-'${TREES[$x]} 2>/dev/null`
	for FILE in $FLI; do
		sed /^$/d $FILE | awk 'BEGIN{
		u_mape=0;
		o_e=0;
		u_e=0;
		}{
		if($4 > $5){ # overestimating
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
		printf("%i %.0f %0.2f %i %.0f %0.2f \n", o_e,(100*o_e/NR),o_mad/o_e,u_e,(100*u_e/NR),u_mad/u_e);


		#printf("%i %.0f %0.2f %0.2f %0.2f %i %.0f %0.2f %0.2f %0.2f \n",
		#o_e,(100*o_e/NR),o_mad/o_e,o_mse/o_e,o_mape/o_e,u_e,(100*u_e/NR),u_mad/u_e,u_mse/u_e,u_mape/u_e);
		}'; 
	done >> $TEMPDIR'/'${TREES[$x]}'-'$REALVSESTIMATEDFILE
	# adding legends in the file
	sed -i "1i o_e % o_mad u_e % u_mad" $TEMPDIR'/'${TREES[$x]}'-'$REALVSESTIMATEDFILE



	# ********************
	# calculating the total number of times that the tree is generated on time 
	# ********************
	echo "-> calculating overall error real time vs time limit"
	FLI=`ls $TEMP*'-'${TREES[$x]} 2>/dev/null`
	i=0
	for FILE in $FLI; do
		sed /^$/d $FILE | awk 'BEGIN{
			onTime=0;
			madOnTime=0;
			madOnTime=0;
		}{
			if($5 <= $3){
				onTime=onTime+1;
				madOnTime+=$3-$5;
			}else{
				madNotOnTime+=$5-$3;
			}
			if($4 < $5){ // si subestimo
				subEstimation=subEstimation+1
				if($5 > limit){
					exceeds=exceeds+1;
				}
			}
			next
		}END{
			printf("%s %0f %0.2f %0.2f %0f %0f %0.2f, %0.2f, %0.2f\n",
			$1,NR,100*onTime/NR,madOnTime/onTime,100*(NR-onTime)/NR,madNotOnTime/(NR-onTime),subEstimation,exceeds,100*exceeds/subEstimation);
		}';
	done >> $TEMPDIR'/'${TREES[$x]}'-'$REALVSLIMITTIMEFILE

	# adding legends in the file
	sed -i "1i domain t_exec %OnTime mad_OnTime %NotOnTime madNotOntime subestimation exceeds %" $TEMPDIR'/'${TREES[$x]}'-'$REALVSLIMITTIMEFILE




	# ********************
	# calculating the overall error 
	# ********************
	echo "-> calculating overall error file"
	FLI=`ls $TEMP*'-'${TREES[$x]} 2>/dev/null`
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
			END{for(i=3;i<=NF;i++)printf("%0.2f ",sum[i]/cont[i]);printf("\n");}' > $TEMPDIR'/'${TREES[$x]}'-'$OVERALLERRORFILE'-'${DOMAINS[i]}
		i=$i+1;
	done

done

