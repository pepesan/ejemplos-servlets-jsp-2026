# 53 - Hibernate: HQL, Criteria y Herencia

Módulo JAR que cubre consultas avanzadas con Hibernate Query Language (HQL), la Criteria API y una introducción a las tres estrategias de herencia JPA.

---

## Contenido

### Entidades para HQL y Criteria

| Clase | Descripción |
|-------|-------------|
| `Producto` | Entidad con nombre, categoría, precio, stock. Base de los ejemplos HQL |

### Herencia SINGLE_TABLE

| Clase | Descripción |
|-------|-------------|
| `Persona` | Raíz abstracta. Tabla `personas` con columna discriminadora `tipo` |
| `EmpleadoST` | Subclase `tipo=EMP`, columna `salario` |
| `Cliente` | Subclase `tipo=CLI`, columna `empresa` |

### Herencia JOINED

| Clase | Descripción |
|-------|-------------|
| `Vehiculo` | Raíz abstracta. Tabla `vehiculos` |
| `Coche` | Tabla `coches` con FK → `vehiculos.id`, columna `puertas` |
| `Moto` | Tabla `motos` con FK → `vehiculos.id`, columna `cilindrada` |

### Herencia TABLE_PER_CLASS

| Clase | Descripción |
|-------|-------------|
| `Pago` | Raíz abstracta. Sin tabla propia. IDs con `GenerationType.TABLE` |
| `PagoTarjeta` | Tabla `pagos_tarjeta` con todas las columnas de `Pago` + `numero_tarjeta` |
| `PagoEfectivo` | Tabla `pagos_efectivo` con todas las columnas de `Pago` + `moneda` |

> Para un ejemplo dedicado y más detallado de herencia, ver [54-hibernate-herencia](../54-hibernate-herencia/README.md).

---

## HQL demostrado

```
from Producto                                        -- consulta simple
from Producto where precio > :min                   -- parámetro nombrado
from Producto where categoria = :cat order by precio -- ordenación
select count(p) from Producto p                     -- agregación
from Producto p where p.nombre like :patron         -- LIKE
```

---

## Scripts

```bash
./build.sh   # mvn clean package
./test.sh    # mvn test
./start.sh   # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 53-hibernate-hql
```

| Test | Cubre |
|------|-------|
| `HqlCriteriaTest` | Consultas HQL con parámetros, LIKE, ORDER BY, COUNT; Criteria API con Restrictions |
| `HerenciaSingleTableTest` | Una tabla, columna discriminadora, consultas polimórficas |
| `HerenciaJoinedTest` | Tabla base + tabla por subclase con FK, JOINs automáticos |
| `HerenciaTablePerClassTest` | Tabla completa por subclase, UNION ALL en consultas polimórficas |
