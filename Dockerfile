# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.6-jdk-11 as builder

# Copy local code to the container image.
WORKDIR /app

RUN mkdir ./m2
COPY settings.xml ./m2
COPY pom.xml .
COPY src ./src

# Build a release artifact.
#RUN mvn package -DskipTests

RUN mvn clean install -q -DskipTests -s ./m2/settings.xml

RUN mvn package -ntp -q -s ./m2/settings.xml

RUN mvn sonar:sonar  -Dsonar.projectKey=User-Demo  -Dsonar.host.url=http://34.175.50.60:9000  -Dsonar.login=a3b1dfa809fdb8055c89d6be7eed9c7583448c29

FROM adoptopenjdk/openjdk11:alpine-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/demoUser*.jar /demoUser.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/demoUser.jar"]
