package todoapp;

/**
 * Callback class for NoteManager's sync method
 * @author Dimitris Kolovos
 *
 */
public interface SyncCallback {
	
	public void finished();
	
	public void failed();
	
}
