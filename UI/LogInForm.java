package UI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class LogInForm {
	private JFrame window;
	
	public LogInForm() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupTheWindow();
				
				Resources.Fonts.LogIn.setUp(window);
				
		}});
	}
	
	private void setupTheWindow() {
		window = new JFrame("Log-in form");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setAlwaysOnTop(true);
		
		//Get the screen resolution in pixels
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		//Set the window size to be 1/4 of the screen's width and 1/2 of the screen's height
		window.setSize(screenWidth / 4, screenHeight / 2);
		
		//Center the window
		window.setLocation(screenWidth / 2 - window.getWidth() / 2, screenHeight / 2 - window.getHeight() / 2);
		
		window.setLayout(null);
		 
		window.getContentPane().setBackground(Resources.Colors.background);
		
		window.setVisible(true);
	}
}
