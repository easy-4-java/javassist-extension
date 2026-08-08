package io.github.easy4j.javassist.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectUtilsTest {

    @Test
    public void shouldIdentifyPrimitiveTypes() {
        assertTrue(ReflectUtils.isPrimitive(int.class));
        assertTrue(ReflectUtils.isPrimitive(String.class));
        assertTrue(ReflectUtils.isPrimitive(Date.class));
        assertFalse(ReflectUtils.isPrimitive(Object.class));
    }

    @Test
    public void shouldIdentifyPrimitiveArrays() {
        assertTrue(ReflectUtils.isPrimitives(int[].class));
        assertTrue(ReflectUtils.isPrimitives(String[].class));
        assertFalse(ReflectUtils.isPrimitives(Object.class));
    }

    @Test
    public void shouldGetBoxedClass() {
        assertEquals(Integer.class, ReflectUtils.getBoxedClass(int.class));
        assertEquals(Boolean.class, ReflectUtils.getBoxedClass(boolean.class));
        assertEquals(Long.class, ReflectUtils.getBoxedClass(long.class));
        assertEquals(Float.class, ReflectUtils.getBoxedClass(float.class));
        assertEquals(Double.class, ReflectUtils.getBoxedClass(double.class));
        assertEquals(Character.class, ReflectUtils.getBoxedClass(char.class));
        assertEquals(Byte.class, ReflectUtils.getBoxedClass(byte.class));
        assertEquals(Short.class, ReflectUtils.getBoxedClass(short.class));
        assertEquals(String.class, ReflectUtils.getBoxedClass(String.class));
    }

    @Test
    public void shouldCheckCompatibility() {
        assertTrue(ReflectUtils.isCompatible(String.class, "hello"));
        assertFalse(ReflectUtils.isCompatible(int.class, null));
        assertTrue(ReflectUtils.isCompatible(Object.class, null));
        assertTrue(ReflectUtils.isCompatible(int.class, 42));
    }

    @Test
    public void shouldCheckCompatibilityArrays() {
        assertTrue(ReflectUtils.isCompatible(new Class<?>[]{String.class}, new Object[]{"a"}));
        assertFalse(ReflectUtils.isCompatible(new Class<?>[]{String.class, Integer.class}, new Object[]{"a"}));
        assertTrue(ReflectUtils.isCompatible(new Class<?>[0], new Object[0]));
    }

    @Test
    public void shouldGetCodeBase() {
        // String.class may have null CodeSource depending on JDK; just test the method works
        String codeBase = ReflectUtils.getCodeBase(ReflectUtilsTest.class);
        assertNotNull(codeBase);
    }

    @Test
    public void shouldReturnNullCodeBaseForNull() {
        assertNull(ReflectUtils.getCodeBase(null));
    }

    @Test
    public void shouldGetName() {
        assertEquals("java.lang.String", ReflectUtils.getName(String.class));
        assertEquals("int", ReflectUtils.getName(int.class));
        assertEquals("java.lang.String[]", ReflectUtils.getName(String[].class));
    }

    @Test
    public void shouldGetNameForMethod() throws Exception {
        Method m = String.class.getMethod("substring", int.class);
        String name = ReflectUtils.getName(m);
        assertNotNull(name);
        assertTrue(name.contains("substring"));
    }

    @Test
    public void shouldGetNameForConstructor() throws Exception {
        Constructor<String> c = String.class.getConstructor();
        String name = ReflectUtils.getName(c);
        assertNotNull(name);
    }

    @Test
    public void shouldGetSignature() {
        String sig = ReflectUtils.getSignature("method1", new Class<?>[]{String.class, int.class});
        assertNotNull(sig);
        assertTrue(sig.contains("method1"));
    }

    @Test
    public void shouldGetSignatureWithNullParams() {
        String sig = ReflectUtils.getSignature("method1", null);
        assertNotNull(sig);
    }

    @Test
    public void shouldGetSignatureWithEmptyParams() {
        String sig = ReflectUtils.getSignature("method1", new Class<?>[0]);
        assertNotNull(sig);
    }

    @Test
    public void shouldGetDesc() {
        assertEquals("I", ReflectUtils.getDesc(int.class));
        assertEquals("V", ReflectUtils.getDesc(void.class));
        assertEquals("Z", ReflectUtils.getDesc(boolean.class));
        assertEquals("B", ReflectUtils.getDesc(byte.class));
        assertEquals("C", ReflectUtils.getDesc(char.class));
        assertEquals("D", ReflectUtils.getDesc(double.class));
        assertEquals("F", ReflectUtils.getDesc(float.class));
        assertEquals("J", ReflectUtils.getDesc(long.class));
        assertEquals("S", ReflectUtils.getDesc(short.class));
        assertEquals("Ljava/lang/String;", ReflectUtils.getDesc(String.class));
        assertEquals("[I", ReflectUtils.getDesc(int[].class));
    }

    @Test
    public void shouldGetDescForClassArray() {
        String desc = ReflectUtils.getDesc(new Class<?>[]{int.class, String.class});
        assertEquals("ILjava/lang/String;", desc);
        assertEquals("", ReflectUtils.getDesc(new Class<?>[0]));
    }

    @Test
    public void shouldGetDescForMethod() throws Exception {
        Method m = String.class.getMethod("length");
        String desc = ReflectUtils.getDesc(m);
        assertNotNull(desc);
        assertTrue(desc.contains("length"));
    }

    @Test
    public void shouldGetDescForConstructor() throws Exception {
        Constructor<String> c = String.class.getConstructor();
        String desc = ReflectUtils.getDesc(c);
        assertNotNull(desc);
        assertTrue(desc.endsWith("V"));
    }

    @Test
    public void shouldGetDescWithoutMethodName() throws Exception {
        Method m = String.class.getMethod("length");
        String desc = ReflectUtils.getDescWithoutMethodName(m);
        assertNotNull(desc);
        assertTrue(desc.startsWith("("));
    }

    @Test
    public void shouldConvertNameToDesc() {
        assertEquals("I", ReflectUtils.name2desc("int"));
        assertEquals("Ljava/lang/String;", ReflectUtils.name2desc("java.lang.String"));
        assertEquals("[I", ReflectUtils.name2desc("int[]"));
    }

    @Test
    public void shouldConvertDescToName() {
        assertEquals("int", ReflectUtils.desc2name("I"));
        assertEquals("java.lang.String", ReflectUtils.desc2name("Ljava/lang/String;"));
        assertEquals("int[]", ReflectUtils.desc2name("[I"));
        assertEquals("void", ReflectUtils.desc2name("V"));
        assertEquals("boolean", ReflectUtils.desc2name("Z"));
        assertEquals("byte", ReflectUtils.desc2name("B"));
        assertEquals("char", ReflectUtils.desc2name("C"));
        assertEquals("double", ReflectUtils.desc2name("D"));
        assertEquals("float", ReflectUtils.desc2name("F"));
        assertEquals("long", ReflectUtils.desc2name("J"));
        assertEquals("short", ReflectUtils.desc2name("S"));
    }

    @Test
    public void shouldConvertNameToClass() throws Exception {
        assertEquals(String.class, ReflectUtils.name2class("java.lang.String"));
        assertEquals(int.class, ReflectUtils.name2class("int"));
        assertEquals(void.class, ReflectUtils.name2class("void"));
    }

    @Test
    public void shouldConvertDescToClass() throws Exception {
        assertEquals(int.class, ReflectUtils.desc2class("I"));
        assertEquals(String.class, ReflectUtils.desc2class("Ljava/lang/String;"));
    }

    @Test(expected = ClassNotFoundException.class)
    public void shouldThrowWhenDescToClassInvalid() throws Exception {
        ReflectUtils.desc2class("X");
    }

    @Test
    public void shouldConvertDescToClassArray() throws Exception {
        Class<?>[] classes = ReflectUtils.desc2classArray("ILjava/lang/String;");
        assertEquals(2, classes.length);
        assertEquals(int.class, classes[0]);
        assertEquals(String.class, classes[1]);
    }

    @Test
    public void shouldReturnEmptyArrayForEmptyDesc() throws Exception {
        Class<?>[] classes = ReflectUtils.desc2classArray("");
        assertEquals(0, classes.length);
    }

    @Test
    public void shouldForName() {
        assertEquals(String.class, ReflectUtils.forName("java.lang.String"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowWhenForNameNotFound() {
        ReflectUtils.forName("com.nonexistent.FakeClass");
    }

    @Test
    public void shouldFindMethodBySignature() throws Exception {
        Method m = ReflectUtils.findMethodByMethodSignature(String.class, "length", null);
        assertNotNull(m);
        assertEquals("length", m.getName());
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldThrowWhenMethodNotFoundBySignature() throws Exception {
        ReflectUtils.findMethodByMethodSignature(String.class, "nonExistent", null);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowWhenAmbiguousMethodSignature() throws Exception {
        ReflectUtils.findMethodByMethodSignature(String.class, "valueOf", null);
    }

    @Test
    public void shouldFindMethodBySignatureWithParams() throws Exception {
        Method m = ReflectUtils.findMethodByMethodSignature(String.class, "substring", new String[]{"int"});
        assertNotNull(m);
    }

    @Test
    public void shouldFindMethodByName() throws Exception {
        Method m = ReflectUtils.findMethodByMethodName(String.class, "length");
        assertNotNull(m);
    }

    @Test
    public void shouldFindConstructor() throws Exception {
        Constructor<?> c = ReflectUtils.findConstructor(String.class, String.class);
        assertNotNull(c);
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldThrowWhenConstructorNotFound() throws Exception {
        ReflectUtils.findConstructor(String.class, List.class);
    }

    @Test
    public void shouldCheckIsInstance() {
        assertTrue(ReflectUtils.isInstance("hello", "java.io.Serializable"));
        assertFalse(ReflectUtils.isInstance("hello", "java.lang.Integer"));
    }

    @Test
    public void shouldGetEmptyObject() {
        Object obj = ReflectUtils.getEmptyObject(String.class);
        assertEquals("", obj);
    }

    @Test
    public void shouldGetEmptyObjectForPrimitive() {
        assertEquals(false, ReflectUtils.getEmptyObject(boolean.class));
        assertEquals('\0', ReflectUtils.getEmptyObject(char.class));
        assertEquals((byte) 0, ReflectUtils.getEmptyObject(byte.class));
        assertEquals((short) 0, ReflectUtils.getEmptyObject(short.class));
        assertEquals(0, ReflectUtils.getEmptyObject(int.class));
        assertEquals(0L, ReflectUtils.getEmptyObject(long.class));
        assertEquals(0F, ReflectUtils.getEmptyObject(float.class));
        assertEquals(0D, ReflectUtils.getEmptyObject(double.class));
    }

    @Test
    public void shouldGetEmptyObjectForArray() {
        Object obj = ReflectUtils.getEmptyObject(String[].class);
        assertNotNull(obj);
        assertTrue(obj.getClass().isArray());
        assertEquals(0, ((String[]) obj).length);
    }

    @Test
    public void shouldGetEmptyObjectForList() {
        Object obj = ReflectUtils.getEmptyObject(ArrayList.class);
        assertNotNull(obj);
        assertTrue(obj instanceof ArrayList);
    }

    @Test
    public void shouldGetEmptyObjectForMap() {
        Object obj = ReflectUtils.getEmptyObject(HashMap.class);
        assertNotNull(obj);
        assertTrue(obj instanceof HashMap);
    }

    @Test
    public void shouldCheckBeanPropertyReadMethod() throws Exception {
        Method m = TestBean.class.getMethod("getName");
        assertTrue(ReflectUtils.isBeanPropertyReadMethod(m));
    }

    @Test
    public void shouldNotBeBeanPropertyReadMethodForVoid() throws Exception {
        Method m = System.class.getMethod("gc");
        assertFalse(ReflectUtils.isBeanPropertyReadMethod(m));
    }

    @Test
    public void shouldNotBeBeanPropertyReadMethodForNull() {
        assertFalse(ReflectUtils.isBeanPropertyReadMethod(null));
    }

    @Test
    public void shouldGetPropertyNameFromBeanReadMethod() throws Exception {
        Method m = TestBean.class.getMethod("getName");
        assertEquals("name", ReflectUtils.getPropertyNameFromBeanReadMethod(m));
    }

    @Test
    public void shouldGetPropertyNameFromIsMethod() throws Exception {
        Method m = TestBean.class.getMethod("isActive");
        assertEquals("active", ReflectUtils.getPropertyNameFromBeanReadMethod(m));
    }

    @Test
    public void shouldCheckBeanPropertyWriteMethod() throws Exception {
        Method m = TestBean.class.getMethod("setName", String.class);
        assertTrue(ReflectUtils.isBeanPropertyWriteMethod(m));
    }

    @Test
    public void shouldNotBeBeanPropertyWriteMethodForNull() {
        assertFalse(ReflectUtils.isBeanPropertyWriteMethod(null));
    }

    @Test
    public void shouldGetPropertyNameFromBeanWriteMethod() throws Exception {
        Method m = TestBean.class.getMethod("setName", String.class);
        assertEquals("name", ReflectUtils.getPropertyNameFromBeanWriteMethod(m));
    }

    @Test
    public void shouldReturnNullForNonSetter() throws Exception {
        Method m = String.class.getMethod("length");
        assertNull(ReflectUtils.getPropertyNameFromBeanWriteMethod(m));
    }

    @Test
    public void shouldCheckPublicInstanceField() throws Exception {
        assertTrue(ReflectUtils.isPublicInstanceField(TestBean.class.getField("publicField")));
    }

    @Test
    public void shouldGetBeanPropertyFields() {
        java.util.Map<String, java.lang.reflect.Field> fields = ReflectUtils.getBeanPropertyFields(TestBean.class);
        assertNotNull(fields);
    }

    @Test
    public void shouldGetBeanPropertyReadMethods() {
        java.util.Map<String, Method> methods = ReflectUtils.getBeanPropertyReadMethods(TestBean.class);
        assertNotNull(methods);
    }

    @Test
    public void shouldGetGenericClass() {
        // HashMap implements Map<K,V>, getGenericClass gets the first type arg
        // Use a class that actually has a typed generic interface
        Class<?> cls = ReflectUtils.getGenericClass(TypeRef.class);
        assertNotNull(cls);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenGenericClassUndefined() {
        ReflectUtils.getGenericClass(String.class);
    }

    @Test
    public void shouldGetGenericClassAtIndex() {
        Class<?> cls = ReflectUtils.getGenericClass(TypeRef.class, 0);
        assertNotNull(cls);
    }

    // Helper interface with a known generic type
    interface TypeRef extends Comparable<String> {}

    // Inner helper class for bean property tests
    public static class TestBean {
        public String publicField;
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public boolean isActive() { return true; }
    }
}
