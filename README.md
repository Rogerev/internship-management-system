# Internship Management System

A Java-based desktop application for managing employees, departments, and salaries with JDBC database integration and Swing GUI.

## 📋 Project Overview

This is an **Internship Management System** built with Java, leveraging:
- **JDBC** for database connectivity
- **Swing** for graphical user interface
- **MySQL** as the database backend
- **Role-based access control** (Admin and User roles)

The application allows administrators to manage departments, employees (persons), and salary information, while regular users can view departments and employee details.

---

## 🏗️ Project Architecture

### Package Structure

```
com.internship
├── app/              # Entry points and business logic
├── db/               # Database connection management
├── dao/              # Data Access Objects (DAO Pattern)
├── model/            # Entity classes
└── ui/               # Swing-based GUI components
```

---

## 📦 Components Overview

### 1. **Database Package** (`com.internship.db`)

#### `DBConnection.java`
- **Purpose**: Centralized database connection management
- **Database**: MySQL (`internship_db`)
- **Credentials**: 
  - Host: `localhost:3306`
  - User: `root`
  - Password: `tanmay-riya`
- **Features**:
  - Uses `com.mysql.cj.jdbc.Driver`
  - Returns `Connection` object for database operations
  - SSL disabled, UTC timezone configured

```java
// Usage
Connection con = DBConnection.getConnection();
```

---

### 2. **Model Package** (`com.internship.model`)

#### `Person.java`
- **Attributes**:
  - `id` (int): Person ID
  - `name` (String): Person's name
  - `email` (String): Person's email
  - `deptId` (int): Associated department ID
- **Methods**: Getters, setters, toString()

#### `Department.java`
- **Attributes**:
  - `id` (int): Department ID
  - `code` (String): Department code
  - `name` (String): Department name
- **Methods**: Getters, setters, toString() (used for JComboBox display)

#### `Salary.java`
- **Attributes**:
  - `salaryId` (int): Unique salary record ID
  - `personId` (int): Associated person ID
  - `basic` (double): Basic salary
  - `hra` (double): House Rent Allowance
  - `allowance` (double): Other allowances
  - `deductions` (double): Total deductions
  - `net` (double): Net salary (basic + hra + allowance - deductions)
- **Methods**: Getters and setters for all attributes

---

### 3. **DAO Package** (`com.internship.dao`)

#### `DepartmentDAO.java`
**Provides CRUD operations for departments:**

| Method | Purpose |
|--------|---------|
| `addDepartment(code, name)` | Insert new department |
| `updateDepartment(id, code, name)` | Update existing department |
| `deleteDepartment(id)` | Delete department by ID |
| `getAllDepartments()` | Retrieve all departments |

**SQL Used:**
```sql
INSERT INTO departments (dept_code, dept_name) VALUES (?, ?)
UPDATE departments SET dept_code=?, dept_name=? WHERE dept_id=?
DELETE FROM departments WHERE dept_id=?
SELECT * FROM departments
```

#### `PersonDAO.java`
**Provides CRUD operations for persons:**

| Method | Purpose |
|--------|---------|
| `addPerson(name, email, deptId)` | Insert new person |
| `updatePerson(id, name, email, deptId)` | Update person details |
| `deletePerson(id)` | Delete person by ID |
| `getAllPersons()` | Retrieve all persons |

**SQL Used:**
```sql
INSERT INTO persons (name, email, dept_id) VALUES (?, ?, ?)
UPDATE persons SET name=?, email=?, dept_id=? WHERE person_id=?
DELETE FROM persons WHERE person_id=?
SELECT * FROM persons
```

#### `SalaryDAO.java`
**Provides CRUD operations for salary records:**

| Method | Purpose |
|--------|---------|
| `addSalary(salary)` | Insert new salary (one per person) |
| `updateSalary(salary)` | Update salary details |
| `getAllSalary()` | Retrieve all salary records |

**Key Features:**
- Uses `SQLIntegrityConstraintViolationException` to detect duplicate salary entries
- Calculates net salary on-the-fly
- Supports bulk salary updates

#### `UserDAO.java`
**Authentication management:**

| Method | Purpose |
|--------|---------|
| `authenticate(username, password)` | Validates user and returns role |

**SQL Used:**
```sql
SELECT role FROM users WHERE username=? AND password=?
```

Returns: User role (ADMIN or USER) or null if invalid

---

### 4. **App Package** (`com.internship.app`)

#### `Main.java`
**Console-based entry point (legacy CLI interface)**
- Requires user authentication first
- Provides menu-driven interface:
  1. Add Department
  2. View Departments
  3. Add Person
  4. View Person
  5. Exit

#### `Login.java`
**Console-based login authentication:**
- Prompts for username and password
- Queries database for user validation
- Returns boolean (true/false)

#### `Person.java` and `Department.java`
**Business logic handlers for console operations:**
- Handle user input from Scanner
- Call database operations
- Display results to console

---

### 5. **UI Package** (`com.internship.ui`)

#### `LoginFrame.java`
**Main login window (Swing GUI entry point)**
- **Components**:
  - Username text field
  - Password field
  - Login button
- **Workflow**:
  1. User enters credentials
  2. Validates input (not empty)
  3. Calls `UserDAO.authenticate()`
  4. Routes to appropriate dashboard:
     - **ADMIN** → `AdminDashboard`
     - **USER** → `UserDashboard`
     - **Invalid** → Error message

#### `AdminDashboard.java`
**Admin home screen (after successful admin login)**
- **Features**:
  - Manage Departments → Opens `DepartmentUI`
  - Manage Persons → Opens `PersonUI`
  - Manage Salary → Opens `SalaryUI`
  - Logout → Returns to `LoginFrame`

#### `UserDashboard.java`
**User home screen (after successful user login)**
- **Features**:
  - View Departments → Opens `DepartmentViewUI` (read-only)
  - View Persons → Opens `PersonViewUI` (read-only)
  - Logout → Returns to `LoginFrame`

#### `DepartmentUI.java`
**Admin panel for department management**
- **UI Components**:
  - Form fields: ID (read-only), Code, Name
  - Table: Shows all departments
  - Buttons: Add, Update, Delete
- **Features**:
  - Input validation:
    - Code: Alphanumeric only
    - Name: Letters and spaces only
  - Row selection auto-fills form fields
  - Refresh table after operations
- **Workflow**: Form → Database → Table Update

#### `DepartmentViewUI.java`
**User panel for viewing departments (read-only)**
- Displays departments in a table
- No edit/delete functionality
- Auto-loads data on window open

#### `PersonUI.java`
**Admin panel for person management**
- **UI Components**:
  - Form fields: ID (read-only), Name, Email, Department (ComboBox)
  - Table: Shows all persons
  - Buttons: Add, Update, Delete
- **Features**:
  - Input validation:
    - Name: Letters and spaces only
    - Email: Standard email format regex
    - Department: Dropdown selection required
  - Dynamic department dropdown from database
  - Row selection auto-fills form and selects corresponding department
  - Net salary calculation in SalaryUI
- **Workflow**: Form → Database → Table Update

#### `PersonViewUI.java`
**User panel for viewing persons (read-only)**
- Displays persons in a table with columns: ID, Name, Email, Dept ID
- No edit/delete functionality
- Auto-loads data on window open

#### `SalaryUI.java`
**Admin panel for salary management (Advanced UI)**
- **UI Components**:
  - Person ComboBox (dropdown)
  - Input fields: Basic, HRA, Allowance, Deductions
  - Split pane: Form above, table below
  - Table: Shows salary records
  - Buttons: Add Salary, Update Salary
- **Advanced Features**:
  - Auto-calculates net salary: `net = basic + hra + allowance - deductions`
  - One salary per person constraint (duplicate check)
  - Salary filtering by selected person
  - Dynamic person list from database
  - Table row selection auto-fills form
  - Add button disabled when salary exists for person (enable on update)
  - Input validation:
    - All numeric fields required
    - No negative values allowed
    - Net salary cannot be negative
- **Workflow**: 
  1. Select person from dropdown
  2. Enter salary components
  3. Click Add (first time) or Update (subsequent times)
  4. Net salary calculated automatically
  5. Table updates and filters by person

---

## 🔄 Application Flow

### Login Process
```
START
  ↓
[LoginFrame] - User enters username & password
  ↓
[UserDAO.authenticate()] - Query database for user & role
  ↓
  ├→ ADMIN role → [AdminDashboard]
  ├→ USER role → [UserDashboard]
  └→ Invalid → Error message → Loop back to login
```

### Admin Workflow
```
[AdminDashboard]
  ├→ Manage Departments → [DepartmentUI]
  │   ├→ Add/Update/Delete departments
  │   └→ View in table
  ├→ Manage Persons → [PersonUI]
  │   ├→ Add/Update/Delete persons
  │   └→ Associate with departments
  ├→ Manage Salary → [SalaryUI]
  │   ├→ Add/Update salary records
  │   └→ Calculate net salary
  └→ Logout → [LoginFrame]
```

### User Workflow
```
[UserDashboard]
  ├→ View Departments → [DepartmentViewUI] - Read-only table
  ├→ View Persons → [PersonViewUI] - Read-only table
  └→ Logout → [LoginFrame]
```

---

## 🗄️ Database Schema

### Tables Required

#### `users`
```sql
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

#### `departments`
```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_code VARCHAR(20) UNIQUE NOT NULL,
    dept_name VARCHAR(100) NOT NULL
);
```

#### `persons`
```sql
CREATE TABLE persons (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    dept_id INT NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

#### `salary`
```sql
CREATE TABLE salary (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    person_id INT UNIQUE NOT NULL,
    basic_salary DOUBLE NOT NULL,
    hra DOUBLE NOT NULL,
    allowance DOUBLE NOT NULL,
    deductions DOUBLE NOT NULL,
    net_salary DOUBLE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons(person_id)
);
```

---

## 🔐 Security Features

1. **Role-Based Access Control**:
   - ADMIN: Full access to manage all entities
   - USER: Read-only access to view data

2. **Input Validation**:
   - Format validation (email, alphanumeric, etc.)
   - Empty field checks
   - Numeric range validation

3. **Database Security**:
   - Prepared statements to prevent SQL injection
   - Try-catch exception handling
   - Connection pooling via centralized DBConnection

---

## 🚀 How to Run

### Prerequisites
- Java 11+
- MySQL Server running
- MySQL JDBC Driver (`mysql-connector-java`)

### Setup Steps

1. **Create Database**:
   ```sql
   CREATE DATABASE internship_db;
   USE internship_db;
   -- Create tables using schema above
   ```

2. **Configure Database Connection**:
   - Update credentials in `DBConnection.java` if needed:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/internship_db";
     private static final String USER = "root";
     private static final String PASSWORD = "tanmay-riya";
     ```

3. **Compile & Run**:
   ```bash
   javac -d bin src/com/internship/ui/LoginFrame.java
   java -cp bin com.internship.ui.LoginFrame
   ```

4. **Default Credentials** (Create in database):
   - Admin: `username: admin, password: admin123, role: ADMIN`
   - User: `username: user, password: user123, role: USER`

---

## 📊 Class Diagram

```
Model Package:
  ├─ Person (id, name, email, deptId)
  ├─ Department (id, code, name)
  └─ Salary (salaryId, personId, basic, hra, allowance, deductions, net)

DAO Package:
  ├─ PersonDAO (CRUD operations)
  ├─ DepartmentDAO (CRUD operations)
  ├─ SalaryDAO (CRUD operations)
  └─ UserDAO (Authentication)

UI Package:
  ├─ LoginFrame (Main entry)
  ├─ AdminDashboard (Admin hub)
  ├─ UserDashboard (User hub)
  ├─ DepartmentUI (Admin management)
  ├─ PersonUI (Admin management)
  ├─ SalaryUI (Admin management)
  ├─ DepartmentViewUI (User read-only)
  └─ PersonViewUI (User read-only)

DB Package:
  └─ DBConnection (Connection factory)

App Package:
  ├─ Main (CLI entry point - legacy)
  ├─ Login (CLI authentication - legacy)
  ├─ Person (CLI business logic - legacy)
  └─ Department (CLI business logic - legacy)
```

---

## ✨ Key Features

✅ **User Authentication** - Role-based login system  
✅ **Department Management** - Add, update, delete departments  
✅ **Employee Management** - Manage employees and their departments  
✅ **Salary Management** - Track and calculate employee salaries  
✅ **Read-Only Access** - Users can view but not modify data  
✅ **Input Validation** - Comprehensive client-side validation  
✅ **Database Integration** - Full JDBC integration with MySQL  
✅ **Swing GUI** - Professional desktop application interface  

---

## 🛠️ Technologies Used

| Technology | Purpose |
|-----------|---------|
| Java | Programming Language |
| Swing | GUI Framework |
| JDBC | Database Connectivity |
| MySQL | Relational Database |
| DAO Pattern | Data Access Layer |

---

## 📝 Design Patterns Used

1. **DAO Pattern**: Separates data access logic from business logic
2. **MVC Pattern**: Model (entity classes), View (Swing UI), Control (DAO/Business logic)
3. **Singleton Pattern**: `DBConnection.getConnection()` for centralized database access

---

## 🔧 Configuration

**Database Connection** (`DBConnection.java`):
```java
private static final String URL = 
    "jdbc:mysql://localhost:3306/internship_db?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "tanmay-riya";
```

Modify these constants if your MySQL setup differs.

---

## 📌 Notes

- The `app` package contains legacy console-based CLI code (Main, Login)
- The `ui` package contains the modern Swing-based GUI (recommended)
- Salary can only be added once per person; subsequent operations are updates
- All monetary values use `double` data type for calculations
- Email validation uses regex pattern: `^[A-Za-z0-9+_.-]+@(.+)$`

---

## 👨‍💼 Author
Created as an internship project demonstrating JDBC and Swing desktop application development.

---

## 📄 License
This project is open for educational purposes.

