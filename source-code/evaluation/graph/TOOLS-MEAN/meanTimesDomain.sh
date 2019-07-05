#!/bin/bash

FILE1=$1
FILE2=$2
FILE3=$3
FILE4=$4
FILE5=$5
RESULT=$6

cat <<EOF | gnuplot
set term pdf #enhanced 14

num_of_categories=5
dx=0.7/num_of_categories
offset=-0.1
time=1000
estType=3
realType=2
#labelOffset=0.05*time
#labelSize=8
#windowCol=6

set key top left
unset colorbox
set grid ytics lw 0.2 lc rgb "#000000"
set title "Estimation within real tree generation time"
set xlabel "planning problem"
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
plot time title "time limit" ls 1, \
 0 title "real time" w boxes fill pattern realType ls 4, \
'$FILE1' u (stringcolumn(2)+offset):3 title "driverlog" w boxes fill pattern estType ls 2, \
'' u (stringcolumn(2)+offset):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 4, \
'' u (stringcolumn(2)+offset):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 2, \
'$FILE2' u (stringcolumn(2)+offset+dx):3 title "logistics" w boxes fill pattern estType ls 3, \
'' u (stringcolumn(2)+offset+dx):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 4, \
'' u (stringcolumn(2)+offset+dx):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 3, \
'$FILE3' u (stringcolumn(2)+offset+dx*2):3 title "openstacks" w boxes fill pattern estType ls 5, \
'' u (stringcolumn(2)+offset+dx*2):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 4, \
'' u (stringcolumn(2)+offset+dx*2):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 5, \
'$FILE4' u (stringcolumn(2)+offset+dx*3):3 title "rovers" w boxes fill pattern estType ls 6, \
'' u (stringcolumn(2)+offset+dx*3):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 4, \
'' u (stringcolumn(2)+offset+dx*3):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 6, \
'$FILE5' u (stringcolumn(2)+offset+dx*4):3 title "Woodworking" w boxes fill pattern estType ls 7, \
'' u (stringcolumn(2)+offset+dx*4):(\$4 < (time*2) ? \$4 : (time*2)) notitle w boxes fill pattern realType ls 4, \
'' u (stringcolumn(2)+offset+dx*4):(\$3 < \$4 ? \$3 : 0) notitle w boxes fill pattern estType ls 7
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
