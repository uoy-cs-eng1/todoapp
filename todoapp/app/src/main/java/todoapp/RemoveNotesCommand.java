package todoapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RemoveNotesCommand implements Command {
	
	protected List<Note> toRemove = null;
	protected NoteManager noteManager = null;
	protected LinkedHashMap<Note, Integer> notePositions = null;
	
	public RemoveNotesCommand(List<Note> toRemove, NoteManager noteManager) {
		this.toRemove = toRemove;
		this.noteManager = noteManager;
	}
	
	@Override
	public void execute() {
		notePositions = new LinkedHashMap<>();
		List<Note> notes = noteManager.getNotes();
		for (Note note : toRemove) {
			notePositions.put(note, notes.indexOf(note));
			noteManager.removeNote(note);
			notes = noteManager.getNotes();
		}
	}

	@Override
	public void undo() {
		ArrayList<Note> reversedNotes = new ArrayList<Note>(notePositions.keySet());
		Collections.reverse(reversedNotes);
		for (Note note : reversedNotes) {
			noteManager.addNote(notePositions.get(note), note);
		}
	}

}
