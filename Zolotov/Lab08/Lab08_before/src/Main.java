package lab08;

import lab08.math.Matrix;
import lab08.math.ParallelMatrixProduct;
import lab08.math.UsualMatrix;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int matrix_size = 1000;

        Random rand = new Random();

        UsualMatrix m = new UsualMatrix(matrix_size, matrix_size);

        for(int i = 0; i < matrix_size; i++) {
            for(int j = 0; j < matrix_size; j++) {
                m.setElement(i, j, Math.abs(rand.nextInt() % 10));
            }
        }

        System.out.print("Usual product... ");
        long simpleTime = System.nanoTime();
        Matrix result = m.product(m);
        simpleTime = System.nanoTime() - simpleTime;

        ParallelMatrixProduct product = new ParallelMatrixProduct();

        System.out.print("Parallel product... ");
        long parallelTime = System.nanoTime();
        Matrix parallelResult = product.parallelMatrixProduct(m, m);
        parallelTime = System.nanoTime() - parallelTime;

        double xFaster = simpleTime/(double)parallelTime;

        if(matrix_size < 20) {
            System.out.println("");
            System.out.println("M*M:");
            System.out.println(result.toString());

            System.out.println("");
            System.out.println("M*M (parallel):");
            System.out.println(parallelResult.toString());

            System.out.println("");
        }


        System.out.println("equals: " + result.equals(parallelResult));

        System.out.println("Usual time: " + simpleTime / 1000 + "ms");
        System.out.println("Parallel time:   " + parallelTime / 1000 + "ms");
        System.out.println("Parallel is " + String.format("%.2f", xFaster) + "x more fast.");
    }

}