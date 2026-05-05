# Car Sales Application

A full-stack web application for posting and managing car listings. The backend is built with Spring Boot, and the frontend is developed in React.

The system allows users to add new listings, filter cars by various criteria such as make, model, price, or fuel type, and view details about each listing. Administrators have additional control over listings, including the ability to activate or deactivate, add, delete, or modify them.

---

## Project Structure

- **automobili_back** — the server-side application responsible for managing listings, processing car data, user authentication and authorization, and communication with the database.
- **automobili_front** — the client-side application that serves as the interface through which users interact with the system directly. Its role is to provide a simple and visually appealing environment where users can browse cars, post or edit their own listings, and administrators can manage all listings.

---

## Technologies

### Backend
- **Java** — primary programming language, chosen for its stability and object-oriented approach
- **Spring Boot** — main framework for rapid application development; enables quick project setup, focus on business logic, and easy integration of RESTful API endpoints
- **Spring Security + JWT (JSON Web Token)** — provides secure authentication and authorization, ensuring that only authorized users can access protected resources
- **MySQL** — relational database for storing data about listings, users, and cars
- **Maven** — dependency management and build tool for the backend

### Frontend
- **React** — for building a dynamic and interactive web interface
- **Axios** — for sending HTTP requests to the Spring Boot API, including attaching JWT tokens in headers for protected resources
- **React Router DOM** — for managing navigation within the application

---

## Features

### Regular Users
- Browse all active car listings
- Filter listings by make, model, price, fuel type, and more
- View detailed information about each listing
- Post new car listings
- Edit or manage their own listings

### Administrators
- All user features, plus:
- Activate or deactivate listings
- Add, edit, or delete any listing

---

## How to Run

### Prerequisites
- Java 17+
- Node.js & npm
- MySQL
- Maven

### Backend

1. **Clone the repository**
   ```
   git clone <repository-url>
   ```

2. **Configure the database** — update `application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/automobili
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the backend**
   ```
   cd automobili_back
   mvn spring-boot:run
   ```

### Frontend

1. **Install dependencies**
   ```
   cd automobili_front
   npm install
   ```

2. **Start the development server**
   ```
   npm start
   ```

The application will be available at `http://localhost:3000`.

---

## Authors

- **Andrijana Opačić**
