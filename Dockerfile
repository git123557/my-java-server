# 使用多阶段构建减小镜像体
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
EXPOSE 80  # 修改为80端口
CMD ["java", "-jar", "my-java-server-0.0.1-SNAPSHOT.jar"]
