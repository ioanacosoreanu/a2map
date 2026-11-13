package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final PrgState state;
    private final String logFilePath; //calea catre fisierul text unde scriem log

    public Repository(PrgState state, String logFilePath) {
        this.state = state;
        this.logFilePath = logFilePath;
//cand logFilePath, false ->overwrite
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
            logFile.print("");// text gol ca sa stergem continutu vechi
        } catch (IOException e) {
            //eroare ca sa nu crape programu
            System.err.println("Avertisment: Nu s-a putut goli fisierul log.");
        }
    }


    @Override
    public PrgState getCrtPrg() {
        return this.state;
    }
    //metoda scris in fisier
    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException {
       //true->append
        System.out.println(logFilePath);
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {

            logFile.println(prgState.toString());
            logFile.println("**");

        } catch (IOException e) {
            throw new MyException("Eroare la scriere in fisier: " + e.getMessage());
        }
    }
}