@REM 因mave site對多模組支援問題手動將檔名改動
@echo off
for /r "target\staging\" %%i in (project-reports.html) do ren "%%i" index.html 2>nul
echo Renaming completed.
pause 
