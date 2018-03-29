#!/bin/bash
javac -cp /opt/tomcat/lib/servlet-api.jar -d ./WebContent/WEB-INF/classes/ ./src/net/lessons/servlet/SimpleServlet.java
jar cfv deploy/SimpleServletApp.war -C WebContent .
cp -f deploy/SimpleServletApp.war /opt/tomcat/webapps/
