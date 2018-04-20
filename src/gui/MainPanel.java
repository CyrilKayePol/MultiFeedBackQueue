package gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import schedulingAlgo.MLFQ2;
import schedulingAlgo.Process;

public class MainPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JMenuItem aboutMenu;
	private JMenuBar menuBar;
	private JTextField numJobsfield;
	public static JTextField averageWaitfield, averageTRfield, averageResponsefield;
	public static double avgWaitingTime, avgTurnAroundTime, avgResponseTime;
	private JComboBox<Integer> numQLevelfield;
	private JComboBox<String> algorithmList;
	private JButton populate;
	public static JButton start;
	public static JPanel dataPanel;
	private Object[][] obj;
	private Object[][] obj1;
	private Process[] processes;
	private int numberJobs, numberQueue = 3;
	private MLFQ2 mlfq;
	
	private JPanel  jobPoolPanel, readyQueuePanel, 
		averagePanel, ganttChartPanel, queuePanel;
	private JLabel data, jobPool, readyQueue, average, ganttChart, 
		numOfJobs, numQLevels, averageWait, averageTurnAround, 
		averageResponse, arrow;
	private String[] schedulingAlgorithms = {"FCFS", "SJF", "SRTF", "Non-Preemptive Priority",
			"Preemptive Priority", "Round Robin"};
	private Integer[] numOfLevel = {1, 2, 3, 4, 5};
	private String[] columnNames = {"Queue ID", "Algorithm", "Quantum", "Priority"};
	private String[] jobColumnNames = {"Job ID", "Arrival Time", "Burst Time", "Priority"};
	
	private JTable queueTable, jobTable;
	private TheTableModel tableModel, jobTableModel;
	private JScrollPane queuePane, jobPane;
	private GanttChart threadedGanttChart;
	private JScrollPane ganttChartPane, readyQueuePane;
	
	public MainPanel(){
		setSize(1366, 730);
		setLayout(null);
		
		initComponents();
		addComponents();
	}
	
	public void initComponents(){
		menuBar = new JMenuBar();
		aboutMenu = new JMenuItem("About");
		
		// JPanels
		dataPanel = new JPanel();
		dataPanel.setBounds(3, 6, 670, 250);
		dataPanel.setBackground(Color.LIGHT_GRAY);
		dataPanel.setLayout(null);
		
		jobPoolPanel = new JPanel();
		jobPoolPanel.setBounds(675, 6, 670, 250);
		jobPoolPanel.setBackground(Color.LIGHT_GRAY);
		jobPoolPanel.setLayout(null);
		
		readyQueuePanel = new JPanel();
		readyQueuePanel.setBounds(3, 260,920, 204);
		readyQueuePanel.setBackground(Color.LIGHT_GRAY);
		readyQueuePanel.setLayout(null);
		
		averagePanel = new JPanel();
		averagePanel.setBounds(925, 260, 420, 204);
		averagePanel.setBackground(Color.LIGHT_GRAY);
		averagePanel.setLayout(null);
		
		ganttChartPanel = new JPanel();
		ganttChartPanel.setBounds(3, 468, 1340, 196);
		ganttChartPanel.setBackground(Color.LIGHT_GRAY);
		ganttChartPanel.setLayout(null);
		
		queuePanel = new JPanel();
		queuePanel.setBounds(15, 102, 640, 140);
		queuePanel.setLayout(null);
		
		//JLabels
		data = new JLabel("DATA", JLabel.CENTER);
		data.setBounds(-10, 0, 670, 35);
		setFontStyle(data);
		
		jobPool = new JLabel("JOB POOL", JLabel.CENTER);
		jobPool.setBounds(0, 0, 670, 35);
		setFontStyle(jobPool);
		
		readyQueue = new JLabel("READY QUEUE", JLabel.CENTER);
		readyQueue.setBounds(-20, 0, 970, 35);
		setFontStyle(readyQueue);
		
		average = new JLabel("AVERAGE TIME", JLabel.CENTER);
		average.setBounds(-20, 0, 470, 35);
		setFontStyle(average);
		
		ganttChart = new JLabel("GANTT CHART", JLabel.CENTER);
		ganttChart.setBounds(-20, 0, 1360, 35);
		setFontStyle(ganttChart);
		
		numOfJobs = new JLabel("Number of Jobs");
		numOfJobs.setBounds(15, 45, 200, 20);
		numOfJobs.setFont(new Font("Arial", Font.BOLD, 15));
		
		numQLevels = new JLabel("Number of Queue Levels");
		numQLevels.setBounds(15, 68, 200, 20);
		numQLevels.setFont(new Font("Arial", Font.BOLD, 15));
		
		numJobsfield = new JTextField();
		numJobsfield.setBounds(220, 45, 200, 20);
		
		numQLevelfield = new JComboBox<Integer> (numOfLevel);
		numQLevelfield.setBounds(220, 68, 200, 20);
		numQLevelfield.setSelectedItem(numOfLevel[2]);
		numQLevelfield.addActionListener(this);
		
		populate = new JButton("Populate Job Pool");
		populate.setBounds(445, 52, 200, 30);
		populate.addActionListener(this);
		
		initQueuePanelComponents();
		
		initJobPoolPanelComponents();
		start.setEnabled(false);
		averageWait = new JLabel("Average Waiting Time");
		averageWait.setBounds(15, 60, 200, 20);
		averageWait.setFont(new Font("Arial", Font.BOLD, 15));
		
		averageTurnAround = new JLabel("Average TurnAround Time");
		averageTurnAround.setBounds(15, 100, 200, 20);
		averageTurnAround.setFont(new Font("Arial", Font.BOLD, 15));
		
		averageResponse = new JLabel("Average Response Time");
		averageResponse.setBounds(15, 140, 200, 20);
		averageResponse.setFont(new Font("Arial", Font.BOLD, 15));
		
		averageWaitfield = new JTextField();
		averageWaitfield.setBounds(225, 55, 179, 25);
		averageWaitfield.setEditable(false);
		
		averageTRfield = new JTextField();
		averageTRfield.setBounds(225, 95, 179, 25);
		averageTRfield.setEditable(false);
		
		averageResponsefield = new JTextField();
		averageResponsefield.setBounds(225, 135, 179, 25);
		averageResponsefield.setEditable(false);
		
		arrow = new JLabel("<--");
		//arrow.setBounds(25, 100, 40,20);
		setFontStyle(arrow);
		
		threadedGanttChart = new GanttChart();
		ganttChartPane = new JScrollPane(threadedGanttChart);
		ganttChartPane.setBounds(15, 55, 1310, 100);
	}
	
	public void initQueuePanelComponents(){
		obj = new Object[numberQueue][4];
		algorithmList = new JComboBox<String>(schedulingAlgorithms);
		for(int i = 0; i < numberQueue; i++){
			for(int j = 0; j < 4; j++){
				if(j == 0){
					obj[i][0] = i+1;
				}
				else if(j == 3){
					obj[i][3] = i+1;
				}
			}
		}
		tableModel = new TheTableModel(obj,columnNames);
		tableModel.tableUsing("queueTable");
		
		queueTable = new JTable(tableModel);
		queueTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(algorithmList));
		queuePane = new JScrollPane(queueTable);
		queuePane.setBounds(0, 0, queuePanel.getWidth(), queuePanel.getHeight());
	}
	public void initJobPoolPanelComponents(){
		jobTableModel = new TheTableModel(obj1,jobColumnNames);
		jobTableModel.tableUsing("jobTable");
		
		jobTable = new JTable(jobTableModel);

		jobPane = new JScrollPane(jobTable);
		jobPane.setBounds(15, 45, 640, 148);
		
		start = new JButton("Start Gantt Chart Simulation");
		start.setBounds(92, 201, 490, 40);
		start.addActionListener(this);
	}
	
	public void addJobPoolPanelComponents(){
		jobPoolPanel.add(jobPool);
		jobPoolPanel.add(jobPane);
		jobPoolPanel.add(start);
	}
	public void addComponents(){
		add(dataPanel);
		add(jobPoolPanel);
		add(readyQueuePanel);
		add(averagePanel);
		add(ganttChartPanel);
		
		dataPanel.add(data);
		dataPanel.add(numOfJobs);
		dataPanel.add(numQLevels);
		dataPanel.add(numJobsfield);
		dataPanel.add(numQLevelfield);
		dataPanel.add(populate);
		dataPanel.add(queuePanel);
		
		queuePanel.add(queuePane);
		
		addJobPoolPanelComponents();
		
		averagePanel.add(averageWait);
		averagePanel.add(averageTurnAround);
		averagePanel.add(averageResponse);
		averagePanel.add(averageWaitfield);
		averagePanel.add(averageTRfield);
		averagePanel.add(averageResponsefield);
		
		readyQueuePanel.add(readyQueue);
		readyQueuePanel.add(arrow);
		
		averagePanel.add(average);
		
		addGanttChartPanelComponents();
		
		menuBar.add(aboutMenu);
		
		aboutMenu.addActionListener(this);
	}
	
	public JMenuBar getMenuBar(){
		return menuBar;
	}
	
	public void addGanttChartPanelComponents(){
		ganttChartPanel.add(ganttChart);
		ganttChartPanel.add(ganttChartPane);
	}
	public void setFontStyle(JLabel label){
		label.setForeground(Color.red);
		label.setFont(new Font("Arial", Font.BOLD, 25));
	}

	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if(obj == aboutMenu){
			AboutPopup about;
			about = new AboutPopup();
			about.setAlwaysOnTop(true);
			about.setLocationRelativeTo(null);
			about.setVisible(true);
		
		}
		else if (obj == populate){
			String numJob = numJobsfield.getText();
			numberQueue = (Integer) numQLevelfield.getSelectedItem();
			
			try{
				numberJobs = Integer.parseInt(numJob);
				jobPoolPanel.removeAll();
				
				obj1 = new Object[numberJobs][4];
				for(int i = 0; i < numberJobs; i++){
					obj1[i][0] = i+1;
				}
				
				initJobPoolPanelComponents();
				addJobPoolPanelComponents();
				
				jobPoolPanel.repaint();
				jobPoolPanel.revalidate();
			}
			catch(Exception e){
				System.err.println(e.getMessage());
			}
		}
		else if (obj == start){
			start.setEnabled(false);
			MainPanel.averageWaitfield.setText("");
			MainPanel.averageTRfield.setText("");
			MainPanel.averageResponsefield.setText("");
			
			startAction();
		}
		else if(obj == numQLevelfield){
			numberQueue = (Integer)numQLevelfield.getSelectedItem();
			
			queuePanel.removeAll();
			initQueuePanelComponents();
			queuePanel.add(queuePane);
			
			queuePanel.repaint();
			queuePanel.revalidate();
		}
	}
	
	public void startAction(){
		try{
			if (jobTable.isEditing())
			    jobTable.getCellEditor().stopCellEditing();
			
			if (queueTable.isEditing())
			    queueTable.getCellEditor().stopCellEditing();
			String[] algo = new String[numberQueue];
			int[] quantum = new int[numberQueue];
			
		    while(true){
		    	try{
					processes = new Process[numberJobs];
					for(int i = 0; i < numberJobs; i++){
						processes[i] = new Process((Integer) jobTable.getModel().getValueAt(i, 0), (Integer) jobTable.getModel().getValueAt(i, 1),
								(Integer) jobTable.getModel().getValueAt(i, 2), (Integer) jobTable.getModel().getValueAt(i, 3) );
					}
					
					for(int i = 0; i < numberQueue; i++){
						for(int j = 0; j < 4; j++){
							if(j == 1){
								algo[i] = (String) queueTable.getModel().getValueAt(i,j);
							}
							else if(j == 2){
								if(algo[i] == "Round Robin"){
									quantum[i] = (Integer) queueTable.getModel().getValueAt(i,j);
								}
								else{
									quantum[i] = 0;
								}
							}
						}
					}
					boolean toContinue = false;
					for(int i = 0; i < numberQueue; i++){
						if(algo[i] == null){
							toContinue = true;
							break;
						}
					}
					
					if(toContinue){
						Integer.parseInt("Hi");
					}
					else{
						for(int i = 0; i < numberQueue; i++){
							if(quantum[i] == 0 && algo[i] == "Round Robin"){
								Integer.parseInt("Hi");
								break;
							}
						}
					}
					
					break;
				}
				catch(Exception e){
					System.out.println("At inner try: Wrong input");
					start.setEnabled(true);
					throw e;
				}
		    }
		    setEnabledAll(dataPanel, false);
			mlfq = new MLFQ2(processes, algo, quantum);
			
			ganttChartPanel.removeAll();
			threadedGanttChart = new GanttChart(mlfq.execute());
			mlfq.computeTurnAroundTime();
			mlfq.computeResponseTime();
			
			double w = 0, tr =0, rt = 0;
			for(int i = 0; i < processes.length; i++){
				tr += processes[i].getTurnAroundTime();
				w+= processes[i].getWaitingTime();
				rt += processes[i].getResponseTime();
			}
			
			avgTurnAroundTime = tr / processes.length;
			avgWaitingTime = w/processes.length;
			avgResponseTime = rt/processes.length;
			
			
			System.out.println("Turn: "+ avgTurnAroundTime + "Wait: "+avgWaitingTime + "Response: " +avgResponseTime);
			ganttChartPane = new JScrollPane(threadedGanttChart);
			ganttChartPane.setBounds(15, 55, 1310, 100);
			addGanttChartPanelComponents();
			
			threadedGanttChart.repaint();
			threadedGanttChart.revalidate();
			
			ganttChartPanel.repaint();
			ganttChartPanel.revalidate();
			
			Thread thread = new Thread(threadedGanttChart);
			thread.start();
		
			readyQueuePanel.removeAll();
			readyQueuePanel.add(readyQueue);
			readyQueuePane = new JScrollPane(threadedGanttChart.getThreadedReadyQueue());
			readyQueuePane.setBounds(15, 55, 895, 100);
			readyQueuePanel.add(readyQueuePane);
			
			readyQueuePanel.repaint();
			readyQueuePanel.revalidate();
		}
		catch(Exception e){
			System.out.print("At MainPanel");
			System.err.println(e.getMessage());
		}
	}
	public static void setEnabledAll(Container container, boolean enabled) {
		   Component[] components = container.getComponents();
		   if (components.length > 0) {
		      for (Component component : components) {
		         component.setEnabled(enabled);
		         if (component instanceof Container) { // has to be a container to contain components
		            setEnabledAll((Container)component, enabled); // the recursive call
		         }
		      }
		   }
		}
}
