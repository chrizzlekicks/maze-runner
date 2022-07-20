import java.util.*;

/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Chris Schimetschka
 */
public class Maze{
    private final int N;
    private final Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        this.M.addEdge(v, w);
    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge(int v, int w) {
        if (v > this.N || w > this.N) return false;
        return M.adj(v).contains(w) || M.adj(w).contains(v) || v == w;
    }

    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        Graph G = new Graph(N*N);
        // initialize a 2d array as representation of the grid
        int[][] nodes = new int[N][N];
        // initialize starting value of nodes
        int count = 0;
        // populate the array with respective node values
        for (int r = 0; r < nodes.length; r++) {
            for (int c = 0; c < nodes[r].length; c++) {
                nodes[r][c] = count;
                count++;
            }
        }
        // add vertical edges to grid
        for (int[] node : nodes) {
            for (int j = 0; j < node.length-1; j++) {
                G.addEdge(node[j], node[j+1]);
            }
        }
        // add horizontal edges to grid
        for (int j = 0; j < nodes[0].length; j++) {
            for (int i = 0; i < nodes.length-1; i++) {
                G.addEdge(nodes[i][j], nodes[i+1][j]);
            }
        }
        return G;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
        Graph g = this.mazegrid();
        RandomDepthFirstPaths r = new RandomDepthFirstPaths(g, startnode);
        r.randomDFS(g);
        int[] edges = r.edge();
        for (int i = 0; i < this.M.V(); i++) {
            this.addEdge(i, edges[i]);
        }
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w) {
        DepthFirstPaths d = new DepthFirstPaths(M, w);
        List<Integer> way = new LinkedList<>();
        d.dfs(M);
        if (v == w) way.add(0);
        else way = d.pathTo(v);
        return way;
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
        Maze m = new Maze(50, 0);
        Graph g = m.M();
        GridGraph vis = new GridGraph(g, m.findWay(0, 2499));
        vis.plot();
    }


}

