package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClassGeneratorTest {

    @Test
    public void shouldCreateNewInstance() {
        ClassGenerator cg = ClassGenerator.newInstance();
        assertNotNull(cg);
    }

    @Test
    public void shouldCreateNewInstanceWithClassLoader() {
        ClassGenerator cg = ClassGenerator.newInstance(ClassLoader.getSystemClassLoader());
        assertNotNull(cg);
    }

    @Test
    public void shouldSetClassName() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.setClassName("com.test.MyClass");
        assertSame(cg, result);
        assertEquals("com.test.MyClass", cg.getClassName());
    }

    @Test
    public void shouldAddInterfaceByName() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addInterface("java.io.Serializable");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddInterfaceByClass() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addInterface(Serializable.class);
        assertSame(cg, result);
    }

    @Test
    public void shouldSetSuperClassByName() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.setSuperClass("java.lang.Object");
        assertSame(cg, result);
    }

    @Test
    public void shouldSetSuperClassByClass() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.setSuperClass(Object.class);
        assertSame(cg, result);
    }

    @Test
    public void shouldAddField() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addField("public int count;");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddFieldWithModifiers() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addField("count", java.lang.reflect.Modifier.PUBLIC, int.class);
        assertSame(cg, result);
    }

    @Test
    public void shouldAddFieldWithModifiersAndDefault() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addField("count", java.lang.reflect.Modifier.PUBLIC, int.class, "0");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddMethod() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addMethod("public int getCount(){ return 0; }");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddMethodWithSignature() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addMethod("getCount", java.lang.reflect.Modifier.PUBLIC, int.class, new Class<?>[0], "return 0;");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddMethodWithExceptions() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addMethod("doSomething", java.lang.reflect.Modifier.PUBLIC, void.class, new Class<?>[0], new Class<?>[]{Exception.class}, "throw new Exception();");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddConstructor() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addConstructor("public <init>(){}");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddConstructorWithParams() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addConstructor(java.lang.reflect.Modifier.PUBLIC, new Class<?>[]{int.class}, "");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddConstructorWithExceptions() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addConstructor(java.lang.reflect.Modifier.PUBLIC, new Class<?>[0], new Class<?>[]{Exception.class}, "");
        assertSame(cg, result);
    }

    @Test
    public void shouldAddDefaultConstructor() {
        ClassGenerator cg = ClassGenerator.newInstance();
        ClassGenerator result = cg.addDefaultConstructor();
        assertSame(cg, result);
    }

    @Test
    public void shouldGetClassPool() {
        ClassGenerator cg = ClassGenerator.newInstance();
        assertNotNull(cg.getClassPool());
    }

    @Test
    public void shouldDetectDynamicClass() {
        assertFalse(ClassGenerator.isDynamicClass(Object.class));
    }

    @Test
    public void shouldGenerateSimpleClass() {
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.test.SimpleClass");
        cg.addDefaultConstructor();
        Class<?> clazz = cg.toClass();
        assertNotNull(clazz);
        assertTrue(ClassGenerator.isDynamicClass(clazz));
    }

    @Test
    public void shouldGenerateClassWithInterface() {
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.test.ImplClass");
        cg.addInterface(Serializable.class);
        cg.addDefaultConstructor();
        Class<?> clazz = cg.toClass();
        assertNotNull(clazz);
        assertTrue(Serializable.class.isAssignableFrom(clazz));
    }

    @Test
    public void shouldGenerateClassWithFieldAndMethod() {
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.test.FieldMethodClass");
        cg.addField("public int value;");
        cg.addMethod("public int getValue(){ return value; }");
        cg.addDefaultConstructor();
        Class<?> clazz = cg.toClass();
        assertNotNull(clazz);
    }

    @Test
    public void shouldGenerateClassWithSuperClass() {
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.test.ChildClass");
        cg.setSuperClass(ArrayList.class);
        cg.addDefaultConstructor();
        Class<?> clazz = cg.toClass();
        assertNotNull(clazz);
        assertTrue(ArrayList.class.isAssignableFrom(clazz));
    }

    @Test
    public void shouldRelease() {
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.test.ReleaseClass" + System.nanoTime());
        cg.addInterface(Serializable.class);
        cg.addField("public int x;");
        cg.addMethod("public int getX(){ return x; }");
        cg.addDefaultConstructor();
        cg.toClass();
        cg.release(); // should not throw
    }

    @Test
    public void shouldGetClassPoolForLoader() {
        assertNotNull(ClassGenerator.getClassPool(ClassLoader.getSystemClassLoader()));
    }

    @Test
    public void shouldGetDefaultClassPoolForNullLoader() {
        assertNotNull(ClassGenerator.getClassPool(null));
    }
}
