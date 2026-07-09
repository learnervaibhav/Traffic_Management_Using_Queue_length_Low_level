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