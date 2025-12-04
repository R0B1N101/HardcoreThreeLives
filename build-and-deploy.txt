@echo off
setlocal

REM === Pad naar je plugin project (dit script mag naast pom.xml staan) ===
set PROJECT_DIR=%~dp0

REM === PAS DIT AAN: pad naar je Minecraft server map ===
REM Voorbeeld: C:\MinecraftTestServer
set SERVER_DIR=D:\TestServer

cd /d "%PROJECT_DIR%"

echo [1/3] Bouwen met Maven...
mvn clean package
if errorlevel 1 (
    echo.
    echo Build is mislukt. Check de foutmeldingen hierboven.
    pause
    exit /b 1
)

echo.
echo [2/3] Kopieer jar naar plugins map...

REM Jar-naam: komt uit pom.xml -> <finalName>HardcoreThreeLives</finalName>
copy /Y "%PROJECT_DIR%target\HardcoreThreeLives.jar" "%SERVER_DIR%\plugins\" >nul

if errorlevel 1 (
    echo.
    echo Kon de jar niet kopiëren. Klopt het pad naar je server?
    echo Huidig ingesteld: %SERVER_DIR%
    pause
    exit /b 1
)

echo.
echo [3/3] Klaar! Nieuwe plugin-versie staat in je plugins map.
echo.
echo Voer nu op je server uit:
echo   /plugman reload HardcoreThreeLives
echo.
echo (In serverconsole of in-game als je OP bent.)
echo.

pause
endlocal
