#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 27-jsp-webjars
