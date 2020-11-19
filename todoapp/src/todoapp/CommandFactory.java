package todoapp;

import java.util.stream.Collectors;

/**
 * Creates commands from plain text
 * @author Dimitris Kolovos
 *
 */
public class CommandFactory {
	
	public Command createCommand(String text, NoteManager noteManager) {
		if (text.equalsIgnoreCase("shopping done")) {
			return new RemoveNotesCommand(noteManager.getNotes(NoteType.Shopping), noteManager);
		}
		else {
			return new AddNoteCommand(createNote(text), noteManager);
		}
	}
	
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
			return new Note(text, NoteType.Social);
		}
		else {
			return new Note(text, NoteType.General);
		}
	}
	
}
