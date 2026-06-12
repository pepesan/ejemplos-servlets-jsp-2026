# 52 - Hibernate: Relaciones entre entidades

Módulo JAR que demuestra los tres tipos de relación JPA con sus dos variantes (unidireccional y bidireccional), organizados en subpaquetes para facilitar la comparación.

---

## Estructura de paquetes

```
com.cursosdedesarrollo/
├── unoauno/          ← @OneToOne
├── unoamuchos/       ← @OneToMany / @ManyToOne
└── muchosamuchos/    ← @ManyToMany
```

---

## OneToOne (`unoauno`)

| Clase | Rol | Estrategia |
|-------|-----|-----------|
| `Persona` | Dueño (FK `pasaporte_id`) | Unidireccional |
| `Pasaporte` | Pasivo (no conoce a Persona) | Unidireccional |
| `Usuario` | Dueño (FK `perfil_id`) | Bidireccional |
| `Perfil` | Inverso (`mappedBy="perfil"`) | Bidireccional |

**Unidireccional**: solo `Persona` conoce a `Pasaporte`. No hay forma de navegar `Pasaporte → Persona` sin HQL.  
**Bidireccional**: `Usuario ↔ Perfil`. `setPerfil()` mantiene la coherencia en ambos lados automáticamente.

---

## OneToMany / ManyToOne (`unoamuchos`)

| Clase | Rol | Estrategia |
|-------|-----|-----------|
| `Categoria` | Dueño `@OneToMany` + `@JoinColumn` | Unidireccional |
| `Articulo` | Pasivo (sin `@ManyToOne`) | Unidireccional |
| `Departamento` | Inverso (`mappedBy="departamento"`) | Bidireccional |
| `Empleado` | Dueño (FK `departamento_id`) | Bidireccional |

**Unidireccional con `@JoinColumn`**: la FK `categoria_id` existe en la tabla `articulos` pero `Articulo` no la expone. Sin `@JoinColumn` Hibernate crearía una tabla de unión innecesaria.  
**Bidireccional**: `Empleado` tiene `@ManyToOne`; `Departamento` tiene `@OneToMany(mappedBy=…)`.

---

## ManyToMany (`muchosamuchos`)

| Clase | Rol | Estrategia |
|-------|-----|-----------|
| `Producto` | Dueño (`@JoinTable productos_etiquetas`) | Unidireccional |
| `Etiqueta` | Pasivo (sin referencia a Producto) | Unidireccional |
| `Pelicula` | Dueño (`@JoinTable actores_peliculas`) | Bidireccional |
| `Actor` | Inverso (`mappedBy="actores"`) | Bidireccional |

---

## Scripts

```bash
./build.sh   # mvn clean package
./test.sh    # mvn test (21 tests)
./start.sh   # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 52-hibernate-relaciones
```

| Test | Cubre |
|------|-------|
| `OneToOneTest` | Persona+Pasaporte (uni) y Usuario+Perfil (bi) |
| `OneToManyTest` | Categoria+Articulo (uni) y Departamento+Empleado (bi) |
| `ManyToOneTest` | Departamento+Empleado (navegación desde el lado dueño) |
| `ManyToManyTest` | Producto+Etiqueta (uni) y Pelicula+Actor (bi) |
