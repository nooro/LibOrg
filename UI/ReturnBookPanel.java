package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dbConnection.*;
import exceptions.*;

public class ReturnBookPanel {
	private static Panel panel;
	
	private static InputField tf_bookID;
	private static InputField tf_username;
	private static Button btn_return;
	private static JLabel errorMessage;
	
	private static DBConnector db;
	
	public static void setUp(JFrame window, DBConnector dbConnector) {
		panel = new Panel(window, "Return book");
		try {
			db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		} catch (NoServerConnectionException e) {
			showErrorMessage(e.getMessage());
		}
		
		tf_username = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_username.getJTextField().setText("Username ");
		tf_username.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_username.getSize().width/2), 
	    									(int)( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    									tf_username.getSize().width, tf_username.getSize().height);
		panel.getJPanel().add(tf_username.getJTextField());
		
		tf_bookID = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_bookID.getJTextField().setText("Book ID ");
		tf_bookID.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_bookID.getSize().width/2), 
											(int)( (tf_username.getJTextField().getBounds().y + tf_username.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.01), 
											tf_bookID.getSize().width, tf_bookID.getSize().height);
		panel.getJPanel().add(tf_bookID.getJTextField());
		
		btn_return = new Button("RETURN", Resources.Fonts.LibraryOrganizer.panelButtons, Resources.Colors.white, Resources.Colors.orange);
		btn_return.getJButton().setSize((int) (btn_return.getJButton().getPreferredSize().width * 1.5), (int)(btn_return.getJButton().getPreferredSize().height*1.5));
		btn_return.getJButton().setLocation((int)(panel.getJPanel().getWidth()/2 - btn_return.getSize().width/2), 
										(int)( (tf_bookID.getJTextField().getBounds().y + tf_bookID.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.02));
	    panel.getJPanel().add(btn_return.getJButton());
	    
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.LibraryOrganizer.errorMessages);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    panel.getJPanel().add(errorMessage);
	}
	
	public static void show() {
		AddBookPanel.hide();
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
	    						(int) ( (btn_return.getJButton().getBounds().y + btn_return.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}
	
	public static void handleEvents() {
		btn_return.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( tf_bookID.getJTextField().getText().equals("Book ID ") || tf_username.getJTextField().getText().equals("Username ")) {
					showErrorMessage("Enter username and book id ");
					return;
				}
				
				try {
					BooksOperator.returnBook(db, tf_username.getJTextField().getText(), Integer.parseInt(tf_bookID.getJTextField().getText()));
					
					errorMessage.setText("Success! ");
					errorMessage.setForeground(Resources.Colors.white);
					Dimension errorMessageSize = errorMessage.getPreferredSize();
				    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
				    						(int) ( (btn_return.getJButton().getBounds().y + btn_return.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
				    						errorMessageSize.width, errorMessageSize.height);
				    errorMessage.setVisible(true);
				    
				    tf_bookID.getJTextField().setText("Book ID ");
				    tf_username.getJTextField().setText("Username ");
				    
				} catch (NumberFormatException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage("Books ids are numbers - don't write letters");
				} catch (NoServerConnectionException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				} catch (NoSuchUserException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				} catch (NoSuchBookException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				} catch(BookNotTakenException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	public static void setUpEffects() {
		btn_return.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_return.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} 
		});
		
		tf_bookID.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_bookID.getJTextField().getText().equals("Book ID ")) {
					tf_bookID.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_bookID.getJTextField().getText().isEmpty()) {
					tf_bookID.getJTextField().setText("Book ID ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
		
		tf_username.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_username.getJTextField().getText().equals("Username ")) {
					tf_username.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_username.getJTextField().getText().isEmpty()) {
					tf_username.getJTextField().setText("Username ");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}				
		});
	}
}
