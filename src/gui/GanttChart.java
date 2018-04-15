package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GanttChart extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private JLabel[] processLabels, jobLabels;
	private JLabel noMoreP;
	private JPanel threadedReadyQueue;
	private boolean isRunning = true;
	
	public GanttChart(){
		setLayout(null);
	}
	
	public GanttChart(ArrayList<String> processQ){
		setLayout(null);
		
		init(processQ);
		setPreferredSize(new Dimension(processQ.size()* 25, 100));
	}
	
	public void init(ArrayList<String> processQ){
		processLabels = new JLabel[processQ.size()];
		jobLabels = new JLabel[processQ.size()];
		int startX = 5;
		int startX1 = 5;
		
		for (int i = 0; i < processQ.size(); i++){
			processLabels[i] = new JLabel(processQ.get(i), JLabel.CENTER);
			processLabels[i].setBackground(Color.pink);
			processLabels[i].setOpaque(true);
			processLabels[i].setBounds(startX, 5, 20, 86);
			startX += 25;
		}
		
		threadedReadyQueue = new JPanel();
		threadedReadyQueue.setLayout(null);
		threadedReadyQueue.setPreferredSize(new Dimension(processQ.size()* 25, 100));
		int id = -1;
		for(int i = 0; i < processQ.size(); i++){
			
			int comp = Integer.parseInt(""+processQ.get(i).charAt(1));
			if(id != comp) {
				startX1 +=5;
				id = comp;
			}
			
			if(i != processQ.size()-1) {
				int comp1 = Integer.parseInt(""+processQ.get(i+1).charAt(1));
				if(id != comp1) {
					jobLabels[i] = new JLabel(processQ.get(i), JLabel.CENTER);
				}else {
					jobLabels[i] = new JLabel();
				}
			}else {
				jobLabels[i] = new JLabel(processQ.get(i), JLabel.CENTER);
			}
			
			jobLabels[i].setBackground(Color.cyan);
			jobLabels[i].setOpaque(true);
			jobLabels[i].setBounds(startX1, 5, 20, 65);
			threadedReadyQueue.add(jobLabels[i]);
			startX1 += 20;
		}
	}
	
	public JPanel getThreadedReadyQueue(){
		return threadedReadyQueue;
	}
	public void run(){
		for(int i =0 ; i < processLabels.length; i++){
			if(isRunning){
				System.out.println(processLabels[i].getText());
				add(processLabels[i]);
				jobLabels[i].setVisible(false);
				
				repaint();
				revalidate();
					
				try{
					Thread.sleep(600);
				}catch(Exception e){
					System.out.println("At run");
					System.err.println(e.getMessage());
				}
				if(i == processLabels.length-1){
					threadedReadyQueue.removeAll();
					noMoreP = new JLabel("No More Process in the Ready Queue!");
					noMoreP.setBounds(15, 15, 400, 40);
					noMoreP.setForeground(Color.RED);
					noMoreP.setFont(new Font("Arial", Font.BOLD, 20));
					threadedReadyQueue.add(noMoreP);
					threadedReadyQueue.repaint();
					MainPanel.start.setEnabled(true);
					MainPanel.setEnabledAll(MainPanel.dataPanel, true);
				}
			}
			else{
				break;
			}
		}
	}
	public boolean getIsRunning(){
		return isRunning;
	}
	public void setIsRunningFalse(){
		isRunning = false;
	}
}
