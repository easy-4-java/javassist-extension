package io.github.easy4j.javassist.bytecode.visit;

import org.junit.Test;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;

import static org.junit.Assert.*;

public class MemberValueCreationVisitorTest {

    private final ConstPool cp = new ConstPool("java.lang.String");

    @Test
    public void shouldCreateNewMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        assertNull(visitor.value);
    }

    @Test
    public void shouldVisitBooleanMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitBooleanMemberValue(new BooleanMemberValue(true, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof BooleanMemberValue);
    }

    @Test
    public void shouldVisitByteMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitByteMemberValue(new ByteMemberValue((byte) 1, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof ByteMemberValue);
    }

    @Test
    public void shouldVisitCharMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitCharMemberValue(new CharMemberValue('a', cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof CharMemberValue);
    }

    @Test
    public void shouldVisitShortMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitShortMemberValue(new ShortMemberValue((short) 1, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof ShortMemberValue);
    }

    @Test
    public void shouldVisitIntegerMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitIntegerMemberValue(new IntegerMemberValue(cp, 42));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof IntegerMemberValue);
    }

    @Test
    public void shouldVisitLongMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitLongMemberValue(new LongMemberValue(100L, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof LongMemberValue);
    }

    @Test
    public void shouldVisitFloatMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitFloatMemberValue(new FloatMemberValue(1.5f, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof FloatMemberValue);
    }

    @Test
    public void shouldVisitDoubleMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitDoubleMemberValue(new DoubleMemberValue(3.14, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof DoubleMemberValue);
    }

    @Test
    public void shouldVisitStringMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitStringMemberValue(new StringMemberValue("test", cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof StringMemberValue);
    }

    @Test
    public void shouldVisitClassMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        visitor.visitClassMemberValue(new ClassMemberValue("java.lang.String", cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof ClassMemberValue);
    }

    @Test
    public void shouldVisitEnumMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        EnumMemberValue emv = new EnumMemberValue(cp);
        emv.setType("java.lang.Thread$State");
        emv.setValue("RUNNABLE");
        visitor.visitEnumMemberValue(emv);
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof EnumMemberValue);
    }

    @Test
    public void shouldVisitAnnotationMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        Annotation ann = new Annotation("java.lang.Deprecated", cp);
        visitor.visitAnnotationMemberValue(new AnnotationMemberValue(ann, cp));
        assertNotNull(visitor.value);
        assertTrue(visitor.value instanceof AnnotationMemberValue);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenVisitArrayMemberValue() {
        MemberValueCreationVisitor visitor = new MemberValueCreationVisitor(cp);
        ArrayMemberValue amv = new ArrayMemberValue(new StringMemberValue(cp), cp);
        visitor.visitArrayMemberValue(amv);
    }
}
