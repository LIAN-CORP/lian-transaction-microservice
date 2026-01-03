# ============================================
# BUILD STAGE
# ============================================
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copy only the necessary files to leverage Docker cache
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

# ============================================
# RUNTIME STAGE
# ============================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
