#!/bin/bash

FILE1=$1
RESULT=$2
TITLE=$3
TIMELIMIT=$4

cat <<EOF | gnuplot

set terminal pdf monochrome solid font 'DejaVuSans,12' size 16cm,12cm
set title '$TITLE'
set auto x
set auto y
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.6
set xtic scale 0 
set xlabel 'window,depth'
set style line 2 lt 1 lc 1 pt 3

set out '$RESULT.pdf'
plot '$FILE1' u 2:xticlabels(1) ti col(3) ls 2

EOF
