package todoapp;

/**
 * Command that adds a Note to a NoteManager
 * @author Dimitris Kolovos
 *
 */
public class AddNoteCommand implements Command {
	
	protected Note note;
	protected NoteManager noteManager;
	
	public AddNoteCommand(Note note, NoteManager noteManager) {
		super();
		this.note = note;
		this.noteManager = noteManager;
	}

	/**
	 * Adds the note to the manager
	 */
	@Override
	public void execute() {
		noteManager.addNote(note);
	}

	/**
	 * Undoes the effect of execute by
	 * removing the note from the manager
	 */
	@Override
	public void undo() {
		noteManager.removeNote(note);
	}

}
