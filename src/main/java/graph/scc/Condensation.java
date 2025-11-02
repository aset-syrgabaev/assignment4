
package graph.scc;

import graph.util.DiGraph;

public class Condensation {
    private final DiGraph dag;
    private final double[][] wDag;
    private final int compCount;

    public Condensation(DiGraph g, double[][] w, Tarjan scc) {
        this.compCount = scc.compCount();
        this.dag = new DiGraph(compCount);
        this.wDag = new double[compCount][compCount];
        for (int i = 0; i < compCount; i++) {
            for (int j = 0; j < compCount; j++) {
                wDag[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        int[] cid = scc.compId();
        for (int u = 0; u < g.size(); u++) {
            int cu = cid[u];
            for (int v : g.adj(u)) {
                int cv = cid[v];
                if (cu != cv) {
                    double ww = w[u][v];
                    if (ww < wDag[cu][cv]) {
                        dag.addEdge(cu, cv);
                        wDag[cu][cv] = ww;
                    }
                }
            }
        }
    }

    public DiGraph getDag() {
        return dag;
    }

    public double[][] getWeights() {
        return wDag;
    }

    public int getCompCount() {
        return compCount;
    }
}
