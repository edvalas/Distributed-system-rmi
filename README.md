# Distributed Systems RMI project

## Ed Lasauskas
## 4th Year
## College: Galway-Mayo Institute of Technology
## Module: Distributed Systems
## Lecturer: Dr. John Healy

### Introduction
This is a Java RMI project which carries out a string comparing service for Distributed systems module. The string comparison algorithms were provided as a starter for the project, I did not write them. This project provides a web application, which takes input of 2 strings and selecting and algorithm to use for string comparison. Then it fires of a request into a queue and the RMI client takes requests of the queue and executes remote string comparing service using pass by reference between the 2 web application and RMI server.

### Structure
In this repo you have the comparator.war file which is the web application. This file can be deployed on a server like tomcat or IDE like eclipse ee.
The string-service.jar is a jar file which contains code for RMI server, this need to be run before the web application can send it requests.
ie.gmit.sw directory contains the source code for the java part of web application and RMI code.
The HTML page and other files of the web application are contained inside the war file.

### Runing this project
1. Download this repo
2. To run the string service(RMI side), run: java –cp ./string-service.jar ie.gmit.sw.Servant, on command line from whatever directory you stored the string-service.jar jar file. This will start up the RMI server to listen to requests from the web application.
3. You can put the comparator.war file into apache Tomcat server, webapps directory which will deploy and run the web appplication or import the war file into eclipse ee or netbeans and run the project from inside the IDE of your choice.
4. If you use tomcat or another server then once deployed open a web browser and navigate to localhost:8080/comparator
