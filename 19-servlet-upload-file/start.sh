#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 19-servlet-upload-file en http://localhost:8019"
mvn tomcat7:run -pl 19-servlet-upload-file
