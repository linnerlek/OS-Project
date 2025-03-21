// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: Process.java

public class Process {
    // core process attributes from the input file
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    // metrics that get calculated by the schedulers
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime;

    // initialize a new process with input values
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.remainingTime = burstTime;
    }

    // getters and setters for all fields
    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }
    
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", priority=" + priority +
                '}';
    }
    
    // create copy to avoid modifying the original
    public Process copy() {
        Process copy = new Process(this.pid, this.arrivalTime, this.burstTime, this.priority);
        copy.waitingTime = this.waitingTime;
        copy.turnaroundTime = this.turnaroundTime;
        copy.remainingTime = this.remainingTime;
        return copy;
    }
}