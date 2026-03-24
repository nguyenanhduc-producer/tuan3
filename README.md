# 🚀 Dự án Quản lý Công việc (Task Management API) - Tuần 9

Đây là hệ thống Backend cung cấp các API RESTful phục vụ cho việc quản lý Dự án, Nhân sự và Phân công công việc. Dự án được xây dựng bằng Spring Boot 3 và áp dụng các tiêu chuẩn thiết kế kiến trúc hiện đại.

## 🛠️ Công nghệ sử dụng
* **Framework:** Spring Boot 3.4.2
* **Ngôn ngữ:** Java 17
* **Database:** SQL Server
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security & JWT (JSON Web Token)
* **API Documentation:** Swagger UI (OpenAPI 3)
* **Testing:** JUnit 5 & Mockito

## ⚙️ Hướng dẫn Cài đặt & Khởi chạy (Setup & Run)

### 1. Yêu cầu hệ thống
* Đã cài đặt Java JDK 17.
* Đã cài đặt Maven (Nếu dùng qua Terminal).
* Đã cài đặt và cấu hình sẵn SQL Server (Cổng mặc định 1433).

### 2. Cấu hình Database
Mở file `src/main/resources/application-dev.properties` và sửa thông tin kết nối SQL Server của bạn:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TASK_MANAGEMENT;...
spring.datasource.username=SA
spring.datasource.password=Mat_khau_cua_ban