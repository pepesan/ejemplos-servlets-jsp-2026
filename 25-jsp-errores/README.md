# 25 — JSP: Manejo de errores

Dos mecanismos complementarios para gestionar errores en una aplicación web Java EE.

## Mecanismo 1 — Directiva `errorPage` (por página JSP)

```jsp
<%@ page errorPage="/mi-error.jsp" %>   ← en la página que puede fallar
<%@ page isErrorPage="true" %>          ← en la página de error receptora
```

- Se declara por JSP, no globalmente.
- La página receptora accede al objeto implícito `exception` (solo con `isErrorPage="true"`).
- El contenedor hace un **forward** interno; la URL del navegador no cambia.

## Mecanismo 2 — `<error-page>` en web.xml (global)

```xml
<!-- Por código HTTP -->
<error-page><error-code>404</error-code><location>/error-404.jsp</location></error-page>
<error-page><error-code>500</error-code><location>/error-500.jsp</location></error-page>

<!-- Por tipo de excepción -->
<error-page>
    <exception-type>java.lang.Exception</exception-type>
    <location>/error-500.jsp</location>
</error-page>
```

- Aplica a toda la aplicación (servlets y JSPs sin `errorPage`).
- El contenedor pone información en atributos `javax.servlet.error.*` del request.

## Atributos disponibles en la página de error

| Atributo | Tipo | Contenido |
|----------|------|-----------|
| `javax.servlet.error.status_code` | Integer | Código HTTP (404, 500…) |
| `javax.servlet.error.request_uri` | String | URI que causó el error |
| `javax.servlet.error.message` | String | Mensaje del error |
| `javax.servlet.error.exception_type` | Class | Clase de la excepción |
| `javax.servlet.error.exception` | Throwable | La excepción (si la hay) |
| `javax.servlet.error.servlet_name` | String | Nombre del servlet |

## Ficheros

| Fichero | Función |
|---------|---------|
| `demos.jsp` | Tiene `errorPage="/mi-error.jsp"`; lanza excepciones con `?forzar=division\|npe\|custom` |
| `mi-error.jsp` | `isErrorPage="true"`; muestra `exception` y atributos del request |
| `error-400.jsp` | Página global para HTTP 400 (web.xml) |
| `error-404.jsp` | Página global para HTTP 404 (web.xml) |
| `error-500.jsp` | Página global para HTTP 500 y excepciones no capturadas (web.xml) |
| `GenerarErrorServlet` | `/generar?tipo=404\|500\|runtime\|npe\|aritm` — activa los distintos mecanismos |

## Arrancar

```bash
./start.sh        # http://localhost:8025
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Portada
curl -s http://localhost:8025/ | grep -o 'Manejo de errores'

# Mecanismo 1: demos.jsp sin error
curl -s http://localhost:8025/demos.jsp | grep -o 'directiva'

# Mecanismo 1: ArithmeticException → mi-error.jsp
curl -s "http://localhost:8025/demos.jsp?forzar=division" | grep -o 'Error capturado'
curl -s "http://localhost:8025/demos.jsp?forzar=division" | grep -o 'ArithmeticException'

# Mecanismo 1: NullPointerException
curl -s "http://localhost:8025/demos.jsp?forzar=npe" | grep -o 'NullPointerException'

# Mecanismo 1: excepción personalizada
curl -s "http://localhost:8025/demos.jsp?forzar=custom" | grep -o 'IllegalArgumentException'

# Mecanismo 2: sendError(404) → error-404.jsp
curl -s "http://localhost:8025/generar?tipo=404" | grep -o 'Página no encontrada'

# Mecanismo 2: URL inexistente → error-404.jsp
curl -s http://localhost:8025/no-existe.jsp | grep -o 'Página no encontrada'

# Mecanismo 2: sendError(500) → error-500.jsp
curl -s "http://localhost:8025/generar?tipo=500" | grep -o 'Error interno'

# Mecanismo 2: RuntimeException desde servlet → error-500.jsp con stack trace
curl -s "http://localhost:8025/generar?tipo=runtime" | grep -o 'RuntimeException'

# Mecanismo 2: NPE desde servlet → error-500.jsp
curl -s "http://localhost:8025/generar?tipo=npe" | grep -o 'NullPointerException'

# 400: parámetro tipo ausente
curl -s http://localhost:8025/generar | grep -o 'Petición incorrecta'
```
