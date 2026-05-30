# 22 — JSP: Include y Layout

Dos mecanismos de inclusión: `<%@ include %>` estático (compilación) y `<jsp:include>` dinámico (ejecución) con `<jsp:param>` para componentes parametrizados.

## Páginas

| Página | Concepto |
|--------|----------|
| `estatico.jsp` | `<%@ include file="WEB-INF/_pie.jspf" %>` — el fragmento comparte variables del padre |
| `dinamico.jsp` | `<jsp:include page="...">` — sub-petición aislada, con cuatro alertas de distinto tipo |
| `con-params.jsp` | `<jsp:param>` — tarjeta reutilizable con título, texto y color diferentes |

## Fragmentos en WEB-INF (no accesibles directamente)

| Fragmento | Uso |
|-----------|-----|
| `WEB-INF/_pie.jspf` | Include estático; lee `paginaTitulo` del padre |
| `WEB-INF/fragmentos/alerta.jsp` | Componente dinámico: parámetros `tipo` y `mensaje` |
| `WEB-INF/fragmentos/tarjeta.jsp` | Componente dinámico: parámetros `titulo`, `texto`, `accentColor` |

## Diferencia clave

| | `<%@ include %>` | `<jsp:include>` |
|--|------------------|-----------------|
| Cuándo | Compilación | Ejecución |
| Variables del padre | Visibles | No accesibles |
| Ruta | Literal fija | Puede ser dinámica |
| Resultado | Un servlet | Servlets separados |

> **Nota Tomcat 7:** Los JSP se compilan con source level 1.6. Usar `if-else` en lugar de `switch(String)` en los fragmentos.

## Arrancar

```bash
./start.sh        # http://localhost:8022
```

## Pruebas curl

```bash
curl -s http://localhost:8022/ | grep -o 'Include y Layout'
curl -s http://localhost:8022/estatico.jsp | grep -o 'estatico.jsp' | head -1
curl -s http://localhost:8022/dinamico.jsp | grep -o 'Tipo info' | head -1
curl -s http://localhost:8022/dinamico.jsp | grep -o 'border-left:4px solid' | wc -l   # 4
curl -s http://localhost:8022/con-params.jsp | grep -o 'Java' | head -1
curl -s http://localhost:8022/con-params.jsp | grep -o 'Módulo 22' | head -1
```
