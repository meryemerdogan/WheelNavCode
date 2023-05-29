package com.example.a18may;

import android.util.Log;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

    public class Dijkstra {
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
        private static double commaToDot(String s)
        {
            s = s.replaceAll(",",".");
            return Double.parseDouble(s);
        }

        public static Vertex[] returnVerticiesToTheGraph(String text, Graph g)
        {
            Scanner initial = new Scanner(text);
            Scanner in = new Scanner(text);
            int nuOfVerticies=-1;
            while(initial.hasNextLine()){
                nuOfVerticies++;
                initial.nextLine();
            }
            //System.out.println(nuOfVerticies);
            Vertex [] verticies = new Vertex[nuOfVerticies];
            in.nextLine();
            for (int i = 0; i < nuOfVerticies; i++) {

                double longitude = commaToDot(in.next());
                double latitude = commaToDot(in.next());
                int id = in.nextInt();
                //System.out.println(longitude + " " + latitude + " " + id);
                verticies[i] = new Vertex(id,longitude,latitude);
                g.addVertex(verticies[i]);
            }
            return verticies;
        }

        public static void addEdges(String text, Graph g) {

            Scanner in = new Scanner(text);
            Log.d("Dijkstra", "started adding edges");
            while(in.hasNextLine()) {
                int firstVertexID = in.nextInt();
                int secondVertexID = in.nextInt();
                Log.d("Dijkstra", "first vertex:" + firstVertexID+ "second vertex: " + secondVertexID);
                try{
                    Vertex v1 = g.getVertexByValue(firstVertexID);
                    Vertex v2 = g.getVertexByValue(secondVertexID);
                    g.addEdge(v1, v2, findDistanceBetweenTwoVertices(v1, v2));
                }
                catch(NoSuchElementException e){
                    Log.d("Dijkstra",firstVertexID +" or " +  secondVertexID + "do not exist");
                }
                if(secondVertexID == 126){
                    break;
                }

                //System.out.println(v1.getID() + " " + v2.getID());
            }

        }
        public static double findDistanceBetweenTwoVertices(Vertex v1, Vertex v2){
            double horizontalDistance = v1.getLatitude()- v2.getLatitude();
            double verticalDistance = v1.getLongitude() -v2.getLongitude();
            return Math.sqrt(Math.pow(horizontalDistance,2) + Math.pow(verticalDistance,2));
        }



    /*public static void main(String[] args) {
        File file = new File("AllNodes.txt");
        Graph testGraph = new Graph(true, false);
        File edgesFile = new File("AllEdges.txt");
        try {
            returnVerticiesToTheGraph(file, testGraph);
            addEdges(edgesFile, testGraph);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        shortestPathBetween(testGraph, testGraph.getVertexByValue(3), testGraph.getVertexByValue(57));
    }
    */

    }

