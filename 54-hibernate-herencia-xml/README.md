# 54B — Hibernate: Herencia con mapeo XML (hbm.xml)

Mismo contenido que `54-hibernate-herencia-anotaciones` pero con mapeo **puramente XML**: sin ninguna anotación JPA en las entidades. Cada jerarquía tiene su fichero `.hbm.xml` que declara la estrategia y las subclases.

## Objetivo

Comparar los dos enfoques de mapeo de herencia en Hibernate 3.x:

| Característica | `54-herencia-anotaciones` | `54-herencia-xml` (este) |
|----------------|--------------------------|--------------------------|
| Mapeo | Anotaciones JPA (`@Inheritance`, `@DiscriminatorColumn`…) | Ficheros `*.hbm.xml` |
| `HibernateUtil` | `AnnotationConfiguration` | `Configuration` |
| Dependencia extra | `hibernate-annotations` | ninguna |
| `hibernate.cfg.xml` | `<mapping class="..."/>` | `<mapping resource="..."/>` |
| `@MappedSuperclass` | Automático | No existe: campos repetidos en cada entidad |

## Estrategias cubiertas

### SINGLE_TABLE — `singletable/`

| Clase | Rol |
|-------|-----|
| `Persona` | Raíz abstracta, tabla `personas` |
| `Empleado` | Subclase, discriminator `"EMP"` |
| `Cliente` | Subclase, discriminator `"CLI"` |

**Fichero HBM:** `Persona.hbm.xml` — contiene toda la jerarquía con `<subclass discriminator-value="...">`.  
**Equivalencias:** `@DiscriminatorColumn` → `<discriminator column="..."/>` · `@DiscriminatorValue` → `discriminator-value` en `<subclass>`.

### JOINED — `joined/`

| Clase | Tabla |
|-------|-------|
| `Vehiculo` | `vehiculos` (raíz abstracta) |
| `Coche` | `coches` (FK → `vehiculos.id`) |
| `Moto` | `motos` (FK → `vehiculos.id`) |

**Fichero HBM:** `Vehiculo.hbm.xml` — contiene toda la jerarquía con `<joined-subclass table="..." ><key column="id"/></joined-subclass>`.

### TABLE_PER_CLASS — `tableperclass/`

| Clase | Tabla |
|-------|-------|
| `Cuenta` | *ninguna* (abstracta) |
| `CuentaCorriente` | `cuentas_corriente` |
| `CuentaAhorro` | `cuentas_ahorro` |

**Fichero HBM:** `Cuenta.hbm.xml` — contiene toda la jerarquía con `<union-subclass>`.  
**Generator:** `sequence` (secuencia compartida `cuenta_seq`) para garantizar IDs únicos en UNION ALL. No se puede usar `identity` porque dos tablas con secuencias independientes producirían IDs duplicados.

### @MappedSuperclass equivalente — `mappedsuperclass/`

| Clase | Rol |
|-------|-----|
| `EntidadAuditable` | Clase Java abstracta pura — **sin fichero HBM** |
| `Factura` | Entidad concreta, tabla `facturas` |
| `Pedido` | Entidad concreta, tabla `pedidos` |

**Diferencia clave vs. anotaciones:** HBM.xml **no tiene equivalente a `@MappedSuperclass`**. Los campos `creado_en` y `modificado_en` se mapean manualmente en `Factura.hbm.xml` y `Pedido.hbm.xml`. La herencia Java funciona igual, pero el mapeo debe repetirse.

## Equivalencias anotación → XML

| Anotación JPA | Elemento HBM.xml |
|---------------|-----------------|
| `@Inheritance(SINGLE_TABLE)` | `<class>` con `<discriminator>` + `<subclass>` |
| `@DiscriminatorColumn(name="tipo")` | `<discriminator column="tipo" type="string"/>` |
| `@DiscriminatorValue("EMP")` | `discriminator-value="EMP"` en `<subclass>` |
| `@Inheritance(JOINED)` | `<class>` con `<joined-subclass><key column="id"/></joined-subclass>` |
| `@Table(name="coches")` | `table="coches"` en `<joined-subclass>` |
| `@Inheritance(TABLE_PER_CLASS)` | `<class abstract="true">` con `<union-subclass>` |
| `GenerationType.TABLE` | `generator class="sequence"` (secuencia compartida) |
| `@MappedSuperclass` | *(sin equivalente)* — repetir campos en cada entidad |
| `@Column(updatable=false)` | `update="false"` en `<property>` |
| `@Temporal(TIMESTAMP)` | `type="timestamp"` en `<property>` |

## Compilar y testear

```bash
./build.sh   # compila el módulo
./test.sh    # ejecuta los 11 tests (4 clases de test)
```

O con Maven directamente (desde la raíz):

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" \
  mvn test -pl 54-hibernate-herencia-xml
```
