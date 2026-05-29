#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 13-servlet-request-response..."
mvn clean package -pl 13-servlet-request-response
