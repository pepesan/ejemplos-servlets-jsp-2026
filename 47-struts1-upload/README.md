# 47 — Struts 1.x: Subida de ficheros

**Puerto:** 8047  
**URL:** http://localhost:8047/

## Qué enseña

Subida de ficheros en Struts 1.x con `FormFile`, `CommonsMultipartRequestHandler` y descarga posterior vía `DescargarFicheroAction`.

| Concepto | Detalle |
|----------|---------|
| `FormFile` | Tipo Struts para campos `<input type="file">`; `getInputStream()`, `getFileSize()`, `destroy()` |
| `CommonsMultipartRequestHandler` | Handler por defecto de Struts; requiere `commons-fileupload` + `commons-io` explícitos |
| `<controller maxFileSize="2M"/>` | Límite de tamaño en `struts-config.xml` |
| `SubirFicheroForm` | `ActionForm` con campo `FormFile archivo` |
| `SubirFicheroAction` | Valida MIME/tamaño, guarda en tmpDir, pasa metadatos a la vista |
| `DescargarFicheroAction` | Escribe el fichero directamente en el response; devuelve `null` |

Tipos permitidos: `image/jpeg`, `image/png`, `image/gif`, `image/webp`, `application/pdf`, `text/plain` — máximo 2 MB.

## Flujo

```
GET /formulario.do          → formulario.jsp
POST /subir.do              → SubirFicheroAction → resultado.jsp
                                                  (enlaces ↓ Descargar / 👁 Ver)
GET /descargar.do?nombre=X          → Content-Disposition: attachment
GET /descargar.do?nombre=X&ver=true → Content-Disposition: inline
```

## Pruebas curl

```bash
# Ver formulario
curl http://localhost:8047/formulario.do

# Subir fichero de texto
curl -F "archivo=@/tmp/prueba.txt;type=text/plain" \
     -F "descripcion=Mi fichero" \
     http://localhost:8047/subir.do

# Descargar el fichero subido
curl -O http://localhost:8047/descargar.do?nombre=prueba.txt

# Ver inline en el navegador
curl "http://localhost:8047/descargar.do?nombre=prueba.txt&ver=true"

# Tipo no permitido (debe mostrar error)
curl -F "archivo=@/bin/ls;type=application/octet-stream" \
     http://localhost:8047/subir.do

# Fichero no encontrado
curl -o /dev/null -w "%{http_code}\n" \
     http://localhost:8047/descargar.do?nombre=noexiste.txt
# → 404
```

## Arrancar / parar

```bash
./start.sh   # http://localhost:8047/
./stop.sh
./build.sh   # mvn clean package
./test.sh    # mvn test
```
