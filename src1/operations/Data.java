package operations;

public class Data {

    private int dimension;
    private int draw;

    public Data(int dimension, int draw) {
        this.dimension = dimension;
        this.draw = draw;
    }

    public void vectorInput(int value, int[] res) {
        for (int i = 0; i < dimension; i++)
            res[i] = value;
    }

    synchronized public void vectorPrint(int[] vector){
        System.out.println();
        if (vector.length <= draw) {
            for (int i = 0; i < dimension; i++)
                System.out.print(vector[i] + " ");
            System.out.println();
        } else
            System.out.println("vector[0] = " + vector[0]);
    }

    public void matrixInput(int value, int[][] res) {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                res[i][j] = value;
    }

    synchronized public void matrixPrint(int[][] matrix){
        System.out.println();
        if (matrix.length <= draw) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        } else
            System.out.println("matrix[0][0] = " + matrix[0][0]);
    }

    public int min(int start, int end, int[] vector) {
        int a = vector[start];
        for (int i = start + 1; i <= end; i++) {
            if (vector[i] < a)
                a = vector[i];
        }
        return a;
    }

    public int max(int start, int end, int[] vector) {
        int a = vector[start];
        for (int i = start + 1; i <= end; i++) {
            if (vector[i] > a)
                a = vector[i];
        }
        return a;
    }
}