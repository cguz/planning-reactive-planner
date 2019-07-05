
package pelea.marp.common.r;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import pelea.planning_task.tools.ExecuteScript_With_Thread;

public class RModel {
	
	private String 	ip="127.0.0.1";
	private int 	port=6311;
	private RConnection connection=null;
 
    public RModel(String ip, int p) {
		this.ip = ip;
		port = p;
		connection();
	}
    
    public RModel(String ip, int p, String start_server) {
		this.ip = ip;
		port = p;
		startServer(start_server);
		connection();
	}

	private void connection() {
		try {
			connection = new RConnection(ip,port);
		} catch (RserveException e) {e.printStackTrace();connection=null;}
	}

	public static void main(String[] args) {
		RModel r = new RModel("127.0.0.1",6311);
		r.eval("library(randomForest)");
		r.eval("load('/home/anubis/Trabajos/java/doctorado/PELEA/ReactivePlanner/evaluation/models/model-un-random-forest-cPrint-regressed-all-filtering-pre-in-window-filtered.rda')");
        System.out.println(r.predict("predict(model-un-random-forest-cPrint-regressed-all-filtering-pre-in-window-filtered, list(depth=6, Rvar=13, DRvar=24, FluentsRoot=11, Producers=25, ProducersU=20))"));	
    }

	public void eval(String command) {
		try {
			if(connection!=null)
				connection.eval(command);
		} catch (RserveException e) {e.printStackTrace();connection=null;}
	}

	public double predict(String command) {
		 try {
	            REXP x= connection.eval(command);
	            return x.asDouble();
	     }
	     catch(Exception e) {e.printStackTrace();}
		 return -1;
	}

	public void startServer(String start_server) {
		Thread thread = new ExecuteScript_With_Thread(start_server); 
		thread.start(); 
		
		long startTime = System.currentTimeMillis();
		while(thread.isAlive()){
			long afterGrounding = System.currentTimeMillis();
			
			if (((afterGrounding - startTime)/1000.00)>=180)
				thread.interrupt();
		}
		
		thread = null;
	}
}