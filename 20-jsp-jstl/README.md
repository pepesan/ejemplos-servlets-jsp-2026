# 20 - JSP y JSTL

Módulo web (WAR) que demuestra el uso de JavaServer Pages y la librería estándar de etiquetas JSTL.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

JSP 2.3 y JSTL 1.2 son compatibles con Java 8 y 11. Tomcat 9 soporta hasta Java 17.

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Contenido previsto

- Elementos de una JSP: directives (`<%@ %>`), declarations (`<%! %>`), scriptlets (`<% %>`).
- Objetos implícitos: `request`, `session`, `application`, `out`.
- JSTL: etiquetas `<c:forEach>`, `<c:if>`, `<c:choose>`, `<fmt:*>`.
- Inclusión (`<jsp:include>`) y reenvío (`<jsp:forward>`) de páginas.

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8083
./stop.sh    # Para el proceso en el puerto 8083
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 20-jsp-jstl-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8083
./stop.sh    # fuser -k 8083/tcp
./test.sh    # mvn test
```
