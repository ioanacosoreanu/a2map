package interpreter.model.stmt;

import interpreter.exceptions.MyException; // Foloseste StatementException daca l-ai creat
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

        // 1. Verificam daca variabila in care vrem sa salvam (varName) exista deja in memorie
        if (!symTable.isDefined(varName)) {
            throw new MyException("readFile: variable " + varName + " is not defined");
        }

        // 2. Verificam daca variabila e de tip "int" (nu putem citi texte in variabile bool)
        var valType = symTable.lookup(varName).getType();
        if (!valType.equals(new IntType())) {
            throw new MyException("readFile: variable " + varName + " is not of type int");
        }

        // 3. Evaluam expresia pentru numele fisierului
        var val = exp.eval(symTable);
        if (!(val instanceof StringValue fileName)) {
            throw new MyException("readFile: " + exp + " is not a string");
        }

        // 4. Verificam daca fisierul este deschis (daca exista in FileTable)
        if (!fileTable.isDefined(fileName)) {
            throw new MyException("readFile: file " + fileName + " is not open");
        }

        // 5. Luam cititorul din FileTable si citim o linie
        BufferedReader reader = fileTable.lookup(fileName);
        try {
            String line = reader.readLine();
            int num;

            // Daca e gol (am ajuns la finalul fisierului), dam valoarea 0, altfel transformam textul in numar[cite: 3]
            if (line == null) {
                num = 0;
            } else {
                num = Integer.parseInt(line.strip());
            }

            // 6. Actualizam variabila in dictionar cu noul numar citit
            symTable.update(varName, new IntValue(num));

        } catch (IOException e) {
            throw new MyException("readFile: IO error - " + e.getMessage());
        } catch (NumberFormatException e) { // Prindem eroarea de conversie din Java
            throw new MyException("readFile: invalid number format - " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}