package pelea.marp.service.rp.helper;
import java.util.Comparator;

public class EstimatedTimeComparator implements Comparator<EstimatedTime>{
	
	@Override
	public int compare(EstimatedTime x, EstimatedTime y){
		
		if(x.getEstimatedTime() < y.getEstimatedTime())
			return 1;
		
		if(x.getEstimatedTime() > y.getEstimatedTime())
			return -1;
		
		if(x.getPlanningHorizon() < y.getPlanningHorizon())
			return 1;
		
		if(x.getPlanningHorizon() > y.getPlanningHorizon())
			return -1;
		
		if(x.getDepth() < y.getDepth())
			return 1;
		
		if(x.getDepth() > y.getDepth())
			return -1;
		
		return 0;
		
		/*
		 
		double dif 	= Math.abs(x.getEstimatedTime() - y.getEstimatedTime());
		double w 	= x.getWindow()-y.getWindow();
		double d 	= x.getDepth()-y.getDepth();
		
		 if(dif<100){
			if(w==0 && d==0)
				return 0;
			else{
				if(w!=0)
					return (x.getWindow()<y.getWindow())?1:-1;
				return (x.getDepth()<y.getDepth())?1:-1;
			}
		}else{
			if (dif==0){
			     return 0; // the first is greater than the second
			}
			return (x.getEstimatedTime() < y.getEstimatedTime())?1:-1;// the first is less than
		}
		 * */
	}
}