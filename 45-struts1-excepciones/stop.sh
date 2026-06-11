#!/bin/bash
fuser -k 8045/tcp 2>/dev/null && echo "45-struts1-excepciones detenido (puerto 8045)" || echo "Puerto 8045 no estaba en uso"
