#!/bin/bash
PID=$(lsof -ti tcp:8062)
if [ -n "$PID" ]; then
    echo "Deteniendo proceso en puerto 8062 (PID $PID)..."
    kill "$PID"
else
    echo "No hay ningún proceso escuchando en el puerto 8062."
fi
