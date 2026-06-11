#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 44-struts1-dynaform..."
mvn test -pl 44-struts1-dynaform
