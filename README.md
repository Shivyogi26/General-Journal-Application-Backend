# 📝 General Journal Application (Spring Boot + PostgreSQL)

A backend application built using **Spring Boot** and **PostgreSQL** that provides secure REST APIs for managing journal entries.

This project was developed as part of a hands-on learning journey to understand how **production-ready backend systems are structured and implemented**.

---

# 🚀 Features

* 🔐 Secure REST APIs
* 🪪 JWT Authentication & Authorization
* 🗄️ PostgreSQL Database Integration
* 🧱 Clean Layered Architecture
* ⚠️ Global Exception Handling
* 📦 DTO Based API Responses
* ✅ Validation using `@Valid`
* 📑 Pagination & Sorting Support
* 📜 Logging Support (SLF4J / Logback)

---

# 🏗️ Architecture

The project follows a **Layered Architecture Pattern** to ensure separation of concerns and maintainability.

---

## 📌 Layers Explanation

### 1️⃣ Controller Layer

* Handles incoming HTTP requests
* Maps endpoints using `@RestController`
* Returns `ResponseEntity`

### 2️⃣ Service Layer

* Contains business logic
* Processes and validates data
* Acts as a bridge between controller and repository

### 3️⃣ Repository Layer

* Handles database operations
* Uses Spring Data JPA (`JpaRepository`)

### 4️⃣ Entity Layer

* Represents database tables
* Annotated with `@Entity`

### 5️⃣ DTO Layer

* Used to transfer data between layers
* Prevents exposing internal entities

---

# 📁 Project Structure

```
journal-app/
│
├── controller/
│   └── JournalController.java
│
├── service/
│   ├── JournalService.java
│   └── impl/
│       └── JournalServiceImpl.java
│
├── repository/
│   └── JournalRepository.java
│
├── entity/
│   └── JournalEntry.java
│
├── dto/
│   ├── JournalRequestDTO.java
│   └── JournalResponseDTO.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── security/
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
│
├── config/
│   └── AppConfig.java
│
└── JournalApplication.java
```

---

# 🔐 Authentication & Authorization

This application uses **JWT (JSON Web Token)** for securing APIs.

### 🔄 Flow:

1. User logs in with credentials
2. Server validates user
3. JWT token is generated
4. Client sends token in headers:

```
Authorization: Bearer <token>
```

5. Filter validates token for each request

---

# 📡 API Endpoints

## 🔑 Authentication APIs

### ➤ Register User

* **POST** `/api/auth/register`

### ➤ Login User

* **POST** `/api/auth/login`

---

## 📓 Journal APIs

### ➤ Create Entry

* **POST** `/api/journals`

### ➤ Get All Entries (Paginated)

* **GET** `/api/journals?page=0&size=10`

### ➤ Get Entry by ID

* **GET** `/api/journals/{id}`

### ➤ Update Entry

* **PUT** `/api/journals/{id}`

### ➤ Delete Entry

* **DELETE** `/api/journals/{id}`

---

# 🗄️ Database Schema

## 📘 JournalEntry Table

| Column    | Type      | Description              |
| --------- | --------- | ------------------------ |
| id        | Long      | Primary Key              |
| title     | String    | Entry Title              |
| content   | String    | Entry Content            |
| createdAt | Timestamp | Created Time             |
| updatedAt | Timestamp | Last Updated Time        |
| user_id   | Long      | Foreign Key (User Table) |

---

# ⚙️ Configuration

## 📄 `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/journal_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Config
jwt.secret=your_secret_key
```

---

# ▶️ Running the Application

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/journal-app.git
```

### 2️⃣ Navigate to Project

```bash
cd journal-app
```

### 3️⃣ Build Project

```bash
mvn clean install
```

### 4️⃣ Run Application

```bash
mvn spring-boot:run
```

---

# 🧪 Testing

You can test APIs using:

* Postman
* Swagger (if enabled)

---

# 📊 Logging

* Uses **SLF4J + Logback**
* Logs important events like:

  * API calls
  * Errors
  * Authentication attempts

---

# 🔮 Future Enhancements

* 🔍 Search functionality
* 📎 File/Image attachments
* 🏷️ Tags & Categories
* 📊 Analytics Dashboard
* ☁️ Docker Deployment
* 🌐 Cloud Deployment (AWS / GCP)

---

# 🤝 Contribution

1. Fork the repository
2. Create a new branch (`feature/your-feature`)
3. Commit changes
4. Push to branch
5. Create Pull Request

---

# 📄 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

Developed as part of backend learning using **Spring Boot** and **PostgreSQL**.

---

# ⭐ Support

If you like this project, give it a ⭐ on GitHub!

