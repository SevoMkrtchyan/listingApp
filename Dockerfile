FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/listingApp-0.0.1-SNAPSHOT.jar listingApp-0.0.1.jar
ENTRYPOINT ["java","-jar","/listingApp-0.0.1.jar"]
