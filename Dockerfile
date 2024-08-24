# Define base image - https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:21-jdk

# Define a work directory
WORKDIR /app

# Copy Adaptable root certificate to allow DB connection
# Comment this line if you want to run this locally
COPY .adaptable /.adaptable

# Copy maven wrapper and pom.xml
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Give permissions to run maven wrapper file
RUN chmod +x mvnw

# Resolve all dependencies
RUN ./mvnw dependency:go-offline

# Run application
CMD ["./mvnw", "spring-boot:run"]