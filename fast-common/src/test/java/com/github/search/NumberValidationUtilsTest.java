package com.github.search;

import com.github.search.utils.NumberValidationUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-31
 * Time: 10:33
 */
public class NumberValidationUtilsTest {

    @Test
    public void testIsPositiveInteger() {
        Assert.assertTrue(NumberValidationUtils.isPositiveInteger("1"));
        Assert.assertTrue(NumberValidationUtils.isPositiveInteger("+12"));
        Assert.assertTrue(NumberValidationUtils.isPositiveInteger("87653521123567"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("0.1"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("0"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("0123"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("-1"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("-0.1"));
        Assert.assertFalse(NumberValidationUtils.isPositiveInteger("ab"));
    }


    //correct test case: -1, -87653521123567
    //wrong test case: 0.1, 0, 0123, 1, -0.1, -ab
    @Test
    public void testIsNegativeInteger() {
        Assert.assertTrue(NumberValidationUtils.isNegativeInteger("-1"));
        Assert.assertTrue(NumberValidationUtils.isNegativeInteger("-87653521123567"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("0.1"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("0"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("0123"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("1"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("-0.1"));
        Assert.assertFalse(NumberValidationUtils.isNegativeInteger("ab"));
    }


    //correct test case: -1, 0, 1, 8673434231, -282464334
    //wrong test case: 0.1, 0123, -0.1, ab
    @Test
    public void testIsWholeNumber() {
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("-1"));
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("0"));
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("1"));
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("+12"));
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("8673434231"));
        Assert.assertTrue(NumberValidationUtils.isWholeNumber("-282464334"));
        Assert.assertFalse(NumberValidationUtils.isWholeNumber("0123"));
        Assert.assertFalse(NumberValidationUtils.isWholeNumber("0.1"));
        Assert.assertFalse(NumberValidationUtils.isWholeNumber("-0.1"));
        Assert.assertFalse(NumberValidationUtils.isWholeNumber("ab"));
    }


    //correct test case: 0.1, 0.132213, 1.0
    //wrong test case: 1, 0.0, 0123, -1, -0.1
    @Test
    public void testIsPositiveDecimal() {
        Assert.assertTrue(NumberValidationUtils.isPositiveDecimal("0.1"));
        Assert.assertTrue(NumberValidationUtils.isPositiveDecimal("0.132213"));
        Assert.assertTrue(NumberValidationUtils.isPositiveDecimal("30.00"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("0."));
        Assert.assertTrue(NumberValidationUtils.isPositiveDecimal("+12.0"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("0123"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("1"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("0.0"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("ab"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("-1"));
        Assert.assertFalse(NumberValidationUtils.isPositiveDecimal("-0.1"));
    }


    //correct test case: -0.132213, -1.0
    //wrong test case: 1, 0, 0123, -1, 0.1
    @Test
    public void testIsNegativeDecimal() {
        Assert.assertTrue(NumberValidationUtils.isNegativeDecimal("-0.132213"));
        Assert.assertTrue(NumberValidationUtils.isNegativeDecimal("-1.0"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("-0."));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("1"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("0"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("0123"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("0.0"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("ab"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("-1"));
        Assert.assertFalse(NumberValidationUtils.isNegativeDecimal("0.1"));
    }


    //correct test case: 0.1, 0.00, -0.132213
    //wrong test case: 1, 0, 0123, -1,  0., ba
    @Test
    public void testIsDecimal() {
        Assert.assertTrue(NumberValidationUtils.isDecimal("0.1"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("0.00"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("+0.0"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("-0.132213"));
        Assert.assertTrue(NumberValidationUtils.isDecimal("0."));
        Assert.assertFalse(NumberValidationUtils.isDecimal("1"));
        Assert.assertFalse(NumberValidationUtils.isDecimal("0123"));
        Assert.assertFalse(NumberValidationUtils.isDecimal("0"));
        Assert.assertFalse(NumberValidationUtils.isDecimal("ab"));
        Assert.assertFalse(NumberValidationUtils.isDecimal("-1"));

    }


    //correct test case: 0.032213, -0.234, 0.0, 1, -1, 0
    //wrong test case: 00.13, ab, +0.14
    @Test
    public void testIsRealNumber() {
        Assert.assertTrue(NumberValidationUtils.isRealNumber("0.032213"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("-0.234"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("0.0"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("1"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("+0.14"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("-1"));
        Assert.assertTrue(NumberValidationUtils.isRealNumber("0.0"));
        Assert.assertFalse(NumberValidationUtils.isRealNumber("00.13"));
        Assert.assertFalse(NumberValidationUtils.isRealNumber("ab"));

    }
}
