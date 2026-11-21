# RIZQ – Food Donation Platform

A Software Engineering project developed using Core Java and MySQL to facilitate food donation management.

## Technologies Used
- Core Java
- MySQL Database
- JDBC
- MySQL Connector/J
- PowerShell

## Main Features
- User Registration and Login
- Donor Dashboard
- Volunteer Dashboard
- Welfare Organization Dashboard
- Admin Dashboard
- Donation Management
- Donation Claiming and Requests
- Feedback Management
- Activity History
- Input Validation

## Data Structures
The project demonstrates six custom data structures:
1. Singly Linked List
2. Doubly Linked List
3. Circular Linked List
4. Stack
5. Queue
6. Binary Search Tree

## Requirements
- JDK 17 or later
- MySQL Server
- MySQL Workbench
- Windows PowerShell
- MySQL Connector/J: mysql-connector-j-9.7.0.jar (included in lib/ folder)


## Database Setup
1. Start MySQL Server.
2. Open MySQL Workbench.
3. Execute the SQL database file provided in the database folder.
4. Ensure the database name is `rizq_db`.

## How to Compile and Run

Open PowerShell in the project folder and execute:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
.\compile.ps1
.\run.ps1
```

The run script will prompt for your local MySQL password.

The default database username in the run script is `root`. Change it if your MySQL username is different.

## Project Structure
- `src/` – Java source code
- `database/` – SQL database file
- `lib/` – Required JAR libraries
- `docs/` – Project documentation
- `compile.ps1` – Compilation script
- `run.ps1` – Application execution script

## Project Documentation
The `docs` folder contains the project proposal, SRS, SDD, and software testing documentation.

## Academic Project
Developed as a Software Engineering project at Sukkur IBA University.
