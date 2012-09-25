@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0
cd ..

call mvn clean compile exec:java antrun:run -Prefresh-db war:war

cd bin
pause