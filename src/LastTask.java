public class LastTask extends Thread {

    private final FirstMonitor fM;
    private final SecondMonitor sM;

    LastTask(FirstMonitor fM, SecondMonitor sM) {
        this.fM = fM;
        this.sM = sM;
    }

    @Override
    public void run() {
        int end = Main.countOfElements - 1;
        int start = Main.countOfElements - Main.border;

        Main.data.vectorInput(1, Main.Q);
        fM.signalQ();

        fM.recordMinQ(Main.data.min(start, end, Main.Q));
        fM.signalB();

        int [][] MR = new int[Main.countOfElements][Main.countOfElements];
        Main.data.matrixInput(1, MR);
        sM.writeMR(MR);
        sM.signalInput();

        Main.data.matrixInput(1, Main.MO);
        sM.signalInput();

        fM.waitZ();
        fM.recordMaxZ(Main.data.max(start, end, Main.Z));
        fM.signalA();

        sM.waitInput();
        Main.calclateFunction(start, end, fM.getA(), fM.getB(), MR, sM);

        sM.signalMA();
        System.gc();
    }
}