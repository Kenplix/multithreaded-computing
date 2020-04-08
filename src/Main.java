import operations.Data;

public class Main {

    static Data d;
    private static final int numberOfThreads = 30;
    static final int matrixDimension = 800;
    static final int border = matrixDimension / numberOfThreads;
    static int[] Z = new int[matrixDimension];
    static int[] C = new int[matrixDimension];
    static int[] B = new int[matrixDimension];
    static int[][] MA = new int[matrixDimension][matrixDimension];
    static int[][] MO = new int[matrixDimension][matrixDimension];
    static int[][] MX = new int[matrixDimension][matrixDimension];

    public static void main(String[] args) {
        d = new Data(matrixDimension);
        FirstMonitor firstMonitor = new FirstMonitor(numberOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(numberOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor);

        firstTask.start();
        lastTask.start();

        for (int n = 2; n < numberOfThreads; n++) {
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
    MA = a*MO + (B*C)(MR*MX)
     */
    static private int[][] calculateFunction(int a, int[][] MO, int[] B, int[] C, int[][] MR, int[][] MX) {
        return  d.matrixAdd(d.intMatrixMult(a, MO), d.intMatrixMult(d.vectorMult(B, C), d.matrixMult(MR, MX)));
    }

    static void boilerplateActions(FirstMonitor firstMonitor, SecondMonitor secondMonitor, boolean isIntermediate) {
        if (!isIntermediate)
            secondMonitor.signalInput();

        secondMonitor.waitInput();

        int[][] MR = secondMonitor.getMR();
        firstMonitor.waitA();
        int a = firstMonitor.getA();
        MA = calculateFunction(a, MO, B, C, MR, MX);
    }
}
