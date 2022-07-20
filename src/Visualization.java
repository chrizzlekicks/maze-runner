import java.awt.geom.Point2D;

/**
 * Visualization tool to plot DiGraphs
 * 
 * Sets canvas size as well as colours etc.
 * Superclass to VisualGraph and GridGraph.
 * VisualGraph plots a directed Graph with weights- the nodes are arranged
 * in a circle.
 * GridGraph plots the Graph without weight or directions- the nodes are
 * arranged in a grid.
 * 
 * @author Prof. Benjamin Blankertz, Vera Röhr
 */

public abstract class Visualization {
  public Graph G;
  protected Config config;
  protected Point2D.Double[] vertices;
  public int nodesl;
  public int canvas;
  
  public Visualization(Graph G)
  {
    this(G, 0);
  }
  
  public Visualization(Graph G, double rMin)
  {
	config = new Config();
	if (G.V() > 20*20)
		config.r/= 2.0;
    this.G = G;
    this.config = config;
    this.canvas = 1050;
    StdDraw.enableDoubleBuffering();
    StdDraw.setCanvasSize(canvas, canvas);
    StdDraw.setXscale(-0.02*canvas, canvas*1.02);
    StdDraw.setYscale(-0.02*canvas, canvas*1.02);
    StdDraw.clear(config.COL_BG);

    this.nodesl = G.V();
    vertices= new Point2D.Double[nodesl];
    for (int v = 0; v < nodesl; v++)
      vertices[v]= new Point2D.Double();
  }

  

}

