# 10 - Servlet configurado por web.xml

Primer contacto con la tecnología Servlet: configuración clásica mediante `web.xml`, sin anotaciones.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

> Para configurar el JDK en IntelliJ IDEA ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Java EE / J2EE — visión general

**J2EE** (Java 2 Platform, Enterprise Edition) fue el nombre de la plataforma empresarial de Java hasta la versión 1.4. A partir de Java EE 5 (2006) pasó a llamarse **Java EE**, y desde 2017, bajo la tutela de la Eclipse Foundation, se denomina **Jakarta EE**.

### ¿Qué incluye?

Java EE es un conjunto de especificaciones (APIs) que los servidores de aplicaciones implementan. Las más relevantes para este curso:

| Especificación | Descripción |
|---|---|
| **Servlet** | Componente Java que recibe peticiones HTTP y genera respuestas. Base de toda aplicación web Java EE. |
| **JSP** (JavaServer Pages) | Páginas con HTML + código Java embebido. Se compilan a Servlets en el primer acceso. |
| **JSTL** | Librería de etiquetas estándar para JSP: bucles, condicionales, formateo. Elimina scriptlets. |
| **Filter** | Interceptor de peticiones/respuestas. Se ejecuta antes y/o después del Servlet (autenticación, logging, compresión…). |
| **Listener** | Observador del ciclo de vida de la aplicación, sesión o petición (`ServletContextListener`, `HttpSessionListener`…). |
| **EJB** | Enterprise JavaBeans. Componentes de negocio con transacciones y seguridad gestionadas por el contenedor. |
| **JPA** | Java Persistence API. ORM estándar (Hibernate es la implementación de referencia). |
| **JTA** | Java Transaction API. Gestión de transacciones distribuidas. |
| **JAX-RS** | API REST. |
| **JSF** | JavaServer Faces. Framework MVC basado en componentes UI. |
| **CDI** | Contexts and Dependency Injection. Inyección de dependencias estándar. |

### Contenedores

| Tipo | Implementa | Ejemplos |
|------|-----------|---------|
| **Contenedor de Servlets** | Solo Servlet + JSP + Filter | Tomcat, Jetty |
| **Servidor de aplicaciones Java EE completo** | Todo el stack Java EE | JBoss/WildFly, WebLogic, WebSphere, GlassFish |

> En este curso usamos **Tomcat** (contenedor de Servlets), suficiente para Servlet, JSP, JSTL y Struts.

### Evolución de versiones

| Plataforma | Año | Servlet | JSP |
|-----------|-----|---------|-----|
| J2EE 1.3 | 2001 | 2.3 | 1.2 |
| J2EE 1.4 | 2003 | 2.4 | 2.0 |
| Java EE 5 | 2006 | 2.5 | 2.1 |
| Java EE 6 | 2009 | 3.0 | 2.2 — aparecen `@WebServlet` |
| Java EE 7 | 2013 | 3.1 | 2.3 |
| Java EE 8 | 2017 | 4.0 | 2.3 — última versión `javax.*` |
| Jakarta EE 9 | 2020 | 5.0 | 3.0 — renombrado a `jakarta.*` |

> Este módulo usa **Servlet 4.0** (`javax.servlet`), la última versión del namespace `javax`. Los proyectos legacy que debemos mantener usan versiones anteriores.

---

## Ejemplo — HolaMundoXmlServlet

Servlet sin ninguna anotación. El mapeo se declara íntegramente en `WEB-INF/web.xml`.

**Clase** (`HolaMundoXmlServlet.java`):
```java
public class HolaMundoXmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("Hola, Mundo (configurado por web.xml)");
    }
}
```

**Registro en `web.xml`**:
```xml
<servlet>
    <servlet-name>holaMundoXml</servlet-name>
    <servlet-class>com.cursosdedesarrollo.HolaMundoXmlServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>holaMundoXml</servlet-name>
    <url-pattern>/hola-xml</url-pattern>
</servlet-mapping>
```

---

## Arranque y pruebas

```bash
./start.sh                            # Tomcat en http://localhost:8010
curl http://localhost:8010/hola-xml   # → Hola, Mundo (configurado por web.xml)
curl -o /dev/null -w "%{http_code}" http://localhost:8010/  # → 200
./stop.sh
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 10-servlet-xml-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8010
./stop.sh    # fuser -k 8010/tcp
./test.sh    # mvn test
```
