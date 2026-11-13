package interpreter.model.exp;

import interpreter.exceptions.MyException; // asigura-te ca importul se potriveste cu pachetul tau
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

public record RelationalExp(Relop op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        // Evaluam ambele parti
        IValue val1 = left.eval(symTable);
        IValue val2 = right.eval(symTable);


        if (!(val1 instanceof IntValue(int n1))) {
            throw new MyException("first operand is not an integer");
        }


        if (!(val2 instanceof IntValue(int n2))) {
            throw new MyException("second operand is not an integer");
        }


        return new BoolValue(switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        });
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}