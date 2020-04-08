public class IntermediateTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;
    private int n;

    IntermediateTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor, int n) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("Task " + n + " started");
        firstMonitor.waitZ();
        int ai = -429496729;
        for (int i = Main.H * (n - 1); i < Main.H * n; i++) {
            if (Main.Z[i] > ai)
                ai = Main.Z[i];
        }
        firstMonitor.recordMax(ai);
        firstMonitor.signalA();

        secondMonitor.waitInput();

        int[][] MRi = secondMonitor.getMR();

        firstMonitor.waitA();
        ai = firstMonitor.getA();

        for (int i = Main.H * (n - 1); i < Main.H * n; i++) {
            for (int j = 0; j < Main.matrixDimension; j++) {
                for (int k = 0; k < Main.matrixDimension; k++) {
                    Main.MA[i][j] += MRi[i][k] * Main.MX[k][j];
                }
                Main.MA[i][j] = ai * (Main.MA[i][j] - Main.MX[i][j]);
            }
        }
        secondMonitor.signalMA();
        System.out.println("Task " + n + " finished");
    }
}
