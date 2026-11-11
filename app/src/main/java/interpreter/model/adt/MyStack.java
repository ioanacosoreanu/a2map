package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) {
            throw new ADTException("pop from an empty stack");
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
    // --- METODA NOUA PENTRU TEMA A3 ---
    @Override
    public java.util.List<T> toList() {
        // Convertim stiva intr-o lista normala
        java.util.List<T> list = new java.util.ArrayList<>(this.stack);
        // O inversam ca sa avem varful stivei pe prima pozitie, fix cum vrea profu'
        java.util.Collections.reverse(list);
        return list;
    }
}