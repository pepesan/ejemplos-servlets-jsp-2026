#!/bin/bash
echo "Parando todos los módulos web..."
for port in \
    8010 8011 8012 8013 8014 8015 8016 8017 8018 8019 \
    8021 8022 8023 8024 8025 8026 8027 \
    8031 8032 \
    8041 8042 8043 8044 8045 8046 8047 8048 \
    8061 8062 \
    8083 8084 8085 8086; do
    fuser -k ${port}/tcp 2>/dev/null && echo "  Puerto ${port} detenido" || echo "  Puerto ${port} no estaba en uso"
done
