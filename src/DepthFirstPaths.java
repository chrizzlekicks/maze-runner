/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java
 ******************************************************************************/

/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  
 *  DISCLAIMER:
 *  These methods have been partly adjusted to fit the excersise.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;


public class DepthFirstPaths {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private final int[] edgeTo;        // edgeTo[v] = last adjacent node on s-v path
    private final int[] distTo;      // distTo[v] = number of edges s-v path
    private final int s;         // source vertex
    private final Queue<Integer> preorder;   // vertices in preorder
    private final Queue<Integer> postorder;  // vertices in postorder

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        postorder = new LinkedList<>();
        preorder  = new LinkedList<>();

        validateVertex(s);
    }
    
    public void dfs(Graph G) {
    	dfs(G, s);
    }

    // depth first search from v
    private void dfs(Graph G, int v) {
        int dist = 0;
        marked[v] = true;
        this.preorder.offer(v);
        this.distTo[s] = dist;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                this.edgeTo[w] = v;
                dfs(G, w, dist);
            }
            this.distTo[v] = dist;
        }
        if (!this.postorder.contains(v)) this.postorder.offer(v);
    }

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param v the vertex
     * @param dist the current distance to source
     */
    private void dfs(Graph G, int v, int dist) {
        marked[v] = true;
        dist++;
        this.preorder.offer(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                this.edgeTo[w] = v;
                dfs(G, w, dist);
            }
            this.distTo[v] = dist;
        }
        if (!this.postorder.contains(v)) this.postorder.offer(v);
    }

    public void nonrecursiveDFS(Graph G) {
        marked = new boolean[G.V()];
        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // depth-first search using an explicit stack
        Stack<Integer> stack = new Stack<Integer>();
        int dist = 0;
        marked[s] = true;
        stack.push(s);
        this.preorder.offer(s);
        this.distTo[s] = dist;
        while (!stack.isEmpty()) {
            int v = stack.peek();
            this.distTo[v] = dist;
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    // discovered vertex w for the first time
                    marked[w] = true;
                    this.preorder.offer(w);
                    stack.push(w);
                    this.edgeTo[w] = v;
                    dist++;
                }
            } else {
                int u = stack.pop();
                this.postorder.offer(u);
                dist--;
            }
        }
    }
    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable (beginning and end are included)
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * 
     * This method is different compared to the original one.
     */
    public List<Integer> pathTo(int v) {
		// TODO
        if (this.edgeTo[v] == v) return null;
        List<Integer> sequence = new LinkedList<>();
        int tmp = v;
        sequence.add(tmp);
        while (this.edgeTo[tmp] != this.s) {
            sequence.add(this.edgeTo[tmp]);
            tmp = this.edgeTo[tmp];
        }
        sequence.add(this.s);
        return sequence;
    }
    
    /**
     * Returns the vertices in postorder. This method differs from the original.
     * @return the vertices in postorder, as a queue of vertices
     */
    public Queue<Integer> post() {
        return postorder;
    }

    /**
     * Returns the vertices in preorder. This method differs from the original.
     * @return the vertices in preorder, as a queue of vertices 
     */
    public Queue<Integer> pre() {
        return preorder;
    }   
    
    /**
     * Returns the class variable edgeTo. This method differs from the original.
     * @return egdeTo
     */
    public int [] edge() {
    	return edgeTo;
    }
    
    /**
     * Returns the class variable distTo. This method differs from the original.
     * @return distTo
     */
    public int [] dist() {
    	return distTo;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}

