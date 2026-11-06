package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTable = state.getSymTable();
//  Verificăm dacă variabila a fost declarată înainte (ex: int v;)
        if (!symTable.isDefined(id)) {
            throw new StatementException("variable " + id + " was not declared");
        }

        IValue val = exp.eval(symTable);
        // Ne uităm la rezultatul expresiei (care e IntType).
        // Dacă se potrivesc, mergem mai departe. Dacă aveam "v = true", dădea eroare aici.
        if (!val.getType().equals(symTable.lookup(id).getType())) {
            throw new StatementException("declared type of variable " + id + " and type of the assigned expression do not match");
        }

        symTable.update(id, val);
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}