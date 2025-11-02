/**
 * Computes single-source shortest paths in a DAG using topological order.
 */
package graph.dagsp;

import graph.topo.KahnTopo;
import graph.util.DiGraph;
import graph.util.Metrics;

import java.util.Arrays;
import java.util.List;

public class DagShortest {
    private final double[] dist;
    private final int[] prev;

    public DagShortest(DiGraph g, double[][] w, int src, Metrics m) {
        int n = g.size();
        this.dist = new double[n];
        this.prev = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);
        dist[src] = 0;
        List<Integer> ord = new KahnTopo(g, m).order();
        for (int v : ord) {
            if (dist[v] == Double.POSITIVE_INFINITY) continue;
            for (int to : g.adj(v)) {
                double nd = dist[v] + w[v][to];
                m.relax++;
                if (nd < dist[to]) {
                    dist[to] = nd;
                    prev[to] = v;
                }
            }
        }
    }

    public double[] dist() {
        return dist;
    }

    public int[] prev() {
        return prev;
    }
}
