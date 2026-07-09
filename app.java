import java.util.Scanner;

public class app {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of simulation steps: ");

        int steps = sc.nextInt();

        TrafficController controller =
                new TrafficController();

        controller.simulate(steps);

        sc.close();
    }
}