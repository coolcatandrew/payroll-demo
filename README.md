# Payroll Software Development Challenge
This demo payroll software is meant to take in a csv with the format shown in sample.csv and convert it to a payroll report.
The software currently only accepts csv with the format :
                                                        
    1) A header, denoting the columns in the sheet (date, hours worked, employee id, job group)
    2) 0 or more data rows
    3) A footer row where the first cell contains the string report id, and the second cell contains a unique identifier for this report.

While developing this payroll software the things I am particularly proud of in my implementation is the level of abstraction
as well as how the front end was implemented. The CSV is converted to the model Payroll which is abstracted into PayStub before being converted
into PayStubViewModel to be used by the front end. This design allows for future expansion, accepting other types of CSV files (such as an employee details CSV)
which can have its data abstracted into PayStubs for the front end. The way PayStub was implemented with its data eventually being converted to strings for
PayStubViewModel allowed details of JobGroup and PayPeriod to be changed as necessary without affecting the front end.

The Frontend was developed using Backbone.js. Although now out of date, Backbone allowed the front end to be written in a modular way using templates;
ensuring new modules or features can be seamlessly added. The Models and Collections used to track the data emit events when changed, allowing for more dynamic
changes to the view. These flexible features make it easy to add new features such as a sidebar which can filter the report based on report ID, employee ID, Dates, or any
other number of data available.

## Deploy Instructions

###Ensure Java is installed on the system.

In a terminal run `java -version` to check version of java

If java not installed enter the following commands in a terminal.

`sudo apt-get update`

`sudo apt-get install default-jdk`

###Download and install Tomcat 9

To download and install tomcat enter the following commands in a terminal.

`wget http://www-eu.apache.org/dist/tomcat/tomcat-9/v9.0.27/bin/apache-tomcat-9.0.27.tar.gz -P /tmp`

`sudo tar xf /tmp/apache-tomcat-9*.tar.gz`

`sudo mv apache-tomcat-9.0.27/ /usr/local/apache-tomcat9`

###Set up deployment and start tomcat

Move the payroll war file to web apps folder:

`sudo mv payroll.war /usr/local/apache-tomcat9/webapps/`

Start the Tomcat server:

`cd /usr/local/apache-tomcat9/bin/`

`sudo ./startup.sh`

###Enjoy the application

Make sure the web browser is working by going to <http://localhost:8080>. You should be able to see the tomcat home page.
You will know it is not working if you see a 404 error in which case you may need to unblock port 8080 or modify the
the /usr/local/apache-tomcat9/conf/server.xml.

If working, go to <http://localhost:8080/payroll> will give you access to the web application.

Once in the web application, select a csv and press the upload button to see a summarized version of the pay report.
