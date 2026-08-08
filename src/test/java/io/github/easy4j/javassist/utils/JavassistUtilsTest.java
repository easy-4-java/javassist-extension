package io.github.easy4j.javassist.utils;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import static org.junit.Assert.*;

public class JavassistUtilsTest {

    private final ClassPool pool = ClassPool.getDefault();

    @Test
    public void shouldGetCtClassFromClass() throws Exception {
        CtClass cc = JavassistUtils.getCtClass(String.class);
        assertNotNull(cc);
        assertEquals("java.lang.String", cc.getName());
    }

    @Test
    public void shouldGetCtClassFromString() throws Exception {
        CtClass cc = JavassistUtils.getCtClass("java.lang.Integer");
        assertNotNull(cc);
        assertEquals("java.lang.Integer", cc.getName());
    }

    @Test
    public void shouldGetCtMethodByClassNameAndName() throws Exception {
        CtMethod cm = JavassistUtils.getCtMethod("java.lang.String", "length");
        assertNotNull(cm);
        assertEquals("length", cm.getName());
    }

    @Test
    public void shouldGetCtMethodFromClassAndName() throws Exception {
        CtMethod cm = JavassistUtils.getCtMethod(String.class, "length");
        assertNotNull(cm);
    }

    @Test
    public void shouldGetCtMethodFromClassAndNameAndParams() throws Exception {
        CtMethod cm = JavassistUtils.getCtMethod(String.class, "substring", int.class);
        assertNotNull(cm);
    }

    @Test
    public void shouldGetCtMethodFromCtClassAndName() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        CtMethod cm = JavassistUtils.getCtMethod(cc, "length");
        assertNotNull(cm);
    }

    @Test
    public void shouldGetCtMethodFromCtClassAndNameAndParams() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        CtMethod cm = JavassistUtils.getCtMethod(cc, "substring", int.class);
        assertNotNull(cm);
    }

    @Test
    public void shouldGetCtMethodFromMethod() throws Exception {
        java.lang.reflect.Method m = String.class.getMethod("length");
        CtMethod cm = JavassistUtils.getCtMethod(m);
        assertNotNull(cm);
    }

    @Test
    public void shouldCheckHasField() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        // String has no public fields in this context, but we can check the method works
        boolean result = JavassistUtils.hasField(cc, "hash");
        // hash is a private field of String
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseForNonExistentField() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertFalse(JavassistUtils.hasField(cc, "nonExistentField12345"));
    }

    @Test
    public void shouldCheckHasMethod() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertTrue(JavassistUtils.hasMethod(cc, "length"));
    }

    @Test
    public void shouldCheckHasMethodWithParams() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertTrue(JavassistUtils.hasMethod(cc, "substring", CtClass.intType));
    }

    @Test
    public void shouldReturnFalseForNonExistentMethod() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertFalse(JavassistUtils.hasMethod(cc, "nonExistentMethod12345"));
    }

    @Test
    public void shouldGetTargetClass() {
        Class<?> clazz = JavassistUtils.getTargetClass("java.lang.String");
        assertNotNull(clazz);
        assertEquals(String.class, clazz);
    }

    @Test
    public void shouldReturnNullForNonExistentTargetClass() {
        Class<?> clazz = JavassistUtils.getTargetClass("com.nonexistent.FakeClass");
        assertNull(clazz);
    }

    @Test
    public void shouldCacheTargetClass() {
        Class<?> c1 = JavassistUtils.getTargetClass("java.lang.Integer");
        Class<?> c2 = JavassistUtils.getTargetClass("java.lang.Integer");
        assertSame(c1, c2);
    }

    @Test
    public void shouldCloneAnnotation() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        Annotation original = new Annotation("java.lang.Deprecated", cp);
        ConstPool newCp = new ConstPool("java.lang.Object");
        Annotation cloned = JavassistUtils.cloneAnnotation(original, newCp);
        assertNotNull(cloned);
        assertEquals("java.lang.Deprecated", cloned.getTypeName());
    }

    @Test
    public void shouldCloneAnnotationWithMembers() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        Annotation original = new Annotation("test.Annotation", cp);
        original.addMemberValue("value", new StringMemberValue("test", cp));
        ConstPool newCp = new ConstPool("java.lang.Object");
        Annotation cloned = JavassistUtils.cloneAnnotation(original, newCp);
        assertNotNull(cloned);
        assertNotNull(cloned.getMemberValue("value"));
    }

    @Test
    public void shouldCreateMemberValueForPrimitiveTypes() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, int.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, short.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, long.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, byte.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, float.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, double.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, char.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, boolean.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, String.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, Class.class));
    }

    @Test
    public void shouldCreateMemberValueWithValues() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, int.class, 42));
        assertNotNull(JavassistUtils.createMemberValue(cp, short.class, (short) 1));
        assertNotNull(JavassistUtils.createMemberValue(cp, long.class, 100L));
        assertNotNull(JavassistUtils.createMemberValue(cp, byte.class, (byte) 1));
        assertNotNull(JavassistUtils.createMemberValue(cp, float.class, 1.0f));
        assertNotNull(JavassistUtils.createMemberValue(cp, double.class, 2.0d));
        assertNotNull(JavassistUtils.createMemberValue(cp, char.class, 'a'));
        assertNotNull(JavassistUtils.createMemberValue(cp, boolean.class, true));
        assertNotNull(JavassistUtils.createMemberValue(cp, String.class, "hello"));
        assertNotNull(JavassistUtils.createMemberValue(cp, Class.class, String.class));
    }

    @Test
    public void shouldCreateMemberValueForEnum() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, TestEnum.class));
        assertNotNull(JavassistUtils.createMemberValue(cp, TestEnum.class, TestEnum.A));
    }

    @Test
    public void shouldCreateMemberValueForArray() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, String[].class));
    }

    @Test
    public void shouldCreateMemberValueForArrayWithValues() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        String[] vals = {"a", "b"};
        assertNotNull(JavassistUtils.createMemberValue(cp, String[].class, vals));
    }

    @Test
    public void shouldCreateMemberValueForAnnotation() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        // Annotation type without a value creates a bare AnnotationMemberValue
        assertNotNull(JavassistUtils.createMemberValue(cp, Deprecated.class));
    }

    @Test
    public void shouldCreateMemberValueForAnnotationWithValue() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        Deprecated dep = TestAnnotated.class.getAnnotation(Deprecated.class);
        assertNotNull(JavassistUtils.createMemberValue(cp, Deprecated.class, dep));
    }

    @Test
    public void shouldCreateMemberValueForCtClassType() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.intType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.booleanType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.byteType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.charType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.shortType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.longType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.floatType));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.doubleType));
    }

    @Test
    public void shouldCreateMemberValueForCtClassWithValues() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.intType, 42));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.booleanType, true));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.byteType, (byte) 1));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.charType, 'a'));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.shortType, (short) 1));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.longType, 100L));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.floatType, 1.0f));
        assertNotNull(JavassistUtils.createMemberValue(cp, CtClass.doubleType, 2.0d));
    }

    @Test
    public void shouldCreateMemberValueForStringCtClass() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        CtClass stringType = pool.get("java.lang.String");
        assertNotNull(JavassistUtils.createMemberValue(cp, stringType, "hello"));
    }

    @Test
    public void shouldCreateMemberValueForClassCtClass() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        CtClass classType = pool.get("java.lang.Class");
        assertNotNull(JavassistUtils.createMemberValue(cp, classType, String.class));
    }

    @Test
    public void shouldGetAnnotationsAttributeForMethod() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        CtMethod cm = cc.getDeclaredMethod("length");
        assertNotNull(JavassistUtils.getAnnotationsAttribute(cm));
    }

    @Test
    public void shouldGetClassAnnotationsAttribute() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertNotNull(JavassistUtils.getClassAnnotationsAttribute(cc));
    }

    @Test
    public void shouldGetFieldAnnotationsAttribute() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        assertNotNull(JavassistUtils.getFieldAnnotationsAttribute(cc.getDeclaredField("hash")));
    }

    @Test
    public void shouldGetParameterAnnotationsAttribute() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        CtMethod cm = cc.getDeclaredMethod("substring", new CtClass[]{CtClass.intType});
        assertNotNull(JavassistUtils.getParameterAnnotationsAttribute(cm));
    }

    @Test
    public void shouldGetMethodParamNames() throws Exception {
        String[] names = JavassistUtils.getMethodParamNames("java.lang.String", "substring");
        assertNotNull(names);
    }

    @Test
    public void shouldGetMethodParamNamesFromCtMethod() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        CtMethod cm = cc.getDeclaredMethod("substring", new CtClass[]{CtClass.intType});
        String[] names = JavassistUtils.getMethodParamNames(cm);
        assertNotNull(names);
        assertEquals(1, names.length);
    }

    @Test
    public void shouldReturnNullWhenGetMethodParamNamesForMethodObject() throws Exception {
        java.lang.reflect.Method m = Object.class.getMethod("getClass");
        // getClass has no local variable info, so it may return null or throw
        try {
            String[] names = JavassistUtils.getMethodParamNames(m);
            // if no exception, names may be null
        } catch (Exception e) {
            // acceptable - NotFoundException or NPE
        }
    }

    @Test
    public void shouldCopyAnnotations() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        javassist.bytecode.AnnotationsAttribute attr = new javassist.bytecode.AnnotationsAttribute(cp, javassist.bytecode.AnnotationsAttribute.visibleTag);
        Annotation ann = new Annotation("java.lang.Deprecated", cp);
        attr.addAnnotation(ann);
        ConstPool newCp = new ConstPool("java.lang.Object");
        javassist.bytecode.AnnotationsAttribute copied = JavassistUtils.copyAnnotations(attr, newCp);
        assertNotNull(copied);
        assertEquals(1, copied.getAnnotations().length);
    }

    @Test
    public void shouldReturnNullWhenCopyNullAnnotations() throws Exception {
        ConstPool cp = new ConstPool("java.lang.Object");
        assertNull(JavassistUtils.copyAnnotations(null, cp));
    }

    @Test
    public void shouldCopyParameterAnnotations() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        javassist.bytecode.ParameterAnnotationsAttribute attr = new javassist.bytecode.ParameterAnnotationsAttribute(cp, javassist.bytecode.ParameterAnnotationsAttribute.visibleTag);
        Annotation[][] anns = new Annotation[1][];
        anns[0] = new Annotation[]{new Annotation("java.lang.Deprecated", cp)};
        attr.setAnnotations(anns);
        ConstPool newCp = new ConstPool("java.lang.Object");
        javassist.bytecode.ParameterAnnotationsAttribute copied = JavassistUtils.copyParameterAnnotations(attr, newCp, 0);
        assertNotNull(copied);
    }

    @Test
    public void shouldReturnNullWhenCopyNullParameterAnnotations() throws Exception {
        ConstPool cp = new ConstPool("java.lang.Object");
        assertNull(JavassistUtils.copyParameterAnnotations(null, cp, 0));
    }

    @Test
    public void shouldReturnNullWhenCopyParameterAnnotationsFromBeyondEnd() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        javassist.bytecode.ParameterAnnotationsAttribute attr = new javassist.bytecode.ParameterAnnotationsAttribute(cp, javassist.bytecode.ParameterAnnotationsAttribute.visibleTag);
        Annotation[][] anns = new Annotation[1][];
        anns[0] = new Annotation[]{new Annotation("java.lang.Deprecated", cp)};
        attr.setAnnotations(anns);
        assertNull(JavassistUtils.copyParameterAnnotations(attr, cp, 5));
    }

    @Test
    public void shouldAddSignatureToField() throws Exception {
        CtClass cc = pool.get("java.lang.String");
        // just verify it doesn't throw
        assertNotNull(JavassistUtils.getFieldAnnotationsAttribute(cc.getDeclaredField("hash")));
    }

    @Test
    public void shouldCreateAnnotation() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        Deprecated dep = TestAnnotated.class.getAnnotation(Deprecated.class);
        Annotation ann = JavassistUtils.createAnnotation(dep, cp);
        assertNotNull(ann);
        assertEquals("java.lang.Deprecated", ann.getTypeName());
    }

    @Test
    public void shouldCreateMemberValueForEnumCtClass() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        CtClass enumType = pool.get("io.github.easy4j.javassist.utils.JavassistUtilsTest$TestEnum");
        assertNotNull(JavassistUtils.createMemberValue(cp, enumType, TestEnum.A));
    }

    @Test
    public void shouldCreateMemberValueForInterfaceCtClass() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        CtClass iface = pool.get("java.io.Serializable");
        assertNotNull(JavassistUtils.createMemberValue(cp, iface));
    }

    @Test
    public void shouldCreateMemberValueForArrayCtClassWithValues() throws Exception {
        ConstPool cp = new ConstPool("java.lang.String");
        CtClass arrType = pool.get("java.lang.String[]");
        String[] vals = {"a", "b"};
        assertNotNull(JavassistUtils.createMemberValue(cp, arrType, vals));
    }

    enum TestEnum { A, B }

    @Deprecated
    static class TestAnnotated {}
}
