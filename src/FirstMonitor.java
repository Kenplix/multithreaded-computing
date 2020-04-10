class FirstMonitor {

    private int numberOfThreads;
    private int F1;
    private int F2;
    private int F3;
    private int F4;
    private int a = -2147483648; // min int value
    private int b = 2147483647; // max int value

    FirstMonitor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    synchronized void recordMaxZ(int ai) {
        if (ai > a)
            a = ai;
    }

    synchronized int getA() {
        return a;
    }

    synchronized void recordMinQ(int bi) {
        if (bi < b)
            b = bi;
    }

    synchronized int getB() {
        return b;
    }

    synchronized void signalA() {
        F1++;
        notify();
    }

    synchronized void signalB() {
        F2++;
        notify();
    }

    synchronized void signalZ() {
        F3++;
        notify();
    }

    synchronized void signalQ() {
        F4++;
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

    synchronized void waitB() {
        try {
            while (F2 != numberOfThreads)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void waitZ() {
        try {
            while (F3 != 1)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void waitQ() {
        try {
            while (F4 != 1)
                wait();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
