# 11 - Servlet configurado por anotaciones

Servlet mapeado con `@WebServlet`, sin ninguna entrada en `web.xml`. Forma estándar desde Servlet 3.0 (Java EE 6).

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

> Para configurar el JDK en IntelliJ IDEA ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Ejemplo

```java
@WebServlet("/hola")
public class HolaMundoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("Hola, Mundo");
    }
}
```

`@WebServlet("/hola")` equivale a declarar en `web.xml`:

```xml
<servlet>
    <servlet-name>holaMundo</servlet-name>
    <servlet-class>com.cursosdedesarrollo.HolaMundoServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>holaMundo</servlet-name>
    <url-pattern>/hola</url-pattern>
</servlet-mapping>
```

## Arranque y pruebas

```bash
./start.sh                      # Tomcat en http://localhost:8011
curl http://localhost:8011/hola  # → Hola, Mundo
./stop.sh
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 11-servlet-anotaciones-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8011
./stop.sh    # fuser -k 8011/tcp
./test.sh    # mvn test
```
