// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: SJFScheduler.java

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJFScheduler implements SchedulingAlgorithm {
    
    @Override
    public List<ExecutionEvent> schedule(List<Process> processes) {
        // make a copy to avoid modifying the original list
        List<Process> copyProcesses = new ArrayList<>();
        for (Process p : processes) {
            copyProcesses.add(p.copy());
        }
        
        List<ExecutionEvent> timeline = new ArrayList<>();
        int currentTime = 0;
        
        // sort by arrival time
        copyProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));
        
        // always get the shortest job next
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
            Comparator.comparingInt(Process::getBurstTime)
        );
        
        int completedProcesses = 0;
        int totalProcesses = copyProcesses.size();
        int index = 0;
        
        while (completedProcesses < totalProcesses) {
            // add any newly arrived processes to the ready queue
            while (index < totalProcesses && copyProcesses.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(copyProcesses.get(index));
                index++;
            }
            
            // if no processes are ready, jump to next arrival time
            if (readyQueue.isEmpty()) {
                currentTime = copyProcesses.get(index).getArrivalTime();
                continue;
            }
            
            // get the shortest job from the ready queue
            Process currentProcess = readyQueue.poll();
            
            // calculate how long this process waited
            int waitingTime = currentTime - currentProcess.getArrivalTime();
            currentProcess.setWaitingTime(waitingTime);
            
            // add this execution to the timeline
            ExecutionEvent event = new ExecutionEvent(
                currentProcess.getPid(),
                currentTime,
                currentTime + currentProcess.getBurstTime()
            );
            timeline.add(event);
            
            // move time forward
            currentTime += currentProcess.getBurstTime();
            
            // calculate total time in system for this process
            int turnaroundTime = waitingTime + currentProcess.getBurstTime();
            currentProcess.setTurnaroundTime(turnaroundTime);
            
            completedProcesses++;
        }
        
        // copy the metrics back to the original process list
        updateOriginalProcesses(processes, copyProcesses);
        
        return timeline;
    }
    
    @Override
    public String getName() {
        return "Shortest Job First (SJF)";
    }
    
    // helper to update original processes with calculated metrics
    private void updateOriginalProcesses(List<Process> original, List<Process> modified) {
        for (Process originalProcess : original) {
            for (Process modifiedProcess : modified) {
                if (originalProcess.getPid() == modifiedProcess.getPid()) {
                    originalProcess.setWaitingTime(modifiedProcess.getWaitingTime());
                    originalProcess.setTurnaroundTime(modifiedProcess.getTurnaroundTime());
                    break;
                }
            }
        }
    }
}