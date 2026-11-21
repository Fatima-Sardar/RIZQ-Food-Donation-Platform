$env:RIZQ_DB_URL = "jdbc:mysql://localhost:3306/rizq_db?serverTimezone=UTC"
$env:RIZQ_DB_USER = "root"

Write-Host "Enter your MySQL password:"
$securePassword = Read-Host -AsSecureString

$ptr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)

try {
    $env:RIZQ_DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($ptr)

    java --enable-native-access=ALL-UNNAMED -cp "out;lib/*" rizq.Main
}
finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ptr)
    Remove-Item Env:RIZQ_DB_PASSWORD -ErrorAction SilentlyContinue
}