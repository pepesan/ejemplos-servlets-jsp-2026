# 14 — Servlet: Parámetros GET y POST

Módulo que muestra cómo leer parámetros enviados por URL (query string, método GET)
y por formulario HTML (método POST) con la misma API de Servlet.

## Servlets

| Servlet | URL | Método | Descripción |
|---------|-----|--------|-------------|
| `ParamsGetServlet` | `/get` | GET | Lee parámetros de la query string |
| `ParamsPostServlet` | `/post` | GET | Muestra formulario HTML |
| `ParamsPostServlet` | `/post` | POST | Procesa los campos del formulario |

## API clave

```java
// Valor único (primer valor si hay varios)
String valor = req.getParameter("nombre");

// Todos los valores de un parámetro multivalor
String[] colores = req.getParameterValues("color");

// Todos los nombres de parámetros recibidos
Enumeration<String> nombres = req.getParameterNames();

// Mapa completo
Map<String, String[]> mapa = req.getParameterMap();

// Query string cruda (sin decodificar)
String qs = req.getQueryString();   // "nombre=Ana&ciudad=Madrid"
```

## Diferencias GET vs POST

| Aspecto | GET | POST |
|---------|-----|------|
| Dónde van los datos | URL (`?clave=valor`) | Cuerpo de la petición |
| Visibilidad | Visible en la barra | Oculto |
| Límite | ~2 KB | Sin límite práctico |
| Caché / marcadores | Sí | No |
| Uso habitual | Búsquedas, filtros | Formularios, datos sensibles |

## Arrancar

```bash
./start.sh        # http://localhost:8014
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# GET sin parámetros → página con "Sin parámetros"
curl -s http://localhost:8014/get | grep -o 'Sin par'

# GET con dos parámetros simples
curl -s "http://localhost:8014/get?nombre=Ana&ciudad=Madrid" | grep -o 'Ana'

# GET multivalor (mismo nombre tres veces)
curl -s "http://localhost:8014/get?color=rojo&color=verde&color=azul" | grep -o 'rojo, verde, azul'

# GET con URL encoding (espacio y caracteres especiales)
curl -s "http://localhost:8014/get?texto=hola+mundo&especial=%3Chola%3E" | grep -o 'hola mundo'

# POST formulario completo → muestra "Formulario recibido correctamente"
curl -s -X POST http://localhost:8014/post \
  --data "nombre=Ana&email=ana%40ejemplo.com&mensaje=Hola&idioma=Java" \
  | grep -o 'correctamente'

# POST sin nombre ni email → muestra mensaje de campos obligatorios
curl -s -X POST http://localhost:8014/post \
  --data "nombre=&email=&mensaje=prueba" \
  | grep -o 'obligatorios'

# Verificar Content-Type de la respuesta
curl -s -I http://localhost:8014/get | grep -i 'content-type'
```
