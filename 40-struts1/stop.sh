#!/bin/bash
fuser -k 8085/tcp 2>/dev/null && echo "05-struts1 detenido (puerto 8085)" || echo "Puerto 8085 no estaba en uso"
