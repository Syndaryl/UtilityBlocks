@echo off
@SET UTILITYBLOCKS_BUILD_PATH=build\libs
@SET UTILITYBLOCKS_DEPLOY_PATH=D:\Games\Minecraft1.9.0\mods\1.9
:BUILD
call gradlew build
:CLEARDEPLOYAREA
FOR /F "delims=" %%I IN ('DIR %UTILITYBLOCKS_DEPLOY_PATH%\*UtilityBlocks-*-universal.jar /B /O:-D') DO echo %%I&call MOVE %UTILITYBLOCKS_DEPLOY_PATH%\%%I %UTILITYBLOCKS_DEPLOY_PATH%\%%I.Disabled & GOTO :DEPLOY
:DEPLOY
FOR /F "delims=" %%I IN ('DIR %UTILITYBLOCKS_BUILD_PATH%\*UtilityBlocks-*-universal.jar /B /O:-D') DO echo %%I&call XCOPY build\libs\%%I %UTILITYBLOCKS_DEPLOY_PATH% & GOTO :EXIT
:EXIT
pause