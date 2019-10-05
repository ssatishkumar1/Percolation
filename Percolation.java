package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean percolates = false;
    private WeightedQuickUnionUF qu1;
    private WeightedQuickUnionUF qu2;
    private boolean[][] grid;
    private int numberOfSites;
    private int top;
    private int bottom;
    private int bottom2;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Incorrect Argument");
        }
        grid = new boolean[N][N];
        qu1 = new WeightedQuickUnionUF(N * N + 4);
        qu2 = new WeightedQuickUnionUF(N * N + 4);
        top = N * N;
        bottom = N * N + 1;
        bottom2 = N * N + 2;
        for (int i = 0; i < grid.length; i++) {
            qu1.union(i, top);
        }
        for (int i = 0; i < grid.length; i++) {
            int value = xyTo1D(grid.length - 1, i);
            qu2.union(value, bottom);
        }
    }

    public void open(int row, int col) {
        if (!check(row) && !check(col)) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        numberOfSites++;
        int[] neighbors = neighbors(row, col);
        for (int i = 0; i < 8; i += 2) {
            if (!(neighbors[i] == -1) || !(neighbors[i + 1] == -1)) {
                if (isOpen(neighbors[i], neighbors[i + 1])) {
                    qu1.union(xyTo1D(neighbors[i], neighbors[i + 1]), xyTo1D(row, col));
                    qu2.union(xyTo1D(neighbors[i], neighbors[i + 1]), xyTo1D(row, col));
                }
            }
        }
        if (qu1.connected(top, xyTo1D(row, col)) && qu2.connected(bottom, xyTo1D(row, col))) {
            percolates = true;
        }
    }

    public boolean isOpen(int row, int col) {
        if (!check(row) && !check(col)) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col)  {
        if (!check(row) && !check(col)) {
            throw new IndexOutOfBoundsException();
        }
        int value = xyTo1D(row, col);
        return ((qu1.connected(top, value)) && (grid[row][col]));
    }

    public int numberOfOpenSites() {
        return numberOfSites;
    }

    public boolean percolates() {
        return percolates;
    }

    private int xyTo1D(int r, int c) {
        return (grid.length * r) + c;
    }

    private boolean check(int N) {
        return (N >= 0 && N < grid.length);
    }
    public static void main(String[] args)  {
    }
    private int[] neighbors(int r, int c) {
        int[] neighbor = new int[8];
        if (r + 1 == grid.length) {
            neighbor[0] = -1;
            neighbor[1] = -1;
        } else {
            neighbor[0] = r + 1;
            neighbor[1] = c;
        }
        if (r - 1 == -1) {
            neighbor[2] = -1;
            neighbor[3] = -1;
        } else {
            neighbor[2] = r - 1;
            neighbor[3] = c;
        }
        if (c + 1 == grid.length) {
            neighbor[4] = -1;
            neighbor[5] = -1;
        } else {
            neighbor[4] = r;
            neighbor[5] = c + 1;
        }
        if (c - 1 == -1) {
            neighbor[6] = -1;
            neighbor[7] = -1;
        } else {
            neighbor[6] = r;
            neighbor[7] = c - 1;
        }
        return neighbor;
    }

}
