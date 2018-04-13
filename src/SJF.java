import java.util.ArrayList;

public class SJF extends SchedulingAlgorithm {
	private ArrayList<Process> arrivalQueue;
	private int burstTimeTotal;
	private Process[] process;
	private ArrayList<String> gantProcess = new ArrayList<String>();
	private ArrayList<String> gantLine = new ArrayList<String>();
	
	public SJF(Process[] process) {
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
	
	public void sortArrivalQueue() {
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
		while(!arrivalQueue.isEmpty()) {
			sortArrivalQueue();
			Process proc = arrivalQueue.get(0);
			arrivalQueue.remove(proc);
			gantProcess.add("P"+proc.getProcessID());
			gantLine.add(burstTimeTotal+"");
			int length = proc.getBurstTime();
			for(int a = 0;a<length;a++) {
				burstTimeTotal +=1;
				proc.setBurstTime(1);
				updateProcess(proc);
				addNewlyArrivedProcess(burstTimeTotal);
				gantProcess.add(" ");
				gantLine.add("-");
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
	
	public static void main(String[] args) {
		Process p[] = new Process[10];
		int arrival[] = {1,5,6,4,2,2,0,7,7,4};
		int burst[] = {6,8,7,3,6,6,3,2,2,9};
		for(int a = 0;a<p.length;a++) {
			p[a] = new Process(a, arrival[a], burst[a], 0);
		}
		SJF sjf = new SJF(p);
		sjf.seedArrivalQueue();
		sjf.execute();
		//sjf.printGant();
	}

}
