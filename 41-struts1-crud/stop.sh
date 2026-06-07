#!/bin/bash
fuser -k 8041/tcp 2>/dev/null && echo "41-struts1-crud detenido (puerto 8041)" || echo "Puerto 8041 no estaba en uso"
