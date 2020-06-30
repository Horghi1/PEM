package util;

import javafx.util.Pair;
import model.Expense;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class UndoOperation {

    private static UndoOperation INSTANCE = null;
    private Stack<Pair<String, Expense>> operations;

    private UndoOperation() {
        this.operations = new Stack<>();
    }

    public static UndoOperation getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UndoOperation();
        }

        return INSTANCE;
    }

    public Pair<String, Expense> undo() {
        if(operations.size() > 0) {
            return operations.pop();
        }
        return null;
    }

    public void add(Pair<String, Expense> entry) {
        operations.add(entry);
    }

    public Stack<Pair<String, model.Expense>> getAll() {
        return this.operations;
    }

}
