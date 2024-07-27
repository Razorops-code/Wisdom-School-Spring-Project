# FROM openjdk:8-jdk-alpine 
# WORKDIR /usr/app
# # Set environment variables for Maven installation
# ENV MAVEN_VERSION=3.8.8
# ENV MAVEN_HOME=/usr/share/maven
# ENV MAVEN_CONFIG="/root/.m2"
# ENV PATH=${MAVEN_HOME}/bin:${PATH}
 
# # Install Maven
# RUN apk update && \
#     apk add --no-cache curl tar bash && \
#     curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -o /tmp/apache-maven.tar.gz && \
#     mkdir -p /usr/share/maven && \
#     tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 && \
#     rm -f /tmp/apache-maven.tar.gz
# COPY . /usr/app
# RUN mvn clean install package
# RUN cd target 
# RUN ls -a
# COPY /usr/app/target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","app.jar"]
# Stage 1: Build
FROM maven:3.8.8-openjdk-8 AS build
WORKDIR /usr/app

# Copy the Maven project files
COPY . .

# Package the application
RUN mvn clean package

# Stage 2: Runtime
FROM openjdk:8-jdk-alpine
WORKDIR /usr/app

# Copy the jar file from the build stage
COPY --from=build /usr/app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
