$files = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
New-Item -ItemType Directory -Force -Path out | Out-Null
javac -cp "lib/*" -d out $files
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "Compilation successful."
