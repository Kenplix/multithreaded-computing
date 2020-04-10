import operations.Data;

public class Main {

    static Data data;
    static int border;
    static int countOfElements;

    static int[] Z;
    static int[] Q;

    static int[][] MO;
    static int[][] MX;
    static int[][] MC;
    static int[][] MA;

    private static int[][] TEMP;

    public static void main(String[] args) {
        countOfElements = Integer.parseInt(args[0]);
        int countOfThreads = Integer.parseInt(args[1]);

        border = countOfElements / countOfThreads;
        if (border == 0 || countOfElements % countOfThreads != 0)
            throw new IllegalArgumentException("Unable to parallelize");

        data = new Data(countOfElements);
        Z = new int[countOfElements];
        Q = new int[countOfElements];

        MO = new int[countOfElements][countOfElements];
        MX = new int[countOfElements][countOfElements];
        MC = new int[countOfElements][countOfElements];
        MA = new int[countOfElements][countOfElements];

        TEMP = new int[countOfElements][countOfElements];

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
            for (int j = 0; j < Main.countOfElements; j++)
                for (int k = 0; k < Main.countOfElements; k++)
                    Main.TEMP[i][j] += MR[i][k] * Main.MX[k][j];

        secondMonitor.signalRX();
        secondMonitor.waitRX();

        for (int i = start; i <= end; i++)
            for (int j = 0; j < Main.countOfElements; j++) {
                for (int k = 0; k < Main.countOfElements; k++)
                    Main.MA[i][j] += Main.MC[i][k] * Main.TEMP[k][j];
                Main.MA[i][j] = a * Main.MO[i][j] + b * Main.MA[i][j];
            }
    }
}
