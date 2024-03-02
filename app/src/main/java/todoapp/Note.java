package todoapp;

/**
 * Note with a text and a type
 * @author Dimitris Kolovos
 *
 */
public class Note {
	
	protected String text;
	protected NoteType type;
	
	public Note(String text, NoteType type) {
		super();
		this.text = text;
		this.type = type;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public NoteType getType() {
		return type;
	}
	
	public void setType(NoteType type) {
		this.type = type;
	}
	
}
