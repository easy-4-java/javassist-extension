package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MixinTest {

    public interface Greetable {
        String greet();
    }

    public interface NameAware {
        String getName();
    }

    public static class GreetDelegate implements Greetable {
        public String greet() { return "Hello"; }
    }

    public static class NameDelegate implements NameAware {
        public String getName() { return "World"; }
    }

    public static class CombinedDelegate implements Greetable, NameAware {
        public String greet() { return "Hi"; }
        public String getName() { return "Combined"; }
    }

    @Test
    public void shouldMixinWithSingleDelegate() {
        Mixin mixin = Mixin.mixin(new Class<?>[]{Greetable.class}, new Class<?>[]{GreetDelegate.class});
        assertNotNull(mixin);
        Object instance = mixin.newInstance(new Object[]{new GreetDelegate()});
        assertNotNull(instance);
        assertTrue(instance instanceof Greetable);
        assertEquals("Hello", ((Greetable) instance).greet());
    }

    @Test
    public void shouldMixinWithMultipleDelegates() {
        Mixin mixin = Mixin.mixin(
                new Class<?>[]{Greetable.class, NameAware.class},
                new Class<?>[]{GreetDelegate.class, NameDelegate.class}
        );
        assertNotNull(mixin);
        Object instance = mixin.newInstance(new Object[]{new GreetDelegate(), new NameDelegate()});
        assertNotNull(instance);
        assertTrue(instance instanceof Greetable);
        assertTrue(instance instanceof NameAware);
        assertEquals("Hello", ((Greetable) instance).greet());
        assertEquals("World", ((NameAware) instance).getName());
    }

    @Test
    public void shouldMixinWithSingleClassArray() {
        Mixin mixin = Mixin.mixin(
                new Class<?>[]{Greetable.class, NameAware.class},
                CombinedDelegate.class
        );
        assertNotNull(mixin);
        Object instance = mixin.newInstance(new Object[]{new CombinedDelegate()});
        assertNotNull(instance);
        assertEquals("Hi", ((Greetable) instance).greet());
        assertEquals("Combined", ((NameAware) instance).getName());
    }

    @Test
    public void shouldMixinWithClassLoader() {
        Mixin mixin = Mixin.mixin(
                new Class<?>[]{Greetable.class},
                new Class<?>[]{GreetDelegate.class},
                ClassLoader.getSystemClassLoader()
        );
        assertNotNull(mixin);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenNotInterface() {
        Mixin.mixin(new Class<?>[]{String.class}, new Class<?>[]{GreetDelegate.class});
    }

    @Test
    public void shouldSupportMixinAware() {
        Mixin mixin = Mixin.mixin(
                new Class<?>[]{Greetable.class},
                new Class<?>[]{GreetDelegate.class}
        );
        assertNotNull(mixin);
    }
}
