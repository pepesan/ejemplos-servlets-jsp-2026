#!/bin/bash
fuser -k 8084/tcp 2>/dev/null && echo "04-mvc detenido (puerto 8084)" || echo "Puerto 8084 no estaba en uso"
