#!/bin/bash
fuser -k 8031/tcp 2>/dev/null && echo "31-mvc-crud detenido (puerto 8031)" || echo "Puerto 8031 no estaba en uso"
