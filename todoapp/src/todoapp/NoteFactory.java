package todoapp;

/**
 * Creates instances of Note from plain text
 * @author Dimitris Kolovos
 *
 */
public class NoteFactory {
	
	/**
	 * Creates a Note from plain text and attempts to detect its type
	 * @param text
	 * @return note with text and type set
	 */
	public Note createNote(String text) {
		
		if (text.startsWith("Buy")) {
			return new Note(text, NoteType.Shopping);
		}
		else if (text.startsWith("Call") || text.startsWith("Reply")) {
			return new Note(text, NoteType.Communication);
		}
		else {
			return new Note(text, NoteType.General);
		}
	}
	
}
