package schedulingAlgo;
import java.util.ArrayList;

public class FCFS extends SchedulingAlgorithm{
	private Process[] process;
	private ArrayList<Process> gantProcess = new ArrayList<Process>();
	private ArrayList<Process> gantLine = new ArrayList<Process>();
	private int[] bursts;
	
	public FCFS(Process[] process) {
		super(process);
		this.process = process;
	}
	
	public ArrayList<Process> execution() {
		bursts = new int[process.length];
		for(int a = 0;a<process.length;a++) {
			gantProcess.add(process[a]);
			while(process[a].getBurstTime() >0) {
				process[a].setBurstTime(1);
				bursts[a] +=1;
			}
		}
		return gantProcess;
	}
	public void printGant() {
		for(int a = 0;a<gantProcess.size();a++) {
			System.out.print(gantProcess.get(a));
		}
		System.out.println();
		for(int b = 0;b<gantLine.size();b++) {
			System.out.print(gantLine.get(b));
		}
	}
	
	public int[] getBurst(){
		return bursts;
	}
	public static void main(String[] args) {
		Process p[] = new Process[10];
		int arrival[] = {1,5,6,4,2,2,0,7,7,4};
		int burst[] = {6,8,7,3,6,6,3,2,2,9};
		for(int a = 0;a<p.length;a++) {
			p[a] = new Process(a, arrival[a], burst[a], 0);
		}
		FCFS fcfs = new FCFS(p);
		fcfs.execution();
		fcfs.printGant();
	}
	
}