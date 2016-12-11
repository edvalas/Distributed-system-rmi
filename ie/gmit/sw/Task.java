package ie.gmit.sw;
//Task class to hold the details from the form of the html page
//and to be able to add Tasks on the inQueue
public class Task {
	//instance vars
	private String algo;
	private String s;
	private String t;
	private String taskNumber;
	
	//overloaded constructor
	public Task(String algo, String s, String t, String taskNumber) {
		super();
		this.algo = algo;
		this.s = s;
		this.t = t;
		this.taskNumber = taskNumber;
	}
	
	//getters generated from source menu
	public String getAlgo() {
		return algo;
	}

	public String getS() {
		return s;
	}

	public String getT() {
		return t;
	}
	
	public String getTaskNumber() {
		return taskNumber;
	}
}