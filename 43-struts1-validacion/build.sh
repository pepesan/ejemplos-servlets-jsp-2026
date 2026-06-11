#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 43-struts1-validacion..."
mvn clean package -pl 43-struts1-validacion
