package schedulingAlgo;

import java.util.ArrayList;

public class RR {
	private ArrayList<Process> processes;
	private ArrayList<Integer> processesTime;
	private int quantum;
	private ArrayList<Process> preempted;
	
	public RR(ArrayList<Process> process, int q){
		processes = process;
		processesTime = new ArrayList<Integer>();
		preempted = new ArrayList<Process>();
	}
	
	public void execute(){
		if(processesTime.get(0) < quantum){
			processes.get(0).setBurstTime(1);
			processesTime.set(0, processesTime.get(0)+1);
		}
		else{
			if(processes.get(0).getBurstTime() > 0){
				preempted.add(processes.remove(0));
			}
			else{
				processes.remove(0);
			}
		}
	}
}
