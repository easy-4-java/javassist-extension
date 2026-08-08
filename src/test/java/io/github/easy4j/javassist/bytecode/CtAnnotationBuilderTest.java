package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import static org.junit.Assert.*;

public class CtAnnotationBuilderTest {

    private final ConstPool cp = new ConstPool("java.lang.String");
    private final ClassPool pool = ClassPool.getDefault();

    @Test
    public void shouldCreateFromAnnotationClass() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        assertNotNull(builder);
    }

    @Test
    public void shouldCreateFromAnnotationInstance() {
        Deprecated dep = AnnotatedClass.class.getAnnotation(Deprecated.class);
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(dep, cp);
        assertNotNull(builder);
    }

    @Test
    public void shouldBuildAnnotation() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        Annotation ann = builder.build();
        assertNotNull(ann);
        assertEquals("java.lang.Deprecated", ann.getTypeName());
    }

    @Test
    public void shouldAddBooleanMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addBooleanMember("flag", true);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddBooleanArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addBooleanMember("flags", new boolean[]{true, false});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddByteMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addByteMember("b", (byte) 1);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddByteArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addByteMember("bs", new byte[]{1, 2});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddCharMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addCharMember("c", 'a');
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddCharArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addCharMember("cs", new char[]{'a', 'b'});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddClassMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addClassMember("cls", "java.lang.String");
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddClassArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addClassMember("clss", new String[]{"java.lang.String", "java.lang.Integer"});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddDoubleMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addDoubleMember("d", 3.14);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddDoubleArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addDoubleMember("ds", new double[]{1.0, 2.0});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddEnumMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addEnumMember("e", TestEnum.A);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddEnumArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addEnumMember("es", new TestEnum[]{TestEnum.A, TestEnum.B});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddFloatMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addFloatMember("f", 1.5f);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddFloatArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addFloatMember("fs", new float[]{1.0f, 2.0f});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddIntegerMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addIntegerMember("i", 42);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddIntegerArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addIntegerMember("is", new int[]{1, 2});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddLongMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addLongMember("l", 100L);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddLongArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addLongMember("ls", new long[]{1L, 2L});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddShortMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addShortMember("s", (short) 1);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddShortArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addShortMember("ss", new short[]{1, 2});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddStringMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addStringMember("str", "hello");
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddStringArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.addStringMember("strs", new String[]{"a", "b"});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddAnnotationMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        Annotation inner = new Annotation("java.lang.SuppressWarnings", cp);
        builder.addAnnotationMember("ann", inner);
        assertNotNull(builder.build());
    }

    @Test
    public void shouldAddAnnotationArrayMember() {
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        Annotation inner = new Annotation("java.lang.SuppressWarnings", cp);
        builder.addAnnotationMember("anns", new Annotation[]{inner});
        assertNotNull(builder.build());
    }

    @Test
    public void shouldMarkClass() throws Exception {
        CtClass cc = pool.makeClass("test.MarkClass" + System.nanoTime());
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.markClass(cc);
        assertNotNull(cc);
    }

    @Test
    public void shouldMarkField() throws Exception {
        CtClass cc = pool.makeClass("test.MarkFieldClass" + System.nanoTime());
        CtClass fieldType = pool.get("java.lang.String");
        javassist.CtField field = new javassist.CtField(fieldType, "testField", cc);
        cc.addField(field);
        CtAnnotationBuilder builder = CtAnnotationBuilder.create(Deprecated.class, cp);
        builder.markField(field);
        assertNotNull(field);
    }

    enum TestEnum { A, B }

    @Deprecated
    static class AnnotatedClass {}
}
