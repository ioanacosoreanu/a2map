package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {

        var val = exp.eval(state.getSymTable());


        if (!(val instanceof StringValue fileName)) {
            throw new MyException("openRFile: " + exp + " is not a string");
        }


        var fileTable = state.getFileTable();


        if (fileTable.isDefined(fileName)) {
            throw new MyException("openRFile: " + fileName + " is already open");
        }

        //  incercam sa deschidem fisierul real
        try {
            var reader = new BufferedReader(new FileReader(fileName.val()));
            fileTable.put(fileName, reader);
        } catch (IOException e) {
            throw new MyException("openRFile: cannot open " + fileName + ": " + e.getMessage());
        }

        return null; // instructiunile returneaza null cand nu creeaza thread-uri noi
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}