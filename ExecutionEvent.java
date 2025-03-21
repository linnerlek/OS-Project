// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: ExecutionEvent.java

public class ExecutionEvent {
    // basic info for a single execution block in the gantt chart
    private int processId;
    private int startTime;
    private int endTime;

    // constructor with all needed parameters
    public ExecutionEvent(int processId, int startTime, int endTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getters for the private fields
    public int getProcessId() {
        return processId;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
    
    // for debugging and logging
    @Override
    public String toString() {
        return "P" + processId + " [" + startTime + " - " + endTime + "]";
    }
}