import java.util.ArrayList;

public class PreemptPrio extends SchedulingAlgorithm{

	private ArrayList<Process> processQueue;
	
	public PreemptPrio(Process[] processes){
		super(processes);
		
		processQueue = new ArrayList<Process>();
	}
	
	public void schedule(){

		int totalBurst = processes[0].getArrivalTime();
		ArrayList<Process> temp = new ArrayList<Process>();
		ArrayList<Process> temp1 = new ArrayList<Process>();
		
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
			System.out.println("I came here");
			totalBurst += processes[processes.length - 1].getBurstTime();
		}
	
		System.out.println("Total Burst "+ totalBurst);
		
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
		}
		
		for(int i = 0; i < processQueue.size(); i++){
			System.out.println("Process "+ processQueue.get(i).getProcessID());
		}
		System.out.println(processQueue.size());
	}
	
	public static void main(String[] args){
		int[] a = {0,1,2,3,4};
		int[] b = {9,4,5,7,3};
		int[] p = {5,3,1,2,4};
		
		Process[] p1 = new Process[5];
		for(int i = 0; i < 5; i++){
			p1[i] = new Process(i+1, a[i], b[i], p[i]);
		}
		
		new PreemptPrio(p1).schedule();
	}
}