package pelea.marp.service.rp.reactive.trie.tools;

import pelea.planning_task.tools.ExecuteScript_With_Thread;

public abstract class Tools {
	
	/**
	 * execute script con un proceso, para evitar los tiempos largos en la ejecución del planner
	 * @param temp
	 */
	public void executeScript(String temp) {
		Thread thread = new ExecuteScript_With_Thread(temp); 
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
