#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 42-struts1-dispatch..."
mvn clean package -pl 42-struts1-dispatch
