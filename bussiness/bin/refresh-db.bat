@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0
cd ..

call mvn clean compile hibernate3:hbm2ddl antrun:run -Prefresh-db war:war

cd bin
pause