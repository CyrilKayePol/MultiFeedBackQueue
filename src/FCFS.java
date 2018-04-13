import java.util.ArrayList;

public class FCFS extends SchedulingAlgorithm{
	private Process[] process;
	private int burstTimeTotal;
	private ArrayList<String> gantProcess = new ArrayList<String>();
	private ArrayList<String> gantLine = new ArrayList<String>();
	
	public FCFS(Process[] process) {
		super(process);
		this.process = process;
	}
	
	public void execution() {
		for(int a = 0;a<process.length;a++) {
			gantLine.add(""+burstTimeTotal);
			gantProcess.add("P"+process[a].getProcessID());
			while(process[a].getBurstTime() >0) {
				process[a].setBurstTime(1);
				gantLine.add("-");
				gantProcess.add(" ");
				burstTimeTotal += 1;
				
			}
		}
		gantLine.add(""+burstTimeTotal);
		
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