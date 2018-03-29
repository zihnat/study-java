# SimpleServlet project

Source code for servlet class:
```
src/net/java/servlet/SimpleServlet.java
```

Source code for .jsp file:
```
WebContent/index.jsp
```

File web.xml:
```
WebContent/WEB-INF/web.xml
```

All following commands are running from project folder.

## Compile project

Create compiled java class file in folder ```WebContent/WEB-INF/classes/``` by next command:
```
javac -cp TOMCAT_FOLDER/lib/servlet-api.jar -d ./WebContent/WEB-INF/classes/ ./src/net/lessons/servlet/SimpleServlet.java
```

## Make war file

Create .war file in folder ```deploy``` by next command:
```
jar cfv deploy/SimpleServletApp.war -C WebContent .
```

## Deploy the web application
Deploy war file to tomcat's ```webapps``` folder:
```
cp deploy/SimpleServletApp.war TOMCAT_FOLDER/webapps/
```
