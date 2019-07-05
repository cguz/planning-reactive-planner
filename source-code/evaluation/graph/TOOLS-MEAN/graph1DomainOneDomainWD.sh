#!/bin/bash

FILE1=$1
RESULT=$2
DOMAIN=$3
TREE=$4

WD=`cut -d " " -f 7 $FILE1`

i=0
for F in $WD; do
	VAR[i]=$F
	let i=$i+1
done

WD=`cut -d " " -f 8 $FILE1`

i=0
for F in $WD; do
	REALNODES[i]=$F
	let i=$i+1
done

cat <<EOF | gnuplot
set term pdf enhanced #14 


num_of_categories=1
dx=0.7/num_of_categories
offset=0.06
time=1000
estType=3
realType=7
#labelOffset=0.05*time
#labelSize=8
#windowCol=6

set key above right
#set key top,0 
unset colorbox
#set grid ytics lw 0.2 lc rgb "#000000"
set title "real time to generate the search tree $TREE"
#set title " "
set xlabel "planning problems - $DOMAIN domain" offset 0,graph -0.06
set ylabel "time (ms)"
set boxwidth 0.5/num_of_categories

set termoption dash

set style fill pattern border -1
set style line 1 lt 2 lc rgb "black" pt 3
set style line 2 lt 1 lc 2
set style line 3 lt 1 lc 3
set style line 4 lt 1 lc 4
set style line 5 lt 1 lc 5
set style line 6 lt 1 lc 6
set style line 7 lt 1 lc 7

set xtics offset 0,graph -0.085

set out '$RESULT.pdf'
plot  0 title "real time:" w boxes fill pattern realType ls 0, \
'$FILE1' u (stringcolumn(2)+offset):3 title "estimated time:" w boxes fill pattern estType ls 0, \
time title "time limit:" ls 1, \
'' u (stringcolumn(2)+offset):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 0, \
'' u (stringcolumn(2)+offset):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 0

offset2=0.0905
offset3=0.078

set label '(${VAR[0]})' font ',8' at graph offset3, graph -0.02 
set label '(${VAR[1]})' font ',8' at graph offset3+offset2*(1), graph -0.02 
set label '(${VAR[2]})' font ',8' at graph offset3+offset2*(2), graph -0.02 
set label '(${VAR[3]})' font ',8' at graph offset3+offset2*(3), graph -0.02 
set label '(${VAR[4]})' font ',8' at graph offset3+offset2*(4), graph -0.02 
set label '(${VAR[5]})' font ',8' at graph offset3+offset2*(5), graph -0.02 
set label '(${VAR[6]})' font ',8' at graph offset3+offset2*(6), graph -0.02 
set label '(${VAR[7]})' font ',8' at graph offset3+offset2*(7), graph -0.02 
set label '(${VAR[8]})' font ',8' at graph offset3+offset2*(8), graph -0.02 
set label '(${VAR[9]})' font ',8' at graph offset3+offset2*(9), graph -0.02 

offset2=0.0905
set label 'total nodes:' font ',8' at graph offset3-0.12, graph -0.065 
set label '${REALNODES[0]}' font ',8' at graph offset3, graph -0.065 
set label '${REALNODES[1]}' font ',8' at graph offset3+offset2*(1), graph -0.065 
set label '${REALNODES[2]}' font ',8' at graph offset3+offset2*(2), graph -0.065 
set label '${REALNODES[3]}' font ',8' at graph offset3+offset2*(3), graph -0.065 
set label '${REALNODES[4]}' font ',8' at graph offset3+offset2*(4), graph -0.065 
set label '${REALNODES[5]}' font ',8' at graph offset3+offset2*(5), graph -0.065 
set label '${REALNODES[6]}' font ',8' at graph offset3+offset2*(6), graph -0.065 
set label '${REALNODES[7]}' font ',8' at graph offset3+offset2*(7), graph -0.065 
set label '${REALNODES[8]}' font ',8' at graph offset3+offset2*(8), graph -0.065 
set label '${REALNODES[9]}' font ',8' at graph offset3+offset2*(9), graph -0.065 

set label 'planning horizon l and depth d: (l, d)' font ',12' at graph 0.01, graph 1.2 

#save 'plot1.gp'
#, \
#'MEAN-DriverLog' u ($2+offset):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' , \
#'MEAN-logistics' u ($2+offset+dx):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' 

MAX=GPVAL_Y_MAX
MIN=GPVAL_Y_MIN

set yrange [MIN:(1.1*time)]
set xrange [0:11]
#set logscale y
set out '$RESULT.pdf'

replot

EOF
