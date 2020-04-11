import operations.Data;

public class Main {

    static Data data;
    static int border;
    static int countOfElements;
    static int countOfThreads;

    static int[] Z;
    static int[] Q;

    static int[][] MO;
    static int[][] MX;
    static int[][] MC;
    static int[][] MA;

    private static int[][] TEMP;

    public static void main(String[] args) {
        countOfElements = Integer.parseInt(args[0]);
        countOfThreads = Integer.parseInt(args[1]);

        border = countOfElements / countOfThreads;
        if (border == 0 || countOfElements % countOfThreads != 0)
            throw new IllegalArgumentException("Unable to parallelize");

        data = new Data(countOfElements, 20);
        Z = new int[countOfElements];
        Q = new int[countOfElements];

        MO = new int[countOfElements][countOfElements];
        MX = new int[countOfElements][countOfElements];
        MC = new int[countOfElements][countOfElements];
        MA = new int[countOfElements][countOfElements];

        TEMP = new int[countOfElements][countOfElements];

        if (countOfThreads == 1) {
            singleThreadedImplementation();
            System.exit(0);
        }

        FirstMonitor firstMonitor = new FirstMonitor(countOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(countOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor);

        firstTask.start();
        lastTask.start();

        for (int n = 1; n < countOfThreads - 1; n++) {
            IntermediateTask task = new IntermediateTask(firstMonitor, secondMonitor, n);
            task.start();
        }

        try {
            firstTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    MA = max(Z) * MO + min(Q) * MC * (MR * MX) | a is max(Z) and b is min(Q)
    */
    static void calclateFunction(int start, int end, int a, int b, int[][] MR, SecondMonitor secondMonitor) {
        for (int i = start; i <= end; i++)
            for (int j = 0; j < countOfElements; j++)
                for (int k = 0; k < countOfElements; k++)
                    TEMP[i][j] += MR[i][k] * MX[k][j];

        secondMonitor.signalRX();
        secondMonitor.waitRX();

        for (int i = start; i <= end; i++)
            for (int j = 0; j < countOfElements; j++) {
                for (int k = 0; k < countOfElements; k++)
                    MA[i][j] += MC[i][k] * TEMP[k][j];
                MA[i][j] = a * MO[i][j] + b * MA[i][j];
            }
    }

    static void singleThreadedImplementation() {
        // long tmpStart = System.currentTimeMillis();
        int start = 0;
        int end = countOfElements - 1;

        data.vectorInput(1, Z);
        data.vectorInput(1, Q);

        data.matrixInput(1, MC);
        data.matrixInput(1, MX);
        data.matrixInput(1, MO);
        int [][] MR = new int[countOfElements][countOfElements];
        data.matrixInput(1, MR);

        int a = data.max(start, end, Z);
        int b = data.min(start, end, Q);

        for (int i = start; i <= end; i++)
            for (int j = 0; j < countOfElements; j++)
                for (int k = 0; k < countOfElements; k++)
                    TEMP[i][j] += MR[i][k] * MX[k][j];

        for (int i = start; i <= end; i++)
            for (int j = 0; j < countOfElements; j++) {
                for (int k = 0; k < countOfElements; k++)
                    MA[i][j] += MC[i][k] * TEMP[k][j];
                MA[i][j] = a * MO[i][j] + b * MA[i][j];
            }

        // data.matrixPrint(MA);
        // System.out.println("Stream 0 runtime: " + (System.currentTimeMillis() - tmpStart));
    }
}
