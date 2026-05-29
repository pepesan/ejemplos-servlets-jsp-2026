#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 03-maven-profiles..."
mvn clean package -pl 03-maven-profiles
