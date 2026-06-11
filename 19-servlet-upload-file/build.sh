#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 19-servlet-upload-file..."
mvn clean package -pl 19-servlet-upload-file
