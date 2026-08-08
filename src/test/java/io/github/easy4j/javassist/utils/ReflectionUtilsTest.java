package io.github.easy4j.javassist.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void shouldGetMethodByName() {
        Method m = ReflectionUtils.getMethod(String.class, "length");
        assertNotNull(m);
        assertEquals("length", m.getName());
    }

    @Test
    public void shouldReturnNullWhenMethodNotFound() {
        Method m = ReflectionUtils.getMethod(String.class, "nonExistent");
        assertNull(m);
    }

    @Test
    public void shouldGetMethodByNameAndParamTypes() {
        Method m = ReflectionUtils.getMethod(String.class, "substring", int.class);
        assertNotNull(m);
    }

    @Test
    public void shouldReturnNullWhenMethodWithWrongParams() {
        Method m = ReflectionUtils.getMethod(String.class, "length", String.class);
        assertNull(m);
    }

    @Test
    public void shouldSearchSuperclasses() {
        Method m = ReflectionUtils.getMethod(Integer.class, "toString");
        assertNotNull(m);
    }

    @Test
    public void shouldGetMethodOnInterface() {
        Method m = ReflectionUtils.getMethod(Runnable.class, "run");
        assertNotNull(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenClassIsNull() {
        ReflectionUtils.getMethod(null, "toString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNameIsNull() {
        ReflectionUtils.getMethod(String.class, (String) null);
    }

    @Test
    public void shouldGetMethodWithNullParamTypes() {
        Method m = ReflectionUtils.getMethod(String.class, "length", (Class<?>[]) null);
        assertNotNull(m);
    }
}
