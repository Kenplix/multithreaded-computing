import operations.Data;

public class Main {

    static Data data;
    static final int countOfElements = 1000;
    private static final int numberOfThreads = 1000;
    static final int border = countOfElements / numberOfThreads;

    static int[] Z = new int[countOfElements];
    static int[] Q = new int[countOfElements];

    static int[][] MO = new int[countOfElements][countOfElements];
    static int[][] MX = new int[countOfElements][countOfElements];
    static int[][] MC = new int[countOfElements][countOfElements];
    static int[][] MA = new int[countOfElements][countOfElements];

    private static int[][] TEMP = new int[countOfElements][countOfElements];

    public static void main(String[] args) {
        if (border == 0 || countOfElements % numberOfThreads != 0)
            throw new IllegalArgumentException("Unable to parallelize");

        data = new Data(countOfElements);
        FirstMonitor firstMonitor = new FirstMonitor(numberOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(numberOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor);

        firstTask.start();
        lastTask.start();

        for (int n = 1; n < numberOfThreads - 1; n++) {
            IntermediateTask task = new IntermediateTask(firstMonitor, secondMonitor, n);
            task.start();
        }

        try {
            firstTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
