# 构建阶段（使用JDK）
FROM openjdk:8-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 运行阶段（只保留JRE和应用）
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/my-java-server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080  # 暴露8080端口
CMD ["java", "-jar", "my-java-server-0.0.1-SNAPSHOT.jar"]
