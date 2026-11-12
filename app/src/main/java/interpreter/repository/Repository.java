package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final PrgState state;
    private final String logFilePath;

    public Repository(PrgState state, String logFilePath) {
        this.state = state;
        this.logFilePath = logFilePath;

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
            logFile.print("");
        } catch (IOException e) {
            System.err.println("Avertisment: Nu s-a putut goli fisierul log.");
        }
    }


    @Override
    public PrgState getCrtPrg() {
        return this.state;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(prgState.toString());
            logFile.println("=========================================");
        } catch (IOException e) {
            throw new MyException("Eroare la scriere: " + e.getMessage());
        }
    }
}