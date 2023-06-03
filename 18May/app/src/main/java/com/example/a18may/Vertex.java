//Some of the code was inspired and modified from https://www.youtube.com/watch?v=dS44jZyj5gU&t=4175s 

package com.example.a18may;
import java.util.ArrayList;

public class Vertex {
    private int data;
    private ArrayList<Edge> edges;
    private double longitude;
    private double latitude;

    public Vertex(int id, double longitude, double latitude)
    {
        this.data = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.edges= new ArrayList<Edge>();
    }

    public void addEdge(Vertex endVertex, Double weight)
    {
        this.edges.add(new Edge(this, endVertex, weight));
    }

    public void removeEdge(Vertex endVertex)
    {
        this.edges.removeIf(edge -> edge.getEnd().equals(endVertex));
    }

    public int getID()
    {
        return this.data;
    }

    public double getLatitude(){
        return this.latitude;

    }

    public double getLongitude(){
        return this.longitude;
    }

    public ArrayList<Edge> getEdges()
    {
        return this.edges;
    }

    public void print(boolean showWeight)
    {
        String message= "";
        if(this.edges.size()==0)
        {
            System.out.println(this.data + "-->");
            return;
        }
        for(int i=0; i< this.edges.size(); i++)
        {
            if(i==0)
            {
                message += this.edges.get(i).getStart().data+"-->";
            }
            message += this.edges.get(i).getEnd().data;
            if(showWeight)
            {
                message += " ("+this.edges.get(i).getWeight()+ ")";
            }
            if( i != this.edges.size() - 1)
            {
                message += ". ";
            }
        }
        System.out.println(message);
    }
}
