package schedulingAlgo;

import java.util.ArrayList;

public class MLFQ1 extends SchedulingAlgorithm{
	private String[] queueAlgo;
	private int[] timeQuantum;
	private Process[] processes;
	private ArrayList<Process> processQueue;
	private ArrayList<String> processExec;
	
	public MLFQ1(String[] q, Process[] p, int[] time){
		super(p);
		
		queueAlgo = q;
		processes = p;
		timeQuantum = time;
		
		processQueue = new ArrayList<Process>();
		processExec = new ArrayList<String>();
	}
	public ArrayList<String> execute(){
		//System.out.println("I came at execute");
		for(int i = 0; i < queueAlgo.length; i++){
			if(queueAlgo[i] == "FCFS"){
				executeFCFS(i);
			}
			else if(queueAlgo[i] == "SJF"){
				executeSJF(i);
			}
			else if(queueAlgo[i] == "SRTF"){
				executeSRTF(i);
			}
			else if(queueAlgo[i] == "Non-Preemptive Priority"){
				//System.out.println("Non-Preemptive");
				executeNoPreemptPrio(i);
			}
			else if(queueAlgo[i] == "Preemptive Priority"){
				//System.out.println("Preemptive");
				executePreemptPrio(i);
			}
			else if(queueAlgo[i] == "Round Robin"){
				//System.out.println("RoundRobin");
				executeRR(i, timeQuantum[i]);
			}
			else{
				
			}
			
			if(processQueue.size() <= 0){
				break;
			}
		} // end first for loop
		return processExec;
	} // end execute
	
	public void executeFCFS(int queueLevel){
		if(queueLevel ==0){
			FCFS fcfs = new FCFS(processes);
			processQueue = fcfs.execution();
			//System.out.println(processQueue.size());
			
			for(int i = 0; i < processQueue.size(); i++){
				for(int j = 0; j < processes.length; j++){
					if(processQueue.get(i).getProcessID() == processes[j].getProcessID()){
						int burst = fcfs.getBurst()[i];
						
						for(int m = 0; m < burst; m++){
							processExec.add("P"+processQueue.get(i).getProcessID());
						}
					}
				}
			}
		}
		else{
			for(int i = 0; i < processQueue.size(); i++){
				int burst = processQueue.get(i).getBurstTime();
				for(int j = 0; j < burst; j++){
					processExec.add("P"+processQueue.get(i).getProcessID());
				}
				processQueue.get(i).setBurstTime(burst);
			}
		}
		
		printProcess();
		processQueue.clear();
	}
	public void executeRR(int queueLevel, int timeQuantum){
		if(queueLevel == 0){
			ArrayList<Process> temp = new ArrayList<Process>();
			ArrayList<Process> temp1 = new ArrayList<Process>();
			int totalBurst = processes[0].getArrivalTime();
			for(int i = 0; i < processes.length - 1; i++){
				temp1.add(processes[i]);
				
				totalBurst += processes[i].getBurstTime();
			}
			temp1.add(processes[processes.length - 1]);
			if(totalBurst < processes[processes.length - 1].getArrivalTime()){
				totalBurst = processes[processes.length - 1].getArrivalTime() + processes[processes.length - 1].getBurstTime();
			}
			else{
				totalBurst += processes[processes.length - 1].getBurstTime();
			}
			
			for(int i = processes[0].getArrivalTime(); i < totalBurst; i++){
				for(int j = 0; j < temp1.size(); j++){
					if(i == temp1.get(j).getArrivalTime()){
						temp.add(temp1.get(j));
					}
				}
				if(temp.size() == processes.length)
					break;
			}
			
			for(int i = 0; i < temp.size(); i++){
				if(temp.get(i).getBurstTime() <= timeQuantum){
					for(int j = 0; j < temp.get(i).getBurstTime();j++){
						processExec.add("P"+temp.get(i).getProcessID());
					}
					temp.get(i).setBurstTime(temp.get(i).getBurstTime());
				}
				else{
					for(int j = 0; j < timeQuantum;j++){
						processExec.add("P"+temp.get(i).getProcessID()+"");
					}
					temp.get(i).setBurstTime(timeQuantum);
					processQueue.add(temp.get(i));
				}
			}
			
			printProcess();
		}
		else if(queueLevel == queueAlgo.length-1){
			while(processQueue.size() != 0){
				executeRR1(timeQuantum);
				printProcess();
			}
		}
		else{
			executeRR1(timeQuantum);
			printProcess();
		}
	}
	public void executeSJF(int queueLevel){
		if(queueLevel ==0){
			SJF sjf = new SJF(processes);
			sjf.seedArrivalQueue();
			processQueue = sjf.execute();
			//System.out.println("PQ "+processQueue.size());
			
			for(int i = 0; i < processQueue.size(); i++){
				for(int j = 0; j < processes.length; j++){
					if(processQueue.get(i).getProcessID() == processes[j].getProcessID()){
						int burst = sjf.getBursts()[i];
						
						for(int m = 0; m < burst; m++){
							processExec.add("P"+processQueue.get(i).getProcessID());
						}
					}
				}
			}
		}
		else{
			for(int i = 0; i < processQueue.size(); i++){
				for(int j = 0; j < processQueue.size(); j++){
					if(processQueue.get(i).getBurstTime() < processQueue.get(j).getBurstTime()){
						Process p = processQueue.get(i);
						processQueue.set(i, processQueue.get(j));
						processQueue.set(j, p);
					}
				}
			}
			
			for(int i = 0; i < processQueue.size(); i++){
				int burst = processQueue.get(i).getBurstTime();
				for(int j = 0; j < burst; j++){
					processExec.add("P"+processQueue.get(i).getProcessID());
				}
				processQueue.get(i).setBurstTime(burst);
			}
		}
		
		printProcess();
		processQueue.clear();
	}
	public void executePreemptPrio(int queueLevel){
		if(queueLevel == 0){
			int  totalBurst = processes[0].getArrivalTime();
			int[] bursts = new int[processes.length];
			ArrayList<Process> temp = new ArrayList<Process>();
			ArrayList<Process> temp1 = new ArrayList<Process>();
			ArrayList<Process> tempQ = new ArrayList<Process>();
			
			for(int i = 0; i < processes.length; i++){
				bursts[i]= processes[i].getBurstTime();
			}
			
			for(int i = 0; i < processes.length - 1; i++){
				temp1.add(processes[i]);
				
				totalBurst += processes[i].getBurstTime();
			}
			
			temp1.add(processes[processes.length - 1]);
			if(totalBurst < processes[processes.length - 1].getArrivalTime()){
				totalBurst = processes[processes.length - 1].getArrivalTime() + processes[processes.length - 1].getBurstTime();
			}
			else{
				totalBurst += processes[processes.length - 1].getBurstTime();
			}
		
			boolean toBreak = false;
			
			for(int k = processes[0].getArrivalTime(); k < totalBurst; k++){
				
				for(int j = 0; j < temp1.size(); j++){
					if(k == temp1.get(j).getArrivalTime()){
						temp.add(temp1.get(j));
					}
				}

				for(int i=0 ; i<temp.size()-1 ; i++)
		        {
		            for(int j=i+1 ; j<temp.size() ;j++)
		            {
		                Process j1 = temp.get(i);
		                Process j2 = temp.get(j);
		                if(j2.getPriorityNum() < (j1.getPriorityNum()))
		                {
		                    temp.set(i, j2);
		                    temp.set(j, j1);
		                }
		            }
		        }
				
				if(temp.get(0).getBurstTime() > 0){
					processQueue.add(temp.get(0));
					temp.get(0).setBurstTime(1);
					
					if(temp.get(0).getBurstTime() == 0){
						temp.remove(0);
					}
				}
				else{
					temp.remove(0);
				}
				
				for(int i = 0; i < processQueue.size(); i++){
					if(tempQ.size() == 0){
						tempQ.add(processQueue.get(i));
					}
					else{
						for(int j = 0; j < tempQ.size(); j++){
							if (processQueue.get(i).getProcessID() == tempQ.get(j).getProcessID()){
								 break;
							}
							else{
								if(j == tempQ.size() - 1){
									tempQ.add(processQueue.get(i));
								}
							}
						}
					}
					if(tempQ.size() == processes.length){
						toBreak = true;
						break;
					}
				}
				
				if(toBreak){
					break;
				}
			}
			
			for(int i = 0; i < tempQ.size() -1; i++){
				int count = tempQ.get(i).getBurstTime();
				
				for(int h = 0; h < processes.length; h++){
					if(tempQ.get(i).getProcessID() == processes[h].getProcessID()){
						for(int j = 0; j < bursts[h]- count; j++){
							processExec.add("P"+tempQ.get(i).getProcessID());
						}
					}
				}
			}
			int lastBurst = (tempQ.get(tempQ.size()-1).getBurstTime()) + 1;
			tempQ.get(tempQ.size() - 1).setBurstTime(lastBurst -1);
			for(int i = 0; i < lastBurst; i++){
				processExec.add("P"+tempQ.get(tempQ.size()-1).getProcessID());
			}
			
			for(int i = 0; i < tempQ.size(); i++){
				int count = tempQ.get(i).getBurstTime();
				if (count == 0){
					tempQ.set(i, null);
				}
			}
			
			processQueue = new ArrayList<Process>();
			for(int i = 0; i < tempQ.size(); i++){
				if (tempQ.get(i) != null){
					processQueue.add(tempQ.get(i));
				}
			}
		}
		else{
			executePP1();
		}
	}
	public void executePP1(){
		ArrayList<Process> temp = new ArrayList<Process>();
		for(int i = 0; i < processQueue.size(); i++){
			temp.add(processQueue.get(i));
		}
		processQueue.clear();
		
		for(int i=0 ; i<temp.size()-1 ; i++)
        {
            for(int j=i+1 ; j<temp.size() ;j++)
            {
                Process j1 = temp.get(i);
                Process j2 = temp.get(j);
                if(j2.getPriorityNum() < (j1.getPriorityNum()))
                {
                    temp.set(i, j2);
                    temp.set(j, j1);
                }
            }
        }
		for(int i = 0; i < temp.size(); i++){
			processQueue.add(temp.get(i));
		}
		for(int i = 0; i <processQueue.size(); i++){
			for(int j = 0; j < processQueue.get(i).getBurstTime(); j++){
				processExec.add("P"+processQueue.get(i).getProcessID());
			}
			processQueue.get(i).setBurstTime(processQueue.get(i).getBurstTime());
		}
		printProcess();
	}
	public void executeRR1(int timeQuantum){
		ArrayList<Process> temp = new ArrayList<Process>();
		for(int i = 0; i < processQueue.size(); i++){
			temp.add(processQueue.get(i));
		}
		processQueue.clear();
		
		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i).getBurstTime() <= timeQuantum){
				for(int j = 0; j < temp.get(i).getBurstTime();j++){
					processExec.add("P"+temp.get(i).getProcessID());
				}
				temp.get(i).setBurstTime(temp.get(i).getBurstTime());
			}
			else{
				for(int j = 0; j < timeQuantum;j++){
					processExec.add("P"+temp.get(i).getProcessID());
				}
				temp.get(i).setBurstTime(timeQuantum);
				processQueue.add(temp.get(i));
			}
		}// end for loop i
		
		printProcess();
	}
	public void executeSRTF(int queueLevel){
		if(queueLevel == 0){
			SRTF srtf = new SRTF(processes);
			srtf.seedArrivalQueue();
			processQueue = srtf.execute();
			processExec = srtf.processExecution();
			//System.out.println("PQ "+processQueue.size());
		}
		else{
			for(int i = 0; i < processQueue.size(); i++){
				for(int j = 0; j < processQueue.size(); j++){
					if(processQueue.get(i).getBurstTime() < processQueue.get(j).getBurstTime()){
						Process p = processQueue.get(i);
						processQueue.set(i, processQueue.get(j));
						processQueue.set(j, p);
					}
				}
			}
			
			for(int i = 0; i < processQueue.size(); i++){
				int burst = processQueue.get(i).getBurstTime();
				for(int j = 0; j < burst; j++){
					processExec.add("P"+processQueue.get(i).getProcessID());
				}
				processQueue.get(i).setBurstTime(burst);
			}
		}
		
		printProcess();
	}
	public void printProcess(){
		//System.out.println("------> Executed! "+processExec.size());
		for(int i = 0; i < processExec.size(); i++){
			//System.out.println("E-Process: "+processExec.get(i));
		}
	}
	public void executeNoPreemptPrio(int queueLevel){
		if(queueLevel == 0){
			NoPreemptPrio prio = new NoPreemptPrio(processes);
			processQueue = prio.execute();
			//System.out.println("PQ "+processQueue.size());
			for(int i = 0; i < processQueue.size(); i++){
				int burst = processQueue.get(i).getBurstTime();
				for(int j = 0; j < burst; j++){
					processExec.add("P"+processQueue.get(i).getProcessID());
				}
			}
			printProcess();
			processQueue.clear();
		}
		else{
			executePP1();
		}
	}
	
	public ArrayList<String> getProcessExec(){
		return processExec;
	}
	
	public void computeTurnAroundTime(){
		
	}
/*	public static void main(String[] args){
		int[] a = {5,4,3,1,2,6, 1};
		int[] b = {55,66,77,19,22,16, 14};
		int[] p = {3,1,2, 8 , 0,1, 1};
		
		Process[] p1 = new Process[7];
		for(int i = 0; i < 7; i++){
			p1[i] = new Process(i, a[i], b[i], p[i]);
		}
		int[] ab = {1,2,0,0,0,0};
		String[] algo = {"Round Robin", "Round Robin","SRTF", "Preemptive Priority","FCFS", "Non-Preemptive Priority"};
		new MLFQ1(algo, p1, ab).execute();
	}*/
}
