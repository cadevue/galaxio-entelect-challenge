@echo off
setlocal

::Run the engine
cd .\engine
call .\run.bat
if %errorlevel% neq 0 (
    echo Engine failed to run
    exit /b %errorlevel%
)

endlocal