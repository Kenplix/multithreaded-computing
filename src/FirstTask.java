import java.util.Arrays;

public class FirstTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;
    private final int border;

    FirstTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor, int border) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
        this.border = border;
    }

    @Override
    public void run() {
        System.out.println("First task started");
        Main.Z = Main.data.vectorInput(1);
        firstMonitor.signalZ();

        int a1 = -429496729;
        for (int i = border - Main.H; i < border; i++) {
            if (Main.Z[i] > a1)
                a1 = Main.Z[i];
        }

        firstMonitor.recordMax(a1);
        firstMonitor.signalA();

        Main.C = Main.data.vectorInput(1);
        secondMonitor.signalInput();

        Main.MX = Main.data.matrixInput(1);
        secondMonitor.signalInput();

        secondMonitor.waitInput();

        int[][] MR1 = secondMonitor.getMR();

        firstMonitor.waitA();
        a1 = firstMonitor.getA();

        Main.MA = Main.calculateFunction(a1, Main.MO, Main.B, Main.C, MR1, Main.MX);
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
