package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel {
	private  JPanel panel;
	private JLabel titleLabel;
	
	public Panel(JFrame window, String title) {
		this.panel = new JPanel();
		this.panel.setSize((int)(window.getWidth()/1.5), (int)(window.getHeight()/1.5));
		this.panel.setLocation(window.getWidth()/2 - panel.getWidth()/2, (window.getHeight() - panel.getHeight()) / 4);
		this.panel.setBackground(Resources.Colors.background);
		this.panel.setLayout(null);
		this.panel.setVisible(false);
		window.add(this.panel);
		
		titleLabel = new JLabel(title);
		titleLabel.setFont(Resources.Fonts.LibraryOrganizer.panelTitles);
		titleLabel.setForeground(Resources.Colors.orange);
		titleLabel.setSize((int) titleLabel.getPreferredSize().getWidth(), (int)titleLabel.getPreferredSize().getHeight());
		titleLabel.setLocation((int)(panel.getWidth()/2 - titleLabel.getWidth()/2), 0);
		panel.add(titleLabel);
	}
	
	public void show() {
		this.panel.setVisible(true);
	}
	
	public void hide() {
		this.panel.setVisible(false);
	}
	
	public JPanel getJPanel() {
		return this.panel;
	}
	
	public JLabel getTitle() {
		return this.titleLabel;
	}
}
