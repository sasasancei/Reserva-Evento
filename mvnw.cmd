@echo off
REM ---------------------------------------------------------------------------
REM Apache Maven Wrapper
REM
REM A helper script to use the Apache Maven wrapper.
REM ---------------------------------------------------------------------------

set APP_NAME="Maven"
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%APP_NAME%

if exist "%DIRNAME%\bin%APP_BASE_NAME%" goto bin_dir
set MAVEN_HOME=%DIRNAME%
if exist "%MAVEN_HOME%\bin%APP_BASE_NAME%" goto bin_dir

:bin_dir
set MAVEN_OPTS=
set JAVA_HOME=
set JAVA_OPTS=
if not "%OS%" == "Windows_NT" set "MAVEN_OPTS= "
if not "%OS%" == "Windows_NT" set "JAVA_HOME= "
if not "%OS%" == "Windows_NT" set "JAVA_OPTS= "
set CMD_LINE_ARGS=

:param
if "%1"=="" goto end
set CMD_LINE_ARGS=%CMD_LINE_ARGS% "%1"
shift
goto param

:end
set SCRIPT_DIR=%~dp0
set SCRIPT_NAME=%~nx0
"%SCRIPT_DIR%mvnw" %CMD_LINE_ARGS%