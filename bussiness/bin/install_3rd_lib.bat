@echo off
echo [INFO] Install SqlServer jdbc driver into maven repository
echo [INFO] current dir %~dp0
cd %~dp0
cd ..
call ls %~dp0\lib\sqljdbc4.jar
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
call mvn install:install-file -Dfile=%~dp0lib\sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=2.0 -Dpackaging=jar

cd bin
pause