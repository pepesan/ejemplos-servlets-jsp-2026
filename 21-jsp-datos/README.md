# 21 — JSP: Paso de datos Servlet → JSP

Demuestra el patrón central de MVC clásico: el servlet deposita datos en el
`request` con `setAttribute()` y hace `forward` a la JSP, que los lee con EL.

## Estructura

```
webapp/
├── index.html       ← portada con enlaces (/)
├── individual.jsp   ← tipos primitivos: String, int, double, boolean, Date, null
├── lista.jsp        ← List<String>, List<Integer>, List<Object> con c:forEach
├── bean.jsp         ← Producto y Alumno: propiedades simples y calculadas
└── WEB-INF/web.xml

src/main/java/
├── DatosServlet.java  ← único servlet, rutas ?vista=individual|lista|bean
├── Producto.java      ← JavaBean con id, nombre, precio, categoría, disponible
└── Alumno.java        ← JavaBean con nombre, apellidos, nota + isAprobado() calculado
```

## Flujo

```
GET /datos?vista=individual  →  cargarIndividual(req)  →  forward  →  individual.jsp
GET /datos?vista=lista       →  cargarLista(req)       →  forward  →  lista.jsp
GET /datos?vista=bean        →  cargarBean(req)        →  forward  →  bean.jsp
```

## Conceptos clave

| Vista | Qué demuestra |
|-------|---------------|
| `individual` | `req.setAttribute()` con String/int/double/boolean/Date/null; EL lo convierte sin cast |
| `lista` | `List<T>` iterada con `c:forEach`; acceso por índice `${lista[0]}`; `varStatus`; acumulador con `c:set` |
| `bean` | EL llama getters: `${p.nombre}` → `p.getNombre()`; getters calculados (`isAprobado()`); filtrado en JSP con `c:if` |

## Arrancar

```bash
./start.sh        # http://localhost:8021
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Portada
curl -s http://localhost:8021/ | grep -o 'Paso de datos'

# individual — texto String del servlet
curl -s "http://localhost:8021/datos?vista=individual" | grep -o 'Hola desde el servlet'

# individual — entero 42 en tabla
curl -s "http://localhost:8021/datos?vista=individual" | grep -o '>42<'

# individual — null con c:out default
curl -s "http://localhost:8021/datos?vista=individual" | grep -o 'sin dato'

# lista — tamaño de colores (5)
curl -s "http://localhost:8021/datos?vista=lista" | grep -o '>5<' | head -1

# lista — varStatus.first = true
curl -s "http://localhost:8021/datos?vista=lista" | grep -o '>true<' | head -1

# lista — suma acumulada de nums (10+20+30+40+50 = 150)
curl -s "http://localhost:8021/datos?vista=lista" | grep -o '>150<'

# bean — nombre del producto en tabla
curl -s "http://localhost:8021/datos?vista=bean" | grep -o 'Teclado' | head -1

# bean — nombreCompleto (getter calculado)
curl -s "http://localhost:8021/datos?vista=bean" | grep -o 'Ana García López' | head -1

# bean — isAprobado() → "Suspenso" aparece (Luis 4.8)
curl -s "http://localhost:8021/datos?vista=bean" | grep -o 'Suspenso' | head -1

# bean — filtrado c:if disponible → Ratón visible
curl -s "http://localhost:8021/datos?vista=bean" | grep -o 'Ratón inalámbrico' | head -1
```
