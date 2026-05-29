# 10 - Ciclo de Vida del Servlet

Módulo web (WAR) que demuestra la arquitectura del contenedor Servlet y el ciclo de vida completo de un Servlet Java EE.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

Servlet API 4.0 y Tomcat 9 requieren Java 8 como mínimo y son compatibles hasta Java 17.

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Contenido previsto

- Arquitectura del contenedor Servlet (Tomcat).
- Métodos clave: `init()`, `service()` (`doGet` / `doPost`) y `destroy()`.
- Mapeo y configuración vía `web.xml` y anotaciones (`@WebServlet`).
- Manejo de `HttpServletRequest` y `HttpServletResponse`.

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8082
./stop.sh    # Para el proceso en el puerto 8082
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 10-servlet-lifecycle-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8082
./stop.sh    # fuser -k 8082/tcp
./test.sh    # mvn test
```
