@echo off
setlocal
set DIR=%~dp0
if exist "%DIR%gradlew.bat" (
  call "%DIR%gradlew.bat" %*
) else (
  echo gradlew.bat no encontrado. Ejecutando gradle directamente...
  gradle %*
)
endlocal
