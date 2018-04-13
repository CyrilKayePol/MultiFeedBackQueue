package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class AboutPopup extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JButton back;
	private JLabel aboutTitle;
	private JTextArea aboutText;
	
	public AboutPopup(){
		setSize(400, 400);
		setLayout(null);
		setUndecorated(true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		initComponents();
		addComponents();
	}
	
	public void initComponents(){
		aboutTitle = new JLabel("ABOUT", JLabel.CENTER);
		aboutTitle.setForeground(Color.red);
		aboutTitle.setFont(new Font("Times New Roman", Font.BOLD, 30));
		aboutTitle.setBounds(0, 5, 400, 45);
		
		aboutText = new JTextArea("Hallo");
		aboutText.setBounds(10, 50, 380, 295);
		
		back = new JButton("Back");
		back.setBounds(150, 360, 100, 30);
	}
	
	public void addComponents(){
		add(aboutTitle);
		add(aboutText);
		add(back);
		
		back.addActionListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == back){
			dispose();
		}
	}
}
