package io.github.easy4j.javassist.utils;

import org.junit.Test;

import java.net.URL;
import java.util.Set;

import static org.junit.Assert.*;

public class ClassHelperTest {

    @Test
    public void shouldReturnClassLoaderForClass() {
        ClassLoader cl = ClassHelper.getClassLoader(String.class);
        assertNotNull(cl);
    }

    @Test
    public void shouldReturnDefaultClassLoader() {
        ClassLoader cl = ClassHelper.getClassLoader();
        assertNotNull(cl);
    }

    @Test
    public void shouldReturnCallerClassLoader() {
        ClassLoader cl = ClassHelper.getCallerClassLoader(ClassHelperTest.class);
        assertNotNull(cl);
    }

    @Test
    public void shouldLoadClassWithThreadContextClassLoader() throws Exception {
        Class<?> clazz = ClassHelper.forNameWithThreadContextClassLoader("java.lang.String");
        assertNotNull(clazz);
        assertEquals(String.class, clazz);
    }

    @Test
    public void shouldLoadClassWithCallerClassLoader() throws Exception {
        Class<?> clazz = ClassHelper.forNameWithCallerClassLoader("java.lang.Integer", ClassHelperTest.class);
        assertNotNull(clazz);
        assertEquals(Integer.class, clazz);
    }

    @Test
    public void shouldLoadClassByName() throws Exception {
        Class<?> clazz = ClassHelper.forName("java.lang.String");
        assertEquals(String.class, clazz);
    }

    @Test
    public void shouldLoadPrimitiveByName() throws Exception {
        assertEquals(int.class, ClassHelper.forName("int"));
        assertEquals(boolean.class, ClassHelper.forName("boolean"));
        assertEquals(byte.class, ClassHelper.forName("byte"));
        assertEquals(char.class, ClassHelper.forName("char"));
        assertEquals(double.class, ClassHelper.forName("double"));
        assertEquals(float.class, ClassHelper.forName("float"));
        assertEquals(long.class, ClassHelper.forName("long"));
        assertEquals(short.class, ClassHelper.forName("short"));
    }

    @Test
    public void shouldLoadArrayByName() throws Exception {
        Class<?> clazz = ClassHelper.forName("java.lang.String[]");
        assertNotNull(clazz);
        assertTrue(clazz.isArray());
        assertEquals(String.class, clazz.getComponentType());
    }

    @Test
    public void shouldLoadInternalArrayFormat() throws Exception {
        Class<?> clazz = ClassHelper.forName("[Ljava.lang.String;");
        assertNotNull(clazz);
        assertTrue(clazz.isArray());
        assertEquals(String.class, clazz.getComponentType());
    }

    @Test(expected = ClassNotFoundException.class)
    public void shouldThrowWhenClassNotFound() throws Exception {
        ClassHelper.forName("com.nonexistent.FakeClass");
    }

    @Test
    public void shouldResolvePrimitiveClassName() {
        assertEquals(int.class, ClassHelper.resolvePrimitiveClassName("int"));
        assertEquals(boolean.class, ClassHelper.resolvePrimitiveClassName("boolean"));
        assertNull(ClassHelper.resolvePrimitiveClassName("String"));
        assertNull(ClassHelper.resolvePrimitiveClassName(null));
    }

    @Test
    public void shouldReturnClasspathUrlsByManifest() {
        Set<URL> urls = ClassHelper.getClasspathUrlsByManifest();
        assertNotNull(urls);
    }

    @Test
    public void shouldReturnNullStringForNullObject() {
        assertEquals("null", ClassHelper.toShortString(null));
    }

    @Test
    public void shouldReturnShortStringForObject() {
        String result = ClassHelper.toShortString("hello");
        assertNotNull(result);
        assertTrue(result.startsWith("String@"));
    }

    @Test
    public void shouldHaveArraySuffixConstant() {
        assertEquals("[]", ClassHelper.ARRAY_SUFFIX);
    }
}
