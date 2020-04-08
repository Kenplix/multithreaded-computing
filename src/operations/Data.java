package operations;
import java.util.Arrays;
import java.util.Scanner;

public class Data {
    private int dimension;

    public int getDimension() {
        return dimension;
    }

    public Data(int dimension) {
        this.dimension = dimension;
    }

    public int[] vectorInput() {
        int[] vector = new int[dimension];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < dimension; i++){
            vector[i] = sc.nextInt();
        }
        return vector;
    }

    public int[] vectorInput(int value) {
        int[] vector = new int[dimension];
        Arrays.fill(vector, value);
        return vector;
    }

    synchronized public void vectorPrint(int [] vector){
        System.out.println();
        if (vector.length <= 15) {
            for (int i = 0; i < dimension; i++) {
                System.out.print(vector[i] + " ");
            }
            System.out.println();
        } else
            System.out.println("vector[0] = " + vector[0]);
    }

    public int[][] matrixInput() {
        int[][] matrix = new int[dimension][dimension];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension;
            j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }

    public int[][] matrixInput(int value) {
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            matrix[i] = vectorInput(value);
        }
        return matrix;
    }

    synchronized public void matrixPrint(int [][] matrix){
        System.out.println();
        if (matrix.length <= 15) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension;
                     j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        } else
            System.out.println("matrix[0][0] = " + matrix[0][0]);
    }

    public int[] vectorAdd(int[] a, int[] b) {
        if (a.length != dimension || b.length != dimension)
            throw new IllegalArgumentException();

        int[] c = new int[dimension];
        for (int i = 0; i < dimension; i++){
            c[i] = a[i] + b[i];
        }
        return c;
    }

    public int[] vectorDiff(int[] a, int[] b) {
        if (a.length != dimension || b.length != dimension)
            throw new IllegalArgumentException();

        int[] c = new int[dimension];
        for (int i = 0; i < dimension; i++){
            c[i] = a[i] - b[i];
        }
        return c;
    }

    public int[][] matrixTrans(int[][] matrix) {
        int buf;
        for (int i = 0; i < matrix.length ; i++){
            for (int j = 0; j <=i; j++){
                buf = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = buf;
            }
        }
        return matrix;
    }

    public int[] vectorMatrixMult(int[] a, int[][] matrix) {
        if (a.length != dimension || matrix.length != dimension)
            throw new IllegalArgumentException();

        int[] vector = new int[dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                vector[i] += a[j] * matrix[j][i];
            }
        }
        return vector;
    }

    public int vectorMult(int[] a, int[] b) {
        if (b.length != dimension || a.length != dimension)
            throw new IllegalArgumentException();

        int scalar = 0;
        for (int i = 0; i < dimension; i++){
            scalar += a[i] * b[i];
        }
        return scalar;
    }

    public int[][] matrixMult(int[][] ma, int[][] mb) {
        if (ma.length != dimension || mb.length != dimension)
            throw new IllegalArgumentException();

        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                for (int k = 0; k < dimension; k++){
                    matrix[i][j] += ma[i][k] * mb[k][j];
                }
            }
        }
        return matrix;
    }

    public int[][] matrixAdd(int[][] ma, int[][] mb) {
        if (ma.length != mb.length)
            throw new IllegalArgumentException();

        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                matrix[i][j] = ma[i][j] + mb[i][j];
            }
        }
        return matrix;
    }

    public int[][] matrixDiff(int[][] ma, int[][] mb) {
        if (ma.length != mb.length)
            throw new IllegalArgumentException();

        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                matrix[i][j] = ma[i][j] - mb[i][j];
            }
        }
        return matrix;
    }

    public int[] intVectorMult(int a, int[] b) {
        int[] vector = new int[dimension];
        for (int i = 0; i < dimension; i++){
            vector[i] = a * b[i];
        }
        return vector;
    }

    public int[][] intMatrixMult(int a, int[][] b) {
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = a * b[i][j];
            }
        }
        return matrix;
    }

    public int min(int[] vector) {
        int a = 2147483647;
        for (int value : vector) {
            if (value < a)
                a = value;
        }
        return a;
    }

    public int min(int start, int end, int[] vector) {
        int a = 2147483647;
        if (end == 0)
            end = start + 1;

        for (int i = start; i < end; i++) {
            if (vector[i] < a)
                a = vector[i];
        }
        return a;
    }

    public int max(int[] vector) {
        int a = -2147483648;
        for (int value : vector) {
            if (value > a)
                a = value;
        }
        return a;
    }

    public int max(int start, int end, int[] vector) {
        int a = -2147483648;
        if (end == 0)
            end = start + 1;

        for (int i = start; i < end; i++) {
            if (vector[i] > a)
                a = vector[i];
        }
        return a;
    }

    public int[] vectorSort(int[] a) {
        if (a.length != dimension)
            throw new IllegalArgumentException();

        int[] vector = Arrays.copyOf(a, dimension);
        Arrays.sort(vector);
        return vector;
    }
}