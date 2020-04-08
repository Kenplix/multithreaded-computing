public class LastTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    LastTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        int [][] MR = Main.d.matrixInput(1);
        secondMonitor.writeMR(MR);
        secondMonitor.signalInput();

        firstMonitor.waitZ();
        int an = Main.d.max(Main.matrixDimension - Main.border, Main.matrixDimension - 1, Main.Z);

        firstMonitor.recordMax(an);
        firstMonitor.signalA();

        Main.B = Main.d.vectorInput(1);
        secondMonitor.signalInput();

        Main.MO = Main.d.matrixInput(1);
        Main.boilerplateActions(firstMonitor, secondMonitor, false);
        secondMonitor.signalMA();
        System.gc();
    }
}
