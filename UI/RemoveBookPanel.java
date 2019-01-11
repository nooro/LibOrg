package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import dbConnection.*;
import exceptions.*;

public class RemoveBookPanel {
	private static Panel panel;
	
	private static InputField tf_bookID;
	private static Button btn_remove;
	private static JLabel errorMessage;
	
	private static DBConnector db;
	
	public static void setUp(JFrame window, DBConnector dbConnector) {
		panel = new Panel(window, "Remove book");
		try {
			db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		} catch (NoServerConnectionException e) {
			showErrorMessage(e.getMessage());
		}
		
		tf_bookID = new InputField("text", Resources.Fonts.LibraryOrganizer.inputFields, Resources.Colors.white, Resources.Colors.green);
		tf_bookID.getJTextField().setText("Book ID ");
		tf_bookID.getJTextField().setBounds((int)(panel.getJPanel().getWidth()/2 - tf_bookID.getSize().width/2), 
	    									(int)( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    									tf_bookID.getSize().width, tf_bookID.getSize().height);
		panel.getJPanel().add(tf_bookID.getJTextField());
		
		
		btn_remove = new Button("REMOVE", Resources.Fonts.LibraryOrganizer.panelButtons, Resources.Colors.white, Resources.Colors.orange);
		btn_remove.getJButton().setSize((int) (btn_remove.getJButton().getPreferredSize().width * 1.5), (int)(btn_remove.getJButton().getPreferredSize().height*1.5));
		btn_remove.getJButton().setLocation((int)(panel.getJPanel().getWidth()/2 - btn_remove.getSize().width/2), 
										(int)( (tf_bookID.getJTextField().getBounds().y + tf_bookID.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.02));
	    panel.getJPanel().add(btn_remove.getJButton());
	    
	    
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.LibraryOrganizer.errorMessages);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    panel.getJPanel().add(errorMessage);
	}
	
	public static void show() {
		AddBookPanel.hide();
		ReturnBookPanel.hide();
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
	    						(int) ( (btn_remove.getJButton().getBounds().y + btn_remove.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}
	
	public static void handleEvents() {
		btn_remove.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( tf_bookID.getJTextField().getText().equals("Book ID ")) {
					showErrorMessage("Enter book id ");
					return;
				}
				
				try {
					BooksOperator.removeBook( db, Integer.parseInt(tf_bookID.getJTextField().getText()) );
					
					errorMessage.setText("Success! ");
					errorMessage.setForeground(Resources.Colors.white);
					Dimension errorMessageSize = errorMessage.getPreferredSize();
				    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
				    						(int) ( (btn_remove.getJButton().getBounds().y + btn_remove.getSize().height) + panel.getJPanel().getHeight()*0.02 ), 
				    						errorMessageSize.width, errorMessageSize.height);
				    errorMessage.setVisible(true);
				    
				    tf_bookID.getJTextField().setText("Book ID ");
				    
				} catch (NumberFormatException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage("Books ids are numbers - don't write letters");
				} catch (NoServerConnectionException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				} catch (InvalidIDException exception) {
					errorMessage.setForeground(Resources.Colors.red);
					showErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	public static void setUpEffects() {
		btn_remove.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_remove.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
	}
}
