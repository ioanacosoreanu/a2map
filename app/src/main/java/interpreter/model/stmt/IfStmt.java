package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        // 1. Calculăm condiția
        IValue condValue = cond.eval(state.getSymTable());

        if (!(condValue instanceof BoolValue(boolean b))) {
            throw new StatementException("Condition in IF statement is not a boolean");
        }

        if (b) {
            state.getExeStack().push(thenS);// Dacă e true, băgăm then pe stivă
        } else {
            state.getExeStack().push(elseS);// Dacă e false, băgăm else pe stivă
        }

        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond.toString() + " then " + thenS.toString() + " else " + elseS.toString() + ")";
    }
}