import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Visualization tool to plot graphs in a grid.
 * 
 * To plot a already constructed DiGraph graph: new GridGraph(graph)
 * To plot a graph and mark a path (Node []) in that Graph:
 * new GridGraph(graph, path)
 * @author Prof. Benjamin Blankertz, Vera Röhr
 */

public class GridGraph extends Visualization{
	  
	public GridGraph(Graph G) {
		super(G);
		double grid= canvas;
		int column= (int) Math.sqrt(nodesl);		
		int gridlength = (int)Math.ceil(grid/column);
		int k=0;
		//set location for nodes
		for (int node=0; node< G.V(); node++) {
			int x= node %column;
			int y= (int) Math.floor(node/column);
			vertices[k].setLocation(y*gridlength, canvas-x*(gridlength-1));
			k++;
		}
	    plot();

	}

	  public void plot()
	  {
	    StdDraw.setPenColor(config.COL_FG);
	    StdDraw.setPenRadius(config.LINEWIDTH);
	    int v=0;
	    //draw nodes
	    for (int node=0; node< G.V();node++) {
	      Point2D.Double p0 = vertices[v];
		    //StdDraw.setPenRadius(config.LINEWIDTH*3);

	      StdDraw.point(p0.getX(), p0.getY());
	      //draw edges
	     for (int adj: G.adj(node)) {
	 	    //StdDraw.setPenRadius(config.LINEWIDTH);

	        Point2D.Double p1 = vertices[adj];
	        StdDraw.line(p0.getX(), p0.getY(), p1.getX(), p1.getY());
	      }
	    v++;
	    }
	    StdDraw.show();
	  }
	  
		public GridGraph(Graph G, List <Integer> path) {
			super(G);
			double grid= canvas;
			int column= (int) Math.sqrt(nodesl);		
			int gridlength = (int)Math.ceil(grid/column);
			int k=0;
			for (int node=0; node< G.V();node++) {
				int x= node %column;
				int y= (int) Math.floor(node/column);
				vertices[k].setLocation(y*gridlength, canvas-x*(gridlength-1));
				k++;
			}
			plot();
		    plot(path, 2);
		}
	  
	  public void plot(List<Integer> path, double enlargeStart)
	  {
	    int index=0;
	    //color nodes and edges in path differently
	    for (int i =0; i<path.size(); i++) {
            int node=path.get(i);
            Point2D.Double p0 = vertices[node];
            StdDraw.setPenColor(config.COL_EMPH);
	        StdDraw.setPenRadius(config.LINEWIDTH);
	        StdDraw.point(p0.getX(), p0.getY());
	        //StdDraw.point(p1.getX(), p1.getY());
		    //StdDraw.filledCircle(p0.getX(), p0.getY(), config.r*enlargeStart);

	      for (int adj : G.adj(node)) {
	        if (i<path.size()-1 && adj==path.get(i+1)) {
	          StdDraw.setPenColor(config.COL_EMPH);
	          StdDraw.setPenRadius(config.LINEWIDTH_MARKED);
	        } 
	        else {
	          StdDraw.setPenColor(config.COL_FG);
	          StdDraw.setPenRadius(config.LINEWIDTH);
	        }
	        Point2D.Double p1 = vertices[adj];
	        StdDraw.line(p0.getX(), p0.getY(), p1.getX(), p1.getY());

	      }
		  StdDraw.pause(50);
		  StdDraw.show();

	    }
	  }
	  
	  
}

