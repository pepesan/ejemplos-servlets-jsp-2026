#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Tests de 22-jsp-include-layout..."
mvn test -pl 22-jsp-include-layout
