package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

//interface for the service to compare distance between strings
//this interface exposes the RMI compare method to the client
//so the client can pass references to data(resultator obj) on which to calc the distance
public interface StringService extends Remote{
	public Resultator compare(String s, String t, String algo) throws RemoteException;
}