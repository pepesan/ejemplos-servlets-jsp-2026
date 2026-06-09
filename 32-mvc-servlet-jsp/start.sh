#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 32-mvc-servlet-jsp en http://localhost:8032"
mvn tomcat7:run -pl 32-mvc-servlet-jsp
