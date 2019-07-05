#!/bin/bash

FILE1=$1
FILE2=$2
RESULT=$3
TITLE=$4
TIMELIMIT=$5

cat <<EOF | gnuplot

set terminal pdf solid font 'DejaVuSans,12' size 16cm,12cm
set title '$TITLE'
set auto x
#set xrange [0:*]
set style data histogram

set key invert reverse Left outside
#set key autotitle columnheader

#set xtics nomirror rotate by -45 scale 0 font ",8"

#set style histogram rows
#set style histogram cluster gap 1
set style histogram gap 5 rowstacked 

set style fill solid noborder
#set style fill solid border -1

set boxwidth 0.6
set xtic scale 0 
set xlabel 'planning problems'
set style line 2 lt 1 lc 0 pt 3

set out '$RESULT.pdf'
plot '$FILE1' u 3:xtic(2) ti col(1), '' u 4 ti col(1) ls 2, \
	'$FILE2' u 3:xtic(2) ti col(1), '' u 4 ti col(1) ls 2

EOF
#rm 'Test.pdf'
