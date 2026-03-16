package com.mycontacts.editcontact;

import java.util.ArrayList;
import java.util.List;

// manages undo/redo stacks for edit commands
public class CommandHistory {
    private final List<EditCommand> undoStack = new ArrayList<>();
    private final List<EditCommand> redoStack = new ArrayList<>();

    // execute and track a command
    public void executeCommand(EditCommand command) {
        command.execute();
        undoStack.add(command);
        redoStack.clear(); // new action clears redo history
    }

    public boolean undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }
        EditCommand command = undoStack.remove(undoStack.size() - 1);
        command.undo();
        redoStack.add(command);
        return true;
    }

    public boolean redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return false;
        }
        EditCommand command = redoStack.remove(redoStack.size() - 1);
        command.execute();
        undoStack.add(command);
        return true;
    }

    public void showHistory() {
        if (undoStack.isEmpty() && redoStack.isEmpty()) {
            System.out.println("No edit history.");
            return;
        }
        System.out.println("Edit history (undo stack):");
        for (int i = 0; i < undoStack.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + undoStack.get(i).getDescription());
        }
        if (!redoStack.isEmpty()) {
            System.out.println("Redo available:");
            for (int i = redoStack.size() - 1; i >= 0; i--) {
                System.out.println("  > " + redoStack.get(i).getDescription());
            }
        }
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }
}
