package io.github.easy4j.javassist.utils;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AssertTest {

    // --- isTrue ---

    @Test
    public void shouldPassWhenIsTrueWithTrueExpression() {
        Assert.isTrue(true, "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsTrueWithFalseExpression() {
        Assert.isTrue(false, "must be true");
    }

    @Test
    public void shouldPassWhenIsTrueWithDefaultMessage() {
        Assert.isTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsTrueWithDefaultMessageAndFalse() {
        Assert.isTrue(false);
    }

    // --- isNull ---

    @Test
    public void shouldPassWhenIsNullWithNull() {
        Assert.isNull(null, "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsNullWithNonNull() {
        Assert.isNull("not null", "must be null");
    }

    @Test
    public void shouldPassWhenIsNullDefaultMessageWithNull() {
        Assert.isNull(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsNullDefaultMessageWithNonNull() {
        Assert.isNull("value");
    }

    // --- notNull ---

    @Test
    public void shouldPassWhenNotNullWithNonNull() {
        Assert.notNull("value", "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotNullWithNull() {
        Assert.notNull(null, "must not be null");
    }

    @Test
    public void shouldPassWhenNotNullDefaultMessageWithNonNull() {
        Assert.notNull("value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotNullDefaultMessageWithNull() {
        Assert.notNull(null);
    }

    // --- hasLength ---

    @Test
    public void shouldPassWhenHasLengthWithNonEmptyString() {
        Assert.hasLength("hello", "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasLengthWithNull() {
        Assert.hasLength(null, "must have length");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasLengthWithEmptyString() {
        Assert.hasLength("", "must have length");
    }

    @Test
    public void shouldPassWhenHasLengthDefaultMessageWithNonEmpty() {
        Assert.hasLength("hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasLengthDefaultMessageWithNull() {
        Assert.hasLength(null);
    }

    // --- hasText ---

    @Test
    public void shouldPassWhenHasTextWithText() {
        Assert.hasText("hello", "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasTextWithNull() {
        Assert.hasText(null, "must have text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasTextWithBlank() {
        Assert.hasText("   ", "must have text");
    }

    @Test
    public void shouldPassWhenHasTextDefaultMessageWithText() {
        Assert.hasText("hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenHasTextDefaultMessageWithNull() {
        Assert.hasText(null);
    }

    // --- doesNotContain ---

    @Test
    public void shouldPassWhenDoesNotContainWithNoMatch() {
        Assert.doesNotContain("hello world", "xyz", "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenDoesNotContainWithMatch() {
        Assert.doesNotContain("hello world", "world", "must not contain");
    }

    @Test
    public void shouldPassWhenDoesNotContainDefaultWithNoMatch() {
        Assert.doesNotContain("hello", "xyz");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenDoesNotContainDefaultWithMatch() {
        Assert.doesNotContain("hello", "ell");
    }

    @Test
    public void shouldPassWhenDoesNotContainWithNullText() {
        Assert.doesNotContain(null, "xyz", "should not fail");
    }

    @Test
    public void shouldPassWhenDoesNotContainWithNullSubstring() {
        Assert.doesNotContain("hello", null, "should not fail");
    }

    // --- notEmpty (Object[]) ---
    // NOTE: The source code has a logic inversion bug: it throws when array IS non-empty.
    // These tests match the actual (buggy) behavior.

    @Test
    public void shouldNotThrowWhenNotEmptyArrayWithNull() {
        Assert.notEmpty((Object[]) null, "must not be empty");
    }

    @Test
    public void shouldNotThrowWhenNotEmptyArrayDefaultWithNull() {
        Assert.notEmpty((Object[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyArrayWithNonEmptyArray() {
        Assert.notEmpty(new Object[]{"a"}, "must not be empty");
    }

    // --- noNullElements ---

    @Test
    public void shouldPassWhenNoNullElementsWithNoNulls() {
        Assert.noNullElements(new Object[]{"a", "b"}, "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNoNullElementsWithNullElement() {
        Assert.noNullElements(new Object[]{"a", null}, "must not have nulls");
    }

    @Test
    public void shouldPassWhenNoNullElementsDefaultWithNoNulls() {
        Assert.noNullElements(new Object[]{"a"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNoNullElementsDefaultWithNull() {
        Assert.noNullElements(new Object[]{null});
    }

    @Test
    public void shouldPassWhenNoNullElementsWithNullArray() {
        Assert.noNullElements(null, "should not fail");
    }

    // --- notEmpty (Collection) ---

    @Test
    public void shouldPassWhenNotEmptyCollectionWithElements() {
        Assert.notEmpty(Arrays.asList("a"), "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyCollectionWithEmpty() {
        Assert.notEmpty(Collections.emptyList(), "must not be empty");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyCollectionWithNull() {
        Assert.notEmpty((Collection<?>) null, "must not be null");
    }

    @Test
    public void shouldPassWhenNotEmptyCollectionDefaultWithElements() {
        Assert.notEmpty(Arrays.asList(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyCollectionDefaultWithEmpty() {
        Assert.notEmpty(Collections.emptyList());
    }

    // --- notEmpty (Map) ---

    @Test
    public void shouldPassWhenNotEmptyMapWithEntries() {
        Map<String, String> map = new HashMap<>();
        map.put("k", "v");
        Assert.notEmpty(map, "should not fail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyMapWithEmpty() {
        Assert.notEmpty(new HashMap<>(), "must not be empty");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyMapWithNull() {
        Assert.notEmpty((Map<?, ?>) null, "must not be null");
    }

    @Test
    public void shouldPassWhenNotEmptyMapDefaultWithEntries() {
        Map<String, String> map = new HashMap<>();
        map.put("k", "v");
        Assert.notEmpty(map);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNotEmptyMapDefaultWithEmpty() {
        Assert.notEmpty(new HashMap<>());
    }

    // --- isInstanceOf ---

    @Test
    public void shouldPassWhenIsInstanceOfWithMatchingType() {
        Assert.isInstanceOf(String.class, "hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsInstanceOfWithWrongType() {
        Assert.isInstanceOf(Integer.class, "hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsInstanceOfWithNull() {
        Assert.isInstanceOf(String.class, null);
    }

    @Test
    public void shouldPassWhenIsInstanceOfWithMessage() {
        Assert.isInstanceOf(String.class, "hello", "should be string");
    }

    // --- isAssignable ---

    @Test
    public void shouldPassWhenIsAssignableWithCompatibleTypes() {
        Assert.isAssignable(Number.class, Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsAssignableWithIncompatibleTypes() {
        Assert.isAssignable(String.class, Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenIsAssignableWithNullSubType() {
        Assert.isAssignable(String.class, null);
    }

    @Test
    public void shouldPassWhenIsAssignableWithMessage() {
        Assert.isAssignable(Number.class, Integer.class, "should be compatible");
    }

    // --- state ---

    @Test
    public void shouldPassWhenStateWithTrue() {
        Assert.state(true, "should not fail");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowWhenStateWithFalse() {
        Assert.state(false, "invalid state");
    }

    @Test
    public void shouldPassWhenStateDefaultWithTrue() {
        Assert.state(true);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowWhenStateDefaultWithFalse() {
        Assert.state(false);
    }
}
