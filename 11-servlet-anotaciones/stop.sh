#!/bin/bash
fuser -k 8011/tcp 2>/dev/null && echo "11-servlet-anotaciones detenido (puerto 8011)" || echo "Puerto 8011 no estaba en uso"
