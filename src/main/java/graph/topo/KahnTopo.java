/**
 * Performs topological sorting using Kahn's algorithm on a DAG.
 */
package graph.topo;

import graph.util.DiGraph;
import graph.util.Metrics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class KahnTopo {
    private final List<Integer> order;

    public KahnTopo(DiGraph g, Metrics m) {
        int n = g.size();
        int[] indeg = new int[n];
        for (int v = 0; v < n; v++) {
            for (int to : g.adj(v)) {
                indeg[to]++;
            }
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < n; v++) {
            if (indeg[v] == 0) {
                q.add(v);
                m.topoPush++;
            }
        }
        order = new ArrayList<>();
        while (!q.isEmpty()) {
            int v = q.remove();
            m.topoPop++;
            order.add(v);
            for (int to : g.adj(v)) {
                indeg[to]--;
                if (indeg[to] == 0) {
                    q.add(to);
                    m.topoPush++;
                }
            }
        }
    }

    public List<Integer> order() {
        return order;
    }
}
