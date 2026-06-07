#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 42-struts1-dispatch en http://localhost:8042"
mvn tomcat7:run -pl 42-struts1-dispatch
