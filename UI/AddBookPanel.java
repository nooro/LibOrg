package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import dbConnection.BooksOperator;
import dbConnection.DBConnector;
import exceptions.NoServerConnectionException;

public class AddBookPanel {
	private static Panel panel;
	
	private static InputField tf_title;
	private static InputField tf_author;
	private static InputField tf_genre;
	private static InputField tf_year;
	private static InputField tf_amount;
	private static InputField tf_pages;
	
	private static Button btn_add;
	
	private static JLabel errorMessage;
	
	private static DBConnector db;
	
	public static void setUp(JFrame window, DBConnector dbConnector) {
		panel = new Panel(window, "Add new book");
		try {
			db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		} catch (NoServerConnectionException e) {
			showErrorMessage(e.getMessage());
		}
		
		tf_title = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_title.getJTextField().setText("Title ");
		tf_title.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_title.getSize().width/2), 
	    									(int)( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    									tf_title.getSize().width, tf_title.getSize().height);
		panel.getJPanel().add(tf_title.getJTextField());
		
		tf_author = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_author.getJTextField().setText("Author ");
		tf_author.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_author.getSize().width/2), 
											(int)( (tf_title.getJTextField().getBounds().y + tf_title.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_author.getSize().width, tf_author.getSize().height);
		panel.getJPanel().add(tf_author.getJTextField());
		
		tf_genre = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_genre.getJTextField().setText("Genre ");
		tf_genre.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_genre.getSize().width/2), 
											(int)( (tf_author.getJTextField().getBounds().y + tf_author.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_genre.getSize().width, tf_genre.getSize().height);
		panel.getJPanel().add(tf_genre.getJTextField());
		
		tf_pages = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_pages.getJTextField().setText("Pages ");
		tf_pages.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_pages.getSize().width/2), 
											(int)( (tf_genre.getJTextField().getBounds().y + tf_genre.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_pages.getSize().width, tf_pages.getSize().height);
		panel.getJPanel().add(tf_pages.getJTextField());
		
		tf_year = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_year.getJTextField().setText("Publishing year ");
		tf_year.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_year.getSize().width/2), 
											(int)( (tf_pages.getJTextField().getBounds().y + tf_pages.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_year.getSize().width, tf_year.getSize().height);
		panel.getJPanel().add(tf_year.getJTextField());
		
		tf_amount = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_amount.getJTextField().setText("Amount ");
		tf_amount.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_amount.getSize().width/2), 
											(int)( (tf_year.getJTextField().getBounds().y + tf_year.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_amount.getSize().width, tf_amount.getSize().height);
		panel.getJPanel().add(tf_amount.getJTextField());
		
		btn_add = new Button("ADD", Resources.Fonts.LibraryOrganizer.panelButtons, Resources.Colors.white, Resources.Colors.orange);
		btn_add.getJButton().setSize((int) (btn_add.getJButton().getPreferredSize().width * 1.5), (int)(btn_add.getJButton().getPreferredSize().height*1.5));
		btn_add.getJButton().setLocation((int)(panel.getJPanel().getWidth()/2 - btn_add.getSize().width/2), 
										(int)( (tf_amount.getJTextField().getBounds().y + tf_amount.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.02));
	    panel.getJPanel().add(btn_add.getJButton());
	    
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.LibraryOrganizer.errorMessages);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    panel.getJPanel().add(errorMessage);
	}
	
	public static void show() {
		ReturnBookPanel.hide();
		RemoveBookPanel.hide();
		TakeBookPanel.hide();
		TakenBooksListPanel.hide();
		WhoTookTheBookPanel.hide();
		SearchPanel.hide();
		panel.show();
	}
	
	public static void hide() {
		panel.hide();
	}
	
	private static void showErrorMessage(String message) {
		errorMessage.setText(message);
		Dimension errorMessageSize = errorMessage.getPreferredSize();
	    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
	    						(int) ( (btn_add.getJButton().getBounds().y + btn_add.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}
	
	public static void handleEvents() {
		btn_add.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tf_title.getJTextField().getText().equals("Title ") || tf_author.getJTextField().getText().equals("Author ") 
					|| tf_genre.getJTextField().getText().equals("Genre ") || tf_year.getJTextField().getText().equals("Publishing year ")
					|| tf_pages.getJTextField().getText().equals("Pages ") || tf_amount.getJTextField().getText().equals("Amount ") ) {
					
					showErrorMessage("Fill all input fields ");
					return;
				}
				
				try {
					BooksOperator.addNewBook(db, 
											tf_title.getJTextField().getText(), 
											tf_author.getJTextField().getText(), 
											tf_genre.getJTextField().getText(), 
											Integer.parseInt(tf_year.getJTextField().getText()), 
											Integer.parseInt(tf_pages.getJTextField().getText()), 
											Integer.parseInt(tf_amount.getJTextField().getText())
													);
					
					errorMessage.setText("Success! ");
					errorMessage.setForeground(Resources.Colors.white);
					Dimension errorMessageSize = errorMessage.getPreferredSize();
				    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
				    						(int) ( (btn_add.getJButton().getBounds().y + btn_add.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
				    						errorMessageSize.width, errorMessageSize.height);
				    errorMessage.setVisible(true);
				    
				    tf_title.getJTextField().setText("Title ");
				    tf_author.getJTextField().setText("Author ");
				    tf_amount.getJTextField().setText("Amount ");
				    tf_genre.getJTextField().setText("Genre ");
				    tf_pages.getJTextField().setText("Pages ");
				    tf_year.getJTextField().setText("Publishing year ");
				    
				} catch (NumberFormatException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage("Years, pages and amouts are numbers - don't use letters ");
				} catch (NoServerConnectionException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	public static void setUpEffects() {
		btn_add.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_add.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} 
		});
		
		tf_title.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_title.getJTextField().getText().equals("Title ")) {
					tf_title.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_title.getJTextField().getText().isEmpty()) {
					tf_title.getJTextField().setText("Title ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_author.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_author.getJTextField().getText().equals("Author ")) {
					tf_author.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_author.getJTextField().getText().isEmpty()) {
					tf_author.getJTextField().setText("Author ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_genre.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_genre.getJTextField().getText().equals("Genre ")) {
					tf_genre.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_genre.getJTextField().getText().isEmpty()) {
					tf_genre.getJTextField().setText("Genre ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_pages.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_pages.getJTextField().getText().equals("Pages ")) {
					tf_pages.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_pages.getJTextField().getText().isEmpty()) {
					tf_pages.getJTextField().setText("Pages ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_year.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_year.getJTextField().getText().equals("Publishing year ")) {
					tf_year.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_year.getJTextField().getText().isEmpty()) {
					tf_year.getJTextField().setText("Publishing year ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_amount.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_amount.getJTextField().getText().equals("Amount ")) {
					tf_amount.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_amount.getJTextField().getText().isEmpty()) {
					tf_amount.getJTextField().setText("Amount ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
	}	
}
