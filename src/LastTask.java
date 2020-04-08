public class LastTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    LastTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        System.out.println("Last task started");
        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                Main.ME[i][j] = 1;
            }
        }
        secondMonitor.signalInput();
        firstMonitor.waitZ();

        int aL = -429496729;
        for (int i = Main.H * (Main.numberOfThreads - 1); i < Main.matrixDimension; i++) {
            if (Main.Z[i] > aL)
                aL = Main.Z[i];
        }
        firstMonitor.max(aL);
        firstMonitor.signalA();

        secondMonitor.waitInput();
        int[][] MBL = secondMonitor.copyMB();

        firstMonitor.waitA();
        aL = firstMonitor.copyA();
        for (int i = Main.H * (Main.numberOfThreads - 1); i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MBL[i][k] * Main.MC[k][j];
                }
                Main.MA[i][j] = aL * (Main.MA[i][j] - Main.ME[i][j]);
            }
        }
        secondMonitor.signalMA();
        System.out.println("Last task finished");
    }
}
