#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 42-struts1-dispatch..."
mvn test -pl 42-struts1-dispatch
