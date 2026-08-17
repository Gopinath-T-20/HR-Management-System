USE HRMS_DB;
GO

-- ================= TABLES =================

CREATE TABLE Department (
    dept_id INT IDENTITY(1,1) PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL
);

CREATE TABLE Employee (
    emp_id INT IDENTITY(1,1) PRIMARY KEY,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    designation VARCHAR(50),
    dept_id INT FOREIGN KEY REFERENCES Department(dept_id),
    date_of_join DATE,
    salary DECIMAL(10,2),
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50)
);

CREATE TABLE attendance (
    attendance_id INT IDENTITY(1,1) PRIMARY KEY,
    attendance_date DATE,
    check_in_time TIME,
    status VARCHAR(255),
    emp_id INT FOREIGN KEY REFERENCES Employee(emp_id)
);

CREATE TABLE leave_request (
    leave_id INT IDENTITY(1,1) PRIMARY KEY,
    from_date DATE,
    to_date DATE,
    reason VARCHAR(255),
    status VARCHAR(255),
    emp_id INT FOREIGN KEY REFERENCES Employee(emp_id)
);

-- ================= SAMPLE DATA =================

INSERT INTO Department (dept_name) VALUES ('BPO'), ('IT'), ('HR'), ('Sales');

INSERT INTO Employee (emp_name, email, phone, designation, dept_id, date_of_join, salary, username, password, role)
VALUES ('Admin', 'admin@hrms.com', '9999999999', 'HR Manager', 3, '2026-01-01', 30000.00, 'Admin', 'admin123', 'HR');

INSERT INTO attendance (attendance_date, check_in_time, status, emp_id)
VALUES ('2026-07-28', '20:47:31', 'Present', 1);