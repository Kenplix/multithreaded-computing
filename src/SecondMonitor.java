class SecondMonitor {

    private int P;
    private int F1;
    private int F2;
    private int[][] MB;

    SecondMonitor(int P) {
        this.P = P;
    }

    synchronized void writeMB(int [][] MB) {
        this.MB = MB;
    }

    synchronized int[][] copyMB() {
        return MB;
    }

    synchronized void signalInput() {
        F1++;
        notify();
    }

    synchronized void signalMA() {
        F2++;
        notify();
    }

    synchronized void waitInput() {
        try {
            while (F1 != 3)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void waitMA() {
        try {
            while (F2 != P - 1)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
