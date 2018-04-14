package schedulingAlgo;
import java.util.ArrayList;

public class NoPreemptPrio extends SchedulingAlgorithm{
	
	private int currentTime;
	private ArrayList<Process> processQueue;
	
	public NoPreemptPrio(Process[] processes){
		super(processes);
		
		processQueue = new ArrayList<Process>();
	}
	public void schedule(){
		currentTime = processes[0].getArrivalTime();
		int index = 0;
		ArrayList<Process> temp = new ArrayList<Process>();
		ArrayList<Process> temp1 = new ArrayList<Process>();
		
		for(int i = 0; i < processes.length; i++){
			System.out.println("Process Arrival "+ processes[i].getProcessID());
			temp1.add(processes[i]);
		}
		processQueue.add(processes[index]);
		currentTime = processes[index].getArrivalTime();
		
		while (index < processes.length){
			currentTime += processQueue.get(index).getBurstTime();
			processQueue.get(index).setBurstTime(processQueue.get(index).getBurstTime());
			 
			System.out.println("Size "+temp1.size());
			for(int i = 1; i < temp1.size(); i++){
				if(temp1.get(i) != null){
					if(temp1.get(i).getArrivalTime() < currentTime){
						temp.add(temp1.get(i));
						temp1.set(i, null);
					}
				}
			}
			
			for(int i = 0; i < temp1.size(); i++){
				if(temp1.get(i) == null)
					temp1.remove(i);
			}
			
			System.out.println("CurrentTime "+ currentTime);
			for(int i = 0; i < temp.size(); i++){
				System.out.println("P "+ temp.get(i).getProcessID());
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
			 if(temp.size()>0){
				 processQueue.add(temp.remove(0));
			 }
			 
			 index += 1;
		}
		
		for(int i = 0; i < processQueue.size(); i++){
			System.out.println("Process "+ processQueue.get(i).getProcessID()+ " burst "+ processQueue.get(i).getBurstTime());
		}
		System.out.println(processQueue.size());
	}
	
	public static void main(String[] args){
		int[] a = {0,1,2,3,4};
		int[] b = {9,4,5,7,3};
		int[] p = {5,3,1,2,4};
		
		Process[] p1 = new Process[5];
		for(int i = 0; i < 5; i++){
			p1[i] = new Process(i, a[i], b[i], p[i]);
		}
		
		new NoPreemptPrio(p1).schedule();
	}
}