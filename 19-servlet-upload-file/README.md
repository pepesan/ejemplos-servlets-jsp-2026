# 19 — Subida de ficheros con `@MultipartConfig`

Demuestra cómo un servlet acepta ficheros subidos con
`enctype="multipart/form-data"` usando la anotación `@MultipartConfig`
(Servlet 3.0+) y la API `request.getPart()`.
No se necesita Apache Commons FileUpload ni ninguna librería externa.

---

## Conceptos clave

### `@MultipartConfig`

La anotación habilita el parsing de peticiones multipart en el servlet.
Sin ella, llamar a `request.getPart()` lanza `IllegalStateException`.

```java
@WebServlet("/subir")
@MultipartConfig(
    fileSizeThreshold = 64 * 1024,       // 64 KB — por encima: vuelca a disco temporal
    maxFileSize       = 2 * 1024 * 1024, // 2 MB máximo por fichero
    maxRequestSize    = 5 * 1024 * 1024  // 5 MB máximo por petición completa
)
public class SubirFicheroServlet extends HttpServlet { ... }
```

| Parámetro | Descripción |
|-----------|-------------|
| `fileSizeThreshold` | Bytes hasta los que la parte se mantiene en memoria. Por encima → disco temporal. |
| `maxFileSize` | Tamaño máximo de un único fichero. Si se supera → `IllegalStateException`. |
| `maxRequestSize` | Tamaño máximo de la petición completa (todos los ficheros + campos). |
| `location` | Directorio de escritura temporal. Si se omite, usa el temp del servidor. |

### Formulario HTML

El atributo `enctype="multipart/form-data"` es **obligatorio** para que el
navegador codifique los ficheros correctamente:

```html
<form method="post" action="subir" enctype="multipart/form-data">
    <input type="file" name="archivo" accept="image/*,.pdf,.txt">
    <input type="text" name="descripcion">
    <button type="submit">Subir</button>
</form>
```

### Leer la parte del fichero

```java
Part parte = request.getPart("archivo");

// Nombre original: está en el header Content-Disposition
String nombre = extraerNombreFichero(parte.getHeader("content-disposition"));
// → "form-data; name=\"archivo\"; filename=\"foto.jpg\""

String mime = parte.getContentType(); // "image/jpeg"
long   tam  = parte.getSize();        // bytes
```

Los campos de texto normales del mismo formulario multipart siguen
funcionando exactamente igual con `request.getParameter("descripcion")`.

### Extraer el nombre del fichero

El nombre original viene en el header `Content-Disposition`:

```
form-data; name="archivo"; filename="foto.jpg"
```

No hay API directa en `Part` para esto; hay que parsear el header:

```java
static String extraerNombreFichero(String contentDisposition) {
    if (contentDisposition == null) return "";
    for (String token : contentDisposition.split(";")) {
        token = token.trim();
        if (token.startsWith("filename")) {
            return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return "";
}
```

Este método es estático y no usa el contenedor, por lo que se prueba
fácilmente con JUnit sin necesidad de Tomcat.

### Guardar en disco

```java
// Directorio temporal de la webapp (gestionado por Tomcat)
File tmpDir = (File) getServletContext()
                 .getAttribute("javax.servlet.context.tempdir");

// Saneamiento: elimina caracteres problemáticos para evitar path traversal
String nombreSeguro = nombre.replaceAll("[^a-zA-Z0-9._-]", "_");

File destino = new File(tmpDir, nombreSeguro);
parte.write(destino.getAbsolutePath());
```

> `Part.write(path)`: si el fichero ya estaba en disco (superó el umbral),
> lo mueve a `path`; si estaba en memoria, lo escribe. En producción se
> usaría un directorio configurado (no el directorio temporal del servidor).

### Alternativa: `<multipart-config>` en `web.xml`

Equivalente a `@MultipartConfig` pero sin recompilar para cambiar límites:

```xml
<servlet>
    <servlet-name>subir</servlet-name>
    <servlet-class>com.cursosdedesarrollo.SubirFicheroServlet</servlet-class>
    <multipart-config>
        <file-size-threshold>65536</file-size-threshold>
        <max-file-size>2097152</max-file-size>
        <max-request-size>5242880</max-request-size>
    </multipart-config>
</servlet>
```

---

## Descarga del fichero subido

Tras una subida exitosa el servlet muestra dos enlaces:

| Enlace | Header enviado | Comportamiento |
|--------|---------------|----------------|
| Descargar | `Content-Disposition: attachment` | El navegador abre el diálogo "Guardar como" |
| Ver en el navegador | `Content-Disposition: inline` | El navegador muestra el fichero (imágenes, PDF) |

```java
// DescargarFicheroServlet.java — fragmento clave
boolean inline = "true".equalsIgnoreCase(req.getParameter("ver"));
res.setHeader("Content-Disposition",
    (inline ? "inline" : "attachment") + "; filename=\"" + nombreSeguro + "\"");
```

La URL incluye el nombre saneado como parámetro: `/descargar?nombre=foto.png`.
El servlet verifica con `getCanonicalPath()` que el fichero pertenece al directorio
temporal antes de servirlo (anti path-traversal).

---

## Estructura de archivos

```
19-servlet-upload-file/
├── src/main/java/com/cursosdedesarrollo/
│   ├── Html.java                        ← helper: cabecera/nav/pie/esc (tema oscuro)
│   ├── SubirFicheroServlet.java         ← @WebServlet + @MultipartConfig, doGet + doPost
│   └── DescargarFicheroServlet.java     ← @WebServlet, sirve ficheros del tmp con Content-Disposition
├── src/test/java/com/cursosdedesarrollo/
│   └── SubirFicheroServletTest.java     ← tests de extraerNombreFichero y formatearTam
└── src/main/webapp/
    ├── index.html                       ← portada con enlace al servlet
    └── WEB-INF/
        └── web.xml
```

---

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8019
./stop.sh    # para el proceso en el puerto 8019
./build.sh   # mvn clean package
./test.sh    # mvn test
```

---

## Pruebas curl

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8019/
# Esperado: 200

# GET formulario de subida
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8019/subir
# Esperado: 200

# POST con fichero de texto válido
echo "Contenido de prueba" > /tmp/prueba.txt
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/prueba.txt;type=text/plain" \
     -F "descripcion=Fichero de prueba" | grep -o "correctamente"
# Esperado: correctamente

# POST con fichero de texto válido + descripción
echo "Hola mundo" > /tmp/hola.txt
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/hola.txt;type=text/plain" \
     -F "descripcion=Mi texto" | grep -o "Descripción"
# Esperado: Descripción

# POST con tipo MIME no permitido
echo "<html><body>test</body></html>" > /tmp/test.html
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/test.html;type=text/html" | grep -o "no permitido"
# Esperado: no permitido

# POST con imagen válida (genera un PNG mínimo de 1x1 px)
printf '\x89PNG\r\n\x1a\n\x00\x00\x00\rIHDR\x00\x00\x00\x01\x00\x00\x00\x01\x08\x02\x00\x00\x00\x90wS\xde\x00\x00\x00\x0cIDATx\x9cc\xf8\x0f\x00\x00\x01\x01\x00\x05\x18\xd8N\x00\x00\x00\x00IEND\xaeB`\x82' > /tmp/pixel.png
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/pixel.png;type=image/png" | grep -o "correctamente"
# Esperado: correctamente

# Verificar que el tamaño se muestra formateado
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/prueba.txt;type=text/plain" | grep -oE "[0-9]+ B"
# Esperado: el tamaño en bytes, p.ej. "20 B"

# Subida + descarga como attachment (debe devolver el contenido original)
echo "Hola descarga" > /tmp/hola.txt
curl -s -X POST http://localhost:8019/subir \
     -F "archivo=@/tmp/hola.txt;type=text/plain" > /dev/null
curl -s -o /tmp/descargado.txt \
     -w "HTTP:%{http_code}\n" \
     "http://localhost:8019/descargar?nombre=hola.txt"
# Esperado: HTTP:200
diff /tmp/hola.txt /tmp/descargado.txt && echo "contenido idéntico"
# Esperado: contenido idéntico

# Ver inline (mismo fichero, Content-Disposition inline)
curl -s -o /dev/null \
     -w "HTTP:%{http_code}\n" \
     "http://localhost:8019/descargar?nombre=hola.txt&ver=true"
# Esperado: HTTP:200

# Fichero no existente → 404
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8019/descargar?nombre=no_existe.txt"
# Esperado: 404

# Sin parámetro → 400
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8019/descargar"
# Esperado: 400
```
