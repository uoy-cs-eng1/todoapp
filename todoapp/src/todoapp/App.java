package todoapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class App extends JFrame implements ActionListener, NoteListener {
	
	// Table that lists all notes
	protected JTable notesTable = new JTable();
	
	// Command stack for enabling undo-redo
	protected CommandStack commandStack = new CommandStack();
	
	// Factory that creates commands from text
	protected CommandFactory commandFactory = new CommandFactory();
	
	// Stores the notes of the app
	protected NoteManager noteManager = new NoteManager();
	
	// Text field and buttons for adding notes,
	// undoing, redoing and synchronising
	protected JTextField noteTextField = new JTextField();
	protected JButton addNoteButton;
	protected JButton undoButton;
	protected JButton redoButton;
	protected JButton syncButton;
	
	// Label shown while synchronising
	protected JLabel statusLabel = new JLabel();
	
	/**
	 * Runs the application. No arguments are expected/supported.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new App().run();
	}
	
	/**
	 * Called when the add note button is pressed
	 */
	public void addNoteButtonPressed() {
		commandStack.execute(commandFactory.createCommand(noteTextField.getText(), noteManager));
		noteTextField.setText("");
	}
	
	/**
	 * Called when the undo button is pressed
	 */
	public void undoButtonPressed() {
		if (commandStack.canUndo()) commandStack.undo();
	}
	
	/**
	 * Called when the redo is pressed
	 */
	public void redoButtonPressed() {
		if (commandStack.canRedo()) commandStack.redo();
	}
	
	/**
	 * Called when the sync button is pressed
	 */
	public void syncButtonPressed() {
		statusLabel.setVisible(true);
		statusLabel.setText("Synchronising...");
		noteManager.sync("www.todoapp.com", new SyncCallback() {
			
			@Override
			public void finished() {
				statusLabel.setVisible(false);
			}
			
			@Override
			public void failed() {
				JOptionPane.showMessageDialog(App.this, "Synchronisation failed");
				statusLabel.setVisible(false);
			}
		});
	}
	
	@Override
	public void noteAdded(Note note) {
		updateNotesAndTitle();
	}
	
	@Override
	public void noteRemoved(Note note) {
		updateNotesAndTitle();
	}
	
	/**
	 * Updates the notes table and the title of the app
	 */
	public void updateNotesAndTitle() {
		List<Note> notes = noteManager.getNotes();
		this.setTitle("To-do app - " + notes.size() + " note" + 
				(notes.size() != 1 ? "s" : ""));
		notesTable.updateUI();
	}
	
	/**
	 * Sets up the UI of the app
	 * @throws Exception
	 */
	public void run() throws Exception {
		
		getContentPane().setLayout(new BorderLayout());
		
		// Set up the containment hierarchy and borders of the components of the UI
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(8,8,8,8));
		JScrollPane notesTableScrollPane = new JScrollPane(notesTable);
		notesTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		mainPanel.add(notesTableScrollPane, BorderLayout.CENTER);
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		bottomPanel.add(buttonsPanel, BorderLayout.EAST);
		bottomPanel.add(noteTextField, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(statusLabel, BorderLayout.NORTH);
		statusLabel.setVisible(false); // The status label should be invisible to start with
		statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Set up the notes table
		notesTable.setRowHeight(30);
		notesTable.setModel(new DefaultTableModel() {
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Note note = noteManager.getNotes().get(rowIndex);
				if (columnIndex == 0) return note.getText();
				else return note.getType();
			}
			
			@Override
			public int getRowCount() {
				return noteManager.getNotes().size();
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0) return "Note";
				else return "Type";
			}
			
			@Override
			public int getColumnCount() {
				return 2;
			}
			
		});
		
		// Set up the buttons at the bottom of the frame
		addNoteButton = new JButton("Add");
		addNoteButton.addActionListener(this);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(this);
		redoButton = new JButton("Redo");
		redoButton.addActionListener(this);
		syncButton = new JButton("Sync");
		syncButton.addActionListener(this);
		
		buttonsPanel.add(addNoteButton);
		buttonsPanel.add(undoButton);
		buttonsPanel.add(redoButton);
		buttonsPanel.add(syncButton);
		noteManager.addListener(this);
		
		// Set up a key listener for noteTextField so that 
		// when enter is pressed while editing, the 
		// addNoteButtonPressed method is called
		noteTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent keyEvent) {}
			
			@Override
			public void keyReleased(KeyEvent keyEvent) {}
			
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == 10) {
					addNoteButtonPressed();
				}
			}
		});
		
		// App will terminate with the window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialise bounds, title and visibility
		setBounds(200, 200, 500, 500);
		updateNotesAndTitle();
		setVisible(true);
		
		// The text field should have focus on startup
		noteTextField.requestFocus();
		
	}
	
	/**
	 * Catch-all listener for all buttons in the app;
	 * delegates to the right ...ButtonPressed() method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addNoteButton) addNoteButtonPressed();
		else if (e.getSource() == undoButton) undoButtonPressed();
		else if (e.getSource() == syncButton) syncButtonPressed();
		else redoButtonPressed();
	}
	
}
