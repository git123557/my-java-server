# 使用官方 Java 基础镜
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 将构建的 jar 文件复制到容器中
COPY target/my-java-server-*.jar app.jar

# 暴露端口 80（微信云托管要求）
EXPOSE 80

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=80"]
