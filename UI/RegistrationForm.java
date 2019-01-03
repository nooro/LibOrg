package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class RegistrationForm {
	private JFrame window;
	
	private InputField tf_username;
	private InputField tf_password;
	private InputField tf_password_repetition;
	private InputField tf_email;
	private Button btn_submit;
	JLabel loginLink;
	
	public RegistrationForm() {
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
	    
	    //Set up the password repetition input text field
	    tf_password_repetition = new InputField("password", Resources.Fonts.Registration.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_password_repetition.getJTextField().setBounds( (int)(window.getWidth()/2 - tf_password_repetition.getSize().width/2), 
												(int)( (tf_password.getJTextField().getBounds().y + tf_password.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
												tf_password_repetition.getSize().width, 
												tf_password_repetition.getSize().height);
	    window.add(tf_password_repetition.getJTextField());
	    
	    //Set up the email input text field
	    tf_email = new InputField("text", Resources.Fonts.Registration.inputFields, Resources.Colors.white, Resources.Colors.green );
	    tf_email.getJTextField().setBounds( (int)(window.getWidth() / 2 - tf_password_repetition.getSize().width / 2), 
	    									(int)( (tf_password_repetition.getJTextField().getBounds().y + tf_password.getJTextField().getHeight()) + (window.getHeight() * 0.03) ), 
	    									tf_email.getSize().width, 
	    									tf_email.getSize().height);
	    window.add(tf_email.getJTextField());

	    //Set up the submit button
	    btn_submit = new Button("REGISTER", Resources.Fonts.Registration.button, Resources.Colors.white, Resources.Colors.orange);
	    btn_submit.getJButton().setSize((int) (btn_submit.getSize().width * 1.2), btn_submit.getSize().height*2);
	    btn_submit.getJButton().setLocation( (int) (window.getWidth()/2 - btn_submit.getJButton().getSize().width/2), 
	    									(int)( (tf_email.getJTextField().getBounds().y + tf_email.getJTextField().getBounds().height) + (window.getHeight()*0.03)) );
	    window.add(btn_submit.getJButton());
	    
	    //Link to the Log-in form
	    loginLink = new JLabel("Already have an account? ");
	    loginLink.setFont(Resources.Fonts.Registration.link);
	    loginLink.setForeground(Resources.Colors.white);
	    Dimension linkSize = loginLink.getPreferredSize();
	    loginLink.setBounds(window.getWidth()/2, (int)((btn_submit.getJButton().getBounds().y + btn_submit.getSize().height) + (window.getHeight()*0.08)), linkSize.width, linkSize.height);
	    window.add(loginLink);
	}
	
	private void handleEvents() {
		
		//Submit button events
		btn_submit.getJButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		btn_submit.getJButton().addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseEntered(MouseEvent e) {
				btn_submit.getJButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }
		});
		
		
		//Link to the login form
		loginLink.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				LogInForm loginForm = new LogInForm();
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
	}
}
