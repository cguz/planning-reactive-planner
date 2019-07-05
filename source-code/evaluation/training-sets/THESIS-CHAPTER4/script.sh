#!/bin/bash
if [ "$1" == "" ]
then
	echo ""
	echo ""
	echo "Manual of use: "
	echo ""
	echo -e "\t-> Statistic for all the data"
	echo -e "\t\t./script.sh benchmark all limit_less L1 L2 L3 L4"
	echo ""
	echo -e "\t-> filter elements less or equals than the limit" 
	echo -e "\t\t./script.sh benchmark time limit"
	echo ""
	echo -e "\t-> Statistic for an specific domain"
	echo -e "\t\t./script benchmark domain limit_less min_task max_task"
	echo ""
	echo -e "\t-> Statistic to see how many times the repairing structures were generated out of time - time-bounded"
	echo -e "\t\t./script benchmark bounded domain"
	echo ""
	echo -e "\t-> Generate table time-bounded"
	echo -e "\t\t./script benchmark table"
	echo ""
	echo -e "\t-> Count total number of window"
	echo -e "\t\t./script benchmark [count_window]"
	echo ""
	echo ""
	exit
fi

L=$3
if [ "$2" == "all" ]
then  
	L1=$4
	L2=$5
	L3=$6
	awk -v LIMIT=$L -v L1=$L1 -v L2=$L2 -v L3=$L3 'BEGIN{total = 0;
	less=0; max=0; maxl=0; maxm=0;
	less1=0; max1=0; maxl1=0; maxm1=0;
	less2=0; max2=0; maxl2=0; maxm2=0;
	less3=0; max3=0; maxl3=0; maxm3=0;
	less4=0; max4=0; maxl4=0; maxm4=0;} 
	{
		total = total +1
		if($5 <= LIMIT){
			less=less+1
			if($5 > max){
				max=$5
				maxl=$2
				maxm=$3
			}
		}
		if($5 > LIMIT && $5 <= L1){
			less1=less1+1
			if($5 > max1){
				max1=$5
				maxl1=$2
				maxm1=$3
			}
		}
		if($5 > L1 && $5 <= L2){
			less2=less2+1
			if($5 > max2){
				max2=$5
				maxl2=$2
				maxm2=$3
			}
		}
		if($5 > L2 && $5 <= L3){
			less3=less3+1
			if($5 > max3){
				max3=$5
				maxl3=$2
				maxm3=$3
			}
		}
		if($5 > L3){
			less4=less4+1
			if($5 -gt max4){
				max4=$5
				maxl4=$2
				maxm4=$3
			}
		}

	} 
	END {print total,less, max, "(" maxl "," maxm ")", less1, max1, "(" maxl1 "," maxm1 ")", less2, max2, "(" maxl2 "," maxm2 ")", less3, max3, "(" maxl3 "," maxm3 ")", less4, max4, "(" maxl4 "," maxm4 ")" }' $1
else
  if [ "$2" == "time" ]
  then
	awk -v LIMIT=$L '{if ($5 <= LIMIT){ print $0; }} ' $1
  else  
	if [ "$2" == "bounded" ]
	then

	for entry in "$1"/*
	do

		grep $3 $entry | awk -v DIR=$entry 'BEGIN{total = 0;less=0;high=0; max=0; maxl=0; maxm=0;domain=""} 
		{
		total = total +1
		if ($9 >= $7){
			high=high+1
		}else { 
			less=less+1
		}
		if($9 > max){
			max=$9
			maxl=$5
			maxm=$6
		}
		} 
		END {
		print DIR
		print "total: ", total, " less than ts: ", less, " higher than ts: ", high, " max complexity: ", max, "(" maxl "," maxm ")"
		print ""
		}'
	done

	else
	
	if [ "$2" == "table" ]
	then
		awk 'BEGIN{task=0; ltask=0} 
		{
		
			if(task != $2){
				task = $2
				printf(" \\\\ [-0.05cm]\n")
			}	

			if($4 == 1){
				printf("%d & (%d,%d) & %.2f & %\047d",$3, $5, $6, $9/1000, $10)
			}else{

				if($4 == 4){
					printf("& & %d & & & & & && & & & & %d & (%d,%d) & %d & %.2f & %\047d",$2,$3, $5, $6, $7/1000, $9/1000, $10)
				}else{
					printf(" & %d & (%d,%d) & %d & %.2f & %\047d",$3, $5, $6, $7/1000, $9/1000, $10)
				}
			}

			if($4 == 3){
				printf(" \\\\ [-0.05cm]\n")
			}
		
		} 
		END { }' $1
	else

	if [ "$2" == "count_window" ]
	then

		TOTAL=0
		for j in `seq 2 7`;
		do
			TOTAL=0
			for i in `seq 3 12`;
			do
				VAR=`awk '{printf ("%d,%d\n",$2,$3)}' $1 | grep -o "$j,$i" | wc -w`
	
				let TOTAL=$TOTAL+$VAR
				echo $j","$i ":" $VAR
			done
			echo "total: " $TOTAL
		done
	else
		for i in `seq $4 $5`;
		do
		grep $2 $1 | awk -v VAR=${i} -v LIMIT=$L 'BEGIN{total = 0;less=0;high=0; max=0; maxl=0; maxm=0;domain=""} 
		{if ($4 == VAR){
			total = total +1
			if($5 <= LIMIT){
				less=less+1
			}else{
				high=high+1
			}
			if($5 > max){
				max=$5
				maxl=$2
				maxm=$3
			}
			if(domain==""){
				domain = $1
			}
		}} 
		END {print domain, VAR, total, less, high, max, "(" maxl "," maxm ")"}'
		done
	fi
	fi
	fi
  fi
fi
