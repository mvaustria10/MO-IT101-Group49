-------------------------------------------------------------------------------
MS2 MotorPH Payroll System

Group 49
Mark Vemher Austria


📌 Overview

MS2Project is a Java console-based payroll system that processes employee salaries from June to December.

It reads data from CSV files, calculates total pay, and applies standard deductions such as SSS, PhilHealth, Pag-IBIG, and Tax.

You can access the source code by navigating to:
src → main → java → com → mycompany → ms2project

-------------------------------------------------------------------------------

📂 Project Files

📊 Employee.csv – Employee information and salary details

📊 Attendance.csv – Daily attendance records

▶️ How to Run

➤ Open the project in NetBeans

➤ Run the program

➤ Log in using the credentials below

➤ User Options

➤ Employee

➤ View personal details using employee number (e.g., 10001)

➤ Payroll Staff

➤ Process payroll for a single employee or all employees

➤ Exit program

-------------------------------------------------------------------------------

🔐 Login Credentials

Employee

Username: employee
Password: 12345

Payroll Staff

Username: Payroll_staff
Password: 12345

-------------------------------------------------------------------------------

⚙️ Features

✅️ Employee detail viewing (Employee login)

✅️ Payroll processing with deductions:

✅️ SSS, PhilHealth, Pag-IBIG, Tax

✅️ CSV-based data loading

✅️ Console interface with role-based access

-------------------------------------------------------------------------------

🛠 Improvements

💡 Improved code comments for better readability

💡 Replaced hardcoded column indices with constants

💡 Extracted reusable payroll calculation method

💡 Updated file paths to be configurable

💡 Implemented data-driven SSS calculation (easier updates)

💡 Fixed issues in processing payroll for all employees

💡 Added safeDoubleParse method for error handling

💡 Added warnings for missing files

-------------------------------------------------------------------------------

❗ Limitations

❌ No overtime or advanced salary rules

❌ Limited data validation

❌ Console-based only (no GUI)

-------------------------------------------------------------------------------

📖 Assumptions

Employee and attendance data in CSV files are complete and correctly formatted

Each employee has a unique employee number

Workdays follow a standard schedule (e.g., fixed working hours per day)

No overtime, night differential, or holiday pay is included

Deduction rates (SSS, PhilHealth, Pag-IBIG, Tax) follow predefined values

Attendance records accurately reflect actual hours worked

Missing or invalid numeric values are handled using default values (via safeDoubleParse)

File paths provided are valid and accessible during runtime

Payroll processing is limited to the period June to December only

The system is used by authorized users with correct login credentials

-------------------------------------------------------------------------------

📝 Project Plan (MMDC Template)
https://docs.google.com/spreadsheets/d/1cyHZtqzrkR0CXDAcpjE_3mWHy2d2gyWiHHVGxZIF0DU/edit?usp=sharing
