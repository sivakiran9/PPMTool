FROM openjdk:latest
COPY target/ppmtool-0.0.1-SNAPSHOT.jar ppmtool-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java" , "-jar" , "ppmtool-0.0.1-SNAPSHOT.jar"]

