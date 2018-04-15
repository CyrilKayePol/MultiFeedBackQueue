package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutPopup extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JButton back;
	private JLabel aboutTitle;
	private JTextArea aboutText;
	private JScrollPane scroll;
	
	public AboutPopup(){
		setSize(600, 400);
		setLayout(null);
		setUndecorated(true);
		getContentPane().setBackground(Color.GRAY);
		
		initComponents();
		addComponents();
	}
	
	public void initComponents(){
		aboutTitle = new JLabel("ABOUT", JLabel.CENTER);
		aboutTitle.setForeground(Color.red);
		aboutTitle.setFont(new Font("Times New Roman", Font.BOLD, 30));
		aboutTitle.setBounds(100, 5, 400, 45);
		
		aboutText = new JTextArea("\tImplementation of the MLFQ adheres with the following policies:\n\n"
				+ "(1) Migration Policy\n"
				+"   Promotion: The process is promoted if it is preempted before it could consume its allocation.\n"
				+"   Demotion: The process is demoted if it still has remaining burst time after using up all its allocation.\n"
				+"   Retention: The process is retained in a queue if no other process exists at higher level queues.\n\n"
				+"(2) Entry Policy\n"
				+"   A newly arrived process always enters the highest priority queue.\n\n"
				+"(3) Priority Policy\n"
				+"   This MLFQ follows 'Higher before Lower' policy where processes in higher priority queue\n"
				+ "   are executed first before other processes. When no process exists in higher level, only then processes\n"
				+ "   at lower queue can be allocated CPU time.\n\n");
		aboutText.setFont(new Font("Verdana", Font.PLAIN, 11));
		aboutText.setEditable(false);
		//aboutText.setBounds(10, 50, 380, 295);
		
		scroll = new JScrollPane(aboutText);
		scroll.setPreferredSize(new Dimension(580,295));
		scroll.setBounds(10, 50, 580, 295);
		
		back = new JButton("Back");
		back.setBounds(250, 360, 100, 30);
	}
	
	public void addComponents(){
		add(aboutTitle);
		add(scroll);
		add(back);
		
		back.addActionListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == back){
			dispose();
		}
	}
}
