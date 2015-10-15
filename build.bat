@echo off
call gradlew build
FOR /F "delims=" %%I IN ('DIR build\libs *-universal.jar /B /O:-D') DO echo %%I&call XCOPY build\libs\%%I D:\Games\.Minecraft1.8.0\mods\1.8& GOTO :EXIT
:EXIT
pause