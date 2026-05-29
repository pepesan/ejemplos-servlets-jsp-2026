#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 12-servlet-lifecycle..."
mvn clean package -pl 12-servlet-lifecycle
