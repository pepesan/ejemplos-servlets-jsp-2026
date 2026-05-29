#!/bin/bash
cd "$(dirname "$0")"
echo "Arrancando todos los módulos web en segundo plano..."
mvn tomcat7:run -pl 10-servlet-lifecycle &
mvn tomcat7:run -pl 20-jsp-jstl &
mvn tomcat7:run -pl 30-mvc &
mvn tomcat7:run -pl 40-struts1 &
mvn tomcat7:run -pl 60-struts1-hibernate &
echo ""
echo "Módulos disponibles:"
echo "  10-servlet-lifecycle  → http://localhost:8082"
echo "  20-jsp-jstl           → http://localhost:8083"
echo "  30-mvc                → http://localhost:8084"
echo "  40-struts1            → http://localhost:8085"
echo "  60-struts1-hibernate  → http://localhost:8086"
echo ""
echo "Pulsa Ctrl+C para detener todos"
wait
