# 18 – Servlet: Patrones de URL

Módulo WAR (puerto **8018**) que demuestra los cuatro tipos de patrón de URL
definidos por la especificación Servlet (§12.2) y el orden de prioridad en que
el contenedor los resuelve.

## Patrones demostrados

| Prioridad | Tipo | Patrón | Servlet |
|-----------|------|--------|---------|
| 1 | Coincidencia exacta | `/exacto` | `ExactoServlet` |
| 2 | Prefijo wildcard | `/catalogo/*` | `CatalogoServlet` |
| 3 | Extensión | `*.do` | `ExtensionServlet` |
| 4 | Por defecto | `/` | `DefaultServlet` |
| — | Referencia | `/patrones` | `ResumenServlet` |

## Arrancar

```bash
./18-servlet-patrones/start.sh
# o:
mvn tomcat7:run -pl 18-servlet-patrones
```

## URLs de prueba

```bash
# Página de inicio (DefaultServlet – patrón /)
curl http://localhost:8018/

# 1. Coincidencia exacta
curl http://localhost:8018/exacto
curl http://localhost:8018/exacto/    # → DefaultServlet (no hay coincidencia)

# 2. Prefijo wildcard
curl http://localhost:8018/catalogo/
curl http://localhost:8018/catalogo/1
curl http://localhost:8018/catalogo/1/editar

# 3. Extensión
curl http://localhost:8018/listar.do
curl http://localhost:8018/pedido/guardar.do
curl http://localhost:8018/admin/usuario/crear.do

# 4. DefaultServlet (nada de lo anterior coincide)
curl http://localhost:8018/no-existe
curl http://localhost:8018/imagen.png

# Tabla de referencia
curl http://localhost:8018/patrones
```

## Algoritmo de resolución (Servlet Spec §12.2)

1. **Exacto**: ¿hay un `url-pattern` igual a la URI?
2. **Prefijo más largo**: ¿hay algún `/xxx/*` cuyo prefijo sea prefijo de la URI? Gana el más largo.
3. **Extensión**: ¿hay un `*.ext` que coincida con la extensión final?
4. **Por defecto**: servlet con `url-pattern=/`.

## Puntos clave

- `getServletPath()` devuelve la parte de la URI que coincidió con el patrón.
- `getPathInfo()` devuelve la sub-ruta variable **solo** en prefijos wildcard; es `null` en exacto, extensión y default.
- El patrón `/*` captura todo (incluido `.do`, JSP y recursos estáticos), desplazando incluso al servlet por defecto. Úsalo con precaución.
- No se puede combinar prefijo y extensión: `/*.do` es inválido en la spec.
- Declarar un servlet con `/` reemplaza al servlet interno de Tomcat que sirve ficheros estáticos.
