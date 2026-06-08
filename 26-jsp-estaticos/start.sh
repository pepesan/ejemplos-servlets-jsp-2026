#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 26-jsp-estaticos en http://localhost:8026"
mvn tomcat7:run -pl 26-jsp-estaticos
