
package graph.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonLoader {
    public static class Data {
        public final DiGraph graph;
        public final double[][] weights;
        public final int source;
        public final String weightModel;

        public Data(DiGraph graph, double[][] weights, int source, String weightModel) {
            this.graph = graph;
            this.weights = weights;
            this.source = source;
            this.weightModel = weightModel;
        }
    }

    public static Data load(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(path));
        int n = root.get("n").asInt();
        boolean directed = root.get("directed").asBoolean();
        DiGraph g = new DiGraph(n);
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (JsonNode e : root.get("edges")) {
            int u = e.get("u").asInt();
            int v = e.get("v").asInt();
            double ww = e.has("w") ? e.get("w").asDouble() : 1.0;
            g.addEdge(u, v);
            w[u][v] = ww;
            if (!directed) {
                g.addEdge(v, u);
                w[v][u] = ww;
            }
        }
        int src = root.has("source") ? root.get("source").asInt() : 0;
        String model = root.has("weight_model") ? root.get("weight_model").asText() : "edge";
        return new Data(g, w, src, model);
    }
}
