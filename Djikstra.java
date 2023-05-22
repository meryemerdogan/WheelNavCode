import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Djikstra {
    public static ArrayList<Vertex> path;

    private static Dictionary[] djikstra(Graph g, Vertex startingVertex)
    {
        Dictionary<Integer, Double> distances = new Hashtable<>();
        Dictionary<Integer, Vertex>  previous = new Hashtable<>();

        PriorityQueue<QueueObject> queue= new PriorityQueue<QueueObject>();

        queue.add(new QueueObject(startingVertex, 0));

        for(Vertex v: g.getVertices())
        {
            if(v!=startingVertex)
            {
                distances.put(v.getID(), Double.MAX_VALUE);
            }
            previous.put(v.getID(), new Vertex(-1,0.0,0.0));
        }

        distances.put(startingVertex.getID(), 0.0);

        while (queue.size() != 0)
        {
            Vertex current = queue.poll().vertex;
            for (Edge e: current.getEdges())
            {
                Double alternative = distances.get(current.getID()) + e.getWeight();
                int neighbourValue = e.getEnd().getID();

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
            System.out.println(nextKey + ": "+ nextVertex.getID());
        }
    }

    public static ArrayList<Vertex> shortestPathBetween(Graph g, Vertex startingVertex, Vertex targetVertex)
    {
        Dictionary[] djikstraDictionaries= djikstra(g, startingVertex);
        Dictionary distances = djikstraDictionaries[0];
        Dictionary previous = djikstraDictionaries[1];
        path = new ArrayList<>();

        Double distance = (Double) distances.get(targetVertex.getID());
        System.out.println("Shortest Distance between "+ startingVertex.getID()+" and "+targetVertex.getID());
        System.out.println(distance);

        
        Vertex v = targetVertex; 

        while(v.getID() != -1)
        {
            path.add(0, v);
            v= (Vertex) previous.get(v.getID());
        }
        System.out.println("Shortest Path");
        for(Vertex pathVertex : path)
        {
            System.out.println(pathVertex.getID());
        }

        return path;
    }
     
    public Vertex[] returnVerticies(File f) throws FileNotFoundException
    {
        Scanner in = new Scanner(f);
        int nuOfVerticies = in.nextInt();
        Vertex [] verticies = new Vertex[nuOfVerticies];
        
        for (int i = 0; i < nuOfVerticies; i++) {
            double longitude = in.nextDouble();
            double latitude = in.nextDouble();

            int id = in.nextInt(); 

            verticies[i] = new Vertex(id,longitude,latitude);
        }
        return verticies;
    }
    
    public static double findDistanceBetweenTwoVertices(Vertex v1, Vertex v2){
        double horizontalDistance = v1.getLatitude()- v2.getLatitude();
        double verticalDistance = v1.getLongitude() -v2.getLongitude();
        return Math.sqrt(Math.pow(horizontalDistance,2) + Math.pow(verticalDistance,2));
    }
     


    public static void main(String[] args) {
        Graph test = new Graph(true,true);
        Vertex a = new Vertex(0,3,3.26);
        Vertex b = new Vertex (1,3,4);
        test.addVertex(a);
        test.addVertex(b);
        test.addEdge(a, b, findDistanceBetweenTwoVertices(a, b));
        shortestPathBetween(test, a, b);
       /* Vertex a =test.addVertex(0,3,3.26);
        Vertex b =test.addVertex(1,);
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
        */
    
    
    }
}