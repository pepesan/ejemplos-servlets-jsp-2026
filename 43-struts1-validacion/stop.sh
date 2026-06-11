#!/bin/bash
fuser -k 8043/tcp 2>/dev/null && echo "43-struts1-validacion detenido (puerto 8043)" || echo "Puerto 8043 no estaba en uso"
