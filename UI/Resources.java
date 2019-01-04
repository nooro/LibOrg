package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

public class Resources {
	
	public static class Fonts {
		
		public static class Forms {
			public static Font title;
			public static Font inputFields;
			public static Font button;
			public static Font link; 
			
			public static void setUp(JFrame window) {
				if( title == null ) {
					title = new Font("Tahoma", Font.BOLD, window.getWidth()/10);
					inputFields = new Font("Tahoma", Font.ITALIC, window.getWidth()/20);
					button = new Font("Tahoma", Font.BOLD, window.getWidth()/25);
					link = new Font("Tahoma", Font.ITALIC, window.getWidth()/30);
				}
			}
		}
		
		public static class LibraryOrganizer {
			public static void setUp(JFrame window) {
				
			}
		}
		
	}
	
	public static class Colors {
		public static Color red = new Color(194, 24, 7);
		public static Color orange = new Color(253, 106, 2);
		public static Color white = new Color(255, 255, 255);
		public static Color green = new Color(41, 171, 135);
		public static Color background = new Color(44, 62, 80);
	}
}
