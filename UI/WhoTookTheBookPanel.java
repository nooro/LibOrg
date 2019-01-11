package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import dbConnection.*;
import exceptions.*;
import liborg.TakenBook;

public class WhoTookTheBookPanel {
	private static Panel panel;
	private static DBConnector db;
	private static JLabel errorMessage;
	private static JLabel titleLabel;
	private static InputField tf_bookID;
	private static Button btn_list;
	
	public static void setUp(JFrame window, DBConnector dbConnector) {
		panel = new Panel(window, "Who took the book");
		db = dbConnector;
		tf_bookID = new InputField("text", Resources.Fonts.Forms.inputFields, Resources.Colors.white, Resources.Colors.green );
		tf_bookID.getJTextField().setBounds( (int)(panel.getJPanel().getWidth()/2 - tf_bookID.getSize().width/2), 
											(int)( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    										tf_bookID.getSize().width, 
	    										tf_bookID.getSize().height);
		tf_bookID.getJTextField().setText("Book ID ");
	    panel.getJPanel().add(tf_bookID.getJTextField());
	    
	    btn_list = new Button("CHECK", Resources.Fonts.LibraryOrganizer.panelButtons, Resources.Colors.white, Resources.Colors.orange);
	    btn_list.getJButton().setSize((int) (btn_list.getJButton().getPreferredSize().width * 1.5), (int)(btn_list.getJButton().getPreferredSize().height*1.5));
	    btn_list.getJButton().setLocation((int)(panel.getJPanel().getWidth()/2 - btn_list.getSize().width/2), 
										(int)( (tf_bookID.getJTextField().getBounds().y + tf_bookID.getJTextField().getHeight()) + panel.getJPanel().getHeight()*0.02));
	    panel.getJPanel().add(btn_list.getJButton());
	    
		titleLabel = new JLabel("User | Book | Take date | Return date");
		titleLabel.setFont(Resources.Fonts.LibraryOrganizer.panelTitles);
		titleLabel.setForeground(Resources.Colors.white);
		titleLabel.setSize((int) titleLabel.getPreferredSize().getWidth(), (int)titleLabel.getPreferredSize().getHeight());
		titleLabel.setLocation((int)(panel.getJPanel().getWidth()/2 - titleLabel.getWidth()/2), (int)((btn_list.getJButton().getBounds().y + btn_list.getJButton().getSize().height) + panel.getJPanel().getSize().height*0.02));
		titleLabel.setVisible(false);
		panel.getJPanel().add(titleLabel);
	    
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.LibraryOrganizer.errorMessages);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    panel.getJPanel().add(errorMessage);
	}
	
	private static void list() {
		
		try {
			panel.getJPanel().removeAll();
			panel.getJPanel().add(titleLabel);
			panel.getJPanel().add(tf_bookID.getJTextField());
			panel.getJPanel().add(btn_list.getJButton());
			panel.getJPanel().add(errorMessage);
			
			errorMessage.setVisible(false);
			titleLabel.setVisible(true);
			
			List<TakenBook> taken = BooksCrawler.checkWhoTookBook(db, Integer.parseInt(tf_bookID.getJTextField().getText()));
			int i = 1;
			for(TakenBook current : taken) {
				JLabel label = new JLabel(current.toString());
				label.setFont(Resources.Fonts.LibraryOrganizer.takenBooks);
				label.setForeground(Resources.Colors.white);
				Dimension labelSize = label.getPreferredSize();
				if(i == 1) {
					label.setBounds(panel.getJPanel().getWidth()/2 - (int)labelSize.getWidth()/2, 
							(int) ( (titleLabel.getBounds().y + titleLabel.getHeight()) + panel.getJPanel().getHeight()*0.05), 
							labelSize.width, labelSize.height);
					label.setVisible(true);
				}else {
					label.setBounds(panel.getJPanel().getWidth()/2 - (int)labelSize.getWidth()/2, 
							(int) ( (titleLabel.getBounds().y + titleLabel.getHeight()) + (i*(labelSize.height+label.getBounds().y)) + panel.getJPanel().getHeight()*0.01), 
							labelSize.width, labelSize.height);
					label.setVisible(true);
				}
				
			    panel.getJPanel().add(label);
			    i++;
			}
			
		} catch (NoServerConnectionException e) {
			showErrorMessage(e.getMessage());
		}
	}
	
	public static void show() {
		AddBookPanel.hide();
		ReturnBookPanel.hide();
		RemoveBookPanel.hide();
		TakeBookPanel.hide();
		TakenBooksListPanel.hide();
		SearchPanel.hide();
		panel.getJPanel().updateUI();
		panel.show();
		
	}
	
	public static void hide() {
		panel.hide();
	}
	
	private static void showErrorMessage(String message) {
		errorMessage.setText(message);
		Dimension errorMessageSize = errorMessage.getPreferredSize();
	    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
	    						(int) ( (titleLabel.getBounds().y + titleLabel.getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}
	
	public static void handleEvents() {
		btn_list.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( tf_bookID.getJTextField().getText().equals("Book ID ") ) {
					showErrorMessage("Enter book id ");
					return;
				}
				
				try {					
					Integer.parseInt(tf_bookID.getJTextField().getText());
				}catch (NumberFormatException exception) {
					showErrorMessage("Books ids are numbers - don't write letters");
					return;
				}
				
				list();
				show();
			}
		});
	}
	
	public static void setUpEffects() {
		btn_list.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_list.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
