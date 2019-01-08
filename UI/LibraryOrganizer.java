package ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import liborg.User;


public class LibraryOrganizer {
	
	private JFrame window = null;
	private JPanel header = null;
	private Button btn_addBook;
	private Button btn_removeBook;
	private Button btn_takeBook;
	private Button btn_returnBook;
	
	public LibraryOrganizer() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if( User.isLoggedIn() ) {
					setupTheWindow();			
					Resources.Fonts.LibraryOrganizer.setUp(window);
					setupTheHeader();
					setupTheBody();
					setUpEffects();
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
		}
		
//		JLabel logOut = new JLabel("LOG OUT ");
//		logOut.setFont(Resources.Fonts.LibraryOrganizer.logOut);
//		logOut.setForeground(Resources.Colors.red);
//		logOut.setBounds(header.getWidth() - logOut.getPreferredSize().width, header.getHeight()/2 - logOut.getPreferredSize().height/2, logOut.getPreferredSize().width, logOut.getPreferredSize().height);
//		header.add(logOut);
	}
	
	private void setupTheBody() {
		
	}
	
	private void setUpEffects() {
		
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
		}
	}	
}
