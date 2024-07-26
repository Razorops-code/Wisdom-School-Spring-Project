# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim
 
# Set the working directory in the container
WORKDIR /app
    
# Copy the application's jar file to the container
COPY target/ygsschool-0.0.1-SNAPSHOT.jar /app/wisdom.jar
 
# Expose the port the application runs on   
EXPOSE 8080
 
# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/wisdom.jar"]
