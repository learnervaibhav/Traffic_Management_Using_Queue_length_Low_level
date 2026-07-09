# Summer Training Project Report

## Project Title
**Traffic Management Low Level Simulation**

## Introduction
Traffic congestion at signalized intersections is a common real-world problem that affects travel time, fuel consumption, and overall road efficiency. This project presents a simple Java-based traffic management simulation that models four lanes, dynamically generates vehicle arrivals, and selects the lane with the highest priority for the green signal.

The objective of the project is to demonstrate how a priority-based scheduling approach can be used to manage traffic flow in a controlled simulation environment. The project tracks queue length, waiting time, number of green signals received, and total vehicles passed for each lane.

The scope of the project includes:
- Simulating four traffic lanes: North, South, East, and West.
- Generating random traffic arrivals at each simulation step.
- Assigning the green signal to the lane with the highest priority.
- Tracking waiting time and performance statistics.
- Displaying a summary at the end of the simulation.

Technologies used:
- Java
- Object-Oriented Programming
- Priority Queue data structure
- Random number generation
- Console-based input and output

Data structures used:
- `ArrayList` to store lane objects
- `PriorityQueue` to select the lane with the highest priority
- Custom class objects to store lane statistics

## System Requirements

### Hardware
- Processor: Ryzen 3 7000 series
- RAM: 8 GB
- Storage: 512 GB

### Software
- Java JDK 23
- IDE: VS Code
- Git for version control

## Flow Chart

```text
Start
  ↓
Read Input
  ↓
Build Data Structure / Graph
  ↓
Process Algorithm
  ↓
Generate Output
  ↓
End
```

## Algorithm
1. Start the program.
2. Read the number of simulation steps from the user.
3. Initialize four lanes: North, South, East, and West.
4. Assign each lane an initial random queue length.
5. Create a statistics object to store simulation data.
6. For each simulation step:
	- Generate random arriving cars for every lane.
	- Update the queue length of each lane.
	- Calculate the priority of each lane using `queueLength + waitingTime`.
	- Select the lane with the highest priority as the green lane.
	- Set the green time based on the selected lane's queue length.
	- Process the green lane and reduce its queue.
	- Reset the waiting time of the green lane.
	- Increase the waiting time of all red lanes.
	- Display the status of all lanes.
	- Pause the simulation so output appears in real time.
7. After all steps are completed, calculate and print the final summary.
8. Display total cycles, vehicles processed, maximum queue seen, average waiting time, and lane-wise statistics.
9. End the program.

```

## Sample Input
```text
5
```

## Sample Output
```text
Enter number of simulation steps: 5
Simulation speed: 1 real second = 10 simulated seconds

=======================================
STEP 1
=======================================
North  Queue=12 Waiting= 0 Priority=12
South  Queue=18 Waiting=24 Priority=42
East   Queue=11 Waiting=24 Priority=35
West   Queue=15 Waiting=24 Priority=39

GREEN SIGNAL : South
GREEN TIME   : 34 sec
...
```

## Result
The implementation satisfies the objective of simulating traffic signal control using a priority-based approach. It generates random traffic, selects the most congested lane, tracks waiting time and throughput, and produces a final summary for analysis.

## Advantages
- Easy to understand and implement
- Demonstrates object-oriented programming concepts
- Uses priority-based scheduling for lane selection
- Tracks performance metrics such as queue length and waiting time
- Time complexity per simulation step is approximately `O(n log n)` due to priority queue usage, where `n` is the number of lanes

## Applications
- Smart traffic signal simulation
- Traffic flow analysis
- Academic demonstration of scheduling algorithms
- Basic prototype for adaptive signal systems

## Conclusion
This project helped in understanding traffic simulation, object-oriented design, and the use of data structures such as lists and priority queues. It also demonstrates how simple scheduling logic can be used to manage congestion in a controlled environment.

## Future Scope
- Add real-time traffic sensor integration
- Support more than four lanes and multiple junctions
- Use machine learning for adaptive signal timing
- Store simulation logs in files or databases
- Add graphical visualization of traffic flow

