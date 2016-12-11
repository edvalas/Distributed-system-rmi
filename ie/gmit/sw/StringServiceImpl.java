package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class StringServiceImpl implements StringService{
	//list of algos available, populated in constructor
	private List<Node> cor = new LinkedList<Node>();
	//private instance of resultator
	private Resultator r;
	
	//constructor
	public StringServiceImpl() throws RemoteException {
		super();
		//make this object available to communicate with the client
		UnicastRemoteObject.exportObject(this, 7777);
		
		//add the algos to the cor list
		cor.add(new Node(new Levenshtein(), "Levenshtein Distance"));
		cor.add(new Node(new DamerauLevenshtein(), "Damerau-Levenshtein Distance"));
		cor.add(new Node(new HammingDistance(), "Hamming Distance"));
	}
	
	//compare method
	public Resultator compare(String s, String t, String algo) throws RemoteException {
		try {
			//put the thread to sleep to slow down the processing
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//create new ResultatorImpl for each call of compare
		r = new ResultatorImpl();

		//go over the list of algos, pick the matching algo passed into the method
		//calc distance between strings
		for (Node n : cor){ //loops over the algo list
			if (n.getName().equalsIgnoreCase(algo)){
				//calc distance between the strings
				int distance = n.getAlgorithm().distance(s, t);
				//once calculated set the distance to resultator obj
				r.setResult("Distance for " + n.getName() + ": " + distance);
				System.out.println("String service result " + r.getResult());
			}
		}
		//once distance set, set r to be processed -> updates client
		r.setProcessed();
		
		return r;
	}

	//private class Node, for the list of algos
	private class Node{
		private Algorithm algo;
		private String name;
		//construct algo with algo obj and name
		public Node(Algorithm a, String name) {
			super();
			this.algo = a;
			this.name = name;
		}
		//get algo
		public Algorithm getAlgorithm() {
			return this.algo;
		}
		//get name
		public String getName() {
			return this.name;
		}
	}
}