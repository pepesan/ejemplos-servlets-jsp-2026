# 24 — JSTL: Biblioteca de funciones `fn:`

Referencia completa de las funciones de cadena y colección de JSTL, con código y resultado lado a lado.

## Declaración necesaria

```jsp
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
```

## Funciones cubiertas

| Grupo | Funciones |
|-------|-----------|
| Longitud | `fn:length` |
| Caso | `fn:toUpperCase`, `fn:toLowerCase`, `fn:trim` |
| Búsqueda | `fn:contains`, `fn:containsIgnoreCase`, `fn:startsWith`, `fn:endsWith`, `fn:indexOf` |
| Subcadenas | `fn:substring`, `fn:substringBefore`, `fn:substringAfter` |
| Reemplazar | `fn:replace` |
| Split/Join | `fn:split`, `fn:join` |
| Seguridad | `fn:escapeXml` |
| Combinadas | Funciones anidadas + uso con `c:forEach` |

## Arrancar

```bash
./start.sh        # http://localhost:8024
```

## Pruebas curl

```bash
curl -s http://localhost:8024/funciones.jsp | grep -o 'fn:length' | head -1
curl -s http://localhost:8024/funciones.jsp | grep -o 'HOLA, MUNDO JSP' | head -1
curl -s http://localhost:8024/funciones.jsp | grep -o '|Hola, Mundo JSP!|'
curl -s http://localhost:8024/funciones.jsp | grep -o '>true<' | head -1
curl -s http://localhost:8024/funciones.jsp | grep -o 'java | servlet | jsp' | head -1
curl -s http://localhost:8024/funciones.jsp | grep -o '&lt;script&gt;' | head -1
```
