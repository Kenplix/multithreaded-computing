import operations.Data;

public class Main {

    static Data data;
    static int matrixDimension = 800;
    static int numberOfThreads = 500;
    static final int H =  (int) (matrixDimension / numberOfThreads);
    static int[] Z = new int[matrixDimension];
    static int[] C = new int[matrixDimension];
    static int[] B = new int[matrixDimension];
    static int[][] MA = new int[matrixDimension][matrixDimension];
    static int[][] MO = new int[matrixDimension][matrixDimension];
    static int[][] MX = new int[matrixDimension][matrixDimension];

    public static void main(String[] args) {
        data = new Data(matrixDimension);

        FirstMonitor firstMonitor = new FirstMonitor(numberOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(numberOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor, H);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor, numberOfThreads - H);

        firstTask.start();
        lastTask.start();

        for (int i = 2; i < numberOfThreads; i++) {
            IntermediateTask task = new IntermediateTask(firstMonitor, secondMonitor, i);
            task.start();
        }

        try {
            firstTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static int[][] calculateFunction(int a, int[][] MO, int[] B, int[] C, int[][] MR, int[][] MX) {
        int[][] first = data.intMatrixMult(a, MO);
        int second = data.vectorMult(B, C);
        int[][] third = data.matrixMult(MR, MX);
        int[][] fourth = data.intMatrixMult(second, third);
        return  data.matrixAdd(first, fourth);
    }
}
