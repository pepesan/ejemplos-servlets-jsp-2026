# 13 - HttpServletRequest y HttpServletResponse

Explora en profundidad los dos objetos que el contenedor pasa a cada servlet: `HttpServletRequest` (todo lo que llega del cliente) y `HttpServletResponse` (todo lo que el servidor puede devolver).

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

> Para configurar el JDK en IntelliJ IDEA ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Servlets

| Servlet | URL | Descripción |
|---------|-----|-------------|
| `InspeccionRequestServlet` | `GET /request` | Vuelca en HTML todo el contenido del `HttpServletRequest` |
| `TiposRespuestaServlet` | `GET /respuesta?tipo=<valor>` | Construye distintos tipos de respuesta según el parámetro |

---

## InspeccionRequestServlet — ¿qué llega en cada petición?

Muestra en tiempo real las seis secciones del `HttpServletRequest`:

| Sección | Métodos usados |
|---------|----------------|
| Línea de petición | `getMethod()`, `getRequestURI()`, `getQueryString()`, `getProtocol()` |
| Parámetros | `getParameter()`, `getParameterValues()`, `getParameterNames()` |
| Cabeceras HTTP | `getHeaderNames()`, `getHeader()` |
| Información del cliente | `getRemoteAddr()`, `getRemotePort()`, `getScheme()`, `isSecure()` |
| Cookies | `getCookies()` |
| Sesión | `getSession(false)`, `getAttribute()` |

```bash
# Sin parámetros
curl http://localhost:8013/request

# Con query string
curl "http://localhost:8013/request?nombre=Ana&edad=30"
```

---

## TiposRespuestaServlet — ¿qué puede devolver el servidor?

El parámetro `tipo` selecciona el tipo de respuesta:

| `tipo` | Status | Content-Type | Descripción |
|--------|--------|--------------|-------------|
| `html` (defecto) | 200 | `text/html` | Página HTML estándar |
| `json` | 200 | `application/json` | JSON construido a mano, sin librería |
| `texto` | 200 | `text/plain` | Texto plano sin etiquetas |
| `xml` | 200 | `application/xml` | XML construido a mano |
| `creado` | 201 | `application/json` | 201 Created + cabecera `Location` |
| `redirigir` | 302 | — | `sendRedirect()` a `/request` |
| `no-encontrado` | 404 | `text/html` | Cuerpo de error personalizado |
| `error` | 500 | `application/json` | Error simulado en JSON |
| `cookie` | 200 | `text/html` | Establece una cookie con `addCookie()` |
| `sesion` | 200 | `text/html` | Guarda atributo en sesión con `getSession(true)` |

### Construir una respuesta JSON a mano

```java
resp.setStatus(HttpServletResponse.SC_OK);          // 200
resp.setContentType("application/json;charset=UTF-8");
resp.getWriter().write("{ \"clave\": \"valor\" }");
```

### Código de estado y cabeceras de respuesta

```java
resp.setStatus(HttpServletResponse.SC_CREATED);     // 201
resp.setHeader("Location", "/recurso/42");
```

### Cookies

```java
Cookie cookie = new Cookie("nombre", "valor");
cookie.setMaxAge(60);   // segundos; -1 = sesión; 0 = borrar
cookie.setPath("/");
resp.addCookie(cookie);
```

### Sesión HTTP

```java
// Crear sesión si no existe (getSession(true)) o solo leer (getSession(false))
HttpSession sesion = req.getSession(true);
sesion.setAttribute("usuario", "Ana");
```

El contenedor gestiona la sesión mediante la cookie `JSESSIONID` (o reescritura de URL si las cookies están desactivadas).

---

## Flujo de exploración recomendado

1. Visita `http://localhost:8013/` — portada con todos los enlaces.
2. Abre `/request` y observa las cabeceras que envía tu navegador.
3. Añade `?nombre=Ana&ciudad=Madrid` y verifica la sección de parámetros.
4. Visita `/respuesta?tipo=cookie` — establece una cookie.
5. Vuelve a `/request` — verifica la cookie en la sección 5.
6. Visita `/respuesta?tipo=sesion` — guarda un atributo en sesión.
7. Vuelve a `/request` — verifica el atributo en la sección 6.
8. Prueba todos los `tipo` con las DevTools del navegador (pestaña Network).

---

## Arranque y pruebas

```bash
./start.sh                                    # Tomcat en http://localhost:8013
curl http://localhost:8013/request            # → HTML con el contenido del request
curl "http://localhost:8013/request?x=1&y=2" # → parámetros visibles en la sección 2
curl http://localhost:8013/respuesta?tipo=json   # → JSON 200
curl -i http://localhost:8013/respuesta?tipo=creado  # → 201 + cabecera Location
curl -i http://localhost:8013/respuesta?tipo=redirigir  # → 302 + Location
curl http://localhost:8013/respuesta?tipo=no-encontrado  # → 404 HTML
curl http://localhost:8013/respuesta?tipo=error   # → 500 JSON
./stop.sh
```

---

## Scripts

```bash
./build.sh   # mvn clean package → genera 13-servlet-request-response-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8013
./stop.sh    # fuser -k 8013/tcp
./test.sh    # mvn test
```

## Tests

| Test | Verifica |
|------|---------|
| `HtmlTest#escapeAmpersand` | `&` → `&amp;` |
| `HtmlTest#escapeEtiquetas` | `<b>` → `&lt;b&gt;` |
| `HtmlTest#escapeComillasDobles` | `"` → `&quot;` |
| `HtmlTest#textoLimpioSinCambios` | Texto sin caracteres especiales pasa intacto |
| `HtmlTest#nullDevuelveCadenaVacia` | `null` → `""` |
| `TiposRespuestaServletTest#construirJson*` (×4) | `construirJson()` produce JSON con módulo, status y mensaje correctos |
