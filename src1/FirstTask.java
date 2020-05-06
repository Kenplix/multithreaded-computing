public class FirstTask extends Thread {

    private final FirstMonitor fM;
    private final SecondMonitor sM;

    FirstTask(FirstMonitor fM, SecondMonitor sM) {
        this.fM = fM;
        this.sM = sM;
    }

    @Override
    public void run() {
        // long tmpStart = System.currentTimeMillis();
        int start = 0;
        int end = Main.border - 1;

        Main.data.vectorInput(1, Main.Z);
        fM.signalZ();

        fM.recordMaxZ(Main.data.max(start, end, Main.Z));
        fM.signalA();

        Main.data.matrixInput(1, Main.MC);
        sM.signalInput();

        Main.data.matrixInput(1, Main.MX);
        sM.signalInput();

        fM.waitQ();
        fM.recordMinQ(Main.data.min(start, end, Main.Q));
        fM.signalB();

        sM.waitInput();
        Main.calclateFunction(start, end, fM.getA(), fM.getB(), sM.getMR(), sM);

        sM.waitMA();
        Main.data.matrixPrint(Main.MA);
        // System.out.println("Stream 0 runtime: " + (System.currentTimeMillis() - tmpStart));
    }
}
