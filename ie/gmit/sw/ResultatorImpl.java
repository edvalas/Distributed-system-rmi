package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//implementation of resultator
public class ResultatorImpl extends UnicastRemoteObject implements Resultator{
	//Marshaling
	private static final long serialVersionUID = 1L;
	private String result;
	//processed initially is false, set to true once distance is calculated
	private boolean processed;

	//constructor which sets up the instance vars to defaults
	public ResultatorImpl() throws RemoteException{
		super();
		result = "";
		processed = false;
	}
	
	//get result, returns result
	public String getResult() throws RemoteException {
		return this.result;
	}
	
	//set result, sets the result
	public void setResult(String result) throws RemoteException {
		this.result = result;
	}

	//check if it is processed
	public boolean isProcessed() throws RemoteException {
		return this.processed;
	}

	//set true to processed
	public void setProcessed() throws RemoteException {
		this.processed = true;
	}
}