@echo off
setlocal

set DIR=%~dp0

set GRADLE_WRAPPER_PROPERTIES=%DIR%gradle\wrapper\gradle-wrapper.properties

if not exist "%GRADLE_WRAPPER_PROPERTIES%" (
    echo gradle-wrapper.properties not found. Creating default properties file.
    mkdir "%DIR%gradle\wrapper" 2>nul
    echo distributionBase=GRADLE_USER_HOME> "%GRADLE_WRAPPER_PROPERTIES%"
    echo distributionPath=wrapper/dists>> "%GRADLE_WRAPPER_PROPERTIES%"
    echo zipStoreBase=GRADLE_USER_HOME>> "%GRADLE_WRAPPER_PROPERTIES%"
    echo zipStorePath=wrapper/dists>> "%GRADLE_WRAPPER_PROPERTIES%"
    echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2.1-bin.zip>> "%GRADLE_WRAPPER_PROPERTIES%"
)

set GRADLE_EXIT_CONSOLE=

if "%OS%"=="Windows_NT" set GRADLE_EXIT_CONSOLE=cmd /C

set CLASSPATH=

if exist "%DIR%gradle\wrapper\gradle-wrapper.jar" (
    set WRAPPER_JAR="%DIR%gradle\wrapper\gradle-wrapper.jar"
) else (
    echo gradle-wrapper.jar not found. Please run 'gradle wrapper' to generate it.
    exit /b 1
)

%GRADLE_EXIT_CONSOLE% java -Dorg.gradle.appname=%~n0 -classpath %WRAPPER_JAR% org.gradle.wrapper.GradleWrapperMain %*
endlocal
