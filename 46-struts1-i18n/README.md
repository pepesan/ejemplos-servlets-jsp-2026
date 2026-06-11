# 46 — Struts 1.x: Internacionalización (i18n)

**Puerto:** 8046  
**URL:** http://localhost:8046/

## Qué enseña

Internacionalización (i18n) en Struts 1.x usando `MessageResources`, `<bean:message>` y `Globals.LOCALE_KEY`.

| Concepto | Detalle |
|----------|---------|
| `Globals.LOCALE_KEY` | Clave de sesión donde Struts guarda el `Locale` activo |
| `<bean:message key="..."/>` | Taglib que lee el mensaje según el Locale de sesión |
| `CambiarIdiomaAction` | Guarda `new Locale("es"|"en")` en sesión y hace redirect (PRG) |
| `MessageResources` | API programática; `getMessage(locale, key, args...)` |
| `ApplicationResources_es.properties` | Fichero de mensajes en español |
| `ApplicationResources_en.properties` | Fichero de mensajes en inglés |
| `<fmt:formatDate type="both">` | JSTL también respeta el locale para fechas |

## Pruebas curl

```bash
# Página inicial (español por defecto)
curl http://localhost:8046/inicio.do

# Cambiar a inglés (302 redirect PRG)
curl -c /tmp/cookies.txt http://localhost:8046/idioma.do?lang=en

# Verificar idioma activo (debe mostrar "Welcome", "English")
curl -b /tmp/cookies.txt http://localhost:8046/inicio.do | grep -i welcome

# Saludo personalizado en inglés
curl -b /tmp/cookies.txt -c /tmp/cookies.txt -X POST http://localhost:8046/saludo.do \
     -d "nombre=Ana"

# Volver a español
curl -b /tmp/cookies.txt http://localhost:8046/idioma.do?lang=es
```

## Arrancar / parar

```bash
./start.sh   # http://localhost:8046/
./stop.sh
./build.sh   # mvn clean package
./test.sh    # mvn test
```
