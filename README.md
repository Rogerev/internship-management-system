# Internship Management System

A comprehensive desktop application built with **Java Swing**, **JDBC**, and **MySQL** for managing employees, departments, and salaries in an organization. The system supports both Admin and User roles with distinct functionalities.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Installation & Setup](#installation--setup)
- [Database Schema](#database-schema)
- [Usage Guide](#usage-guide)
- [User Roles](#user-roles)
- [File Descriptions](#file-descriptions)
- [Important Notes](#important-notes)

## Overview

The Internship Management System is a multi-user desktop application designed to streamline the management of organizational data including employees (Persons), departments, and salary information. It features:

- **Role-based access control** with Admin and User dashboards
- **GUI-based interface** built with Java Swing for ease of use
- **CRUD operations** for departments, persons, and salaries
- **User authentication** system with credential verification
- **Database integration** using MySQL and JDBC

## Features

### Core Functionalities

1. **User Authentication**
   - Username and password-based login
   - Role-based dashboard routing (Admin/User)
   - Secure credential validation

2. **Department Management**
   - Create new departments (Admin only)
   - View all departments (Admin & User)
   - Update department information (Admin only)
   - Delete departments (Admin only)
   - Display department code and name

3. **Person Management**
   - Add new persons/employees (Admin only)
   - View all persons (Admin & User)
   - Update person details (Admin only)
   - Delete persons (Admin only)
   - Store information: Name, Email, Department ID

4. **Salary Management**
   - Add salary records for employees (Admin only)
   - Update salary information (Admin only)
   - Delete salary records (Admin only)
   - Calculate net salary (Basic + HRA + Allowance - Deductions)
   - Track salary by person ID

## Architecture

The application follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────┐
│          UI Layer               │
│  (Swing Components/Frames)      │
├─────────────────────────────────┤
│          DAO Layer              │
│  (Data Access Objects)          │
├─────────────────────────────────┤
│       Database Layer            │
│   (JDBC Connections)            │
├─────────────────────────────────┤
│      MySQL Database             │
└─────────────────────────────────┘
```

### Layer Descriptions

- **UI Layer**: Java Swing components handling user interface and interactions
- **DAO Layer**: Data Access Objects providing database operations (CRUD)
- **Database Layer**: JDBC connection management and SQL execution
- **Model Layer**: Plain Java objects representing data entities

## Project Structure

```
jdbc/
├── src/
│   └── com/
│       └── internship/
│           ├── app/                    # ⚠️ LEARNING PURPOSE ONLY (See Notes)
│           │   ├── Main.java           # Console menu entry point
│           │   ├── Login.java          # Console login handler
│           │   ├── Person.java         # Console person operations
│           │   └── Department.java     # Console department operations
│           │
│           ├── db/                     # Database Configuration
│           │   └── DBConnection.java   # JDBC connection manager
│           │
│           ├── dao/                    # Data Access Objects
│           │   ├── UserDAO.java        # User authentication queries
│           │   ├── PersonDAO.java      # Person CRUD operations
│           │   ├── DepartmentDAO.java  # Department CRUD operations
│           │   └── SalaryDAO.java      # Salary CRUD operations
│           │
│           ├── model/                  # Entity Models
│           │   ├── Person.java         # Person entity
│           │   ├── Department.java     # Department entity
│           │   └── Salary.java         # Salary entity
│           │
│           └── ui/                     # Swing GUI Components
│               ├── LoginFrame.java     # Login window
│               ├── AdminDashboard.java # Admin main menu
│               ├── UserDashboard.java  # User main menu
│               ├── DepartmentUI.java   # Department CRUD interface
│               ├── DepartmentViewUI.java
│               ├── PersonUI.java       # Person CRUD interface
│               ├── PersonViewUI.java
│               └── SalaryUI.java       # Salary CRUD interface
│
└── README.md                           # This file
```

## Technology Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 8+ | Programming language |
| Swing | Built-in | GUI Framework |
| JDBC | Built-in | Database connectivity |
| MySQL | 5.7+ | Relational Database |
| MySQL Connector/J | 8.0+ | MySQL JDBC driver |

## Installation & Setup

### Prerequisites

1. **Java Development Kit (JDK)** - Version 8 or higher
2. **MySQL Server** - Version 5.7 or higher
3. **MySQL Connector/J** - MySQL JDBC driver

### Step 1: Database Setup

Create the MySQL database and tables:

```sql
-- Create database
CREATE DATABASE internship_db;
USE internship_db;

-- Create users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

-- Create departments table
CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_code VARCHAR(20) UNIQUE NOT NULL,
    dept_name VARCHAR(100) NOT NULL
);

-- Create persons table
CREATE TABLE persons (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    dept_id INT NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

-- Create salary table
CREATE TABLE salary (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    person_id INT UNIQUE NOT NULL,
    basic_salary DOUBLE NOT NULL,
    hra DOUBLE,
    allowance DOUBLE,
    deductions DOUBLE,
    net_salary DOUBLE,
    FOREIGN KEY (person_id) REFERENCES persons(person_id)
);

-- Insert sample users
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('user1', 'user123', 'USER');
```

### Step 2: Configure Database Connection

Edit `DBConnection.java` with your MySQL credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/internship_db?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### Step 3: Add MySQL JDBC Driver

Add the MySQL Connector JAR file to your project classpath.

### Step 4: Run the Application

**Option A: GUI Mode**
```java
Run: com.internship.ui.LoginFrame
```

**Option B: Console Mode**
```java
Run: com.internship.app.Main
```

## Database Schema

### users Table
| Column | Data Type | Constraints |
|---|---|---|
| user_id | INT | PRIMARY KEY, AUTO_INCREMENT |
| username | VARCHAR(50) | UNIQUE, NOT NULL |
| password | VARCHAR(50) | NOT NULL |
| role | ENUM('ADMIN','USER') | NOT NULL |

### departments Table
| Column | Data Type | Constraints |
|---|---|---|
| dept_id | INT | PRIMARY KEY, AUTO_INCREMENT |
| dept_code | VARCHAR(20) | UNIQUE, NOT NULL |
| dept_name | VARCHAR(100) | NOT NULL |

### persons Table
| Column | Data Type | Constraints |
|---|---|---|
| person_id | INT | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | NOT NULL |
| dept_id | INT | FOREIGN KEY (departments.dept_id) |

### salary Table
| Column | Data Type | Constraints |
|---|---|---|
| salary_id | INT | PRIMARY KEY, AUTO_INCREMENT |
| person_id | INT | UNIQUE, FOREIGN KEY (persons.person_id) |
| basic_salary | DOUBLE | NOT NULL |
| hra | DOUBLE | DEFAULT 0 |
| allowance | DOUBLE | DEFAULT 0 |
| deductions | DOUBLE | DEFAULT 0 |
| net_salary | DOUBLE | NOT NULL |

## Usage Guide

### Login

1. Launch the application via `LoginFrame.java`
2. Enter credentials (Username: `admin`, Password: `admin123` for admin account)
3. System validates credentials and routes to appropriate dashboard

### Admin Dashboard

After login with admin credentials, you access:

1. **Manage Departments**
   - Add: Create new department with code and name
   - Update: Modify existing department details
   - Delete: Remove departments
   - View: Display all departments in table

2. **Manage Persons**
   - Add: Create new employee record with name, email, and department
   - Update: Modify employee information
   - Delete: Remove employee records
   - View: Display all employees in table

3. **Manage Salary**
   - Add: Create salary record with basic, HRA, allowance, deductions
   - Update: Modify salary components
   - Delete: Remove salary records
   - View: Display salary information for all employees

### User Dashboard

After login with regular user credentials, you access:

1. **View Departments** - Read-only display of all departments
2. **View Persons** - Read-only display of all employees
3. **Logout** - Exit the application

## User Roles

### Admin Role
- Full CRUD (Create, Read, Update, Delete) access to all data
- Can manage departments, employees, and salaries
- Can create and modify system data
- Default login: `admin` / `admin123`

### User Role
- Read-only access to departments and employees
- Cannot modify any data
- Can only view information
- Default login: `user1` / `user123`

## File Descriptions

### UI Layer (`ui/` package)

| Class | Purpose |
|---|---|
| **LoginFrame.java** | Authentication window; validates credentials and routes to appropriate dashboard |
| **AdminDashboard.java** | Main menu for admin users with CRUD management options |
| **UserDashboard.java** | Main menu for regular users with read-only view options |
| **DepartmentUI.java** | CRUD interface for department management (Admin only) |
| **DepartmentViewUI.java** | Read-only view of departments (User only) |
| **PersonUI.java** | CRUD interface for employee management (Admin only) |
| **PersonViewUI.java** | Read-only view of employees (User only) |
| **SalaryUI.java** | CRUD interface for salary management (Admin only) |

### DAO Layer (`dao/` package)

| Class | Purpose |
|---|---|
| **UserDAO.java** | Authenticates users and returns role from database |
| **PersonDAO.java** | CRUD operations for person/employee records |
| **DepartmentDAO.java** | CRUD operations for department records |
| **SalaryDAO.java** | CRUD operations for salary records |

### Database & Model Layer

| Class | Purpose |
|---|---|
| **DBConnection.java** | Manages JDBC connections to MySQL database |
| **Person.java** (model) | Entity class with properties: id, name, email, deptId |
| **Department.java** (model) | Entity class with properties: id, code, name |
| **Salary.java** (model) | Entity class with salary components: basic, hra, allowance, deductions, net |

## Key Implementation Details

### Connection Management
- JDBC connections managed through `DBConnection` singleton
- Try-with-resources ensures proper resource cleanup
- Null checks prevent NullPointerException

### Data Validation
- Email format validation using regex
- Department code alphanumeric validation
- Department name alphabetic validation
- Salary amount non-negative validation

### GUI Features
- JFrame-based modular window design
- JTable with dynamic data loading
- JComboBox for department selection
- JOptionPane for user feedback
- BorderLayout for responsive UI

## Important Notes

### ⚠️ About the `app/` Package

The `app/` package (containing `Main.java`, `Login.java`, `Person.java`, `Department.java`) was created **for learning purposes only** and has **NO connection to the actual project**.

- **Status**: Deprecated/Legacy
- **Purpose**: Console-based learning implementation  
- **Recommendation**: Ignore this package and use the `ui/` package for the actual application
- **Usage**: The GUI-based application (`ui/LoginFrame.java`) is the primary and recommended entry point

### Security Considerations

1. **Credentials Management**: Database credentials are currently hardcoded in `DBConnection.java`. For production:
   - Use environment variables
   - Use configuration files (not in version control)
   - Implement encrypted credential storage

2. **Password Storage**: Passwords are stored in plain text. For production:
   - Use bcrypt or SHA-256 hashing
   - Never store plaintext passwords

3. **SQL Injection**: All DAO classes use `PreparedStatement` to prevent SQL injection attacks

### Code Quality

- **Resource Management**: All database operations use try-with-resources for automatic closure
- **Null Checks**: Connection validity is checked before operations
- **Error Handling**: Comprehensive exception handling with error messages
- **Validation**: Input validation before database operations

## Future Enhancements

1. Add data export functionality (PDF/Excel/CSV)
2. Implement password encryption (bcrypt/SHA-256)
3. Add audit logging for data modifications
4. Implement connection pooling (HikariCP)
5. Add email notifications for salary updates
6. Implement advanced search and filter functionality
7. Add department-wise salary reports
8. Implement data backup and restore
9. Add print functionality
10. Implement pagination for large datasets
