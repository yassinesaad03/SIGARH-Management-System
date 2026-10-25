
# SIGARH — Human Resources Management System

A full-stack web application designed to simplify human resources management through personnel record management, PDF generation, and automated email notifications.

---

## 📌 Overview

SIGARH (Système Informatique de Gestion des Ressources Humaines) is a web application developed to facilitate the management of personnel information within an organization.

The application allows an administrator to manage personnel records, including personal and professional information and photos. It also generates PDF personnel files and automatically sends email notifications with the corresponding PDF attached after a record is created or updated.

---

## ✨ Key Features

- 🔐 **Administrator Authentication:** Login and logout functionality for the administrator.
- 👥 **Personnel Management:** Add personnel with all required personal and professional information and their photo, view the personnel list and individual records, update and delete records, and generate PDF personnel files.
- 📧 **Automated Email Notifications:** Automatically send an email to personnel after their record is added or updated, with the corresponding PDF file attached.

---

## 💻 Technical Stack

### Frontend
- HTML5
- CSS3
- JavaScript

### Backend
- Java
- Spring Boot

### Database
- PostgreSQL

### Additional Technologies
- REST API
- Maven
- OpenPDF for PDF generation
- JavaMailSender for email notifications

---

## 📁 Project Structure

```text
SIGARH/
├── src/
│   └── main/
│       ├── java/
│       │   └── org/telecom/sigarh/
|       |       ├── config/
│       │       ├── controllers/
│       │       ├── dto/
│       │       ├── entities/
│       │       ├── repositories/
|       |       ├── services/
│       └── resources/
│           ├── static/
│           │   ├── html/
│           │   ├── css/
│           │   ├── js/
│           │   └── images/
│           └── application.properties
├── uploads/
│   └── personnel photos
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java JDK compatible with the project configuration
- PostgreSQL
- Maven
- A modern web browser

---

## ⚙️ Installation

### 1. Clone the repository

```bash
git clone https://github.com/yassinesaad03/SIGARH-Management-System.git)
```

### 2. Configure the database

Create a PostgreSQL database for SIGARH and configure the database URL, username, and password in `src/main/resources/application.properties`.

### 3. Run the application

From the project root directory, execute:

```bash
mvn spring-boot:run
```

### 4. Access the application

Once the Spring Boot application is running, open the login page in your browser:

```text
http://localhost:8081/html/login.html
```

Make sure the port matches your application configuration and that the frontend API URL points to the correct backend address.

---

## 🎯 Project Objectives

- Simplify personnel administration.
- Centralize personal and professional personnel information.
- Automate email notifications and the delivery of personnel files.

---

## 👨‍💻 Author

**YASSINE SAAD**

Developed as a Human Resources Management System project.
