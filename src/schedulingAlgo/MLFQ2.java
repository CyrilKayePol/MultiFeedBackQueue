package schedulingAlgo;

import java.util.ArrayList;
import schedulingAlgo.Process;


public class MLFQ2 extends SchedulingAlgorithm{
	private Process[] p;
	private ArrayList<Process> q1,q2,q3,q4,q5;
	private ArrayList<String> processExec;
	private ArrayList<Process> process;
	public static int currentBurstTime;
	private int totalBurstTime;
	private ArrayList<Process> preempted = new ArrayList<Process>();
	private String[] queueNames;
	private int[] timeQuantum;

	public MLFQ2(Process[] p, String[] queueAlgo, int[] quantum) {
		super(p);
		this.p = p;
		q1 = new ArrayList<Process>();
		q2 = new ArrayList<Process>();
		q3 = new ArrayList<Process>();
		q4 = new ArrayList<Process>();
		q5 = new ArrayList<Process>();
		processExec = new ArrayList<String>();
		
		process = new ArrayList<Process>();
		queueNames = queueAlgo;
		timeQuantum = quantum;
	
		currentBurstTime = p[0].getArrivalTime();
		totalBurstTime = getTotalBurstTime();
		
	}
	
	public ArrayList<String> execute() {
		entry();
		boolean isExecutingAProcess = false;
		while(currentBurstTime < totalBurstTime) {
			isExecutingAProcess = false;
			while(!q1.isEmpty()) {
				q1execute(queueNames[0]);
				isExecutingAProcess = true;
			}
			preempted  = new ArrayList<Process>();
			//System.out.println("q1 is empty");
			while(!q2.isEmpty()) {
				q2execute(queueNames[1]);
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			preempted  = new ArrayList<Process>();
			if(!q1.isEmpty()) {continue;}
			//System.out.println("q2 is empty");
			while(!q3.isEmpty()) {
				q3execute(queueNames[2]);
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			preempted  = new ArrayList<Process>();
			if(!q1.isEmpty()) {continue;}
			//System.out.println("q3 is empty");
			while(!q4.isEmpty()) {
				q4execute(queueNames[3]);
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			while(!q5.isEmpty()) {
				q5execute(queueNames[4]);
				isExecutingAProcess = true;
				if(!q1.isEmpty()) {break;}
			}
			
			if(!isExecutingAProcess) {
				entry();
				currentBurstTime++;
			}
		}
		return processExec;
	}
	
	private void entry() {
		for(int a = 0;a < p.length; a++) {
			if(p[a].getArrivalTime() == currentBurstTime) {
				q1.add(p[a]);
			}
			
		}
		
		
	}
	
	private void q1execute(String algoName) {
		
		if(algoName == "FCFS"){
			q1 = fcfs(q1, 1);
		}
		else if(algoName == "SRTF"){
			q1 = srtfORpreemptivePrio(q1, 1, false);
		}
		else if(algoName == "Preemptive Priority"){
			q1 = srtfORpreemptivePrio(q1, 1, true);
		}
		else if(algoName == "SJF"){
			q1 = sjfORnonPreemptivePrio(q1, 1, false);
		}
		else if(algoName == "Non-Preemptive Priority"){
			q1 = sjfORnonPreemptivePrio(q1, 1, true);
		}
		else if(algoName == "Round Robin"){
			q1 = roundRobin(q1,1, timeQuantum[0]);
		}
		
		if(queueNames.length> 1){
			q2.addAll(preempted);
		}
		else{
			q1.addAll(preempted);
			preempted = new ArrayList<Process>();
		}
	}
	
	private void q2execute(String algoName) {
		
		if(algoName == "FCFS"){
			q2 = fcfs(q2, 2);
		}
		else if(algoName == "SRTF"){
			q2 = srtfORpreemptivePrio(q2, 2, false);
		}
		else if(algoName == "Preemptive Priority"){
			q2 = srtfORpreemptivePrio(q2, 2, true);
		}
		else if(algoName == "SJF"){
			q2 = sjfORnonPreemptivePrio(q2, 2, false);
		}
		else if(algoName == "Non-Preemptive Priority"){
			q2 = sjfORnonPreemptivePrio(q2, 2, true);
		}
		else if(algoName == "Round Robin"){
			q2 = roundRobin(q2, 2, timeQuantum[1]);
		}
		if(queueNames.length> 2){
			q3.addAll(preempted);
		}
		else{
			q2.addAll(preempted);
			preempted = new ArrayList<Process>();
		}
		System.out.println("+++++++");
	}
	
	private void q3execute(String algoName) {
		if(algoName == "FCFS"){
			q3 = fcfs(q3, 3);
		}
		else if(algoName == "SRTF"){
			System.out.println("I came at q3 SRTF");
			q3 = srtfORpreemptivePrio(q3, 3, false);
		}
		else if(algoName == "Preemptive Priority"){
			q3 = srtfORpreemptivePrio(q3, 3, true);
		}
		else if(algoName == "SJF"){
			q3 = sjfORnonPreemptivePrio(q3, 3, false);
		}
		else if(algoName == "Non-Preemptive Priority"){
			q3 = sjfORnonPreemptivePrio(q3, 3, true);
		}
		else if(algoName == "Round Robin"){
			q3 = roundRobin(q3, 3, timeQuantum[2]);
		}
		
		if(queueNames.length> 3){
			q4.addAll(preempted);
		}
		else{
			System.out.println("I came at q3 preempted");
			q3.addAll(preempted);
			preempted = new ArrayList<Process>();
		}
		System.out.println("*******");
	}
	
	private void q4execute(String algoName) {
		if(algoName == "FCFS"){
			q4 = fcfs(q4, 4);
		}
		else if(algoName == "SRTF"){
			q4 = srtfORpreemptivePrio(q4, 4, false);
		}
		else if(algoName == "Preemptive Priority"){
			q4 = srtfORpreemptivePrio(q4, 4, true);
		}
		else if(algoName == "SJF"){
			q4 = sjfORnonPreemptivePrio(q4, 4, false);
		}
		else if(algoName == "Non-Preemptive Priority"){
			q4 = sjfORnonPreemptivePrio(q4, 4, true);
		}
		else if(algoName == "Round Robin"){
			q4 = roundRobin(q4, 4, timeQuantum[3]);
		}
		if(queueNames.length> 3){
			q5.addAll(preempted);
		}
		else{
			q4.addAll(preempted);
			preempted = new ArrayList<Process>();
		}
		System.out.println("///////");
	}
	private void q5execute(String algoName) {
		if(algoName == "FCFS"){
			q5 = fcfs(q5, 5);
		}
		else if(algoName == "SRTF"){
			q5 = srtfORpreemptivePrio(q5, 5, false);
		}
		else if(algoName == "Preemptive Priority"){
			q5 = srtfORpreemptivePrio(q5, 5, true);
		}
		else if(algoName == "SJF"){
			q5 = sjfORnonPreemptivePrio(q5, 5, false);
		}
		else if(algoName == "Non-Preemptive Priority"){
			q5 = sjfORnonPreemptivePrio(q5, 5, true);
		}
		else if(algoName == "Round Robin"){
			q5 = roundRobin(q5, 5, timeQuantum[4]);
		}
		q5.addAll(preempted);
		preempted = new ArrayList<Process>();
		System.out.println("***********");
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
				processExec.add("P"+proc.getProcessID());
				process.add(proc);
				updateProcess(proc);
				entry();
				if((!q1.isEmpty()) && level > 1 && a == length-1) {
					
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
				processExec.add("P"+proc.getProcessID());
				process.add(proc);
				updateProcess(proc);
				entry();
				if((!q1.isEmpty()) && level > 1 && a == length-1) {
					
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
		for(int i = 0 ; i < arrivalQueue.size(); i++)
			System.out.println("P "+arrivalQueue.get(i).getProcessID());
		while(!arrivalQueue.isEmpty()) {
			continueExecution = true;
			proc = arrivalQueue.get(0);
			int length = proc.getBurstTime();
			
			if(length == 0){
				arrivalQueue.remove(proc);
				System.out.println("I came at 0 length");
			}
		
			for(int a = 0;a<length;a++) {
				if(continueExecution) {
					currentBurstTime +=1;
					proc.setBurstTime(1);
					processExec.add("P"+proc.getProcessID());
					process.add(proc);
					updateProcess(proc);
					entry();
					if((!q1.isEmpty()) && level > 1) {
						System.out.println("::P"+proc.getProcessID()+ " end = "+currentBurstTime);
						proc.setPreemtedCount(1);
						updateProcess(proc);
						q1.add(proc);
						arrivalQueue.remove(proc);
						return arrivalQueue;
					}
					
					continueExecution = sortArrivalQueue(proc, arrivalQueue, isPP);
					
					if(proc.getBurstTime() == 0) {
						//System.out.println("hi");
						arrivalQueue.remove(proc);
					}
				}else {
					proc.setPreemtedCount(1);
					updateProcess(proc);
					System.out.println("[preempted "+proc.getProcessID()+" at time "+currentBurstTime+" remaining = "+proc.getBurstTime());
					break;
				}
			}
			
			//System.out.println("P"+proc.getProcessID()+ " end = "+currentBurstTime);
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
					processExec.add("P"+proc.getProcessID());
					process.add(proc);
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
				
				if((!q1.isEmpty()) && level > 1) {
					q1.add(proc);
					proc.setPreemtedCount(1);
					arrivalQueue.remove(proc);
					
					return arrivalQueue;
				}
			}
			arrivalQueue.remove(proc);
		}
		System.out.println("Preempted "+ preempted.size());
		
		for(int i = 0; i < preempted.size(); i++)
			System.out.println("P "+preempted.get(i).getProcessID() + ": "+preempted.get(i).getBurstTime());
		return arrivalQueue;
	}
	/*public static void main(String[] args) {
		Process p[] = new Process[11];
		int arrival[] = {11,15,16,14,12,12,3,17,17,4, 38};
		int burst[] =   {4,8,2,3,2,6,7,2,12,9, 3};
		int prio[] =   {4,8,2,3,2,6,7,2,12,9, 1};
		for(int a = 0;a<p.length;a++) {
			p[a] = new Process(a, arrival[a], burst[a], prio[a]);
		}
		String[] q = {"Round Robin", "Round Robin", "SRTF"};
		int[] quant = {3, 4, 0};
		MLFQ2 mlfq = new MLFQ2(p, q, quant);
		mlfq.execute();
		mlfq.computeTurnAroundTime();
		mlfq.computeResponseTime();		
	}*/
	
	public void computeTurnAroundTime(){
		int[] completion = new int[processes.length];
		for(int i = 0; i < processes.length; i++){
			for(int j = 0; j < process.size(); j++){
				if(processes[i].getProcessID() == process.get(j).getProcessID()){
					completion[i]=j + processes[0].getArrivalTime();
				}
			}
		}
		
		for(int i = 0; i < completion.length; i++){
			processes[i].setTurnAroundTime(completion[i] + 1 - processes[i].getArrivalTime());
		}
	}
	public void computeResponseTime(){
		for(int i = 0; i < processes.length; i++){
			for(int j = 0; j < process.size(); j++){
				if(processes[i].getProcessID() == process.get(j).getProcessID()){
					processes[i].setResponseTime(j + processes[0].getArrivalTime() - processes[i].getArrivalTime());
					break;
				}
			}
		}
		for(int i = 0; i < processes.length; i++){
			System.out.println("RT "+ processes[i].getResponseTime() + "P"+processes[i].getProcessID());
		}
	}
	
}