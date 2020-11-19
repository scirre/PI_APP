@echo off

echo This script installs this TotalCross Application on a WINDOWS CE or WINDOWS MOBILE device.
rem test on the ProgramFiles variable
if not "%ProgramFiles%"=="" goto ok
set ProgramFiles=c:\Progra~1
:ok
"%ProgramFiles%\Microsoft ActiveSync\CeAppMgr.exe" ".\CONTROLE_Install.ini"
if "%errorlevel%"=="1" goto end

"%windir%\WindowsMobile\CeAppMgr.exe" ".\CONTROLE_Install.ini"
if "%errorlevel%"=="1" goto end

CeAppMgr.exe ".\CONTROLE_Install.ini"
if "%errorlevel%"=="0" goto end

echo:
echo ERROR: Cannot locate CeAppMgr.exe. Please put the
echo "Microsoft ActiveSync" directory on your path variable.
echo It is usually located under "Program Files".
echo:
pause
:end
