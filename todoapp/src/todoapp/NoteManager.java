package todoapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Manages a list of notes
 * @author Dimitris Kolovos
 *
 */
public class NoteManager {
	
	protected List<NoteListener> listeners = new ArrayList<>();
	protected List<Note> notes = new ArrayList<>();
	
	/**
	 * Adds a listener that will be notified when
	 * notes are added/removed
	 * @param listener
	 */
	public void addListener(NoteListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Removes the provided listener
	 * @param listener
	 */
	public void removeListener(NoteListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Adds a note and notifies listeners
	 * @param note
	 */
	public void addNote(int index, Note note) {
		notes.add(index, note);
		for (NoteListener listener : listeners) {
			listener.noteAdded(note);
		}
	}
	
	/**
	 * Adds a note and notifies listeners
	 * @param note
	 */
	public void addNote(Note note) {
		addNote(notes.size(), note);
	}
	
	/**
	 * Removes a note and notifies listeners
	 * @param note
	 */
	public void removeNote(Note note) {
		notes.remove(note);
		for (NoteListener listener : listeners) {
			listener.noteRemoved(note);
		}
	}
	
	/**
	 * Returns an unmodifiable copy of the list of notes
	 * If you need to add/remove notes use the addNote/removeNote
	 * methods instead
	 * @return
	 */
	public List<Note> getNotes() {
		return Collections.unmodifiableList(notes);
	}
	
	/**
	 * Convenience method that returns all notes of
	 * a specified type
	 * @param type
	 * @return
	 */
	public List<Note> getNotes(NoteType type) {
		return notes.stream().
				filter(n -> n.getType() == NoteType.Shopping).
				collect(Collectors.toList());
	}
	
	/**
	 * Synchronises the notes in the app with a remote server
	 * and notifies the callback object when synchronisation
	 * had finished/failed
	 * @param server The URL of the server to synchronise with
	 * @param callback The callback object
	 */
	public void sync(String server, SyncCallback callback) {
		// Simulate synchronisation with a remote server
		// that takes 2 seconds
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (Math.random() > 0.5) {
					callback.finished();
				}
				else {
					callback.failed();
				}
			}
		}, 2000);
		
		
	}
	
}
