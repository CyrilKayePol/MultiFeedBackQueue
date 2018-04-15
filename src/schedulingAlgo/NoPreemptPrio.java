package schedulingAlgo;
import java.util.ArrayList;

public class NoPreemptPrio extends SchedulingAlgorithm{
	
	private int currentTime;
	private ArrayList<Process> processQueue;
	
	public NoPreemptPrio(Process[] processes){
		super(processes);
		
		processQueue = new ArrayList<Process>();
	}
	public ArrayList<Process> execute(){
		currentTime = processes[0].getArrivalTime();
		int index = 0;
		ArrayList<Process> temp = new ArrayList<Process>();
		ArrayList<Process> temp1 = new ArrayList<Process>();
		
		for(int i = 0; i < processes.length; i++){
			temp1.add(processes[i]);
		}
		processQueue.add(processes[index]);
		currentTime = processes[index].getArrivalTime();
		
		while (index < processes.length){
			currentTime += processQueue.get(index).getBurstTime();
	
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
		return processQueue;
	}
	public ArrayList<Process> getProcessQueue(){
		return processQueue;
	}
}