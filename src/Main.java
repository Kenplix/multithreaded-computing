public class Main {

    static int matrixDimension = 8000;
    static int numberOfThreads = 500;
    static final int H = matrixDimension / numberOfThreads;
    static int[] Z = new int[matrixDimension];
    static int[][] MA = new int[matrixDimension][matrixDimension];
    static int[][] MC = new int[matrixDimension][matrixDimension];
    static int[][] ME = new int[matrixDimension][matrixDimension];

    public static void main(String[] args) {
        FirstMonitor firstMonitor = new FirstMonitor(numberOfThreads);
        SecondMonitor secondMonitor = new SecondMonitor(numberOfThreads);

        FirstTask firstTask = new FirstTask(firstMonitor, secondMonitor);
        SecondTask secondTask = new SecondTask(firstMonitor, secondMonitor);
        PenultimateTask penultimateTask = new PenultimateTask(firstMonitor, secondMonitor);
        LastTask lastTask = new LastTask(firstMonitor, secondMonitor);

        firstTask.start();
        secondTask.start();
        penultimateTask.start();
        lastTask.start();

        for (int i = 3; i < numberOfThreads - 1; i++) {
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
