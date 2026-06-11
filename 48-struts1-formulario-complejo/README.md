# 48 — Struts 1.x: Formulario complejo

**Puerto:** 8048  
**URL:** http://localhost:8048/

## Qué enseña

Uso de todos los tipos de campo del formulario en Struts 1.x:

| Tag Struts | HTML generado | Campo del formulario |
|------------|---------------|----------------------|
| `<html:text>` | `<input type="text">` | Nombre, apellidos, email |
| `<html:radio>` | `<input type="radio">` | Género, modalidad (opción única) |
| `<html:select>` + `<html:option>` | `<select><option>` | País, nivel de experiencia |
| `<html:multibox>` | `<input type="checkbox">` | Tecnologías → `String[]` |
| `<html:textarea>` | `<textarea>` | Comentarios (multilínea) |
| HTML5 nativo | `<input type="date">` | Fechas (Struts 1.x no tiene tag propio) |

### Puntos clave

- **`<html:multibox>`** requiere `String[]` en el `ActionForm` y `reset()` debe poner `tecnologias = new String[0]` — sin reset, los checkboxes desmarcados mantienen su valor anterior.
- **`<html:radio>`** marca automáticamente el radio cuyo `value` coincide con el campo del form (repopulación automática).
- **`<html:select>`** selecciona la opción correcta en repopulación sin código extra.
- **Fecha con `<input type="date">`**: Struts lee el parámetro POST por nombre y popula el `ActionForm` aunque el HTML no sea un tag Struts.
- **Validación en `ActionForm.validate()`**: Struts llama a `validate()` antes de invocar la Action si `validate="true"` en struts-config. Con errores, hace forward a `input` sin llegar a la Action.

## Pruebas curl

```bash
# Ver el formulario vacío
curl http://localhost:8048/formulario.do

# Enviar sin datos → vuelve al formulario con mensajes de error
curl -X POST http://localhost:8048/registrar.do

# Enviar formulario completo válido
curl -X POST http://localhost:8048/registrar.do \
  --data-urlencode "nombre=María" \
  --data-urlencode "apellidos=López" \
  --data-urlencode "email=maria@ejemplo.com" \
  --data-urlencode "fechaNacimiento=1988-03-22" \
  -d "genero=f&pais=es" \
  -d "tecnologias=java&tecnologias=python&tecnologias=sql" \
  -d "modalidad=hibrido&nivel=senior" \
  --data-urlencode "comentarios=Backend Java" \
  -d "fechaDisponible=2026-09-01"

# Email inválido → error de formato
curl -X POST http://localhost:8048/registrar.do \
  -d "nombre=Ana&apellidos=G&email=invalido&fechaNacimiento=1990-01-01" \
  -d "genero=f&pais=es&tecnologias=java&modalidad=remoto&nivel=junior"

# Sin tecnologías seleccionadas → error de checkboxes
curl -X POST http://localhost:8048/registrar.do \
  -d "nombre=Ana&apellidos=G&email=a@b.com&fechaNacimiento=1990-01-01" \
  -d "genero=f&pais=es&modalidad=remoto&nivel=junior"
```

## Arrancar / parar

```bash
./start.sh   # http://localhost:8048/
./stop.sh
./build.sh   # mvn clean package
./test.sh    # mvn test (10 tests de validación)
```
