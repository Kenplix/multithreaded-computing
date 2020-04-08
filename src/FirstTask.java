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

        firstMonitor.recordMax(a1);
        firstMonitor.signalA();

        Arrays.fill(Main.C, 1);
        secondMonitor.signalInput();

        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                Main.MX[i][j] = 1;
            }
        }
        secondMonitor.signalInput();

        secondMonitor.waitInput();

        int[][] MR1 = secondMonitor.getMR();

        firstMonitor.waitA();
        a1 = firstMonitor.getA();
        // calculate function

        int BmC = 0;
        for (int i = 0; i < Main.H; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                BmC += Main.B[j] * Main.C[j];
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MO[i][j] = a1 * Main.MO[i][j];
                    Main.MA[i][j] += MR1[i][k] * Main.MX[k][j];
                }
                Main.MA[i][j] = a1 * Main.MO[i][j] + Main.MX[i][j]);
            }
        }

        secondMonitor.waitMA();
        // show result
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
