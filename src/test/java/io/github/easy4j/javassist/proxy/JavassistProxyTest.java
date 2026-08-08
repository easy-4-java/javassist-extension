package io.github.easy4j.javassist.proxy;

import org.junit.Test;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.MethodFilter;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class JavassistProxyTest {

    public static class SampleClass {
        public String hello() { return "hello"; }
        public int add(int a, int b) { return a + b; }
    }

    @Test
    public void shouldGetProxyFromTarget() throws Exception {
        SampleClass target = new SampleClass();
        SampleClass proxy = JavassistProxy.getProxy(target);
        assertNotNull(proxy);
    }

    @Test
    public void shouldGetProxyFromClass() throws Exception {
        SampleClass proxy = JavassistProxy.getProxy(SampleClass.class);
        assertNotNull(proxy);
    }

    @Test
    public void shouldGetProxyWithCustomHandler() throws Exception {
        SampleClass proxy = JavassistProxy.getProxy(SampleClass.class, new MethodHandler() {
            public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
                return "intercepted";
            }
        });
        assertNotNull(proxy);
        assertEquals("intercepted", proxy.hello());
    }

    @Test
    public void shouldInvokeThroughProxy() throws Exception {
        SampleClass proxy = JavassistProxy.getProxy(SampleClass.class, new MethodHandler() {
            public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
                return proceed.invoke(self, args);
            }
        });
        assertEquals("hello", proxy.hello());
        assertEquals(3, proxy.add(1, 2));
    }

    @Test
    public void shouldGetProxyFromTargetObject() throws Exception {
        SampleClass target = new SampleClass();
        SampleClass proxy = JavassistProxy.getProxy(target);
        assertNotNull(proxy);
        // default handler prints to stdout and delegates
        String result = proxy.hello();
        assertNotNull(result);
    }
}
