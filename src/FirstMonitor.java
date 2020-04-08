class FirstMonitor {

    private int numberOfThreads;
    private int F1;
    private int F2;
    private int a = -2147483648; //min int value

    FirstMonitor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    synchronized void recordMax(int ai) {
        if (ai > a)
            a = ai;
    }

    synchronized int getA() {
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
            while (F1 != numberOfThreads)
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
