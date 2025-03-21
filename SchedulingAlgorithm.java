// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: SchedulingAlgorithm.java

import java.util.List;

// common interface for all scheduling algorithms
public interface SchedulingAlgorithm {
    // run the scheduling algorithm on a list of processes
    // returns timeline of execution for gantt chart
    List<ExecutionEvent> schedule(List<Process> processes);
    String getName();
}