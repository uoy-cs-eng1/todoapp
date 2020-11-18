package todoapp;

public interface NoteListener {
	
	/**
	 * Called when a note has been added to a NoteManager
	 * @param note
	 */
	public void noteAdded(Note note);
	
	/**
	 * Called when a note has been removed from a NoteManager
	 * @param note
	 */
	public void noteRemoved(Note note);
	
}
