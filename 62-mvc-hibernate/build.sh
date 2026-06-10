#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 62-mvc-hibernate
