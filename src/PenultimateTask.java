public class PenultimateTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    PenultimateTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        System.out.println("Penultimate task started");
        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                Main.MC[i][j] = 1;
            }
        }
        secondMonitor.signalInput();
        firstMonitor.waitZ();

        int aP = -429496729;
        for (int i = Main.H * (Main.numberOfThreads - 2); i < Main.H * (Main.numberOfThreads - 1); i++) {
            if (Main.Z[i] > aP)
                aP = Main.Z[i];
        }
        firstMonitor.max(aP);
        firstMonitor.signalA();

        secondMonitor.waitInput();
        int[][] MBP = secondMonitor.copyMB();

        firstMonitor.waitA();
        aP = firstMonitor.copyA();
        for (int i = Main.H * (Main.numberOfThreads - 2); i < Main.H * (Main.numberOfThreads - 1); i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MBP[i][k] * Main.MC[k][j];
                }
                Main.MA[i][j] = aP * (Main.MA[i][j] - Main.ME[i][j]);
            }
        }
        secondMonitor.signalMA();
        System.out.println("Penultimate task finished");
    }
}
