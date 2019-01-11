package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import dbConnection.*;
import exceptions.*;
import liborg.TakenBook;

public class TakenBooksListPanel {
	private static Panel panel;
	private static DBConnector db;
	private static JLabel errorMessage;
	private static JLabel titleLabel;
	
	public static void setUp(JFrame window, DBConnector dbConnector) {
		panel = new Panel(window, "");
		
		titleLabel = new JLabel("User | Book | Take date | Return date");
		titleLabel.setFont(Resources.Fonts.LibraryOrganizer.panelTitles);
		titleLabel.setForeground(Resources.Colors.orange);
		titleLabel.setSize((int) titleLabel.getPreferredSize().getWidth(), (int)titleLabel.getPreferredSize().getHeight());
		titleLabel.setLocation((int)(panel.getJPanel().getWidth()/2 - titleLabel.getWidth()/2), 0);
		panel.getJPanel().add(titleLabel);
		
	    listTakenBooks();
	    
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.LibraryOrganizer.errorMessages);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    panel.getJPanel().add(errorMessage);
	}
	
	private static void listTakenBooks() {
		
		try {
			db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
			
			panel.getJPanel().removeAll();
			panel.getJPanel().add(titleLabel);
			List<TakenBook> taken = BooksCrawler.getTakenBooks(db);
			int i = 1;
			for(TakenBook current : taken) {
				JLabel label = new JLabel(current.toString());
				label.setFont(Resources.Fonts.LibraryOrganizer.takenBooks);
				label.setForeground(Resources.Colors.white);
				Dimension labelSize = label.getPreferredSize();
				if(i == 1) {
					label.setBounds(panel.getJPanel().getWidth()/2 - (int)labelSize.getWidth()/2, 
							(int) ( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
							labelSize.width, labelSize.height);
					label.setVisible(true);
				}else {
					label.setBounds(panel.getJPanel().getWidth()/2 - (int)labelSize.getWidth()/2, 
							(int) ( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + (i*(labelSize.height+label.getBounds().y)) + panel.getJPanel().getHeight()*0.01), 
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
		WhoTookTheBookPanel.hide();
		SearchPanel.hide();
		panel.getJPanel().updateUI();
		listTakenBooks();
		panel.show();
	}
	
	public static void hide() {
		panel.hide();
	}
	
	private static void showErrorMessage(String message) {
		errorMessage.setText(message);
		Dimension errorMessageSize = errorMessage.getPreferredSize();
	    errorMessage.setBounds(panel.getJPanel().getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
	    						(int)( (panel.getTitle().getBounds().y + panel.getTitle().getHeight()) + panel.getJPanel().getHeight()*0.05), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}

}
