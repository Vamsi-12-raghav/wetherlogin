# Weather Login App

A simple Spring Boot web application with basic authentication flows:
- Register
- Login
- Forgot password

## Tech Stack
- Java 17
- Spring Boot
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Run Locally

### Windows (PowerShell)
```powershell
./mvnw.cmd spring-boot:run
```

### Using built JAR
```powershell
./mvnw.cmd clean package
java -jar target/weather-0.0.1-SNAPSHOT.jar
```

## App URLs
- Main: http://localhost:8080
- Login: http://localhost:8080/login.html
- Home: http://localhost:8080/home.html
- Forgot Password: http://localhost:8080/forgot-password.html

## Project Structure
- `src/main/java/com/example/weather` - Spring Boot application code
- `src/main/resources/static` - frontend static HTML pages
- `src/test/java/com/example/weather` - tests