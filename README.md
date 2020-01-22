# Document-Management-System
First projent in VTMC

 
## Getting Started

### Prerequisites

* Java 8+
* Node.js
* Maven
* Tomcat


### Installing

To install this application, run the following commands:

```
git clone https://github.com/JustasMarkauskas/Document-Management-System.git

```

This will get a copy of the project installed locally. To install all of its dependencies and start each app, follow the instructions below.
To run Spring Boot API, cd into the dms-app folder and run:


```
mvn clean install spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
or
mvn clean install org.codehaus.cargo:cargo-maven2-plugin:1.7.7:run -Dcargo.maven.containerId=tomcat9x -Dcargo.servlet.port=8081 -Dcargo.maven.containerUrl=http://repo1.maven.org/maven2/org/apache/tomcat/tomcat/9.0.29/tomcat-9.0.29.zip
```

To run React UI, cd into the dms-ui folder and run:
```
1. yarn install
2. yarn start
```


### Using


* Browse to http://localhost:3000/ to see REACT UI
* Browse to http://localhost:8081/swagger-ui.html#/ to use Swagger if you ran Spring Boot API with first option
* Browse to http://localhost:8081/dms-app/swagger-ui.html#/ to use Swagger if you ran Spring Boot API with second option
* Browse to http://localhost:8081/console/ to connect to database if you ran Spring Boot API with first option
* Browse to http://localhost:8081/dms-app/console/ to connect to database if you ran Spring Boot API with second option
