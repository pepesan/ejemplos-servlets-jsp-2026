# 12 - Ciclo de Vida del Servlet

Demuestra los tres métodos del ciclo de vida de un Servlet y cuándo los invoca el contenedor (Tomcat), junto con los conceptos de `init-param`, `load-on-startup` y estado compartido entre peticiones.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

> Para configurar el JDK en IntelliJ IDEA ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## El ciclo de vida de un Servlet

El contenedor (Tomcat) gestiona la instancia del servlet. El programador no instancia ni destruye servlets directamente.

```
  Arranque del servidor
        │
        ▼
  ┌─────────────┐
  │   init()    │  ← llamado UNA SOLA VEZ al cargar el servlet
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │  service()  │  ← llamado en CADA petición HTTP
  │  (doGet /   │     puede ejecutarse en hilos concurrentes
  │   doPost)   │
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │  destroy()  │  ← llamado UNA SOLA VEZ al descargar el servlet
  └─────────────┘
        │
        ▼
  Parada del servidor (o redespliegue)
```

---

## Los tres métodos en detalle

### 1. `init(ServletConfig config)`

```java
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);   // SIEMPRE llamar primero a super.init()

    // Aquí se inicializan recursos costosos:
    // conexiones a BD, pools de objetos, lecturas de ficheros de config...
    autor = config.getInitParameter("autor");
}
```

- Garantía: **se ejecuta una sola vez** antes de que el servlet atienda peticiones.
- `ServletConfig` da acceso a los `<init-param>` del `web.xml` y al `ServletContext`.
- **Siempre llamar `super.init(config)`**: sin eso, `getServletContext()` y `getServletConfig()` devuelven `null`.

### 2. `service(HttpServletRequest, HttpServletResponse)`

```java
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    // HttpServlet.service() lee req.getMethod() y delega:
    //   GET  → doGet()
    //   POST → doPost()
    //   PUT  → doPut()  ...etc.
    super.service(req, resp);
}
```

- Llamado **en cada petición**.
- `HttpServlet` ya implementa `service()`: examina el método HTTP y delega al `doGet()`/`doPost()` correspondiente.
- **Normalmente no se sobreescribe `service()`**, solo `doGet()`/`doPost()`. Aquí se sobreescribe únicamente para hacer visible la llamada.

### 3. `destroy()`

```java
@Override
public void destroy() {
    // Aquí se liberan recursos: cerrar conexiones, guardar estado...
    log("destroy() — total peticiones: " + contadorPeticiones.get());
    super.destroy();
}
```

- Garantía: **se ejecuta una sola vez** cuando el contenedor descarga el servlet.
- Verás el mensaje en la consola de Tomcat al pulsar `Ctrl+C` o redesplegar.

---

## `load-on-startup`

```xml
<servlet>
    <servlet-name>cicloVida</servlet-name>
    <servlet-class>com.cursosdedesarrollo.CicloVidaServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```

| Valor | Comportamiento |
|-------|---------------|
| Ausente o `-1` | Carga perezosa: `init()` se ejecuta en la primera petición |
| `1`, `2`, `3`… | Eager: `init()` se ejecuta al arrancar el servidor, en el orden indicado |

Útil para detectar errores de configuración al arrancar en lugar de en la primera petición real.

---

## `init-param`

```xml
<init-param>
    <param-name>autor</param-name>
    <param-value>Curso de Servlets 2026</param-value>
</init-param>
```

```java
String autor = config.getInitParameter("autor");
```

Parámetros de configuración específicos del servlet, accesibles en `init()`. Para parámetros globales (toda la aplicación) se usa `<context-param>` + `getServletContext().getInitParameter()`.

---

## Variables de instancia y concurrencia

El contenedor crea **una sola instancia** del servlet y atiende peticiones concurrentes con **hilos distintos**. Las variables de instancia son compartidas:

```java
// CORRECTO: AtomicInteger es thread-safe
private final AtomicInteger contadorPeticiones = new AtomicInteger(0);

// PELIGROSO: int normal no es thread-safe bajo concurrencia
// private int contadorPeticiones = 0;  ← NO hacer esto
```

Las variables locales dentro de `doGet()`/`doPost()` son seguras porque cada hilo tiene su propia pila.

---

## FormularioServlet — doGet + doPost

Un servlet que atiende dos métodos HTTP en la misma URL `/formulario`:

| Método | URL | Qué hace |
|--------|-----|---------|
| `GET` | `/formulario` | Sirve el formulario HTML (o lo muestra con error si el POST fue inválido) |
| `POST` | `/formulario` | Lee los parámetros del cuerpo, valida y devuelve un resumen |

### Patrón PRG (Post-Redirect-Get)

Cuando la validación falla, el servlet **redirige** al GET en lugar de responder directamente:

```java
resp.sendRedirect("/formulario?error=vacio");
return;
```

Esto evita que al pulsar F5 el navegador reenvíe el POST. Es el patrón estándar para formularios web.

### Leer parámetros POST

```java
// SIEMPRE fijar la codificación ANTES de leer parámetros
req.setCharacterEncoding("UTF-8");

String nombre  = req.getParameter("nombre");
String email   = req.getParameter("email");
String mensaje = req.getParameter("mensaje");
```

Los parámetros viajan en el **cuerpo** de la petición con formato `application/x-www-form-urlencoded`. `getParameter()` los lee igual que en GET, pero no aparecen en la URL.

### Escapar HTML para evitar XSS

Al reflejar datos del usuario en la respuesta HTML siempre hay que escapar:

```java
static String escaparHtml(String texto) {
    return texto
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
}
```

Sin esto, un usuario podría inyectar `<script>alert('XSS')</script>` como nombre.

---

## Arranque y pruebas

```bash
./start.sh                              # Tomcat en http://localhost:8012
# Página de inicio:
curl http://localhost:8012/             # → index.html con descripción de los servlets
# CicloVidaServlet:
curl http://localhost:8012/ciclo-vida   # → HTML con init-params y contador
# Recargar → el contador de peticiones sube
# Parar el servidor → ver "destroy()" en la consola de Tomcat

# FormularioServlet (GET):
curl http://localhost:8012/formulario

# FormularioServlet (POST con datos válidos):
curl -X POST http://localhost:8012/formulario \
     -d "nombre=Ana&email=ana@ejemplo.com&mensaje=Hola"

# POST con campos vacíos → redirige 302 a /formulario?error=vacio:
curl -v -X POST http://localhost:8012/formulario -d "nombre=&email=&mensaje="

./stop.sh
```

---

## Scripts

```bash
./build.sh   # mvn clean package → genera 12-servlet-lifecycle-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8012
./stop.sh    # fuser -k 8012/tcp
./test.sh    # mvn test
```

## Tests

| Test | Verifica |
|------|---------|
| `contadorIniciaCero` | El `AtomicInteger` arranca a 0 antes de cualquier petición |
| `contadorEsAtomicInteger` | El campo usa `AtomicInteger` (thread-safe) |
| `initGuardaParametros` | `init()` lee correctamente los `init-param` de `ServletConfig` |
| `momentoInitSeEstableceEnInit` | `momentoInit` se fija durante `init()`, no antes |
| `FormularioServletTest#escaparHtml*` (×5) | `escaparHtml()` escapa `&`, `<`, `>`, `"` y deja texto limpio sin cambios |
