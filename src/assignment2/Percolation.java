package assignment2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] sites;
    private final WeightedQuickUnionUF unionUF_TopBottom;
    private final WeightedQuickUnionUF unionUF_Top;
    private final int n;
    private int openSiteNum = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        sites = new boolean[n * n];
        unionUF_TopBottom = new WeightedQuickUnionUF(n * n + 2);
        unionUF_Top = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n; i++) {
            unionUF_TopBottom.union(n * n, i);
            unionUF_TopBottom.union(n * n + 1, n * n - i - 1);
            unionUF_Top.union(n * n, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 ||  col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException();
        }

        int cur = getIndex(row, col);

        if (!isOpen(row, col)) {
            sites[cur] = true;
            openSiteNum++;
        }

        if (row > 1 && isOpen(row - 1, col)) {
            unionUF_TopBottom.union(cur, cur - n);
            unionUF_Top.union(cur, cur - n);
        }

        if (row < n && isOpen(row + 1, col)) {
            unionUF_TopBottom.union(cur, cur + n);
            unionUF_Top.union(cur, cur + n);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            unionUF_TopBottom.union(cur, cur - 1);
            unionUF_Top.union(cur, cur - 1);
        }

        if (col < n && isOpen(row, col + 1)) {
            unionUF_TopBottom.union(cur, cur + 1);
            unionUF_Top.union(cur, cur + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 ||  col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException();
        }

        return sites[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 ||  col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException();
        }

        return (isOpen(row, col)) && (unionUF_Top.find(n * n) == unionUF_Top.find(getIndex(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }

        return unionUF_TopBottom.find(n * n) == unionUF_TopBottom.find(n * n + 1);
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);

        assert (!percolation.isFull(3, 1));
    }
}