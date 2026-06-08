#!/bin/bash
echo "Parando 18-servlet-patrones (puerto 8018)..."
fuser -k 8018/tcp 2>/dev/null && echo "Detenido." || echo "No había proceso en el puerto 8018."
