package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private double fraction;
    private double z = 1.96;
    private int N;
    private double T;
    private double[] fractions;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        this.T = T;
        this.N = N;
        fractions = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int one = StdRandom.uniform(N);
                int two = StdRandom.uniform(N);
                p.open(one, two);
            }
            fraction = (p.numberOfOpenSites() / ((double) (N * N)));
            fractions[i] = fraction;
        }
    }
    public double mean() {
        return StdStats.mean(fractions);
    }
    public double stddev() {
        return StdStats.stddev(fractions);
    }
    public double confidenceLow() {
        return (mean() - (z * (stddev() / Math.sqrt(T))));
    }
    public double confidenceHigh() {
        return (mean() + (z * (stddev() / Math.sqrt(T))));
    }
}
