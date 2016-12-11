package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;
//interface to handle a resultator obj, which is used putting resultatorImpls on outQueue
//so the client can poll the outQueue for the resultator
public interface Resultator extends Remote{
	public String getResult() throws RemoteException;
	public void setResult(String result) throws RemoteException;
	public boolean isProcessed() throws RemoteException;
	public void setProcessed() throws RemoteException;
}