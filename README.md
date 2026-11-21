# RIZQ - Core Java + MySQL

## Requirements
- JDK 17 or later
- MySQL Server
- MySQL Connector/J JAR placed directly inside `lib/`
- VS Code or another Java IDE

## Database
Run `database/schema.sql` in MySQL Workbench.

## Configure database
PowerShell (replace the password locally; do not share it):
```powershell
$env:RIZQ_DB_URL="jdbc:mysql://localhost:3306/rizq_db?serverTimezone=UTC"
$env:RIZQ_DB_USER="root"
$env:RIZQ_DB_PASSWORD="YOUR_PASSWORD"
```

## Compile and run
```powershell
.\compile.ps1
.\run.ps1
```

This is an academic starter implementation. It demonstrates JDBC, role selection,
shared Volunteer/Welfare Organization login, separate organization registration
details, OOP inheritance/abstraction, and six custom data structures. Extend and
test each workflow before final submission. For a real deployment, store salted
password hashes rather than plain text; this starter's simple password field is
for local demonstration only.
