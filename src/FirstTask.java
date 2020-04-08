public class FirstTask extends Thread {

    private final FirstMonitor firstMonitor;
    private final SecondMonitor secondMonitor;

    FirstTask(FirstMonitor firstMonitor, SecondMonitor secondMonitor) {
        this.firstMonitor = firstMonitor;
        this.secondMonitor = secondMonitor;
    }

    @Override
    public void run() {
        Main.Z = Main.d.vectorInput(1);
        firstMonitor.signalZ();
        int a1 = Main.d.max(0, Main.border, Main.Z);
        firstMonitor.recordMax(a1);
        firstMonitor.signalA();

        Main.C = Main.d.vectorInput(1);
        secondMonitor.signalInput();

        Main.MX = Main.d.matrixInput(1);
        Main.boilerplateActions(firstMonitor, secondMonitor, false);
        secondMonitor.waitMA();

        Main.d.matrixPrint(Main.MA);
    }
}
