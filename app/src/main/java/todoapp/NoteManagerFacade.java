package todoapp;

import java.util.List;

public class NoteManagerFacade implements NoteListener, SyncCallback {
	
	protected NoteManager noteManager = new NoteManager();
	protected CommandStack commandStack = new CommandStack();
	protected CommandFactory commandFactory = new CommandFactory();
	
	public NoteManagerFacade() {
		noteManager.addListener(this);
	}
	
	public void addNote(String text) {
		commandStack.execute(commandFactory.
				createCommand(text, noteManager));
	}

	public void undo() {
		if (commandStack.canUndo()) 
			commandStack.undo();
	}
	
	public void redo() {
		if (commandStack.canRedo()) commandStack.redo();
	}
	
	public List<Note> getNotes() {
		return noteManager.getNotes();
	}
	
	public void sync(String server) {
		noteManager.sync(server, this);
	}
	
	@Override
	public void noteAdded(Note note) {
		
	}

	@Override
	public void noteRemoved(Note note) {
		
	}

	@Override
	public void finished() {
		
	}

	@Override
	public void failed() {
		
	}
	
}
