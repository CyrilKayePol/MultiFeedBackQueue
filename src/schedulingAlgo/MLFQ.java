package schedulingAlgo;

import java.util.ArrayList;
import schedulingAlgo.Process;


public class MLFQ extends SchedulingAlgorithm{
	private Process[] p;
	private ArrayList<Process> q1,q2,q3,q4,q5;
	public static int currentBurstTime;
	private int totalBurstTime;
	private ArrayList<Process> preempted = new ArrayList<Process>();
	
	private static final int HIGHLEVEL = 1;
	private static final int LOWLEVEL = 2;
	public MLFQ(Process[] p) {
		super(p);
		this.p = p;
		
		q1 = new ArrayList<Process>();
		q2 = new ArrayList<Process>();
		q3 = new ArrayList<Process>();
		q4 = new ArrayList<Process>();
		q5 = new ArrayList<Process>();
		currentBurstTime = p[0].getArrivalTime();
		totalBurstTime = getTotalBurstTime();
		
	}
	public void execute() {
		entry();
		boolean isExecutingAProcess = false;
		while(currentBurstTime < totalBurstTime) {
			isExecutingAProcess = false;
			while(!q1.isEmpty()) {
				q1execute();
				isExecutingAProcess = true;
			}
			preempted  = new ArrayList<Process>();
			//System.out.println("q1 is empty");
			while(!q2.isEmpty()) {
				q2execute();
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			preempted  = new ArrayList<Process>();
			if(!q1.isEmpty()) {continue;}
			//System.out.println("q2 is empty");
			while(!q3.isEmpty()) {
				q3execute();
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			preempted  = new ArrayList<Process>();
			if(!q1.isEmpty()) {continue;}
			//System.out.println("q3 is empty");
			while(!q4.isEmpty()) {
				q4execute();
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			
			if(!isExecutingAProcess) {
				entry();
				currentBurstTime++;
			}
		}
	}
	
	private void entry() {
		for(int a = 0;a < p.length; a++) {
			if(p[a].getArrivalTime() == currentBurstTime) {
				q1.add(p[a]);
				System.out.println("ADDED "+p[a].getProcessID());
				
			}
			
		}
		
		
	}
	
	private void q1execute() {
		q1 = roundRobin(q1, HIGHLEVEL, 3);
		q2.addAll(preempted);
		System.out.println("======");
		
	}
	
	private void q2execute() {
		q2 = srtfORpreemptivePrio(q2, LOWLEVEL, false);
		q3.addAll(preempted);
		System.out.println("+++++++");
	}
	
	private void q3execute() {
		q3 = sjfORnonPreemptivePrio(q3, LOWLEVEL, false);
		q4.addAll(preempted);
		System.out.println("*******");
	}
	
	private void q4execute() {
		q4 = sjfORnonPreemptivePrio(q4, LOWLEVEL, false);
		q5.addAll(preempted);
		System.out.println("///////");
	}
	
	public ArrayList<Process> fcfs(ArrayList<Process> arrivalQueue, int level) {
		Process proc = null;
		while(!arrivalQueue.isEmpty()) {
			proc = arrivalQueue.get(0);
			arrivalQueue.remove(proc);

			int length = proc.getBurstTime();
			System.out.println("P"+proc.getProcessID()+ " start = "+currentBurstTime);
			for(int a = 0;a<length;a++) {
				currentBurstTime +=1;
				proc.setBurstTime(1);
				updateProcess(proc);
				entry();
				if((!q1.isEmpty()) && level == LOWLEVEL && a == length-1) {
					
					System.out.println("::P"+proc.getProcessID()+ " end = "+currentBurstTime);
					return arrivalQueue;
				}
			}
			
			System.out.println("P"+proc.getProcessID()+ " end = "+currentBurstTime);
		}
		return arrivalQueue;
		
	}
	
	public ArrayList<Process> sjfORnonPreemptivePrio(ArrayList<Process> arrivalQueue, int level, boolean isNPP) {
		Process proc = null;
		while(!arrivalQueue.isEmpty()) {
			sortArrivalQueue(proc, arrivalQueue, isNPP);

			proc = arrivalQueue.get(0);
			arrivalQueue.remove(proc);

			int length = proc.getBurstTime();
			System.out.println("P"+proc.getProcessID()+ " start = "+currentBurstTime);
			for(int a = 0;a<length;a++) {
				currentBurstTime +=1;
				proc.setBurstTime(1);
				updateProcess(proc);
				entry();
				if((!q1.isEmpty()) && level == LOWLEVEL && a == length-1) {
					
					System.out.println("::P"+proc.getProcessID()+ " end = "+currentBurstTime);
					return arrivalQueue;
				}
			}
			
			System.out.println("P"+proc.getProcessID()+ " end = "+currentBurstTime);
		}
		return arrivalQueue;
		
	}
	public ArrayList<Process> srtfORpreemptivePrio(ArrayList<Process> arrivalQueue, int level, boolean isPP) {
		boolean continueExecution = true;
		Process proc = null;
		sortArrivalQueue(proc, arrivalQueue, isPP);
		while(!arrivalQueue.isEmpty()) {
			continueExecution = true;
			proc = arrivalQueue.get(0);
			
			int length = proc.getBurstTime();
			System.out.println("P"+proc.getProcessID()+ " start = "+currentBurstTime);
			for(int a = 0;a<length;a++) {
				if(continueExecution) {
					currentBurstTime +=1;
					proc.setBurstTime(1);
					updateProcess(proc);
					entry();
					if((!q1.isEmpty()) && level == LOWLEVEL) {
						System.out.println("::P"+proc.getProcessID()+ " end = "+currentBurstTime);
						proc.setPreemtedCount(1);
						updateProcess(proc);
						q1.add(proc);
						arrivalQueue.remove(proc);
						return arrivalQueue;
					}
					
					continueExecution = sortArrivalQueue(proc, arrivalQueue, isPP);
					
					if(proc.getBurstTime() == 0) {
						arrivalQueue.remove(proc);
					}
				}else {
					proc.setPreemtedCount(1);
					updateProcess(proc);
					System.out.println("[preempted "+proc.getProcessID()+" at time "+currentBurstTime+" remaining = "+proc.getBurstTime());
					break;
				}
			}
			
			System.out.println("P"+proc.getProcessID()+ " end = "+currentBurstTime);
		}
		return arrivalQueue;
	}
	

	public boolean sortArrivalQueue(Process current, ArrayList<Process> arrivalQueue, boolean prioritySched) {
		Process temp;
		for(int b = 0;b<arrivalQueue.size();b++) {
			for(int a = b+1;a<arrivalQueue.size();a++) {
				int bt1,bt2;
				if(prioritySched) {
					bt1 = arrivalQueue.get(b).getPriorityNum();
					bt2 = arrivalQueue.get(a).getPriorityNum();
				}else {
					bt1 = arrivalQueue.get(b).getBurstTime();
					bt2 = arrivalQueue.get(a).getBurstTime();
				}
				
				if( bt1>bt2 ) {
					temp = arrivalQueue.get(b);
					arrivalQueue.set(b, arrivalQueue.get(a));
					arrivalQueue.set(a, temp);
				}else if( bt1 == bt2) {
					int at1 = arrivalQueue.get(b).getArrivalTime();
					int at2 = arrivalQueue.get(a).getArrivalTime();
					if(at1 > at2) {
						temp = arrivalQueue.get(b);
						arrivalQueue.set(b, arrivalQueue.get(a));
						arrivalQueue.set(a, temp);
					}else if(at1 == at2) {
						int pid1 = arrivalQueue.get(b).getProcessID();
						int pid2 = arrivalQueue.get(a).getProcessID();
						if(pid1 > pid2) {
							temp = arrivalQueue.get(b);
							arrivalQueue.set(b, arrivalQueue.get(a));
							arrivalQueue.set(a, temp);
						}
					}
				}
			}
		}
		
		if(current == arrivalQueue.get(0)) {
			return true;
		}
		return false;
		
	}
	
	
	public void updateProcess(Process current) {
		for(int a = 0;a<p.length;a++) {
			if(current.getProcessID() == p[a].getProcessID()) {
				p[a] = current;
				break;
			}
		}
	}
	
	
	public ArrayList<Process> roundRobin(ArrayList<Process> arrivalQueue, int level, int timeQuantum){
		Process proc = null;
		while(!arrivalQueue.isEmpty()){
			proc = arrivalQueue.get(0);
			
			for(int i = 0; i < timeQuantum; i++){
				currentBurstTime += 1;
				
				if(proc.getBurstTime() > 0){
					proc.setBurstTime(1);
					updateProcess(proc);
					entry();
					
					if(i == timeQuantum -1 && proc.getBurstTime() > 0){
						preempted.add(proc);
					}
				}
				else
				{
					currentBurstTime -= 1;
					break;
				}
				
				if((!q1.isEmpty()) && level == LOWLEVEL) {
					System.out.println("::P"+proc.getProcessID()+ " end = "+currentBurstTime + " burst "+proc.getBurstTime());
					q1.add(proc);
					proc.setPreemtedCount(1);
					arrivalQueue.remove(proc);
					
					return arrivalQueue;
				}
			}
			System.out.println("Proc "+ proc.getProcessID()+"-burst time-"+ proc.getBurstTime() + "-at time "+currentBurstTime);
			arrivalQueue.remove(proc);
		}
		System.out.println("ArrivalQueue Size"+ arrivalQueue.size());
		System.out.println("Preempted Size"+ preempted.size());
		return arrivalQueue;
	}
	public static void main(String[] args) {
		Process p[] = new Process[10];
		int arrival[] = {1,15,16,14,12,12,0,17,17,4};
		int burst[] =   {4,8,2,3,2,6,7,2,12,9};
		for(int a = 0;a<p.length;a++) {
			p[a] = new Process(a, arrival[a], burst[a], 0);
		}
		MLFQ mlfq = new MLFQ(p);
		mlfq.execute();
	}
}