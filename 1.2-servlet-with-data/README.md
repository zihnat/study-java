# SimpleServlet project

Source code for servlet class:
```
src/net/java/servlet/ServletWithData.java
```

File web.xml:
```
WebContent/WEB-INF/web.xml
```

For JDBC we will need
```
mysql-connector-java-XXX-bin.jar
```

DB for sample is mysql with database "servlet" and table "list".
Table may be created with command:
```
 CREATE TABLE `list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `cost` float DEFAULT NULL,
  PRIMARY KEY (`id`)
)
```
And filled with data using:
```
insert into list (name, age, cost) values ("VW Golf I", 30, 300.00);
insert into list (name, age, cost) values ("VW Golf II", 20, 1000.00);
insert into list (name, age, cost) values ("Pobeda", 70, 10000.00);
insert into list (name, age, cost) values ("Ford Focus", 3, 3000.10);
insert into list (name, age, cost) values ("Lada Kalina", 7, 500.50);
insert into list (name, age, cost) values ("Pontiac GTO II", 34, 9999.99);
```


All following commands are running from project folder.

## Compile project

Create compiled java class file in folder ```WebContent/WEB-INF/classes/``` by next command:
```
javac -cp TOMCAT_FOLDER/lib/servlet-api.jar:mysql-connector-java-XXX-bin.jar -d ./WebContent/WEB-INF/classes/ ./src/net/lessons/servlet/ServletWithData.java

```

## Make war file

Extract your jdbc jar file to ./WebContent/WEB-INF/classes/ folder.

Create .war file in folder ```deploy``` by next command:
```
jar cfv deploy/SimpleServletApp.war -C WebContent .
```

## Deploy the web application
Deploy war file to tomcat's ```webapps``` folder:
```
cp deploy/SimpleServletApp.war TOMCAT_FOLDER/webapps/
```
