# 20 — JSP y JSTL

Colección de páginas JSP que demuestran cada concepto de forma aislada.
Un único servlet (`DatosServlet`) proporciona datos a las páginas que los necesitan.

## Estructura

```
webapp/
├── index.html              ← portada con enlaces (/)
├── directivas.jsp          ← @page, @taglib, @include (incluye WEB-INF/_menu.jspf)
├── expresiones.jsp         ← <%! %>, <% %>, <%= %>
├── objetos-implicitos.jsp  ← request, session, application, out, config, page, pageContext
├── el.jsp                  ← ${param.x}, operadores, empty, ternario
├── jstl-core.jsp           ← c:out, c:set, c:if, c:choose, c:forEach, c:url
├── jstl-fmt.jsp            ← fmt:formatDate, fmt:formatNumber, fmt:setLocale
└── WEB-INF/
    ├── web.xml
    └── _menu.jspf          ← fragmento de navegación (incluido estáticamente)
```

## Flujo de navegación

| URL | Tipo | JSP destino |
|-----|------|-------------|
| `/` | HTML | index.html |
| `/directivas.jsp` | directo | directivas.jsp |
| `/expresiones.jsp` | directo | expresiones.jsp |
| `/objetos-implicitos.jsp` | directo | objetos-implicitos.jsp |
| `/el.jsp?nombre=Ana` | directo | el.jsp |
| `/datos` | Servlet → forward | jstl-core.jsp |
| `/datos?vista=fmt` | Servlet → forward | jstl-fmt.jsp |

## Arrancar

```bash
./start.sh        # http://localhost:8083
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Portada
curl -s http://localhost:8083/ | grep -o 'JSP y JSTL'

# directivas.jsp — @include activo (el menú está en la página)
curl -s http://localhost:8083/directivas.jsp | grep -o 'JSTL core'

# expresiones.jsp — scriptlets y expresiones
curl -s http://localhost:8083/expresiones.jsp | grep -o 'Scripting JSP'

# objetos-implicitos.jsp — muestra método GET
curl -s http://localhost:8083/objetos-implicitos.jsp | grep -o '>GET<'

# el.jsp sin params → operador ternario muestra "Anonimo"
curl -s http://localhost:8083/el.jsp | grep -o 'Anonimo'

# el.jsp con params → nombre=Ana visible en la página
curl -s "http://localhost:8083/el.jsp?nombre=Ana&edad=28" | grep -o '>Ana<'

# EL: 3 + 4 = 7
curl -s http://localhost:8083/el.jsp | grep -o '>7<'

# Servlet → jstl-core.jsp: c:forEach sobre lista de frutas
curl -s http://localhost:8083/datos | grep -o 'Manzana'

# Servlet → jstl-core.jsp: productos en tabla
curl -s http://localhost:8083/datos | grep -o 'Teclado'

# Servlet → jstl-fmt.jsp
curl -s "http://localhost:8083/datos?vista=fmt" | grep -o 'Biblioteca de formato'

# fmt:setLocale: tabla con distintos locales
curl -s "http://localhost:8083/datos?vista=fmt" | grep -o 'es_ES'
```
