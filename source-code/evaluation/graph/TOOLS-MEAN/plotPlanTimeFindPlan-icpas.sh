#!/bin/bash

#RELACIÓN REPAIR VS TIME BY DOMAINS
FILE1=$1
FILE2=$2
RESULT=$3
TITLE=$4
TITLE1=$5
TITLE2=$6
TIMELIMIT=$7

cat <<EOF | gnuplot
set terminal pdf monochrome solid font 'DejaVuSans,12' size 16cm,12cm
set size square
set size 1.0, 1.0
set origin 0.0, 0.0
set out 'Test.pdf'
#set grid ls 1
#set logscale x
#set logscale y
#set yrange [1:50]
set yrange [-0.1:*]
set xrange [0.9:*]
set ytics
set xlabel 'planning problems'
set ylabel 'time (ms)'
set title '$TITLE with an autonomous selection of the optimal window and depth - $TIMELIMIT'
#set nokey
set style line 2 lt 1 lc 1 pt 3
set style line 3 lt 1 lc 2 pt 4
plot '$FILE1' u 2:6 title "$TITLE1" w lp ls 2 ,\
'$FILE2' u  2:6 title "$TITLE2" w lp ls 3

MAX=GPVAL_Y_MAX
MIN=GPVAL_Y_MIN

set yrange [MIN:MAX+0.5]
set out '$RESULT.pdf'

replot

#save '$RESULT.gp'
EOF
rm 'Test.pdf'
