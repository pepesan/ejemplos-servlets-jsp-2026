#!/bin/bash
PID=$(lsof -ti tcp:8032)
if [ -n "$PID" ]; then
    echo "Deteniendo proceso en puerto 8032 (PID $PID)..."
    kill "$PID"
else
    echo "No hay ningún proceso escuchando en el puerto 8032."
fi
