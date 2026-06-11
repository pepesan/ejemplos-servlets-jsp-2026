#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 47-struts1-upload..."
mvn clean package -pl 47-struts1-upload
