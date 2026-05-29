# 50 - Hibernate Clásico: ORM y Mapping

Módulo JAR que demuestra la capa de persistencia con Hibernate 3.x: configuración XML, mapeos `hbm.xml` y consultas HQL.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| **Java 8** | **Java 8 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

> **Java 8 es obligatorio.** Hibernate 3.6.x usa cglib y javassist para generar proxies de entidades mediante manipulación de bytecode. Estas técnicas son incompatibles con el sistema de módulos de Java 9+ y provocan `IllegalAccessError` o `ClassNotFoundException` en tiempo de ejecución.

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

## Contenido previsto

- Introducción a ORM y ventajas de Hibernate frente a JDBC directo.
- Configuración de Hibernate via `hibernate.cfg.xml`.
- Mapeo de entidades con `hbm.xml` y con anotaciones clásicas.
- `SessionFactory`, `Session` y gestión de transacciones.
- Ciclo de vida de objetos persistentes: `transient` → `persistent` → `detached`.
- Consultas con HQL (Hibernate Query Language).

## Scripts

```bash
./build.sh   # mvn clean package
./start.sh   # mvn test (ejecuta los demos de Hibernate)
./stop.sh    # módulo JAR, no tiene servidor
./test.sh    # mvn test
```
