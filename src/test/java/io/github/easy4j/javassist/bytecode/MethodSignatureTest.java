package io.github.easy4j.javassist.bytecode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodSignatureTest {

    @Test
    public void shouldParseSimpleSignature() {
        MethodSignature sig = MethodSignature.parse("(I)V");
        assertNotNull(sig);
        assertEquals("V", sig.returnType);
        assertEquals(1, sig.paramTypes.size());
        assertEquals("I", sig.paramTypes.get(0));
    }

    @Test
    public void shouldParseNoParamsSignature() {
        MethodSignature sig = MethodSignature.parse("()V");
        assertNotNull(sig);
        assertEquals("V", sig.returnType);
        assertEquals(0, sig.paramTypes.size());
    }

    @Test
    public void shouldParseMultipleParamsSignature() {
        MethodSignature sig = MethodSignature.parse("(ILjava/lang/String;)Z");
        assertNotNull(sig);
        assertEquals("Z", sig.returnType);
        assertEquals(2, sig.paramTypes.size());
    }

    @Test
    public void shouldParseClassParamSignature() {
        MethodSignature sig = MethodSignature.parse("(Ljava/lang/Object;)Ljava/lang/String;");
        assertNotNull(sig);
        assertEquals("Ljava/lang/String;", sig.returnType);
        assertEquals(1, sig.paramTypes.size());
        assertEquals("Ljava/lang/Object;", sig.paramTypes.get(0));
    }

    @Test
    public void shouldParseArrayParamSignature() {
        MethodSignature sig = MethodSignature.parse("([I)V");
        assertNotNull(sig);
        assertEquals(1, sig.paramTypes.size());
        assertEquals("[I", sig.paramTypes.get(0));
    }

    @Test
    public void shouldParseTypeParameterSignature() {
        MethodSignature sig = MethodSignature.parse("<T:Ljava/lang/Object;>(TT;)V");
        assertNotNull(sig);
        assertEquals("V", sig.returnType);
        assertEquals(1, sig.paramTypes.size());
        assertNotNull(sig.typeParameters);
    }

    @Test
    public void shouldParseExceptionTypes() {
        MethodSignature sig = MethodSignature.parse("()V^Ljava/lang/Exception;");
        assertNotNull(sig);
        assertEquals("V", sig.returnType);
        assertTrue(sig.exceptionTypes.contains("Exception"));
    }

    @Test
    public void shouldReturnToString() {
        MethodSignature sig = MethodSignature.parse("(I)V");
        String str = sig.toString();
        assertNotNull(str);
        assertTrue(str.contains("("));
        assertTrue(str.contains(")"));
    }

    @Test
    public void shouldParseGenericReturnType() {
        MethodSignature sig = MethodSignature.parse("()TT;");
        assertNotNull(sig);
        assertEquals("TT;", sig.returnType);
    }

    @Test
    public void shouldParseNestedGenericClassParam() {
        MethodSignature sig = MethodSignature.parse("(Ljava/util/List<Ljava/lang/String;>;)V");
        assertNotNull(sig);
        assertEquals(1, sig.paramTypes.size());
    }
}
