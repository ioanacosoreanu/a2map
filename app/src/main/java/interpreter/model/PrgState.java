package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class PrgState {
    // ExeStack (Stiva de execuție): Ține minte ce instrucțiuni mai avem de rulat.
    // Folosim o stivă (LIFO) pentru că la instrucțiuni compuse (A; B) punem B, apoi A, ca A să iasă prima.
    private IStack<IStmt> exeStack;
    // SymTable (Tabela de simboluri): Aici salvăm variabilele și valorile lor (ex: v -> 2, a -> true).
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        // Când se creează starea, punem automat tot programul pe stiva de execuție ca să înceapă rularea
        exeStack.push(program);
    }

    public IStack<IStmt> getExeStack() { return exeStack; }
    public IDictionary<String, IValue> getSymTable() { return symTable; }
    public IList<IValue> getOut() { return out; }

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "\n" +
                "SymTable:\n" + symTable.toString() + "\n" +
                "Out:\n" + out.toString() + "\n";
    }
}