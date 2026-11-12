package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        // 1. Evaluam expresia ca sa aflam numele fisierului
        var val = exp.eval(symTable);
        if (!(val instanceof StringValue fileName)) {
            throw new MyException("closeRFile: " + exp + " is not a string");
        }

        // 2. Verificam daca e in FileTable (daca e deschis macar)
        if (!fileTable.isDefined(fileName)) {
            throw new MyException("closeRFile: file " + fileName + " is not open");
        }

        // 3. Luam cititorul, il inchidem folosind comanda de la Java, apoi il stergem din memorie
        BufferedReader reader = fileTable.lookup(fileName);
        try {
            reader.close();
            fileTable.remove(fileName); // Il stergem din FileTable[cite: 5]
        } catch (IOException e) {
            throw new MyException("closeRFile: cannot close " + fileName + ": " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}