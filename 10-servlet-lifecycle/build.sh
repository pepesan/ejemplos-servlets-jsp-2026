#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 02-servlet-lifecycle..."
mvn clean package -pl 10-servlet-lifecycle
