//Some of the code was inspired and modified from https://www.youtube.com/watch?v=dS44jZyj5gU&t=4175s 

package com.example.a18may;

public class Edge {
    private Vertex start;
    private Vertex end;
    private Double weight;

    public Edge(Vertex startV, Vertex endV, Double inputWeight)
    {
        this.start=startV;
        this.end= endV;
        this.weight= inputWeight;

    }
    public Vertex getStart()
    {
        return this.start;
    }
    public Vertex getEnd()
    {
        return this.end;
    }
    public Double getWeight()
    {
        return this.weight;
    }
}
