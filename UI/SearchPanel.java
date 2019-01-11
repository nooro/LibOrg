package ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import dbConnection.*;
import exceptions.NoServerConnectionException;
import liborg.Book;

public class SearchPanel {
	
	private static Panel panel;
	private static JLabel titleLabel;
	private static DBConnector db;
	
	public static void setUp(JFrame window) {
		panel = new Panel(window, "");
		
		titleLabel = new JLabel("ID | Author | Title | Genre | Publishing year | Pages | Amount");
		titleLabel.setFont(Resources.Fonts.LibraryOrganizer.panelTitles);
		titleLabel.setForeground(Resources.Colors.white);
		titleLabel.setSize((int) titleLabel.getPreferredSize().getWidth(), (int)titleLabel.getPreferredSize().getHeight());
		titleLabel.setLocation((int)(panel.getJPanel().getWidth()/2 - titleLabel.getWidth()/2), 0);
		titleLabel.setVisible(true);
		panel.getJPanel().add(titleLabel);
	}
	
	public static void show(String text) {
		AddBookPanel.hide();
		ReturnBookPanel.hide();
		RemoveBookPanel.hide();
		TakenBooksListPanel.hide();
		WhoTookTheBookPanel.hide();
		panel.show();
		
		try {
			db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
			
			panel.getJPanel().updateUI();
			panel.getJPanel().removeAll();
			panel.getJPanel().add(titleLabel);
			
			List<Book> books = BooksCrawler.search(db, text);
			int i = 1;
			for(Book current : books) {
				JLabel label = new JLabel(current.toString());
				label.setFont(Resources.Fonts.LibraryOrganizer.takenBooks);
				if(current.getAmount() > 0) { label.setForeground(Resources.Colors.white); }
				else { label.setForeground(Resources.Colors.red); }
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
			
		} catch (NoServerConnectionException e) {}
	}
	
	public static void hide() {
		panel.getJPanel().setVisible(false);
	}
}
