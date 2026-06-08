#!/bin/bash
cd "$(dirname "$0")/.."
fuser -k 8026/tcp 2>/dev/null && echo "Detenido." || echo "No había proceso en el puerto 8026."
