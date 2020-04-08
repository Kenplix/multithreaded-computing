class FirstMonitor {

    private int P;
    private int F1;
    private int F2;
    private int a = -429496729;

    FirstMonitor(int P) {
        this.P = P;
    }

    synchronized void max(int ai) {
        if (ai > a)
            a = ai;
    }

    synchronized int copyA() {
        return a;
    }

    synchronized void signalA() {
        F1++;
        notify();
    }

    synchronized void signalZ() {
        F2++;
        notify();
    }

    synchronized void waitA() {
        try {
            while (F1 != P)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void waitZ() {
        try {
            while (F2 != 1)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


