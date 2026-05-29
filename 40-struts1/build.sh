#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 05-struts1..."
mvn clean package -pl 40-struts1
