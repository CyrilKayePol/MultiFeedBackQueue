package schedulingAlgo;
import java.util.Arrays;


public abstract class SchedulingAlgorithm  {
	
	protected Process[] processes;
	protected float avgWaitingTime, avgTurnaroundTime, avgResponseTime;
	
	public SchedulingAlgorithm(Process[] p){
		processes = p;
		
		Arrays.sort(processes);
	}
	
	public SchedulingAlgorithm() {}
	
	public void schedule(){}
	
	public int getTotalBurstTime() {
		int total = 0;
		for(int a = 0;a<processes.length;a++) {
			total+=processes[a].getBurstTime();
		}
		
		return total;
	}
	

	/*
	public float computeAvgTurnaroundTime(){
		
	}
	
	public float computeAvgWaitingTime(){
		
	}
	
	public float computeAvgResponseTime(){
		
	}
	*/
	
}