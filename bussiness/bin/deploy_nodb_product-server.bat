@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0
cd ..

call mvn clean compile war:war -Dapp_name=callface

cd bin
pause