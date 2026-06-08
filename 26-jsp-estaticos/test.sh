#!/bin/bash
cd "$(dirname "$0")/.."
mvn test -pl 26-jsp-estaticos
