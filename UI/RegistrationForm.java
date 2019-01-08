package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;

import dbConnection.*;
import liborg.User;

public class RegistrationForm {
	private JFrame window;
	
	private InputField tf_username;
	private InputField tf_password;
	private InputField tf_password_repetition;
	private InputField tf_email;
	private Button btn_submit;
	private JLabel loginLink;
	private JLabel errorMessage;
	
	private DBConnector db;
	
	public RegistrationForm(DBConnector dbConnector) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setupTheWindow();
				Resources.Fonts.Forms.setUp(window);
				setupTheBody();
				setUpEffects();
				db = dbConnector;
				handleEvents();
		}});	
	}
	
	private void setupTheWindow() {
		window = new JFrame("Registration form");
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
		JLabel title = new JLabel("Registration");
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
	    
	    //Set up the password repetition input text field
	    tf_password_repetition = new InputField("password", Resources.Fonts.Forms.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_password_repetition.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_password_repetition.getSize().width/2), 
												(int)( (tf_password.getJTextField().getBounds().y + tf_password.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
												tf_password_repetition.getSize().width, 
												tf_password_repetition.getSize().height);
	    tf_password_repetition.getJTextField().setText("Password");
	    window.add(tf_password_repetition.getJTextField());
	    
	    //Set up the email input text field
	    tf_email = new InputField("text", Resources.Fonts.Forms.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_email.getJTextField().setBounds( (int)(window.getWidth() / 2 - tf_password_repetition.getSize().width / 2), 
	    									(int)( (tf_password_repetition.getJTextField().getBounds().y + tf_password.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
	    									tf_email.getSize().width, 
	    									tf_email.getSize().height);
	    tf_email.getJTextField().setText("E-mail");
	    window.add(tf_email.getJTextField());

	    //Set up the submit button
	    btn_submit = new Button("REGISTER", Resources.Fonts.Forms.button, Resources.Colors.white, Resources.Colors.orange);
	    btn_submit.getJButton().setSize((int) (btn_submit.getJButton().getPreferredSize().width * 1.2), btn_submit.getJButton().getPreferredSize().height*2);
	    btn_submit.getJButton().setLocation( (int) (window.getWidth()/2 - btn_submit.getJButton().getSize().width/2), 
	    									(int)( (tf_email.getJTextField().getBounds().y + tf_email.getJTextField().getBounds().height) + (window.getHeight()*0.03)) );
	    window.add(btn_submit.getJButton());
	    
	    //Link to the Log-in form
	    loginLink = new JLabel("Already have an account? ");
	    loginLink.setFont(Resources.Fonts.Forms.link);
	    loginLink.setForeground(Resources.Colors.white);
	    Dimension linkSize = loginLink.getPreferredSize();
	    loginLink.setBounds(window.getWidth()/2, (int)((btn_submit.getJButton().getBounds().y + btn_submit.getSize().height) + (window.getHeight()*0.08)), linkSize.width, linkSize.height);
	    window.add(loginLink);
	    
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
		loginLink.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				LogInForm loginForm = new LogInForm(db);
				window.setVisible(false);
	        }

	        @Override
	        public void mouseEntered(MouseEvent e) {
	            loginLink.setForeground(Resources.Colors.orange);
	            loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	        	loginLink.setForeground(Resources.Colors.white);
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
		
		//Password repetition placeholder
		tf_password_repetition.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_password_repetition.getJTextField().getText().equals("Password")) {
					tf_password_repetition.getJTextField().setText("");
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_password_repetition.getJTextField().getText().isEmpty()) {
					tf_password_repetition.getJTextField().setText("Password");
		        }
			}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		//E-mail placeholder
		tf_email.getJTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tf_email.getJTextField().getText().equals("E-mail")) {
					tf_email.getJTextField().setText("");
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tf_email.getJTextField().getText().isEmpty()) {
					tf_email.getJTextField().setText("E-mail");
		        }
			}
			@Override
			public void keyTyped(KeyEvent e) {}
		});		
	}
	
	private void handleEvents() {
		
		//Submit button on click
		btn_submit.getJButton().addActionListener(new ActionListener() {
			
			Pattern emailValidationPattern = Pattern.compile("[a-zA-Z0-9_.-]+@[a-zA-Z0-9_-]+.[a-z]+");
			Matcher emailValidationMatcher;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				emailValidationMatcher = emailValidationPattern.matcher(tf_email.getJTextField().getText());
				
				//Check if there are empty input fields 
				if(tf_username.getJTextField().getText().equals("Username") || tf_password.getJTextField().getText().equals("Password") ||
					tf_password_repetition.getJTextField().getText().equals("Password") || tf_email.getJTextField().getText().equals("E-mail")
					) {
					throwErrorMessage("Fill all input boxes ");
					return;
				}
				
				//Check if the passwords are the same
				else if(!tf_password.getJTextField().getText().equals(tf_password_repetition.getJTextField().getText())) {
					throwErrorMessage("Repeat the password correctly ");
					return;
				}
				
				//Check if the e-mail is valid
				else if(!emailValidationMatcher.matches()) {
					throwErrorMessage("Enter valid e-mail address ");
					return;
				}
				
				DBConnector.Result result = UsersOperator.register(db, tf_username.getJTextField().getText(), tf_password.getJTextField().getText(), tf_email.getJTextField().getText());
				
				if(result == DBConnector.Result.NO_SERVER_CONNECTION) {
					throwErrorMessage("No server connection ");
				}
				else if(result == DBConnector.Result.REGISTER_USERNAME_TAKEN) {
					throwErrorMessage("This username already exists ");
				}
				else if(result == DBConnector.Result.SQL_ERROR) {
					throwErrorMessage("SQL error ");
				}
				else if(result == DBConnector.Result.REGISTERED) {
					db.closeConnection();
					LibraryOrganizer liborg = new LibraryOrganizer();
					window.setVisible(false);
				}
			}
		});

		//Link to the login form on click
		loginLink.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				LogInForm loginForm = new LogInForm(db);
				window.setVisible(false);
	        }
		});
	}
}
