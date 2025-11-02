
package graph;

import graph.dagsp.DagLongest;
import graph.dagsp.DagShortest;
import graph.scc.Condensation;
import graph.scc.Tarjan;
import graph.topo.KahnTopo;
import graph.util.DiGraph;
import graph.util.JsonLoader;
import graph.util.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "data/tasks.json";
        Metrics m = new Metrics();
        long t0 = System.nanoTime();
        JsonLoader.Data data = JsonLoader.load(path);
        Tarjan scc = new Tarjan(data.graph, m);
        Condensation cd = new Condensation(data.graph, data.weights, scc);
        DiGraph dag = cd.getDag();
        double[][] wDag = cd.getWeights();
        KahnTopo topo = new KahnTopo(dag, m);
        int srcComp = scc.compId()[data.source];
        DagShortest sp = new DagShortest(dag, wDag, srcComp, m);
        DagLongest lp = new DagLongest(dag, wDag, srcComp, m);
        long t1 = System.nanoTime();
        m.timeNs = t1 - t0;
        System.out.println("scc:");
        int k = 0;
        for (var c : scc.components()) {
            System.out.println(k + ": " + c);
            k++;
        }
        System.out.println("topo: " + topo.order());
        System.out.println("shortest:");
        for (int v = 0; v < cd.getCompCount(); v++) {
            double d = sp.dist()[v];
            if (d == Double.POSITIVE_INFINITY) {
                System.out.println(v + " = inf");
            } else {
                System.out.println(v + " = " + d + " path=" + buildPath(srcComp, v, sp.prev()));
            }
        }
        System.out.println("longest:");
        for (int v = 0; v < cd.getCompCount(); v++) {
            double d = lp.dist()[v];
            if (d == Double.NEGATIVE_INFINITY) {
                System.out.println(v + " = -inf");
            } else {
                System.out.println(v + " = " + d + " path=" + buildPath(srcComp, v, lp.prev()));
            }
        }
        System.out.println("metrics.dfs=" + m.dfs);
        System.out.println("metrics.topoPush=" + m.topoPush);
        System.out.println("metrics.topoPop=" + m.topoPop);
        System.out.println("metrics.relax=" + m.relax);
        System.out.println("metrics.timeNs=" + m.timeNs);
    }

    private static List<Integer> buildPath(int src, int v, int[] prev) {
        List<Integer> r = new ArrayList<>();
        if (v == src && prev[v] == -1) {
            r.add(src);
            return r;
        }
        if (prev[v] == -1) {
            r.add(v);
            return r;
        }
        int cur = v;
        while (cur != -1) {
            r.add(0, cur);
            if (cur == src) break;
            cur = prev[cur];
        }
        return r;
    }
}
