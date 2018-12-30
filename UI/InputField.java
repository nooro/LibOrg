package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InputField {
	
	private JTextField textField;
	Dimension size;
	
	public InputField(String type, Font font, Color foreground, Color background) {
		if(type.equals("text"))
			this.textField = new JTextField(15);
		else if(type.equals("password"))
			this.textField = new JPasswordField(15);
		
		this.textField.setFont(font);
		this.textField.setBorder(null);
		
		this.textField.setBackground(background);
		this.textField.setForeground(foreground);
		
		size = this.textField.getPreferredSize();
	}
	
	public Dimension getSize() {
		return this.size;
	}
	
	public JTextField getJTextField() {
		return this.textField;
	}
}
