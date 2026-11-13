package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new StatementException("program state stack is empty");
        }
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();


        repo.logPrgStateExec(prg);
        display(prg);

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);

            // 2. scrie in fisier starea noua dupa fiecare pas executat
            repo.logPrgStateExec(prg);
            display(prg); // afiseaza pe ecran
        }
    }

    private void display(PrgState prgState) {
        if (displayFlag) {
            System.out.println(prgState.toString());
        }
    }
}