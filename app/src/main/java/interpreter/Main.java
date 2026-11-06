package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import java.util.Scanner;

public class Main {
    private static Controller makeController(IStmt stmt) {
        PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), stmt);
        IRepository repo = new Repository(prg);
        return new Controller(repo, true);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTOY LANGUAGE MENU");
            System.out.println("1. Run Example 1: int v; v=2; print(v)");
            System.out.println("2. Run Example 2: int a; int b; a=2+3*5; b=a+1; print(b)");
            System.out.println("3. Run Example 3: bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String option = scanner.nextLine();
            Controller ctrl = null;

            if (option.equals("1")) {
                ctrl = makeController(Examples.EX1);
            } else if (option.equals("2")) {
                ctrl = makeController(Examples.EX2);
            } else if (option.equals("3")) {
                ctrl = makeController(Examples.EX3);
            } else if (option.equals("0")) {
                System.out.println("Exiting");
                break;
            } else {
                System.out.println(" Try again.");
                continue;
            }

            try {
                ctrl.allStep();
            } catch (MyException e) {
                System.out.println("\nExecution Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\nUnexpected Error: " + e.getMessage());
            }
        }
    }
}