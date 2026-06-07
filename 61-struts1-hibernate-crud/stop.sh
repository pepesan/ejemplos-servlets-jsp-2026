#!/bin/bash
fuser -k 8061/tcp 2>/dev/null && echo "61-struts1-hibernate-crud detenido (puerto 8061)" || echo "Puerto 8061 no estaba en uso"
