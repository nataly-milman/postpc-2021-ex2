package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.SimpleCalculatorImpl;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class SimpleCalculatorImplTest {

    @Test
    public void when_noInputGiven_then_outputShouldBe0() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        assertEquals("0", calculatorUnderTest.output());
    }

    @Test
    public void when_inputIsPlus_then_outputShouldBe0Plus() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertPlus();
        assertEquals("0+", calculatorUnderTest.output());
    }

    @Test
    public void when_inputIsMinus_then_outputShouldBeCorrect() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertMinus();
        String expected = "0-";
        assertEquals(expected, calculatorUnderTest.output());
    }

    @Test
    public void when_callingInsertDigitWithIllegalNumber_then_exceptionShouldBeThrown() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        try {
            calculatorUnderTest.insertDigit(357);
            fail("should throw an exception and not reach this line");
        } catch (RuntimeException e) {
            // good :)
        }
    }

    @Test
    public void when_callingDeleteLast_then_lastOutputShouldBeDeleted() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + 7 =
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.deleteLast();
        assertEquals("5", calculatorUnderTest.output());
    }

    @Test
    public void when_callingClear_then_outputShouldBeCleared() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(4);
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());
    }

    @Test
    public void when_savingState_should_loadThatStateCorrectly() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // give some input
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);

        // save current state
        Serializable savedState = calculatorUnderTest.saveState();
        assertNotNull(savedState);

        // call `clear` and make sure calculator cleared
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());

        // load the saved state and make sure state was loaded correctly
        calculatorUnderTest.loadState(savedState);
        assertEquals("5+7", calculatorUnderTest.output());
    }

    @Test
    public void when_savingStateFromFirstCalculator_should_loadStateCorrectlyFromSecondCalculator() {
        SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
        SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();
        // I forgot to remove the to-do
        // was implemented in the last (copy_to_another_calc) function by mistake so:
        copy_to_another_calc();
    }

    // 10 tests:

    @Test
    public void delete_last_after_equals() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + 7 =
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertEquals();
        calculatorUnderTest.deleteLast();
        assertEquals("1", calculatorUnderTest.output());
    }

    @Test
    public void equal_at_the_beginning() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertEquals();
        assertEquals("0", calculatorUnderTest.output());
    }

    @Test
    public void one_operation_with_delete_negative_res() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 - 7 =
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.deleteLast();
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertEquals();
        assertEquals("-2", calculatorUnderTest.output());
    }

    @Test
    public void two_operations() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + 7 - 2 =
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertEquals();
        assertEquals("10", calculatorUnderTest.output());
    }

    @Test
    public void multi_digit_operations() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 125 + 30 - 5 =
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertDigit(0);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertEquals();
        assertEquals("150", calculatorUnderTest.output());
    }

    @Test
    public void double_plus() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + +
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertPlus();
        assertEquals("5+", calculatorUnderTest.output());
    }

    @Test
    public void double_op() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + = - =
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertEquals();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertEquals();
        assertEquals("5", calculatorUnderTest.output());
    }

    @Test
    public void ignore_if_last_is_operation() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + 3 -
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertEquals();
        assertEquals("8", calculatorUnderTest.output());
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertEquals();
        assertEquals("8", calculatorUnderTest.output());
    }

    @Test
    public void partially_calculated() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // 5 + 3 = - 2
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertEquals();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(2);
        assertEquals("8-2", calculatorUnderTest.output());
    }

    @Test
    public void copy_to_another_calc() {
        SimpleCalculatorImpl calculatorUnderTest1 = new SimpleCalculatorImpl();
        // 5 +
        calculatorUnderTest1.insertDigit(5);
        calculatorUnderTest1.insertPlus();
        Serializable saved = calculatorUnderTest1.saveState();
        assertNotNull(saved);
        SimpleCalculatorImpl calculatorUnderTest2 = new SimpleCalculatorImpl();
        calculatorUnderTest2.loadState(saved);
        // 5 (on the second)
        calculatorUnderTest2.insertDigit(5);
        calculatorUnderTest2.insertEquals();
        assertEquals("10", calculatorUnderTest2.output());
        // 2 (on the first)
        calculatorUnderTest1.insertDigit(2);
        calculatorUnderTest1.insertEquals();
        assertEquals("7", calculatorUnderTest1.output());
    }

}

