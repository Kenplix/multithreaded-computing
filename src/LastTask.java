import java.util.Arrays;

public class LastTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    LastTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor, int i) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        System.out.println("Last task started");
        int [][] MB = new int[Main.matrixDimension][Main.matrixDimension];

        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                MB[i][j] = 1;
            }
        }
        secondMonitor.writeMR(MB);
        secondMonitor.signalInput();

        firstMonitor.waitZ();

        int aL = -429496729;
        for (int i = Main.H * (Main.numberOfThreads - 1); i < Main.matrixDimension; i++) {
            if (Main.Z[i] > aL)
                aL = Main.Z[i];
        }
        firstMonitor.recordMax(aL);
        firstMonitor.signalA();

        Arrays.fill(Main.B, 1);
        secondMonitor.signalInput();

        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                Main.MO[i][j] = 1;
            }
        }
        secondMonitor.signalInput();

        secondMonitor.waitInput();
        int[][] MRn = secondMonitor.getMR();

        firstMonitor.waitA();
        aL = firstMonitor.getA();
        for (int i = Main.H * (Main.numberOfThreads - 1); i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MRn[i][k] * Main.MX[k][j];
                }
                Main.MA[i][j] = aL * (Main.MA[i][j] - Main.MX[i][j]);
            }
        }
        secondMonitor.signalMA();
        System.out.println("Last task finished");
    }
}
