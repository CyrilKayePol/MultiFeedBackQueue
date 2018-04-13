import java.util.ArrayList;

public class RoundRobin extends SchedulingAlgorithm{
	
	private int timeQuantum;
	private ArrayList<Process> processQueue;
	
	public RoundRobin(Process[] processes, int timeQuant){
		super(processes);
		timeQuantum = timeQuant;
		processQueue = new ArrayList<Process>();
	}
	
	public void schedule(){

		ArrayList<Process> temp = new ArrayList<Process>();
		ArrayList<Process> temp1 = new ArrayList<Process>();
		Process p = null;
		int totalBurst = processes[0].getArrivalTime();
		for(int i = 0; i < processes.length - 1; i++){
			System.out.println("Process Arrival "+ processes[i].getProcessID());
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
	
		System.out.println("Total Burst "+ totalBurst);
		
		for(int i = processes[0].getArrivalTime(); i < totalBurst; i++){
			for(int j = 0; j < temp1.size(); j++){
				if(i == temp1.get(j).getArrivalTime()){
					temp.add(temp1.get(j));
				}
			}
			if(p != null && p.getBurstTime() > 0){
				temp.add(p);
			}
			
			System.out.println("At time "+ i);
			for(int k = 0; k < temp.size(); k++){
				System.out.println("P  "+ temp.get(k).getProcessID());
			}
			
			if(temp.size() > 0){
				int cpuTime = 0;
				if(timeQuantum > temp.get(0).getBurstTime()){
					cpuTime = temp.get(0).getBurstTime();
				}
				else{
					cpuTime = timeQuantum;
				}
				
				p = temp.remove(0);
				
				int b = i;
				for(int a = b; a < cpuTime + b; a++){
					for(int c =0; c < temp1.size(); c++){
						if(a == temp1.get(c).getArrivalTime()){
							if(!(temp.contains(temp1.get(c))) && temp1.get(c) != p){
								temp.add(temp1.get(c));
							}
						}
					}
					processQueue.add(p);
					p.setBurstTime(1);
					if(a < cpuTime + b - 1)
						i += 1;
				}
			}
		}
		
		for(int i = 0; i < processQueue.size(); i++){
			System.out.println("Process "+ processQueue.get(i).getProcessID());
		}
		System.out.println(processQueue.size());
		
	}
	
	public static void main(String[] args){
		int[] a = {5,4,3,1,2,6};
		int[] b = {5,6,7,9,2,3};
		int[] p = {3,1,2, 8 , 0,1};
		
		Process[] p1 = new Process[6];
		for(int i = 0; i < 6; i++){
			p1[i] = new Process(i+1, a[i], b[i], p[i]);
		}
		
		new RoundRobin(p1, 3).schedule();
	}
}