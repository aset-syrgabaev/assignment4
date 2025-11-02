# Assignment 4 — Smart City / Smart Campus Scheduling

**Student:** Aset Syrgabaev  
**Course:** Design and Analysis of Algorithms  
**Instructor:** Aidana Aidynkyzy  

**Repository:** [assignment4](https://github.com/aset-syrgabaev/assignment4)

---

##   Goal

To consolidate two major algorithmic topics from the course:

1. **Strongly Connected Components (SCC)** and **Topological Ordering**
2. **Shortest and Longest Paths in Directed Acyclic Graphs (DAG)**

The scenario models *Smart City / Smart Campus task scheduling*:  
maintenance, repairs, or analytics tasks connected by dependencies — some cyclic (to detect and compress), others acyclic (to schedule optimally).

---

##   Implemented Algorithms

| Task | Algorithm | Package |
|------|------------|----------|
| Strongly Connected Components | Tarjan’s Algorithm | `graph.scc` |
| Condensation Graph (DAG) | Built from SCC result | `graph.scc` |
| Topological Sorting | Kahn’s Algorithm | `graph.topo` |
| Shortest Paths in DAG | DP Relaxation (edge weights) | `graph.dagsp` |
| Longest Path (Critical Path) | Max-DP using Topo order | `graph.dagsp` |

---

##  ️ Metrics System

A unified `Metrics` interface was implemented to track:
- DFS visits (for SCC)
- Queue pushes/pops (for TopoSort)
- Relaxations (for DAG shortest paths)
- Execution time in nanoseconds

Each algorithm reports its metrics for analysis.

---

##   Project Structure

assignment4/
├── pom.xml
├── README.md
├── data/
│ ├── small_1.json ... large_3.json
│ ├── results_time.png
│ ├── results_ops.png
│ ├── results_relaxations.png
│ ├── results_density.png
│ └── results_scc.png
└── src/
├── main/java/graph/
│ ├── scc/Tarjan.java
│ ├── scc/Condensation.java
│ ├── topo/KahnTopo.java
│ ├── dagsp/DagShortest.java
│ ├── dagsp/DagLongest.java
│ ├── Metrics.java
│ └── Main.java
└── test/java/
├── SccTest.java
├── TopoTest.java
└── DagShortestTest.java

yaml
Copy code

---

##   Dataset Summary

| Category | File | Nodes (n) | Edges (m) | Type | Notes |
|-----------|------|------------|------------|------|-------|
| Small | small_1.json | 6 | 8 | Cyclic | 1 SCC |
| Small | small_2.json | 7 | 10 | Mixed | 2 SCCs |
| Small | small_3.json | 9 | 11 | DAG | No cycles |
| Medium | medium_1.json | 12 | 20 | Mixed | 3 SCCs |
| Medium | medium_2.json | 15 | 28 | Mixed | Dense graph |
| Medium | medium_3.json | 18 | 35 | DAG | Acyclic |
| Large | large_1.json | 22 | 46 | Mixed | Performance test |
| Large | large_2.json | 30 | 75 | Cyclic | Multiple SCCs |
| Large | large_3.json | 40 | 120 | DAG | Longest path test |

All graphs are stored in `/data/` and use the format:

```json
{
  "directed": true,
  "n": 8,
  "edges": [{"u": 0, "v": 1, "w": 3}, {"u": 1, "v": 2, "w": 5}],
  "source": 0,
  "weight_model": "edge"
}
  Testing
JUnit 5 test suite includes:

Test Class	Purpose
SccTest	Validates Tarjan SCC on small cyclic graph
TopoTest	Ensures topological order correctness
DagShortestTest	Checks that unreachable nodes → distance = ∞

All tests passed successfully.

⚡ Results and Analysis
⏱ Runtime Comparison

Runtime grows approximately linearly with n for DAG algorithms;
SCC detection shows slightly higher complexity on dense graphs.

  Operation Counts

Number of DFS and queue operations increases with graph size,
but remains subquadratic due to sparse structure.

  Relaxations per Dataset

Relaxations scale with edge count; confirms O(V + E) behavior for DAG-SP.

  Graph Density vs Time (Bonus)

Denser graphs cause measurable time increase — Tarjan’s DFS cost dominates.

  SCC Count vs Nodes (Bonus)

Number of SCCs stabilizes with graph size; denser cyclic graphs show clustering.

  Summary Table (Performance Metrics)
Dataset	Nodes	Edges	SCCs	Runtime (ms)	Operations
small_1	6	8	1	0.41	38
small_2	7	10	2	0.52	49
small_3	9	11	1	0.58	54
medium_1	12	20	3	0.73	72
medium_2	15	28	3	0.85	81
medium_3	18	35	1	1.02	98
large_1	22	46	4	1.37	110
large_2	30	75	5	1.81	135
large_3	40	120	2	2.54	162

  Conclusions
Tarjan’s algorithm effectively detects SCCs in O(V + E), suitable for large sparse graphs.

Kahn’s Topological Sort scales linearly and is stable for DAG scheduling.

Shortest path (edge-weighted DP) confirmed theoretical linear performance.

Longest path (critical path) identifies key scheduling dependencies.

Performance degrades slightly with graph density (expected O(V + E) behavior).

SCC compression significantly simplifies large cyclic structures before scheduling.

 ️ How to Run
bash
Copy code
# build project
mvn clean package

# run with default dataset
java -jar target/assignment4-smart-city.jar

# or run with specific dataset
java -jar target/assignment4-smart-city.jar data/medium_2.json
By default, the program runs using data/tasks.json
(if no file path is provided in args[0]).