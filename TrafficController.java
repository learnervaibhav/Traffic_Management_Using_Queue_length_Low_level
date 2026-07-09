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

            lane.setQueueLength(
                    lane.getQueueLength() + arrivingCars);

            stats.maxQueueSeen = Math.max(
                    stats.maxQueueSeen,
                    lane.getQueueLength());
        }
    }

    private Lane chooseGreenLane() {

        PriorityQueue<Lane> pq =
                new PriorityQueue<>(
                        (a, b) -> b.getPriority() - a.getPriority());

        pq.addAll(lanes);

        return pq.poll();
    }

    private void processGreenLane(Lane lane, int greenTime) {

        lane.incrementGreenCount();

        int carsLeaving = greenTime * 2;

        carsLeaving = Math.min(carsLeaving,
                lane.getQueueLength());

        lane.setQueueLength(
                lane.getQueueLength() - carsLeaving);

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

            System.out.printf(
                    "%-6s Queue=%2d Waiting=%2d Priority=%2d%n",
                    lane.getName(),
                    lane.getQueueLength(),
                    lane.getWaitingTime(),
                    lane.getPriority());
        }

        System.out.println();

        System.out.println("GREEN SIGNAL : " + greenLane.getName());

        System.out.println("GREEN TIME   : " + greenTime + " sec");
    }

    private void printSummary() {

        System.out.println("\n====================================");
        System.out.println("SIMULATION SUMMARY");
        System.out.println("====================================");

        System.out.println("Cycles : " + stats.totalCycles);

        System.out.println("Vehicles Processed : "
                + stats.totalVehiclesProcessed);

        System.out.println("Maximum Queue Seen : "
                + stats.maxQueueSeen);

        double avgWaiting =
                (double) stats.totalWaitingTime /
                        (stats.totalCycles * lanes.size());

        System.out.printf("Average Waiting Time : %.2f sec%n",
                avgWaiting);

        System.out.println();

        for (Lane lane : lanes) {

            System.out.println(
                    lane.getName());

            System.out.println(
                    " Green Signals : " +
                            lane.getGreenCount());

            System.out.println(
                    " Cars Passed   : " +
                            lane.getTotalCarsPassed());

            System.out.println();
        }
    }

    private void pauseForSimulation(int simulatedSeconds) {

        long realDelayMillis =
                (simulatedSeconds * 1000L) /
                        SIMULATED_SECONDS_PER_REAL_SECOND;

        try {

            Thread.sleep(realDelayMillis);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}