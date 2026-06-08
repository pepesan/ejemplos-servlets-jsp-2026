<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Demo jQuery – WebJar</title>
    <%-- Bootstrap para el layout --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light p-4">
<div class="container">

<nav class="mb-4 small">
    <a href="${pageContext.request.contextPath}/" class="text-secondary me-3">← Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-bootstrap.jsp" class="text-secondary me-3">Bootstrap →</a>
    <a href="${pageContext.request.contextPath}/demo-versionless.jsp" class="text-secondary me-3">Sin versión →</a>
    <a href="${pageContext.request.contextPath}/referencia.jsp" class="text-secondary">Referencia →</a>
</nav>

<h1 class="text-warning mb-1">Demo jQuery 3.7.1</h1>
<p class="text-secondary mb-4">
    jQuery cargado desde el WebJar:
    <code class="text-warning">/webjars/jquery/3.7.1/jquery.min.js</code>
</p>

<%-- ── Demo 1: versión y estado ─────────────────────────────────────── --%>
<div class="card bg-secondary-subtle border-secondary mb-3">
    <div class="card-body">
        <h5 class="text-warning">1. Versión cargada</h5>
        <p>Versión de jQuery en uso: <strong id="jq-version" class="text-success">cargando…</strong></p>
        <p class="small text-secondary">
            Si el WebJar cargó, jQuery está disponible como <code>$</code> y <code>jQuery</code>.
        </p>
    </div>
</div>

<%-- ── Demo 2: contador ────────────────────────────────────────────── --%>
<div class="card bg-secondary-subtle border-secondary mb-3">
    <div class="card-body">
        <h5 class="text-warning">2. Manipulación del DOM</h5>
        <p>Contador: <span id="contador" class="badge bg-warning text-dark fs-5">0</span></p>
        <button id="btn-sumar" class="btn btn-warning btn-sm me-2">+ Sumar</button>
        <button id="btn-restar" class="btn btn-outline-warning btn-sm me-2">− Restar</button>
        <button id="btn-reset" class="btn btn-outline-secondary btn-sm">Reset</button>
    </div>
</div>

<%-- ── Demo 3: filtrado de tabla ──────────────────────────────────── --%>
<div class="card bg-secondary-subtle border-secondary mb-3">
    <div class="card-body">
        <h5 class="text-warning">3. Filtrar tabla en tiempo real</h5>
        <input type="text" id="filtro" class="form-control form-control-sm bg-dark text-light border-secondary mb-2"
               placeholder="Escribe para filtrar...">
        <table class="table table-dark table-sm table-hover" id="tabla-frutas">
            <thead><tr><th>Fruta</th><th>Color</th><th>Temporada</th></tr></thead>
            <tbody>
                <tr><td>Manzana</td><td>Rojo</td><td>Otoño</td></tr>
                <tr><td>Naranja</td><td>Naranja</td><td>Invierno</td></tr>
                <tr><td>Fresa</td><td>Rojo</td><td>Primavera</td></tr>
                <tr><td>Sandía</td><td>Verde/Rojo</td><td>Verano</td></tr>
                <tr><td>Uva</td><td>Morado</td><td>Otoño</td></tr>
                <tr><td>Pera</td><td>Verde</td><td>Otoño</td></tr>
            </tbody>
        </table>
        <p class="small text-secondary">Filas visibles: <span id="filas-visibles">6</span></p>
    </div>
</div>

<%-- ── Demo 4: petición AJAX ──────────────────────────────────────── --%>
<div class="card bg-secondary-subtle border-secondary mb-3">
    <div class="card-body">
        <h5 class="text-warning">4. Petición AJAX al servidor</h5>
        <button id="btn-ajax" class="btn btn-warning btn-sm mb-2">Consultar hora del servidor</button>
        <div id="ajax-resultado" class="small text-secondary">Pulsa el botón para consultar.</div>
    </div>
</div>

<%-- ── Código de referencia ───────────────────────────────────────── --%>
<div class="card bg-secondary-subtle border-secondary mb-3">
    <div class="card-body">
        <h5 class="text-warning">Cómo incluir jQuery desde WebJar</h5>
        <pre class="bg-black text-success p-3 rounded small mb-2">
&lt;!-- En el &lt;head&gt; de la JSP --&gt;
&lt;script src="${'$'}{pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"&gt;&lt;/script&gt;

&lt;!-- O sin versión con WebjarsServlet (/wj/*) --&gt;
&lt;script src="${'$'}{pageContext.request.contextPath}/wj/jquery/jquery.min.js"&gt;&lt;/script&gt;</pre>
        <p class="small text-secondary mb-0">
            El fichero está en
            <code>META-INF/resources/webjars/jquery/3.7.1/jquery.min.js</code>
            dentro del JAR de Maven. Tomcat lo sirve directamente.
        </p>
    </div>
</div>

</div><%-- /container --%>

<script src="${pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script>
$(function () {
    // Demo 1: versión
    $('#jq-version').text('jQuery ' + $.fn.jquery);

    // Demo 2: contador
    var n = 0;
    function actualizarContador() {
        $('#contador').text(n)
            .removeClass('bg-warning bg-danger bg-success text-dark')
            .addClass(n > 0 ? 'bg-success' : n < 0 ? 'bg-danger' : 'bg-warning text-dark');
    }
    $('#btn-sumar').on('click', function () { n++; actualizarContador(); });
    $('#btn-restar').on('click', function () { n--; actualizarContador(); });
    $('#btn-reset').on('click', function () { n = 0; actualizarContador(); });

    // Demo 3: filtrado de tabla
    $('#filtro').on('input', function () {
        var q = $(this).val().toLowerCase();
        var visibles = 0;
        $('#tabla-frutas tbody tr').each(function () {
            var coincide = $(this).text().toLowerCase().includes(q);
            $(this).toggle(coincide);
            if (coincide) visibles++;
        });
        $('#filas-visibles').text(visibles);
    });

    // Demo 4: AJAX
    $('#btn-ajax').on('click', function () {
        var btn = $(this).prop('disabled', true).text('Consultando…');
        $.ajax({
            url: '${pageContext.request.contextPath}/api/hora',
            success: function (data) {
                $('#ajax-resultado').html(
                    '<span class="text-success">Hora del servidor: <strong>' +
                    $('<span>').text(data).html() + '</strong></span>'
                );
            },
            error: function (xhr) {
                $('#ajax-resultado').html(
                    '<span class="text-danger">Error ' + xhr.status +
                    ' — ¿está arrancado el servidor?</span>'
                );
            },
            complete: function () {
                btn.prop('disabled', false).text('Consultar hora del servidor');
            }
        });
    });
});
</script>
</body>
</html>
