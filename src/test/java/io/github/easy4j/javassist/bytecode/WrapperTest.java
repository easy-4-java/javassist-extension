package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import io.github.easy4j.javassist.exception.NoSuchPropertyException;

import static org.junit.Assert.*;

public class WrapperTest {

    public static class SampleBean {
        public String name;
        public int age;
        private boolean active;

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }

        public String greet(String greeting) { return greeting + " " + name; }
        public void doNothing() { }
    }

    @Test
    public void shouldGetWrapperForClass() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        assertNotNull(w);
    }

    @Test
    public void shouldGetCachedWrapper() {
        Wrapper w1 = Wrapper.getWrapper(SampleBean.class);
        Wrapper w2 = Wrapper.getWrapper(SampleBean.class);
        assertSame(w1, w2);
    }

    @Test
    public void shouldGetObjectWrapper() {
        Wrapper w = Wrapper.getWrapper(Object.class);
        assertNotNull(w);
    }

    @Test
    public void shouldGetPropertyNames() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        String[] names = w.getPropertyNames();
        assertNotNull(names);
        assertTrue(names.length > 0);
    }

    @Test
    public void shouldGetPropertyType() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        Class<?> type = w.getPropertyType("name");
        assertEquals(String.class, type);
    }

    @Test
    public void shouldReturnNullForUnknownPropertyType() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        assertNull(w.getPropertyType("nonExistent"));
    }

    @Test
    public void shouldCheckHasProperty() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        assertTrue(w.hasProperty("name"));
        assertFalse(w.hasProperty("nonExistent"));
    }

    @Test
    public void shouldGetPropertyValue() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        SampleBean bean = new SampleBean();
        bean.name = "test";
        Object value = w.getPropertyValue(bean, "name");
        assertEquals("test", value);
    }

    @Test(expected = NoSuchPropertyException.class)
    public void shouldThrowWhenGetNonExistentProperty() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        w.getPropertyValue(new SampleBean(), "nonExistent");
    }

    @Test
    public void shouldSetPropertyValue() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        SampleBean bean = new SampleBean();
        w.setPropertyValue(bean, "name", "newValue");
        assertEquals("newValue", bean.name);
    }

    @Test(expected = NoSuchPropertyException.class)
    public void shouldThrowWhenSetNonExistentProperty() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        w.setPropertyValue(new SampleBean(), "nonExistent", "value");
    }

    @Test
    public void shouldGetPropertyValues() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        SampleBean bean = new SampleBean();
        bean.name = "test";
        bean.age = 25;
        Object[] values = w.getPropertyValues(bean, new String[]{"name", "age"});
        assertNotNull(values);
        assertEquals(2, values.length);
        assertEquals("test", values[0]);
    }

    @Test
    public void shouldSetPropertyValues() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        SampleBean bean = new SampleBean();
        w.setPropertyValues(bean, new String[]{"name", "age"}, new Object[]{"test", 25});
        assertEquals("test", bean.name);
        assertEquals(25, bean.age);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSetPropertyValuesMismatchedLength() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        w.setPropertyValues(new SampleBean(), new String[]{"name"}, new Object[]{"a", "b"});
    }

    @Test
    public void shouldGetMethodNames() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        String[] names = w.getMethodNames();
        assertNotNull(names);
        assertTrue(names.length > 0);
    }

    @Test
    public void shouldGetDeclaredMethodNames() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        String[] names = w.getDeclaredMethodNames();
        assertNotNull(names);
        assertTrue(names.length > 0);
    }

    @Test
    public void shouldCheckHasMethod() {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        assertTrue(w.hasMethod("greet"));
        assertFalse(w.hasMethod("nonExistentMethod"));
    }

    @Test
    public void shouldInvokeMethod() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        SampleBean bean = new SampleBean();
        bean.name = "World";
        Object result = w.invokeMethod(bean, "greet", new Class<?>[]{String.class}, new Object[]{"Hello"});
        assertEquals("Hello World", result);
    }

    @Test
    public void shouldInvokeVoidMethod() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        Object result = w.invokeMethod(new SampleBean(), "doNothing", new Class<?>[0], new Object[0]);
        assertNull(result);
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldThrowWhenInvokeNonExistentMethod() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        w.invokeMethod(new SampleBean(), "nonExistent", new Class<?>[0], new Object[0]);
    }

    @Test
    public void shouldHandleObjectMethods() throws Exception {
        Wrapper w = Wrapper.getWrapper(Object.class);
        Object obj = new Object();
        assertEquals(obj.getClass(), w.invokeMethod(obj, "getClass", new Class<?>[0], new Object[0]));
        assertEquals(obj.hashCode(), w.invokeMethod(obj, "hashCode", new Class<?>[0], new Object[0]));
        assertEquals(obj.toString(), w.invokeMethod(obj, "toString", new Class<?>[0], new Object[0]));
        assertEquals(obj.equals("a"), w.invokeMethod(obj, "equals", new Class<?>[]{Object.class}, new Object[]{"a"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenObjectEqualsWrongArgs() throws Exception {
        Wrapper w = Wrapper.getWrapper(Object.class);
        w.invokeMethod(new Object(), "equals", new Class<?>[]{Object.class, Object.class}, new Object[]{"a", "b"});
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldThrowWhenObjectMethodNotFound() throws Exception {
        Wrapper w = Wrapper.getWrapper(Object.class);
        w.invokeMethod(new Object(), "nonExistent", new Class<?>[0], new Object[0]);
    }

    @Test(expected = NoSuchPropertyException.class)
    public void shouldThrowWhenObjectGetProperty() throws Exception {
        Wrapper w = Wrapper.getWrapper(Object.class);
        w.getPropertyValue(new Object(), "name");
    }

    @Test(expected = NoSuchPropertyException.class)
    public void shouldThrowWhenObjectSetProperty() throws Exception {
        Wrapper w = Wrapper.getWrapper(Object.class);
        w.setPropertyValue(new Object(), "name", "value");
    }

    @Test
    public void shouldReturnEmptyPropertyNamesForObject() {
        Wrapper w = Wrapper.getWrapper(Object.class);
        assertEquals(0, w.getPropertyNames().length);
    }

    @Test
    public void shouldReturnFalseForHasPropertyOnObject() {
        Wrapper w = Wrapper.getWrapper(Object.class);
        assertFalse(w.hasProperty("name"));
    }

    @Test
    public void shouldReturnNullForPropertyTypeOnObject() {
        Wrapper w = Wrapper.getWrapper(Object.class);
        assertNull(w.getPropertyType("name"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenPrimitiveType() {
        Wrapper.getWrapper(int.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenGetPropertyValueWithWrongInstanceType() throws Exception {
        Wrapper w = Wrapper.getWrapper(SampleBean.class);
        w.getPropertyValue("not a bean", "name");
    }
}
