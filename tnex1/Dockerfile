FROM openjdk:17-jdk-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file jar vào container
COPY target/tnex1-0.0.1-SNAPSHOT.jar app-1.0.0.jar

# Cấu hình lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app-1.0.0.jar"]

