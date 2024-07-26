FROM openjdk:8-jdk-alpine 
WORKDIR /usr/app
COPY . /usr/app
RUN chmod +x mvnw && ./mvnw clean install package
COPY /usr/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
