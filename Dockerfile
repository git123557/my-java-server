# 使用包含JDK的基础镜像（关键修改点）
FROM openjdk:8-slim

# 设置工作目录
WORKDIR /app

# 复制Maven构建产物
COPY pom.xml .
COPY src ./src

# 构建应用（使用镜像自带的Maven）
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# 暴露端口（与Spring Boot应用配置的端口一致）
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "target/my-java-server-0.0.1-SNAPSHOT.jar"]
