package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Programul de test pentru fisiere (Pasul 6)
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Alege un exemplu:");
        System.out.println("1. Exemplul 1");
        System.out.println("2. Exemplul 2");
        System.out.println("3. Exemplul 3");
        System.out.println("4. Exemplul 4 (Fisiere - test.in)");

        String choice = scanner.nextLine();
        Controller ctrl = null;

        if (choice.equals("1")) {
            PrgState prg = PrgState.of(Examples.EX1);
            IRepository repo = new Repository(prg, "log1.txt");
            ctrl = new Controller(repo, true);
        } else if (choice.equals("2")) {
            PrgState prg = PrgState.of(Examples.EX2);
            IRepository repo = new Repository(prg, "log2.txt");
            ctrl = new Controller(repo, true);
        } else if (choice.equals("3")) {
            PrgState prg = PrgState.of(Examples.EX3);
            IRepository repo = new Repository(prg, "log3.txt");
            ctrl = new Controller(repo, true);
        } else if (choice.equals("4")) {
            PrgState prg = PrgState.of(ex4);
            IRepository repo = new Repository(prg, "log4.txt");
            ctrl = new Controller(repo, true);
        } else {
            System.out.println("Optiune invalida!");
            return;
        }

        try {
            ctrl.allStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}