FROM openjdk:18
EXPOSE 8080:8080

RUN mkdir /app

WORKDIR /app
COPY ./server.jar /app/server.jar

ENTRYPOINT ["java","-jar","/app/server.jar"]