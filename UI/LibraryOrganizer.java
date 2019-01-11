package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dbConnection.DBConnector;
import liborg.User;


public class LibraryOrganizer {
	
	private JFrame window = null;
	private JPanel header = null;
	private Button btn_addBook;
	private Button btn_removeBook;
	private Button btn_takeBook;
	private Button btn_returnBook;
	private Button btn_listTakenBooks;
	private Button btn_whoTookBook;
	private InputField tf_search;
	private Button btn_search;
	private JLabel logOut;
	
	private DBConnector db;
	
	public LibraryOrganizer(DBConnector dbConnector) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if( User.isLoggedIn() ) {
					setupTheWindow();			
					Resources.Fonts.LibraryOrganizer.setUp(window);
					setupTheHeader();
					
					AddBookPanel.setUp(window, dbConnector);
					AddBookPanel.setUpEffects();
					AddBookPanel.handleEvents();
					
					RemoveBookPanel.setUp(window, dbConnector);
					RemoveBookPanel.setUpEffects();
					RemoveBookPanel.handleEvents();
					
					ReturnBookPanel.setUp(window, dbConnector);
					ReturnBookPanel.setUpEffects();
					ReturnBookPanel.handleEvents();
					
					TakeBookPanel.setUp(window, dbConnector);
					TakeBookPanel.setUpEffects();
					TakeBookPanel.handleEvents();
					
					WhoTookTheBookPanel.setUp(window, dbConnector);
					WhoTookTheBookPanel.setUpEffects();
					WhoTookTheBookPanel.handleEvents();
					
					TakenBooksListPanel.setUp(window, dbConnector);
					
					SearchPanel.setUp(window);
					
					setUpHeaderEffects();
					db = dbConnector;
					handleHeaderEvents();
				}
		}});
	}
	
	private void setupTheWindow() {
		window = new JFrame("Library Organizer");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Get the screen resolution in pixels and make the window fullscreen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		window.setLayout(null);

		window.getContentPane().setBackground(Resources.Colors.background);
		
		window.setVisible(true);
	}
	
	private void setupTheHeader() {
		header = new JPanel();
		header.setBounds(0, 0, window.getWidth(), window.getHeight()/15);
		header.setBackground(Resources.Colors.headerBackground);
		header.setLayout(null);
		header.setVisible(true);
		window.add(header);

		//Set up the buttons available only for administrators
		if(User.isAdmin()) {
			btn_addBook = new Button("ADD BOOK", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_addBook.getJButton().setBounds(0, 0, 
												(int)(btn_addBook.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_addBook.getJButton());
			
			btn_removeBook = new Button("REMOVE BOOK", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_removeBook.getJButton().setBounds( (int)(btn_addBook.getJButton().getBounds().x+btn_addBook.getSize().width), 0, 
													(int)(btn_removeBook.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_removeBook.getJButton());
			
			btn_takeBook = new Button("TAKE BOOK", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_takeBook.getJButton().setBounds( (int)(btn_removeBook.getJButton().getBounds().x+btn_removeBook.getSize().width), 0, 
												(int)(btn_takeBook.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_takeBook.getJButton());
			
			btn_returnBook = new Button("RETURN BOOK", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_returnBook.getJButton().setBounds( (int)(btn_takeBook.getJButton().getBounds().x+btn_takeBook.getSize().width), 0, 
													(int)(btn_returnBook.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_returnBook.getJButton());
			
			btn_listTakenBooks = new Button("LIST TAKEN BOOKS", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_listTakenBooks.getJButton().setBounds( (int)(btn_returnBook.getJButton().getBounds().x+btn_returnBook.getSize().width), 0, 
													(int)(btn_listTakenBooks.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_listTakenBooks.getJButton());
			
			btn_whoTookBook = new Button("WHO TOOK THE BOOK", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.black);
			btn_whoTookBook.getJButton().setBounds( (int)(btn_listTakenBooks.getJButton().getBounds().x+btn_listTakenBooks.getSize().width), 0, 
													(int)(btn_whoTookBook.getJButton().getPreferredSize().width * 1.1), header.getHeight());
			header.add(btn_whoTookBook.getJButton());
		}
		
		logOut = new JLabel("LOG OUT ");
		logOut.setFont(Resources.Fonts.LibraryOrganizer.logOut);
		logOut.setForeground(Resources.Colors.red);
		logOut.setSize((int)(logOut.getPreferredSize().getWidth()*1.5), (int)(header.getHeight()));
		logOut.setLocation((int)(header.getWidth() -logOut.getWidth()), 0);
		header.add(logOut);
		
		btn_search = new Button("search", Resources.Fonts.LibraryOrganizer.buttons, Resources.Colors.white, Resources.Colors.orange);
		btn_search.getJButton().setBounds( (int)(logOut.getBounds().x-logOut.getSize().width), 0, 
												(int)(btn_search.getJButton().getPreferredSize().width * 1.1), header.getHeight()/2);
		header.add(btn_search.getJButton());
		
		tf_search = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.black );
		tf_search.getJTextField().setBounds( (int)(btn_search.getJButton().getBounds().x - tf_search.getSize().width), 0, 
	    										tf_search.getSize().width, header.getHeight()/2);
		tf_search.getJTextField().setText("...");
	    header.add(tf_search.getJTextField());
		
	}
	
	private void setUpHeaderEffects() {
		
		//Header buttons hover effects
		if(User.isAdmin()) {
			btn_addBook.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_addBook.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_addBook.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_addBook.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
			
			btn_removeBook.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_removeBook.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_removeBook.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_removeBook.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
			
			btn_takeBook.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_takeBook.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_takeBook.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_takeBook.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
			
			btn_returnBook.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_returnBook.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_returnBook.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_returnBook.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
			
			btn_listTakenBooks.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_listTakenBooks.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_listTakenBooks.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_listTakenBooks.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
			
			btn_whoTookBook.getJButton().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_whoTookBook.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btn_whoTookBook.getJButton().setBackground(Resources.Colors.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_whoTookBook.getJButton().setBackground(Resources.Colors.black);
		        } 
			});
		}
		
		//Log-out hover effects
		logOut.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	        	logOut.setForeground(Resources.Colors.white);
	        	logOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	        	logOut.setForeground(Resources.Colors.red);
	        }
		});
		
		btn_search.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_search.getJButton().setBackground(Resources.Colors.green);
				btn_search.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_search.getJButton().setBackground(Resources.Colors.orange);
	        } 
		});
		
		tf_search.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_search.getJTextField().getText().equals("...")) {
					tf_search.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_search.getJTextField().getText().isEmpty()) {
					tf_search.getJTextField().setText("...");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}
						
		});
		
	}
	
	private void handleHeaderEvents() {
		if(User.isAdmin()) {
			btn_addBook.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AddBookPanel.show();
				}
			});	
			
			btn_removeBook.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RemoveBookPanel.show();
				}
			});	
			
			btn_takeBook.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TakeBookPanel.show();
				}
			});	
			
			btn_returnBook.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ReturnBookPanel.show();
				}
			});
			
			btn_listTakenBooks.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TakenBooksListPanel.show();
				}
			});
			
			btn_whoTookBook.getJButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					WhoTookTheBookPanel.show();
				}
			});
		}
			
		logOut.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				User.logOut();
				window.setVisible(false);
				new LogInForm(db);
	        }
		});
		
		btn_search.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchPanel.show(tf_search.getJTextField().getText());
			}
		});
	}
}
