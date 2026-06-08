# 51 - Hibernate: Mapeo con anotaciones JPA

Módulo JAR que sustituye los ficheros `hbm.xml` del módulo 50 por anotaciones JPA directamente en la clase Java: `@Entity`, `@Id`, `@GeneratedValue`, `@Column`, etc.

---

## Contenido

| Clase | Descripción |
|-------|-------------|
| `Empleado` | Entidad mapeada con anotaciones JPA (`@Entity`, `@Table`, `@Column`) |
| `EmpleadoDao` | DAO con operaciones CRUD usando `Session` |
| `HibernateUtil` | Factoría de `SessionFactory` via `AnnotationConfiguration` |

### Anotaciones demostradas

| Anotación | Uso |
|-----------|-----|
| `@Entity` | Marca la clase como entidad persistente |
| `@Table(name="…")` | Nombre de tabla explícito |
| `@Id` | Clave primaria |
| `@GeneratedValue(IDENTITY)` | Auto-incremento en BD |
| `@Column(nullable, length, unique)` | Restricciones de columna |

### Diferencia con el módulo 50

| Aspecto | 50 (hbm.xml) | 51 (anotaciones) |
|---------|-------------|------------------|
| Mapeo | Fichero XML separado | Directamente en la clase Java |
| Cohesión | Baja (clase + XML) | Alta (todo en la clase) |
| Estándar | Hibernate propietario | JPA estándar |

---

## Scripts

```bash
./build.sh   # mvn clean package
./test.sh    # mvn test
./start.sh   # mvn test (módulo JAR, ejecuta los tests)
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 51-hibernate-anotaciones
```

> `--add-opens` es necesario porque Hibernate 3.6 usa Javassist para generar proxies
> mediante manipulación de bytecode, incompatible con el sistema de módulos de Java 9+.
