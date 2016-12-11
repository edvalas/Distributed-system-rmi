package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servant {
	public static void main(String[] args) throws Exception{
		//make string serviceimpl
		StringService service = new StringServiceImpl();
		//create registry
		LocateRegistry.createRegistry(1099);			
		//bing the name to the stringserviceimpl obj
		Naming.rebind("string-service", service);	
		
		System.out.println("Server ready");
	}
}