# !/usr/bin/env python
# -*- coding: utf-8 -*-

f = 'forward-ps-pfile2'
df = 'digraph-'
infile = open(f, 'r')

# partial_state
ps = []
# actions
act = []

for line in infile:
	temp = line.split(":")
	if "partial state" in temp[0]:
		#index = int(temp[0].replace("partial state",""))
		ps.append(temp[1])
		print(temp[1])
		
	if "action" in temp[0]:
		act.append(temp[1])
		print(temp[1])

# Cerramos el fichero.
infile.close()


infile = open(df+f, 'w')
infile.write('digraph '+df+f+'{ ');
infile.write('rankdir=LR;')
infile.write('size="8,5";')
infile.write('node [shape=none];')

for i in range(len(ps)-1):
	infile.write(ps[i]+'->'+ps[i+1]+'[ label=""]')

infile.write('}')
infile.close()
