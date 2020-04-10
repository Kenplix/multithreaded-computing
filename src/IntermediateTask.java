public class IntermediateTask extends Thread {

    private final FirstMonitor fM;
    private final SecondMonitor sM;
    private int n;

    IntermediateTask(FirstMonitor fM, SecondMonitor sM, int n) {
        this.fM = fM;
        this.sM = sM;
        this.n = n;
    }

    @Override
    public void run() {
        int start = n * Main.border;
        int end = (n + 1) * Main.border - 1;

        fM.waitZ();
        fM.recordMaxZ(Main.data.max(start, end - 1, Main.Z));
        fM.signalA();

        fM.waitQ();
        fM.recordMinQ(Main.data.min(start, end, Main.Q));
        fM.signalB();

        sM.waitInput();
        Main.calclateFunction(start, end, fM.getA(), fM.getB(), sM.getMR(), sM);

        sM.signalMA();
        System.gc();
    }
}