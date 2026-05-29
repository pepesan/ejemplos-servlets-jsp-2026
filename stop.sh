#!/bin/bash
echo "Parando todos los módulos web..."
for port in 8010 8011 8083 8084 8085 8086; do
    fuser -k ${port}/tcp 2>/dev/null && echo "  Puerto ${port} detenido" || echo "  Puerto ${port} no estaba en uso"
done
