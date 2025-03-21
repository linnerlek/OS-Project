# Process Scheduling Simulator
This project implements a process scheduling simulator for Operating Systems *(CSC 4320, Spring 2025)*. The simulator demonstrates how an operating system schedules processes using different CPU scheduling algorithms.

## Features
- Reads process data from a text file
- Implements multiple scheduling algorithms:
    - **First-Come, First-Serve (FCFS)**
    - **Shortest Job First (SJF)**
- Displays a text-based Gantt chart for visualization
- Calculates and displays waiting time and turnaround time for each process
- Calculates average waiting time and average turnaround time

## How to Run the Program
1. Compile all Java files:
```
javac *.java
```

2. Run the main program:
```
java ProcessScheduler
```

3. Follow the interactive menu to:
    - Load process data from a file
    - View loaded processes
    - Run scheduling algorithms (FCFS and SJF)
    - View results

## Input file format
The program expects process data in a text file with the following format:
```
PID Arrival_Time Burst_Time Priority
1 0 5 2
2 2 3 1
...
```

## Sample Output
```
Gantt Chart:
| P1 | P4 | P3 | P6 | P8 | P7 | P2 | P5 | P10 | P9 |
0    5    6    8    10   12   15   18   22    26   32

Process Metrics:
PID	Waiting Time	Turnaround Time
----------------------------------------
1	0		5
2	13		16
3	2		4
4	0		1
5	12		16
6	1		3
7	4		7
8	1		3
9	15		21
10	10		14

Average Waiting Time: 5.80
Average Turnaround Time: 9.00
```