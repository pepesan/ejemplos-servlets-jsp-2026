#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 61-struts1-hibernate-crud..."
mvn test -pl 61-struts1-hibernate-crud
