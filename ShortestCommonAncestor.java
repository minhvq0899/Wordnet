import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class ShortestCommonAncestor {

    private final Digraph G;
    private boolean[] marked;
    private boolean isCycle = false;
    private boolean isRooted = false;
    private int visited;
    private int root;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G){
        if (G == null) throw new IllegalArgumentException("Argument digraph is null");
        this.G = G;
        RootedDAG(G);
        if (!isRooted()) throw new IllegalArgumentException("Not rooted");
        if (isCycle()) throw new IllegalArgumentException("Has cycle");
    }

    private void RootedDAG(Digraph g) {
        Digraph G = g.reverse();
        for (int i = 0; i < G.V(); i++) {
            visited = 0;
            marked = new boolean[G.V()];
            dfs(G, i, i);
            if (visited == G.V()) {
                isRooted = true;
                root = i;
            }
        }
    }


    private void dfs(Digraph g, int curr, int orig) {
        visited++;
        marked[curr] = true;
        for (int w : g.adj(curr)) {
            if (w == orig) {
                isCycle = true;
            } else if (!marked[w]) {
                dfs(g, w, orig);
            }
        }
    }

    private boolean isCycle() {
        return isCycle;
    }

    private boolean isRooted() {
        return isRooted;
    }


    // helper method to find a shortest path between 2 vertices
    private int helperVertices(int v, int w, String s){
        // we need 2 to measure the distance between v to ancestor and w to ancestor
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.G, w);
        // set distance to positive infinity
        int minDis = Integer.MAX_VALUE;
        // set ancestor
        int realAncestor = -1;
        Queue<Integer> queue = new Queue<Integer>(); // a queue for BFS
        // to start out, we haven't visited any synset yet
        boolean[] visited = new boolean[this.G.V()];
        for (int i = 0; i<this.G.V(); i++ ) {
            visited[i] = false;
        }
        queue.enqueue(v); // we will start BFS from vertex v
        visited[v] = true;
        int disVtoAncestor; // this stores the value distance from v to ancestor
        int disWtoAncestor; // this stores the value distance from w to ancestor

        while (!queue.isEmpty()) {
            int potentialAncestor = queue.dequeue();
            if (bfdpV.hasPathTo(potentialAncestor) && bfdpW.hasPathTo(potentialAncestor)) {
                // compute the distance between v,w to potentialAncestor
                disVtoAncestor = bfdpV.distTo(potentialAncestor);
                disWtoAncestor = bfdpW.distTo(potentialAncestor);
                // find the min distance
                if (disVtoAncestor + disWtoAncestor < minDis) {
                    minDis = disVtoAncestor + disWtoAncestor;
                    realAncestor = potentialAncestor;
                }
            }
            // add all the neighbors of potentialAncestor into queue
            for (int neighbor: this.G.adj(potentialAncestor)){
                if (!visited[neighbor]) {
                    queue.enqueue(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        if(s.equals("length")){
            return minDis;
        } else {
            return realAncestor;
        }
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w){
        if (v < 0 || this.G.V() <= v || w < 0 || this.G.V() <= w) {
            throw new IllegalArgumentException("Argument vertex is out of bounds");
        }
        return helperVertices(v,w,"length");
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w){
        if (v < 0 || this.G.V() <= v || w < 0 || this.G.V() <= w) {
            throw new IllegalArgumentException("Argument vertex is out of bounds");
        }
        return helperVertices(v,w,"ancestor");
    }

    // helper method to find a shortest path between 2 sets of vertices
    private int helperSetVertices(Iterable<Integer> subsetA, Iterable<Integer> subsetB, String s){
        // we need 2 to measure the distance between v to ancestor and w to ancestor
        BreadthFirstDirectedPaths bfdpA = new BreadthFirstDirectedPaths(this.G, subsetA);
        BreadthFirstDirectedPaths bfdpB = new BreadthFirstDirectedPaths(this.G, subsetB);
        // set distance to positive infinity
        int minDis = Integer.MAX_VALUE;
        // set ancestor
        int realAncestor = -1;
        Queue<Integer> queue = new Queue<Integer>(); // a queue for BFS
        // to start out, we haven't visited any synset yet
        boolean[] visited = new boolean[this.G.V()];
        for (int i = 0; i<this.G.V(); i++ ) {
            visited[i] = false;
        }
        for (int i: subsetB) {
            queue.enqueue(i);
            visited[i] = true;
        }
        int disAtoAncestor; // this stores the value distance from subsetA to ancestor
        int disBtoAncestor; // this stores the value distance from subsetB to ancestor

        while (!queue.isEmpty()) {
            int potentialAncestor = queue.dequeue();
            if (bfdpA.hasPathTo(potentialAncestor)) { // here we don't have to check if there exists a path from subset B to potentialAncestor
                // compute the shortest distance between A, B to potentialAncestor
                disAtoAncestor = bfdpA.distTo(potentialAncestor);
                disBtoAncestor = bfdpB.distTo(potentialAncestor);
                // find the min distance
                if (disAtoAncestor + disBtoAncestor < minDis) {
                    minDis = disAtoAncestor + disBtoAncestor;
                    realAncestor = potentialAncestor;
                }
            }
            // add all the neighbors of potentialAncestor into queue
            for (int neighbor: this.G.adj(potentialAncestor)){
                if (!visited[neighbor]) {
                    queue.enqueue(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        if(s.equals("lengthSubset")){
            return minDis;
        } else {
            return realAncestor;
        }
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        if (subsetA == null || subsetB == null) throw new IllegalArgumentException("Subset is null");
        Iterator itA = subsetA.iterator();
        Iterator itB = subsetB.iterator();
        if (!itA.hasNext() || !itB.hasNext()) throw new IllegalArgumentException("Iterable argument contains zero vertices");

        return helperSetVertices(subsetA, subsetB, "lengthSubset");
    }


    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        if (subsetA == null || subsetB == null) throw new IllegalArgumentException("Subset is null");
        Iterator itA = subsetA.iterator();
        Iterator itB = subsetB.iterator();
        if (!itA.hasNext() || !itB.hasNext()) throw new IllegalArgumentException("Iterable argument contains zero vertices");

        return helperSetVertices(subsetA, subsetB, "ancestorSubset");
    }

    // unit testing (required)
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
    }
}
