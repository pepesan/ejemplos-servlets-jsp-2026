#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 26-jsp-estaticos
