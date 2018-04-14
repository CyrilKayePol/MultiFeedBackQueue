package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private MainPanel mainPanel;
	private Dimension screenSize;
	
	public MainFrame(){
		init();
		
		add(mainPanel);
		
		setSize((int) screenSize.getWidth(),(int) screenSize.getHeight() - 35);
		setJMenuBar(mainPanel.getMenuBar());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void init(){
		mainPanel = new MainPanel();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}
}
