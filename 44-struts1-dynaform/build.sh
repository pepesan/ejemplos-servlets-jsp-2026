#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 44-struts1-dynaform..."
mvn clean package -pl 44-struts1-dynaform
