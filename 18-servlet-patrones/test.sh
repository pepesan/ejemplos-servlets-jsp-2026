#!/bin/bash
cd "$(dirname "$0")/.."
mvn test -pl 18-servlet-patrones
