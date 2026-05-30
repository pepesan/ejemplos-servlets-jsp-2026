#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Arrancando 22-jsp-include-layout en http://localhost:8022"
mvn tomcat7:run -pl 22-jsp-include-layout
