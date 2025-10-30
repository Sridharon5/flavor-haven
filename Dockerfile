# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy pom.xml and source
COPY . .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/flavor-haven-0.0.1-SNAPSHOT.jar"]
