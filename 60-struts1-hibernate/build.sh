#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 07-struts1-hibernate..."
mvn clean package -pl 60-struts1-hibernate
