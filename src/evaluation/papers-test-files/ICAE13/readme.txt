# *************************************************************************************
# ESTIMATING THE SIZE - TRAINING BENCHMAKRS
# JOURNAL: ICAE13 - RV. 2340
# 
# In this document we show the training benchmarks and R commands to calculate 
# the weights of the linear predictive model used to estimate the branching factor. 
# And additionally, we show the R commands to calculate the mean time to evaluate a node.
#
# *************************************************************************************

# The training benchmarks may be found in: "training-sets/filtered-benchmarks.t"
# This file is a normal plain text.

# The R commands to calcultae the weights of the predictive model and the mean time are the follows: 


*************************************************************************************

##  R COMMANDS ##

# read training data
d <- read.table("analysisAllFilter", header=T)

# load the data and attach
m1 <- d[,c("window", "depth", "Rvar", "DRvar", "RvarRoot","FluentsRoot", "Producers", "ProducersU","Time", "N", "ENodes", "Domain")]
attach(m1)

#CALCULO DE FACTOR DE RAMIFICACION DE MUESTRAS
BH3 = ((d[,c("N")]^(1/d[,c("depth")]))-0.34)

# preparing datas
y.data <- data.frame(
	B=BH3,
	Time=d[,c("Time")],
	N=d[,c("N")],
	ENodes=d[,c("ENodes")],
	depth=d[,c("depth")],
	window=d[,c("window")],
	Rvar=d[,c("Rvar")],
	DRvar=d[,c("DRvar")],
	RvarRoot=d[,c("RvarRoot")],
	FluentsRoot=d[,c("FluentsRoot")],
	Producers=d[,c("Producers")],
	ProducersU=d[,c("ProducersU")]
)

## multi linear predictive model
prediction_model1 = lm(BH3 ~ Rvar+DRvar+RvarRoot+FluentsRoot+Producers+ProducersU)


# # # # # # # # # # # # # # # # # # # # # # #
# mean time of generated or evaluated nodes # 
# # # # # # # # # # # # # # # # # # # # # # #

meanT = mean(Time/N)

meanT

*************************************************************************************
