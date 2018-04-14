package schedulingAlgo;
public class ProcessExecutionHistory{
	private int numOfTimesPreempted;
	
	public int getTimesPreempted(){
		return numOfTimesPreempted;
	}
	
	public void setTimesPreempted(){
		numOfTimesPreempted += 1;
	}
}