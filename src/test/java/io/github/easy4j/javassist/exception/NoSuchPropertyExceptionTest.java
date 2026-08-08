package io.github.easy4j.javassist.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoSuchPropertyExceptionTest {

    @Test
    public void shouldCreateWithDefaultConstructor() {
        NoSuchPropertyException ex = new NoSuchPropertyException();
        assertNotNull(ex);
        assertNull(ex.getMessage());
    }

    @Test
    public void shouldCreateWithMessage() {
        NoSuchPropertyException ex = new NoSuchPropertyException("property not found");
        assertNotNull(ex);
        assertEquals("property not found", ex.getMessage());
    }

    @Test
    public void shouldBeRuntimeException() {
        assertTrue(new NoSuchPropertyException() instanceof RuntimeException);
    }
}
