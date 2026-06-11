package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class RecursoNoEncontradoExceptionTest {

    @Test
    public void guardaMensaje() {
        RecursoNoEncontradoException ex = new RecursoNoEncontradoException("Producto 99 no existe");
        assertEquals("Producto 99 no existe", ex.getMessage());
    }

    @Test
    public void esSubclaseDeRuntimeException() {
        assertTrue(new RecursoNoEncontradoException("x") instanceof RuntimeException);
    }

    @Test
    public void logginHandlerExtiendePadre() {
        assertTrue(new LoggingExceptionHandler()
            instanceof org.apache.struts.action.ExceptionHandler);
    }
}
