# HR Management System (HRMS)

A full-stack HR Management System with role-based access for Employees and HR admins, built using Spring Boot (backend REST APIs) and AngularJS (dynamic frontend).

## Features

- **Employee Management** — Add, view, update, delete employee records
- **Department Management** — Manage company departments
- **Attendance Tracking** — Track employee attendance
- **Leave Management** — Employees can apply for leave; HR can approve/reject requests
- **Role-Based Access** — Different UI and permissions for Employee vs HR roles
- **Secure Authentication** — Login system with BCrypt password encryption

## Tech Stack

**Backend:** Java, Spring Boot, Spring Data JPA, Hibernate, REST API, SQL Server  
**Frontend:** AngularJS, HTML, CSS, JavaScript  
**Security:** BCrypt password hashing  
**Architecture:** Layered (Controller → Service → Repository), 3-tier client-server

## Modules

| Module | Description |
|---|---|
| Employee | CRUD operations for employee records |
| Department | Manage departments, linked to employees |
| Attendance | Track and view attendance records |
| Leave | Apply, view history, approve/reject leave requests |
| Auth | Secure login with encrypted passwords |

## How to Run

### Backend
1. Open the project in IntelliJ IDEA
2. Configure `application.properties` with your SQL Server credentials
3. Run `HrmsApplication.java`
4. Backend runs on `http://localhost:8080`

### Frontend
1. Open the `hrms-frontend` folder
2. Run using Live Server (VS Code) or any local server
3. Frontend runs on `http://localhost:5500`


## Author

**[ Gopinath T ]**