# 基础镜像（使用OpenJDK 8）
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 复制Maven构建产物（确保先复制pom.xml以利用缓存）
COPY pom.xml .
COPY src ./src

# 构建应用
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# 暴露端口（与Spring Boot应用配置的端口一致）
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "target/my-java-server-0.0.1-SNAPSHOT.jar"]
