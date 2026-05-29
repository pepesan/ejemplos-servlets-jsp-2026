# 30 - Patrón MVC Clásico (Front Controller)

Módulo web (WAR) que implementa el patrón Model-View-Controller usando un Servlet genérico como Front Controller.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

Servlet API 4.0 y JSTL 1.2 son compatibles con Java 8 y 11.

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Contenido previsto

- Definición y beneficios del patrón MVC.
- Implementación del Front Controller: un único Servlet que enruta todas las peticiones.
- Model beans: paso de datos entre la capa de control y la vista (JSP).
- Ejemplo práctico: aplicación web CRUD simple con Servlets + JSP + MVC.

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8084
./stop.sh    # Para el proceso en el puerto 8084
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 30-mvc-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8084
./stop.sh    # fuser -k 8084/tcp
./test.sh    # mvn test
```
