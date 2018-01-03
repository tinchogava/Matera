@REM *** PARAMETERS/VARIABLES ***
SET BackupDir=D:\BCKMASA
SET mysqldir=C:\Program Files\MySQL\MySQL Server 5.5\bin
SET mysqlpassword=123456
SET mysqluser=matera
SET mysqldatabase=matera

@REM *** EXECUTION ***
@REM Change to mysqldir
CD %mysqldir%
@REM dump/backup ALL database, this is all in one line
mysqldump -u %mysqluser% -p%mysqlpassword% --databases %mysqldatabase% > %BackupDir%\BACKUP.sql
@REM - YOU ARE DONE!