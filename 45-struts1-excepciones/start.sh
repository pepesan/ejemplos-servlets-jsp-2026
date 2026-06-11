#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 45-struts1-excepciones en http://localhost:8045"
mvn tomcat7:run -pl 45-struts1-excepciones
