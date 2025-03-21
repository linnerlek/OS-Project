// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: FCSScheduler.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFSScheduler implements SchedulingAlgorithm {
    
    @Override
    public List<ExecutionEvent> schedule(List<Process> processes) {
        // make a copy to avoid messing up the original processes
        List<Process> copyProcesses = new ArrayList<>();
        for (Process p : processes) {
            copyProcesses.add(p.copy());
        }
        
         // sort by arrival time, whichever process arrives first gets to run first
        Collections.sort(copyProcesses, Comparator.comparingInt(Process::getArrivalTime));
        
        List<ExecutionEvent> timeline = new ArrayList<>();
        int currentTime = 0;
        
        // loop through each process in arrival order
        for (Process process : copyProcesses) {
            // gap between processes, CPU might be idle if next process hasn't arrived yet
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
            
            // wait time = current time - when it arrived
            int waitingTime = currentTime - process.getArrivalTime();
            process.setWaitingTime(waitingTime);
            
             // add to timeline
            ExecutionEvent event = new ExecutionEvent(
                process.getPid(), 
                currentTime, 
                currentTime + process.getBurstTime()
            );
            timeline.add(event);
            
            // move clock forward by the burst time, In FCFS, once a process starts, it runs to completion
            currentTime += process.getBurstTime();
            
            // turnaround time = waiting time + how long it actually ran
            int turnaroundTime = waitingTime + process.getBurstTime();
            process.setTurnaroundTime(turnaroundTime);
        }
        
        // update the original list with the wait and turnaround times
        updateOriginalProcesses(processes, copyProcesses);
        
        return timeline;
    }
    
    @Override
    public String getName() {
        return "First-Come, First-Served (FCFS)";
    }
    
    // helper to copy calculated times back to original processes
    private void updateOriginalProcesses(List<Process> original, List<Process> modified) {
        for (Process modifiedProcess : modified) {
            for (Process originalProcess : original) {
                if (originalProcess.getPid() == modifiedProcess.getPid()) {
                    originalProcess.setWaitingTime(modifiedProcess.getWaitingTime());
                    originalProcess.setTurnaroundTime(modifiedProcess.getTurnaroundTime());
                    break;
                }
            }
        }
    }
}