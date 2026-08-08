package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;

import static org.junit.Assert.*;

public class CtFieldBuilderTest {

    private final ClassPool pool = ClassPool.getDefault();

    @Test
    public void shouldCreateField() throws Exception {
        CtClass cc = pool.makeClass("test.CtFieldBuilderTest1" + System.nanoTime());
        CtClass fieldType = pool.get("java.lang.String");
        CtFieldBuilder builder = CtFieldBuilder.create(cc, fieldType, "myField");
        assertNotNull(builder);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldCreateFieldWithValue() throws Exception {
        CtClass cc = pool.makeClass("test.CtFieldBuilderTest2" + System.nanoTime());
        CtClass fieldType = pool.get("java.lang.String");
        CtFieldBuilder builder = CtFieldBuilder.create(cc, fieldType, "myField", "default");
        assertNotNull(builder);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldReturnExistingField() throws Exception {
        CtClass cc = pool.makeClass("test.CtFieldBuilderTest3" + System.nanoTime());
        CtClass fieldType = pool.get("java.lang.String");
        CtFieldBuilder builder1 = CtFieldBuilder.create(cc, fieldType, "existingField");
        assertNotNull(builder1.build());
        // create again with same name
        CtFieldBuilder builder2 = CtFieldBuilder.create(cc, fieldType, "existingField");
        assertNotNull(builder2.build());
    }
}
