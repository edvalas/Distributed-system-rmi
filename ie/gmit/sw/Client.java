package ie.gmit.sw;

import java.rmi.Naming;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
// RMI client class which receives the in and out Queues from the web client
// and then creates a task, by polling a task from the inQueue for processing
public class Client implements Runnable{
	//instance vars
	LinkedList<Task> inQueue;
	ConcurrentHashMap<String, Resultator> outQueue;

	//construct client with in and out Queues
	public Client(LinkedList<Task> inQueue, ConcurrentHashMap<String, Resultator> outQueue){
		this.inQueue = inQueue;
		this.outQueue = outQueue;
	}
	
	//method to update the resultator obj in map
	public void update(String taskNum, Resultator r){
		outQueue.put(taskNum, r);
	}
	
	//run method for this class, starts when thread is started in web app
	public void run(){
		try 
		{
			//get a handle on the rmi string comparing service
			StringService service = (StringService) Naming.lookup("rmi://localhost:1099/string-service");
			
			//create a task by taking a task from the inQueue
			Task currentTask = inQueue.poll();
			
			//resultator obj which calls the service and its compare method
			//retrieves information entered by user from the task obj
			//which was retrieved from the inQueue
			Resultator r = service.compare(currentTask.getS(), currentTask.getT(), currentTask.getAlgo());
			
			//do while loop to keep looping, until result of the resultator obj is set by the service.
			do{
				if(r.getResult() != null){
					//update the resultator on the outQueue with new info
					update(currentTask.getTaskNumber(), r);
					System.out.println("client result " + r.getResult());
				}
			}
			while(r.isProcessed() == false);
			
			//update outQueue
			//update(currentTask.getTaskNumber(), r);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}