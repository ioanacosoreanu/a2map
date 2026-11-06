package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    @Test
    void assignTypeMismatchThrows() {
        IStmt decl = new VarDeclStmt("v", new IntType());
        IStmt assign = new AssignStmt("v", new ValueExp(new BoolValue(true))); // v e int, îi dăm bool

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        assertDoesNotThrow(() -> decl.execute(state));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }
}