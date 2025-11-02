package graph;

import graph.dagsp.DagShortest;
import graph.util.DiGraph;
import graph.util.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DagShortestTest {
    @Test
    public void unreachableVertex() {
        DiGraph g = new DiGraph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        double[][] w = new double[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                w[i][j] = Double.POSITIVE_INFINITY;
        w[0][1] = 1;
        w[1][2] = 2;
        DagShortest sp = new DagShortest(g, w, 0, new Metrics());
        assertEquals(0.0, sp.dist()[0]);
        assertEquals(1.0, sp.dist()[1]);
        assertEquals(3.0, sp.dist()[2]);
        assertEquals(Double.POSITIVE_INFINITY, sp.dist()[3]);
    }
}
