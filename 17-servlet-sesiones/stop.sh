#!/bin/bash
fuser -k 8017/tcp 2>/dev/null && echo "17-servlet-sesiones detenido (puerto 8017)" || echo "Puerto 8017 no estaba en uso"
