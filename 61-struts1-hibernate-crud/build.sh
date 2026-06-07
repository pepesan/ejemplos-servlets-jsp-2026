#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 61-struts1-hibernate-crud..."
mvn clean package -pl 61-struts1-hibernate-crud
