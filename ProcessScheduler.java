// Operating Systems Project 1 - Process Scheduling Simulation
// Linn Kloefta
// CSC 4320 - Spring 2025
// Filename: ProcessScheduler.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessScheduler {
    private List<Process> processes;
    private List<SchedulingAlgorithm> algorithms;
    
    public ProcessScheduler() {
        processes = new ArrayList<>();
        algorithms = new ArrayList<>();
        
        // scheduling algorithms
        algorithms.add(new FCFSScheduler());
        algorithms.add(new SJFScheduler());
    }
    
    public void readProcessesFromFile(String filename) throws IOException {
        processes.clear();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                // skip header line with column names
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // skip blank lines in the file
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // parse the process data, splitting on whitespace
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 4) {
                    int pid = Integer.parseInt(parts[0]);
                    int arrivalTime = Integer.parseInt(parts[1]);
                    int burstTime = Integer.parseInt(parts[2]);
                    int priority = Integer.parseInt(parts[3]);
                    
                    processes.add(new Process(pid, arrivalTime, burstTime, priority));
                }
            }
        }
        
        System.out.println("Read " + processes.size() + " processes from " + filename);
    }
    
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            // print menu options
            System.out.println("\n====== Process Scheduling Simulator ======");
            System.out.println("1. Load processes from file");
            System.out.println("2. Display loaded processes");
            
            // dynamically add menu options for each algorithm
            for (int i = 0; i < algorithms.size(); i++) {
                System.out.println((3 + i) + ". Run " + algorithms.get(i).getName());
            }
            
            System.out.println((3 + algorithms.size()) + ". Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the leftover newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter filename: ");
                    String filename = scanner.nextLine();
                    try {
                        readProcessesFromFile(filename);
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;
                    
                case 2:
                    displayProcesses();
                    break;
                    
                default:
                    // handle algorithm selection or exit
                    if (choice >= 3 && choice < 3 + algorithms.size()) {
                        int algorithmIndex = choice - 3;
                        runSchedulingAlgorithm(algorithms.get(algorithmIndex));
                    } else if (choice == 3 + algorithms.size()) {
                        exit = true;
                    } else {
                        System.out.println("Invalid choice!");
                    }
            }
        }
        
        scanner.close();
    }
    
    private void displayProcesses() {
        // check if processes loaded
        if (processes.isEmpty()) {
            System.out.println("No processes loaded. Please load processes from a file first.");
            return;
        }
        
        // show the loaded processes in a table
        System.out.println("\nLoaded Processes:");
        System.out.println("PID\tArrival\tBurst\tPriority");
        System.out.println("---------------------------------");
        
        for (Process p : processes) {
            System.out.println(p.getPid() + "\t" + p.getArrivalTime() + "\t" + 
                              p.getBurstTime() + "\t" + p.getPriority());
        }
    }
    
    private void runSchedulingAlgorithm(SchedulingAlgorithm algorithm) {
        // make sure there is processes to schedule
        if (processes.isEmpty()) {
            System.out.println("No processes loaded. Please load processes from a file first.");
            return;
        }
        
        System.out.println("\nRunning " + algorithm.getName() + "...");
        
        // run the selected algorithm
        List<ExecutionEvent> timeline = algorithm.schedule(processes);
        
        // show the results visually and numerically
        displayGanttChart(timeline);
        displayProcessMetrics();
    }
    
    private void displayGanttChart(List<ExecutionEvent> timeline) {
        System.out.println("\nGantt Chart:");
        
        StringBuilder processBar = new StringBuilder("|");
        
        for (ExecutionEvent event : timeline) {
            String processLabel = " P" + event.getProcessId() + " ";
            processBar.append(processLabel).append("|");
        }
        
        System.out.println(processBar.toString());
        
        StringBuilder timeLine = new StringBuilder();
        timeLine.append("0");  
        
        int currentPos = 1;  // start after the first "|"
        
        for (ExecutionEvent event : timeline) {
            int targetPos = currentPos + (" P" + event.getProcessId() + " ").length();
        
            int spacesToAdd = targetPos - timeLine.length();
            
            // add the spaces
            for (int i = 0; i < spacesToAdd; i++) {
                timeLine.append(" ");
            }
            
            // add the end time
            timeLine.append(event.getEndTime());
            
            // update current position for next iteration
            currentPos = targetPos + 1;  // +1 for the "|"
        }
        
        // print the timeline
        System.out.println(timeLine.toString());
    }
    
    private void displayProcessMetrics() {
        System.out.println("\nProcess Metrics:");
        System.out.println("PID\tWaiting Time\tTurnaround Time");
        System.out.println("----------------------------------------");
        
        // calculate totals for the averages
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        
        // print individual process metrics
        for (Process p : processes) {
            System.out.println(p.getPid() + "\t" + p.getWaitingTime() + "\t\t" + p.getTurnaroundTime());
            totalWaitingTime += p.getWaitingTime();
            totalTurnaroundTime += p.getTurnaroundTime();
        }
        
        // calculate and print averages
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();
        
        System.out.println("\nAverage Waiting Time: " + String.format("%.2f", avgWaitingTime));
        System.out.println("Average Turnaround Time: " + String.format("%.2f", avgTurnaroundTime));
    }
    
    public static void main(String[] args) {
        ProcessScheduler scheduler = new ProcessScheduler();
        scheduler.displayMenu();
    }
}