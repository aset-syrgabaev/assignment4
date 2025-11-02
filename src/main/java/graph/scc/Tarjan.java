/**
 * Implements Tarjan's algorithm for finding strongly connected components.
 */
package graph.scc;

import graph.util.DiGraph;
import graph.util.Metrics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tarjan {
    private final DiGraph g;
    private final int n;
    private final int[] idx;
    private final int[] low;
    private final boolean[] inStack;
    private final Deque<Integer> st;
    private final List<List<Integer>> comps;
    private final int[] compId;
    private int time = 0;
    private int compCount = 0;
    private final Metrics m;

    public Tarjan(DiGraph g, Metrics m) {
        this.g = g;
        this.n = g.size();
        this.idx = new int[n];
        this.low = new int[n];
        this.inStack = new boolean[n];
        this.st = new ArrayDeque<>();
        this.comps = new ArrayList<>();
        this.compId = new int[n];
        this.m = m;
        for (int i = 0; i < n; i++) {
            idx[i] = -1;
        }
        for (int v = 0; v < n; v++) {
            if (idx[v] == -1) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        m.dfs++;
        idx[v] = time;
        low[v] = time;
        time++;
        st.push(v);
        inStack[v] = true;
        for (int to : g.adj(v)) {
            if (idx[to] == -1) {
                dfs(to);
                if (low[to] < low[v]) {
                    low[v] = low[to];
                }
            } else if (inStack[to]) {
                if (idx[to] < low[v]) {
                    low[v] = idx[to];
                }
            }
        }
        if (low[v] == idx[v]) {
            List<Integer> c = new ArrayList<>();
            while (true) {
                int u = st.pop();
                inStack[u] = false;
                c.add(u);
                compId[u] = compCount;
                if (u == v) break;
            }
            comps.add(c);
            compCount++;
        }
    }

    public List<List<Integer>> components() {
        return comps;
    }

    public int[] compId() {
        return compId;
    }

    public int compCount() {
        return compCount;
    }
}
