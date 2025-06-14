# 使用 Maven 基础镜像构建
FROM maven:3.8.6-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests  # 先打包生成 jar 文件

# 使用运行时镜像
FROM openjdk:11-jre-slim
WORKDIR /app
# 从 builder 阶段复制 jar 文件
COPY --from=builder /app/target/my-java-server-*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=80"]
