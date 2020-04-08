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
        firstMonitor.waitZ();
        int ai = Main.d.max((n - 1) * Main.border, n * Main.border - 1, Main.Z);
        firstMonitor.recordMax(ai);
        firstMonitor.signalA();

        Main.boilerplateActions(firstMonitor, secondMonitor, true);
        secondMonitor.signalMA();
        System.gc();
    }
}
