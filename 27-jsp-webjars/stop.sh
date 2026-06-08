#!/bin/bash
cd "$(dirname "$0")/.."
fuser -k 8027/tcp 2>/dev/null && echo "Detenido." || echo "No había proceso en el puerto 8027."
