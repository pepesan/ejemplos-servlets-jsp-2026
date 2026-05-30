#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Arrancando 24-jsp-jstl-funciones en http://localhost:8024"
mvn tomcat7:run -pl 24-jsp-jstl-funciones
