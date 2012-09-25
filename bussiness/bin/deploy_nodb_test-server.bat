@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0
cd ..

call mvn clean compile war:war -Dproduct_ip=10.90.3.122 -Dapp_name=callface -Dproduct_port=38183 -Dproduct_resource_ip=10.90.3.122 -Dproduct_resource_port=38183

cd bin
pause