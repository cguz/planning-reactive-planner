#!/bin/bash

FILE1=$1
RESULT=$2

WD=`cut -d " " -f 3 $FILE1`

i=0
for F in $WD; do
	VAR[i]=$F
	let i=$i+1
done

WD=`cut -d " " -f 8 $FILE1`
i=0
for F in $WD; do
	REPETICIONES[i]=$F
	let i=$i+1
done

cat <<EOF | gnuplot
set term pdf #enhanced 14

num_of_categories=1
dx=0.7/num_of_categories
offset=0.0
time=1000
estType=3
realType=7
#labelOffset=0.05*time
#labelSize=8
#windowCol=6

set key top left
unset colorbox
#set grid ytics lw 0.2 lc rgb "#000000"
set title "Estimated time versus real time to generate the tree"
#set title "Estimated time versus real time to generate the tree\n grouped by window and depth selection"
set xlabel "window and depth" offset 0,graph -0.025
set ylabel "time (ms)"
set boxwidth 0.5/num_of_categories

set style fill pattern border -1
set style line 1 lt 1 lc 1 pt 4 lw 3
set style line 2 lt 1 lc 2
set style line 3 lt 1 lc 3
set style line 4 lt 1 lc 4
set style line 5 lt 1 lc 5
set style line 6 lt 1 lc 6
set style line 7 lt 1 lc 7

conteo=10
set xtics ("${VAR[0]}" conteo, "${VAR[1]}" conteo+1, "${VAR[2]}" conteo+2, "${VAR[3]}" conteo+3, "${VAR[4]}" conteo+4, "${VAR[5]}" conteo+5, "${VAR[6]}" conteo+6, "${VAR[7]}" conteo+7, "${VAR[8]}" conteo+8) offset 0,graph -0.025


offset2=0.1
offset3=0.085

set label '(${REPETICIONES[0]})' font ',8' at graph offset3+0.005, graph -0.02 
set label '(${REPETICIONES[1]})' font ',8' at graph offset3+offset2*(1), graph -0.02 
set label '(${REPETICIONES[2]})' font ',8' at graph offset3+offset2*(2), graph -0.02 
set label '(${REPETICIONES[3]})' font ',8' at graph offset3+offset2*(3), graph -0.02 
set label '(${REPETICIONES[4]})' font ',8' at graph offset3+offset2*(4), graph -0.02 
set label '(${REPETICIONES[5]})' font ',8' at graph offset3+offset2*(5), graph -0.02 
set label '(${REPETICIONES[6]})' font ',8' at graph offset3+offset2*(6), graph -0.02 
set label '(${REPETICIONES[7]})' font ',8' at graph offset3+offset2*(7), graph -0.02 
set label '(${REPETICIONES[8]})' font ',8' at graph 0.004+offset3+offset2*(8), graph -0.02 


set label '(#) number of executions' font ',12' at graph offset3-0.01, graph 0.76 


set out '$RESULT.pdf'
plot time title "time limit" ls 9, \
 0 title "real time" w boxes fill pattern realType ls 0, \
'$FILE1' u (stringcolumn(2)+offset):4 title "parcprinter" w boxes fill pattern estType ls 0, \
'' u (stringcolumn(2)+offset):(\$5 < (time*2) ? \$5 : (time*2)) notitle w boxes fill pattern realType ls 0, \
'' u (stringcolumn(2)+offset):(\$4 < \$5 ? \$4 : 0) notitle w boxes fill pattern estType ls 0
#save 'plot1.gp'
#, \
#'MEAN-DriverLog' u ($2+offset):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' , \
#'MEAN-logistics' u ($2+offset+dx):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' 

MAX=GPVAL_Y_MAX
MIN=GPVAL_Y_MIN

set yrange [MIN:(4*time)]
set xrange [conteo-1:conteo+9]
#set logscale y
set out '$RESULT.pdf'

replot

EOF
