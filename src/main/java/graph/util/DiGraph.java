
package graph.util;

import java.util.ArrayList;
import java.util.List;

public class DiGraph {
    private final int n;
    private final List<List<Integer>> adj;

    public DiGraph(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<Integer> adj(int v) {
        return adj.get(v);
    }

    public int size() {
        return n;
    }
}
