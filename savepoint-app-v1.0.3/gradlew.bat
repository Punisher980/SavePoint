@echo off
where gradle >nul 2>nul
if %errorlevel% equ 0 (
  gradle %*
  exit /b %errorlevel%
)
echo Gradle nao encontrado. Abra o projeto no Android Studio ou instale Gradle 8.10.2.
exit /b 1
