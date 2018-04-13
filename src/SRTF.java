import java.util.ArrayList;

public class SRTF extends SchedulingAlgorithm {
	private ArrayList<Process> arrivalQueue;
	private int burstTimeTotal;
	private Process[] process;
	private ArrayList<String> gantProcess = new ArrayList<String>();
	private ArrayList<String> gantLine = new ArrayList<String>();
	
	public SRTF(Process[] process) {
		super(process);
		this.process = process;
		arrivalQueue = new ArrayList<Process>();
		burstTimeTotal = process[0].getArrivalTime();
		
	}
	
	public void seedArrivalQueue() {
		for(int a = 0;a<process.length;a++) {
			if(process[a].getArrivalTime() == process[0].getArrivalTime()) {
				arrivalQueue.add(process[a]);
			}else {
				break;
			}
		}
	}
	
	public boolean sortArrivalQueue(Process current) {
		Process temp;
		for(int b = 0;b<arrivalQueue.size();b++) {
			for(int a = b+1;a<arrivalQueue.size();a++) {
				int bt1 = arrivalQueue.get(b).getBurstTime();
				int bt2 = arrivalQueue.get(a).getBurstTime();
				if( bt1>bt2 ) {
					temp = arrivalQueue.get(b);
					arrivalQueue.set(b, arrivalQueue.get(a));
					arrivalQueue.set(a, temp);
				}else if( bt1 == bt2) {
					int at1 = arrivalQueue.get(b).getArrivalTime();
					int at2 = arrivalQueue.get(a).getArrivalTime();
					if(at1 > at2) {
						temp = arrivalQueue.get(b);
						arrivalQueue.set(b, arrivalQueue.get(a));
						arrivalQueue.set(a, temp);
					}else if(at1 == at2) {
						int pid1 = arrivalQueue.get(b).getProcessID();
						int pid2 = arrivalQueue.get(a).getProcessID();
						if(pid1 > pid2) {
							temp = arrivalQueue.get(b);
							arrivalQueue.set(b, arrivalQueue.get(a));
							arrivalQueue.set(a, temp);
						}
					}
				}
			}
		}
		
		if(current == arrivalQueue.get(0)) {
			return true;
		}
		return false;
		
	}
	
	public void addNewlyArrivedProcess(int time) {
		for(int a = 0;a<process.length;a++) {
			if(process[a].getArrivalTime() == time && !arrivalQueue.contains(process[a])) {
				arrivalQueue.add(process[a]);
			}
		}
	}
	
	public void updateProcess(Process p) {
		for(int a = 0;a<process.length;a++) {
			if(p.getProcessID() == process[a].getProcessID()) {
				process[a] = p;
				break;
			}
		}
	}
	public void execute() {
		boolean continueExecution = true;
		Process proc = null;
		while(!arrivalQueue.isEmpty()) {
			continueExecution = true;
			if((proc != arrivalQueue.get(0)) && (proc != null) && proc.getBurstTime() != 0) {
				proc.setPreemtedCount(1);
				updateProcess(proc);
			}
			proc = arrivalQueue.get(0);
			gantProcess.add("P"+proc.getProcessID());
			gantLine.add(burstTimeTotal+"");
			int length = proc.getBurstTime();
			for(int a = 0;a<length;a++) {
				if(continueExecution) {
					burstTimeTotal +=1;
					proc.setBurstTime(1);
					updateProcess(proc);
					addNewlyArrivedProcess(burstTimeTotal);
					continueExecution = sortArrivalQueue(proc);
					gantProcess.add(" ");
					gantLine.add("-");
					
					if(proc.getBurstTime() == 0) {
						arrivalQueue.remove(proc);
					}
				}else {
					break;
				}
			}
			
			
		}
		gantLine.add(burstTimeTotal+"");
		
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
	
	public void printExecutionHistory() {
		System.out.println();
		for(int a = 0;a<process.length;a++) {
			System.out.println("P"+process[a].getProcessID()+" :"+process[a].getPreemptedCount());
		}
	}
	
	public static void main(String[] args) {
		Process p[] = new Process[2];
		int arrival[] = {0,2};
		int burst[] = {9,2};
		for(int a = 0;a<p.length;a++) {
			p[a] = new Process(a, arrival[a], burst[a], 0);
		}
		SRTF srtf = new SRTF(p);
		srtf.seedArrivalQueue();
		srtf.execute();
		srtf.printGant();
		srtf.printExecutionHistory();
	}

}
