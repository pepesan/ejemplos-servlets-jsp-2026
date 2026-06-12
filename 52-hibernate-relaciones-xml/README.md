# 52B — Hibernate: Relaciones con mapeo XML (hbm.xml)

Mismo contenido que el módulo `52-hibernate-relaciones-anotaciones` pero con mapeo **puramente XML**: sin ninguna anotación JPA en las entidades. Cada clase tiene su propio fichero `.hbm.xml` que define la tabla, columnas y relaciones.

## Objetivo

Comparar los dos enfoques de mapeo de relaciones en Hibernate 3.x:

| Característica | `52-relaciones-anotaciones` | `52-relaciones-xml` (este) |
|----------------|----------------------------|---------------------------|
| Mapeo | Anotaciones JPA (`@Entity`, `@OneToOne`…) | Ficheros `*.hbm.xml` |
| `HibernateUtil` | `AnnotationConfiguration` | `Configuration` |
| Dependencia extra | `hibernate-annotations` | ninguna |
| Entidades | Con `import javax.persistence.*` | POJOs puros, sin imports JPA |
| `hibernate.cfg.xml` | `<mapping class="..."/>` | `<mapping resource="..."/>` |

## Relaciones cubiertas

### OneToOne

| Variante | Clases | Tabla con FK |
|----------|--------|-------------|
| Unidireccional | `Persona → Pasaporte` | `personas.pasaporte_id` |
| Bidireccional | `Usuario ↔ Perfil` | `usuarios.perfil_id` |

**XML clave:** `<many-to-one unique="true">` representa `@OneToOne @JoinColumn` (lado dueño). `<one-to-one property-ref="perfil">` representa `@OneToOne(mappedBy="perfil")` (lado inverso).

### OneToMany

| Variante | Clases | FK |
|----------|--------|-----|
| Unidireccional | `Categoria → Articulo` | `articulos.categoria_id` |
| Bidireccional | `Departamento ↔ Empleado` | `empleados.departamento_id` |

**XML clave:** `<bag cascade="all,delete-orphan">` (sin `inverse`) para el lado dueño unidireccional. `<bag inverse="true">` para el lado inverso del bidireccional.

### ManyToMany

| Variante | Clases | Tabla de unión |
|----------|--------|----------------|
| Unidireccional | `Producto → Etiqueta` | `productos_etiquetas` |
| Bidireccional | `Pelicula ↔ Actor` | `actores_peliculas` |

**XML clave:** `<bag table="..."><many-to-many class="..."/></bag>`. El lado inverso del bidireccional lleva `inverse="true"`.

## Estructura del módulo

```
52-hibernate-relaciones-xml/
├── pom.xml
├── src/main/java/com/cursosdedesarrollo/
│   ├── HibernateUtil.java                  ← Configuration (no AnnotationConfiguration)
│   ├── unoauno/     Pasaporte, Persona, Perfil, Usuario
│   ├── unoamuchos/  Articulo, Categoria, Empleado, Departamento
│   └── muchosamuchos/ Etiqueta, Producto, Actor, Pelicula
├── src/main/resources/
│   ├── hibernate.cfg.xml                   ← <mapping resource="..."/>
│   └── com/cursosdedesarrollo/
│       ├── unoauno/     *.hbm.xml (4 ficheros)
│       ├── unoamuchos/  *.hbm.xml (4 ficheros)
│       └── muchosamuchos/ *.hbm.xml (4 ficheros)
└── src/test/java/com/cursosdedesarrollo/
    ├── OneToOneTest.java   (6 tests)
    ├── OneToManyTest.java  (6 tests)
    ├── ManyToOneTest.java  (3 tests)
    └── ManyToManyTest.java (6 tests)
```

## Equivalencias anotación → XML

| Anotación JPA | Elemento HBM.xml |
|---------------|-----------------|
| `@Entity @Table(name="t")` | `<class name="C" table="t">` |
| `@Id @GeneratedValue(IDENTITY)` | `<id name="id"><generator class="identity"/></id>` |
| `@Column(nullable=false, length=100)` | `<property name="x" not-null="true" length="100"/>` |
| `@OneToOne @JoinColumn(name="c")` | `<many-to-one unique="true" column="c"/>` |
| `@OneToOne(mappedBy="x")` | `<one-to-one property-ref="x"/>` |
| `@OneToMany @JoinColumn` (uni) | `<bag inverse="false"><key column="..."/><one-to-many/></bag>` |
| `@OneToMany(mappedBy="x")` | `<bag inverse="true"><key column="..."/><one-to-many/></bag>` |
| `@ManyToOne` | `<many-to-one column="..."/>` |
| `@ManyToMany @JoinTable` (dueño) | `<bag table="..."><key/><many-to-many/></bag>` |
| `@ManyToMany(mappedBy="x")` | `<bag table="..." inverse="true"><key/><many-to-many/></bag>` |
| `cascade=ALL` | `cascade="all"` |
| `orphanRemoval=true` | `cascade="all,delete-orphan"` |
| `fetch=LAZY` | `fetch="select"` o `lazy="true"` |

## Compilar y testear

```bash
./build.sh   # compila el módulo
./test.sh    # ejecuta los 21 tests
```

O con Maven directamente (desde la raíz del proyecto):

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" \
  mvn test -pl 52-hibernate-relaciones-xml
```

> Requiere `MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED"` con JDK 9+.
> Con JDK 8 no hace falta. Ver [README raíz](../README.md) para más detalles.
