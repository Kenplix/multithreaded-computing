import java.util.Arrays;

public class FirstTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    FirstTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        System.out.println("First task started");
        Arrays.fill(Main.Z, 1);
        firstMonitor.signalZ();

        int a1 = -429496729;
        for (int i = 0; i < Main.H; i++) {
            if (Main.Z[i] > a1)
                a1 = Main.Z[i];
        }
        firstMonitor.max(a1);
        firstMonitor.signalA();

        secondMonitor.waitInput();
        int[][] MB1 = secondMonitor.copyMB();

        firstMonitor.waitA();
        a1 = firstMonitor.copyA();

        for (int i = 0; i < Main.H; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MB1[i][k] * Main.MC[k][j];
                }
                Main.MA[i][j] = a1 * (Main.MA[i][j] - Main.ME[i][j]);

//                if (Main.MA[i][j] == -1) {
//                    break;
//                }
            }
        }
        secondMonitor.waitMA();

        if (Main.matrixDimension <= 15) {
            for (int i = 0; i < Main.matrixDimension; i++) {
                for (int j = 0; j < Main.matrixDimension; j++) {
                    System.out.print(Main.MA[i][j] + " ");
                }
                System.out.println();
            }
        } else
            System.out.println("MA[0][0] = " + Main.MA[0][0]);
        System.out.println("First task finished");
    }
}
