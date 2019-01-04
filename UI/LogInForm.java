package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private JLabel registrationLink;
	private JLabel errorMessage;
	
	public LogInForm() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupTheWindow();	
				Resources.Fonts.Forms.setUp(window);
				setupTheBody();
				setUpEffects();
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
		title.setFont(Resources.Fonts.Forms.title);
		title.setForeground(Resources.Colors.orange);
	    Dimension titleSize = title.getPreferredSize();
	    title.setBounds(window.getWidth()/2-titleSize.width/2, (int)(window.getHeight()*0.09), titleSize.width, titleSize.height);
	    window.add(title);
	    
	    //Set up the username input text field
	    tf_username = new InputField("text", Resources.Fonts.Forms.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_username.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_username.getSize().width/2), 
	    										(int)( (title.getBounds().y + title.getHeight()) + (window.getHeight() * 0.1) ), 
	    										tf_username.getSize().width, 
	    										tf_username.getSize().height);
	    tf_username.getJTextField().setText("Username");
	    window.add(tf_username.getJTextField());
	   
	    //Set up the password input text field
	    tf_password = new InputField("password", Resources.Fonts.Forms.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_password.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_password.getSize().width/2), 
	    										(int)( (tf_username.getJTextField().getBounds().y + tf_username.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
	    										tf_password.getSize().width, 
	    										tf_password.getSize().height);
	    tf_password.getJTextField().setText("Password");
	    window.add(tf_password.getJTextField());
	    
	    //Set up the submit button
	    btn_submit = new Button("LOG IN", Resources.Fonts.Forms.button, Resources.Colors.white, Resources.Colors.orange);
	    btn_submit.getJButton().setSize((int) (btn_submit.getSize().width * 1.2), btn_submit.getSize().height*2);
	    btn_submit.getJButton().setLocation( (int) (window.getWidth()/2 - btn_submit.getJButton().getSize().width/2), 
	    									(int)( (tf_password.getJTextField().getBounds().y + tf_password.getJTextField().getBounds().height) + (window.getHeight()*0.03)) );
	    window.add(btn_submit.getJButton());
	    
	    //Link to the Log-in form
	    registrationLink = new JLabel("Already have an account? ");
	    registrationLink.setFont(Resources.Fonts.Forms.link);
	    registrationLink.setForeground(Resources.Colors.white);
	    Dimension linkSize = registrationLink.getPreferredSize();
	    registrationLink.setBounds(window.getWidth()/2, (int)((btn_submit.getJButton().getBounds().y + btn_submit.getSize().height) + (window.getHeight()*0.08)), linkSize.width, linkSize.height);
	    window.add(registrationLink);
	    
	    //Error message
	    errorMessage = new JLabel("");
	    errorMessage.setFont(Resources.Fonts.Forms.link);
	    errorMessage.setForeground(Resources.Colors.red);
	    errorMessage.setVisible(false);
	    window.add(errorMessage);
	}
	
	private void throwErrorMessage(String message) {
		errorMessage.setText(message);
		Dimension errorMessageSize = errorMessage.getPreferredSize();
	    errorMessage.setBounds(window.getWidth()/2 - (int)errorMessageSize.getWidth()/2, 
	    						(int)(window.getHeight() - (int)errorMessageSize.getHeight() - window.getHeight()*0.1), 
	    						errorMessageSize.width, errorMessageSize.height);
	    errorMessage.setVisible(true);
	}
	
	private void setUpEffects() {
		
		//Submit button hover effects
		btn_submit.getJButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_submit.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		
		//Link hover effects
		registrationLink.addMouseListener(new MouseAdapter() {
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
		
		//Username placeholder
		tf_username.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_username.getJTextField().getText().equals("Username")) {
					tf_username.getJTextField().setText("");
			    }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_username.getJTextField().getText().isEmpty()) {
					tf_username.getJTextField().setText("Username");
		        }
			}		
			@Override
			public void keyTyped(KeyEvent e) {}
						
		});

		//Password placeholder
		tf_password.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_password.getJTextField().getText().equals("Password")) {
					tf_password.getJTextField().setText("");
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_password.getJTextField().getText().isEmpty()) {
					tf_password.getJTextField().setText("Password");
		        }
			}
			@Override
			public void keyTyped(KeyEvent e) {}
		});		
	}
	
	private void handleEvents() {
		
		//Submit button on click
		btn_submit.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
				DBConnector.LogInResult result = db.logIn(tf_username.getJTextField().getText(), tf_password.getJTextField().getText());
				
				if( tf_username.getJTextField().getText().equals("") || tf_password.getJTextField().getText().equals("") ) {
					throwErrorMessage("Enter username and password ");
				}
				else if( result == DBConnector.LogInResult.NO_SERVER_CONNECTION ) {
					throwErrorMessage("No connection with the server ");
				}
				else if( result == DBConnector.LogInResult.INVALID_DATA ) {
					throwErrorMessage("Invalid username-password combination ");
				}
				else if( result == DBConnector.LogInResult.LOGGED_IN ) {
					db.closeConnection();
					LibraryOrganizer liborg = new LibraryOrganizer();
					window.setVisible(false);
				}
			}
		});
		
		//Link to the login form on click
		registrationLink.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				RegistrationForm registrationForm = new RegistrationForm();
				window.setVisible(false);
	        }
		});
	}
}
