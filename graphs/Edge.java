package graphs;
/**
 * Edge
 * Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 * Changes made by @author Meryem Erdoğan
 */
public class Edge implements Comparable<Edge> {
    
    private int startVertex;
    private int endVertex;
    private double weight;
    
    public Edge(int v, int w, double weight)
    {
        this.startVertex = v;
        this.endVertex = w;
        this.weight = weight;
    }

    public int from() 
    { return startVertex; }
    
    public int to() 
    { 
        return endVertex;
    }

    @Override
    public int compareTo(Edge that) {
        if(this.weight < that.weight)       { return -1; }
        else if(this.weight > that.weight)  { return  1; }
        else                                { return  0; }
    }
}
/******************************************************************************
 *  Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 ******************************************************************************/
