@echo off
call gradlew build
FOR /F "delims=" %%I IN ('DIR D:\Games\Forge_Eclipse_workspace\AnimalsDropBones\build\libs /B /O:-D') DO echo %%I&call XCOPY D:\Games\Forge_Eclipse_workspace\AnimalsDropBones\build\libs\%%I D:\Games\.minecraft\mods\1.8& GOTO :EXIT
:EXIT
pause