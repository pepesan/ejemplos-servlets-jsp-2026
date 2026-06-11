#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 48-struts1-formulario-complejo en http://localhost:8048"
mvn tomcat7:run -pl 48-struts1-formulario-complejo
