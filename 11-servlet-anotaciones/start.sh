#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 11-servlet-anotaciones en http://localhost:8011"
mvn tomcat7:run -pl 11-servlet-anotaciones
