# =========================
# 1. Stage build: Maven + Java 21
# =========================
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

# Copy file pom.xml và tải dependency trước (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy toàn bộ source code và build project
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# 2. Stage runtime: JDK 21 nhẹ
# =========================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy jar từ stage build, tên jar đã đặt là english-smart-note.jar
COPY --from=build /app/target/english-smart-note.jar app.jar

# Expose port Spring Boot
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java","-jar","app.jar"]