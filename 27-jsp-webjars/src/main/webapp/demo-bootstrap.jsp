<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Demo Bootstrap – WebJar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light p-4">
<div class="container">

<nav class="mb-4 small">
    <a href="${pageContext.request.contextPath}/" class="text-secondary me-3">← Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-jquery.jsp" class="text-secondary me-3">jQuery →</a>
    <a href="${pageContext.request.contextPath}/demo-versionless.jsp" class="text-secondary me-3">Sin versión →</a>
    <a href="${pageContext.request.contextPath}/referencia.jsp" class="text-secondary">Referencia →</a>
</nav>

<h1 class="text-info mb-1">Demo Bootstrap 5.3.3</h1>
<p class="text-secondary mb-4">
    Componentes Bootstrap cargados desde WebJar:
    <code class="text-info">/webjars/bootstrap/5.3.3/css/bootstrap.min.css</code>
</p>

<%-- ── Alertas ──────────────────────────────────────────────────────── --%>
<h5 class="text-info">Alertas</h5>
<div class="alert alert-success alert-dismissible fade show" role="alert">
    Bootstrap cargó correctamente desde el WebJar.
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>
<div class="alert alert-warning">
    Si no ves estilos, el path <code>/webjars/bootstrap/5.3.3/css/bootstrap.min.css</code> no se resolvió.
</div>

<%-- ── Botones ──────────────────────────────────────────────────────── --%>
<h5 class="text-info mt-4">Botones + Tooltips</h5>
<div class="mb-3">
    <button type="button" class="btn btn-primary me-2"
            data-bs-toggle="tooltip" title="Tooltip en btn-primary">Primary</button>
    <button type="button" class="btn btn-secondary me-2"
            data-bs-toggle="tooltip" title="Tooltip en btn-secondary">Secondary</button>
    <button type="button" class="btn btn-success me-2"
            data-bs-toggle="tooltip" title="Bootstrap JS cargado">Success</button>
    <button type="button" class="btn btn-outline-info"
            data-bs-toggle="tooltip" title="Bootstrap bundle incluye Popper">Info</button>
</div>

<%-- ── Modal ──────────────────────────────────────────────────────── --%>
<h5 class="text-info mt-4">Modal</h5>
<button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#miModal">
    Abrir modal
</button>
<div class="modal fade" id="miModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-dark border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title text-info">Modal desde WebJar Bootstrap</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-secondary">
                Este modal funciona porque Bootstrap JS está cargado desde el WebJar.<br>
                <code>/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js</code>
            </div>
            <div class="modal-footer border-secondary">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<%-- ── Toast ─────────────────────────────────────────────────────── --%>
<h5 class="text-info mt-4">Toast</h5>
<button type="button" class="btn btn-warning" id="btn-toast">Mostrar toast</button>
<div class="position-fixed bottom-0 end-0 p-3" style="z-index:9999">
    <div id="miToast" class="toast bg-dark border-warning text-light" role="alert">
        <div class="toast-header bg-dark border-warning text-warning">
            <strong class="me-auto">WebJar Bootstrap</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
        </div>
        <div class="toast-body">Bootstrap JS funcionando correctamente.</div>
    </div>
</div>

<%-- ── Accordion ──────────────────────────────────────────────────── --%>
<h5 class="text-info mt-4">Accordion</h5>
<div class="accordion" id="acordeon">
    <div class="accordion-item bg-dark border-secondary">
        <h2 class="accordion-header">
            <button class="accordion-button bg-dark text-light" type="button"
                    data-bs-toggle="collapse" data-bs-target="#p1">
                ¿Qué es un WebJar?
            </button>
        </h2>
        <div id="p1" class="accordion-collapse collapse show" data-bs-parent="#acordeon">
            <div class="accordion-body text-secondary">
                Un JAR Maven que empaqueta recursos estáticos (JS, CSS) siguiendo la
                convención <code>META-INF/resources/webjars/{nombre}/{version}/</code>.
                Tomcat los sirve automáticamente.
            </div>
        </div>
    </div>
    <div class="accordion-item bg-dark border-secondary">
        <h2 class="accordion-header">
            <button class="accordion-button collapsed bg-dark text-light" type="button"
                    data-bs-toggle="collapse" data-bs-target="#p2">
                ¿Cómo referenciar Bootstrap en JSP?
            </button>
        </h2>
        <div id="p2" class="accordion-collapse collapse" data-bs-parent="#acordeon">
            <div class="accordion-body text-secondary">
                <code>
                &lt;link href="${'$'}{pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"&gt;
                &lt;script src="${'$'}{pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"&gt;
                </code>
            </div>
        </div>
    </div>
</div>

</div>

<script src="${pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script>
    // Activar tooltips
    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(function(el) {
        new bootstrap.Tooltip(el);
    });
    // Toast
    document.getElementById('btn-toast').addEventListener('click', function() {
        new bootstrap.Toast(document.getElementById('miToast')).show();
    });
</script>
</body>
</html>
