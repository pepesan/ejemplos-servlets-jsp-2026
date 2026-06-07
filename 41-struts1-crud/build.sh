#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 41-struts1-crud..."
mvn clean package -pl 41-struts1-crud
