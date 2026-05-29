# 40 - Struts 1.x: Acciones y Validaciones

Módulo web (WAR) que demuestra la arquitectura de Struts 1.x: flujo de peticiones, `ActionForm`, `Action` y validación clásica.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| **Java 8** | **Java 8 Temurin** | Java 8 (`release=8` en el POM raíz) |

> **Java 8 es obligatorio.** Struts 1.3.x usa reflexión y acceso a campos privados de formas que el sistema de módulos de Java 9+ (`--illegal-access`) bloquea o elimina. Con Java 11+ el servidor arranca pero pueden producirse errores en tiempo de ejecución al procesar formularios.

### Configurar Java 8 en IntelliJ IDEA

1. Instalar Java 8 Temurin con SDKMAN:
   ```bash
   sdk install java 8.0.412-tem
   sdk use java 8.0.412-tem
   ```
2. `File → Project Structure → Project Settings → Project`
   - `SDK` → seleccionar `java-8` (o añadirlo desde `~/.sdkman/candidates/java/8.0.412-tem`)
   - `Language level` → `8 - Lambdas, type annotations etc.`
3. `File → Settings → Build Tools → Maven → JDK for importer` → `java-8`
4. Ventana Maven → `Reload All Maven Projects`

> Para los pasos completos de importar el proyecto en IntelliJ, ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Contenido previsto

- Arquitectura y flujo de peticiones en Struts 1.x (`ActionServlet` → `Action` → JSP).
- Configuración central: `struts-config.xml` (form-beans y action-mappings).
- `ActionForm`: enlace automático de datos del formulario HTML.
- `Action`: implementación de la lógica de negocio y navegación.
- Validación clásica de Struts (método `validate()` en `ActionForm`).

## Configuración Struts

El `ActionServlet` está mapeado en `web.xml` con el patrón `*.do`.
Las acciones y formularios se registran en `src/main/webapp/WEB-INF/struts-config.xml`.

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8085
./stop.sh    # Para el proceso en el puerto 8085
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 40-struts1-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8085
./stop.sh    # fuser -k 8085/tcp
./test.sh    # mvn test
```
