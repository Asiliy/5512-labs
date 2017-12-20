package lab08.math;

import java.util.ArrayList;
import java.util.List;

public class ParallelMatrixProduct {
    public UsualMatrix parallelMatrixProduct(final UsualMatrix a, final UsualMatrix b) {
        UsualMatrix result = new UsualMatrix(a.getRows(), b.getCols());
        int numThreads = 4;
        List<ProductThread> threads = new ArrayList<>(numThreads);
        int step = a.getRows() / numThreads;
        for(int i = 0; i < a.getRows(); i += step) {
            int from_row = i;
            int to_row = i + step;
            if(to_row >= a.getRows()) {
                to_row = a.getRows();
            }
            threads.add(new ProductThread(from_row, to_row, a, b, result));
        }
        threads.forEach(Thread::start);
        try {
            for(Thread thr: threads) {
                thr.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    private class ProductThread extends Thread {
        int from_row, to_row;
        UsualMatrix a, b, result;

        ProductThread(int from_row, int to_row, UsualMatrix a, UsualMatrix b, UsualMatrix result) {
            this.from_row = from_row;
            this.to_row = to_row;
            this.result = result;
            this.a = a;
            this.b = b;
        }

        public void run() {
            for(int row = from_row; row < to_row; row++) {
                for (int col = 0; col < b.getCols(); col++) {
                    int temp = 0;
                    for (int inner = 0; inner < b.getRows(); inner++) {
                        temp += a.getElement(row, inner) * b.getElement(inner, col);
                    }
                    result.setElement(row, col, temp);
                }
            }
        }
    }

}

