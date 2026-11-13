package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        //  verificam daca variabila in care vrem sa salvam (varName) exista deja in memorie
        if (!symTable.isDefined(varName)) {
            throw new MyException("readFile: variable " + varName + " is not defined");
        }

        //  verificam daca variabila e de tip int
        var valType = symTable.lookup(varName).getType();
        if (!valType.equals(new IntType())) {
            throw new MyException("readFile: variable " + varName + " is not of type int");
        }


        var val = exp.eval(symTable);
        if (!(val instanceof StringValue fileName)) {
            throw new MyException("readFile: " + exp + " is not a string");
        }


        if (!fileTable.isDefined(fileName)) {
            throw new MyException("readFile: file " + fileName + " is not open");
        }

        //  luam cititorul din FileTable si citim o linie
        BufferedReader reader = fileTable.lookup(fileName);
        try {
            String line = reader.readLine();
            int num;

            // daca e gol (am ajuns la finalul fisierului), dam valoarea 0, altfel transformam textul in numar
            if (line == null) {
                num = 0;
            } else {
                num = Integer.parseInt(line.strip());
            }

            //  actualizam variabila in dictionar cu noul numar citit
            symTable.update(varName, new IntValue(num));

        } catch (IOException e) {
            throw new MyException("readFile: IO error - " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new MyException("readFile: invalid number format - " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}