package io.github.easy4j.javassist.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoSuchMethodExceptionTest {

    @Test
    public void shouldCreateWithDefaultConstructor() {
        NoSuchMethodException ex = new NoSuchMethodException();
        assertNotNull(ex);
        assertNull(ex.getMessage());
    }

    @Test
    public void shouldCreateWithMessage() {
        NoSuchMethodException ex = new NoSuchMethodException("method not found");
        assertNotNull(ex);
        assertEquals("method not found", ex.getMessage());
    }

    @Test
    public void shouldBeRuntimeException() {
        assertTrue(new NoSuchMethodException() instanceof RuntimeException);
    }
}
