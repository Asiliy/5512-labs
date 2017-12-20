package lab08.math;

public class UsualMatrix implements Matrix {

    private final int     rows;
    private final int     cols;
    private int[][] matrix;

    public UsualMatrix(int rows, int cols) {
        this.rows   = rows;
        this.cols   = cols;
        createMatrix();
    }


    public Matrix product (Matrix b) {
        if(this.getRows() != b.getCols()) {
        }
        Matrix result = new UsualMatrix(this.getRows(), b.getCols());
        for (int row = 0; row < this.getRows(); row++) {
            for (int col = 0; col < b.getCols(); col++) {
                int temp = 0;
                for(int inner = 0; inner < b.getRows(); inner++) {
                    temp += getElement(row, inner) * b.getElement(inner, col);
                }
                result.setElement(row, col, temp);
            }
        }

        return result;
    }


    public void setElement (int row, int column, int value) {
        matrix[row][column] = value;
    }

    public int getElement (int row, int collumn) {
        return matrix[row][collumn];
    }

    public final int getRows() {
        return rows;
    }

    public final int getCols() {
        return cols;
    }

    public final String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                str.append(getElement(i, j)).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj == this) {
            return true;
        }

        if(obj.getClass() != this.getClass()) {
            return false;
        }

        Matrix b = (Matrix) obj;
        if(this.getRows() != b.getRows() || this.getCols() != b.getCols()) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(getElement(i, j) != b.getElement(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void createMatrix() {
        this.matrix = new int[rows][cols];
    }


}
