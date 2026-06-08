#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 18-servlet-patrones
