package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ProxyTest {

    public interface Greeter {
        String greet(String name);
        void sayBye();
    }

    @Test
    public void shouldGetProxy() {
        Proxy proxy = Proxy.getProxy(Greeter.class);
        assertNotNull(proxy);
    }

    @Test
    public void shouldGetProxyWithClassLoader() {
        Proxy proxy = Proxy.getProxy(Greeter.class.getClassLoader(), Greeter.class);
        assertNotNull(proxy);
    }

    @Test
    public void shouldCreateInstanceWithDefaultHandler() {
        Proxy proxy = Proxy.getProxy(Greeter.class);
        Object instance = proxy.newInstance();
        assertNotNull(instance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowWhenInvokingDefaultHandler() throws Throwable {
        Proxy proxy = Proxy.getProxy(Greeter.class);
        Greeter g = (Greeter) proxy.newInstance();
        g.greet("test");
    }

    @Test
    public void shouldCreateInstanceWithCustomHandler() {
        Proxy proxy = Proxy.getProxy(Greeter.class);
        Object instance = proxy.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                if ("greet".equals(method.getName())) {
                    return "Hello " + args[0];
                }
                return null;
            }
        });
        assertNotNull(instance);
        Greeter g = (Greeter) instance;
        assertEquals("Hello World", g.greet("World"));
    }

    @Test
    public void shouldHandleVoidReturn() {
        Proxy proxy = Proxy.getProxy(Greeter.class);
        final boolean[] called = {false};
        Greeter g = (Greeter) proxy.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                if ("sayBye".equals(method.getName())) {
                    called[0] = true;
                }
                return null;
            }
        });
        g.sayBye();
        assertTrue(called[0]);
    }

    @Test
    public void shouldCacheProxyPerClassLoader() {
        Proxy p1 = Proxy.getProxy(Greeter.class);
        Proxy p2 = Proxy.getProxy(Greeter.class);
        assertSame(p1, p2);
    }

    @Test
    public void shouldHaveReturnNullInvoker() throws Throwable {
        assertNotNull(Proxy.RETURN_NULL_INVOKER);
        Object result = Proxy.RETURN_NULL_INVOKER.invoke(null, null, null);
        assertNull(result);
    }

    @Test
    public void shouldHaveThrowUnsupportedInvoker() {
        assertNotNull(Proxy.THROW_UNSUPPORTED_INVOKER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedInvoker() throws Throwable {
        Method m = Object.class.getMethod("toString");
        Proxy.THROW_UNSUPPORTED_INVOKER.invoke(null, m, null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenNotInterface() {
        Proxy.getProxy(String.class);
    }
}
