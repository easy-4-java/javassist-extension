package io.github.easy4j.javassist.bytecode.visit;

import org.junit.Test;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;

import static org.junit.Assert.*;

public class ArrayIndexAssigningVisitorTest {

    private final ConstPool cp = new ConstPool("java.lang.String");

    @Test
    public void shouldAssignStringMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitStringMemberValue(new StringMemberValue("test", cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof StringMemberValue);
    }

    @Test
    public void shouldAssignBooleanMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitBooleanMemberValue(new BooleanMemberValue(true, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof BooleanMemberValue);
    }

    @Test
    public void shouldAssignByteMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitByteMemberValue(new ByteMemberValue((byte) 1, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof ByteMemberValue);
    }

    @Test
    public void shouldAssignCharMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitCharMemberValue(new CharMemberValue('a', cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof CharMemberValue);
    }

    @Test
    public void shouldAssignShortMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitShortMemberValue(new ShortMemberValue((short) 1, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof ShortMemberValue);
    }

    @Test
    public void shouldAssignIntegerMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitIntegerMemberValue(new IntegerMemberValue(cp, 42));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof IntegerMemberValue);
    }

    @Test
    public void shouldAssignLongMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitLongMemberValue(new LongMemberValue(100L, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof LongMemberValue);
    }

    @Test
    public void shouldAssignFloatMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitFloatMemberValue(new FloatMemberValue(1.5f, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof FloatMemberValue);
    }

    @Test
    public void shouldAssignDoubleMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitDoubleMemberValue(new DoubleMemberValue(3.14, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof DoubleMemberValue);
    }

    @Test
    public void shouldAssignClassMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        visitor.visitClassMemberValue(new ClassMemberValue("java.lang.String", cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof ClassMemberValue);
    }

    @Test
    public void shouldAssignEnumMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        EnumMemberValue emv = new EnumMemberValue(cp);
        emv.setType("java.lang.Thread$State");
        emv.setValue("RUNNABLE");
        visitor.visitEnumMemberValue(emv);
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof EnumMemberValue);
    }

    @Test
    public void shouldAssignAnnotationMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        Annotation ann = new Annotation("java.lang.Deprecated", cp);
        visitor.visitAnnotationMemberValue(new AnnotationMemberValue(ann, cp));
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof AnnotationMemberValue);
    }

    @Test
    public void shouldAssignArrayMemberValue() {
        MemberValue[] arr = new MemberValue[1];
        ArrayIndexAssigningVisitor visitor = new ArrayIndexAssigningVisitor(arr, 0, cp);
        StringMemberValue[] inner = new StringMemberValue[]{new StringMemberValue("a", cp), new StringMemberValue("b", cp)};
        ArrayMemberValue amv = new ArrayMemberValue(new StringMemberValue(cp), cp);
        amv.setValue(inner);
        visitor.visitArrayMemberValue(amv);
        assertNotNull(arr[0]);
        assertTrue(arr[0] instanceof ArrayMemberValue);
    }
}
