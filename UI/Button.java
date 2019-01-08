package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class Button {
	
	private JButton button;
	Dimension size;
	
	public Button(String text, Font font, Color foreground, Color background) {
		this.button = new JButton(text);
		
		this.button.setFont(font);
		this.button.setBorder(null);
		
		this.button.setBackground(background);
		this.button.setForeground(foreground);
		
		size = this.button.getPreferredSize();
	}
	
	public Dimension getSize() {
		return this.button.getBounds().getSize();
	}
	
	public JButton getJButton() {
		return this.button;
	}
}
