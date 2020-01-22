#!/bin/sh
mvn clean install -DskipTests spring-boot:run -Dspring-boot.run.arguments=--server.port=8081