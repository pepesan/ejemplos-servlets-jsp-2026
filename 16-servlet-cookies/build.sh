#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 16-servlet-cookies..."
mvn clean package -pl 16-servlet-cookies
