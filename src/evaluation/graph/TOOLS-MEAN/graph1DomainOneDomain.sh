#!/bin/bash

FILE1=$1
RESULT=$2
DOMAIN=$3
TREE=$4

cat <<EOF | gnuplot
set term pdf #enhanced 14

num_of_categories=1
dx=0.7/num_of_categories
offset=-0.1
time=1000
estType=3
realType=7
#labelOffset=0.05*time
#labelSize=8
#windowCol=6

set key top left
unset colorbox
#set grid ytics lw 0.2 lc rgb "#000000"
set title "Real time against time limit to generate the search tree $TREE"
set xlabel "planning problems - $DOMAIN domain"
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

set out '$RESULT.pdf'
plot time title "time limit" ls 9, \
 0 title "real time" w boxes fill pattern realType ls 0, \
'$FILE1' u (stringcolumn(2)+offset):3 title "estimated time" w boxes fill pattern estType ls 0, \
'' u (stringcolumn(2)+offset):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 0, \
'' u (stringcolumn(2)+offset):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 0
#save 'plot1.gp'
#, \
#'MEAN-DriverLog' u ($2+offset):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' , \
#'MEAN-logistics' u ($2+offset+dx):(($3<$4 ? $4 : $3)+labelOffset):windowCol notitle w labels font ',labelSize' 

MAX=GPVAL_Y_MAX
MIN=GPVAL_Y_MIN

set yrange [MIN:(4*time)]
#set logscale y
set out '$RESULT.pdf'

replot

EOF
