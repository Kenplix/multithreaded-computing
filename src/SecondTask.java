public class SecondTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    public SecondTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        System.out.println("Second task started");
        int [][] MB = new int[Main.matrixDimension][Main.matrixDimension];

        for (int i = 0; i < Main.matrixDimension; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                MB[i][j] = 1;
            }
        }

        secondMonitor.writeMB(MB);
        secondMonitor.signalInput();

        firstMonitor.waitZ();
        int a2 = -429496729;
        for (int i = Main.H; i < Main.H * 2; i++) {
            if (Main.Z[i] > a2)
                a2 = Main.Z[i];
        }
        firstMonitor.max(a2);
        firstMonitor.signalA();

        int[][] MB2 = secondMonitor.copyMB();
        firstMonitor.waitA();
        a2 = firstMonitor.copyA();
        secondMonitor.waitInput();
        for (int i = Main.H; i < Main.H * 2; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MB2[i][k] * Main.MC[k][j];
                }
                Main.MA[i][j] = a2 * (Main.MA[i][j] - Main.ME[i][j]);
            }
        }
        secondMonitor.signalMA();
        System.out.println("Second task finished");
    }
}
