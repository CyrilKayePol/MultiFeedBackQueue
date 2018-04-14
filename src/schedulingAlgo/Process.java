package schedulingAlgo;
public class Process implements Comparable<Process> {
	private int arrivalTime, burstTime, priority, processID;
	private int preemptedCount = 0;
	public Process(int pID, int arrive, int burst, int prio){
		
		processID = pID;
		arrivalTime = arrive;
		burstTime = burst;
		priority = prio;
		
	}
	
	public int getProcessID(){
		return processID;
	}
	
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public int getBurstTime(){
		return burstTime;
	}
	
	public void setBurstTime(int givenCPUTime){
		burstTime -= givenCPUTime;
	}
	
	public int getPriorityNum(){
		return priority;
	}
	
	public int getPreemptedCount() {
		return preemptedCount;
	}
	
	public void setPreemtedCount(int count) {
		this.preemptedCount +=count;
	}
	public int compareTo(Process o) {
		return this.getArrivalTime() - o.getArrivalTime();
	}
	
}