package com.cursosdedesarrollo;

import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class CharsetFilterTest {

    @Test
    public void doFilterLlamaChain() throws Exception {
        CharsetFilter filtro = new CharsetFilter();
        filtro.init(mockFilterConfig());

        AtomicBoolean chainLlamada = new AtomicBoolean(false);

        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest req, ServletResponse resp)
                    throws IOException, ServletException {
                chainLlamada.set(true);
            }
        };

        filtro.doFilter(new StubServletRequest(null), new StubServletResponse(), chain);

        assertTrue("CharsetFilter debe propagar la petición llamando a chain.doFilter",
                chainLlamada.get());

        filtro.destroy();
    }

    @Test
    public void doFilterNoRompeConEncodingYaFijado() throws Exception {
        CharsetFilter filtro = new CharsetFilter();
        AtomicBoolean chainLlamada = new AtomicBoolean(false);

        FilterChain chain = (req, resp) -> chainLlamada.set(true);

        // Simula una petición que ya tiene charset fijado
        filtro.doFilter(new StubServletRequest("ISO-8859-1"), new StubServletResponse(), chain);

        assertTrue(chainLlamada.get());
    }

    // ── Stubs mínimos ─────────────────────────────────────────────────────────

    private static FilterConfig mockFilterConfig() {
        return new FilterConfig() {
            @Override public String getFilterName() { return "charsetFilter"; }
            @Override public javax.servlet.ServletContext getServletContext() { return null; }
            @Override public String getInitParameter(String name) { return null; }
            @Override public java.util.Enumeration<String> getInitParameterNames() {
                return java.util.Collections.emptyEnumeration();
            }
        };
    }

    /** Implementación mínima de ServletRequest para las pruebas del CharsetFilter. */
    private static class StubServletRequest implements ServletRequest {
        private String encoding;

        StubServletRequest(String encodingInicial) {
            this.encoding = encodingInicial;
        }

        @Override public String getCharacterEncoding() { return encoding; }
        @Override public void setCharacterEncoding(String env) { this.encoding = env; }

        // ── Métodos no usados por el filtro ───────────────────────────────────
        @Override public Object getAttribute(String n) { return null; }
        @Override public java.util.Enumeration<String> getAttributeNames() { return java.util.Collections.emptyEnumeration(); }
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public javax.servlet.ServletInputStream getInputStream() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public java.util.Locale getLocale() { return null; }
        @Override public java.util.Enumeration<java.util.Locale> getLocales() { return null; }
        @Override public String getLocalName() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public String getParameter(String n) { return null; }
        @Override public java.util.Map<String,String[]> getParameterMap() { return java.util.Collections.emptyMap(); }
        @Override public java.util.Enumeration<String> getParameterNames() { return java.util.Collections.emptyEnumeration(); }
        @Override public String[] getParameterValues(String n) { return null; }
        @Override public String getProtocol() { return null; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public int getRemotePort() { return 0; }
        @Override public javax.servlet.RequestDispatcher getRequestDispatcher(String p) { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public javax.servlet.ServletContext getServletContext() { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public boolean isSecure() { return false; }
        @Override public void removeAttribute(String n) {}
        @Override public void setAttribute(String n, Object o) {}
        @Override public javax.servlet.AsyncContext startAsync() { return null; }
        @Override public javax.servlet.AsyncContext startAsync(ServletRequest rq, ServletResponse rs) { return null; }
        @Override public javax.servlet.AsyncContext getAsyncContext() { return null; }
        @Override public javax.servlet.DispatcherType getDispatcherType() { return null; }
        /** @deprecated */ @Override public String getRealPath(String path) { return null; }
    }

    private static class StubServletResponse implements ServletResponse {
        private String encoding;
        @Override public void setCharacterEncoding(String enc) { this.encoding = enc; }
        @Override public String getCharacterEncoding() { return encoding; }

        @Override public void flushBuffer() {}
        @Override public int getBufferSize() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public java.util.Locale getLocale() { return null; }
        @Override public javax.servlet.ServletOutputStream getOutputStream() { return null; }
        @Override public java.io.PrintWriter getWriter() { return null; }
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void resetBuffer() {}
        @Override public void setBufferSize(int size) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setContentType(String type) {}
        @Override public void setLocale(java.util.Locale loc) {}
    }
}
