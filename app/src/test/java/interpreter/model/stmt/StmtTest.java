package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    @Test
    void assignTypeMismatchThrows() {
        IStmt decl = new VarDeclStmt("v", new IntType());
        IStmt assign = new AssignStmt("v", new ValueExp(new BoolValue(true)));

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        assertDoesNotThrow(() -> decl.execute(state));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    //int a; a=true; print(a);
    @Test
    void testspecial() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getSymTable().put("a", new IntValue(0));

        IStmt invalidAssign = new AssignStmt("a", new ValueExp(new interpreter.model.value.BoolValue(true)));

        try {
            invalidAssign.execute(state);

            org.junit.jupiter.api.Assertions.fail("eroare la eroare");

        } catch (interpreter.exceptions.MyException e) {
        }
    }
}