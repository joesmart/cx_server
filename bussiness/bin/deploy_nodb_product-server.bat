@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0
cd ..

call mvn clean compile war:war -Dproduct_ip=111.1.17.82 -Dapp_name=callface -Dproduct_port=38183 -Dproduct_resource_ip=111.1.17.82 -Dproduct_resource_port=38183

cd bin
pause