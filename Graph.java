import java.util.ArrayList;

public class Graph {
    
    private ArrayList<Vertex> vertices;
    private boolean isWeighted;
    private boolean isDirected;

    public Graph(boolean inputIsWeighted, boolean inputIsDirected)
    {
        this.vertices= new ArrayList<Vertex>();
        this.isWeighted=inputIsWeighted;
        this.isDirected= inputIsDirected;

    }

    public Vertex addVertex(String data)
    {
        Vertex newVertex = new Vertex(data);
        this.vertices.add(newVertex);
        return newVertex;
    }

    public void addEdge(Vertex vertex1, Vertex vertex2, Double weight)
    {
        if(!this.isWeighted)
        {
            weight= null;
        }
        vertex1.addEdge(vertex2, weight);
        if (!this.isDirected)
        {
            vertex2.addEdge(vertex1, weight);
        }
    }

    public void removeEdge(Vertex vertex1, Vertex vertex2)
    {
        vertex1.removeEdge(vertex2);
        if(!this.isDirected)
        {
            vertex2.removeEdge(vertex1);
        }
    }

    public void removeVertex(Vertex vertex)
    {
        this.vertices.remove(vertex);
    }

    public ArrayList<Vertex> getVertices()
    {
        return this.vertices;
    }

    public boolean isWeighted()
    {
        return this.isWeighted;
    }

    public boolean isDirected()
    {
        return this.isDirected;
    }
    public Vertex getVertexByValue(String value)
    {
        for (Vertex v: this.vertices)
        {
            if(v.getData().equals(value))
            { 
                return v;
            }
        }
        return null;
    }

    public void print()
    {
        for (Vertex v: this.vertices)
        {
            v.print(isWeighted);
        }
    }
    public static void main(String[] args) {
        Graph routeNetwork= new Graph(true, true);
        Vertex G1 = routeNetwork.addVertex("G1");
        Vertex G2= routeNetwork.addVertex("G2");

        routeNetwork.addEdge(G1,G2,100.0);
        routeNetwork.print();

    }
}
