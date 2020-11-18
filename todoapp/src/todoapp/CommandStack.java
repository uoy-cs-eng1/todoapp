package todoapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard command stack class
 * for undoing and redoing commands
 * @author Dimitris Kolovos
 *
 */
public class CommandStack {
	
	// Commands that have been already executed
	protected List<Command> stack = new ArrayList<Command>();
	
	// Position of the current command
	protected int stackPointer = 0;
	
	/**
	 * Runs the execute method of the provided
	 * command and adds it to the stack of 
	 * executed commands
	 * @param command
	 */
	public void execute(Command command) {
		stack = stack.subList(0, stackPointer);
		command.execute();
		stack.add(command);
		stackPointer++;
	}
	
	/**
	 * Runs the undo method of the last
	 * executed command.
	 */
	public void undo() {
		stackPointer--;
		stack.get(stackPointer).undo();
	}
	
	/**
	 * Checks if there is a command to undo
	 * @return
	 */
	public boolean canUndo() {
		return stackPointer > 0;
	}
	
	/**
	 * Runs the execute method of the last 
	 * command that was undone
	 */
	public void redo() {
		stack.get(stackPointer).execute();
		stackPointer++;
	}
	
	/**
	 * Checks if there is a command to redo
	 * @return
	 */
	public boolean canRedo() {
		return stackPointer < stack.size();
	}
	
}
