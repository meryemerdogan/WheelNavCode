package graphs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Graph
 * Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 * Changes made by @author Meryem Erdoğan
 */
public class Graph {

    private int V;                          //vertex num
    private int E;                          //edge num
    private ArrayList<Integer>[] adj;       //adjacency list
    Scanner in;                             //scanner object for reading files
    Edge edges[];

    public Graph(File f) throws FileNotFoundException
    {        
        setGraph();
        
        in = new Scanner(f);
        
        this.V = in.nextInt();
        int E = in.nextInt();

        edges = new Edge[E];

        for (int i = 0; i < E; i++) {
            int v = in.nextInt();
            int w = in.nextInt();
            double weight = in.nextDouble();
            addEdge(v, w, weight);
        }
    }
    public Graph(int V)
    {
        setGraph();
        this.V = V;
    }

    private void setGraph()
    {
        this.E = 0;
        
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        
        for (int v = 0; v < V; v++) 
        {
            adj[v] = new ArrayList<Integer>();
        }
    }
    
    public int V()      {  return V;  }
    public int E()      {  return E;  }

    public void addEdge( int v, int w ,double weight)
    {
        adj[v].add(w);
        adj[w].add(v);
        edges[E] = new Edge(v, w, weight);
        E++;
    }

    public Iterable<Integer> adj(int v)        
    {  return adj[v];  }

}
/******************************************************************************
 *  Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 ******************************************************************************/

