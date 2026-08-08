package io.github.easy4j.javassist.utils;

import org.junit.Test;

import javassist.ClassPool;
import javassist.ClassClassPath;

import static org.junit.Assert.*;

public class ClassPoolFactoryTest {

    @Test
    public void shouldReturnDefaultPool() {
        ClassPool pool = ClassPoolFactory.getDefaultPool();
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnPoolWithClassPaths() {
        ClassPool pool = ClassPoolFactory.getClassPool(new ClassClassPath(String.class));
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnDefaultPoolWhenNoClassPaths() {
        ClassPool pool = ClassPoolFactory.getClassPool((javassist.ClassPath[]) null);
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnDefaultPoolWhenEmptyClassPaths() {
        ClassPool pool = ClassPoolFactory.getClassPool(new javassist.ClassPath[0]);
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnPoolForClassLoader() {
        ClassPool pool = ClassPoolFactory.getClassPool(ClassLoader.getSystemClassLoader());
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnDefaultPoolForNullClassLoader() {
        ClassPool pool = ClassPoolFactory.getClassPool((ClassLoader) null);
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnCachedPoolForSameClassLoader() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        ClassPool pool1 = ClassPoolFactory.getClassPool(cl);
        ClassPool pool2 = ClassPoolFactory.getClassPool(cl);
        assertSame(pool1, pool2);
    }

    @Test
    public void shouldReturnPoolForCurrentContextClassLoader() {
        ClassPool pool = ClassPoolFactory.getClassPoolForCurrentContextClassLoader();
        assertNotNull(pool);
    }

    @Test
    public void shouldReturnPoolForManifest() {
        ClassPool pool = ClassPoolFactory.getClassPoolForManifest();
        assertNotNull(pool);
    }
}
