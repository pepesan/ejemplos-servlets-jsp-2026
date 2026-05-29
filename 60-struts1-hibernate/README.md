# 60 - Struts 1.x + Hibernate Clásico

Módulo web (WAR) que integra Struts 1.x como capa web y Hibernate 3.x como capa de persistencia. Representa el stack completo de una aplicación Java EE legacy.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| **Java 8** | **Java 8 Temurin** | Java 8 (`release=8` en el POM raíz) |

> **Java 8 es obligatorio.** Este módulo combina Struts 1.3.x y Hibernate 3.6.x, ambos incompatibles con el sistema de módulos de Java 9+. Struts usa reflexión sobre campos privados; Hibernate usa cglib/javassist para proxies de entidades. Con Java 11+ ambas capas pueden fallar en tiempo de ejecución.

### Configurar Java 8 en IntelliJ IDEA

1. Instalar Java 8 Temurin con SDKMAN:
   ```bash
   sdk install java 8.0.412-tem
   sdk use java 8.0.412-tem
   ```
2. `File → Project Structure → Project Settings → Project`
   - `SDK` → `java-8`
   - `Language level` → `8 - Lambdas, type annotations etc.`
3. `File → Settings → Build Tools → Maven → JDK for importer` → `java-8`
4. Ventana Maven → `Reload All Maven Projects`

> Para los pasos completos de importar el proyecto en IntelliJ, ver [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Stack

| Capa | Tecnología |
|------|------------|
| Web / Controlador | Struts 1.3.10 (`ActionServlet`, `Action`, `ActionForm`) |
| Vista | JSP + Struts Taglib |
| Persistencia | Hibernate 3.6.10 (`Session`, `Transaction`, HQL) |
| Base de datos | H2 en memoria (`jdbc:h2:mem:cursodb`) |

## Arquitectura del flujo

```
Navegador → *.do → ActionServlet → Action → (Hibernate) → BD
                                     ↓
                                   JSP (respuesta)
```

## Configuración

| Fichero | Propósito |
|---------|-----------|
| `WEB-INF/web.xml` | Registra el `ActionServlet` con patrón `*.do` |
| `WEB-INF/struts-config.xml` | Define `form-beans` y `action-mappings` |
| `src/main/resources/hibernate.cfg.xml` | Conexión H2, dialecto, `hbm2ddl.auto=create-drop` |

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8086
./stop.sh    # Para el proceso en el puerto 8086
```

## Scripts

```bash
./build.sh   # mvn clean package → genera 60-struts1-hibernate-1.0-SNAPSHOT.war
./start.sh   # mvn tomcat7:run → http://localhost:8086
./stop.sh    # fuser -k 8086/tcp
./test.sh    # mvn test
```
