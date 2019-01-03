package ui;

import javax.swing.*;
import java.awt.*;

public class LibraryOrganizer {
	
	private JFrame window = null;
	
	
	public LibraryOrganizer() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupTheWindow();			
				
				Resources.Fonts.LibraryOrganizer.setUp(window);
				
				setupTheBody();
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
	
	private void setupTheBody() {
		
	}
	
}
