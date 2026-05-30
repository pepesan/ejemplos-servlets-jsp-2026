#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Arrancando 23-jsp-formularios en http://localhost:8023"
mvn tomcat7:run -pl 23-jsp-formularios
