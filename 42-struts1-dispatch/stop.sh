#!/bin/bash
fuser -k 8042/tcp 2>/dev/null && echo "42-struts1-dispatch detenido (puerto 8042)" || echo "Puerto 8042 no estaba en uso"
