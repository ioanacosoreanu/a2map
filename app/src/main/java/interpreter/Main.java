package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.Relop;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.model.value.IntValue;
import interpreter.model.type.BoolType;
import interpreter.model.exp.RelationalExp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

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
                // int a; int b; bool c; a=1; b=2; c=(a!=b); print(c)
        IStmt ex5 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c", new BoolType()),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c", new RelationalExp(Relop.NE, new VarExp("a"), new VarExp("b"))),
                                                        new PrintStmt(new VarExp("c"))
                                                )
                                        )
                                )
                        )
                )
        );



        Scanner scanner = new Scanner(System.in);
        System.out.println("Alege un exemplu:");
        System.out.println("1. Exemplul 1");
        System.out.println("2. Exemplul 2");
        System.out.println("3. Exemplul 3");
        System.out.println("4. Exemplul 4 (Fisiere - test.in)");
        System.out.println("5. Exemplul 5");

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
        } else if (choice.equals("5")) {
            PrgState prg = PrgState.of(ex5);
            IRepository repo = new Repository(prg, "log5.txt");
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

