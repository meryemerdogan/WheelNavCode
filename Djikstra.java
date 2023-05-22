import java.util.*;

public class Djikstra {
    public static ArrayList<Vertex> path;

    private static Dictionary[] djikstra(Graph g, Vertex startingVertex)
    {
        Dictionary<String, Double> distances = new Hashtable<>();
        Dictionary<String, Vertex>  previous = new Hashtable<>();

        PriorityQueue<QueueObject> queue= new PriorityQueue<QueueObject>();

        queue.add(new QueueObject(startingVertex, 0));

        for(Vertex v: g.getVertices())
        {
            if(v!=startingVertex)
            {
                distances.put(v.getData(), Double.MAX_VALUE);
            }
            previous.put(v.getData(), new Vertex("Null"));
        }

        distances.put(startingVertex.getData(), 0.0);

        while (queue.size() != 0)
        {
            Vertex current = queue.poll().vertex;
            for (Edge e: current.getEdges())
            {
                Double alternative = distances.get(current.getData()) + e.getWeight();
                String neighbourValue = e.getEnd().getData();

                if(alternative < distances.get(neighbourValue))
                {
                    distances.put(neighbourValue, alternative);
                    previous.put(neighbourValue, current);
                    queue.add(new QueueObject(e.getEnd(), distances.get(neighbourValue)));
                }
            }
        }
        return new Dictionary[] {distances, previous};
    }

    public void djikstraResultPrinter(Dictionary[] d)
    {
        System.out.println("Distances:\n");
        for( Enumeration keys = d[0].keys(); keys.hasMoreElements();)
        {
            String nextKey = keys.nextElement().toString();
            System.out.println(nextKey + ": "+ d[0].get(nextKey));
        }
        System.out.println("\nPrevious:\n");
        for( Enumeration keys = d[1].keys(); keys.hasMoreElements();)
        {
            String nextKey = keys.nextElement().toString();
            Vertex nextVertex= (Vertex) d[1].get(nextKey);
            System.out.println(nextKey + ": "+ nextVertex.getData());
        }
    }

    public static ArrayList<Vertex> shortestPathBetween(Graph g, Vertex startingVertex, Vertex targetVertex)
    {
        Dictionary[] djikstraDictionaries= djikstra(g, startingVertex);
        Dictionary distances = djikstraDictionaries[0];
        Dictionary previous = djikstraDictionaries[1];
        path = new ArrayList<>();

        Double distance = (Double) distances.get(targetVertex.getData());
        System.out.println("Shortest Distance between "+ startingVertex.getData()+" and "+targetVertex.getData());
        System.out.println(distance);

        
        Vertex v = targetVertex; 

        while(v.getData() != "Null")
        {
            path.add(0, v);
            v= (Vertex) previous.get(v.getData());
        }
        System.out.println("Shortest Path");
        for(Vertex pathVertex : path)
        {
            System.out.println(pathVertex.getData());
        }

        return path;
    }
     


    public static void main(String[] args) {
        Graph test = new Graph(true,true);
        Vertex a =test.addVertex("A");
        Vertex b =test.addVertex("B");
        Vertex c =test.addVertex("C");
        Vertex d =test.addVertex("D");
        Vertex e =test.addVertex("E");
        Vertex f =test.addVertex("F");
        Vertex g =test.addVertex("G");

        test.addEdge(a,c,100.0);
        test.addEdge(a,b,3.0);
        test.addEdge(a,d,4.0);
        test.addEdge(d,c,3.0);
        test.addEdge(d,e,8.0);
        test.addEdge(e,f,10.0);
        test.addEdge(b,g,9.0);
        test.addEdge(e,g,13.0);
        test.addEdge(f,g,5.0);

        //djikstraResultPrinter(djikstra(test, a));

        shortestPathBetween(test, a,g);
    
    }
}