# 23 — JSP: Formularios con validación y PRG

Ciclo completo de un formulario web: GET muestra el form, POST valida, si hay errores hace forward con los errores inline, si todo es correcto hace sendRedirect (patrón PRG).

## Flujo

```
GET  /registro          → formulario vacío (RegistroBean vacío en request)
POST /registro          → valida todos los campos
  ├── errores           → forward a formulario.jsp (errores + bean en request)
  └── sin errores       → session.setAttribute + sendRedirect /registro?ok=1  (PRG)
GET  /registro?ok=1     → exito.jsp (lee nombre de sesión, lo consume)
```

## Validaciones

| Campo | Regla |
|-------|-------|
| nombre | obligatorio, mínimo 3 caracteres |
| email | obligatorio, debe contener @ |
| password | obligatoria, mínimo 6 caracteres |
| edad | obligatoria, número entero entre 18 y 120 |

## Diseño en la JSP

- `${bean.nombre}` — re-rellena el campo con el valor enviado
- `${errores.nombre}` — muestra el error junto al campo con `<c:if test="${not empty errores.nombre}">`
- `${errores.size()}` — resumen de cuántos errores hay

## Arrancar

```bash
./start.sh        # http://localhost:8023
```

## Pruebas curl

```bash
# GET: formulario vacío
curl -s http://localhost:8023/registro | grep -o 'Formulario de registro'

# POST vacío: 4 errores (2 "obligatorio" + 2 "obligatoria")
curl -s -X POST http://localhost:8023/registro --data "" | grep -o 'obligatori' | wc -l   # 4

# POST email sin @
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Ana&email=noemail&password=secret123&edad=25" | grep -o 'debe contener @'

# POST edad menor 18
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Ana&email=a@b.com&password=secret&edad=15" | grep -o 'mayor de 18'

# POST válido → 302
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Carlos&email=carlos@test.com&password=secret123&edad=30" \
  -o /dev/null -w "%{http_code}"   # 302

# GET ?ok=1 → éxito
curl -s "http://localhost:8023/registro?ok=1" | grep -o 'Registro completado'

# Nombre se conserva en re-render con errores
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Elena&email=malemail&password=short&edad=abc" | grep -o 'Elena'
```
