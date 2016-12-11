package ie.gmit.sw;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceHandler extends HttpServlet{
	private String remoteHost = null;
	private static long jobNumber = 0;
	//Marshaling
	private static final long serialVersionUID = 1L;
	//inQueue to take requests for string comparing and holding tasks of those requests
	private static LinkedList<Task> inQueue = new LinkedList<Task>();
	//outQueue to store resultators and task numbers
	private static ConcurrentHashMap<String, Resultator> outQueue = new ConcurrentHashMap<String, Resultator>();
	//client obj which will be constructed with both queues
	private Client c;
	//thread to run the run method of the client to compare strings using remote service
	private Thread worker;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		boolean complete = false; // boolean to control the pull request every 10 seconds
		
		//Initialise some request variables with the submitted form info. These are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		if (taskNumber == null){
			//if theres a task, set a new task number, increment jobNumber
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			//Add job to inQueue, make a new task with the info for web page
			inQueue.add(new Task(algorithm, s, t, taskNumber));
			//make resultator obj and add it to the outQueue at the same time
			Resultator r = new ResultatorImpl();
			outQueue.put(taskNumber, r);
			
			//create the client with both Queues
			//each web page will get a new rmi client to compare the strings
			c = new Client(inQueue, outQueue);
			//new thread to start the run method in client obj
			worker = new Thread(c);
			//start the worker
			worker.start();
		}else{
			//Check check outQueue for results
			//get resultator of map by taskNumber
			Resultator r = outQueue.get(taskNumber);
			//if r is not null and processed boolean is set to true, then job is finished
			if(r != null && r.isProcessed() == true){
				//output details
				out.print("Calculated Distance between 2 strings: " + r.getResult());
				//set switch(the boolean var) to stop polling the rmi server
				complete = true;
				//remove the resultator from the outQueue
				outQueue.remove(taskNumber);
			}
			else{
				//out put still in progress, if the resultator obj
				//has not been updated by the compare service
				out.print("Processing...");
			}
		}
		
		//output process finished if result is done processing
		//else keep polling the rmi server with the form for updates
		if(complete == true){
			out.print("</br>Processing complete");
		}
		else
		{
			out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
			out.print("<div id=\"r\"></div>");
		
			out.print("<font color=\"#993333\"><b>");
			out.print("RMI Server is located at " + remoteHost);
			out.print("<br>Algorithm: " + algorithm);		
			out.print("<br>String <i>s</i> : " + s);
			out.print("<br>String <i>t</i> : " + t);
			//form which is sent to rmi server as a pull request to check for updates
			out.print("<form name=\"frmRequestDetails\">");
			out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
			out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
			out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
			out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
			out.print("</form>");								
			out.print("</body>");	
			out.print("</html>");	
		
			out.print("<script>");
			
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
			out.print("</script>");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}