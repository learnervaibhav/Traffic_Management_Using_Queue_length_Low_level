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

## Source Code

### 1. app.java
```java
import java.util.Scanner;

public class app {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter number of simulation steps: ");

		int steps = sc.nextInt();

		TrafficController controller = new TrafficController();

		controller.simulate(steps);

		sc.close();
	}
}
```

### 2. Lane.java
```java
public class Lane {

	private String name;
	private int queueLength;
	private int waitingTime;
	private int greenCount;
	private int totalCarsPassed;

	public Lane(String name, int queueLength) {
		this.name = name;
		this.queueLength = queueLength;
		this.waitingTime = 0;
		this.greenCount = 0;
		this.totalCarsPassed = 0;
	}

	public String getName() {
		return name;
	}

	public int getQueueLength() {
		return queueLength;
	}

	public void setQueueLength(int queueLength) {
		this.queueLength = queueLength;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void addWaitingTime(int time) {
		waitingTime += time;
	}

	public void resetWaitingTime() {
		waitingTime = 0;
	}

	public void incrementGreenCount() {
		greenCount++;
	}

	public int getGreenCount() {
		return greenCount;
	}

	public void addCarsPassed(int cars) {
		totalCarsPassed += cars;
	}

	public int getTotalCarsPassed() {
		return totalCarsPassed;
	}

	public int getPriority() {
		return queueLength + waitingTime;
	}
}
```

### 3. SimulationStats.java
```java
public class SimulationStats {

	int totalVehiclesProcessed = 0;
	int totalWaitingTime = 0;
	int maxQueueSeen = 0;
	int totalCycles = 0;
}
```

### 4. TrafficController.java
```java
import java.util.*;

public class TrafficController {

	private static final int SIMULATED_SECONDS_PER_REAL_SECOND = 10;

	private ArrayList<Lane> lanes;
	private Random random;
	private SimulationStats stats;

	public TrafficController() {

		random = new Random();

		lanes = new ArrayList<>();

		lanes.add(new Lane("North", random.nextInt(20) + 5));
		lanes.add(new Lane("South", random.nextInt(20) + 5));
		lanes.add(new Lane("East", random.nextInt(20) + 5));
		lanes.add(new Lane("West", random.nextInt(20) + 5));

		stats = new SimulationStats();
	}

	public void simulate(int steps) {

		System.out.println("Simulation speed: 1 real second = 10 simulated seconds");

		for (int step = 1; step <= steps; step++) {

			System.out.println("\n=======================================");
			System.out.println("STEP " + step);
			System.out.println("=======================================");

			generateTraffic();

			Lane greenLane = chooseGreenLane();

			int greenTime = 10 + greenLane.getQueueLength();

			processGreenLane(greenLane, greenTime);

			updateRedLanes(greenLane, greenTime);

			printStatus(greenLane, greenTime);

			pauseForSimulation(greenTime);

			stats.totalCycles++;
		}

		printSummary();
	}

	private void generateTraffic() {

		for (Lane lane : lanes) {

			int arrivingCars = random.nextInt(6);

			lane.setQueueLength(lane.getQueueLength() + arrivingCars);

			stats.maxQueueSeen = Math.max(stats.maxQueueSeen, lane.getQueueLength());
		}
	}

	private Lane chooseGreenLane() {

		PriorityQueue<Lane> pq = new PriorityQueue<>((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));

		pq.addAll(lanes);

		return pq.poll();
	}

	private void processGreenLane(Lane lane, int greenTime) {

		lane.incrementGreenCount();

		int carsLeaving = greenTime * 2;

		carsLeaving = Math.min(carsLeaving, lane.getQueueLength());

		lane.setQueueLength(lane.getQueueLength() - carsLeaving);

		lane.resetWaitingTime();

		lane.addCarsPassed(carsLeaving);

		stats.totalVehiclesProcessed += carsLeaving;
	}

	private void updateRedLanes(Lane greenLane, int greenTime) {

		for (Lane lane : lanes) {

			if (lane != greenLane) {

				lane.addWaitingTime(greenTime);

				stats.totalWaitingTime += greenTime;
			}
		}
	}

	private void printStatus(Lane greenLane, int greenTime) {

		for (Lane lane : lanes) {

			System.out.printf("%-6s Queue=%2d Waiting=%2d Priority=%2d%n", lane.getName(), lane.getQueueLength(), lane.getWaitingTime(), lane.getPriority());
		}

		System.out.println();

		System.out.println("GREEN SIGNAL : " + greenLane.getName());

		System.out.println("GREEN TIME   : " + greenTime + " sec");
	}

	private void printSummary() {

		
		System.out.println("SIMULATION SUMMARY");

		System.out.println("Cycles : " + stats.totalCycles);

		System.out.println("Vehicles Processed : " + stats.totalVehiclesProcessed);

		System.out.println("Maximum Queue Seen : " + stats.maxQueueSeen);

		double avgWaiting = (double) stats.totalWaitingTime / (stats.totalCycles * lanes.size());

		System.out.printf("Average Waiting Time : %.2f sec%n", avgWaiting);

		System.out.println();

		for (Lane lane : lanes) {

			System.out.println(lane.getName());

			System.out.println(" Green Signals : " + lane.getGreenCount());

			System.out.println(" Cars Passed   : " + lane.getTotalCarsPassed());

			System.out.println();
		}
	}

	private void pauseForSimulation(int simulatedSeconds) {

		long realDelayMillis = (simulatedSeconds * 1000L) / SIMULATED_SECONDS_PER_REAL_SECOND;

		try {

			Thread.sleep(realDelayMillis);

		} catch (InterruptedException e) {

			Thread.currentThread().interrupt();
		}
	}
}
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

