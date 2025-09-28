# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

#MAINTAINER Onur Can
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="Onur Can"

# Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]