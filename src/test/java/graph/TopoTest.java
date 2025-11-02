package graph;

import graph.topo.KahnTopo;
import graph.util.DiGraph;
import graph.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TopoTest {
    @Test
    public void simpleDAG() {
        DiGraph g = new DiGraph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        KahnTopo topo = new KahnTopo(g, new Metrics());
        List<Integer> order = topo.order();
        assertEquals(4, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(3));
        assertTrue(order.indexOf(1) < order.indexOf(3));
    }
}
