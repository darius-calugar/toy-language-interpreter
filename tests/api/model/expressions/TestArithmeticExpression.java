package api.model.expressions;


import api.model.collections.IDictionary;
import api.model.exceptions.DivisionByZeroException;
import api.model.values.IValue;
import api.model.values.IntValue;
import org.junit.jupiter.api.Test;

public class TestArithmeticExpression {
    IExpression                 expression;
    IDictionary<String, IValue> dummySymbolTable;

    @Test
    void testAddition_validOperands_operandsAdded() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(4)),
                new ValueExpression(new IntValue(5)),
                ArithmeticOperation.add);
        assert ((IntValue) expression.evaluate(dummySymbolTable)).getRawValue() == 9;
    }

    @Test
    void testSubtraction_validOperands_operandsSubtracted() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(10)),
                new ValueExpression(new IntValue(4)),
                ArithmeticOperation.subtract);
        assert ((IntValue) expression.evaluate(dummySymbolTable)).getRawValue() == 6;
    }

    @Test
    void testProduct_validOperands_operandsMultiplied() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(2)),
                new ValueExpression(new IntValue(5)),
                ArithmeticOperation.multiply);
        assert ((IntValue) expression.evaluate(dummySymbolTable)).getRawValue() == 10;
    }

    @Test
    void testDivision_validOperands_operandsDivided() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(10)),
                new ValueExpression(new IntValue(3)),
                ArithmeticOperation.divide);
        assert ((IntValue) expression.evaluate(dummySymbolTable)).getRawValue() == 3;
    }

    @Test
    void testDivision_divisionByZero_exceptionThrown() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(5)),
                new ValueExpression(new IntValue(0)),
                ArithmeticOperation.divide);
        try {
            expression.evaluate(dummySymbolTable);
            assert false;
        } catch (DivisionByZeroException ignored) {
            assert true;
        }
    }

    @Test
    void testMod_validOperands_correctModulo() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(11)),
                new ValueExpression(new IntValue(3)),
                ArithmeticOperation.mod);
        assert ((IntValue) expression.evaluate(dummySymbolTable)).getRawValue() == 2;
    }

    @Test
    void testMod_divisionByZero_exceptionThrown() {
        expression = new ArithmeticExpression(
                new ValueExpression(new IntValue(5)),
                new ValueExpression(new IntValue(0)),
                ArithmeticOperation.mod);
        try {
            expression.evaluate(dummySymbolTable);
            assert false;
        } catch (DivisionByZeroException ignored) {
            assert true;
        }
    }
}
