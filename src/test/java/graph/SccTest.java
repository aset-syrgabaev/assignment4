
package graph;

import graph.scc.Tarjan;
import graph.util.DiGraph;
import graph.util.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SccTest {
    @Test
    public void simpleCycle() {
        DiGraph g = new DiGraph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        Tarjan scc = new Tarjan(g, new Metrics());
        assertEquals(1, scc.compCount());
    }
}
