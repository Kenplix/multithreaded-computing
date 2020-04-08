public class Main {

    static int matrixDimension = 800;
    static int numberOfThreads = 500;
    static final int H = matrixDimension / numberOfThreads;
    static int[] Z = new int[matrixDimension];
    static int[] C = new int[matrixDimension];
    static int[] B = new int[matrixDimension];
    static int[][] MA = new int[matrixDimension][matrixDimension];
    static int[][] MO = new int[matrixDimension][matrixDimension];
    static int[][] MX = new int[matrixDimension][matrixDimension];

    public static void main(String[] args) {
        FirstMonitor firstMonitor = new FirstMonitor(numberOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(numberOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor);

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
}
