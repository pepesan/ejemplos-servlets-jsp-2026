# 54 - Hibernate: Estrategias de herencia

Módulo JAR dedicado a las cuatro formas de mapear jerarquías de clases en Hibernate/JPA, organizadas en subpaquetes independientes para compararlas lado a lado.

---

## Estructura de paquetes

```
com.cursosdedesarrollo/
├── singletable/      ← InheritanceType.SINGLE_TABLE
├── joined/           ← InheritanceType.JOINED
├── tableperclass/    ← InheritanceType.TABLE_PER_CLASS
└── mappedsuperclass/ ← @MappedSuperclass (no es herencia JPA real)
```

---

## SINGLE_TABLE (`singletable`)

Una sola tabla con columna discriminadora. Es la estrategia por defecto de Hibernate.

```
tabla: personas
  id      BIGINT PK
  tipo    VARCHAR(3)   ← "EMP" o "CLI"  (gestionado por Hibernate)
  nombre  VARCHAR NOT NULL
  salario DOUBLE        ← nullable (solo Empleado lo usa)
  empresa VARCHAR       ← nullable (solo Cliente lo usa)
```

| Clase | Discriminador | Campos propios |
|-------|--------------|----------------|
| `Persona` (abstracta) | — | `id`, `nombre` |
| `Empleado` | `"EMP"` | `salario` |
| `Cliente` | `"CLI"` | `empresa` |

**Ventaja**: sin JOINs, rendimiento máximo en lecturas.  
**Desventaja**: columnas de subclases no pueden ser `NOT NULL` (viola 3FN).

---

## JOINED (`joined`)

Tabla base + tabla propia por subclase, unidas por FK. Esquema normalizado.

```
tabla: vehiculos        → id, marca
tabla: coches           → id (FK→vehiculos), puertas NOT NULL
tabla: motos            → id (FK→vehiculos), cilindrada NOT NULL
```

| Clase | Tabla | Campos propios |
|-------|-------|----------------|
| `Vehiculo` (abstracta) | `vehiculos` | `id`, `marca` |
| `Coche` | `coches` | `puertas NOT NULL` |
| `Moto` | `motos` | `cilindrada NOT NULL` |

**Ventaja**: esquema normalizado; columnas de subclase pueden ser `NOT NULL`.  
**Desventaja**: cada consulta polimórfica hace JOINs contra todas las tablas de subclase.

---

## TABLE_PER_CLASS (`tableperclass`)

Cada subclase tiene su propia tabla completa. No existe tabla base.

```
tabla: cuentas_corriente → id, saldo, descubierto_max NOT NULL
tabla: cuentas_ahorro    → id, saldo, tasa_interes NOT NULL
(no existe tabla "cuentas")
```

| Clase | Tabla | Campos propios |
|-------|-------|----------------|
| `Cuenta` (abstracta) | — (sin tabla) | `id`, `saldo` |
| `CuentaCorriente` | `cuentas_corriente` | `descubierto_max NOT NULL` |
| `CuentaAhorro` | `cuentas_ahorro` | `tasa_interes NOT NULL` |

> **IMPORTANTE**: se usa `@GeneratedValue(GenerationType.TABLE)` porque `IDENTITY`
> generaría IDs duplicados entre tablas. Las consultas polimórficas (`from Cuenta`)
> se traducen a `SELECT … UNION ALL SELECT …`.

**Ventaja**: sin JOINs; columnas propias pueden ser `NOT NULL`.  
**Desventaja**: columnas de la superclase se repiten en cada tabla; `UNION ALL` en consultas polimórficas.

---

## @MappedSuperclass (`mappedsuperclass`)

**No es una estrategia de herencia JPA.** Solo hereda el mapeo, no la entidad.

```
tabla: pedidos   → id, total,  creado_en, modificado_en
tabla: facturas  → id, numero, cliente, creado_en, modificado_en
(no existe tabla "entidades_auditables")
```

| Clase | Tipo | Descripción |
|-------|------|-------------|
| `EntidadAuditable` | `@MappedSuperclass` | Campos `creadoEn`/`modificadoEn` con `@PrePersist`/`@PreUpdate` |
| `Pedido` | `@Entity` | Hereda los campos de auditoría |
| `Factura` | `@Entity` | Hereda los campos de auditoría |

- `EntidadAuditable` **no se registra** en `hibernate.cfg.xml`.
- `from EntidadAuditable` en HQL lanzaría `HibernateException` (no es `@Entity`).
- Caso de uso típico: campos de auditoría, soft-delete, versionado.

---

## Comparativa de estrategias

| Criterio | SINGLE_TABLE | JOINED | TABLE_PER_CLASS | @MappedSuperclass |
|----------|:---:|:---:|:---:|:---:|
| Tablas creadas | 1 | 1 + N subclases | N subclases | N subclases |
| JOINs en SELECT | No | Sí | No (UNION ALL) | No |
| NOT NULL en subclase | No | Sí | Sí | Sí |
| Consulta polimórfica | Muy rápida | JOIN | UNION ALL | Imposible |
| Normalización | Baja | Alta | Media | Alta |
| Cuándo usarlo | Jerarquías simples, rendimiento | Esquemas complejos, integridad | Pocas subclases sin polimorfismo | Campos comunes sin jerarquía |

---

## Scripts

```bash
./build.sh   # mvn clean package
./test.sh    # mvn test (15 tests)
./start.sh   # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 54-hibernate-herencia
```

| Test | Estrategia | Tests |
|------|-----------|-------|
| `HerenciaSingleTableTest` | SINGLE_TABLE | 4 |
| `HerenciaJoinedTest` | JOINED | 4 |
| `HerenciaTablePerClassTest` | TABLE_PER_CLASS | 3 |
| `HerenciaMappedSuperclassTest` | @MappedSuperclass | 3 |
