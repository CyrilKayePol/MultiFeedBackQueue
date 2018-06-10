package schedulingAlgo;
import java.util.ArrayList;

public class NPP extends SchedulingAlgorithm {
	private ArrayList<Process> arrivalQueue;
	private int burstTimeTotal;
	private Process[] process;
	private ArrayList<Process> gantProcess = new ArrayList<Process>();
	private ArrayList<String> gantLine = new ArrayList<String>();
	private int[] bursts;
	
	public NPP(Process[] process) {
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
				int bt1 = arrivalQueue.get(b).getPriorityNum();
				int bt2 = arrivalQueue.get(a).getPriorityNum();
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
	public ArrayList<Process> execute() {
		bursts = new int[process.length];
		int count = 0;
		Process proc = null;
		while(!arrivalQueue.isEmpty()) {
			sortArrivalQueue();
			if((proc != arrivalQueue.get(0)) && (proc != null) && proc.getBurstTime() != 0) {
				proc.setPreemtedCount(1);
				updateProcess(proc);
			}
			proc = arrivalQueue.get(0);
			arrivalQueue.remove(proc);
			gantProcess.add(proc);
			gantLine.add(burstTimeTotal+"");
			int length = proc.getBurstTime();
			bursts[count] = length;
			count += 1;
			//System.out.println("P"+proc.getProcessID()+ " start = "+burstTimeTotal);
			for(int a = 0;a<length;a++) {
				burstTimeTotal +=1;
				proc.setBurstTime(1);
				updateProcess(proc);
				addNewlyArrivedProcess(burstTimeTotal);
				gantLine.add("-");
			}
			//System.out.println("P"+proc.getProcessID()+ " end = "+burstTimeTotal);
			
		}
		gantLine.add(burstTimeTotal+"");
		return gantProcess;
	}
	
	public int[] getBursts(){
		return bursts;
	}
	public void printGant() {
		for(int a = 0;a<gantProcess.size();a++) {
			//System.out.print(gantProcess.get(a).getProcessID());
		}
		//System.out.println();
		for(int b = 0;b<gantLine.size();b++) {
			//System.out.print(gantLine.get(b));
		}
	}
	
	public void printExecutionHistory() {
		//System.out.println();
		for(int a = 0;a<process.length;a++) {
			//System.out.println("P"+process[a].getProcessID()+" :"+process[a].getPreemptedCount());
		}
	}
	
	public static void main(String[] args) {
		int a[] = {1,3,2,0,8};
		int b[] = {10,5,2,7,1};
		int p[] = {1,5,3,2,1};
		
		Process process[] = new Process[5];
		for(int i = 0;i<process.length;i++) {
			process[i] = new Process(i,a[i], b[i], p[i]);
		}
		
		NPP n = new NPP(process);
		n.seedArrivalQueue();
		n.execute();
		n.printGant();
		n.printExecutionHistory();
	}
}
