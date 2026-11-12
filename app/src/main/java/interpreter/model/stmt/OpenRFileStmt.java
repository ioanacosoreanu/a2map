package interpreter.model.stmt;

import interpreter.exceptions.MyException; // Foloseste StatementException daca ai clasa asta creata
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        // 1. Evaluam expresia ca sa vedem numele fisierului
        var val = exp.eval(state.getSymTable());

        // 2. Verificam daca rezultatul e de tip String
        if (!(val instanceof StringValue fileName)) {
            throw new MyException("openRFile: " + exp + " is not a string");
        }

        // 3. Luam FileTable-ul din starea programului
        var fileTable = state.getFileTable();

        // 4. Verificam daca fisierul nu e deja deschis
        if (fileTable.isDefined(fileName)) {
            throw new MyException("openRFile: " + fileName + " is already open");
        }

        // 5. Incercam sa deschidem fisierul real din calculator
        try {
            var reader = new BufferedReader(new FileReader(fileName.val()));
            fileTable.put(fileName, reader);
        } catch (IOException e) {
            throw new MyException("openRFile: cannot open " + fileName + ": " + e.getMessage());
        }

        return null; // Instructiunile returneaza null cand nu creeaza thread-uri noi (asta e pt A4)
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}