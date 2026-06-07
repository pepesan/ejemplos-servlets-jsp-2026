#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 61-struts1-hibernate-crud en http://localhost:8061"
mvn tomcat7:run -pl 61-struts1-hibernate-crud
