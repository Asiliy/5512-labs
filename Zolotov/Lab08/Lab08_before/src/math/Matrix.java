package lab08.math;

public interface Matrix {
    Matrix product(Matrix b);
    void setElement(int row, int column, int value);
    int getElement(int row, int collumn);
    int getRows();
    int getCols();
    String toString();
    boolean equals(Object obj);
}