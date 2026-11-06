package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpTest {
    @Test
    void divisionByZeroThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp div = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> div.eval(symTable));
    }

    @Test
    void arithmeticPrecedence() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        // 2 + 3 * 5 = 17
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))));
        assertDoesNotThrow(() -> assertEquals(new IntValue(17), e.eval(symTable)));
    }
}