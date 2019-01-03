package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import dbConnection.DBConnector;
import liborg.User;

public class LogInForm {
	private JFrame window;
	
	private InputField tf_username;
	private InputField tf_password;
	private Button btn_submit;
	JLabel registrationLink;
	
	public LogInForm() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupTheWindow();
				
				Resources.Fonts.Registration.setUp(window);
				
				setupTheBody();
				
				handleEvents();
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
	
	private void setupTheBody() {
		//Set up the title
		JLabel title = new JLabel("Log in");
		title.setFont(Resources.Fonts.Registration.title);
		title.setForeground(Resources.Colors.orange);
	    Dimension titleSize = title.getPreferredSize();
	    title.setBounds(window.getWidth()/2-titleSize.width/2, (int)(window.getHeight()*0.09), titleSize.width, titleSize.height);
	    window.add(title);
	    
	    //Set up the username input text field
	    tf_username = new InputField("text", Resources.Fonts.Registration.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_username.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_username.getSize().width/2), 
	    										(int)( (title.getBounds().y + title.getHeight()) + (window.getHeight() * 0.1) ), 
	    										tf_username.getSize().width, 
	    										tf_username.getSize().height);
	    window.add(tf_username.getJTextField());
	   
	    //Set up the password input text field
	    tf_password = new InputField("password", Resources.Fonts.Registration.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_password.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_password.getSize().width/2), 
	    										(int)( (tf_username.getJTextField().getBounds().y + tf_username.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
	    										tf_password.getSize().width, 
	    										tf_password.getSize().height);
	    window.add(tf_password.getJTextField());
	    
	    //Set up the submit button
	    btn_submit = new Button("LOG IN", Resources.Fonts.Registration.button, Resources.Colors.white, Resources.Colors.orange);
	    btn_submit.getJButton().setSize((int) (btn_submit.getSize().width * 1.2), btn_submit.getSize().height*2);
	    btn_submit.getJButton().setLocation( (int) (window.getWidth()/2 - btn_submit.getJButton().getSize().width/2), 
	    									(int)( (tf_password.getJTextField().getBounds().y + tf_password.getJTextField().getBounds().height) + (window.getHeight()*0.03)) );
	    window.add(btn_submit.getJButton());
	    
	    //Link to the Log-in form
	    registrationLink = new JLabel("Already have an account? ");
	    registrationLink.setFont(Resources.Fonts.Registration.link);
	    registrationLink.setForeground(Resources.Colors.white);
	    Dimension linkSize = registrationLink.getPreferredSize();
	    registrationLink.setBounds(window.getWidth()/2, (int)((btn_submit.getJButton().getBounds().y + btn_submit.getSize().height) + (window.getHeight()*0.08)), linkSize.width, linkSize.height);
	    window.add(registrationLink);
	}
	
	private void handleEvents() {
		
		//Submit button events
		btn_submit.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
				if(!db.isConnected()) {
					System.out.println("No connection with the server");
					return;
				}

				db.logIn(tf_username.getJTextField().getText(), tf_password.getJTextField().getText());
				System.out.println(User.isLoggedIn());
			}
		});
		
		btn_submit.getJButton().addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseEntered(MouseEvent e) {
				btn_submit.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }
		});
		
		
		//Link to the login form
		registrationLink.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				RegistrationForm registrationForm = new RegistrationForm();
				window.setVisible(false);
	        }

	        @Override
	        public void mouseEntered(MouseEvent e) {
	        	registrationLink.setForeground(Resources.Colors.orange);
	        	registrationLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	        	registrationLink.setForeground(Resources.Colors.white);
	        }
		});
	}
}
