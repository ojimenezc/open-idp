FROM openjdk:8-jdk-alpine
COPY ./target/*.jar /usr/app/idp.jar
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java","-jar","idp.jar"]